package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.content.Intent;
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
import com.example.officertestapp.HeroActivity;
import com.example.officertestapp.Home.ReleaseASlot03Fragment;
import com.example.officertestapp.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class PaymentDetailsHelper {
    Context context;
    View view;
    FragmentManager fragmentManager;
    ParkngoStorage parkngoStorage;

    public PaymentDetailsHelper (View view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.parkngoStorage = new ParkngoStorage(context);
    }

    public void getPaymentDetails(String paymentId) {
        String parkingId = parkngoStorage.getData("parkingID");

        // get the token
        String token = parkngoStorage.getData("token");

        // volley request
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/view_payment_details_of_session/" + paymentId;
        Log.d("Request URL", apiURL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
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
                headers.put("encoded-parking-id", parkingId);

                // Logging header values
                Log.d("Header Values", "Token: " + token);
                Log.d("Header Values", "Encoded Parking ID: " + parkingId);

                return headers;
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

            // Extract values from the JSON response
            String responseCode = responseData.getString("response_code");
            String message = responseData.getString("message");

            // Log the parsed response data
            Log.d("responseCode", responseCode);
            Log.d("message", message);


            // Check if the response code is "800"
            if ("800".equals(responseCode)) {
                // Show a toast message with the response message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                // Extract values from the JSON response
                String PaymentID = responseData.getString("payment_id");
                String VehicleNumber = responseData.getString("vehicle_number");
                String VehicleType = responseData.getString("vehicle_type");
                String StartTime = responseData.getString("start_time");
                String EndTime = responseData.getString("end_time");
                String TimeWent = responseData.getString("time_went");
                String Amount = responseData.getString("amount");

                // Bundle response values
                Bundle bundle = new Bundle();
                bundle.putString("PaymentID", PaymentID);
                bundle.putString("VehicleNumber", VehicleNumber);
                bundle.putString("VehicleType", VehicleType);
                bundle.putString("StartTime", StartTime);
                bundle.putString("EndTime", EndTime);
                bundle.putString("TimeWent", TimeWent);
                bundle.putString("Amount", Amount);

                // Log the values for debugging
                Log.d("Bundle Values", "Payment ID: " + PaymentID);
                Log.d("Bundle Values", "Vehicle Number: " + VehicleNumber);
                Log.d("Bundle Values", "Vehicle Type: " + VehicleType);
                Log.d("Bundle Values", "Start Time: " + StartTime);
                Log.d("Bundle Values", "End Time: " + EndTime);
                Log.d("Bundle Values", "Time Went: " + TimeWent);
                Log.d("Bundle Values", "Amount: " + Amount);

                //parse the bundle to the fragment03
                // Create an instance of the new fragment and set the bundle
                ReleaseASlot03Fragment releaseASlot03Fragment = new ReleaseASlot03Fragment();
                releaseASlot03Fragment.setArguments(bundle);

                // Navigate to AssignVehicle03Fragment
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(releaseASlot03Fragment);
            } else {
                // Show a toast message with the response message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        }catch (JSONException e){
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
