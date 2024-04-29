package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.officertestapp.Helpers.VehicleNumberHelper;
import com.example.officertestapp.HeroActivity;
import com.example.officertestapp.Home.AssignVehicle03Fragment;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AssignAVehicleConfirmationHelper {
    View assignAVehicleConfirmationView;
    Context context;
    FragmentManager fragmentManager;
    ParkngoStorage parkngoStorage;
    String vehicleNumber;
    String vehicleNumberProcessed;
    String vehicleType;
    String startTimeStamp;
    String driverId;

    public AssignAVehicleConfirmationHelper(View assignAVehicleConfirmationView, Context context, FragmentManager fragmentManager, String vehicleNumber, String vehicleNumberProcessed, String vehicleType, String startTimeStamp, String driverId) {
        this.assignAVehicleConfirmationView = assignAVehicleConfirmationView;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.vehicleNumber = vehicleNumber;
        this.vehicleNumberProcessed = vehicleNumberProcessed;
        this.vehicleType = vehicleType;
        this.startTimeStamp = startTimeStamp;
        this.driverId = driverId;
    }

    public void initTextViews() {
        TextView vehicleNumberTextView = assignAVehicleConfirmationView.findViewById(R.id.frag_assign_vehicle_01_vehicle_number_txt_view);
        TextView vehicleTypeTextView = assignAVehicleConfirmationView.findViewById(R.id.frag_assign_vehicle_01_vehicle_type_txt_view);

        String splittedVehicleNumber = VehicleNumberHelper.splitVehicleNumber(vehicleNumberProcessed);
        vehicleNumberTextView.setText(splittedVehicleNumber);

        String formattedType = VehicleNumberHelper.formatVehicleType(vehicleType);
        vehicleTypeTextView.setText(formattedType);
    }

    public void initYesBtnListener() {
        Button yesBtn = assignAVehicleConfirmationView.findViewById(R.id.assign_vehicle_02_yes_btn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AssignAVehicleConfirmationFragment", "Yes button clicked");

                // volley request
                RequestQueue queue = Volley.newRequestQueue(context);
                String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/start";
                Log.d("Request URL", apiURL);

                ParkngoStorage parkngoStorage = new ParkngoStorage(context);

                // Get the parkingId
                String parkingId = parkngoStorage.getData("parkingID");

                // get the token
                String token = parkngoStorage.getData("token");

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
                        params.put("vehicle_number", vehicleNumberProcessed);
                        params.put("vehicle_type", vehicleType);
                        //params.put("start_time", startTimeStamp);
                        params.put("parking_id", parkingId);
                        params.put("driver_id", driverId);

                        // Log the values
                        Log.d("RequestParameters", "Vehicle Number: " + vehicleNumberProcessed);
                        Log.d("RequestParameters", "Vehicle Type: " + vehicleType);
                        Log.d("RequestParameters", "Parking ID: " + parkingId);
                        Log.d("RequestParameters", "Driver ID: " + driverId);

                        return params;

                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
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
                JSONObject responseData = jsonResponse.getJSONObject("response");

                // Extract values from the JSON response
                String responseCode = responseData.getString("response_code");
                String message = responseData.getString("message");

                if(responseCode.equals("101") || responseCode.equals("204")) // the officer's parking space has been changed
                {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    parkngoStorage.clearData();
                    Intent i = new Intent(context, HeroActivity.class);
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.logout_immediately();
                }else{
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void initBackBtnListener() {
        Button backButton = assignAVehicleConfirmationView.findViewById(R.id.assign_vehicle_02_back_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous fragment
                fragmentManager.popBackStack();
            }
        });
    }

}
