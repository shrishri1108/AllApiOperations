package com.example.allapioperations;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allapioperations.Controller.RegisterRetrofitClient;
import com.example.allapioperations.Controller.RetrofitClient;
import com.example.allapioperations.Model.RealPathUtils;
import com.example.allapioperations.Model.RegisterModel;
import com.example.allapioperations.Model.RegisteredUsersDetailsModel;
import com.example.allapioperations.databinding.ActivityRegisteredBinding;

import java.util.ArrayList;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registered extends AppCompatActivity {

    private String user_name;
    private String user_contact_no;
    private String user_pwd;
    private ActivityRegisteredBinding registeredBinding;
    private String user_email;
    private String path="";
    private String user_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registeredBinding = ActivityRegisteredBinding.inflate(getLayoutInflater());
        setContentView(registeredBinding.getRoot());
        registeredBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name = registeredBinding.userName.getText().toString();
                user_email = registeredBinding.userEmail.getText().toString().toLowerCase();
                user_address = registeredBinding.userAddress.getText().toString();
                user_pwd = registeredBinding.userPassword.getText().toString();
                user_contact_no = registeredBinding.userContactNo.getText().toString();
                userExists();
            }
        });
        registeredBinding.uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    Intent intent= new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 10);
                }
                else {
                    ActivityCompat.requestPermissions(Registered.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });

        // Calling Login page onclick og already registered button .
        registeredBinding.btnAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Registered.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void userExists() {

        //Fetching all users contact no & name from api to verify
//          Calling the retrofit library .
        Call<ArrayList<RegisteredUsersDetailsModel>> call = RetrofitClient
                .getInstance()
                .getMyApi()
                .getRegisteredUsers();
        call.enqueue(new Callback<ArrayList<RegisteredUsersDetailsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RegisteredUsersDetailsModel>> call, Response<ArrayList<RegisteredUsersDetailsModel>> response) {
                if (response.isSuccessful()) {
                    boolean isUserRegistered = false;
                    ArrayList<RegisteredUsersDetailsModel> registered_user = response.body();
                    for (int i=0; i<registered_user.size();i++ ) {
                        if (registered_user.get(i) != null) {
//                            Checking user  exists  or  not .
                            if (registered_user.get(i).getUser_mobile_no().equals(user_contact_no)) {
                                isUserRegistered = true;
                                break;
                            }
                        }
                    }
                    if(!isUserRegistered){
//                      Sending  data               to server if  contact  no does not exists.
                        sendRegistrationDataToServer();
                    }
                    else {

                        // Sending  to   Login Page  if user's   mobile  no  exists
                        Toast.makeText(Registered.this,  "Mobile no already exists"+" Please Login here. ", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Registered.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(Registered.this, "Connection is successful ! But getting error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RegisteredUsersDetailsModel>> call, Throwable t) {
                Toast.makeText(Registered.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode  == 10  && resultCode == Activity.RESULT_OK){
            Uri uri=  data.getData();
            Context context=  Registered.this ;
            path = RealPathUtils.getRealPath(context, uri);
            Bitmap bitmap= BitmapFactory.decodeFile(path);
            registeredBinding.showSelectedImgs.setImageBitmap( bitmap);
        }

    }

    private void sendRegistrationDataToServer() {
        Call<RegisterModel> call = RegisterRetrofitClient
                .getInstance()
                .getMyRegisterApi()
                .pushFormData(user_name, user_email, user_contact_no, user_address, user_pwd);
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if (!(response.body().getPostResponse() == "Record Insterted Successfully")) {
                    Intent intent = new Intent(Registered.this, DashBoard.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Registered.this, " " + " Welcome, You are Logged In Successfully ! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(Registered.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean checkValidation() {
        String user_name_regex = "\\w";
        String contact_no_regex = "[[^0]{1}[0-9]{9}]";
        String email_regex = "[[a-z]{1}[\\w@.\\S]]";
        String pwds_regex = "[[\\w]{8}+]";
        if ((Pattern.matches(user_name_regex, user_name))) {
            registeredBinding.userName.setError("Name should be alphabet or number only & First and Last Name ");
            registeredBinding.userName.requestFocus(View.FOCUS_RIGHT);
        } else if (!Pattern.matches(contact_no_regex, user_contact_no)) {
            registeredBinding.userContactNo.setError("Mandatory! should not start with 0 and Length must be 10");
            registeredBinding.userContactNo.requestFocus(View.FOCUS_RIGHT);
        } else if (!Pattern.matches(email_regex, user_email)) {
            registeredBinding.userEmail.setError("Mandatory! Invalid Email");
            registeredBinding.userEmail.requestFocus(View.FOCUS_RIGHT);
        } else if (!Pattern.matches(pwds_regex, user_pwd)) {
            registeredBinding.userPassword.setError("Mandatory! Invalid Password");
            registeredBinding.userPassword.requestFocus(View.FOCUS_RIGHT);
        } else if (registeredBinding.userConfirmPassword.getText().toString().equals(user_pwd)) {
            registeredBinding.userConfirmPassword.getNextFocusForwardId();
            registeredBinding.userConfirmPassword.setError(" Enter same password ");
        } else {

            return true;
        }
        return false;

    }
}