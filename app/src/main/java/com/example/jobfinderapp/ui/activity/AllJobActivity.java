package com.example.jobfinderapp.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.jobfinderapp.R;
import com.example.jobfinderapp.databinding.ActivityAllJobBinding;
import com.example.jobfinderapp.repository.database.local.entity.Result;
import com.example.jobfinderapp.ui.adapter.AllJobAdapter;
import com.example.jobfinderapp.ui.base.BaseActivity;
import com.example.jobfinderapp.utils.Constants;
import com.example.jobfinderapp.utils.PaginationScrollListener;
import com.example.jobfinderapp.utils.Utility;
import com.example.jobfinderapp.viewmodel.AllJobViewModel;

public class AllJobActivity extends BaseActivity {
    private ActivityAllJobBinding binding;
    private AllJobAdapter allJobAdapter;
    private AllJobViewModel allJobViewModel;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        allJobViewModel = new ViewModelProvider(this).get(AllJobViewModel.class);
        allJobViewModel.initRepository(getApplicationContext());

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        observeDataChanged();
                    }
                }, Constants.DELAY_MILLIS);
            }
        });

        binding.shimmerLayout.startShimmer();

        allJobAdapter = new AllJobAdapter(new AllJobAdapter.Callback() {
            @Override
            public void onItemClick(Result result) {
                DetailActivity.starter(AllJobActivity.this, result);
            }

            @Override
            public void saveJob(Result result) {
                executor.execute(() -> allJobViewModel.insert(result));
                Utility.toast(getApplicationContext(), "Insert successfully");
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);

        setUpRecyclerView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                observeDataChanged();
            }
        }, Constants.DELAY_MILLIS);

        binding.recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                allJobViewModel.setLoading(true);
                allJobViewModel.setCurrentPage();
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return allJobViewModel.getLastPage();
            }

            @Override
            public boolean isLoading() {
                return allJobViewModel.getLoading();
            }
        });
    }

    private void setUpRecyclerView() {
        binding.recyclerView.setAdapter(allJobAdapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void observeDataChanged() {
        allJobViewModel.getJobByPage(String.valueOf(allJobViewModel.getCurrentPage()), Constants.APP_ID, Constants.APP_KEY).observe(this, job -> {
            if (job == null) {
                return;
            }
            allJobAdapter.setResults(job.getResults());

            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        });
    }

    private void loadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                allJobAdapter.removeLoadingFooter();
                allJobViewModel.setLoading(false);

                allJobViewModel.getJobByPage(String.valueOf(allJobViewModel.getCurrentPage()), Constants.APP_ID, Constants.APP_KEY).observe(AllJobActivity.this, job -> {
                    if (job == null) {
                        return;
                    }
                    allJobAdapter.setResults(job.getResults());
                });
                allJobAdapter.addLoadingFooter();
            }
        }, Constants.DELAY_MILLIS);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}