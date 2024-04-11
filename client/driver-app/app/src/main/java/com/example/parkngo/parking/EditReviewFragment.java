package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.EditReviewData;

public class EditReviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View editReviewView =  inflater.inflate(R.layout.fragment_edit_review, container, false);
        return editReviewView;
    }
}