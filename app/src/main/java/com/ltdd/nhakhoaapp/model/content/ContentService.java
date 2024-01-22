package com.ltdd.nhakhoaapp.model.content;

import com.google.gson.annotations.SerializedName;
import com.ltdd.nhakhoaapp.model.domain.Service;
import com.ltdd.nhakhoaapp.model.domain.Specialist;

import java.util.List;

public class ContentService {
    @SerializedName("content")
    private List<Service> content;

    public List<Service> getContent() {
        return content;
    }

    public void setContent(List<Service> content) {
        this.content = content;
    }

    public ContentService(List<Service> content) {
        this.content = content;
    }

    public ContentService() {
    }
}
