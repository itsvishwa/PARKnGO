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
import com.example.parkngo.parking.helpers.PMRecycleViewAdapter;
import com.example.parkngo.parking.helpers.ParkingFetchData;
import com.example.parkngo.parking.helpers.ParkingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParkingFragment extends Fragment {
    private View loadingView;
    private View parkingView;
    private View noAvailableParkingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the loading fragment initially
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        parkingView = inflater.inflate(R.layout.fragment_parking, container, false);
        noAvailableParkingView = inflater.inflate(R.layout.fragment_no_available_parking, container, false);

        // Data fetching and processing
        new ParkingFetchData(loadingView, parkingView, noAvailableParkingView, getContext());

        return loadingView;
    }
}
