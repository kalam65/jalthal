package com.essddasaay.jalthal;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnalysisFragment extends Fragment {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    private ImageView imgCaptured;
    private TextView tvResult;
    private MediaPlayer mediaPlayer;
    private Button btnSaveResult, btnCapture;
    private CheckBox checkboxPrivacy;

    private String waterType = "";  // To store water type
    private double pHValue = 0.0;   // To store pH value

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        // Initialize views
        imgCaptured = view.findViewById(R.id.imgCaptured);
        tvResult = view.findViewById(R.id.tvResult);
        btnSaveResult = view.findViewById(R.id.btnSaveResult);
        btnCapture = view.findViewById(R.id.btnCapture);
        checkboxPrivacy = view.findViewById(R.id.checkboxPrivacy);

        // Initialize MediaPlayer for sound effects
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.sound);

        // Set click listeners
        btnSaveResult.setOnClickListener(v -> saveAnalysisResult());
        btnCapture.setOnClickListener(v -> {
            if (checkboxPrivacy.isChecked()) {
                checkCameraPermission();
            } else {
                Toast.makeText(getContext(), "Please agree to the Privacy Policy", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void saveAnalysisResult() {
        String resultData = tvResult.getText().toString();
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("WaterAnalysis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String jsonData = sharedPreferences.getString("AnalysisResults", "[]");
        Type listType = new TypeToken<List<AnalysisResult>>() {}.getType();
        List<AnalysisResult> resultsList = gson.fromJson(jsonData, listType);

        if (resultsList == null) {
            resultsList = new ArrayList<>();
        }

        resultsList.add(new AnalysisResult(resultData, timestamp));

        editor.putString("AnalysisResults", gson.toJson(resultsList));
        editor.apply();

        Toast.makeText(getContext(), "Analysis result saved successfully!", Toast.LENGTH_SHORT).show();
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Camera permission granted", Toast.LENGTH_SHORT).show();
                openCamera();
            } else {
                Toast.makeText(getContext(), "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(getContext(), "No camera app available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == requireActivity().RESULT_OK) {
            if (data != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imgCaptured.setImageBitmap(imageBitmap);
                analyzeImage(imageBitmap);
            }
        } else {
            Toast.makeText(getContext(), "Failed to capture image", Toast.LENGTH_SHORT).show();
        }
    }

    private void analyzeImage(Bitmap bitmap) {
        int pixel = bitmap.getPixel(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;

        String result = analyzeWaterColor(red, green, blue);
        animateText(result);
    }

    private String analyzeWaterColor(int red, int green, int blue) {
        String waterType;
        double pHValue;

        // Check for white water (red, green, blue values are close and high)
        if (red >= 200 && green >= 200 && blue >= 200 && Math.abs(red - green) <= 30 && Math.abs(green - blue) <= 30 && Math.abs(blue - red) <= 30) {
            waterType = "White water";
            pHValue = 7.0;  // Example pH for neutral, clean water
            return "Water looks white with a pH of " + pHValue + ". It is likely pure, clean water.";
        }
        // Strongly Acidic (Red) - pH range: 0 - 3
        else if (red > green && red > blue && red >= 150) {
            waterType = "Strongly Acidic (Red water)";
            pHValue = 2.5;  // Example pH for battery acid
            return "Water looks strongly acidic (Red) with a pH of " + pHValue + ". It is like battery acid.";
        }
        // Mild Acidic (Orange) - pH range: 3 - 5
        else if (red > green && red > blue && red >= 100) {
            waterType = "Mildly Acidic (Orange water)";
            pHValue = 4.0;  // Example pH for vinegar
            return "Water looks mildly acidic (Orange) with a pH of " + pHValue + ". It is like vinegar or lemon juice.";
        }
        // Slightly Acidic (Yellow) - pH range: 5 - 6
        else if (yellowRange(red, green, blue)) {  // Custom function to detect yellowish tones
            waterType = "Slightly Acidic (Yellow water)";
            pHValue = 5.5;  // Example pH for slightly acidic water
            return "Water looks slightly acidic (Yellow) with a pH of " + pHValue + ". It is like tap water in some areas.";
        }
        // Neutral (Green) - pH range: 6 - 7
        else if (green > red && green > blue && green >= 50) {
            waterType = "Neutral (Green water)";
            pHValue = 7.0;  // Example pH for neutral water
            return "Water looks neutral (Green) with a pH of " + pHValue + ". It is like distilled water or clean rainwater.";
        }
        // Slightly Alkaline (Light Blue) - pH range: 7 - 9
        else if (blue > red && blue > green && blue >= 50 && blue < 100) {
            waterType = "Slightly Alkaline (Light Blue water)";
            pHValue = 8.0;  // Example pH for softened water
            return "Water looks slightly alkaline (Light Blue) with a pH of " + pHValue + ". It is like softened water.";
        }
        // Alkaline (Blue) - pH range: 9 - 11
        else if (blue > red && blue > green && blue >= 100 && blue < 150) {
            waterType = "Alkaline (Blue water)";
            pHValue = 10.0;  // Example pH for spring water
            return "Water looks alkaline (Blue) with a pH of " + pHValue + ". It is like spring water in some regions.";
        }
        // Highly Alkaline (Dark Blue/Purple) - pH range: 11 - 14
        else if (blue > red && blue > green && blue >= 150) {
            waterType = "Highly Alkaline (Dark Blue/Purple water)";
            pHValue = 12.0;  // Example pH for cleaning solutions
            return "Water looks highly alkaline (Dark Blue/Purple) with a pH of " + pHValue + ". It is like cleaning solutions.";
        }
        // If none of the above categories match, it's considered unclear or neutral
        else {
            waterType = "Unclear water";  // Possibly muddy or unclear water
            pHValue = 7.5;
            return "Water color is unclear with a pH of " + pHValue + ". It may be slightly alkaline.";
        }
    }

    // Helper function to detect yellow range based on RGB values
    private boolean yellowRange(int red, int green, int blue) {
        return (green > red && green > blue && green > 50 && red < 100 && blue < 100);
    }



    private void animateText(String result) {
        tvResult.setText("");
        tvResult.setTextSize(22);
        tvResult.setTextColor(getResources().getColor(R.color.blue));

        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        final Handler handler = new Handler();
        final int delay = 100;
        final String finalResult = result;

        handler.postDelayed(new Runnable() {
            int index = 0;

            @Override
            public void run() {
                if (index < finalResult.length()) {
                    tvResult.append(String.valueOf(finalResult.charAt(index++)));
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }
}
