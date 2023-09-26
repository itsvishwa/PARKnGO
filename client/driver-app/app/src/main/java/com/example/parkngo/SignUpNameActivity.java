package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_name);
    }

    public void sign_up_name_act_continue_btn_handler(View v){
        Intent i = new Intent(this, SignupVehicleInfoActivity.class);
        startActivity(i);
    }
}