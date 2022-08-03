package com.example.jobfinderapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jobfinderapp.databinding.FragmentSavedBinding;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.ui.activity.DetailActivity;
import com.example.jobfinderapp.ui.adapter.AllMarkedJobAdapter;
import com.example.jobfinderapp.ui.base.BaseFragment;
import com.example.jobfinderapp.utils.Utility;
import com.example.jobfinderapp.viewmodel.AllMarkedJobViewModel;

public class SavedFragment extends BaseFragment {
    private FragmentSavedBinding binding;
    private AllMarkedJobAdapter adapter;
    private AllMarkedJobViewModel allMarkedJobViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allMarkedJobViewModel = new ViewModelProvider(this).get(AllMarkedJobViewModel.class);
        allMarkedJobViewModel.initRepository(getContext());
        adapter = new AllMarkedJobAdapter(new AllMarkedJobAdapter.Callback() {
            @Override
            public void onItemClick(Result result) {
                DetailActivity.starter(getContext(), result);
            }

            @Override
            public void deleteItem(Result result, int position) {
                executor.execute(() -> allMarkedJobViewModel.delete(result));
                Utility.snackBar(getContext(), binding.getRoot(), "Deleted successfully");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSavedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView();
        observeData();
    }

    private void setUpRecyclerView() {
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new AllMarkedJobAdapter.SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
    }

    private void observeData() {
        allMarkedJobViewModel.getLiveData().observe(getViewLifecycleOwner(), results -> {
            if (results == null) {
                return;
            }
            if (results.size() > 0) {
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.image.setVisibility(View.GONE);
                adapter.setResults(results);
            } else {
                binding.recyclerView.setVisibility(View.GONE);
                binding.image.setVisibility(View.VISIBLE);
            }

        });
    }
}