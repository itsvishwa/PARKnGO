package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginOtpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
    }

    public void login_otp_act_back_btn_handler(View v){
        Intent i = new Intent(this, LoginMobileNumberActivity.class);
        startActivity(i);
    }
}