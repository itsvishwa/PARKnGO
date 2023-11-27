package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.login.LoginMobileNumberActivity;
import com.example.parkngo.signup.SignUpNameActivity;

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


    // login button
    public void hero_act_login_btn_handler(View v){
        Intent i = new Intent(this, LoginMobileNumberActivity.class);
        startActivity(i);
    }

    // signup button
    public void hero_act_signup_btn_handler(View v){
        Intent i = new Intent(this, SignUpNameActivity.class);
        startActivity(i);
    }
}