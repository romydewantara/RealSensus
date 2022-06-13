package com.example.realsensus.listener;

import androidx.fragment.app.Fragment;

public interface FragmentListener {
    void onFragmentFinish(Fragment currentFragment, int destinationFragment, boolean isForward);
    void onActivityFinish();
    void onActivityBackPressed();
}
