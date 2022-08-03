package com.example.jobfinderapp.repository.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search")
public class Search {
    @NonNull
    @PrimaryKey
    private String title;

    public Search(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
