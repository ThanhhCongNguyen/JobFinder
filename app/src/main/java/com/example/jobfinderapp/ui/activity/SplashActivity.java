package com.example.jobfinderapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.jobfinderapp.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}