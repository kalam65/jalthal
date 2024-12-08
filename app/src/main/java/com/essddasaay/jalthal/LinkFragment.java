package com.essddasaay.jalthal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LinkFragment extends Fragment {
    private Button btnHome, btnAnalytics, btnHistory, btnLink;
    private ImageView zoomCaterersBanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_link, container, false);

        // Initialize buttons
        btnHome = rootView.findViewById(R.id.btnHome);
        btnAnalytics = rootView.findViewById(R.id.btnAnalytics);
        btnHistory = rootView.findViewById(R.id.btnHistory);
        btnLink = rootView.findViewById(R.id.btnLink);
        zoomCaterersBanner=rootView.findViewById(R.id.zoomCaterersBanner);
        zoomCaterersBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a round blue Toast in the center
                showCenterToast("Loading link... Please wait");

                // Define the web URL to open
                String url = "https://play.google.com/store/search?q=zoom%20caterers&c=apps&hl=en";  // Replace with your desired URL

                // Create a Handler to introduce a delay of 3 seconds (3,000 milliseconds)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Create an Intent to open the URL in a browser
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                        // Start the activity
                        startActivity(intent);
                    }
                }, 1000); // 3000 milliseconds = 3 seconds
            }
        });

        //
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a round blue Toast in the center
                showCenterToast("Loading link... Please wait");

                // Define the web URL to open
                String url = "https://www.presidiumhisar.com/cbse/WATER-TESTING-REPORT-UPDATED.pdf";  // Replace with your desired URL

                // Create a Handler to introduce a delay of 3 seconds (3,000 milliseconds)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Create an Intent to open the URL in a browser
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                        // Start the activity
                        startActivity(intent);
                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });

        // Button to open link with a 3-second round blue Toast in the center
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a round blue Toast in the center
                showCenterToast("Loading link... Please wait");

                // Define the web URL to open
                String url = "https://www.fondriest.com/environmental-measurements/parameters/water-quality/ph/";  // Replace with your desired URL

                // Create a Handler to introduce a delay of 3 seconds (3,000 milliseconds)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Create an Intent to open the URL in a browser
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                        // Start the activity
                        startActivity(intent);
                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });

        // Button to open Analytics URL with a 3-second round blue Toast in the center
        btnAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a round blue Toast in the center
                showCenterToast("Loading link... Please wait");

                // Define the web URL to open
                String url = "https://www2.whoi.edu/site/eslab/seawater-sampling-reports/";  // Replace with your desired URL

                // Create a Handler to introduce a delay of 3 seconds (3,000 milliseconds)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Create an Intent to open the URL in a browser
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                        // Start the activity
                        startActivity(intent);
                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });

        // Button to open History URL with a 3-second round blue Toast in the center
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a round blue Toast in the center
                showCenterToast("Loading link... Please wait");

                // Define the web URL to open
                String url = "https://cdn.who.int/media/docs/default-source/wash-documents/wash-chemicals/ph.pdf?sfvrsn=16b10656_4#:~:text=The%20pH%20of%20most%20drinking,within%20the%20range%206.5%E2%80%938.5.";  // Replace with your desired URL

                // Create a Handler to introduce a delay of 3 seconds (3,000 milliseconds)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Create an Intent to open the URL in a browser
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                        // Start the activity
                        startActivity(intent);
                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });

        return rootView;
    }

    // Method to show a custom Toast in the center with blue background
    private void showCenterToast(String message) {
        // Create a custom Toast layout
        Toast toast = new Toast(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) getView(), false);
        // Set the message to the custom layout
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);
        // Set the background color of the Toast
        layout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark)); // Set blue background
        // Set the Toast to show in the center
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        // Show the Toast
        toast.show();
    }
}
