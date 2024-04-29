package com.example.officertestapp.Home.Helpers;

import android.content.Context;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentManager;


import com.example.officertestapp.Helpers.VehicleNumberHelper;
import com.example.officertestapp.Home.AssignAVehicleConfirmationFragment;
import com.example.officertestapp.Home.CaptureAct;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.Locale;

public class AssignAVehicleAddDetailsHelper {
    View assignAVehicleAddDetailsView;
    Context context;
    FragmentManager fragmentManager;
    private ActivityResultLauncher<ScanOptions> barLauncher;

    public AssignAVehicleAddDetailsHelper(View assignAVehicleAddDetailsView, Context context, FragmentManager fragmentManager, ActivityResultLauncher<ScanOptions> barLauncher) {
        this.assignAVehicleAddDetailsView = assignAVehicleAddDetailsView;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.barLauncher = barLauncher;
    }

    public void initializeSpinners() {
        // Province spinner
        Spinner spinnerProvinces = assignAVehicleAddDetailsView.findViewById(R.id.spinner_provinces);
        ArrayList<String> provinceTypes = new ArrayList<>(Arrays.asList("WP", "SP", "CP", "EP", "NC", "NP", "NW", "SG", "UP", "NONE"));
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, provinceTypes);
        provinceAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerProvinces.setAdapter(provinceAdapter);

        // Vehicle type spinner
        Spinner spinnerVehicleTypes = assignAVehicleAddDetailsView.findViewById(R.id.spinner_vehicle_types);
        ArrayList<String> vehicleTypes = new ArrayList<>(Arrays.asList("Car", "Tuktuk", "Bicycle", "Mini Van", "Van", "Lorry", "Mini Bus", "Long Vehicles"));
        ArrayAdapter<String> vTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, vehicleTypes);
        vTypeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerVehicleTypes.setAdapter(vTypeAdapter);

        // Province spinner
        Spinner spinnerSymbols = assignAVehicleAddDetailsView.findViewById(R.id.spinner_symbols);
        ArrayList<String> symbols = new ArrayList<>(Arrays.asList("ශ්\u200Dරී", "-", "NONE"));
        ArrayAdapter<String> symbolsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, symbols);
        symbolsAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerSymbols.setAdapter(symbolsAdapter);

        // Spinner item selected listeners
        spinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(context, item + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerVehicleTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(context, item + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerSymbols.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(context, item + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }



    public void initReserveTheSlotBtnListener() {
        Button reserveSlotBtn = assignAVehicleAddDetailsView.findViewById(R.id.frag_home_assign_vehicle01_reserve_slot_btn);

        reserveSlotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting reference to the views
                EditText lettersEditTextView = assignAVehicleAddDetailsView.findViewById(R.id.vehicle_num_letters);
                EditText digitsEditTextView = assignAVehicleAddDetailsView.findViewById(R.id.vehicle_num_digits);
                EditText driverIdEditTextView = assignAVehicleAddDetailsView.findViewById(R.id.driver_id_etxt);

                Spinner symbolsSpinnerView = assignAVehicleAddDetailsView.findViewById(R.id.spinner_symbols);
                Spinner provincesSpinnerView = assignAVehicleAddDetailsView.findViewById(R.id.spinner_provinces);
                Spinner vehicleTypesSpinnerView = assignAVehicleAddDetailsView.findViewById(R.id.spinner_vehicle_types);

                TextView reserveTimeTextView = assignAVehicleAddDetailsView.findViewById(R.id.reserve_time_txt);
                TextView reserveDateTextView = assignAVehicleAddDetailsView.findViewById(R.id.reserve_date_txt);

                String digits = digitsEditTextView.getText().toString();

                // Validate views
                if (TextUtils.isEmpty(lettersEditTextView.getText())
                        || TextUtils.isEmpty(digitsEditTextView.getText())
                        || provincesSpinnerView.getSelectedItem() == null
                        || vehicleTypesSpinnerView.getSelectedItem() == null
                        || TextUtils.isEmpty(reserveTimeTextView.getText())
                        || TextUtils.isEmpty(reserveDateTextView.getText())) {
                    Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show();// Check if digitsEditTextView contains exactly 4 values


                }
                else if (digits.length() != 4) {
                        Toast.makeText(context, "Digits must be exactly 4 characters long!", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Extract user inputs
                    String letters = lettersEditTextView.getText().toString().toUpperCase();
                    String selectedSymbol = symbolsSpinnerView.getSelectedItem().toString();
                    digits = digitsEditTextView.getText().toString();
                    String selectedProvince = provincesSpinnerView.getSelectedItem().toString();

                    String vehicleNumber = letters + selectedSymbol + digits + selectedProvince;
                    String originalVehicleNumber = letters + "#" + selectedSymbol + "#" +digits + "#" + selectedProvince;

                    // Preprocess vehicle number
                    String preprocessVehicleNumber = VehicleNumberHelper.preprocessVehicleNumber(originalVehicleNumber);

                    // Get vehicle type
                    String selectedVehicleType = vehicleTypesSpinnerView.getSelectedItem().toString();
                    String selectedVehicleTypeProcessed = VehicleNumberHelper.vehicleTypeToProcessed(selectedVehicleType);

                    // Get driverId
                    String driverId = driverIdEditTextView.getText().toString();


                    // Bundle the values
                    Bundle bundle = new Bundle();
                    bundle.putString("vehicle_number", vehicleNumber);
                    bundle.putString("vehicle_number_processed", preprocessVehicleNumber);
                    bundle.putString("vehicle_type", selectedVehicleTypeProcessed);
                    bundle.putString("driver_id", driverId);


                    // Log the values for debugging
                    Log.d("Bundle Values", "Vehicle Number: " + vehicleNumber);
                    Log.d("Bundle Values", "Vehicle Number Processed: " + preprocessVehicleNumber);
                    Log.d("Bundle Values", "Vehicle Type: " + selectedVehicleTypeProcessed);
                    Log.d("Bundle Values", "Driver ID: " + driverId);


                    // Send Bundle to AssignAVehicleConfirmationFragment
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.replaceFragment(new AssignAVehicleConfirmationFragment(), bundle, view);
                }

            }
        });

    }


    public void initScanTheQRBtnListener() {
        Button scanTheQRBtn = assignAVehicleAddDetailsView.findViewById(R.id.scan_qr_btn);

        scanTheQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanOptions options = new ScanOptions();
                options.setPrompt("Scan the Driver's QR code here");
                options.setBeepEnabled(true);
                options.setOrientationLocked(true);
                options.setCaptureActivity(CaptureAct.class);
                barLauncher.launch(options);
            }
        });
    }


}

