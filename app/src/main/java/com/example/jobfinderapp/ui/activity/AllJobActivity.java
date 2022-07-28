package com.example.jobfinderapp.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
                Utility.snackBar(getApplicationContext(), binding.getRoot(), "Inserted successfully");
            }
        });

        setUpRecyclerView();
        observeDataChanged(allJobViewModel.getCurrentPage());

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, allJobViewModel.getPages());
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.recyclerView.setVisibility(View.GONE);
                binding.shimmerLayout.setVisibility(View.VISIBLE);
                binding.shimmerLayout.startShimmer();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        observeDataChanged(String.valueOf(i));
                    }
                }, Constants.DELAY_MILLIS);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinner.setAdapter(adapter);
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

    private void initToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }
}