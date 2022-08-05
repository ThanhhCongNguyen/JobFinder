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
    private int currentPage = 1;
    private List<Result> results;

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage() {
        currentPage++;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void addMoreResults(List<Result> results) {
        this.results.addAll(results);
    }

}
