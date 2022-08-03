package com.example.jobfinderapp.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jobfinderapp.utils.Executor;

import java.util.concurrent.ExecutorService;

public class BaseFragment extends Fragment {
    protected ExecutorService executor;

    public BaseFragment() {
        this.executor = Executor.getInstance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
