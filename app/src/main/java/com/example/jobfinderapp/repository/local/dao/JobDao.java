package com.example.jobfinderapp.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.repository.local.entity.Search;

import java.util.List;

@Dao
public interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Result result);

    @Delete()
    void delete(Result result);

    @Query("SELECT * FROM result LIMIT 10")
    LiveData<List<Result>> getLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSearch(Search search);

    @Delete()
    void deleteSearch(Search search);

    @Query("SELECT * FROM search LIMIT 10")
    LiveData<List<Search>> getSearchLiveData();
}
