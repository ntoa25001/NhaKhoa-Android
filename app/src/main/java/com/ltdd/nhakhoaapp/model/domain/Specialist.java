package com.ltdd.nhakhoaapp.model.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class Specialist {

    @SerializedName("specialistId")
    @Expose
    private Long specialistId;
    @SerializedName("specialistName")
    @Expose
    private String specialistName;
    @SerializedName("sign")
    @Expose
    private String sign;
    @SerializedName("doctors")
    @Expose
    private Collection<Doctor> doctors;

    public Specialist() {
    }

    public Specialist(Long specialistId, String specialistName, String sign, Collection<Doctor> doctors) {
        this.specialistId = specialistId;
        this.specialistName = specialistName;
        this.sign = sign;
        this.doctors = doctors;
    }

    public Long getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Long specialistId) {
        this.specialistId = specialistId;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Collection<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Collection<Doctor> doctors) {
        this.doctors = doctors;
    }
}
