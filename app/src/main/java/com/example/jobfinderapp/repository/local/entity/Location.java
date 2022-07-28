package com.example.jobfinderapp.repository.local.entity;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Location implements Serializable {
    @SerializedName("area")
    private ArrayList<String> area;
    @SerializedName("__CLASS__")
    private String __CLASS__;
    @SerializedName("display_name")
    private String displayName;

    public Location(ArrayList<String> area, String __CLASS__, String display_name) {
        this.area = area;
        this.__CLASS__ = __CLASS__;
        this.displayName = display_name;
    }

    public ArrayList<String> getArea() {
        return area;
    }

    public void setArea(ArrayList<String> area) {
        this.area = area;
    }

    public String get__CLASS__() {
        return __CLASS__;
    }

    public void set__CLASS__(String __CLASS__) {
        this.__CLASS__ = __CLASS__;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
