package com.example.bacodelabs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.bacodelabs.R;
import com.example.bacodelabs.listener.FragmentListener;

/**
 * Created by: kamikaze
 * on October, 12 2020
 * */

public class MainActivity extends AppCompatActivity implements FragmentListener {

    public final static int FRAGMENT_GOTO_HOME = 0;
    public final static int FRAGMENT_GOTO_ACCOUNT = 1;
    public final static int FRAGMENT_GOTO_SETTINGS = 2;
    public final static int FRAGMENT_GOTO_PROJECTS = 3;

    public final static String TAG_FRAGMENT_HOME = "home_fragment";
    public final static String TAG_FRAGMENT_ACCOUNT = "account_fragment";
    public final static String TAG_FRAGMENT_SETTINGS = "settings_fragment";
    public final static String TAG_FRAGMENT_PROJECTS = "projects_fragment";

    public static final String TAG_SAVED_FRAGMENT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentFinish(Fragment currentFragment, int destination, boolean isForward) {

    }

    @Override
    public void onFragmentPaused() {

    }

    @Override
    public void onActivityFinish() {

    }

    @Override
    public void onActivityBackPressed() {

    }
}
