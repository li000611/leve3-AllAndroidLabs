package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}




