package com.example.parkngo.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parkngo.HeroActivity;
import com.example.parkngo.R;

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

    public void login_mobile_number_act_back_btn_handler(View v){
        Intent i = new Intent(this, HeroActivity.class);
        startActivity(i);
    }
}