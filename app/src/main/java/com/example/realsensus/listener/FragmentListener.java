package com.example.realsensus.listener;

import androidx.fragment.app.Fragment;

import com.example.realsensus.model.Citizen;

public interface FragmentListener {
    void onFragmentFinish(Fragment currentFragment, int destinationFragment, boolean isForward);
    void onActivityFinish();
    void onActivityBackPressed();
    void onFragmentPassingCitizen(Citizen citizen);
}
