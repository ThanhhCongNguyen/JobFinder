package com.example.jobfinderapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.jobfinderapp.repository.JobRepository;
import com.example.jobfinderapp.repository.database.local.entity.Job;
import com.example.jobfinderapp.repository.database.local.entity.Result;
import com.example.jobfinderapp.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllJobViewModel extends BaseViewModel {
    private JobRepository repository;
    private int currentPage = 1;
    private Boolean isLastPage = false;
    private Boolean isLoading = false;
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

    public void setCurrentPage() {
        currentPage++;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public Boolean getLoading() {
        return isLoading;
    }

    public void setLoading(Boolean loading) {
        isLoading = loading;
    }

    public Boolean getLastPage() {
        return isLastPage;
    }

    public void setLastPage(Boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
