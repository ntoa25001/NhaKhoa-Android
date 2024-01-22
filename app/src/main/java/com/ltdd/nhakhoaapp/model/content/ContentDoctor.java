package com.ltdd.nhakhoaapp.model.content;


import com.google.gson.annotations.SerializedName;
import com.ltdd.nhakhoaapp.model.domain.Doctor;

import java.util.List;

public class ContentDoctor {
    @SerializedName("content")
    private List<Doctor> content;


    public ContentDoctor() {
    }

    public ContentDoctor(List<Doctor> content) {
        this.content = content;
    }

    public List<Doctor> getContent() {
        return content;
    }

    public void setContent(List<Doctor> content) {
        this.content = content;
    }
}
