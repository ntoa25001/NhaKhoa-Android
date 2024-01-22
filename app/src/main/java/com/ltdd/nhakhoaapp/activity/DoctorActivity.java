package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.controller.adapter.DoctorAdapter;
import com.ltdd.nhakhoaapp.model.api.RetrofitClient;
import com.ltdd.nhakhoaapp.model.content.ContentDoctor;
import com.ltdd.nhakhoaapp.model.domain.Doctor;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorActivity extends AppCompatActivity {

    private SearchView searchView;

    private ImageView imageClose;
    private DoctorAdapter adapter;
    private List<Doctor> listDoctor = new ArrayList<>();
    private RecyclerView rcDoctor;
    private Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        rcDoctor = findViewById(R.id.rc_doctor);
        searchView = findViewById(R.id.search_view);
        toolbar = findViewById(R.id.tol_doctor);
        imageClose = findViewById(R.id.icon_close_view6);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setNavigationOnClickListener(v -> finish());
        adapter = new DoctorAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcDoctor.setLayoutManager(linearLayoutManager);

        loadData();

        //Click item in Card View get doctor[i]
        adapter.setOnItemClickListener(position -> {
            Doctor doctor= listDoctor.get(position);
            Long doctorId = doctor.getDoctorId();
            String doctorName = doctor.getName();
            String specialName =  doctor.getSpecialist().getSpecialistName();
            String day = doctor.getDay();

            Intent i = new Intent(DoctorActivity.this, AppointmentDoctorActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("doctorId", doctorId);
            bundle.putString("doctorName", doctorName);
            bundle.putString("specialName", specialName);
            bundle.putString("day", day);
            i.putExtra("dataId", bundle);
            startActivity(i);
        });


        //Search doctor by name
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                RetrofitClient.getRetrofit().searchDoctor(query).enqueue(new Callback<ContentDoctor>() {
                    @Override
                    public void onResponse(Call<ContentDoctor> call, Response<ContentDoctor> response) {
                        listDoctor.clear();
                        adapter.notifyDataSetChanged();
                        ContentDoctor contentDoctor = response.body();
                        listDoctor = contentDoctor.getContent();
                        adapter.setData(listDoctor);
                        rcDoctor.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<ContentDoctor> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RetrofitClient.getRetrofit().searchDoctor(newText).enqueue(new Callback<ContentDoctor>() {
                    @Override
                    public void onResponse(Call<ContentDoctor> call, Response<ContentDoctor> response) {
                        listDoctor.clear();
                        adapter.notifyDataSetChanged();
                        ContentDoctor contentDoctor = response.body();
                        listDoctor = contentDoctor.getContent();
                        adapter.setData(listDoctor);
                        rcDoctor.setAdapter(adapter);
                    }
                    @Override
                    public void onFailure(Call<ContentDoctor> call, Throwable t) {

                    }
                });
                return false;
            }
        });
    }

    //Call api get list doctor
    private void loadData() {
        RetrofitClient.getRetrofit().getAllDoctor().enqueue(new Callback<ContentDoctor>() {
            @Override
            public void onResponse(Call<ContentDoctor> call, Response<ContentDoctor> response) {
                if(response.isSuccessful()) {
                    ContentDoctor contentDoctor = response.body();
                    if (contentDoctor ==null) {
                        return;
                    }
                    List<Doctor> doctors = contentDoctor.getContent();
                    listDoctor.addAll(doctors);
                    adapter.setData(listDoctor);
                    rcDoctor.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ContentDoctor> call, Throwable t) {
                Toast.makeText(DoctorActivity.this, "Call error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}