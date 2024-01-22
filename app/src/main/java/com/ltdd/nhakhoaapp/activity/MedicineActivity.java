package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.ltdd.nhakhoaapp.R;

public class MedicineActivity extends AppCompatActivity {

    ImageView imageClose;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        imageClose = findViewById(R.id.icon_close_view10);

        //Close activity
        imageClose.setOnClickListener(v -> {
            finish();
        });
    }
}