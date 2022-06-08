package com.example.realsensus.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.realsensus.R;
import com.example.realsensus.listener.CitizenFormDialogListener;

public class CitizenFormDialog extends DialogFragment {

    private boolean shown = false;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String familyCardId;

    CitizenFormDialogListener citizenFormDialogListener;

    public CitizenFormDialog() {
        // Required empty public constructor
    }

    public static CitizenFormDialog newInstance(Context context, String famylyCardId) {
        CitizenFormDialog fragment = new CitizenFormDialog();
        Bundle arguments = new Bundle();
        mContext = context;
        famylyCardId = famylyCardId;

        arguments.putString("family_card_id", famylyCardId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return inflater.inflate(R.layout.layout_form_citizen, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_form_citizen_dialog_rounded));
        }
        view.findViewById(R.id.buttonSave).setOnClickListener(onSaveButtonClicked);
        view.findViewById(R.id.buttonCancel).setOnClickListener(onCancelButtonClicked);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mContext.getApplicationContext() instanceof CitizenFormDialogListener) {
            citizenFormDialogListener = (CitizenFormDialogListener) mContext;
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        if (shown) return;
        try {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(this, tag);
            fragmentTransaction.commitAllowingStateLoss();
            shown = true;
        } catch (IllegalStateException e) {
            Log.d("InputUserDialog", "Exception: ", e);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        shown = false;
        super.onDismiss(dialog);
    }

    private final View.OnClickListener onSaveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            citizenFormDialogListener.onButtonSaveClicked();
            dismiss();
        }
    };

    private final View.OnClickListener onCancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            citizenFormDialogListener.onButtonCancelClicked();
            dismiss();
        }
    };

    public CitizenFormDialog getCitizenFormDialogListener(CitizenFormDialogListener citizenFormDialogListener) {
        this.citizenFormDialogListener = citizenFormDialogListener;
        return this;
    }

}
