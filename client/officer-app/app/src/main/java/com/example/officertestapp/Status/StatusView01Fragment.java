<<<<<<<< HEAD:client/officer-app/app/src/main/java/com/example/officertestapp/Status/StatusView01Fragment.java
package com.example.officertestapp.Status;
========
package com.example.officertestapp.status;
>>>>>>>> 22d497590bee195024bce84735caf13fa6e7079a:client/officer-app/app/src/main/java/com/example/officertestapp/status/StatusView01Fragment.java

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.officertestapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatusView01Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusView01Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatusView01Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatusView01Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusView01Fragment newInstance(String param1, String param2) {
        StatusView01Fragment fragment = new StatusView01Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_view01, container, false);
    }
}