package com.example.jobfinderapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderapp.databinding.ItemSearchResultBinding;
import com.example.jobfinderapp.repository.local.entity.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private List<Result> results;
    private Callback callback;

    public SearchResultAdapter(Callback callback) {
        this.results = new ArrayList<>();
        this.callback = callback;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchResultBinding binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchResultViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        Result result = results.get(position);
        if (result != null) {
            holder.binding.companyName.setText(result.getCompany().getDisplayName());
            holder.binding.title.setText(result.getTitle());
            holder.binding.location.setText(result.getLocation().getDisplayName());

            String created = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(result.getCreated());
            holder.binding.createdText.setText(created);

            holder.itemView.setOnClickListener(view -> {
                callback.onItemClick(result);
            });

            holder.binding.save.setOnClickListener(view -> {
                callback.saveJob(result);
                holder.binding.save.setVisibility(View.INVISIBLE);
            });
        }
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }


    public interface Callback {
        void onItemClick(Result result);

        void saveJob(Result result);
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchResultBinding binding;

        public SearchResultViewHolder(@NonNull ItemSearchResultBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
