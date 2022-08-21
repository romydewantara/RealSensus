package com.example.realsensus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.realsensus.constant.Constant;
import com.example.realsensus.fragment.CitizenDataFragment;
import com.example.realsensus.fragment.CitizenFormFragment;
import com.example.realsensus.fragment.HomeFragment;
import com.example.realsensus.fragment.ScannerFragment;
import com.example.realsensus.listener.FragmentListener;
import com.example.realsensus.model.Citizen;

/**
 * Created by Muhammad Fakhri Pratama
 * github: ari.japindo@gmail.com | akufakhri61
 */
public class MainActivity extends AppCompatActivity implements FragmentListener {

    public static final int FRAGMENT_FINISH_GOTO_HOME = 0;
    public static final int FRAGMENT_FINISH_GOTO_SCANNER = 1;
    public static final int FRAGMENT_FINISH_GOTO_CITIZEN = 2;
    public static final int FRAGMENT_FINISH_GOTO_CITIZEN_FORM = 3;

    public static final String TAG_FRAGMENT_HOME = "home";
    public static final String TAG_FRAGMENT_SCANNER = "scanner";
    public static final String TAG_FRAGMENT_CITIZEN = "citizen";
    public static final String TAG_FRAGMENT_CITIZEN_FORM = "citizen_form";

    private Fragment fragment;
    private Fragment previousFragment;
    private Citizen citizen;

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
    public void onBackPressed() {
        if (fragment instanceof ScannerFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME, false);
        } else if (fragment instanceof CitizenDataFragment) {
            if (previousFragment != null && previousFragment instanceof ScannerFragment) {
                onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_SCANNER, false);
            } else {
                onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME, false);
            }
        } else if (fragment instanceof CitizenFormFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_CITIZEN, false);
        } else {
            finish();
        }
    }

    @Override
    public void onFragmentFinish(Fragment currentFragment, int destinationFragment, boolean isForward) {

        int enter = R.anim.enter_from_right;
        int exit = R.anim.exit_to_left;
        if (!isForward) {
            enter = R.anim.enter_from_left;
            exit = R.anim.exit_to_right;
        }

        switch (destinationFragment) {

            case FRAGMENT_FINISH_GOTO_HOME:
                fragment = new HomeFragment();
                ((HomeFragment) fragment).addPrevFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.main, fragment, TAG_FRAGMENT_HOME)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_SCANNER:
                fragment = new ScannerFragment();
                ((ScannerFragment) fragment).addPrevFragmentTag(currentFragment.getTag());
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constant.KEY_AUTO_FOCUS, isAutoFocus);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.main, fragment, TAG_FRAGMENT_SCANNER)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_CITIZEN:
                fragment = new CitizenDataFragment();
                previousFragment = currentFragment;
                ((CitizenDataFragment) fragment).addPrevFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.main, fragment, TAG_FRAGMENT_CITIZEN)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_CITIZEN_FORM:
                fragment = new CitizenFormFragment(getApplicationContext(), citizen);
                previousFragment = currentFragment;
                ((CitizenFormFragment) fragment).addPrevFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.main, fragment, TAG_FRAGMENT_CITIZEN_FORM)
                        .commit();
                break;
        }
    }

    @Override
    public void onActivityFinish() {
        finish();
    }

    @Override
    public void onActivityBackPressed() {
        onBackPressed();
    }

    @Override
    public void onFragmentPassingCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    private void goToHome() {
        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, fragment, TAG_FRAGMENT_HOME)
                .commit();
    }
}