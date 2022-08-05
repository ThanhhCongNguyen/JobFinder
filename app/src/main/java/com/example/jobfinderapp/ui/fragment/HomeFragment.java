package com.example.jobfinderapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jobfinderapp.R;
import com.example.jobfinderapp.databinding.FragmentHomeBinding;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.ui.activity.AllJobActivity;
import com.example.jobfinderapp.ui.activity.DetailActivity;
import com.example.jobfinderapp.ui.activity.SearchActivity;
import com.example.jobfinderapp.ui.adapter.RecommendedJobAdapter;
import com.example.jobfinderapp.ui.base.BaseFragment;
import com.example.jobfinderapp.utils.Constants;
import com.example.jobfinderapp.utils.Utility;
import com.example.jobfinderapp.viewmodel.JobViewModel;
import com.google.android.material.appbar.AppBarLayout;

public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding binding;
    private JobViewModel jobViewModel;
    private RecommendedJobAdapter recommendedJobAdapter;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        jobViewModel.initRepository(getContext());

        recommendedJobAdapter = new RecommendedJobAdapter(new RecommendedJobAdapter.Callback() {
            @Override
            public void onItemClick(Result result) {
                DetailActivity.starter(getContext(), result);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View viewMain, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(viewMain, savedInstanceState);
        initToolbar();

        binding.shimmerLayout.startShimmer();

        setUpRecommendedRecyclerView();
        observeRecommendedRecyclerView();

        binding.showAllRecommendedJob.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AllJobActivity.class));
        });

        binding.linearSearch.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.searchAction) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpRecommendedRecyclerView() {
        binding.recyclerRecommended.setAdapter(recommendedJobAdapter);
        binding.recyclerRecommended.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observeRecommendedRecyclerView() {
        jobViewModel.getJob(Constants.APP_ID, Constants.APP_KEY).observe(getViewLifecycleOwner(), job -> {
            if (job == null) {
                return;
            }
            recommendedJobAdapter.setResults(job.getResults());

            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        });
    }


    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        binding.collapsingToolbar.setTitle(getString(R.string.jobFinder));
        binding.toolbar.setVisibility(View.VISIBLE);

        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if ((binding.collapsingToolbar.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(binding.collapsingToolbar))) {
                    binding.toolbar.setVisibility(View.VISIBLE);
                } else {
                    binding.toolbar.setVisibility(View.GONE);
                }
            }
        });
    }
}