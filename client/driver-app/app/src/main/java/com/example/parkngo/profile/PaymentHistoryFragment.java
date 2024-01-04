package com.example.parkngo.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.profile.helpers.PHFetchData;
import com.example.parkngo.profile.helpers.PHRecycleViewAdapter;
import com.example.parkngo.profile.helpers.PaymentHistoryModel;

import java.util.ArrayList;


public class PaymentHistoryFragment extends Fragment {

    ArrayList<PaymentHistoryModel> paymentHistoryModels = new ArrayList<>();
    View loadingView;
    View paymentHistoryView;
    View errorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        paymentHistoryView = inflater.inflate(R.layout.fragment_payment_history, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        errorView = inflater.inflate(R.layout.fragment_error, container, false);

        new PHFetchData(paymentHistoryView, loadingView, errorView, getContext(), (MainActivity)requireContext());

        return loadingView;
    }

}