package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.api.RetrofitClient;
import com.ltdd.nhakhoaapp.model.domain.Appointment;
import com.ltdd.nhakhoaapp.model.domain.Doctor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InforAppointmentIdActivity extends AppCompatActivity {

    private EditText edtName, edtDate, edtTime, edtNote,edtBacsi;
    private Button btnBack ,btnUpdate;
    Long doctorId;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_appointment_id);

        edtName = findViewById(R.id.doctoredt);
        edtDate = findViewById(R.id.dateedt);
        edtTime = findViewById(R.id.timeedt);
        edtNote = findViewById(R.id.noteedt);
        btnUpdate = findViewById(R.id.btn_update);
     //   btnBack = findViewById(R.id.btn_back_2);

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });


        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("data");
        Long id = bundle.getLong("id");

        RetrofitClient.getRetrofit().getAppointmentId(id).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                Appointment appointment = response.body();
                doctorId = appointment.getDoctor();
                RetrofitClient.getRetrofit().getDoctorId(doctorId).enqueue(new Callback<Doctor>() {
                    @Override
                    public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                        Doctor doctor = response.body();
                        edtName.setText(doctor.getName());
                    }

                    @Override
                    public void onFailure(Call<Doctor> call, Throwable t) {

                    }
                });
                edtDate.setText(appointment.getAppointmentDate());
                edtTime.setText(appointment.getPeriod());
                edtNote.setText(appointment.getNote());
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ người dùng
                String appointmentDate = edtDate.getText().toString();
                String period = edtTime.getText().toString();
                String note = edtNote.getText().toString();


                // Tạo đối tượng Appointment mới với dữ liệu từ người dùng
                Appointment appointment = new Appointment(id, appointmentDate, period, note);

                RetrofitClient.getRetrofit().updateAppointmentById(appointment,id).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            // Xử lý khi cập nhật thành công
                            Toast.makeText(InforAppointmentIdActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                        } else {
                            // Xử lý khi cập nhật thất bại
                            Toast.makeText(InforAppointmentIdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(InforAppointmentIdActivity.this, "API call failed", Toast.LENGTH_SHORT).show();

                    }
                });
                finish();

            }
        });

    }
}