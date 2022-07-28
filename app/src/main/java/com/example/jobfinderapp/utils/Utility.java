package com.example.jobfinderapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.example.jobfinderapp.R;
import com.google.android.material.snackbar.Snackbar;

public class Utility {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void snackBar(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.blue));
        snackbar.show();

//        Snackbar snackbar = Snackbar.make(view, "Test", Snackbar.LENGTH_LONG);
//        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
//                snackbarLayout.getLayoutParams();
//        layoutParams.setMargins(32, 0, 32, 32);
//        snackbarLayout.setLayoutParams(layoutParams);
//        snackbar.show();
    }
}
