package com.example.jobfinderapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.example.jobfinderapp.R;
import com.google.android.material.snackbar.Snackbar;

public class Utility {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void snackBar(Context context, View view, String message) {
        Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) snackBar.getView().getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        snackBar.getView().setBackgroundColor(context.getResources().getColor(R.color.black));
        snackBar.getView().setLayoutParams(layoutParams);
        snackBar.show();
    }
}
