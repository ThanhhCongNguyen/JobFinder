package com.example.jobfinderapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.jobfinderapp.repository.JobRepository;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.ui.base.BaseViewModel;

import java.util.List;

public class AllMarkedJobViewModel extends BaseViewModel {
    private JobRepository repository;

    public AllMarkedJobViewModel() {
    }

    public void initRepository(Context context) {
        repository = new JobRepository(context);
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
