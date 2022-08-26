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
    private AppCompatTextView textViewNumberId;
    private AppCompatTextView textViewName;
    private AppCompatTextView textViewPob;
    private AppCompatEditText editTextNumberId;
    private AppCompatEditText editTextName;
    private AppCompatEditText editTextPob;
    private AppCompatEditText editTextDob;
    private AppCompatEditText editTextAddress;
    private AppCompatEditText editTextReligion;
    private AppCompatEditText editTextMarriageState;
    private AppCompatEditText editTextTypeOfWork;
    private AppCompatEditText editTextCitizens;
    private AppCompatEditText editTextValidUntil;
    private ImageView imageScanNik;
    private ImageView imageScanName;
    private ImageView imageScanPob;
    private ImageView imageScanDob;
    private ImageView imageScanAddress;
    private ImageView imageScanReligion;
    private ImageView imageScanMarriageState;
    private ImageView imageScanTypeOfWork;
    private ImageView imageScanCitizens;
    private ImageView imageScanValidUntil;

    String numberId, name, pob, dob, address, religion, marriageState, typeOfWork, citizenship, validUntil;

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
        textViewNumberId = view.findViewById(R.id.textViewInfoNumberId);
        textViewName = view.findViewById(R.id.textViewInfoName);
        textViewPob = view.findViewById(R.id.textViewInfoPob);

        imageScanNik = view.findViewById(R.id.imageScanNik);
        imageScanName = view.findViewById(R.id.imageScanName);
        imageScanPob = view.findViewById(R.id.imageScanPob);
        imageScanDob = view.findViewById(R.id.imageScanDob);
        imageScanAddress = view.findViewById(R.id.imageScanAddress);
        imageScanReligion = view.findViewById(R.id.imageScanReligion);
        imageScanMarriageState = view.findViewById(R.id.imageScanMarriageState);
        imageScanTypeOfWork = view.findViewById(R.id.imageScanTypeOfWork);
        imageScanCitizens = view.findViewById(R.id.imageScanCitizens);
        imageScanValidUntil = view.findViewById(R.id.imageScanValidUntil);

        editTextNumberId = view.findViewById(R.id.editTextNumberId);
        editTextName = view.findViewById(R.id.editTextName);
        editTextPob = view.findViewById(R.id.editTextPob);
        editTextDob = view.findViewById(R.id.editTextDob);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextReligion = view.findViewById(R.id.editTextReligion);
        editTextMarriageState = view.findViewById(R.id.editTextMarriageState);
        editTextTypeOfWork = view.findViewById(R.id.editTextTypeOfWork);
        editTextCitizens = view.findViewById(R.id.editTextCitizens);
        editTextValidUntil = view.findViewById(R.id.editTextValidUntil);
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
        view.findViewById(R.id.imageScanPob).setOnClickListener(onScanPobClicked);
        view.findViewById(R.id.imageScanDob).setOnClickListener(onScanDobClicked);
        view.findViewById(R.id.imageScanAddress).setOnClickListener(onScanAddressClicked);
        view.findViewById(R.id.imageScanReligion).setOnClickListener(onScanReligionClicked);
        view.findViewById(R.id.imageScanMarriageState).setOnClickListener(onScanMarriageStateClicked);
        view.findViewById(R.id.imageScanTypeOfWork).setOnClickListener(onScanTypeOfWorkClicked);
        view.findViewById(R.id.imageScanCitizens).setOnClickListener(onScanCitizenshipClicked);
        view.findViewById(R.id.imageScanValidUntil).setOnClickListener(onScanValidUntilClicked);

        Citizen citizen = RSPreference.getInstance(mContext).loadTempCitizensScanResult();
        if (citizen != null) {
            editTextNumberId.setText(citizen.getNumberId());
            editTextName.setText(citizen.getName());
            editTextPob.setText(citizen.getPob());
            editTextDob.setText("");
            editTextAddress.setText(citizen.getAddress());
            editTextReligion.setText(citizen.getReligion());
            editTextMarriageState.setText(citizen.getMarriageState());
            editTextTypeOfWork.setText(citizen.getTypeOfWork());
            editTextCitizens.setText(citizen.getCitizenship());
            editTextValidUntil.setText("");
        }

        switch (Constant.scanType) {
            case Constant.scanNumber:
                editTextNumberId.setText(mParams);
                break;
            case Constant.scanName:
                editTextName.setText(mParams);
                break;
            case Constant.scanPob:
                editTextPob.setText(mParams);
                break;
            case Constant.scanDob:
                editTextDob.setText(mParams);
                break;
            case Constant.scanAddress:
                editTextAddress.setText(mParams);
                break;
            case Constant.scanReligion:
                editTextReligion.setText(mParams);
                break;
            case Constant.scanMarriageState:
                editTextMarriageState.setText(mParams);
                break;
            case Constant.scanTypeOfWork:
                editTextTypeOfWork.setText(mParams);
                break;
            case Constant.scanCitizenship:
                editTextCitizens.setText(mParams);
                break;
            case Constant.scanValidUntil:
                editTextValidUntil.setText(mParams);
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
                Citizen.Date dobObject = new Citizen.Date();
                if (!dob.equalsIgnoreCase("")) {
                    //String dateOfBirth = editTextDob.getText().toString();
                    //Log.d("dateOfBirth", "showLoading - dob: " + dateOfBirth);
                    dobObject = new Citizen.Date(
                            dob.split("-")[0],
                            dob.split("-")[1],
                            dob.split("-")[2]);
                }
                Citizen.Date validUntilObject = new Citizen.Date();
                if (!validUntil.equalsIgnoreCase("")) {
                    //String validUntil = editTextValidUntil.getText().toString();
                    validUntilObject = new Citizen.Date(
                            validUntil.split("-")[0],
                            validUntil.split("-")[1],
                            validUntil.split("-")[2]);
                }

                Citizen citizen = new Citizen(numberId, name, pob, dobObject, address, religion,  marriageState, typeOfWork, citizenship, validUntilObject);
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

    private final View.OnClickListener onScanPobClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanPobClicked();
    };

    private final View.OnClickListener onScanDobClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanDobClicked();
    };

    private final View.OnClickListener onScanAddressClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanAddressClicked();
    };

    private final View.OnClickListener onScanReligionClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanReligionClicked();
    };

    private final View.OnClickListener onScanMarriageStateClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanMarriageStateClicked();
    };

    private final View.OnClickListener onScanTypeOfWorkClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanTypeOfWorkClicked();
    };

    private final View.OnClickListener onScanCitizenshipClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanCitizenshipClicked();
    };

    private final View.OnClickListener onScanValidUntilClicked = v -> {
        dismiss();
        citizenFormDialogListener.onScanValidUntilClicked();
    };

    public CitizenFormDialog getCitizenFormDialogListener(CitizenFormDialogListener citizenFormDialogListener) {
        this.citizenFormDialogListener = citizenFormDialogListener;
        return this;
    }

    private boolean isFilled() {
        if (editTextNumberId.getText() != null && editTextNumberId.getText().toString().equalsIgnoreCase("")) {
            textViewNumberId.setVisibility(View.VISIBLE);
            numberId = "";
        } else {
            textViewNumberId.setVisibility(View.GONE);
            numberId = editTextNumberId.getText().toString();
        }
        if (editTextName.getText() != null && editTextName.getText().toString().equalsIgnoreCase("")) {
            textViewName.setVisibility(View.VISIBLE);
            name = "";
        } else {
            textViewName.setVisibility(View.GONE);
            name = editTextName.getText().toString();
        }
        if (editTextPob.getText() != null && editTextPob.getText().toString().equalsIgnoreCase("")) {
            textViewPob.setVisibility(View.VISIBLE);
            pob = "";
        } else {
            textViewPob.setVisibility(View.GONE);
            pob = editTextPob.getText().toString();
        }
        dob = editTextDob.getText() != null && !editTextDob.getText().toString().equalsIgnoreCase("") ? editTextDob.getText().toString() : "";
        address = editTextAddress.getText() != null && !editTextAddress.getText().toString().equalsIgnoreCase("") ? editTextAddress.getText().toString() : "";
        religion = editTextReligion.getText() != null && !editTextReligion.getText().toString().equalsIgnoreCase("") ? editTextReligion.getText().toString() : "";
        marriageState = editTextMarriageState.getText() != null && !editTextMarriageState.getText().toString().equalsIgnoreCase("") ? editTextMarriageState.getText().toString() : "";
        typeOfWork = editTextTypeOfWork.getText() != null && !editTextTypeOfWork.getText().toString().equalsIgnoreCase("") ? editTextTypeOfWork.getText().toString() : "";
        citizenship = editTextCitizens.getText() != null && !editTextCitizens.getText().toString().equalsIgnoreCase("") ? editTextCitizens.getText().toString() : "";
        validUntil = editTextValidUntil.getText() != null && !editTextValidUntil.getText().toString().equalsIgnoreCase("") ? editTextValidUntil.getText().toString() : "";
        return !numberId.equalsIgnoreCase("") && !name.equalsIgnoreCase("") && !pob.equalsIgnoreCase("");
    }

}
