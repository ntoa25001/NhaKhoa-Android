package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.ltdd.nhakhoaapp.R;

public class OpenActivity extends AppCompatActivity {

    private Handler handler;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        progressBar =findViewById(R.id.loading);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                Intent i = new Intent(OpenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        },1000);
    }
}