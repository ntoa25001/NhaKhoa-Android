package com.ltdd.nhakhoaapp.model.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Appointment implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("appointmentDate")
    @Expose
    private String appointmentDate;

    @SerializedName("period")
    @Expose
    private String period;

    @SerializedName("note")
    @Expose
    private String note;

    private Long doctorId;
    private Long patientId;

    @SerializedName("patient")
    @Expose
    private String patient;

    @SerializedName("doctor")
    @Expose
    private Long doctor;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("services")
    @Expose
    private List<Service> services;

    public Appointment() {
    }

    public Appointment(String appointmentDate, String period, String note, Long doctorId, Long patientId) {
        this.appointmentDate = appointmentDate;
        this.period = period;
        this.note = note;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.patient = patient;
        this.doctor = doctor;
    }

    public Appointment(Long id,String appointmentDate, String period, String note) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.period = period;
        this.note = note;
    }

    public Appointment(String appointmentDate, String period, String note, Long doctorId, Long patientId, List<Service> services) {
        this.appointmentDate = appointmentDate;
        this.period = period;
        this.note = note;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.services = services;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
    //    public Appointment(String appointmentDate, String period, String note, Long doctorId, Long patientId) {
//        this.appointmentDate = appointmentDate;
//        this.period = period;
//        this.note = note;
//        this.doctorId = doctorId;
//        this.patientId = patientId;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public Long getDoctor() {
        return doctor;
    }

    public void setDoctor(Long doctor) {
        this.doctor = doctor;
    }
}
