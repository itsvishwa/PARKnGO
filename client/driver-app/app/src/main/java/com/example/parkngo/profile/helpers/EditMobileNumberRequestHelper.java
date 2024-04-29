package com.example.parkngo.profile.helpers;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.profile.ProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditMobileNumberRequestHelper {

    Context context;
    View editMobileNumberView;
    EditText otpDigit1View;
    EditText otpDigit2View;
    EditText otpDigit3View;
    EditText otpDigit4View;
    String mobileNumber = "";
    String otp = "";

    public EditMobileNumberRequestHelper(Context context, View editMobileNumberView){
        this.context = context;
        this.editMobileNumberView = editMobileNumberView;
        otpDigit1View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_1);
        otpDigit2View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_2);
        otpDigit3View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_3);
        otpDigit4View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_4);
    }

    public void init(){
        setEditTextListener(otpDigit1View, otpDigit2View);
        setEditTextListener(otpDigit2View, otpDigit3View);
        setEditTextListener(otpDigit3View, otpDigit4View);
        setEditTextListener(otpDigit4View, null);
        changeMobileNumberBtnHandler();
        sendOtpBtnHandler();
    }

    private void executeGetOTPRequest(String mobileNumber){

        RequestQueue queue = Volley.newRequestQueue(context);

        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/profile/send_otp/" + mobileNumber;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                // status code 200
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String responseJSON = jsonResponse.getString("response");
                            Toast.makeText(context, "OTP Sent", Toast.LENGTH_LONG).show();
                            // Disable button temporarily
                            setButtonClickable(false);
                            // Re-enable button after 1 minute
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setButtonClickable(true);
                                }
                            }, 60000); // 1 minute (60,000 milliseconds)
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
                                if (response.equals("PROF_MNAE")){
                                    Toast.makeText(context, "Entered mobile number is already registered!", Toast.LENGTH_LONG).show(); // print the error
                                }else{
                                    Toast.makeText(context, response, Toast.LENGTH_LONG).show(); // print the error
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // get the token
                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                String token = parkngoStorage.getData("token");
                headers.put("token", token);
                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void executeChangeMobileNumberRequest(String mobileNumber, String otp){

        RequestQueue queue = Volley.newRequestQueue(context);

        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/profile/update_mobile_number/" + mobileNumber + "/" + otp;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                // status code 200
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String responseJSON = jsonResponse.getString("response");
                            ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                            parkngoStorage.updateData("mobileNumber", mobileNumber);

                            // redirect to main activity
                            MainActivity mainActivity = (MainActivity) context;
                            mainActivity.replaceFragment(new ProfileFragment());
                            Toast.makeText(context, responseJSON, Toast.LENGTH_LONG).show();
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
                                Toast.makeText(context, response, Toast.LENGTH_LONG).show(); // print the error
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // get the token
                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                String token = parkngoStorage.getData("token");
                headers.put("token", token);
                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

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

    private int checkMobileNumberInput(String mobileNumber){
        if (mobileNumber.equals("")){
            return 1;
        }else if(mobileNumber.length() != 9){
            return 2;
        }else{
            return 3;
        }
    }

    private void changeMobileNumberBtnHandler(){
        Button changeMobileNumberBtn = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_change_mobile_number_btn);
        changeMobileNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int result = checkMobileNumberInput(mobileNumber);
                if (result == 1){
                    Toast.makeText(context, "Mobile Number can't be empty!", Toast.LENGTH_LONG).show();
                }else if(result == 2){
                    Toast.makeText(context, "Invalid mobile number", Toast.LENGTH_LONG).show();
                }else{
                    // getting OTP
                    EditText digit1View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_1);
                    EditText digit2View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_2);
                    EditText digit3View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_3);
                    EditText digit4View = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_digit_4);

                    otp = "";
                    otp = digit1View.getText().toString() + digit2View.getText().toString() + digit3View.getText().toString() + digit4View.getText().toString();
                    if(otp.equals("")){
                        Toast.makeText(context, "OTP is required", Toast.LENGTH_LONG).show();
                    }else{
                        executeChangeMobileNumberRequest(mobileNumber, otp);
                    }
                }
            }
        });
    }

    private void sendOtpBtnHandler(){
        // On click listeners ..............................................................................
        Button sendOTPBtn = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_send_otp_btn);
        sendOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isButtonClickable()) {
                    // the action
                    EditText mobileNumberView = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_mobile_number);
                    mobileNumber = "";
                    mobileNumber = mobileNumberView.getText().toString();

                    int result = checkMobileNumberInput(mobileNumber);

                    if (result == 1){
                        Toast.makeText(context, "Mobile Number can't be empty!", Toast.LENGTH_LONG).show();
                    }else if(result == 2){
                        Toast.makeText(context, "Invalid mobile number", Toast.LENGTH_LONG).show();
                    }else{
                        executeGetOTPRequest(mobileNumber);
                    }
                } else {
                    // Button is not clickable
                    Toast.makeText(context, "Please wait", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isButtonClickable() {
        Button sendOTPBtn = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_send_otp_btn);
        return sendOTPBtn.isEnabled();
    }

    private void setButtonClickable(boolean clickable) {
        Button sendOTPBtn = editMobileNumberView.findViewById(R.id.edit_mobile_number_frag_send_otp_btn);
        sendOTPBtn.setEnabled(clickable);
    }
}
