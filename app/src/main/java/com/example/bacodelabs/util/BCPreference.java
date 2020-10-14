package com.example.bacodelabs.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by: kamikaze
 * on October, 12 2020
 * */

public class BCPreference {

    private static BCPreference instance = null;
    private static SharedPreferences sharedPreferences;
    private final static String preferenceName = "BCPreference";

    // final key
    private final static String KEY_LOGGED_IN = "logged_in";
    private final static String KEY_USERNAME = "key_username";
    private final static String KEY_PASSWORD = "key_password";
    private final static String DEVELOPER_ID = "developer_id";
    private final static String DEVELOPER_NAME = "developer_name";
    private final static String DEVELOPER_PROJECT = "developer_project";

    public static BCPreference getInstance(Context context) {
        if (instance == null) {
            instance = new BCPreference();
            sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        }
        return instance;
    }

    public static int getDeveloperId(Context context) {
        return sharedPreferences.getInt(DEVELOPER_ID, 0);
    }

    public static void setDeveloperId(int developerId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DEVELOPER_ID, developerId);
        editor.apply();
    }

    public static String getDeveloperName(Context context) {
        return sharedPreferences.getString(DEVELOPER_NAME, "");
    }

    public static void setDeveloperName(String developerName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEVELOPER_NAME, developerName);
        editor.apply();
    }

    public static String getDeveloperProject(Context context) {
        return sharedPreferences.getString(DEVELOPER_PROJECT, "");
    }

    public static void setDeveloperProject(String developerProject) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEVELOPER_PROJECT, developerProject);
        editor.apply();
    }

    public static boolean getLoggedIn(Context context) {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public static void setKeyLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public static String getUsername(Context context) {
        return new SmallDB(context).getString(KEY_USERNAME);
    }

    public static void setUsername(Context context, String username) {
        new SmallDB(context).putString(KEY_USERNAME, username);
    }

    public static void logout(Context context) {
        setUsername(context, "");
        setKeyLoggedIn(false);
        SmallDB smallDB = new SmallDB(context);
        //Delete all sharedPref
        Map<String, ?> map = smallDB.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            if (!key.contains(KEY_USERNAME)) {
                smallDB.remove(key);
            }
        }
    }

}
