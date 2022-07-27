package com.example.jobfinderapp.repository.database.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.jobfinderapp.repository.database.local.dao.JobDao;
import com.example.jobfinderapp.repository.database.local.entity.Result;
import com.example.jobfinderapp.utils.Converter;

@Database(entities = Result.class, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class JobDatabase extends RoomDatabase {
    private static JobDatabase instance;

    public abstract JobDao jobDao();

    public static JobDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (JobDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            JobDatabase.class,
                            "job_database").build();
                }
            }
        }
        return instance;
    }
}
