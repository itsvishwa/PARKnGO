package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignupMobileNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mobile_number);
    }

    public void signup_mobile_number_act_otp_btn_handler(View v){
        Intent i = new Intent(this, SignupOtpActivity.class);
        startActivity(i);
    }

    public void signup_mobile_number_act_back_btn_handler(View v){
        Intent i = new Intent(this, SignUpNameActivity.class);
        startActivity(i);
    }
}