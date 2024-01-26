package com.example.officertestapp.Home;


import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

public class AssignVehicle01Fragment extends Fragment {

    private Spinner spinnerProvinces;
    private Spinner spinnerSlots;
    private Button btn_scan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assign_vehicle01, container, false);

        btn_scan = view.findViewById(R.id.scan_qr_btn);
        btn_scan.setOnClickListener(clikedView ->
        {
            scanCode();
        });

        spinnerProvinces = view.findViewById(R.id.spinner_provinces);
        spinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), item + "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        ArrayList<String> provinceTypes = new ArrayList<>();
        String[] pTypes = {"WP", "SP", "CP", "EP", "NC", "NP", "NW", "SG", "UP"};

        for (int i=0; i<pTypes.length; i++){
            provinceTypes.add(pTypes[i]);
        }

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, provinceTypes);
        provinceAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerProvinces.setAdapter(provinceAdapter);


        spinnerSlots = view.findViewById(R.id.spinner_vehicle_types);
        spinnerSlots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), item + "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        ArrayList<String> vehicleTypes = new ArrayList<>();
        String[] vTypes = {"Car", "Bike", "Van", "Lorry", "Bus"};

        for (int i=0; i<vTypes.length; i++){
            vehicleTypes.add(vTypes[i]);
        }

        ArrayAdapter<String> vTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, vehicleTypes);
        vTypeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerSlots.setAdapter(vTypeAdapter);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        return view;
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

    // Initialize barLauncher here
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
        }
    });
}

