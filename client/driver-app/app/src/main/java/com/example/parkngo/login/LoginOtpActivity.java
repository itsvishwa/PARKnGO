package com.example.parkngo.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;

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

    public void login_otp_act_login_btn_handler(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}