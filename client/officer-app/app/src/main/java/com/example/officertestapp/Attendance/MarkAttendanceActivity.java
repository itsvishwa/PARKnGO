package com.example.officertestapp.Attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.Helpers.DateTimeHelper;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.Home.AssignVehicle03Fragment;
import com.example.officertestapp.Login.LoginOtpActivity;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MarkAttendanceActivity extends AppCompatActivity {
    private DateTimeHelper dateTimeHelper;
    private String timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        // Find the TextViews in the layout
        TextView officerNameView = findViewById(R.id.mark_attendance_act_name);
        TextView officerMobileNumView = findViewById(R.id.mark_attendance_act_mobile);
        TextView officerIdView = findViewById(R.id.mark_attendance_act_officer_id);
        TextView officerNICView = findViewById(R.id.mark_attendance_act_nic);

        TextView officerParkingNameView = findViewById(R.id.mark_attendance_act_parking_name);
        TextView officerCompanyNameView = findViewById(R.id.mark_attendance_act_company_name);

        TextView currentTimeView = findViewById(R.id.mark_attendance_act_time);
        TextView currentDateView = findViewById(R.id.mark_attendance_act_date);

        ParkngoStorage parkngoStorage = new ParkngoStorage(this);
        String firstName = parkngoStorage.getData("firstName");
        String lastName = parkngoStorage.getData("lastName");
        String mobileNumber = parkngoStorage.getData("mobileNumber");
        String nic = parkngoStorage.getData("nic");
        String parkingName = parkngoStorage.getData("parkingName");
        String officerId  = parkngoStorage.getData("officerID");
        String companyName  = parkngoStorage.getData("companyName");
        String companyPhoneNumber  = parkngoStorage.getData("companyPhoneNumber");


        // Set values to UI components
        officerNameView.setText(firstName + " " + lastName);
        officerMobileNumView.setText(mobileNumber);
        officerIdView.setText(officerId);
        officerNICView.setText(nic);

        officerParkingNameView.setText(parkingName);
        officerCompanyNameView.setText(companyName);

        // Initialize and start DateTimeHelper
        dateTimeHelper = new DateTimeHelper(currentTimeView, currentDateView);
        dateTimeHelper.startUpdatingDateTime();


        //Initialize Call button to take a phone call to the company
        Button callBtn = findViewById(R.id.mark_attendance_act_call_btn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + companyPhoneNumber));
                startActivity(intent);
            }
        });


        // Find the SlideToActView by its ID
        SlideToActView slideToActView = findViewById(R.id.duty_in_swipe_btn);

        // Set up event listener for slide completion
        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {

            RequestQueue queue = Volley.newRequestQueue(MarkAttendanceActivity.this);

            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                // Retrieve the text values from TextViews
                String currentTime = currentTimeView.getText().toString();
                String currentDate = currentDateView.getText().toString();

                // Convert current date and time strings to timestamp
                long timestampLong = dateTimeHelper.getTimestampFromDateTimeString(currentDate, currentTime);
                timestamp = String.valueOf(timestampLong);

                String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/profile/mark_work_shift_on";
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

        });

    }

    protected void onDestroy() {
        super.onDestroy();
        // Stop updating DateTimeHelper when activity is destroyed
        if (dateTimeHelper != null) {
            dateTimeHelper.stopUpdatingDateTime();
        }
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
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                // Navigate to Next Activity with Intent Extras
                Intent i = new Intent(MarkAttendanceActivity.this, MarkedAttendanceSuccessfulActivity.class);
                i.putExtra("time_stamp", timestamp);
                startActivity(i);

            } else {
                // Show a toast message with the response message
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.e("JSON Parsing Error", "Error parsing response: " + e.getMessage());
            Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show();
        }
    }

    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");
                Toast.makeText(MarkAttendanceActivity.this, response, Toast.LENGTH_LONG).show(); // print the error
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

}