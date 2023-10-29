package com.example.parkngo.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;

public class SignupOtpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
    }

    public void signup_otp_act_signup_btn_handler(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void signup_otp_act_back_btn_handler(View v){
        Intent i = new Intent(this, SignupMobileNumberActivity.class);
        startActivity(i);
    }
}