package com.example.parkngo.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;

import java.util.ArrayList;


public class PaymentHistoryFragment extends Fragment {

    ArrayList<PaymentHistoryModel> paymentHistoryModels = new ArrayList<>();

    public PaymentHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_history, container, false);

        // set the models array
        setupPaymentHistoryModels();

        // get reference to the recycle view
        RecyclerView recyclerView = view.findViewById(R.id.payment_frag_recycle_view);

        PHRecycleViewAdapter adapter = new PHRecycleViewAdapter(getContext(), paymentHistoryModels);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void setupPaymentHistoryModels(){
        String[] dateTimes = {"10.30 AM | 04 July", "10.40 AM | 10 July", "8.30 AM | 14 July", "10.20 AM | 09 July", "08.30 AM | 09 July"};
        String[] amount = {"425.00", "300.00", "970.00", "150.00", "200.00"};
        String[] duration = {"03 Hours 25 Minutes", "03 Hours 25 Minutes", "03 Hours 25 Minutes", "03 Hours 25 Minutes", "03 Hours 25 Minutes"};
        String[] vehicleType = {"Car", "Van", "Bike", "Car", "Car"};

        for(int i=0; i<dateTimes.length; i++){
            paymentHistoryModels.add(new PaymentHistoryModel(dateTimes[i], amount[i], duration[i], vehicleType[i]));
        }

    }
}