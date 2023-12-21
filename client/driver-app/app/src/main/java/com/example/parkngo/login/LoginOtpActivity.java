package com.example.parkngo.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.signup.SignupMobileNumberActivity;
import com.example.parkngo.signup.SignupOtpActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginOtpActivity extends AppCompatActivity {

    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        Intent intent = getIntent();
        mobileNumber = intent.getStringExtra("MOBILE_NUMBER");

        TextView mobileNumberView = findViewById(R.id.mobile_number_otp_act_mobile_number_text);
        mobileNumberView.setText("(+94)" + mobileNumber);

    }


    // send the user entered OTP to server and get the response
    // if succeed redirect to main activity
    // otherwise state the error
    public void login_otp_act_login_btn_handler(View view) {
        // getting user data
        EditText optDigit1View = findViewById(R.id.login_act_otp_digit_1);
        EditText optDigit2View = findViewById(R.id.login_act_otp_digit_2);
        EditText optDigit3View = findViewById(R.id.login_act_otp_digit_3);
        EditText optDigit4View = findViewById(R.id.login_act_otp_digit_4);

        String otpDigit1 = optDigit1View.getText().toString();
        String otpDigit2 = optDigit2View.getText().toString();
        String otpDigit3 = optDigit3View.getText().toString();
        String otpDigit4 = optDigit4View.getText().toString();

        // validating user data
        if (otpDigit1.equals("") || otpDigit2.equals("") || otpDigit3.equals("") || otpDigit4.equals("")){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
            return;
        }

        // build the otp
        String otp = otpDigit1 + otpDigit2 + otpDigit3 + otpDigit4;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/user/login/driver/" + mobileNumber + "/" + otp;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                // status code 200 => login successful
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            // fetching response's data
                            String responseData = jsonResponse.getString("response");
                            String token = jsonResponse.getString("token");
                            JSONObject userData = jsonResponse.getJSONObject("user_data");

                            String firstName = userData.getString("first_name");
                            String lastName = userData.getString("last_name");
                            String mobileNumber = userData.getString("mobile_number");

                            // structuring data inorder to store
                            String dataArr[][] = {{"token", token}, {"firstName", firstName}, {"lastName", lastName}, {"mobileNumber", mobileNumber}};

                            // store data in shared preference
                            ParkngoStorage parkngoStorage = new ParkngoStorage(LoginOtpActivity.this);
                            parkngoStorage.addData(dataArr);

                            // redirect to main page
                            Intent i = new Intent(LoginOtpActivity.this, MainActivity.class);
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
                                Toast.makeText(LoginOtpActivity.this, response, Toast.LENGTH_LONG).show(); // print the error
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


    // resend btn handler
    // request the server to send the OTP again
    public void login_otp_act_resend_btn(View view){
        RequestQueue queue = Volley.newRequestQueue(this);

        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/user/send_otp/"  + mobileNumber;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            String responseData = jsonObject.getString("response");
                            Toast.makeText(LoginOtpActivity.this, responseData, Toast.LENGTH_LONG).show();
                        }catch (JSONException e){
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorResponse;
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorResponse = new String(error.networkResponse.data);
                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse);
                                String response = jsonObject.getString("response");
                                Toast.makeText(LoginOtpActivity.this, response, Toast.LENGTH_LONG).show(); // print the error
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        );
        queue.add(stringRequest);
    }


    public void login_otp_act_back_btn_handler(View v){
        Intent i = new Intent(this, LoginMobileNumberActivity.class);
        startActivity(i);
    }
}