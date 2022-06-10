package com.example.realsensus.util;

import android.content.Context;
import android.util.Log;

import com.example.realsensus.helper.RSPreference;
import com.example.realsensus.model.Citizen;
import com.example.realsensus.model.CitizenDataMaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

public class AppUtil {

    private final String TAG = "AppUtil";
    private final Context context;
    private CitizenDataMaster citizenDataMaster;

    public AppUtil(Context context) {
        this.context = context;
        citizenDataMaster = RSPreference.getInstance(context).loadCitizensDataMaster();
    }

    public void generateCitizensDataFromJson() {
        String jsonFile = readTextFileFromAssets(context, "json/citizen_data.json");
        Type citizensData = new TypeToken<CitizenDataMaster>() {}.getType();
        CitizenDataMaster citizenDataMaster = new Gson().fromJson(jsonFile, citizensData);
        RSPreference.getInstance(context).saveCitizensDataMaster(citizenDataMaster);
        CitizenDataMaster citizenDataMaster1 = RSPreference.getInstance(context).loadCitizensDataMaster();
        Log.d(TAG, "generateCitizensDataFromJson - gson: " + new Gson().toJson(citizenDataMaster1));
    }

    public void addCitizenDataMaster(Citizen citizen) {
        citizenDataMaster.getCitizensData().add(citizen);
        RSPreference.getInstance(context).saveCitizensDataMaster(citizenDataMaster);
        Log.d(TAG, "addCitizenDataMaster - citizens data: " + new Gson().toJson(RSPreference.getInstance(context).loadCitizensDataMaster()));
    }

    public void updateCitizensDataMaster(Citizen citizen) {
        if (citizenDataMaster != null && citizenDataMaster.getCitizensData().size() > 0) {
            for (int i = 0; i < citizenDataMaster.getCitizensData().size(); i++) {
                Citizen tempCitizen = citizenDataMaster.getCitizensData().get(i);
                if (tempCitizen.getFamilyCardId().equalsIgnoreCase(citizen.getFamilyCardId())) {
                    citizenDataMaster.getCitizensData().set(i, citizen);
                    break;
                }
            }
        }
        RSPreference.getInstance(context).saveCitizensDataMaster(citizenDataMaster);
        Log.d(TAG, "addCitizenDataMaster - citizens data: " + new Gson().toJson(RSPreference.getInstance(context).loadCitizensDataMaster()));
    }

    public void deleteCitizen(Citizen citizen) {
        if (citizenDataMaster != null && citizenDataMaster.getCitizensData().size() > 0) {
            for (int i = 0; i < citizenDataMaster.getCitizensData().size(); i++) {
                Citizen tempCitizen = citizenDataMaster.getCitizensData().get(i);
                if (tempCitizen.getFamilyCardId().equalsIgnoreCase(citizen.getFamilyCardId())) {
                    citizenDataMaster.getCitizensData().remove(i);
                    break;
                }
            }
        }
        RSPreference.getInstance(context).saveCitizensDataMaster(citizenDataMaster);
        Log.d(TAG, "addCitizenDataMaster - citizens data: " + new Gson().toJson(RSPreference.getInstance(context).loadCitizensDataMaster()));
    }

    public String readTextFileFromAssets(Context context, String fileName) {
        InputStream is;
        try {
            is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // byte buffer into a string
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }
}
