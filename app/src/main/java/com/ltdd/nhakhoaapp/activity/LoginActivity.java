package com.ltdd.nhakhoaapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ltdd.nhakhoaapp.R;
import com.ltdd.nhakhoaapp.model.api.RetrofitClient;
import com.ltdd.nhakhoaapp.model.domain.Patient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private AppCompatButton btnSignIn;
    private TextView btnSignUp, tvSignalStrength;

    private String email, password, patientName;
    private Long patientId;

    private Patient patient;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.email_dn);
        edtPassword = findViewById(R.id.pass_dn);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btn_signup);
        tvSignalStrength = findViewById(R.id.tvSignalStrength);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Please enter email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Please enter password");
                    return;
                }

                // Check WiFi signal strength before attempting to login
                int signalStrength = getWiFiSignalStrength();
                tvSignalStrength.setText("WiFi Signal Strength: " + signalStrength + "/4");

                if (signalStrength == 0) {
                    Toast.makeText(LoginActivity.this, "Unable to connect to the internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginApp(email, password);
            }
        });
    }

    private int getWiFiSignalStrength() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int signalStrength = wifiInfo.getRssi(); // Signal strength in dBm
            return WifiManager.calculateSignalLevel(signalStrength, 5); // Convert to level from 0 to 4
        } else {
            return 0;
        }
    }

    private void loginApp(String email, String password) {
        RetrofitClient.getRetrofit().login(email, password).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                patient = response.body();
                if (patient == null) {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    return;
                }
                patientName = patient.getName();
                patientId = patient.getPatientId();
                if (patient == null) {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.putString("name", patientName);
                    editor.putLong("patientId", patientId);
                    editor.apply();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Call error in " + this.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
