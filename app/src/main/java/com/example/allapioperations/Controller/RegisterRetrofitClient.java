package com.example.allapioperations.Controller;

import com.example.allapioperations.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterRetrofitClient {
    private static RegisterRetrofitClient registerRetrofitClient=null;
    private static Retrofit retrofit;

    public RegisterRetrofitClient() {
        retrofit= new Retrofit.Builder()
                .baseUrl(Api.DASHBOARD_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RegisterRetrofitClient getInstance(){
        if(registerRetrofitClient==null){
            registerRetrofitClient= new RegisterRetrofitClient();
        }
        return registerRetrofitClient;
    }

    public static Api getMyRegisterApi(){
        return retrofit.create(Api.class);
    }

}
