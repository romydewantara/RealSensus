package com.example.realsensus.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.realsensus.LoginActivity;
import com.example.realsensus.MainActivity;
import com.example.realsensus.R;
import com.example.realsensus.helper.FragmentPermissionHelper;
import com.example.realsensus.helper.RSPreference;
import com.example.realsensus.listener.FragmentListener;
import com.example.realsensus.listener.FragmentPermissionListener;

/**
 * Created by Muhammad Fakhri Pratama
 * github: ari.japindo@gmail.com | akufakhri61
 */
public class HomeFragment extends Fragment {

    private Context context;
    private FragmentListener mListener;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String previousFragment = "";

    public void addPrevFragmentTag(String previousFragment) {
        this.previousFragment = previousFragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (!previousFragment.equals("")){
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(previousFragment)).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        v.findViewById(R.id.cardViewScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
        v.findViewById(R.id.cardViewCitizen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentFinish(HomeFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_CITIZEN, true);
            }
        });
        v.findViewById(R.id.cardViewLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RSPreference.getInstance(context).logout();
                startActivity(new Intent(context, LoginActivity.class));
                mListener.onActivityFinish();
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentListener) {
            mListener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentListener");
        }
        requestPermissionLauncher = new FragmentPermissionHelper().startPermissionHelper(this, isGranted -> {
            if (isGranted) {
                mListener.onFragmentFinish(HomeFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.no_camera_permission), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void requestPermissions() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
    }
}