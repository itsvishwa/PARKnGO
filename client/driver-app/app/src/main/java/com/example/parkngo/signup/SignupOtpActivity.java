package com.example.parkngo.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.parkngo.login.LoginOtpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupOtpActivity extends AppCompatActivity {

    String firstName, lastName, mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);

        Intent intent = getIntent();
        firstName = intent.getStringExtra("FIRST_NAME");
        lastName = intent.getStringExtra("LAST_NAME");
        mobileNumber = intent.getStringExtra("MOBILE_NUMBER");

         // change the mobile number dynamically
        TextView mobileNumberView = findViewById(R.id.mobile_number_otp_signup_act);
        mobileNumberView.setText("(+94) " + mobileNumber);

        EditText otpDigit1View = findViewById(R.id.sign_up_act_otp_digit_1);
        EditText otpDigit2View = findViewById(R.id.sign_up_act_otp_digit_2);
        EditText otpDigit3View = findViewById(R.id.sign_up_act_otp_digit_3);
        EditText otpDigit4View = findViewById(R.id.sign_up_act_otp_digit_4);

        setEditTextListener(otpDigit1View, otpDigit2View);
        setEditTextListener(otpDigit2View, otpDigit3View);
        setEditTextListener(otpDigit3View, otpDigit4View);
        setEditTextListener(otpDigit4View, null);
    }

    // sign up button handler => user registration part
    public void signup_otp_act_signup_btn_handler(View v){
        // getting user data
        EditText optDigit1View = findViewById(R.id.sign_up_act_otp_digit_1);
        EditText optDigit2View = findViewById(R.id.sign_up_act_otp_digit_2);
        EditText optDigit3View = findViewById(R.id.sign_up_act_otp_digit_3);
        EditText optDigit4View = findViewById(R.id.sign_up_act_otp_digit_4);

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

        // check otp and register the driver
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/user/register/" + otp;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                // status code 200 => valid OTP
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
                            String dataArr[][] = {{"token", token}, {"firstName", Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1)}, {"lastName", Character.toUpperCase(lastName.charAt(0)) + lastName.substring(1)}, {"mobileNumber", mobileNumber}};

                            // store data in shared preference
                            ParkngoStorage parkngoStorage = new ParkngoStorage(SignupOtpActivity.this);
                            parkngoStorage.addData(dataArr);

                            // redirect to main page
                            Intent i = new Intent(SignupOtpActivity.this, MainActivity.class);
                            startActivity(i);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                // status code 400 => Invalid OTP
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorResponse;
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorResponse = new String(error.networkResponse.data);
                            try {
                                JSONObject jsonResponse = new JSONObject(errorResponse);
                                String response = jsonResponse.getString("response");
                                Toast.makeText(SignupOtpActivity.this, response, Toast.LENGTH_LONG).show(); // print the error
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("mobile_number", mobileNumber);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // back button
    public void signup_otp_act_back_btn_handler(View v){
        Intent i = new Intent(this, SignupMobileNumberActivity.class);
        startActivity(i);
    }

    // resend otp code
    public void sign_up_act_resend_btn_handler(View view) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/user/send_otp/" + mobileNumber;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                // status code 200
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String responseJSON = jsonResponse.getString("response");
                            Toast.makeText(SignupOtpActivity.this, "OTP Sent", Toast.LENGTH_LONG).show();
                            // otp resending successful
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
                                Toast.makeText(SignupOtpActivity.this, response, Toast.LENGTH_LONG).show(); // print the error
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


    // cursor move to the next EditText
    private void setEditTextListener (final EditText currentEditText, final EditText nextEditText) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1 && nextEditText != null) {
                    nextEditText.requestFocus();
                }
            }
        });
    }
}