package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.controller.adapter.ServiceListAdapter;
import com.ltdd.nhakhoaapp.model.api.RetrofitClient;
import com.ltdd.nhakhoaapp.model.content.ContentService;
import com.ltdd.nhakhoaapp.model.domain.Service;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ImageView imageClose;
    ServiceListAdapter serviceListAdapter;
    private List<Service> services;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);



        imageClose = findViewById(R.id.icon_close_view5);
        recyclerView =findViewById(R.id.rc_service_detail);
        //Close activity
        imageClose.setOnClickListener(v -> {
            finish();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        services = new ArrayList<>();
        serviceListAdapter = new ServiceListAdapter(ServiceActivity.this);



        RetrofitClient.getRetrofit().getAllService().enqueue(new Callback<ContentService>() {
            @Override
            public void onResponse(Call<ContentService> call, Response<ContentService> response) {
                if (response.isSuccessful()) {
                    ContentService content = response.body();
                    if (content == null) {
                        return;
                    }
                    List<Service> s = content.getContent();
                    services.addAll(s);
                    serviceListAdapter.setData(s);
                    recyclerView.setAdapter(serviceListAdapter);
                    serviceListAdapter.notifyDataSetChanged();
                    Toast.makeText(ServiceActivity.this, "thành công", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ContentService> call, Throwable t) {
                Toast.makeText(ServiceActivity.this, "false" , Toast.LENGTH_SHORT).show();
            }
        });


    }

}