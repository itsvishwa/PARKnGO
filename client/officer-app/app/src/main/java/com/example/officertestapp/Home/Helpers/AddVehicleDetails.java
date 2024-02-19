package com.example.officertestapp.Home.Helpers;

import android.content.Context;

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
import com.example.officertestapp.Home.AssignVehicle03Fragment;
import com.example.officertestapp.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class AddVehicleDetails {

    Context context;
    View view;
    FragmentManager fragmentManager;

    public AddVehicleDetails(View view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void addDetails(String token, String vehicleNumber, String selectedVehicleType, String startTimeStamp, String parkingId, String driverId) {
        // volley request
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/start";
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
                params.put("vehicle_number", vehicleNumber);
                params.put("vehicle_type", selectedVehicleType);
                params.put("start_time", startTimeStamp);
                params.put("parking_id", parkingId);
                params.put("driver_id", driverId);

                // Log the values
                Log.d("RequestParameters", "Vehicle Number: " + vehicleNumber);
                Log.d("RequestParameters", "Vehicle Type: " + selectedVehicleType);
                Log.d("RequestParameters", "Start Time: " + startTimeStamp);
                Log.d("RequestParameters", "Parking ID: " + parkingId);
                Log.d("RequestParameters", "Driver ID: " + driverId);

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
            JSONObject innerResponse = jsonResponse.getJSONObject("response");
            String responseCode = innerResponse.getString("response_code");
            String message = innerResponse.getString("message");

            // Log the parsed response data
            Log.d("Response Code", responseCode);
            Log.d("Message", message);

            // Check if the response code is "800"
            if ("800".equals(responseCode)) {
                // Show a toast message with the response message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                // Navigate to AssignVehicle03Fragment
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new AssignVehicle03Fragment());
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

