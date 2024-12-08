package com.essddasaay.jalthal;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    // Declare buttons
    private Button btnHome, btnAnalytics, btnHistory, btnLink;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize buttons
        btnHome = rootView.findViewById(R.id.btnHome);
        btnAnalytics = rootView.findViewById(R.id.btnAnalytics);
        btnHistory = rootView.findViewById(R.id.btnHistory);
        btnLink = rootView.findViewById(R.id.btnLink);

        // Set click listeners
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a round blue Toast in the center
                showCenterToast("Loading page... Please wait");
                // Load HomeFragment (if it's not the current one)
                // Create a Handler to introduce a delay of 3 seconds (3,000 milliseconds)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Create an Intent to open the URL in a browser
                        loadFragment(new HomeFragment());

                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });

        btnAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCenterToast("Loading page... Please wait");
                // Load AnalyticsFragment
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Create an Intent to open the URL in a browser
                        loadFragment(new AnalysisFragment());

                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCenterToast("Loading page... Please wait");
                // Load HistoryFragment
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Create an Intent to open the URL in a browser
                        loadFragment(new HistoryFragment());


                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCenterToast("Loading page... Please wait");

                // Load LinkFragment
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Create an Intent to open the URL in a browser
                        loadFragment(new LinkFragment());


                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });

        return rootView;
    }

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

    // Method to replace fragments
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
        transaction.commit();
    }
}
