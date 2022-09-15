package com.example.allapioperations.Model;

import com.google.gson.annotations.SerializedName;

public class DashboardDataResult {

    @SerializedName("Id")
    private String Id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("phone")
    private String contactno;
    @SerializedName("address")
    private String address;
    @SerializedName("img")
    private String img;

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() {
        return contactno;
    }

    public String getAddress() {
        return address;
    }

    public String getImg() {
        return img;
    }
}
