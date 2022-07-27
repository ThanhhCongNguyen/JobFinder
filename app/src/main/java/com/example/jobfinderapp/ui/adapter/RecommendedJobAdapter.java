package com.example.jobfinderapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderapp.databinding.ItemRecommendedJobBinding;
import com.example.jobfinderapp.repository.database.local.entity.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecommendedJobAdapter extends RecyclerView.Adapter<RecommendedJobAdapter.RecommendedViewHolder> {
    private List<Result> results;
    private Callback callback;

    public RecommendedJobAdapter(Callback callback) {
        this.results = new ArrayList<>();
        this.callback = callback;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecommendedJobBinding binding = ItemRecommendedJobBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecommendedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        Result result = results.get(position);
        if (result != null) {
            holder.binding.title.setText(result.getTitle());
            holder.binding.companyName.setText(result.getCompany().getDisplayName());
            holder.binding.location.setText(result.getLocation().getDisplayName());

            String isFullTime = result.getContractTime();
            if (isFullTime == null) {
                isFullTime = "No Data";
            }
            holder.binding.fullTimeText.setText(isFullTime);
            String created = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(result.getCreated());
            holder.binding.createdText.setText(created);

            holder.itemView.setOnClickListener(view -> {
                callback.onItemClick(result);
            });

            holder.binding.save.setOnClickListener(view -> {
                callback.saveJob(result);
            });
        }
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    public class RecommendedViewHolder extends RecyclerView.ViewHolder {
        private ItemRecommendedJobBinding binding;

        public RecommendedViewHolder(@NonNull ItemRecommendedJobBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onItemClick(Result result);

        void saveJob(Result result);
    }
}
