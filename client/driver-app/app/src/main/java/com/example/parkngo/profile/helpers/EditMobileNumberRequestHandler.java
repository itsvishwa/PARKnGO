package com.example.parkngo.profile.helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.MainActivity;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.login.LoginMobileNumberActivity;
import com.example.parkngo.login.LoginOtpActivity;
import com.example.parkngo.profile.ProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditMobileNumberRequestHandler {

    Context context;
    public EditMobileNumberRequestHandler(Context context){
        this.context = context;
    }

    public void executeGetOTPRequest(String mobileNumber){

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

    public void executeChangeMobileNumberRequest(String mobileNumber, String otp){

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
}
