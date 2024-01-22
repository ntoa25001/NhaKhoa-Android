package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.controller.adapter.ServiceAdapter;
import com.ltdd.nhakhoaapp.controller.adapter.TimeAdapter;
import com.ltdd.nhakhoaapp.model.api.RetrofitClient;
import com.ltdd.nhakhoaapp.model.content.ContentService;
import com.ltdd.nhakhoaapp.model.domain.Appointment;
import com.ltdd.nhakhoaapp.model.domain.Doctor;
import com.ltdd.nhakhoaapp.model.domain.Service;
import com.ltdd.nhakhoaapp.model.domain.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDoctorActivity extends AppCompatActivity {

    private RecyclerView rcDichvu, rcTime;
    private TimeAdapter timeAdapter;
    private List<Service> services;
    private List<Time> timeList;
    private Button btnService, backTime;
    private AlertDialog dialogD, dialogA;

    private ServiceAdapter serviceAdapter;
    private TextInputEditText txtBacsi, txtDichvu;
    private TextInputEditText txtChuyenKhoa, txtNgayKham, txtGioKham, txtNote,edtName ;

    private Time time;
    List<Service> selectedServices;
    private Button btnSave;
    private Long doctorId;
    private Long patientId;
    private Long id;

    private ImageView imgClose;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_doctor);

        timeAdapter = new TimeAdapter(this);
        serviceAdapter = new ServiceAdapter(this);

        edtName =  findViewById(R.id.txtInName_text);
        btnSave = findViewById(R.id.btn_save);
        txtBacsi = findViewById(R.id.txt_bs);
        txtChuyenKhoa = findViewById(R.id.txt_chuyenkhoa);
        txtNgayKham = findViewById(R.id.txt_ngaykham);
        txtNote = findViewById(R.id.txt_note);
        txtGioKham = findViewById(R.id.txt_giokham);
        imgClose = findViewById(R.id.icon_close_dt);
        txtDichvu = findViewById(R.id.txt_dichvu);


        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        id = sharedPreferences.getLong("patientId", -1);
        String name = sharedPreferences.getString("name","");

        edtName.setText(name);
        txtNgayKham.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AppointmentDoctorActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        txtNgayKham.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);

                    },
                    year, month, day);
            datePickerDialog.show();
        });
        txtBacsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppointmentDoctorActivity.this, DoctorActivity.class);
                startActivity(i);
            }
        });

        timeList = getListTime();
        showTime();

        services = new ArrayList<>();
        showService();

        readData();
        btnSave.setOnClickListener(v -> {
            saveAppointment();

        });

        //Close activity
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppointmentDoctorActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void saveAppointment() {
        String ngayKham = txtNgayKham.getText().toString().trim();
        String day = txtGioKham.getText().toString().trim();
        String note = txtNote.getText().toString();
        Appointment appointment = new Appointment(ngayKham, day, note, doctorId, id, selectedServices);

        if(TextUtils.isEmpty(ngayKham)) {
            txtNgayKham.setError("Input text");

        }

        if(TextUtils.isEmpty(day)) {
            txtGioKham.setError("Input text");

        }

        if(TextUtils.isEmpty(note)) {
            txtNote.setError("Input text");

        }
        apiCreateAppointment(appointment);
    }


    @SuppressLint("MissingInflatedId")
    private void showService() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lựa chọn ");

        View view = getLayoutInflater().inflate(R.layout.layout_service, null);
        rcDichvu = view.findViewById(R.id.rc_service);

        RetrofitClient.getRetrofit().getAllService().enqueue(new Callback<ContentService>() {
            @Override
            public void onResponse(Call<ContentService> call, Response<ContentService> response) {
                if(response.isSuccessful()) {
                    ContentService content = response.body();
                    if (content ==null) {
                        return;
                    }
                    List<Service> s = content.getContent();
                    services.addAll(s);
                    serviceAdapter.setData(services);
                    rcDichvu.setAdapter(serviceAdapter);
                }
            }
            @Override
            public void onFailure(Call<ContentService> call, Throwable t) {

                Toast.makeText(AppointmentDoctorActivity.this, "Call failaass", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcDichvu.setLayoutManager(linearLayoutManager);
        btnService = view.findViewById(R.id.btn_back_service);

        btnService.setOnClickListener(v -> dialogD.dismiss());
        builder.setView(view);
        dialogD = builder.create();
        dialogD.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog);
        dialogD.getWindow().getAttributes().windowAnimations = R.style.MydialogAnimation;
        txtDichvu.setOnClickListener(v -> {
            dialogD.show();
        });
//        serviceAdapter.setOnItemClickListener(position -> {
//            Service service = services.get(position);
//            serviceId = service.get;
//            edtChuyenKhoa.setText(specialist.getSpecialistName());
//            dialogD.dismiss();
//        });
        serviceAdapter.setOnCheckedChangeListener((position, isChecked) -> {
            Service service = services.get(position);
            service.setSelected(isChecked);
            if (isChecked) {
                // Xử lý khi CheckBox được chọn
                Toast.makeText(AppointmentDoctorActivity.this, "Bạn đã chọn dịch vụ: " + service.getName(), Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý khi CheckBox bị bỏ chọn
                Toast.makeText(AppointmentDoctorActivity.this, "Bạn đã bỏ chọn dịch vụ: " + service.getName(), Toast.LENGTH_SHORT).show();
            }

            selectedServices = new ArrayList<>();
            for (Service s : services) {
                if (s.getSelected()) {
                    selectedServices.add(s);
                }
            }
            txtDichvu.setText(service.getName());
        });

    }

    @SuppressLint("MissingInflatedId")
    private void showTime() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lựa chọn ");

        View view = getLayoutInflater().inflate(R.layout.layout_time, null);
        rcTime = view.findViewById(R.id.rc_time);

        timeAdapter.setData(getListTime());
        rcTime.setAdapter(timeAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcTime.setLayoutManager(gridLayoutManager);
        backTime = view.findViewById(R.id.btn_back_time);

        backTime.setOnClickListener(v -> dialogA.dismiss());
        builder.setView(view);
        dialogA = builder.create();
        dialogA.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog);
        dialogA.getWindow().getAttributes().windowAnimations = R.style.MydialogAnimation;
        txtGioKham.setOnClickListener(v -> {
            dialogA.show();
        });

        timeAdapter.setOnItemClickListener(position -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            boolean isTimeSelected = sharedPreferences.getBoolean("isTimeSelected", false);

            Time time = timeList.get(position);

            if (!isTimeSelected) {
                txtGioKham.setText(time.getPeriod());

                dialogA.dismiss();
                // Đánh dấu thời gian đã được chọn
                time.setTimeSelected(true);

                // Cập nhật trạng thái chọn của tất cả các thời gian khác
                for (int i = 0; i < timeList.size(); i++) {
                    if (i != position) {
                        timeList.get(i).setTimeSelected(false);
                    }
                }

                // Thông báo cho adapter biết để cập nhật giao diện
                timeAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Chỉ có thể chọn một thời gian duy nhất 2", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Chỉ có thể chọn một thời gian duy nhất", Toast.LENGTH_SHORT).show();
            }
        });

//        timeAdapter.setOnItemClickListener(position -> {
//            Time time = timeList.get(position);
//            txtGioKham.setText(time.getPeriod());
//            dialogA.dismiss();
//
//        });
    }
    private List<Time> getListTime() {
        List<Time> timeList = new ArrayList<>();
        timeList.add(new Time("6:00-7:00"));
        timeList.add(new Time("8:00-9:00"));
        timeList.add(new Time("10:00-11:00"));
        timeList.add(new Time("13:00-14:00"));
        timeList.add(new Time("15:00-16:00"));
        return  timeList;
    }


    private void apiCreateAppointment(Appointment appointment) {
        RetrofitClient.getRetrofit().createAppointment(appointment).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Intent i = new Intent(AppointmentDoctorActivity.this, InforAppointmentActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(AppointmentDoctorActivity.this, "Call error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void readData() {
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("dataId");
        if (bundle == null) {
            return;
        }
        doctorId = bundle.getLong("doctorId");
        RetrofitClient.getRetrofit().getDoctorId(doctorId).enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                Doctor doctor = response.body();
                txtBacsi.setText(doctor.getName());
                txtChuyenKhoa.setText(doctor.getSpecialist().getSpecialistName());
              //  txtGioKham.setText(doctor.getDay());
                timeAdapter.setOnItemClickListener(position -> {
                    Time time = timeList.get(position);
                    txtGioKham.setText(time.getPeriod());

                });
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                Toast.makeText(AppointmentDoctorActivity.this, "Call error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}