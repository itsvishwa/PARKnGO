package com.example.officertestapp.Attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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
import com.example.officertestapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MarkAttendanceActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private ParkngoStorage parkngoStorage;
    private DateTimeHelper dateTimeHelper;
    private String timestamp;
    private TextView currentTimeView;
    private TextView currentDateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        // Find the TextViews in the layout
        TextView officerNameAppBarView = findViewById(R.id.mark_attendance_officer_name);

        TextView officerNameView = findViewById(R.id.mark_attendance_act_name);
        TextView officerMobileNumView = findViewById(R.id.mark_attendance_act_mobile);
        TextView officerIdView = findViewById(R.id.mark_attendance_act_officer_id);
        TextView officerNICView = findViewById(R.id.mark_attendance_act_nic);

        TextView officerParkingNameView = findViewById(R.id.mark_attendance_act_parking_name);
        TextView officerCompanyNameView = findViewById(R.id.mark_attendance_act_company_name);

        currentTimeView = findViewById(R.id.mark_attendance_act_time);
        currentDateView = findViewById(R.id.mark_attendance_act_date);


        //Retrieve data from shared preferences
        parkngoStorage = new ParkngoStorage(this);
        String firstNameStr = parkngoStorage.getData("firstName");
        String lastNameStr = parkngoStorage.getData("lastName");
        String mobileNumberStr = parkngoStorage.getData("mobileNumber");
        String nicStr = parkngoStorage.getData("nic");
        String parkingNameStr = parkngoStorage.getData("parkingName");
        String officerIdStr = parkngoStorage.getData("officerID");
        String companyNameStr = parkngoStorage.getData("companyName");
        String companyPhoneNumberStr = parkngoStorage.getData("companyPhoneNumber");


        // Set values to Text Views
        officerNameAppBarView.setText(firstNameStr);

        officerNameView.setText(firstNameStr + " " + lastNameStr);
        officerMobileNumView.setText(mobileNumberStr);
        officerIdView.setText(officerIdStr);
        officerNICView.setText(nicStr);

        officerParkingNameView.setText(parkingNameStr);
        officerCompanyNameView.setText(companyNameStr);


        // Initialize and start DateTimeHelper
        dateTimeHelper = new DateTimeHelper(currentTimeView, currentDateView);
        dateTimeHelper.startUpdatingDateTime();


        //Initialize Call button to take a phone call to the company number
        Button callBtn = findViewById(R.id.mark_attendance_act_call_btn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + companyPhoneNumberStr));
                startActivity(intent);
            }
        });


        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Find the SlideToActView by its ID
        SlideToActView slideToActView = findViewById(R.id.duty_in_swipe_btn);


        // Set up event listener for slide completion
        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                retrieveDeviceLocation();
            }
        });
    }


    private void retrieveDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

            // Return for now, permissions will be handled in onRequestPermissionsResult
            return;
        }

        // Check if location services are enabled
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Location services are disabled, prompt the user to enable them
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
            return;
        }

        // Permissions are already granted, proceed with getting location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Handle the location object, e.g., get latitude and longitude
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);

                            markAttendance(location.getLatitude(), location.getLongitude());
                        } else {
                            Log.e("Location", "Last known location is null");
                        }
                    }
                });
    }

    private void markAttendance(double latitude, double longitude) {
        // Retrieve the text values from TextViews
        String currentTime = currentTimeView.getText().toString();
        String currentDate = currentDateView.getText().toString();

        // Convert current date and time strings to timestamp
        long timestampLong = dateTimeHelper.getTimestampFromDateTimeString(currentDate, currentTime);
        timestamp = String.valueOf(timestampLong);

        RequestQueue queue = Volley.newRequestQueue(MarkAttendanceActivity.this);


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
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));

                // Log the values
                Log.d("RequestParameters", "Parking ID: " + parkingId);
                Log.d("RequestParameters", "Timestamp: " + timestamp);
                Log.d("RequestParameters", "Latitude: " + String.valueOf(latitude));
                Log.d("RequestParameters", "Longitude: " + String.valueOf(longitude));

                return params;

            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Check if permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with getting location
                retrieveDeviceLocation();
            } else {
                // Permission denied, handle accordingly (e.g., show a message to the user)
                Log.e("Location", "Permission denied");
            }
        }
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

            } else if ("802".equals(responseCode)) {
                // Log that we are in the 400 case
                Log.d("Response Code", "Handling 802 case");

                // Show a toast message with the response message
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                // Navigate to Another Activity
                Intent i = new Intent(MarkAttendanceActivity.this, MarkedAttendanceFailActivity.class);
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