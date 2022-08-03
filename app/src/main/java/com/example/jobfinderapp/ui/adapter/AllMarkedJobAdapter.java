package com.example.jobfinderapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderapp.databinding.ItemAllMarkedJobBinding;
import com.example.jobfinderapp.repository.local.entity.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AllMarkedJobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Result> results;
    private Callback callback;

    public AllMarkedJobAdapter(Callback callback) {
        this.results = new ArrayList<>();
        this.callback = callback;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void deleteItem(Result result, int position) {
        callback.deleteItem(result, position);
        results.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAllMarkedJobBinding binding = ItemAllMarkedJobBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    public class RecommendedViewHolder extends RecyclerView.ViewHolder {
        private ItemAllMarkedJobBinding binding;

        public RecommendedViewHolder(@NonNull ItemAllMarkedJobBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onItemClick(Result result);

        void deleteItem(Result result, int position);
    }

    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private AllMarkedJobAdapter allMarkedJobAdapter;

        public SwipeToDeleteCallback(AllMarkedJobAdapter allMarkedJobAdapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.allMarkedJobAdapter = allMarkedJobAdapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Result result = allMarkedJobAdapter.results.get(position);
            allMarkedJobAdapter.deleteItem(result, position);
        }
    }
}
