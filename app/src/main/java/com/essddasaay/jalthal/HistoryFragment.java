package com.essddasaay.jalthal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private AnalysisAdapter adapter;
    private List<AnalysisResult> analysisResults;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        // Initialize the list and load saved data
        analysisResults = new ArrayList<>();
        loadAnalysisResults();

        // Set up RecyclerView
        adapter = new AnalysisAdapter(analysisResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void loadAnalysisResults() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("WaterAnalysis", Context.MODE_PRIVATE);
        String savedData = sharedPreferences.getString("AnalysisResults", "[]"); // Default is an empty JSON array
        Gson gson = new Gson();

        // Deserialize the saved JSON data into a list
        analysisResults.clear();
        analysisResults.addAll(gson.fromJson(savedData, new TypeToken<List<AnalysisResult>>() {}.getType()));
    }
}
