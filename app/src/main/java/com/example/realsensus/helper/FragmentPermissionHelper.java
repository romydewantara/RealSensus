package com.example.realsensus.helper;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.realsensus.listener.FragmentPermissionListener;

public class FragmentPermissionHelper {

    public ActivityResultLauncher<String> startPermissionHelper(Fragment fragmentActivity, FragmentPermissionListener fragmentPermissionListener) {
        return fragmentActivity.registerForActivityResult(new ActivityResultContracts.RequestPermission(), fragmentPermissionListener::onPermissionGranted);
    }

}
