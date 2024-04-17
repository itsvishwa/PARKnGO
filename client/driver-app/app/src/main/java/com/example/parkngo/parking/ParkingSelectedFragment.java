package com.example.parkngo.parking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.ParkingSelectedFetchData;

public class ParkingSelectedFragment extends Fragment {

    private View parkingSelectedView;
    private View loadingView;
    private String parkingID;
    private String userReviewId;

    MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get main activity context
        mainActivity = (MainActivity)requireContext();

        // Inflate the layout for this fragment
        parkingSelectedView =  inflater.inflate(R.layout.fragment_parking_selected, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        // Retrieve data from arguments
        if (getArguments() != null) {
            parkingID = getArguments().getString("parkingID");
        }

        // Perform data loading in the background
        ParkingSelectedFetchData parkingSelectedFetchData = new ParkingSelectedFetchData(parkingSelectedView, loadingView, parkingID, getContext());


        // onclick listeners ......................................................................................................
        //set add review btn handler
        Button addReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_add_review_btn);
        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("parkingID", parkingID);
                mainActivity.replaceFragment(new AddReviewFragment(), data);
            }
        }
        );

        // set delete review btn handler
        Button deleteReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_delete_review_btn);
        deleteReviewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bundle data = new Bundle();
                data.putString("_id", parkingSelectedFetchData.getUserReviewId());

                mainActivity.replaceFragment(new DeleteReviewFragment(), data);
            }
        });

        // set edit review btn handler
        Button editReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_edit_review_btn);
        editReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("_id", parkingSelectedFetchData.getUserReviewId());
                data.putString("content", parkingSelectedFetchData.getUserReviewContent());
                data.putInt("rating", parkingSelectedFetchData.getUserReviewRating());

                mainActivity.replaceFragment(new EditReviewFragment(), data);
            }
        });


        Button navigateBtn  = parkingSelectedView.findViewById(R.id.parking_selected_fragment_navigate_btn);
        navigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = parkingSelectedFetchData.getLatitude();
                String longitude = parkingSelectedFetchData.getLongitude();

                double sourceLatitude = 6.902727395785716;
                double sourceLongitude = 79.86126018417747;
                double destinationLatitude = Double.parseDouble(latitude);
                double destinationLongitude = Double.parseDouble(longitude);

                String uri = "https://www.google.com/maps/dir/?api=1&origin=" + sourceLatitude + "," + sourceLongitude +
                        "&destination=" + destinationLatitude + "," + destinationLongitude;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.startActivity(intent);
            }
        });
        // onclick listeners ......................................................................................................


        return loadingView;
    }
}