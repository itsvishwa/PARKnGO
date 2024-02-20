package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.Home.AssignVehicle03Fragment;
import com.example.officertestapp.Home.ReleaseASlot01Fragment;
import com.example.officertestapp.Home.ReleaseASlot02Fragment;
import com.example.officertestapp.Home.ReleaseASlot03Fragment;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReleaseASlotHelper {
    Context context;
    View view;
    FragmentManager fragmentManager;

    public ReleaseASlotHelper(View view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void releaseSlot(String sessionID, String timeStamp){
        // get the token
        ParkngoStorage parkngoStorage = new ParkngoStorage(context);
        String token = parkngoStorage.getData("token");

        String parkingID = parkngoStorage.getData("parkingID");

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

                // Logging header value
                Log.d("Header Values", "Token: " + token);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("session_id", sessionID);
                params.put("parking_id", parkingID);
                params.put("timestamp", timeStamp);

                // Log the values
                Log.d("RequestParameters", "Session ID: " + sessionID);
                Log.d("RequestParameters", "Parking ID: " + parkingID);
                Log.d("RequestParameters", "Time Stamp: " + timeStamp);

                return params;

            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void successResponseHandler(String response){
        Log.d("Raw Response", response);


//        try {
//            JSONObject jsonResponse = new JSONObject(response);
//            JSONObject innerResponse = jsonResponse.getJSONObject("response");
//            String responseCode = innerResponse.getString("response_code");
//            String message = innerResponse.getString("message");
//
//            // Log the parsed response data
//            Log.d("Response Code", responseCode);
//            Log.d("Message", message);
//
//            // Check if the response code is "800"
//            if ("800".equals(responseCode)) {
//                // Show a toast message with the response message
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//
//                // Extract values from the JSON response
//                String paymentId = innerResponse.getString("payment_id");
//                String vehicleNumber = innerResponse.getString("vehicle_number");
//                String vehicleType = innerResponse.getString("vehicle_type");
//                String startTime = innerResponse.getString("start_time");
//                String endTime = innerResponse.getString("end_time");
//                String timeWent = innerResponse.getString("time_went");
//                String amount = innerResponse.getString("amount");
//                String formattedVehicleNumber = vehicleNumber.replaceAll("(\\D)(\\d)", "$1 $2");
//
//                Bundle bundle = new Bundle();
//                bundle.putString("paymentId", paymentId);
//                bundle.putString("vehicleNumber", vehicleNumber);
//                bundle.putString("vehicleType", vehicleType);
//                bundle.putString("startTime", startTime);
//                bundle.putString("endTime", endTime);
//                bundle.putString("timeWent", timeWent);
//                bundle.putString("amount", amount);
//                bundle.putString("formattedVehicleNumber", formattedVehicleNumber);
//
//                Log.d("Bundle Values", "Payment Id: " + paymentId);
//                Log.d("Bundle Values", "Vehicle Number: " + vehicleNumber);
//                Log.d("Bundle Values", "Vehicle Type: " + vehicleType);
//                Log.d("Bundle Values", "Start Time: " + startTime);
//                Log.d("Bundle Values", "End Time: " + endTime);
//                Log.d("Bundle Values", "Time Went: " + timeWent);
//                Log.d("Bundle Values", "Amount: " + amount);
//                Log.d("Bundle Values", "Formatted Vehicle Number: " + formattedVehicleNumber);
//
//                // Set the end session data bundle in the fragment
//                ((ReleaseASlot02Fragment) fragmentManager.findFragmentById(R.id.main_act_frame_layout)).setEndSessionDataBundle(bundle);
//            } else {
//                // Show a toast message with the response message
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (JSONException e) {
//            Log.e("JSON Parsing Error", "Error parsing response: " + e.getMessage());
//            Toast.makeText(context, "Error parsing response", Toast.LENGTH_SHORT).show();
//        }
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