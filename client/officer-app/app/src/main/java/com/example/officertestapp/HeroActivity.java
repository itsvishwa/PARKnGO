package com.example.officertestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
    }

    public void hero_act_login_btn_handler(View v){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}