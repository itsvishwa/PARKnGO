package com.example.officertestapp.ForceEnd.Helpers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.ForceEnd.ForceEndDetailsFragment;
import com.example.officertestapp.ForceEnd.ForceEndMainFragment;
import com.example.officertestapp.ForceEnd.ForceEndedModel;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.Helpers.VehicleNumberHelper;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.Profile.Helpers.PPRecycleViewAdapter;
import com.example.officertestapp.Profile.Helpers.PaymentProfileModel;
import com.example.officertestapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ForceEndedFetchData {
    View view;
    View loadingView;
    Context context;
    ParkngoStorage parkngoStorage;

    public ForceEndedFetchData(View view, View loadingView, Context context) {
        this.view = view;
        this.loadingView = loadingView;
        this.context = context;
        this.parkngoStorage = new ParkngoStorage(context);
        fetchData();
    }

    public void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(context);

        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/force_end_sessions_list";
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
                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                String token = parkngoStorage.getData("token");
                String encodedParkingId = parkngoStorage.getData("parkingID");

                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                headers.put("encoded-parking-id", encodedParkingId);

                // Logging header values
                Log.d("Header Values", "Token: " + token);
                Log.d("Header Values", "Encoded Parking ID: " + encodedParkingId);

                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    // response-success handler
    private void successResponseHandler(String response){
        Log.d("Raw Response", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray resultDataArr = jsonObject.getJSONArray("response");

            ArrayList<ForceEndedModel> forceEndedModels = new ArrayList<>();

            for(int i=0; i<resultDataArr.length(); i++) {
                JSONObject resultData = resultDataArr.getJSONObject(i);
                String sessionId = resultData.getString("session_id");
                String vehicle = resultData.getString("vehicle");
                String vehicleType = resultData.getString("vehicle_type");
                String startDateTime = resultData.getString("session_start_date_and_timestamp");
                String endDateTime = resultData.getString("session_force_end_date_and_timestamp");

                String formattedVehicleNum = VehicleNumberHelper.splitVehicleNumber(vehicle);

                // format the timestamp to date time according to the devices time zone
                // Convert the timestamp string to a long value
                long startTimestamp = Long.parseLong(startDateTime);
                // Create a Date object from the timestamp
                Date startDate = new Date(startTimestamp * 1000);
                // Create a SimpleDateFormat object with your desired format
                SimpleDateFormat sdf = new SimpleDateFormat("hh.mm a - dd MMM YYYY", Locale.ENGLISH);
                // Set the timezone to the device's local timezone
                sdf.setTimeZone(TimeZone.getDefault());
                // Format the date object to a string
                String formattedStartDate = sdf.format(startDate);


                // format the timestamp to date time according to the devices time zone
                // Convert the timestamp string to a long value
                long endTimestamp = Long.parseLong(endDateTime);
                // Create a Date object from the timestamp
                Date endDate = new Date(endTimestamp * 1000);
                // Create a SimpleDateFormat object with your desired format
                SimpleDateFormat sdf1 = new SimpleDateFormat("hh.mm a - dd MMM YYYY", Locale.ENGLISH);
                // Set the timezone to the device's local timezone
                sdf1.setTimeZone(TimeZone.getDefault());
                // Format the date object to a string
                String formattedEndDate = sdf1.format(endDate);


                forceEndedModels.add(new ForceEndedModel(sessionId, formattedVehicleNum, vehicleType, formattedStartDate, formattedEndDate));

                // setting up the available parking spaces recycle view
                RecyclerView recyclerView = view.findViewById(R.id.force_end_frag_recycle_view);
                ForceEndedRecycleViewAdapter adapter = new ForceEndedRecycleViewAdapter(forceEndedModels ,context, view);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));


                // Replace the loading view with the parking view
                ViewGroup parent = (ViewGroup) loadingView.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(loadingView);
                    parent.removeView(loadingView);
                    parent.addView(view, index);
                }
            }

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
