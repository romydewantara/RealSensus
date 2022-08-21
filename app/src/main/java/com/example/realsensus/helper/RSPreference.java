package com.example.realsensus.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.realsensus.constant.Constant;
import com.example.realsensus.model.Citizen;
import com.example.realsensus.model.CitizenDataMaster;
import com.example.realsensus.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class RSPreference {

    private final static String prefName = "RSPrefs";
    @SuppressLint("StaticFieldLeak")
    private static RSPreference instance = null;
    private static SharedPreferences preferences;

    public static RSPreference getInstance(Context context) {
        if (instance == null) {
            instance = new RSPreference();
            preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        }
        return instance;
    }

    public boolean isSignIn() {
        return getBoolean(Constant.KEY_SIGN_IN);
    }

    public void setSignIn(boolean isSignedIn) {
        putBoolean(Constant.KEY_SIGN_IN, isSignedIn);
    }

    public void setUser(User user) {
        putObject(Constant.KEY_USER, user);
    }

    public Object getUser() {
        return getObject(Constant.KEY_USER, User.class);
    }

    public void addCitizen(Citizen citizen) {
        putObject(Constant.KEY_CITIZEN, citizen);
    }

    public Object getCitizen() {
        return getObject(Constant.KEY_CITIZEN, Citizen.class);
    }

    public void saveCitizensDataMaster(CitizenDataMaster citizenDataMaster) {
        putObject(Constant.KEY_CITIZENS_DATA, citizenDataMaster);
    }

    public CitizenDataMaster loadCitizensDataMaster() {
        return (CitizenDataMaster) getObject(Constant.KEY_CITIZENS_DATA, CitizenDataMaster.class);
    }

    public void storeOCRTextResult(String ocrTextResult) {
        putString(Constant.KEY_OCR_TEXT_RESULT, ocrTextResult);
    }

    public String takeOCRTextResult() {
        return getString(Constant.KEY_OCR_TEXT_RESULT);
    }

    public void saveTempCitizenScanResult(Citizen citizen) {
        putObject(Constant.KEY_CITIZENS_FORM_DATA, citizen);
    }

    public Citizen loadTempCitizensScanResult() {
        return (Citizen) getObject(Constant.KEY_CITIZENS_FORM_DATA, Citizen.class);
    }

    public void logout() {
        setUser(null);
        setSignIn(false);
        //Delete all sharedpref except checksum values
        Map<String, ?> map = getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            if (!key.contains(Constant.KEY_USER)){
                //remove(key);
            }
        }
    }
    public void putObject(String key, Object obj) {
        keyNullChecker(key);
        Gson gson = new Gson();
        putString(key, gson.toJson(obj));
    }

    public Object getObject(String key, Class<?> classOfT) {
        String json = getString(key);
        return new Gson().fromJson(json, classOfT);
    }

    public void putString(String key, String value) {
        keyNullChecker(key);
        valueNullChecker(value);
        preferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value) {
        keyNullChecker(key);
        preferences.edit().putBoolean(key, value).apply();
    }

    public void putListObject(String key, ArrayList<Object> objArrayList) {
        keyNullChecker(key);
        Gson gson = new Gson();
        ArrayList<String> objString = new ArrayList<>();
        for (Object o : objArrayList) {
            objString.add(gson.toJson(o));
        }
        putListString(key, objString);
    }

    public ArrayList<Object> getListObject(String key, Class<?> mClass) {
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(key);
        ArrayList<Object> objects = new ArrayList<Object>();

        for (String jObjString : objStrings) {
            Object value = gson.fromJson(jObjString, mClass);
            objects.add(value);
        }
        return objects;
    }

    public void putListString(String key, ArrayList<String> stringListParam) {
        String[] stringList = stringListParam.toArray(new String[stringListParam.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", stringList)).apply();
    }

    public ArrayList<String> getListString(String key) {
        return new ArrayList<>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    private void keyNullChecker(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    public void valueNullChecker(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }
}
