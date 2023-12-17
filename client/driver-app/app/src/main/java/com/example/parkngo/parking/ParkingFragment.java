package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class ParkingFragment extends Fragment {
    private View loadingView;
    private View parkingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the loading fragment initially
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        parkingView = inflater.inflate(R.layout.fragment_parking, container, false);

        // Perform data loading in the background
        fetchDataFromAPI();

        return loadingView;
    }

    private void fetchDataFromAPI() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/parkingSpace/view_all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse JSON response and update UI
                            successResponseHandler(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        String errorResponse;
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorResponse = new String(error.networkResponse.data);
                            try {
                                JSONObject jsonResponse = new JSONObject(errorResponse);
                                errorResponseHandler(jsonResponse.getString("response"));
                                // Handle specific errors
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void successResponseHandler(String response) throws JSONException {
        // Parse JSON response and update UI
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
        PMRecycleViewAdapter pmRecycleViewAdapter = new PMRecycleViewAdapter(parkingModels, requireContext());
        recyclerView.setAdapter(pmRecycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Replace the loading view with the parking view
        ViewGroup parent = (ViewGroup) loadingView.getParent();
        if (parent != null) {
            int index = parent.indexOfChild(loadingView);
            parent.removeView(loadingView);
            parent.addView(parkingView, index);
        }
    }

    private void errorResponseHandler(String response) throws JSONException {
        // TODO:: Handle error response
        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
    }
}
