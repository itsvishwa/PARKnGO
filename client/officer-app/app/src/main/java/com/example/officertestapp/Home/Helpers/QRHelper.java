package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class QRHelper {
    private View view;
    private Context context;
    private FragmentManager fragmentManager;

    public QRHelper(View view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        searchSession();
    }

    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                Toast.makeText(context, jsonResponse.getString("response"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
