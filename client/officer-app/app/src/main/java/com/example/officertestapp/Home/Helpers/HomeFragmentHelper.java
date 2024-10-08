package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragmentHelper {
    public static void setTopAppBarDetailsInFragment(View view, Context context) {
        //Create an instance of ParkngoStorage using the Context
        ParkngoStorage parkngoStorage = new ParkngoStorage(context);

        // Get data from storage
        //String firstName = parkngoStorage.getData("firstName");
        String parkingName = parkngoStorage.getData("parkingName");

        // Find the TextViews by their IDs
        //TextView officerNameTextView = view.findViewById(R.id.home_frag_officer_name);
        TextView parkingNameView = view.findViewById(R.id.home_frag_parking_name);

        // Set the officer's name and parking name in the TextViews
        //officerNameTextView.setText(firstName);
        parkingNameView.setText(parkingName);
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
