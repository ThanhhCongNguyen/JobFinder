package com.example.jobfinderapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderapp.databinding.ItemSearchBinding;
import com.example.jobfinderapp.repository.local.entity.Search;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Search> searches;
    private Callback callback;

    public SearchAdapter(Callback callback) {
        this.searches = new ArrayList<>();
        this.callback = callback;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Search search = searches.get(position);
        if (search != null) {
            holder.binding.recentTitle.setText(search.getTitle());

            holder.itemView.setOnClickListener(view -> {
                callback.onItemClick(search);
            });

            holder.binding.delete.setOnClickListener(view -> {
                callback.deleteSearch(search);
            });
        }
    }

    @Override
    public int getItemCount() {
        return searches != null ? searches.size() : 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchBinding binding;

        public SearchViewHolder(@NonNull ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onItemClick(Search search);

        void deleteSearch(Search search);
    }
}
