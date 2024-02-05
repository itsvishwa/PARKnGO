package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.R;
import com.example.officertestapp.Status.PSRecycleViewAdapter;
import com.example.officertestapp.Status.ParkingStatusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchSessionHelper {
    private View view;
    private Context context;
    private FragmentManager fragmentManager;
    private Button continueBtn;

    public SearchSessionHelper(View view, Context context, FragmentManager fragmentManager, Button continueBtn) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.continueBtn = continueBtn;
        searchSession();
    }

    private void searchSession() {
        // getting reference to the views
        EditText lettersEditText = view.findViewById(R.id.vehicle_num_letters);
        EditText digitsEditText = view.findViewById(R.id.vehicle_num_digits);
        Spinner provincesSpinner = view.findViewById(R.id.spinner_provinces);


        if (TextUtils.isEmpty(lettersEditText.getText()) || TextUtils.isEmpty(digitsEditText.getText()) || provincesSpinner.getSelectedItem() == null){
            // Show a toast or some UI indication that input is incomplete
            Toast.makeText(context, "Please fill all fields of Vehicle Number", Toast.LENGTH_SHORT).show();

        }else {
            // Get input data from UI
            String letters = lettersEditText.getText().toString();
            String digits = digitsEditText.getText().toString();
            String province = provincesSpinner.getSelectedItem().toString();

            // Concatenate the values to create the complete vehicle number
            String completeVehicleNumber = letters + digits + province;
            //Toast.makeText(context, "Complete Vehicle Number: " + completeVehicleNumber, Toast.LENGTH_SHORT).show();

            // get the token
            ParkngoStorage parkngoStorage = new ParkngoStorage(context);
            String token = parkngoStorage.getData("token");
            String encodedParkingId = parkngoStorage.getData("parkingID");

            // volley request
            RequestQueue queue = Volley.newRequestQueue(context);

            String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/search/"+ completeVehicleNumber;
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
                    Map<String, String> headers = new HashMap<>();
                    headers.put("token", token);
                    headers.put("encoded-parking-id", encodedParkingId);
                    return headers;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
            Log.d("token", token);
            Log.d("encoded-parking-id", encodedParkingId);
        }
    }

    private void successResponseHandler(String response){
        Log.d("Raw Response", response);
        Toast.makeText(context, "Session Search Successful", Toast.LENGTH_SHORT).show();

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject responseData = jsonResponse.getJSONObject("response");

            // Extract values from the JSON response
            String sessionID = responseData.getString("session_id");
            String parkedTimeDate = responseData.getString("Parked_Time_Date");
            String duration = responseData.getString("Duration");
            String amount = responseData.getString("Amount");
            String vehicleNumber = responseData.getString("Vehicle_Number");
            String vehicleType = responseData.getString("Vehicle_Type");
            String sessionStartedAt = responseData.getString("Session_started_at");
            String endedTimeDate = responseData.getString("Ended_Time_Date");
            String sessionEndedAt = responseData.getString("Session_ended_at");


            // Pass the values to the next fragment using a Bundle
            Bundle bundle = new Bundle();
            bundle.putString("sessionID", sessionID);
            bundle.putString("parkedTimeDate", parkedTimeDate);
            bundle.putString("duration", duration);
            bundle.putString("amount", amount);
            bundle.putString("vehicleNumber", vehicleNumber);
            bundle.putString("vehicleType", vehicleType);
            bundle.putString("sessionStartedAt", sessionStartedAt);
            bundle.putString("endedTimeDate", endedTimeDate);
            bundle.putString("sessionEndedAt", sessionEndedAt);

            // Log the values for debugging
            Log.d("Bundle Values", "Session ID: " + sessionID);
            Log.d("Bundle Values", "Parked Time/Date: " + parkedTimeDate);
            Log.d("Bundle Values", "Duration: " + duration);
            Log.d("Bundle Values", "Amount: " + amount);
            Log.d("Bundle Values", "Vehicle Number: " + vehicleNumber);
            Log.d("Bundle Values", "Vehicle Type: " + vehicleType);
            Log.d("Bundle Values", "Session Started At: " + sessionStartedAt);
            Log.d("Bundle Values", "Ended Time/Date: " + endedTimeDate);
            Log.d("Bundle Values", "Session Ended At: " + sessionEndedAt);


            TextView parkedDateTimeView = view.findViewById(R.id.parked_Date_Time_txt);
            TextView durationView = view.findViewById(R.id.duration_txt);
            TextView amountView = view.findViewById(R.id.amount_txt);



            // Set the values to TextViews
            parkedDateTimeView.setText(parkedTimeDate);
            durationView.setText(duration);
            amountView.setText(amount);

            // Enable the continueBtn
            continueBtn.setEnabled(true);
            continueBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryColor));

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

