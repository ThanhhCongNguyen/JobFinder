package com.example.jobfinderapp.utils;

import androidx.room.TypeConverter;

import com.example.jobfinderapp.repository.local.entity.Category;
import com.example.jobfinderapp.repository.local.entity.Company;
import com.example.jobfinderapp.repository.local.entity.Location;
import com.google.gson.Gson;

import java.util.Date;

public class Converter {
    public static Gson gson = new Gson();

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String locationConverter(Location location) {
        return location == null ? null : gson.toJson(location);
    }

    @TypeConverter
    public static Location gsonToLocation(String value) {
        return value == null ? null : gson.fromJson(value, Location.class);
    }

    @TypeConverter
    public static String categoryConverter(Category category) {
        return category == null ? null : gson.toJson(category);
    }

    @TypeConverter
    public static Category gsonToCategory(String value) {
        return value == null ? null : gson.fromJson(value, Category.class);
    }

    @TypeConverter
    public static String companyConverter(Company company) {
        return company == null ? null : gson.toJson(company);
    }

    @TypeConverter
    public static Company gsonToCompany(String value) {
        return value == null ? null : gson.fromJson(value, Company.class);
    }
}
