package com.example.officertestapp.ForceEnd.Helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.HeroActivity;
import com.example.officertestapp.Home.ReleaseASlot06Fragment;
import com.example.officertestapp.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForceEndedPaymentReceiveHelper {
    Context context;
    View view;
    FragmentManager fragmentManager;
    ParkngoStorage parkngoStorage;

    public ForceEndedPaymentReceiveHelper(View view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.parkngoStorage = new ParkngoStorage(context);
    }

    public void paymentReceivedCash(String sessionId, String amount) {
        Log.d("paymentReceivedCash", "sessionId : " + sessionId);
        Log.d("paymentReceivedCash", "amount : " + amount);

        // Get the parkingId
        ParkngoStorage parkngoStorage = new ParkngoStorage(context);
        String parkingId = parkngoStorage.getData("parkingID");

        // get the token
        String token = parkngoStorage.getData("token");

        // volley request
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/payment/force_ended_payment_accept";
        Log.d("Request URL", apiURL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandler(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorResponseHandler(error);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("parking_id", parkingId);
                params.put("session_id", sessionId);
                params.put("amount", amount);

                // Log the values
                Log.d("RequestParameters", "Parking ID: " + parkingId);
                Log.d("RequestParameters", "Session ID: " + sessionId);
                Log.d("RequestParameters", "Amount: " + amount);

                return params;

            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void successResponseHandler(String response){
        Log.d("Raw Response", response);

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject responseData = jsonResponse.getJSONObject("response");

            String responseCode = responseData.getString("response_code");
            String message = responseData.getString("message");

            // Log the parsed response data
            Log.d("Response Code", responseCode);
            Log.d("Message", message);

            // Check if the response code is "800"
            if ("800".equals(responseCode)) {
                // Show a toast message with the response message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                // Navigate to AssignVehicle03Fragment
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new ReleaseASlot06Fragment());

            } else {
                // Show a toast message with the response message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }

        } catch (JSONException e) {
            Log.e("JSON Parsing Error", "Error parsing response: " + e.getMessage());
            Toast.makeText(context, "Error parsing response", Toast.LENGTH_SHORT).show();
        }
    }

    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");
                if(response.equals("101") || response.equals("204")) // the officer's parking space has been changed
                {
                    parkngoStorage.clearData();
                    Intent i = new Intent(context, HeroActivity.class);
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.logout_immediately();
                }else{
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
