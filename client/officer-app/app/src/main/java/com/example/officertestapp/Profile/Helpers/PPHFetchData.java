package com.example.officertestapp.Profile.Helpers;

import android.content.Context;
import android.content.Intent;
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
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.HeroActivity;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PPHFetchData {
    View view;
    View loadingView;
    Context context;
    MainActivity mainActivity;

    public PPHFetchData(View view, View loadingView, Context context, MainActivity mainActivity) {
        this.view = view;
        this.loadingView = loadingView;
        this.context = context;
        this.mainActivity = mainActivity;
        fetchData();
    }

    public void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(context);

        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/profile/officer_payment_history";
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
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray resultDataArr = jsonObject.getJSONArray("response");

            ArrayList<PaymentProfileModel> paymentHistoryModels = new ArrayList<>();

            for(int i=0; i<resultDataArr.length(); i++) {
                JSONObject resultData = resultDataArr.getJSONObject(i);
                String dateTime = resultData.getString("Date_and_Time");
                String amount = resultData.getString("Amount");
                String vehicle = resultData.getString("Vehicle");
                String paymentMethod = resultData.getString("Payment_Method");

                paymentHistoryModels.add(new PaymentProfileModel(dateTime, amount, vehicle, paymentMethod));

                // setting up the available parking spaces recycle view
                RecyclerView recyclerView = view.findViewById(R.id.profile_payment_frag_recycle_view);
                PPRecycleViewAdapter adapter = new PPRecycleViewAdapter(paymentHistoryModels ,context);
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
