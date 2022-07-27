package com.example.jobfinderapp.repository.database.local.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {
    @SerializedName("__CLASS__")
    private String __CLASS__;
    @SerializedName("label")
    private String label;
    @SerializedName("tag")
    private String tag;
}