package com.example.officertestapp.Status;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.officertestapp.R;
import com.example.officertestapp.Status.Helpers.StatusFetchData;
import com.example.officertestapp.Status.Helpers.StatusMainSpinner;

import java.util.ArrayList;

public class StatusMainFragment extends Fragment {


    ArrayList<ParkingStatusModel> parkingStatusModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_main, container, false);
        View loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        // init spinners
        new StatusMainSpinner(view, getContext());
        new StatusFetchData(getContext(),view, loadingView);

        return loadingView;
    }
}