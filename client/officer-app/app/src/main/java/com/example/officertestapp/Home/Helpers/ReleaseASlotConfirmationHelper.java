package com.example.officertestapp.Home.Helpers;

import android.content.Context;

import android.os.Bundle;
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
import com.example.officertestapp.Home.PaymentDetailsFragment;
import com.example.officertestapp.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class ReleaseASlotConfirmationHelper {

    Context context;
    View view;
    FragmentManager fragmentManager;

    public ReleaseASlotConfirmationHelper(View view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void releaseSlot(String sessionId) {

        // Get the parkingId
        ParkngoStorage parkngoStorage = new ParkngoStorage(context);
        String parkingId = parkngoStorage.getData("parkingID");

        // get the token
        String token = parkngoStorage.getData("token");

        // volley request
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/end";
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


                // Log the values
                Log.d("RequestParameters", "Parking ID: " + parkingId);
                Log.d("RequestParameters", "Session ID: " + sessionId);

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

                // Extract values from the JSON response
                String paymentID = responseData.getString("payment_id");

                //parse the bundle to the PaymentDetailsHelper
                // Create an instance of PaymentDetailsHelper and pass the bundle
                Bundle data = new Bundle();
                data.putString("_id", paymentID);

                Log.d("Bundle Values", "Payment ID: " + paymentID);

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new PaymentDetailsFragment(), data, view);

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
                Toast.makeText(context, jsonResponse.getString("response"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}