package com.example.jobfinderapp.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jobfinderapp.R;
import com.example.jobfinderapp.databinding.ActivityMainBinding;
import com.example.jobfinderapp.repository.database.local.entity.Result;
import com.example.jobfinderapp.ui.adapter.MarkerJobAdapter;
import com.example.jobfinderapp.ui.adapter.RecommendedJobAdapter;
import com.example.jobfinderapp.ui.base.BaseActivity;
import com.example.jobfinderapp.utils.Constants;
import com.example.jobfinderapp.utils.Utility;
import com.example.jobfinderapp.viewmodel.JobViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private JobViewModel jobViewModel;
    private RecommendedJobAdapter recommendedJobAdapter;
    private MarkerJobAdapter markerJobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        jobViewModel.initRepository(getApplicationContext());

        binding.shimmerLayout.startShimmer();

        recommendedJobAdapter = new RecommendedJobAdapter(new RecommendedJobAdapter.Callback() {
            @Override
            public void onItemClick(Result result) {
                DetailActivity.starter(MainActivity.this, result);
            }

            @Override
            public void saveJob(Result result) {
                executor.execute(() -> jobViewModel.insert(result));
                Utility.toast(getApplicationContext(), "Save successfully");
            }
        });

        markerJobAdapter = new MarkerJobAdapter(new MarkerJobAdapter.Callback() {
            @Override
            public void onItemClick(Result result) {
                DetailActivity.starter(MainActivity.this, result);
            }

            @Override
            public void deleteItem(Result result, int position) {
                executor.execute(() -> jobViewModel.delete(result));
                Snackbar snackbar = Snackbar.make(binding.recyclerViewMarked, "Deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        executor.execute(() -> jobViewModel.insert(result));
                        markerJobAdapter.notifyItemInserted(position);
                    }
                });
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                snackbar.show();
            }
        });

        setUpMarkedJobRecyclerView();
        observeMarkedData();

        setUpRecommendedRecyclerView();
        observeRecommendedRecyclerView();

        binding.showAllRecommendedJob.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AllJobActivity.class));
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.searchAction);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        searchView.setQueryHint("What are you looking for?");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpMarkedJobRecyclerView() {
        binding.recyclerViewMarked.setAdapter(markerJobAdapter);
        binding.recyclerViewMarked.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MarkerJobAdapter.SwipeToDeleteCallback(markerJobAdapter));
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewMarked);
    }

    private void observeMarkedData() {
        jobViewModel.getLiveData().observe(this, results -> {
            if (results == null) {
                return;
            }
            if (results.size() > 0) {
                binding.tv5.setVisibility(View.VISIBLE);
                binding.recyclerViewMarked.setVisibility(View.VISIBLE);
                markerJobAdapter.setResults(results);
            } else {
                binding.tv5.setVisibility(View.GONE);
                binding.recyclerViewMarked.setVisibility(View.GONE);
            }
        });
    }

    private void setUpRecommendedRecyclerView() {
        binding.recyclerRecommended.setAdapter(recommendedJobAdapter);
        binding.recyclerRecommended.setLayoutManager(new LinearLayoutManager(this));
    }

    private void observeRecommendedRecyclerView() {
        jobViewModel.getJob(Constants.APP_ID, Constants.APP_KEY).observe(this, job -> {
            if (job == null) {
                return;
            }
            recommendedJobAdapter.setResults(job.getResults());

            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        });
    }

    private void search(String data) {
        jobViewModel.search(Constants.APP_ID, Constants.APP_KEY, Constants.RESULT_PER_PAGE, data, Constants.CONTENT_TYPE)
                .observe(MainActivity.this, job -> {
                    if (job == null) {

                    }
                    binding.recyclerRecommended.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);

                    recommendedJobAdapter.setResults(job.getResults());
                });
    }
}