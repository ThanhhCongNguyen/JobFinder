package com.example.jobfinderapp.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jobfinderapp.R;
import com.example.jobfinderapp.databinding.ActivityAllJobBinding;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.ui.adapter.AllJobAdapter;
import com.example.jobfinderapp.ui.base.BaseActivity;
import com.example.jobfinderapp.utils.Constants;
import com.example.jobfinderapp.utils.Utility;
import com.example.jobfinderapp.viewmodel.AllJobViewModel;

public class AllJobActivity extends BaseActivity {
    private ActivityAllJobBinding binding;
    private AllJobAdapter allJobAdapter;
    private AllJobViewModel allJobViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolBar();

        allJobViewModel = new ViewModelProvider(this).get(AllJobViewModel.class);
        allJobViewModel.initRepository(getApplicationContext());

        binding.shimmerLayout.startShimmer();

        allJobAdapter = new AllJobAdapter(new AllJobAdapter.Callback() {
            @Override
            public void onItemClick(Result result) {
                DetailActivity.starter(AllJobActivity.this, result);
            }

            @Override
            public void saveJob(Result result, View view) {
                executor.execute(() -> allJobViewModel.insert(result));
                Utility.snackBar(getApplicationContext(), binding.parentA, "Inserted successfully");
            }
        });

        setUpRecyclerView();
        observeDataChanged(String.valueOf(allJobViewModel.getCurrentPage()));

        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int currentPage = allJobViewModel.getCurrentPage();
                binding.loadMore.setVisibility(View.VISIBLE);
                if (currentPage < 20) {
                    binding.loadMore.setOnClickListener(view -> {
                        loadMore(String.valueOf(currentPage));
                        allJobViewModel.setCurrentPage();
                    });
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setUpRecyclerView() {
        binding.recyclerView.setAdapter(allJobAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void observeDataChanged(String page) {
        allJobViewModel.getJobByPage(page, Constants.APP_ID, Constants.APP_KEY).observe(this, job -> {
            if (job == null) {
                return;
            }
            allJobAdapter.setResults(job.getResults());

            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        });
    }

    private void loadMore(String page) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loadMore.setVisibility(View.GONE);
        allJobViewModel.getJobByPage(page, Constants.APP_ID, Constants.APP_KEY).observe(this, job -> {
            if (job == null) {
                return;
            }
            allJobAdapter.addMoreResults(job.getResults());
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private void initToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }
}