package com.example.parkngo.parking.helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParkingFetchData {

    View loadingView;
    View parkingView;
    View noAvailableParkingView;
    Context context;

    public ParkingFetchData(View loadingView, View parkingView, View noAvailableParkingView, Context context){
        this.loadingView = loadingView;
        this.parkingView = parkingView;
        this.noAvailableParkingView = noAvailableParkingView;
        this.context = context;
        fetchData();
    }

    private void fetchData(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/parkingSpace/view_all";

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
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // response-success handler
    private void successResponseHandler(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray parkingSpacesDataArr = jsonObject.getJSONArray("response");
            ArrayList<ParkingModel> parkingModels = new ArrayList<>();
            for (int i = 0; i < parkingSpacesDataArr.length(); i++) {
                JSONObject parkingSpaceData = parkingSpacesDataArr.getJSONObject(i);
                int _id = parkingSpaceData.getInt("_id");
                String name = parkingSpaceData.getString("name");
                String address = parkingSpaceData.getString("address");
                String type = parkingSpaceData.getString("is_public").equals("1") ? "Public" : "Customer Only";
                String status = parkingSpaceData.getString("is_closed").equals("1") ? "Closed" :  "Open";
                int avg_star_count = parkingSpaceData.getInt("avg_star_count");
                int total_review_count = parkingSpaceData.getInt("total_review_count");
                parkingModels.add(new ParkingModel(_id, name, type, address, avg_star_count, total_review_count, status));
            }

            // Inflate the parking fragment once data is loaded
            RecyclerView recyclerView = parkingView.findViewById(R.id.parking_frag_recycle_view);
            PMRecycleViewAdapter pmRecycleViewAdapter = new PMRecycleViewAdapter(parkingModels, context);
            recyclerView.setAdapter(pmRecycleViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // Replace the loading view with the parking view
            ViewGroup parent = (ViewGroup) loadingView.getParent();
            if (parent != null) {
                int index = parent.indexOfChild(loadingView);
                parent.removeView(loadingView);
                parent.addView(parkingView, index);
            }
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }

    // response-error handler
    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");
                if(response.equals("N/A")) // "N/A" if there are no parking spaces
                {
                    // Replace the loading view with the parking view
                    ViewGroup parent = (ViewGroup) loadingView.getParent();
                    if (parent != null) {
                        int index = parent.indexOfChild(loadingView);
                        parent.removeView(loadingView);
                        parent.addView(noAvailableParkingView, index);
                    }
                }else{
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
