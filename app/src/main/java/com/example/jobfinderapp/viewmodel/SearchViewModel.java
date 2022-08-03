package com.example.jobfinderapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.jobfinderapp.repository.JobRepository;
import com.example.jobfinderapp.repository.local.entity.Job;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.repository.local.entity.Search;
import com.example.jobfinderapp.ui.base.BaseViewModel;

import java.util.List;

public class SearchViewModel extends BaseViewModel {
    private JobRepository repository;

    public SearchViewModel() {
    }

    public void initRepository(Context context) {
        repository = new JobRepository(context);
    }

    public LiveData<List<Search>> getSearchLiveData() {
        return repository.getSearchLiveData();
    }

    public void insertSearch(Search search) {
        repository.insertSearch(search);
    }

    public void insertJob(Result result) {
        repository.insertJob(result);
    }

    public void deleteSearch(Search search) {
        repository.deleteSearch(search);
    }

    public LiveData<Job> search(String appId, String appKey, String resultPerPage, String what, String contentType) {
        return repository.search(appId, appKey, resultPerPage, what, contentType);
    }
}
