package com.example.allapioperations.Model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {

    @SerializedName("msg")
    private String postResponse;

    public String getPostResponse() {
        return postResponse;
    }
}
