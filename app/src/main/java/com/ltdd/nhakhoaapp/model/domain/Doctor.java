package com.ltdd.nhakhoaapp.model.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Doctor implements Serializable {
    @SerializedName("doctorId")
    @Expose
    private Long doctorId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("specialist")
    @Expose
    private Specialist specialist;
    @SerializedName("times")
    @Expose
    private List<Time> times;




    public Doctor() {
    }

    public Doctor(Long doctorId, String name, String avatar, String email, String phone, String day, Specialist specialist, List<Time> times) {
        this.doctorId = doctorId;
        this.name = name;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.day = day;
        this.specialist = specialist;
        this.times = times;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }
}
