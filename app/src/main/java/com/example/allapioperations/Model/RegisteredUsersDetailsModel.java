package com.example.allapioperations.Model;

import com.google.gson.annotations.SerializedName;

public class RegisteredUsersDetailsModel {

    @SerializedName("phone")
    private String user_mobile_no;
    @SerializedName("name")
    private String user_name;

    public String getUser_mobile_no() {
        return user_mobile_no;
    }

    public String getUser_name() {
        return user_name;
    }
}
