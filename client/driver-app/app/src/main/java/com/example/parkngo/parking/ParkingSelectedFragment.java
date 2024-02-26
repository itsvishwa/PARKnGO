package com.example.parkngo.parking;

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
        // onclick listeners ......................................................................................................


        return loadingView;
    }
}