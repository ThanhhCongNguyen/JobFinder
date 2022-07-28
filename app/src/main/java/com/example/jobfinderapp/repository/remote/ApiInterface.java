package com.example.jobfinderapp.repository.remote;

import com.example.jobfinderapp.repository.local.entity.Job;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("v1/api/jobs/gb/search/1?")
    Call<Job> getJobList(@Query("app_id") String appId,
                         @Query("app_key") String appKey);

    @GET("v1/api/jobs/gb/search/{page}?")
    Call<Job> getJobByPage(@Path("page") String page,
                           @Query("app_id") String appId,
                           @Query("app_key") String appKey);

    @GET("v1/api/jobs/gb/search/1?")
    Call<Job> search(@Query("app_id") String appId,
                     @Query("app_key") String appKey,
                     @Query("results_per_page") String resultPerPage,
                     @Query("what") String what,
                     @Query("content-type") String contentType);
}
