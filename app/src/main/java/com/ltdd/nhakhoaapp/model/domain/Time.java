package com.ltdd.nhakhoaapp.model.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Time implements Serializable {

    @SerializedName("timeId")
    @Expose

    private int timeId;

    @SerializedName("period")
    @Expose
    private String period;

    @SerializedName("doctor")
    @Expose
    private Doctor doctor;

    private boolean isTimeSelected = false;


    public boolean isTimeSelected() {
        return isTimeSelected;
    }

    public void setTimeSelected(boolean timeSelected) {
        isTimeSelected = timeSelected;
    }

    public Time() {
    }

    public Time(String period) {
        this.period = period;
    }

    public Time(int timeId, String period, Doctor doctor) {
        this.timeId = timeId;
        this.period = period;
        this.doctor = doctor;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
