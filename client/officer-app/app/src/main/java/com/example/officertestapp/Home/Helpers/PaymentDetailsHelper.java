package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class PaymentDetailsHelper {
    Context context;
    View view;
    ParkngoStorage parkngoStorage;
    String amount;

    public PaymentDetailsHelper (View view, Context context) {
        this.view = view;
        this.context = context;
        this.parkngoStorage = new ParkngoStorage(context);
    }

    public void initLayout(String paymentId) {
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
                amount = responseData.getString("amount");

                TextView vehicleNumberTextView = view.findViewById(R.id.vehicle_num_txt_view);
                TextView vehicleTypeTextView = view.findViewById(R.id.vehicle_type_txt_view);
                TextView sessionStartedTimeTextView = view.findViewById(R.id.session_started_time_txt_view);
                TextView sessionEndedTimeTextView = view.findViewById(R.id.session_ended_time_txt_view);
                TextView timeDurationTextView = view.findViewById(R.id.time_duration_txt_view);
                TextView amountTextView = view.findViewById(R.id.amount_txt_view);

                // Insert space between letters and numbers in the vehicle number
                String formattedVehicleNumber = VehicleNumber.replaceAll("(\\D)(\\d+)", "$1 $2 ");


//                 format the timestamp to date time according to the devices time zone
//                 Convert the timestamp string to a long value
            long timestampStart = Long.parseLong(StartTime);
            // Create a Date object from the timestamp
            Date startDate = new Date(timestampStart * 1000);
            // Create a SimpleDateFormat object with your desired format
            SimpleDateFormat sdf = new SimpleDateFormat("hh.mm a", Locale.ENGLISH);
            // Set the timezone to the device's local timezone
            sdf.setTimeZone(TimeZone.getDefault());
            // Format the date object to a string
            String formattedStartTime = sdf.format(startDate);


            long timestampEnd = Long.parseLong(EndTime);
            Date EndDate = new Date(timestampEnd * 1000);
            SimpleDateFormat edf = new SimpleDateFormat("hh.mm a", Locale.ENGLISH);
            edf.setTimeZone(TimeZone.getDefault());
            String formattedEndTime = sdf.format(EndDate);

            vehicleNumberTextView.setText(formattedVehicleNumber);
            vehicleTypeTextView.setText(VehicleType);
            sessionStartedTimeTextView.setText(formattedStartTime);
            sessionEndedTimeTextView.setText(formattedEndTime);
            timeDurationTextView.setText(TimeWent);
            amountTextView.setText(amount);

            } else {
                // Show a toast message with the response message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        }catch (JSONException e){
            Log.e("JSON Parsing Error", "Error parsing response: " + e.getMessage());
            Toast.makeText(context, "Error parsing response", Toast.LENGTH_SHORT).show();
        }
    }

    public String getAmount(){
        return amount;
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
