package com.example.jobfinderapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.jobfinderapp.repository.JobRepository;
import com.example.jobfinderapp.repository.database.local.entity.Job;
import com.example.jobfinderapp.repository.database.local.entity.Result;
import com.example.jobfinderapp.ui.base.BaseViewModel;

import java.util.List;

public class JobViewModel extends BaseViewModel {
    private JobRepository repository;

    public JobViewModel() {
    }

    public void initRepository(Context context) {
        repository = new JobRepository(context);
    }

    public LiveData<Job> getJob(String appId, String appKey) {
        return repository.getJob(appId, appKey);
    }

    public LiveData<Job> search(String appId, String appKey, String resultPerPage, String what, String contentType) {
        return repository.search(appId, appKey, resultPerPage, what, contentType);
    }

    public LiveData<List<Result>> getLiveData() {
        return repository.getLiveData();
    }

    public void insert(Result result) {
        repository.insertJob(result);
    }

    public void delete(Result result) {
        repository.deleteJob(result);
    }
}
