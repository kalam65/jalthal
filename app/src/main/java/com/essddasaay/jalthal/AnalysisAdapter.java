package com.essddasaay.jalthal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.AnalysisViewHolder> {
    private final List<AnalysisResult> analysisResults;
    private final int[] colors = {R.color.color1, R.color.color2, R.color.color3, R.color.color4};

    public AnalysisAdapter(List<AnalysisResult> analysisResults) {
        this.analysisResults = analysisResults;
    }

    @NonNull
    @Override
    public AnalysisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_analysis, parent, false);
        return new AnalysisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnalysisViewHolder holder, int position) {
        AnalysisResult result = analysisResults.get(position);
        holder.tvResult.setText(result.getResult());
        holder.tvTimestamp.setText(result.getTimestamp());

        // Set a random color for each CardView
        int randomColor = colors[new Random().nextInt(colors.length)];
        holder.cardView.setCardBackgroundColor(holder.cardView.getContext().getResources().getColor(randomColor));
    }

    @Override
    public int getItemCount() {
        return analysisResults.size(); // Show all items
    }

    static class AnalysisViewHolder extends RecyclerView.ViewHolder {
        TextView tvResult, tvTimestamp;
        CardView cardView;

        public AnalysisViewHolder(@NonNull View itemView) {
            super(itemView);
            tvResult = itemView.findViewById(R.id.tvResult);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
