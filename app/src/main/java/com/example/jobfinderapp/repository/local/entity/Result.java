package com.example.jobfinderapp.repository.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "result")
public class Result implements Serializable {
    @SerializedName("adref")
    private String adref;
    @SerializedName("contract_time")
    private String contractTime;
    @SerializedName("redirect_url")
    private String redirect_url;
    @SerializedName("location")
    private Location location;
    @SerializedName("description")
    private String description;
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private String id;
    @SerializedName("created")
    private Date created;
    @SerializedName("contract_type")
    private String contractType;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("company")
    private Company company;
    @SerializedName("salary_is_predicted")
    private String salaryIsPredicted;
    @SerializedName("title")
    private String title;
    @SerializedName("salary_min")
    private double salaryMin;
    @SerializedName("salary_max")
    private double salaryMax;
    @SerializedName("category")
    private Category category;
    @SerializedName("__CLASS__")
    private String __CLASS__;
    @SerializedName("longitude")
    private double longitude;


    public String getAdref() {
        return adref;
    }

    public void setAdref(String adref) {
        this.adref = adref;
    }

    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getSalaryIsPredicted() {
        return salaryIsPredicted;
    }

    public void setSalaryIsPredicted(String salaryIsPredicted) {
        this.salaryIsPredicted = salaryIsPredicted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(double salaryMax) {
        this.salaryMax = salaryMax;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String get__CLASS__() {
        return __CLASS__;
    }

    public void set__CLASS__(String __CLASS__) {
        this.__CLASS__ = __CLASS__;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
