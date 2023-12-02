package com.example.parkngo.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class ParkngoStorage {

    SharedPreferences preferencesObject;
    SharedPreferences.Editor editorObject;

    public ParkngoStorage(Context context){
        preferencesObject = context.getSharedPreferences("session_data.xml", Context.MODE_PRIVATE);
        editorObject = preferencesObject.edit();
    }

    public void addData(String[][] dataArr){
        for (String[] datum : dataArr) {
            editorObject.putString(datum[0], datum[1]);
        }
        editorObject.apply();
    }

    public String getData(String key){
        return preferencesObject.getString(key, "empty");
    }

    public void clearData() {
        editorObject.clear();
        editorObject.apply();
    }
}
