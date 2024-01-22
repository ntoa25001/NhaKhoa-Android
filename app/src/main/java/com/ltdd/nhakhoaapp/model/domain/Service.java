package com.ltdd.nhakhoaapp.model.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;

    @SerializedName("isSelected")
    @Expose
    private Boolean isSelected;
//    @SerializedName("appointment")
//    @Expose
//    private Appointment appointment;

    public Service() {
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public Service(Long id, String name, String detail, Boolean isSelected) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.isSelected = isSelected;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
