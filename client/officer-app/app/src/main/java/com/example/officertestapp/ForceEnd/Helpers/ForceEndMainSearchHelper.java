package com.example.officertestapp.ForceEnd.Helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


import androidx.fragment.app.FragmentManager;
import com.example.officertestapp.R;

public class ForceEndMainSearchHelper {
    View forceEndsessionsView;
    Context context;
    FragmentManager fragmentManager;
    View loadingView;

    public ForceEndMainSearchHelper(View forceEndsessionsView, Context context, FragmentManager fragmentManager, View loadingView) {
        this.forceEndsessionsView = forceEndsessionsView;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.loadingView = loadingView;

    }

    public void initSearchBarListener(){
        SearchView searchView = forceEndsessionsView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // switch back to loading view until the data is get fetched
                ViewGroup parent = (ViewGroup) forceEndsessionsView.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(forceEndsessionsView);
                    parent.removeView(forceEndsessionsView);
                    parent.addView(loadingView, index);
                }

                searchResultFetchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void searchResultFetchData(String keyword) {

    }
}
