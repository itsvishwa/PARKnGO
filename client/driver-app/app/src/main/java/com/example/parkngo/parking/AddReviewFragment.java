package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.AddReviewHelper;

public class AddReviewFragment extends Fragment {
    String parkingID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_review, container, false);

        if (getArguments() != null) {
            parkingID = getArguments().getString("parkingID");
        }

        AddReviewHelper addReviewHelper = new AddReviewHelper(view, parkingID, getContext(), requireActivity().getSupportFragmentManager());
        addReviewHelper.init();

        return view;
    }
}