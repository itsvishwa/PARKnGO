package com.example.officertestapp.Profile.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.R;

public class ProfileFragmentHelper {
    public static void setTopAppBarDetailsInFragment(View view, Context context) {
        //Create an instance of ParkngoStorage using the Context
        ParkngoStorage parkngoStorage = new ParkngoStorage(context);

        // Get data from storage
        String parkingName = parkngoStorage.getData("parkingName");

        // Find the TextViews by their IDs
        TextView parkingNameView = view.findViewById(R.id.profile_frag_app_bar_parking_name);

        // Set the officer's name and parking name in the TextViews
        parkingNameView.setText(parkingName);
    }
}
