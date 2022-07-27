package com.example.jobfinderapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderapp.databinding.ItemMarkedJobBinding;
import com.example.jobfinderapp.repository.database.local.entity.Result;

import java.util.ArrayList;
import java.util.List;

public class MarkerJobAdapter extends RecyclerView.Adapter<MarkerJobAdapter.MarkedViewHolder> {
    private List<Result> results;
    private Callback callback;

    public MarkerJobAdapter(Callback callback) {
        this.results = new ArrayList<>();
        this.callback = callback;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MarkedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMarkedJobBinding binding = ItemMarkedJobBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MarkedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkedViewHolder holder, int position) {
        Result result = results.get(position);
        if (result != null) {
            holder.binding.jobName.setText(result.getTitle());
            holder.binding.companyName.setText(result.getCompany().getDisplayName());
            holder.binding.salary.setText((int)result.getSalaryMin() + " / " + (int)result.getSalaryMax());
            holder.binding.location.setText(result.getLocation().getDisplayName());

            holder.itemView.setOnClickListener(view -> {
                callback.onItemClick(result);
            });
        } else return;
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    public class MarkedViewHolder extends RecyclerView.ViewHolder {
        private ItemMarkedJobBinding binding;

        public MarkedViewHolder(@NonNull ItemMarkedJobBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onItemClick(Result result);

        void deleteItem(Result result, int position);
    }

    public void deleteItem(Result result, int position) {
        callback.deleteItem(result, position);
        results.remove(position);
        notifyItemRemoved(position);
    }

    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private MarkerJobAdapter markerJobAdapter;

        public SwipeToDeleteCallback(MarkerJobAdapter markerJobAdapter) {
            super(0, ItemTouchHelper.UP | ItemTouchHelper.DOWN);
            this.markerJobAdapter = markerJobAdapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Result result = markerJobAdapter.results.get(position);
            markerJobAdapter.deleteItem(result, position);
        }
    }
}
