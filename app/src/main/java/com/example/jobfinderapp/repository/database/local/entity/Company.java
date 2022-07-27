package com.example.jobfinderapp.repository.database.local.entity;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity
public class Company implements Serializable {
    @SerializedName("__CLASS__")
    private String __CLASS__;
    @SerializedName("display_name")
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }
}
