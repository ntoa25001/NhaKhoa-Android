package com.ltdd.nhakhoaapp.model.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PatientDto {


    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private long phone;

    @SerializedName("idCard")
    @Expose
    private String idCard;

    @SerializedName("address")
    @Expose

    private String address;

    @SerializedName("password")
    @Expose
    private String password;


    private String birthYear;

    public PatientDto(String name, String email, long phone, String idCard, String address, String password, String birthYear) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.idCard = idCard;
        this.address = address;
        this.password = password;
        this.birthYear = birthYear;
    }

    public PatientDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }
}
