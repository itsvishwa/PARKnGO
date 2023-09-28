package com.example.officertestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void login_otp_act_send_otp_btn_handler(View v){
        Intent i = new Intent(this, LoginOtpActivity.class);
        startActivity(i);
    }
}
