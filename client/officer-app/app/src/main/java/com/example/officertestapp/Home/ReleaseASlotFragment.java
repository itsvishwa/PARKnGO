package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.InProgressDetailsHelper;
import com.example.officertestapp.Home.Helpers.SearchSessionHelper;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;

public class ReleaseASlotFragment extends Fragment {
    private Spinner spinnerProvinces;
    private Spinner spinnerSymbols;
    private Button continueBtn;
    String sessionID;
    ArrayList<String> provinceTypes;
    ArrayList<String> symbols;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_release_a_slot, container, false);

        continueBtn = view.findViewById(R.id.release_vehicle_continue_btn);
        continueBtn.setEnabled(false);

        // Find the view
        spinnerProvinces = view.findViewById(R.id.spinner_provinces);
        spinnerSymbols = view.findViewById(R.id.spinner_symbols);

        provinceTypes = new ArrayList<>(Arrays.asList("WP", "SP", "CP", "EP", "NC", "NP", "NW", "SG", "UP", "NONE"));

        symbols = new ArrayList<>(Arrays.asList("ශ්\u200Dරී", "-", "NONE"));

        if (getArguments() != null) {
            sessionID = getArguments().getString("_id", "-1");
            // Call the InProgressDetailsHelper here
            InProgressDetailsHelper inProgressDetailsHelper = new InProgressDetailsHelper(view, getContext(), continueBtn, spinnerProvinces, spinnerSymbols, provinceTypes, symbols);
            inProgressDetailsHelper.initLayout(sessionID);
        }

        //Initialize spinner
        initializeSpinners();

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        // Initialize barLauncher
        ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Log.d("QR Content", "qr content " + result.getContents());
                // Process the scanned QR code content using QRHelper
                InProgressDetailsHelper inProgressDetailsHelper = new InProgressDetailsHelper(view, getContext(), continueBtn, spinnerProvinces, spinnerSymbols, provinceTypes, symbols);
                inProgressDetailsHelper.initLayout(result.getContents());
            }
        });

        // Handle scan QR button click
        Button scanQRBtn = view.findViewById(R.id.release_slot_scan_qr_btn);
        scanQRBtn.setOnClickListener(new View.OnClickListener() {
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

        // Handle search button click
        Button searchBtn = view.findViewById(R.id.release_vehicle_search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SearchButtonClicked", "Search button is clicked");
                SearchSessionHelper searchSessionHelper = new SearchSessionHelper(view, getContext(), requireActivity().getSupportFragmentManager(), continueBtn);
                searchSessionHelper.searchSession();

                new SearchSessionHelper(view, getContext(), requireActivity().getSupportFragmentManager(), continueBtn);
            }
        });

        return view;
    }

    private void initializeSpinners() {
        // Province spinner
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, provinceTypes);
        provinceAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerProvinces.setAdapter(provinceAdapter);


        // Symbols spinner
        ArrayAdapter<String> symbolsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, symbols);
        symbolsAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerSymbols.setAdapter(symbolsAdapter);

        // Spinner item selected listeners
        spinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerSymbols.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
}