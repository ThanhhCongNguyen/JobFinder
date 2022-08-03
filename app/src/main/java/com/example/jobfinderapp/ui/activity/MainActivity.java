package com.example.jobfinderapp.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.jobfinderapp.R;
import com.example.jobfinderapp.databinding.ActivityMainBinding;
import com.example.jobfinderapp.ui.base.BaseActivity;
import com.example.jobfinderapp.ui.fragment.HomeFragment;
import com.example.jobfinderapp.ui.fragment.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initMenu();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        loadFragment(new HomeFragment());
    }

    private void initMenu() {
        onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.saved:
                        fragment = new SavedFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        };
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}