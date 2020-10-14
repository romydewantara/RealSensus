package com.example.bacodelabs.viewmodel;

import android.content.Context;
import android.util.Log;

import com.example.bacodelabs.model.Developer;
import com.example.bacodelabs.util.AppUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DataViewModel {

    private Context context;
    private Developer developer;
    private AppUtil appUtil;

    public DataViewModel(Context context) {
        this.context = context;
        appUtil = new AppUtil();
    }

    public Developer getDeveloper() {
        String jsonFile = appUtil.readTextFileFromAssets(context, "json/developers.json");
        Type listData = new TypeToken<Developer>() {}.getType();
        developer = new Gson().fromJson(jsonFile, listData);
        Log.d("Planet", "getJsonData - mainProgress: " + new Gson().toJson(developer));
        return developer;
    }
}
