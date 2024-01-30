package com.example.officertestapp.Home;


import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.officertestapp.Home.Helpers.AddVehicleDetails;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.VehicleDataHelper;
import com.example.officertestapp.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;

public class AssignVehicle01Fragment extends Fragment {

    private TextView reserveDateTxt;
    private TextView reserveTimeTxt;

    private Handler handler;

    private Spinner spinnerProvinces;
    private Spinner spinnerSlots;
    private Button btn_scan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assign_vehicle01, container, false);

        btn_scan = view.findViewById(R.id.scan_qr_btn);
        btn_scan.setOnClickListener(clikedView -> scanCode());

        spinnerProvinces = view.findViewById(R.id.spinner_provinces);
        spinnerSlots = view.findViewById(R.id.spinner_vehicle_types);

        // Initialize spinners and adapters
        initializeSpinners();

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        // Find the TextViews for date and time
        reserveDateTxt = view.findViewById(R.id.reserve_date_txt);
        reserveTimeTxt = view.findViewById(R.id.reserve_time_txt);

        // Set the initial date and time
        updateDateTime();

        // Start updating date and time every second
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(dateTimeUpdater, 1000);


        // Handle reserve slot button click
        Button reserveSlotBtn = view.findViewById(R.id.frag_home_assign_vehicle01_reserve_slot_btn);
        reserveSlotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of VehicleDataHelper
                VehicleDataHelper vehicleDataHelper = new VehicleDataHelper(getView(), getContext(), getParentFragmentManager());

                // Get the Bundle from the VehicleDataHelper
                Bundle vehicleDataBundle = vehicleDataHelper.createAssignVehicleDataBundle();

                // Check if the Bundle is not empty
                if (!vehicleDataBundle.isEmpty()) {
                    // Navigate to AssignVehicle02Fragment with the Bundle
                    AssignVehicle02Fragment assignVehicle02Fragment = new AssignVehicle02Fragment();
                    assignVehicle02Fragment.setArguments(vehicleDataBundle);

                    // Navigate to AssignVehicle02Fragment
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_act_frame_layout, assignVehicle02Fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });


        return view;
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


    private void initializeSpinners() {
        // Province spinner
        ArrayList<String> provinceTypes = new ArrayList<>(Arrays.asList("WP", "SP", "CP", "EP", "NC", "NP", "NW", "SG", "UP"));
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, provinceTypes);
        provinceAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerProvinces.setAdapter(provinceAdapter);

        // Vehicle type spinner
        ArrayList<String> vehicleTypes = new ArrayList<>(Arrays.asList("Car", "Bike", "Van", "Lorry", "Bus"));
        ArrayAdapter<String> vTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, vehicleTypes);
        vTypeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerSlots.setAdapter(vTypeAdapter);

        // Spinner item selected listeners
        spinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), item + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerSlots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), item + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }


    private void scanCode() {
        //Toast.makeText(getContext(), "Scanning QR Code...", Toast.LENGTH_SHORT).show();
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan the Driver's QR code here");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }


    // Initialize barLauncher
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
        }
    });
}

