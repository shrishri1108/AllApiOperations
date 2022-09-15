package com.example.allapioperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.allapioperations.Adapters.DashboardDataAdapter;
import com.example.allapioperations.Controller.RetrofitClient;
import com.example.allapioperations.Model.DashboardDataResult;
import com.example.allapioperations.databinding.ActivityDashBoardBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity {

    private ActivityDashBoardBinding dashBoardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashBoardBinding= ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(dashBoardBinding.getRoot());
        dashBoardBinding.dashboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDashBoardData(this.getApplicationContext());
        }

    private void getDashBoardData(Context context) {
        Call<ArrayList<DashboardDataResult>> call= RetrofitClient
                .getInstance()
                .getMyApi()
                .getDashboardData();

        call.enqueue(new Callback<ArrayList<DashboardDataResult>>() {
            @Override
            public void onResponse(Call<ArrayList<DashboardDataResult>> call, Response<ArrayList<DashboardDataResult>> response) {
                ArrayList<DashboardDataResult> myDashboardUserListFromApi= response.body();
                Log.d("Apis", "onResponse: "+myDashboardUserListFromApi);
                DashboardDataAdapter dataAdapter = new DashboardDataAdapter(context,myDashboardUserListFromApi);
                dashBoardBinding.dashboardRecyclerView.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DashboardDataResult>> call, Throwable t) {
                Log.d("tAjjj", "onFailure: "+t.getMessage());
                Toast.makeText(DashBoard.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}