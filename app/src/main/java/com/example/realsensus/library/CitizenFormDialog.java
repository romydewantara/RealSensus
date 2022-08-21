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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.realsensus.R;
import com.example.realsensus.constant.Constant;
import com.example.realsensus.helper.RSPreference;
import com.example.realsensus.listener.CitizenFormDialogListener;
import com.example.realsensus.model.Citizen;

public class CitizenFormDialog extends DialogFragment {

    private boolean shown = false;

    //widget
    private AppCompatEditText editTextNumberId;
    private AppCompatEditText editTextName;
    private AppCompatTextView textViewNumberId;
    private AppCompatTextView textViewName;
    private ImageView imageScanNik;
    private ImageView imageScanName;

    String numberId = "", name = "";

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String mParams;

    CitizenFormDialogListener citizenFormDialogListener;

    public CitizenFormDialog() {
        // Required empty public constructor
    }

    public static CitizenFormDialog newInstance(Context context, String params) {
        CitizenFormDialog fragment = new CitizenFormDialog();
        Bundle arguments = new Bundle();
        mContext = context;
        mParams = params;

        arguments.putString("params", mParams);
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
        View view = inflater.inflate(R.layout.layout_form_citizen, container, false);
        imageScanNik = view.findViewById(R.id.imageScanNik);
        imageScanName = view.findViewById(R.id.imageScanName);
        editTextNumberId = view.findViewById(R.id.editTextNumberId);
        editTextName = view.findViewById(R.id.editTextName);
        textViewNumberId = view.findViewById(R.id.textViewInfoNumberId);
        textViewName = view.findViewById(R.id.textViewInfoName);

        return view;
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
        view.findViewById(R.id.imageScanNik).setOnClickListener(onScanNikClicked);
        view.findViewById(R.id.imageScanName).setOnClickListener(onScanNameClicked);

        Citizen citizen = RSPreference.getInstance(mContext).loadTempCitizensScanResult();
        if (citizen != null) {
            editTextNumberId.setText(citizen.getNumberId());
            editTextName.setText(citizen.getName());
        }

        switch (Constant.scanType) {
            case Constant.scanNumber:
                editTextNumberId.setText(mParams);
                break;
            case Constant.scanName:
                editTextName.setText(mParams);
                break;
        }
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
            if (isFilled()) {
                Citizen citizen = new Citizen(numberId, name);
                RSPreference.getInstance(mContext).addCitizen(citizen);
                citizenFormDialogListener.onButtonSaveClicked();
                dismiss();
            }
        }
    };

    private final View.OnClickListener onCancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            citizenFormDialogListener.onButtonCancelClicked();
            dismiss();
        }
    };

    private final View.OnClickListener onScanNikClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanNikClicked();
    };

    private final View.OnClickListener onScanNameClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanNameClicked();
    };

    public CitizenFormDialog getCitizenFormDialogListener(CitizenFormDialogListener citizenFormDialogListener) {
        this.citizenFormDialogListener = citizenFormDialogListener;
        return this;
    }

    private boolean isFilled() {
        if (editTextNumberId.getText() != null && editTextNumberId.getText().toString().equalsIgnoreCase("")) {
            textViewNumberId.setVisibility(View.VISIBLE);
        } else {
            textViewNumberId.setVisibility(View.GONE);
            numberId = editTextNumberId.getText().toString();
        }
        if (editTextName.getText() != null && editTextName.getText().toString().equalsIgnoreCase("")) {
            textViewName.setVisibility(View.VISIBLE);
        } else {
            textViewName.setVisibility(View.GONE);
            name = editTextName.getText().toString();
        }
        return !numberId.equalsIgnoreCase("") && !name.equalsIgnoreCase("");
    }

    /*private boolean isFilled() {
        if (editTextFamilyCardId.getText() != null && editTextFamilyCardId.getText().toString().equalsIgnoreCase("")) {
            textViewFamilyCard.setVisibility(View.VISIBLE);
        } else {
            textViewFamilyCard.setVisibility(View.GONE);
            familyCardId = editTextFamilyCardId.getText().toString();
        }
        if (editTextNumberId.getText() != null && editTextNumberId.getText().toString().equalsIgnoreCase("")) {
            textViewNumberId.setVisibility(View.VISIBLE);
        } else {
            textViewNumberId.setVisibility(View.GONE);
            numberId = editTextNumberId.getText().toString();
        }
        if (editTextName.getText() != null && editTextName.getText().toString().equalsIgnoreCase("")) {
            textViewName.setVisibility(View.VISIBLE);
        } else {
            textViewName.setVisibility(View.GONE);
            name = editTextName.getText().toString();
        }
        if (editTextPobDob.getText() != null && editTextPobDob.getText().toString().equalsIgnoreCase("")) {
            textViewPobDob.setVisibility(View.VISIBLE);
        } else {
            textViewPobDob.setVisibility(View.GONE);
            pobDob = editTextPobDob.getText().toString();
        }
        return !familyCardId.equalsIgnoreCase("") && !numberId.equalsIgnoreCase("")
                && !name.equalsIgnoreCase("") && !pobDob.equalsIgnoreCase("");
    }*/

}
