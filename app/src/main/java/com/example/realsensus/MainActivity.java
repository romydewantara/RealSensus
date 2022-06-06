package com.example.realsensus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.realsensus.constant.Constant;
import com.example.realsensus.fragment.HomeFragment;
import com.example.realsensus.fragment.ScannerFragment;
import com.example.realsensus.listener.FragmentListener;

/**
 * Created by Muhammad Fakhri Pratama
 * github: ari.japindo@gmail.com | akufakhri61
 */
public class MainActivity extends AppCompatActivity implements FragmentListener {

    public static final int FRAGMENT_FINISH_GOTO_HOME = 0;
    public static final int FRAGMENT_FINISH_GOTO_SCANNER = 1;

    public static final String TAG_FRAGMENT_HOME = "home";
    public static final String TAG_FRAGMENT_SCANNER = "scanner";

    private Fragment fragment;

    private boolean isAutoFocus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            goToHome();
        }
    }

    @Override
    public void onFragmentFinish(Fragment currentFragment, int destinationFragment, boolean isForward) {
        switch (destinationFragment) {

            case FRAGMENT_FINISH_GOTO_HOME:
                fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, fragment, TAG_FRAGMENT_HOME)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_SCANNER:
                fragment = new ScannerFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constant.KEY_AUTO_FOCUS, isAutoFocus);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, fragment, TAG_FRAGMENT_HOME)
                        .commit();
                break;
        }
    }

    private void goToHome() {
        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, fragment, TAG_FRAGMENT_HOME)
                .commit();
    }
}