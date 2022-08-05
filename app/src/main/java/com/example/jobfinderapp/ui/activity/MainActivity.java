package com.example.jobfinderapp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.jobfinderapp.R;
import com.example.jobfinderapp.databinding.ActivityMainBinding;
import com.example.jobfinderapp.ui.base.BaseActivity;
import com.example.jobfinderapp.ui.fragment.HomeFragment;
import com.example.jobfinderapp.ui.fragment.SavedFragment;
import com.example.jobfinderapp.utils.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.internal.Util;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;
    private long backPressedTime;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initMenu();
        loadFragment(new HomeFragment());
        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        if (binding.bottomNavigation.getSelectedItemId() == R.id.home) {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                Utility.snackBar(this, binding.getRoot(), getResources().getString(R.string.press_back));
            }
            backPressedTime = System.currentTimeMillis();
        } else {
            binding.bottomNavigation.setSelectedItemId(R.id.home);
            loadFragment(new HomeFragment());
        }
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
     //   transaction.addToBackStack(null);
        transaction.commit();
    }
}