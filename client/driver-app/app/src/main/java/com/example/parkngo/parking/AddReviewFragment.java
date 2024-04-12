package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.AddReviewData;

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

        // onclick listeners ......................................................................................................
        // getting references to the button
        Button  reviewAddBtn = view.findViewById(R.id.add_review_frag_add_review_btn);
        reviewAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddReviewData(view, parkingID, getContext(), requireActivity().getSupportFragmentManager());
            }
        });
        // onclick listeners ......................................................................................................

        return view;
    }
}