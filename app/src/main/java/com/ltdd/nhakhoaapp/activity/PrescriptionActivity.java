package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.controller.adapter.PrescriptionAdapter;
import com.ltdd.nhakhoaapp.model.api.RetrofitClient;
import com.ltdd.nhakhoaapp.model.domain.Prescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageClose;
    PrescriptionAdapter prescriptionAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precription);
        recyclerView = findViewById(R.id.rc_prescription);

        imageClose = findViewById(R.id.icon_close_view4);

        //Close activity
        imageClose.setOnClickListener(v -> {
            finish();
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Long patientId = sharedPreferences.getLong("patientId", -1);

        RetrofitClient.getRetrofit().getPrescriptionByPatient(patientId).enqueue(new Callback<List<Prescription>>() {
            @Override
            public void onResponse(Call<List<Prescription>> call, Response<List<Prescription>> response) {
                List<Prescription> prescriptionList = response.body();
                getAllPrescriptionByPatientId(prescriptionList);
                Toast.makeText(PrescriptionActivity.this, "thành công", Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onFailure(Call<List<Prescription>> call, Throwable t) {
                Toast.makeText(PrescriptionActivity.this, "lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAllPrescriptionByPatientId(List<Prescription> prescriptionList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        prescriptionAdapter = new PrescriptionAdapter( prescriptionList, this);
        recyclerView.setAdapter(prescriptionAdapter);
        prescriptionAdapter.notifyDataSetChanged();
    }
}