package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentFetchData {
    Context context;
    View view;
    ParkngoStorage parkngoStorage;

    public PaymentFetchData(Context context, View view) {
        this.context = context;
        this.view = view;
        this.parkngoStorage = new ParkngoStorage(context);
    }

    public void fetchDta(String paymentId) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/view_payment_details_of_session/" + paymentId;

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
                String token = parkngoStorage.getData("token");
                String parkingID = parkngoStorage.getData("parkingID");

                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                headers.put("X-Encoded-Data", parkingID);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    private void successResponseHandler(String response){
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject responseData = jsonResponse.getJSONObject("response");

            // Extract values from the JSON response
            String vehicleNumber = responseData.getString("vehicle_number");
            // Insert space between letters and numbers in the vehicle number
            String formattedVehicleNumber = vehicleNumber.replaceAll("(\\D)(\\d)", "$1 $2");

            // Extracting vehicle number without province
            String vehicleNumberWithoutProvince = formattedVehicleNumber.substring(0, formattedVehicleNumber.length() - 2);

            String vehicleType = responseData.getString("vehicle_type");
            String vehicleTypeCaps = responseData.getString("vehicle_type").toUpperCase();
            String sessionStartedAt = responseData.getString("start_time");
            String sessionEndedAt = responseData.getString("end_time");
            String timeWent = responseData.getString("time_went");
            String amount = responseData.getString("amount");

            TextView vehicleNumberView = view.findViewById(R.id.vehicle_num_txt_view);
            TextView vehicleTypeView = view.findViewById(R.id.vehicle_type_txt_view);
            TextView sessionStartedAtView = view.findViewById(R.id.session_started_time_txt_view);
            TextView sessionEndedAtView = view.findViewById(R.id.session_ended_time_txt_view);
            TextView durationView = view.findViewById(R.id.time_duration_txt_view);
            TextView amountView = view.findViewById(R.id.amount_txt_view);

            // Set the values to TextViews
            vehicleNumberView.setText(formattedVehicleNumber);
            vehicleTypeView.setText(vehicleTypeCaps);
            sessionStartedAtView.setText(sessionStartedAt);
            sessionEndedAtView.setText(sessionEndedAt);
            durationView.setText(timeWent);
            amountView.setText(amount);

        }catch (JSONException e){
            throw new RuntimeException(e);
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
