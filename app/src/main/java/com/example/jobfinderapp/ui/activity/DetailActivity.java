package com.example.jobfinderapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.jobfinderapp.R;
import com.example.jobfinderapp.databinding.ActivityDetailBinding;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.ui.base.BaseActivity;
import com.example.jobfinderapp.utils.Constants;
import com.example.jobfinderapp.viewmodel.DetailViewModel;

import java.text.SimpleDateFormat;

public class DetailActivity extends BaseActivity {
    private ActivityDetailBinding binding;
    private DetailViewModel detailViewModel;

    public static void starter(Context context, Result result) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constants.RESULT_KEY, result);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolBar();

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        Intent intent = getIntent();
        if (intent != null) {
            Result result = (Result) intent.getSerializableExtra(Constants.RESULT_KEY);
            if (result != null) {
                detailViewModel.setResult(result);

                binding.toolbar.setTitle(result.getTitle());
                binding.title.setText(result.getTitle());
                binding.companyName.setText(result.getCompany().getDisplayName());
                binding.location.setText(result.getLocation().getDisplayName());
                binding.salary.setText((int) result.getSalaryMin() + " / " + (int) result.getSalaryMax());
                String created = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(result.getCreated());
                binding.createdText.setText(created);
                binding.isFullTime.setText(result.getContractTime());
                binding.description.setText(result.getDescription());
            }
        }

        binding.applyButton.setOnClickListener(view -> {
            Uri uri = Uri.parse(detailViewModel.getResult().getRedirect_url());
            Intent intentView = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentView);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }
}