package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandler();
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

    private void successResponseHandler(){
        Toast.makeText(context, "Parking Session Added Successfully", Toast.LENGTH_SHORT).show();

        // Create a new instance of AssignVehicle03Fragment
        AssignVehicle03Fragment assignVehicle03Fragment = new AssignVehicle03Fragment();

        // Navigate to AssignVehicle02Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_act_frame_layout, assignVehicle03Fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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

