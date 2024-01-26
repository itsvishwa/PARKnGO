package com.example.officertestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.Login.LoginActivity;

import java.util.Objects;

public class HeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);

        ParkngoStorage parkngoStorage = new ParkngoStorage(this);
        String result = parkngoStorage.getData("token");

        // check whether the user is already logged in or not
        if (!Objects.equals(result, "empty")){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    public void hero_act_login_btn_handler(View v){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}