package com.example.parkngo.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.login.HeroActivity;

public class SignUpNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_name);
    }

    public void sign_up_name_act_continue_btn_handler(View v){

        EditText firstNameView = findViewById(R.id.sign_up_name_act_first_name);
        EditText lastNameView = findViewById(R.id.sign_up_name_act_last_name);

        // validating user data
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        if(firstName.equals("") || lastName.equals("")){
            Toast.makeText(this, "First name and Last name is required", Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(this, SignupMobileNumberActivity.class);
        i.putExtra("FIRST_NAME", firstName);
        i.putExtra("LAST_NAME", lastName);
        startActivity(i);
    }

    public void sign_up_name_act_back_btn_handler(View v){
        Intent i = new Intent(this, HeroActivity.class);
        startActivity(i);
    }
}