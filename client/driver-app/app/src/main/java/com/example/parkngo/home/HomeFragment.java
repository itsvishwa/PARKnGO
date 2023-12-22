package com.example.parkngo.home;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ParkngoStorage parkngoStorage;
    String first_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        parkngoStorage = new ParkngoStorage(getContext());
        first_name = parkngoStorage.getData("firstName");

        // spinner code start................................................................
        Spinner spinner = view.findViewById(R.id.home_frag_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getContext(), item, Toast.LENGTH_LONG).show();
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, vehicleTypes);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        // spinner code end................................................................


        // set the pulse animation for constraint layout
        ConstraintLayout constraintLayout = view.findViewById(R.id.home_frag_cons_layout);
        Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.anim_pulse);
        constraintLayout.startAnimation(pulse);

        // replace the user name
        TextView nameView = view.findViewById(R.id.home_frag_user_name);
        first_name = Character.toUpperCase(first_name.charAt(0)) + first_name.substring(1);
        nameView.setText(first_name);

        return view;
    }
}
