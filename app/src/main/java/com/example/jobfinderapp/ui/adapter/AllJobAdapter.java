package com.example.jobfinderapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderapp.R;
import com.example.jobfinderapp.databinding.ItemRecommendedJobBinding;
import com.example.jobfinderapp.repository.database.local.entity.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AllJobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Result> results;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private boolean isLoadingAdded = false;
    private Callback callback;

    public AllJobAdapter(Callback callback) {
        this.results = new ArrayList<>();
        this.callback = callback;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM:
                ItemRecommendedJobBinding binding = ItemRecommendedJobBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                viewHolder = new RecommendedViewHolder(binding);
                break;
            case LOADING:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Result result = results.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                RecommendedViewHolder recommendedViewHolder = (RecommendedViewHolder) holder;
                recommendedViewHolder.binding.companyName.setText(result.getCompany().getDisplayName());
                recommendedViewHolder.binding.title.setText(result.getTitle());
                recommendedViewHolder.binding.location.setText(result.getLocation().getDisplayName());

                String isFullTime = result.getContractTime();
                if (isFullTime == null) {
                    isFullTime = "No Data";
                }
                recommendedViewHolder.binding.fullTimeText.setText(isFullTime);
                String created = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(result.getCreated());
                recommendedViewHolder.binding.createdText.setText(created);

                recommendedViewHolder.itemView.setOnClickListener(view -> {
                    callback.onItemClick(result);
                });

                recommendedViewHolder.binding.save.setOnClickListener(view -> {
                    callback.saveJob(result);
                });
                break;

            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == results.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public interface Callback {
        void onItemClick(Result result);

        void saveJob(Result result);
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        //  add(new Result());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = results.size() - 1;
        Result result = getItem(position);

        if (result != null) {
            results.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(Result result) {
        results.add(result);
        notifyItemInserted(results.size() - 1);
    }

    public Result getItem(int position) {
        return results.get(position);
    }

    public class RecommendedViewHolder extends RecyclerView.ViewHolder {
        private ItemRecommendedJobBinding binding;

        public RecommendedViewHolder(@NonNull ItemRecommendedJobBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
