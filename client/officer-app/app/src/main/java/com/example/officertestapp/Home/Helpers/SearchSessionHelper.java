package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
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
import com.example.officertestapp.Home.ReleaseASlot01Fragment;
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

public class SearchSessionHelper {
    private View view;
    private Context context;
    private FragmentManager fragmentManager;
    private Button continueBtn;
    ParkngoStorage parkngoStorage;

    public SearchSessionHelper(View view, Context context, FragmentManager fragmentManager, Button continueBtn) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.continueBtn = continueBtn;
        this.parkngoStorage = new ParkngoStorage(context);
        searchSession();
    }

    public void searchSession() {
        Log.d("SearchSession", "This is searchSession");
        // getting reference to the views
        EditText lettersEditText = view.findViewById(R.id.vehicle_num_letters);
        EditText digitsEditText = view.findViewById(R.id.vehicle_num_digits);
        Spinner provincesSpinner = view.findViewById(R.id.spinner_provinces);


        if (TextUtils.isEmpty(lettersEditText.getText()) || TextUtils.isEmpty(digitsEditText.getText()) || provincesSpinner.getSelectedItem() == null){
            // Show a toast that input is incomplete
            Toast.makeText(context, "Please fill all fields of Vehicle Number", Toast.LENGTH_SHORT).show();

        }else {
            // Get input data from UI
            String letters = lettersEditText.getText().toString();
            String digits = digitsEditText.getText().toString();
            String province = provincesSpinner.getSelectedItem().toString();

            // Concatenate the values to create the complete vehicle number
            String completeVehicleNumber = letters + digits + province;
            Log.d("Complete Vehicle Number", completeVehicleNumber);

            // get the token
            ParkngoStorage parkngoStorage = new ParkngoStorage(context);
            String token = parkngoStorage.getData("token");

            String encodedParkingId = parkngoStorage.getData("parkingID");

            // volley request
            RequestQueue queue = Volley.newRequestQueue(context);

            String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/search/"+ completeVehicleNumber;
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
                String SessionID = responseData.getString("session_id");
                String EndTimeStamp = responseData.getString("end_Time_Stamp");
                String StartTimeStamp = responseData.getString("start_Time_Stamp");
                String Duration = responseData.getString("Duration");
                String Amount = responseData.getString("Amount");


                // format the timestamp to date time according to the devices time zone
                // Convert the timestamp string to a long value
                long timestamp = Long.parseLong(StartTimeStamp);
                // Create a Date object from the timestamp
                Date startDate = new Date(timestamp * 1000);
                // Create a SimpleDateFormat object with your desired format
                SimpleDateFormat sdf = new SimpleDateFormat("hh.mm a\ndd MMM yyyy", Locale.ENGLISH);
                // Set the timezone to the device's local timezone
                sdf.setTimeZone(TimeZone.getDefault());
                // Format the date object to a string
                String formattedDate = sdf.format(startDate);


                // Insert space between letters and numbers in the vehicle number
                String formattedVehicleNumber = responseData.getString("Vehicle_Number").replaceAll("(\\D)(\\d)", "$1 $2");

                // Extracting vehicle number without province
                String vehicleNumberWithoutProvince = formattedVehicleNumber.substring(0, formattedVehicleNumber.length() - 2);
                String vehicleTypeCaps = responseData.getString("Vehicle_Type").toUpperCase();


                // Bundle response values
                Bundle bundle = new Bundle();
                bundle.putString("SessionID", SessionID);
                bundle.putString("EndTimeStamp", EndTimeStamp);
                bundle.putString("ParkedTimeDate", formattedDate);
                bundle.putString("Duration", Duration);
                bundle.putString("Amount", Amount);
                bundle.putString("vehicleNumberWithoutProvince", vehicleNumberWithoutProvince);
                bundle.putString("vehicleTypeCaps", vehicleTypeCaps);

                // Log the values for debugging
                Log.d("Bundle Values", "Session ID: " + SessionID);
                Log.d("Bundle Values", "End Time Stamp: " + EndTimeStamp);
                Log.d("Bundle Values", "Parked Time/Date: " + formattedDate);
                Log.d("Bundle Values", "Duration: " + Duration);
                Log.d("Bundle Values", "Amount: " + Amount);
                Log.d("Bundle Values", "Vehicle Number Without Province: " + vehicleNumberWithoutProvince);
                Log.d("Bundle Values", "Vehicle Type Caps: " + vehicleTypeCaps);


                // Set the session data bundle in the fragment
                ((ReleaseASlot01Fragment) fragmentManager.findFragmentById(R.id.main_act_frame_layout)).setSearchSessionDataBundle(bundle);


                TextView parkedDateTimeView = view.findViewById(R.id.parked_Date_Time_txt);
                TextView durationView = view.findViewById(R.id.duration_txt);
                TextView amountView = view.findViewById(R.id.amount_txt);

                // Set the values to TextViews
                parkedDateTimeView.setText(formattedDate);
                durationView.setText(Duration);
                amountView.setText(Amount);

                // Enable the continueBtn
                continueBtn.setEnabled(true);
                continueBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryColor));


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