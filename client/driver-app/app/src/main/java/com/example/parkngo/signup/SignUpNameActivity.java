package com.example.parkngo.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parkngo.R;
import com.example.parkngo.login.HeroActivity;

public class SignUpNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_name);
    }

    public void sign_up_name_act_continue_btn_handler(View v){
        Intent i = new Intent(this, SignupMobileNumberActivity.class);
        startActivity(i);
    }

    public void sign_up_name_act_back_btn_handler(View v){
        Intent i = new Intent(this, HeroActivity.class);
        startActivity(i);
    }
}