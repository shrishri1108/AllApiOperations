package com.example.allapioperations;

import com.example.allapioperations.Model.DashboardDataResult;
import com.example.allapioperations.Model.RegisterModel;
import com.example.allapioperations.Model.RegisteredUsersDetailsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    static String DASHBOARD_BASE_URL = "https://raviworldwidemedicines.com/";

    @GET("get_api_data.php")
    Call<ArrayList<DashboardDataResult>> getDashboardData();


    @GET("get_api_data.php")
    Call<ArrayList<RegisteredUsersDetailsModel>> getRegisteredUsers ();

    @FormUrlEncoded
    @POST("post_api_data.php")
    Call<RegisterModel> pushFormData(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String contactno,
            @Field("address") String address,
            @Field("password") String password
    );
}
