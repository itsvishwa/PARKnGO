package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignupVehicleInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_vehicle_info);
    }

    public void signup_vehicle_info_act_continue_btn_handler(View v){
        Intent i = new Intent(this, SignupMobileNumberActivity.class);
        startActivity(i);
    }
}