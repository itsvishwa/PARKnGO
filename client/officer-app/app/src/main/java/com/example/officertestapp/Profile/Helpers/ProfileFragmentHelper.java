package com.example.officertestapp.Profile.Helpers;

import android.content.Context;
import android.content.Intent;
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
import com.example.officertestapp.Attendance.MarkAttendanceActivity;
import com.example.officertestapp.Attendance.MarkAttendanceOffActivity;
import com.example.officertestapp.Attendance.MarkedAttendanceSuccessfulActivity;
import com.example.officertestapp.Helpers.DateTimeHelper;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.HeroActivity;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragmentHelper {
    View profileMainView;
    Context context;
    FragmentManager fragmentManager;
    ParkngoStorage parkngoStorage;

    public ProfileFragmentHelper(View profileMainView, Context context, FragmentManager fragmentManager) {
        this.profileMainView = profileMainView;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void initDutyOffSwipeButton(TextView currentTimeView, TextView currentDateView) {
        //Initialize swipe button to mark duty off
        SlideToActView slideToActView = profileMainView.findViewById(R.id.duty_off_swipe_btn);
        // Set up event listener for slide completion
        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                markDutyOff(currentTimeView.getText().toString(), currentDateView.getText().toString());
            }
        });
    }



    private void markDutyOff(String currentTime, String currentDate) {
        Log.d("ProfileFragment", "Duty Off Button Swiped");

        // Convert current date and time strings to timestamp
        long timestampLong = DateTimeHelper.getTimestampFromDateTimeString(currentDate, currentTime);
        String timestamp = String.valueOf(timestampLong);

        RequestQueue queue = Volley.newRequestQueue(context);


        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/profile/mark_work_shift_off";
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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = parkngoStorage.getData("token");


                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);

                // Logging header values
                Log.d("Header Values", "Token: " + token);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                String parkingId = parkngoStorage.getData("parkingID");

                params.put("parking_id", parkingId);
                params.put("time_stamp", timestamp);

                // Log the values
                Log.d("RequestParameters", "Parking ID: " + parkingId);
                Log.d("RequestParameters", "Timestamp: " + timestamp);

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
            String timestamp = innerResponse.getString("time_stamp");

            // Log the parsed response data
            Log.d("Response Code", responseCode);
            Log.d("Message", message);
            Log.d("timestamp", timestamp);

            // Check if the response code is "800"
            if ("800".equals(responseCode)) {
                // Show a toast message with the response message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                // Navigate to MarkAttendanceOffActivity
                Intent intent = new Intent(context, MarkAttendanceOffActivity.class);
                intent.putExtra("timestamp", timestamp);
                context.startActivity(intent);
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
