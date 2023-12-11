package com.example.officertestapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void login_otp_act_send_otp_btn_handler(View v){

        /*//Mobile number send server
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);


        String res = "http://localhost/parkngo/server/index.php?otp="entered_number";
        builder.setMessage(res);
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();*/

        Intent i = new Intent(this, LoginOtpActivity.class);
        startActivity(i);
    }

}
