package com.example.parkngo.parking.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ErrorFragment;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.home.helpers.APSRecycleViewAdapter;
import com.example.parkngo.home.helpers.AvailableParkingSpaceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParkingHelper {

    View loadingView;
    View parkingView;
    View errorView;
    Context context;

    public ParkingHelper(View loadingView, View parkingView, View errorView, Context context){
        this.loadingView = loadingView;
        this.parkingView = parkingView;
        this.errorView = errorView;
        this.context = context;
    }

    public void init(){
        fetchData();
        initSearchBarListener();
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
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                String token = parkngoStorage.getData("token");
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
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

                Bundle data = new Bundle();

                if(response.equals("PS_IPSID")) // PS_IPSID => means no parking available
                {
                    data.putString("MainText1", "No Available Parking Spaces");
                    data.putString("subText1", "Please try again later");
                    data.putInt("img", R.drawable.not_available);
                    data.putString("MainText2", "Parking spaces not available for selected vehicle type!");
                    data.putString("subText2", "Sorry, no parking slots are currently available. Please try again later or consider alternative parking options");
                }else{
                    data.putString("MainText1", "Unknown Error");
                    data.putString("subText1", "Please try again later");
                    data.putInt("img", R.drawable.not_available);
                    data.putString("MainText2", "Unknown error occurred! ");
                    data.putString("subText2", "We sincerely apologize for the inconvenience this has caused.");
                }
                MainActivity mainActivity = (MainActivity) context;

                mainActivity.replaceFragment(new ErrorFragment(), data);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // initializing search bar listener
    private void initSearchBarListener(){
        SearchView searchView = parkingView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // switch back to loading view until the data is get fetched
                ViewGroup parent = (ViewGroup) parkingView.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(parkingView);
                    parent.removeView(parkingView);
                    parent.addView(loadingView, index);
                }
                searchResultFetchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }


    // fetching search result data
    private void searchResultFetchData(String keyword){
        RequestQueue queue = Volley.newRequestQueue(context);
        keyword = keyword.replace(" ", "_");
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/parkingSpace/search_all/" + keyword;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandlerSRD(response);
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
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                return headers;
            }
        };
        queue.add(stringRequest);
    }


    // response-success handler - SRD (Search Result Fetch Data)
    private void successResponseHandlerSRD( String response) {
        try{
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

}
