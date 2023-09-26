package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginMobileNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mobile_number);
    }

    public void login_mobile_number_act_otp_btn_handler(View v){
        Intent i = new Intent(this, LoginOtpActivity.class);
        startActivity(i);
    }
}