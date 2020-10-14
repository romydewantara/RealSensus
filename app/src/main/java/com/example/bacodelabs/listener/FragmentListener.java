package com.example.bacodelabs.listener;

import androidx.fragment.app.Fragment;

/**
 * Created by: kamikaze
 * on October, 12 2020
 * */
public interface FragmentListener {
    void onFragmentFinish(Fragment currentFragment, int destination, boolean isForward);
    void onFragmentPaused();
    void onActivityFinish();
    void onActivityBackPressed();
}
