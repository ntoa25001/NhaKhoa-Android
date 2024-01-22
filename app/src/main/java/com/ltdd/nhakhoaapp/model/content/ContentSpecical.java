package com.ltdd.nhakhoaapp.model.content;


import com.google.gson.annotations.SerializedName;
import com.ltdd.nhakhoaapp.model.domain.Specialist;

import java.util.List;

public class ContentSpecical {
    @SerializedName("content")

    private List<Specialist> content;

    public ContentSpecical() {
    }

    public ContentSpecical(List<Specialist> content) {
        this.content = content;
    }

    public List<Specialist> getContent() {
        return content;
    }

    public void setContent(List<Specialist> content) {
        this.content = content;
    }
}

