package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.controller.adapter.DoctorDialogAdapter;
import com.ltdd.nhakhoaapp.controller.adapter.ServiceAdapter;
import com.ltdd.nhakhoaapp.controller.adapter.SpecialistAdapter;
import com.ltdd.nhakhoaapp.controller.adapter.TimeAdapter;

import com.google.android.material.textfield.TextInputEditText;
import com.ltdd.nhakhoaapp.model.api.RetrofitClient;
import com.ltdd.nhakhoaapp.model.content.ContentService;
import com.ltdd.nhakhoaapp.model.content.ContentSpecical;
import com.ltdd.nhakhoaapp.model.domain.Appointment;
import com.ltdd.nhakhoaapp.model.domain.Doctor;
import com.ltdd.nhakhoaapp.model.domain.Service;
import com.ltdd.nhakhoaapp.model.domain.Specialist;
import com.ltdd.nhakhoaapp.model.domain.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends AppCompatActivity {

    private RecyclerView rcTime, rcSpec,rcDtor, rcDichvu;
    private TimeAdapter timeAdapter;

    private DoctorDialogAdapter doctorAdapter;
    private SpecialistAdapter specialistAdapter;

    private ServiceAdapter serviceAdapter;
    private List<Specialist> specialists;

    private List<Service> services;
    private List<Time> timeList;

    private List<Doctor> doctors;

    private TextView txtGone;
    private TextInputEditText edtGioKham, edtChuyenKhoa, edtBacsi, edtNgayKham,edtNote, edtName ,editDichvu;

    private Button backTime,btnSpec, btnDoctor,btnNgayKham, btnService ,btnDatlich;
    private AlertDialog dialogC,dialogA,dialogB, dialogD;

    private String period;
    private Long specialistId, doctorId;

    List<Service> selectedServices;
    private Long patientId;

    private ImageView imgClose;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        timeAdapter = new TimeAdapter(this);
        specialistAdapter = new SpecialistAdapter(this);
        doctorAdapter = new DoctorDialogAdapter(this);
        serviceAdapter = new ServiceAdapter(this);

        edtChuyenKhoa = findViewById(R.id.edt_chuyenkhoa);
        edtNgayKham = findViewById(R.id.edt_ngaykham);
        edtGioKham = findViewById(R.id.edt_giokham);
        edtBacsi = findViewById(R.id.edt_bacsi);
        imgClose= findViewById(R.id.icon_close_view);
        edtName = findViewById(R.id.txtInName);
        edtNote = findViewById(R.id.edt_note);
        editDichvu = findViewById(R.id.edt_dichvu);





        btnDatlich = findViewById(R.id.btnDatlich);
        txtGone = findViewById(R.id.txt_gone);
        specialists = new ArrayList<>();
        services = new ArrayList<>();
        doctors = new ArrayList<>();
        timeList = getListTime();

        showTime();
        showSpecial();
        showDoctor();
        showService();

        //Get data from sharepreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        patientId = sharedPreferences.getLong("patientId", -1);
        String name = sharedPreferences.getString("name","");
        edtName.setText(name);

        edtNgayKham.setOnClickListener(v -> onClickEdtNgayKham());
        btnDatlich.setOnClickListener(v -> saveAppointment());

        //Close activity
        imgClose.setOnClickListener(v -> {
            Intent i = new Intent(AppointmentActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });


    }

    @SuppressLint("MissingInflatedId")
    private void showDoctor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lựa chọn ");

        View view = getLayoutInflater().inflate(R.layout.layout_doctor, null);
        rcDtor = view.findViewById(R.id.rc_dtor);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcDtor.setLayoutManager(linearLayoutManager);
        btnDoctor = view.findViewById(R.id.btn_back_dt);

        btnDoctor.setOnClickListener(v -> dialogB.dismiss());
        builder.setView(view);
        dialogB = builder.create();
        dialogB.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog);
        dialogB.getWindow().getAttributes().windowAnimations = R.style.MydialogAnimation;
        edtBacsi.setOnClickListener(v -> {
            doctors.clear();
            RetrofitClient.getRetrofit().getAllDoctorByTime(period, specialistId).enqueue(new Callback<List<Doctor>>() {
                @Override
                public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                    if(response.body() == null) {
                        Toast.makeText(AppointmentActivity.this, "Danh sách Trống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    doctors = response.body();
                    doctorAdapter.setData(doctors);
                    rcDtor.setAdapter(doctorAdapter);
                }

                @Override
                public void onFailure(Call<List<Doctor>> call, Throwable t) {
                    Toast.makeText(AppointmentActivity.this, "Call fail", Toast.LENGTH_SHORT).show();

                }
            });
            dialogB.show();
        });

        doctorAdapter.setOnItemClickListener(position -> {
            Doctor doctor = doctors.get(position);
            edtBacsi.setText(doctor.getName());
            doctorId = doctor.getDoctorId();
            dialogB.dismiss();
        });
    }

    @SuppressLint("MissingInflatedId")
    private void showSpecial() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lựa chọn ");

        View view = getLayoutInflater().inflate(R.layout.layout_specialist, null);
        rcSpec = view.findViewById(R.id.rc_special);

        RetrofitClient.getRetrofit().getAllSpec().enqueue(new Callback<ContentSpecical>() {
            @Override
            public void onResponse(Call<ContentSpecical> call, Response<ContentSpecical> response) {
                if(response.isSuccessful()) {
                    ContentSpecical content = response.body();
                    if (content ==null) {
                        return;
                    }
                    List<Specialist> s = content.getContent();
                    specialists.addAll(s);
                    specialistAdapter.setData(specialists);
                    rcSpec.setAdapter(specialistAdapter);
                }
            }
            @Override
            public void onFailure(Call<ContentSpecical> call, Throwable t) {

                Toast.makeText(AppointmentActivity.this, "Call fail", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcSpec.setLayoutManager(linearLayoutManager);
        btnSpec = view.findViewById(R.id.btn_back_spec);

        btnSpec.setOnClickListener(v -> dialogC.dismiss());
        builder.setView(view);
        dialogC = builder.create();
        dialogC.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog);
        dialogC.getWindow().getAttributes().windowAnimations = R.style.MydialogAnimation;
        edtChuyenKhoa.setOnClickListener(v -> {
            dialogC.show();
        });
        specialistAdapter.setOnItemClickListener(position -> {
            Specialist specialist = specialists.get(position);
            specialistId = specialist.getSpecialistId();
            edtChuyenKhoa.setText(specialist.getSpecialistName());
            dialogC.dismiss();
        });
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

                Toast.makeText(AppointmentActivity.this, "Call failaass", Toast.LENGTH_SHORT).show();
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
        editDichvu.setOnClickListener(v -> {
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
                Toast.makeText(AppointmentActivity.this, "Bạn đã chọn dịch vụ: " + service.getName(), Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý khi CheckBox bị bỏ chọn
                Toast.makeText(AppointmentActivity.this, "Bạn đã bỏ chọn dịch vụ: " + service.getName(), Toast.LENGTH_SHORT).show();
            }

             selectedServices = new ArrayList<>();
            for (Service s : services) {
                if (s.getSelected()) {
                    selectedServices.add(s);
                    editDichvu.setText(s.getName());
                }
            }
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
        edtGioKham.setOnClickListener(v -> {
            dialogA.show();
        });

        timeAdapter.setOnItemClickListener(position -> {
            Time time = timeList.get(position);
            edtGioKham.setText(time.getPeriod());
            period = time.getPeriod();

            if(doctors != null) {
            edtBacsi.setVisibility(View.VISIBLE);}
            dialogA.dismiss();

        });
    }

    private void onClickEdtNgayKham() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AppointmentActivity.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    edtNgayKham.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void saveAppointment() {
        String ngayKham = edtNgayKham.getText().toString().trim();
        String day = edtGioKham.getText().toString().trim();
        String note = edtNote.getText().toString();

        Appointment appointment = new Appointment(ngayKham, day, note, doctorId, patientId, selectedServices);


        if(TextUtils.isEmpty(ngayKham)) {
            edtNgayKham.setError("Input text");
            return;

        }
        if(TextUtils.isEmpty(day)) {
            edtGioKham.setError("Input text");
            return;
        }
        if(TextUtils.isEmpty(note)) {
            edtNote.setError("Input text");
            return;

        }
        apiCreateAppointment(appointment);
    }

    private void apiCreateAppointment(Appointment appointment) {
        RetrofitClient.getRetrofit().createAppointment(appointment).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Intent i = new Intent(AppointmentActivity.this, InforAppointmentActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(AppointmentActivity.this, "Call error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Time> getListTime() {
        List<Time> timeList = new ArrayList<>();
        timeList.add(new Time("6:00-7:00"));
        timeList.add(new Time("7:00-8:00"));
        timeList.add(new Time("8:00-9:00"));
        timeList.add(new Time("9:00-10:00"));
        timeList.add(new Time("10:00-11:00"));
        timeList.add(new Time("13:00-14:00"));
        timeList.add(new Time("14:00-15:00"));
        timeList.add(new Time("15:00-16:00"));
       return  timeList;
    }
}