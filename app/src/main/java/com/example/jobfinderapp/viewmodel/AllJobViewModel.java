package com.example.jobfinderapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.jobfinderapp.repository.JobRepository;
import com.example.jobfinderapp.repository.local.entity.Job;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllJobViewModel extends BaseViewModel {
    private JobRepository repository;
    private String currentPage = "1";
    private Boolean isLastPage = false;
    private Boolean isLoading = false;
    private List<Result> results;
    private String[] pages = {"1", "2", "3", "4", "5", "6"};

    public AllJobViewModel() {
    }

    public void initRepository(Context context) {
        repository = new JobRepository(context);
        results = new ArrayList<>();
    }

    public LiveData<Job> getJobByPage(String page, String appId, String appKey) {
        return repository.getJobByPage(page, appId, appKey);
    }

    public void insert(Result result) {
        repository.insertJob(result);
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String[] getPages() {
        return pages;
    }
}
