package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VehicleDataHelper {
    private Context context;
    private View view;
    private FragmentManager fragmentManager;
    private String driverId;

    public VehicleDataHelper(View view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }


    public Bundle createAssignVehicleDataBundle() {
        // getting reference to the views
        EditText lettersEditText = view.findViewById(R.id.vehicle_num_letters);
        EditText digitsEditText = view.findViewById(R.id.vehicle_num_digits);
        EditText driverIdEditText = view.findViewById(R.id.driver_id_etxt);
        Spinner provincesSpinner = view.findViewById(R.id.spinner_provinces);
        Spinner vehicleTypesSpinner = view.findViewById(R.id.spinner_vehicle_types);
        TextView reserveTimeTextView = view.findViewById(R.id.reserve_time_txt);
        TextView reserveDateTextView = view.findViewById(R.id.reserve_date_txt);

        // Validate views
        if (TextUtils.isEmpty(lettersEditText.getText())
                || TextUtils.isEmpty(digitsEditText.getText())
                || provincesSpinner.getSelectedItem() == null
                || vehicleTypesSpinner.getSelectedItem() == null
                || TextUtils.isEmpty(reserveTimeTextView.getText())
                || TextUtils.isEmpty(reserveDateTextView.getText())) {
            Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            // Return an empty Bundle or handle the failure case as appropriate
            return new Bundle();

        } else {
            // Extract user inputs
            // Get vehicleNumber
            String letters = lettersEditText.getText().toString();
            String digits = digitsEditText.getText().toString();
            String selectedProvince = provincesSpinner.getSelectedItem().toString();
            String vehicleNumber = letters + digits + selectedProvince;
            String vehicleNumberWithoutProvince = letters + " " + digits;

            // Get vehicle type
            String selectedVehicleType = vehicleTypesSpinner.getSelectedItem().toString();
            String selectedVehicleTypeLowercase = selectedVehicleType.toLowerCase();
            String selectedVehicleTypeCaps = vehicleTypesSpinner.getSelectedItem().toString().toUpperCase();

            // Get driverId
            String driverId = driverIdEditText.getText().toString();

            // Get the timestamp
            String reserveDate = reserveDateTextView.getText().toString();
            String reserveTime = reserveTimeTextView.getText().toString();
            // Combine date and time into a single string in the expected format
            String dateTimeString = reserveDate + " " + reserveTime;

            // Calculate timestamp
            long startTimeStamp = calculateTimestamp(dateTimeString);

            // Get the parkingId
            ParkngoStorage parkngoStorage = new ParkngoStorage(context);
            String parkingId = parkngoStorage.getData("parkingID");

            // get the token
            String token = parkngoStorage.getData("token");


            Bundle bundle = new Bundle();
            bundle.putString("token", token);
            bundle.putString("vehicle_number", vehicleNumber);
            bundle.putString("vehicle_type", selectedVehicleTypeLowercase);
            bundle.putString("start_time", String.valueOf(startTimeStamp));
            bundle.putString("parking_id", parkingId);
            bundle.putString("driver_id", driverId);

            bundle.putString("vehicle_number_without_province", vehicleNumberWithoutProvince);
            bundle.putString("vehicle_type_caps", selectedVehicleTypeCaps);

            // Log the values before returning the Bundle
            Log.d("BundleValues", "Vehicle Number: " + vehicleNumber);
            Log.d("BundleValues", "Vehicle Type: " + selectedVehicleTypeLowercase);
            Log.d("BundleValues", "Start Time Stamp: " + startTimeStamp);
            Log.d("BundleValues", "Parking ID: " + parkingId);
            Log.d("BundleValues", "Driver ID: " + driverId);
            Log.d("BundleValues", "vehicle_number_without_province: " + vehicleNumberWithoutProvince);
            Log.d("BundleValues", "vehicle_type_caps: " + selectedVehicleTypeCaps);


            return bundle;
        }
    }


    private long calculateTimestamp(String dateTimeString) {

        long calcStartTimeStamp = 0;

        try {
            // Parse the original dateTimeString with the current format
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm a", Locale.getDefault());
            Date date = originalFormat.parse(dateTimeString);

            // Format the date back to the expected format "yyyy-MM-dd HH:mm"
            SimpleDateFormat expectedFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String formattedDateTimeString = expectedFormat.format(date);

            // Convert the formatted date and time to timestamp
            calcStartTimeStamp = date.getTime() / 1000L; // Convert to seconds

        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the case where parsing fails (invalid date or time format)
            Toast.makeText(context, "Invalid date or time format", Toast.LENGTH_SHORT).show();
        }

        return calcStartTimeStamp;
    }
}
