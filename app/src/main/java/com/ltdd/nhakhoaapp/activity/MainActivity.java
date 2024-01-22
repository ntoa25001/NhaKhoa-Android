package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.controller.adapter.ImageAdapter;

import com.google.android.material.navigation.NavigationView;
import com.ltdd.nhakhoaapp.model.domain.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private Button btnBook, btnDay, btnDoctor, btnBackDialog ,btnServiceDetal,btnPrescription;
    private AlertDialog dialog;
    private List<Image> images;
    private ViewPager viewPager;
    private ImageAdapter adapter;
    private ImageView imgAction;
    private Timer timer;
    private CircleIndicator circleIndicator;

    private Long patientId;

    private String username,password, name;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Button btnInfor, btnPhieu;

    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBook = findViewById(R.id.btn_datlich);
        toolbar = findViewById(R.id.tl_bar);
        navigationView = findViewById(R.id.navi_view);
        drawerLayout = findViewById(R.id.drawA);
        btnInfor = findViewById(R.id.btn_hs);
        btnPhieu = findViewById(R.id.btn_phieu);
        imgAction = findViewById(R.id.action_view_bar);
        btnServiceDetal = findViewById(R.id.btn_service_detail);
        btnPrescription = findViewById(R.id.btn_prescription);


        circleIndicator= findViewById(R.id.circle_photo);
        viewPager = findViewById(R.id.view_photo);
        images = getListImage();
        adapter = new ImageAdapter(this,images);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);

        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        imgAction.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        View header = navigationView.getHeaderView(0);
        textView = header.findViewById(R.id.txt_name_tk);


        //Custom navigation on click
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mn_dk:
                    break;
                case R.id.mn_cs:
                    break;
                case R.id.mn_tg:
                    break;
                case R.id.mn_lh:
                    break;
                case R.id.mn_gt:
                    break;
                case R.id.mn_logout:
                    SharedPreferences preferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            return false;
        });

        //Information patient
        btnInfor.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, InforPatientActivity.class);
            startActivity(i);
        });

        //Information appointment
        btnPhieu.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, InforAppointmentActivity.class);
            startActivity(i);
        });

        btnServiceDetal.setOnClickListener(v ->{
            Intent i = new Intent(MainActivity.this, ServiceActivity.class);
            startActivity(i);
        });

        btnPrescription.setOnClickListener(v ->{
            Intent i = new Intent(MainActivity.this, PrescriptionActivity.class);
            startActivity(i);
        });


        //Login
        authenLogin();

        //Custom dialog button
        showDialog();

        //Custom slide bar
        slideBar();

    }

    private void authenLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        username = sharedPreferences.getString("email", "");
        password = sharedPreferences.getString("password", "");
        patientId = sharedPreferences.getLong("patientId", -1);
        name = sharedPreferences.getString("name","");
        textView.setText(name);

        if (!username.isEmpty() && !password.isEmpty()) {
            Toast.makeText(this, "Welcome Back ", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    //Slide bar
    private void slideBar() {
        images = getListImage();
        if(timer ==null) {
            timer =new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int total = images.size()-1;
                        if(currentItem<total) {
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });

            }
        },200, 3000);
    }

    //Create dialog
    @SuppressLint("MissingInflatedId")
    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lựa chọn");
        View view = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        btnDay = view.findViewById(R.id.btn_ngay);
        btnDoctor = view.findViewById(R.id.btn_bacsi);
        btnBackDialog = view.findViewById(R.id.btn_back_dialog);
        btnBackDialog.setOnClickListener(v -> dialog.dismiss());
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.MydialogAnimation;
        btnBook.setOnClickListener(v -> {
            dialog.show();
        });
        btnDay.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AppointmentActivity.class);
            startActivity(i);
        });
        btnDoctor.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, DoctorActivity.class);
            startActivity(i);
        });

    }
    //Set data image
    private List<Image> getListImage() {
        List<Image> images = new ArrayList<>();
        images.add(new Image(R.drawable.nhakhoa1));
        images.add(new Image(R.drawable.nhakhoa2));
        images.add(new Image(R.drawable.nhakhoa3));
        return images;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}