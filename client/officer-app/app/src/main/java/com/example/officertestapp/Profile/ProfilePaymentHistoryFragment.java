package com.example.officertestapp.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.officertestapp.R;

import java.util.ArrayList;

public class ProfilePaymentHistoryFragment extends Fragment {

    ArrayList<PaymentProfileModel> paymentProfileModels = new ArrayList<>();

    public ProfilePaymentHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_payment_history, container, false);

        setupPaymentProfileModels();

        RecyclerView recyclerView = view.findViewById(R.id.profile_payment_frag_recycle_view);

        PPRecycleViewAdapter adapter = new PPRecycleViewAdapter(paymentProfileModels, getContext());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void setupPaymentProfileModels() {
        String[] dateTimes = {"10.30 AM | 04 July", "10.40 AM | 10 July", "8.30 AM | 14 July", "10.20 AM | 09 July", "08.30 AM | 09 July"};

        String[] amounts = {"425.00", "300.00", "970.00", "150.00", "200.00"};

        String[] vehicleNums = {"ABC 1050 WP", "BCD 2050 SP", "ADC 3050 WP", "CBC 4050 WP", "BBC 5050 WP"};

        String[] paymentMethods = {"CASH", "CASH", "CASH", "CASH", "CASH"};

        for (int i = 0; i < dateTimes.length ; i++) {
            paymentProfileModels.add(new PaymentProfileModel(dateTimes[i], amounts[i], vehicleNums[i], paymentMethods[i]));
        }
    }
}