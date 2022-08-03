package com.example.jobfinderapp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jobfinderapp.databinding.ActivitySearchBinding;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.repository.local.entity.Search;
import com.example.jobfinderapp.ui.adapter.SearchAdapter;
import com.example.jobfinderapp.ui.adapter.SearchResultAdapter;
import com.example.jobfinderapp.ui.base.BaseActivity;
import com.example.jobfinderapp.utils.Constants;
import com.example.jobfinderapp.utils.Utility;
import com.example.jobfinderapp.viewmodel.SearchViewModel;

public class SearchActivity extends BaseActivity {
    private ActivitySearchBinding binding;
    private SearchViewModel searchViewModel;
    private SearchAdapter searchAdapter;
    private SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.initRepository(getApplicationContext());

        searchAdapter = new SearchAdapter(new SearchAdapter.Callback() {
            @Override
            public void onItemClick(Search search) {
                binding.progressBar.setVisibility(View.VISIBLE);
                search(search.getTitle());
                binding.searchEdittext.setText(search.getTitle());
            }

            @Override
            public void deleteSearch(Search search) {
                executor.execute(() -> searchViewModel.deleteSearch(search));
            }
        });

        searchResultAdapter = new SearchResultAdapter(new SearchResultAdapter.Callback() {
            @Override
            public void onItemClick(Result result) {
                DetailActivity.starter(SearchActivity.this, result);
            }

            @Override
            public void saveJob(Result result) {
                executor.execute(() -> searchViewModel.insertJob(result));
                Utility.snackBar(getApplicationContext(), binding.getRoot(), "Inserted Successfully");
            }
        });

        setUpSearchRecyclerView();
        observeSearchData();

        setUpDataRecyclerView();

        binding.searchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.searchEdittext.getWindowToken(), 0);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String data = binding.searchEdittext.getText().toString().trim();
                            executor.execute(() -> searchViewModel.insertSearch(new Search(data)));
                            search(data);
                        }
                    }, 1000);
                    return true;
                }
                return false;
            }
        });

        binding.backIcon.setOnClickListener(view -> {
            finish();
        });
    }

    private void setUpSearchRecyclerView() {
        binding.recyclerView.setAdapter(searchAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpDataRecyclerView() {
        binding.recyclerViewData.setAdapter(searchResultAdapter);
        binding.recyclerViewData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void observeSearchData() {
        searchViewModel.getSearchLiveData().observe(this, searches -> {
            if (searches == null) {
                return;
            }
            searchAdapter.setSearches(searches);
        });
    }

    private void search(String data) {
        binding.recyclerView.setVisibility(View.GONE);
        binding.tv1.setVisibility(View.GONE);
        binding.recyclerViewData.setVisibility(View.VISIBLE);

        searchViewModel.search(Constants.APP_ID, Constants.APP_KEY, Constants.RESULT_PER_PAGE, data, Constants.CONTENT_TYPE)
                .observe(this, job -> {
                    if (job == null) {
                        return;
                    }
                    searchResultAdapter.setResults(job.getResults());
                });

        binding.progressBar.setVisibility(View.GONE);
    }
}

