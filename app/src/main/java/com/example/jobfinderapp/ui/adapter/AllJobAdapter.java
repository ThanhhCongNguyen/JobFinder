package com.example.jobfinderapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderapp.databinding.ItemAllRecommendedJobBinding;
import com.example.jobfinderapp.repository.local.entity.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AllJobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Result> results;
    private Callback callback;

    public AllJobAdapter(Callback callback) {
        this.results = new ArrayList<>();
        this.callback = callback;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void addMoreResults(List<Result> results) {
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAllRecommendedJobBinding binding = ItemAllRecommendedJobBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecommendedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Result result = results.get(position);
        RecommendedViewHolder recommendedViewHolder = (RecommendedViewHolder) holder;
        recommendedViewHolder.binding.companyName.setText(result.getCompany().getDisplayName());
        recommendedViewHolder.binding.title.setText(result.getTitle());
        recommendedViewHolder.binding.location.setText(result.getLocation().getDisplayName());

        String created = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(result.getCreated());
        recommendedViewHolder.binding.createdText.setText(created);

        recommendedViewHolder.itemView.setOnClickListener(view -> {
            callback.onItemClick(result);
        });

        recommendedViewHolder.binding.save.setOnClickListener(view -> {
            callback.saveJob(result, view);
            recommendedViewHolder.binding.save.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    public interface Callback {
        void onItemClick(Result result);

        void saveJob(Result result, View view);
    }

    public class RecommendedViewHolder extends RecyclerView.ViewHolder {
        private ItemAllRecommendedJobBinding binding;

        public RecommendedViewHolder(@NonNull ItemAllRecommendedJobBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
