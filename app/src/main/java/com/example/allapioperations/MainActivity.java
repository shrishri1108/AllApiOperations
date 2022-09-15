package com.example.allapioperations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allapioperations.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private String u_ids;
    private String user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        u_ids = activityMainBinding.userName.getEditText().toString();
        user_password = activityMainBinding.userPwds.getEditText().toString();
        activityMainBinding.btnNotRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registered.class);
                startActivity(intent);
                finish();
            }
        });

    }
}