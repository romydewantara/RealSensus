package com.example.bacodelabs.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

public class SmallDB {

    private SharedPreferences preferences;

    public SmallDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    /**
     * Put String value into SharedPreferences with 'key' and save
     * @param key SharedPreferences key
     * @param value String value to be added
     */
    public void putString(String key, String value) {
        checkForNullKey(key); checkForNullValue(value);
        preferences.edit().putString(key, value).apply();
    }
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void checkForNullKey(String key){
        if (key == null){
            throw new NullPointerException();
        }
    }

    public void checkForNullValue(String value){
        if (value == null){
            throw new NullPointerException();
        }
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }
}
