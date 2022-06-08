package com.example.realsensus.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realsensus.R;
import com.example.realsensus.library.CitizenFormDialog;
import com.example.realsensus.listener.CitizenFormDialogListener;

/**
 * Created by Muhammad Fakhri Pratama
 * github: ari.japindo@gmail.com | akufakhri61
 */
public class CitizenDataFragment extends Fragment {

    private Context context;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public CitizenDataFragment() {
        // Required empty public constructor
    }

    public static CitizenDataFragment newInstance(String param1, String param2) {
        CitizenDataFragment fragment = new CitizenDataFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_citizen_data, container, false);
        v.findViewById(R.id.imageAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                CitizenFormDialog citizenFormDialog = CitizenFormDialog.newInstance(context, "")
                        .getCitizenFormDialogListener(new CitizenFormDialogListener() {
                            @Override
                            public void onButtonSaveClicked() {

                            }

                            @Override
                            public void onButtonCancelClicked() {}
                        });
                if (fm != null) {
                    citizenFormDialog.show(fm, "citizen_form_dialog");
                }
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}