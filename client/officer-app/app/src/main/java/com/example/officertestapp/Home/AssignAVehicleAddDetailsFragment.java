package com.example.officertestapp.Home;


import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;


import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.officertestapp.Home.Helpers.AssignAVehicleAddDetailsHelper;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.QRHelper;
import com.example.officertestapp.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class AssignAVehicleAddDetailsFragment extends Fragment {
    private TextView reserveDateTxt;
    private TextView reserveTimeTxt;
    private Handler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View assignAVehicleAddDetailsView = inflater.inflate(R.layout.fragment_assign_a_vehicle_add_details, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(assignAVehicleAddDetailsView, requireContext());

        // Find the TextViews for date and time
        reserveDateTxt = assignAVehicleAddDetailsView.findViewById(R.id.reserve_date_txt);
        reserveTimeTxt = assignAVehicleAddDetailsView.findViewById(R.id.reserve_time_txt);

        // Set the initial date and time
        updateDateTime();

        // Start updating date and time every second
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(dateTimeUpdater, 1000);


        // Initialize barLauncher
        ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                // Process the scanned QR code content using QRHelper
                QRHelper qrHelper = new QRHelper(getView(), requireContext(), requireActivity().getSupportFragmentManager());
                qrHelper.processQRCode(result.getContents());
            }
        });


        // Helper Class
        AssignAVehicleAddDetailsHelper assignAVehicleAddDetailsHelper = new AssignAVehicleAddDetailsHelper(assignAVehicleAddDetailsView, getContext(),requireActivity().getSupportFragmentManager(), barLauncher);

        // Initialize spinners and adapters
        assignAVehicleAddDetailsHelper.initializeSpinners();

        // Handle scan QR button click
        assignAVehicleAddDetailsHelper.initScanTheQRBtnListener();

        // Handle Reserve the slot button click
        assignAVehicleAddDetailsHelper.initReserveTheSlotBtnListener();


        return assignAVehicleAddDetailsView;
    }

    // Runnable to update date and time
    private Runnable dateTimeUpdater = new Runnable() {
        @Override
        public void run() {
            updateDateTime();
            handler.postDelayed(this, 1000); // Schedule the next update after 1 second
        }
    };

    private void updateDateTime() {
        reserveDateTxt.setText(HomeFragmentHelper.getCurrentDate());
        reserveTimeTxt.setText(HomeFragmentHelper.getCurrentTime());
    }

    public void onDestroyView() {
        super.onDestroyView();
        // Stop the handler when the fragment is destroyed
        if (handler != null) {
            handler.removeCallbacks(dateTimeUpdater);
        }
    }
}

