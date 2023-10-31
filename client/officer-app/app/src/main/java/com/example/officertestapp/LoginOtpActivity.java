package com.example.officertestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginOtpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
    }
    public void login_otp_act_continue_btn_handler(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void login_otp_act_back_btn_handler(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
