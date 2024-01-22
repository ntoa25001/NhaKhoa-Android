package com.ltdd.nhakhoaapp.model.api;



import com.ltdd.nhakhoaapp.model.content.ContentDoctor;
import com.ltdd.nhakhoaapp.model.content.ContentService;
import com.ltdd.nhakhoaapp.model.content.ContentSpecical;
import com.ltdd.nhakhoaapp.model.domain.Appointment;
import com.ltdd.nhakhoaapp.model.domain.Doctor;
import com.ltdd.nhakhoaapp.model.domain.Patient;
import com.ltdd.nhakhoaapp.model.domain.PatientDto;
import com.ltdd.nhakhoaapp.model.domain.Prescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("doctors")
    Call<ContentDoctor> getAllDoctor();

    @GET("doctors/times")
    Call<List<Doctor>> getAllDoctorByTime(@Query("period") String period, @Query("specialistId") Long specialistId);

    @GET("specialists")
    Call<ContentSpecical> getAllSpec();

    @GET("services")
    Call<ContentService> getAllService();

    @GET("doctors/{id}")
    Call<Doctor> getDoctorId(@Path("id") Long id);



    @GET("doctors")
    Call<ContentDoctor> searchDoctor(@Query("name") String name);

    @GET("patients")
    Call<Patient> getPatientByEmail(@Query("email") String email);

    @POST("patients")
    Call<Patient> updatePatientByEmail(@Query("email") String email);

    @GET("patient/{patientId}/medicine/list")
    Call<List<Prescription>> getPrescriptionByPatient(@Path("patientId") Long patientId);

    @Headers("Content-Type: application/json")
    @POST("newAppointment")
    Call<String> createAppointment(@Body Appointment appointment);

    @POST("appointments/{id}")
    Call<String> updateAppointmentById(@Body Appointment appointment, @Path("id") Long id);

    @POST("login")
    Call<Patient> login(@Query("email") String email, @Query("password") String password);

    @POST("newPatient")
    Call<String> register(@Body PatientDto patient);

    @GET("appointment/{patientId}")
    Call<List<Appointment>> getAppointmentByPatientId(@Path("patientId") Long patientId);


    @GET("appointments/{id}")
    Call<Appointment> getAppointmentId(@Path("id") Long id);
    @DELETE("appointments/{id}")
    Call<Appointment> deleteAppointmentId(@Path("id") Long id);


}
