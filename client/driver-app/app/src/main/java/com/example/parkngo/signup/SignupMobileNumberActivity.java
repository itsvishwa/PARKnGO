package com.example.parkngo.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;

public class SignupMobileNumberActivity extends AppCompatActivity {
    String firstName;
    String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mobile_number);

        Intent intent = getIntent();
        firstName = intent.getStringExtra("FIRST_NAME");
        lastName = intent.getStringExtra("LAST_NAME");

    }

    public void signup_mobile_number_act_otp_btn_handler(View v){

        // get reference to mobile number text view
        EditText mobileNumberView = findViewById(R.id.editText_mobile_number_signup);

        // validate the mobile number
        String mobileNumber = mobileNumberView.getText().toString();
        if (mobileNumber.equals("")){
            // invalid mobile number
            Toast.makeText(SignupMobileNumberActivity.this, "Mobile Number is required", Toast.LENGTH_LONG).show();
            return;
        } else if (mobileNumber.length() != 9) {
            // invalid mobile number
            Toast.makeText(SignupMobileNumberActivity.this, "Invalid mobile number", Toast.LENGTH_LONG).show();
            return;
        }


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/driver/get_otp/" + mobileNumber;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                // status code 200
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String responseJSON = jsonResponse.getString("response");
                            Toast.makeText(SignupMobileNumberActivity.this, responseJSON, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SignupMobileNumberActivity.this, SignupOtpActivity.class);
                            i.putExtra("FIRST_NAME", firstName);
                            i.putExtra("LAST_NAME", lastName);
                            i.putExtra("MOBILE_NUMBER", mobileNumber);
                            startActivity(i);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                // status code 400
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorResponse;
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorResponse = new String(error.networkResponse.data);
                            try {
                                JSONObject jsonResponse = new JSONObject(errorResponse);
                                String response = jsonResponse.getString("response");
                                Toast.makeText(SignupMobileNumberActivity.this, response, Toast.LENGTH_LONG).show(); // print the error
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        );
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void signup_mobile_number_act_back_btn_handler(View v){
        Intent i = new Intent(this, SignUpNameActivity.class);
        startActivity(i);
    }
}