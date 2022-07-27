package com.example.jobfinderapp.ui.base;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jobfinderapp.utils.Executor;

import java.util.concurrent.ExecutorService;

public class BaseActivity extends AppCompatActivity {
    protected ExecutorService executor;

    public BaseActivity() {
        this.executor = Executor.getInstance();
    }
}
