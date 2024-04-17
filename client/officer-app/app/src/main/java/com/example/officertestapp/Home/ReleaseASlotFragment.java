package com.example.officertestapp.Home;

import android.os.Bundle;

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
import com.example.officertestapp.Home.Helpers.SearchSessionHelper;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ReleaseASlotFragment extends Fragment {
    private Spinner spinnerProvinces;
    private Button continueBtn;
    private Bundle searchSessionDataBundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_release_a_slot, container, false);

        // Find the view
        spinnerProvinces = view.findViewById(R.id.spinner_provinces);

        continueBtn = view.findViewById(R.id.release_vehicle_01_continue_btn);
        continueBtn.setEnabled(false);

        //Initialize spinner
        initializeSpinner();

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        // Handle search button click
        Button searchBtn = view.findViewById(R.id.release_vehicle_01_search_btn);
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
                        ReleaseASlot02Fragment releaseASlot02Fragment = new ReleaseASlot02Fragment();
                        ((MainActivity) requireActivity()).replaceFragment(releaseASlot02Fragment, searchSessionDataBundle, getView());
                    }

                } else {
                    // Continue button is disabled, show a message indicating it's disabled
                    Toast.makeText(getContext(), "Continue button is disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void initializeSpinner() {
        // Province spinner
        ArrayList<String> provinceTypes = new ArrayList<>(Arrays.asList("WP", "SP", "CP", "EP", "NC", "NP", "NW", "SG", "UP"));
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, provinceTypes);
        provinceAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerProvinces.setAdapter(provinceAdapter);


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
    }

    // Method to set the session data bundle
    public void setSearchSessionDataBundle(Bundle bundle) {
        searchSessionDataBundle = bundle;
    }

}

