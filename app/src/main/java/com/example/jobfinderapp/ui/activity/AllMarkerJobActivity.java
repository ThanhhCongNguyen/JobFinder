package com.example.jobfinderapp.ui.activity;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.example.jobfinderapp.R;
import com.example.jobfinderapp.databinding.ActivityAllMarkerJobBinding;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.ui.adapter.AllMarkedJobAdapter;
import com.example.jobfinderapp.ui.base.BaseActivity;
import com.example.jobfinderapp.utils.Utility;
import com.example.jobfinderapp.viewmodel.AllMarkedJobViewModel;
import com.google.android.material.snackbar.Snackbar;

public class AllMarkerJobActivity extends BaseActivity {
    private AllMarkedJobViewModel allMarkedJobViewModel;
    private AllMarkedJobAdapter adapter;
    private ActivityAllMarkerJobBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllMarkerJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolBar();

        allMarkedJobViewModel = new ViewModelProvider(this).get(AllMarkedJobViewModel.class);
        allMarkedJobViewModel.initRepository(getApplicationContext());
        adapter = new AllMarkedJobAdapter(new AllMarkedJobAdapter.Callback() {
            @Override
            public void onItemClick(Result result) {
                DetailActivity.starter(AllMarkerJobActivity.this, result);
            }

            @Override
            public void deleteItem(Result result, int position) {
                executor.execute(() -> allMarkedJobViewModel.delete(result));
                Utility.snackBar(getApplicationContext(), binding.getRoot(), "Deleted Successfully");
            }
        });

        binding.shimmerLayout.startShimmer();
        setUpRecyclerView();
        observeDataChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setUpRecyclerView() {
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new AllMarkedJobAdapter.SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
    }

    private void observeDataChanged() {
        allMarkedJobViewModel.getLiveData().observe(this, results -> {
            if (results == null) {
                return;
            }
            adapter.setResults(results);

            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        });
    }

    private void initToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }
}