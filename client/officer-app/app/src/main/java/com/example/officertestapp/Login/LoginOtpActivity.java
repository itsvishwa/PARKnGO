package com.example.officertestapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.Attendance.MarkAttendanceActivity;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginOtpActivity extends AppCompatActivity {
    private TextView countDownTxtView;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000;
    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        Intent intent = getIntent();
        mobileNumber = intent.getStringExtra("MOBILE_NUMBER");

        // Initialize countDownTxtView
        countDownTxtView = findViewById(R.id.countDownTxtView);

        // Start the countdown timer
        startCountDown();

        // Enable the resend button initially
        TextView resendTextView = findViewById(R.id.textView6);
        resendTextView.setEnabled(false);
        
        TextView mobileNumberView = findViewById(R.id.mobile_number_otp_act_mobile_number_text);
        mobileNumberView.setText("(+94)" + mobileNumber);


        EditText otpDigit1View = findViewById(R.id.login_act_otp_digit_1);
        EditText otpDigit2View = findViewById(R.id.login_act_otp_digit_2);
        EditText otpDigit3View = findViewById(R.id.login_act_otp_digit_3);
        EditText otpDigit4View = findViewById(R.id.login_act_otp_digit_4);

        setEditTextListener(otpDigit1View, otpDigit2View);
        setEditTextListener(otpDigit2View, otpDigit3View);
        setEditTextListener(otpDigit3View, otpDigit4View);
        setEditTextListener(otpDigit4View, null);

    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { // Count every 1 second
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                countDownTxtView.setText("(0 s)"); // Update text to 0 seconds
                // Enable the resend button when countdown finishes
                TextView resendTextView = findViewById(R.id.textView6);
                resendTextView.setEnabled(true); // Enable the resend button
            }
        }.start();
    }

    private void updateCountDownText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        String timeLeftFormatted = String.format("(%02d s)", seconds);
        countDownTxtView.setText(timeLeftFormatted);
    }

    // Override onDestroy to stop the countdown timer when the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancel the countdown timer to prevent memory leaks
        }
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


    public void login_otp_act_continue_btn_handler(View v){

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

        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/user/login/officer/" + mobileNumber + "/" + otp;

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
                            String nic = userData.getString("nic");
                            String officer_id = userData.getString("officer_id");
                            String parking_id = userData.getString("parking_id");
                            String parking_name = userData.getString("parking_name");
                            String company_name = userData.getString("company_name");
                            String company_phone_number = userData.getString("company_phone_number");

                            // structuring data inorder to store
                            String dataArr[][] = {{"token", token}, {"firstName", Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1)}, {"lastName", Character.toUpperCase(lastName.charAt(0)) + lastName.substring(1)}, {"mobileNumber", mobileNumber}, {"nic", nic}, {"officerID", officer_id}, {"parkingID", parking_id}, {"parkingName", parking_name}, {"companyName", company_name}, {"companyPhoneNumber", company_phone_number}};

                            // store data in shared preference
                            ParkngoStorage parkngoStorage = new ParkngoStorage(LoginOtpActivity.this);
                            parkngoStorage.addData(dataArr);

                            // redirect to main page
                            Intent i = new Intent(LoginOtpActivity.this, MarkAttendanceActivity.class);
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





    public void login_otp_act_back_btn_handler(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
