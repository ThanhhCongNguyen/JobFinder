package com.example.jobfinderapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jobfinderapp.repository.local.dao.JobDao;
import com.example.jobfinderapp.repository.local.db.JobDatabase;
import com.example.jobfinderapp.repository.local.entity.Job;
import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.repository.local.entity.Search;
import com.example.jobfinderapp.repository.remote.ApiInterface;
import com.example.jobfinderapp.repository.remote.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobRepository {
    private final ApiInterface apiInterface;
    private JobDatabase jobDatabase;
    private JobDao jobDao;

    public JobRepository(Context context) {
        jobDatabase = JobDatabase.getDatabase(context);
        apiInterface = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        jobDao = jobDatabase.jobDao();
    }

    public LiveData<List<Result>> getLiveData() {
        return jobDao.getLiveData();
    }

    public void insertJob(Result result) {
        jobDao.insert(result);
    }

    public void deleteJob(Result result) {
        jobDao.delete(result);
    }

    public LiveData<List<Search>> getSearchLiveData() {
        return jobDao.getSearchLiveData();
    }

    public void insertSearch(Search search) {
        jobDao.insertSearch(search);
    }

    public void deleteSearch(Search search) {
        jobDao.deleteSearch(search);
    }

    public LiveData<Job> getJob(String appId, String appKey) {
        final MutableLiveData<Job> data = new MutableLiveData<>();
        apiInterface.getJobList(appId, appKey)
                .enqueue(new Callback<Job>() {
                    @Override
                    public void onResponse(Call<Job> call, Response<Job> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Job> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<Job> getJobByPage(String page, String appId, String appKey) {
        final MutableLiveData<Job> data = new MutableLiveData<>();
        apiInterface.getJobByPage(page, appId, appKey)
                .enqueue(new Callback<Job>() {
                    @Override
                    public void onResponse(Call<Job> call, Response<Job> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Job> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<Job> search(String appId, String appKey, String resultPerPage, String what, String contentType) {
        final MutableLiveData<Job> data = new MutableLiveData<>();
        apiInterface.search(appId, appKey, resultPerPage, what, contentType)
                .enqueue(new Callback<Job>() {
                    @Override
                    public void onResponse(Call<Job> call, Response<Job> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Job> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
