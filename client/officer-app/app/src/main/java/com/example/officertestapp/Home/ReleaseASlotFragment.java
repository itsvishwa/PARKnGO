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
import com.example.officertestapp.Home.Helpers.QREndSessionDetailsHelper;
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
    private Bundle searchSessionDataBundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_release_a_slot, container, false);

        // Find the view
        spinnerProvinces = view.findViewById(R.id.spinner_provinces);
        spinnerSymbols = view.findViewById(R.id.spinner_symbols);

        continueBtn = view.findViewById(R.id.release_vehicle_continue_btn);
        continueBtn.setEnabled(false);

        //Initialize spinner
        initializeSpinners();

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        // Initialize barLauncher
        ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                // Process the scanned QR code content using QRHelper
                QREndSessionDetailsHelper qREndSessionDetailsHelper = new QREndSessionDetailsHelper(getView(), requireContext(), requireActivity().getSupportFragmentManager());
                qREndSessionDetailsHelper.processQRCode(result.getContents());
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
                new SearchSessionHelper(view, getContext(), requireActivity().getSupportFragmentManager(), continueBtn);
            }
        });

        // Handle continue button click
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the button is enabled before processing
                if (continueBtn.isEnabled()) {
                    // Continue button is enabled

                    // Check if the Bundle is not empty
                    if (!searchSessionDataBundle.isEmpty()) {
                        // Navigate to ReleaseASlot02Fragment with the Bundle
                        ReleaseASlotConfirmationFragment releaseASlotConfirmationFragment = new ReleaseASlotConfirmationFragment();
                        ((MainActivity) requireActivity()).replaceFragment(releaseASlotConfirmationFragment, searchSessionDataBundle, getView());
                    }

                } else {
                    // Continue button is disabled, show a message indicating it's disabled
                    Toast.makeText(getContext(), "Continue button is disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void initializeSpinners() {
        // Province spinner
        ArrayList<String> provinceTypes = new ArrayList<>(Arrays.asList("WP", "SP", "CP", "EP", "NC", "NP", "NW", "SG", "UP", "NONE"));
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, provinceTypes);
        provinceAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerProvinces.setAdapter(provinceAdapter);


        // Symbols spinner
        ArrayList<String> symbols = new ArrayList<>(Arrays.asList("ශ්\u200Dරී", "-", "NONE"));
        ArrayAdapter<String> symbolsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, symbols);
        symbolsAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerSymbols.setAdapter(symbolsAdapter);

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

        spinnerSymbols.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    // Method to set the session data bundle
    public void setSearchSessionDataBundle(Bundle bundle) {
        searchSessionDataBundle = bundle;
    }

}