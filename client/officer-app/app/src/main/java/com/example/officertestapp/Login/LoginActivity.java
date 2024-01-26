package com.example.officertestapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login_mobile_number_act_otp_btn_handler(View v){
        EditText mobileNumberView = findViewById(R.id.login_mobile_number_act_mobile_number_input);
        String mobileNumber = mobileNumberView.getText().toString();


        // validate the mobile number
        if (mobileNumber.equals("")){
            // invalid mobile number
            Toast.makeText(this, "Mobile Number is required", Toast.LENGTH_LONG).show();
            return;
        } else if (mobileNumber.length() != 9) {
            // invalid mobile number
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_LONG).show();
            return;
        }


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/user/send_otp_login/officer/" + mobileNumber;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                // status code 200
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String responseJSON = jsonResponse.getString("response");
                            Toast.makeText(LoginActivity.this, responseJSON, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this, LoginOtpActivity.class);
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
                                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show(); // print the error
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

    public void login_otp_act_send_otp_btn_handler(View v){
        Intent i = new Intent(this, LoginOtpActivity.class);
        startActivity(i);
    }

}
