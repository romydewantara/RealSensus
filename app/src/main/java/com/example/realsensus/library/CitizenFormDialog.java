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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.realsensus.R;
import com.example.realsensus.helper.RSPreference;
import com.example.realsensus.listener.CitizenFormDialogListener;
import com.example.realsensus.model.Citizen;

public class CitizenFormDialog extends DialogFragment {

    private boolean shown = false;

    //widget
    private AppCompatEditText editTextFamilyCardId;
    private AppCompatEditText editTextNumberId;
    private AppCompatEditText editTextName;
    private AppCompatEditText editTextPobDob;
    private AppCompatTextView textViewFamilyCard;
    private AppCompatTextView textViewNumberId;
    private AppCompatTextView textViewName;
    private AppCompatTextView textViewPobDob;

    String familyCardId = "", numberId = "", name = "", pobDob = "";

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String mFamilyCardId;

    CitizenFormDialogListener citizenFormDialogListener;

    public CitizenFormDialog() {
        // Required empty public constructor
    }

    public static CitizenFormDialog newInstance(Context context, String famylyCardId) {
        CitizenFormDialog fragment = new CitizenFormDialog();
        Bundle arguments = new Bundle();
        mContext = context;
        mFamilyCardId = famylyCardId;

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
        View view = inflater.inflate(R.layout.layout_form_citizen, container, false);
        editTextFamilyCardId = view.findViewById(R.id.editTextFamilyCardId);
        editTextNumberId = view.findViewById(R.id.editTextNumberId);
        editTextName = view.findViewById(R.id.editTextName);
        editTextPobDob = view.findViewById(R.id.editTextPobDob);
        textViewFamilyCard = view.findViewById(R.id.textViewInfoFamilyCard);
        textViewNumberId = view.findViewById(R.id.textViewInfoNumberId);
        textViewName = view.findViewById(R.id.textViewInfoName);
        textViewPobDob = view.findViewById(R.id.textViewInfoPobDob);

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
        if (mFamilyCardId != null && !mFamilyCardId.equalsIgnoreCase("")) {
            editTextFamilyCardId.setText(mFamilyCardId);
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
                Log.d("CFD", "onClick - familyCardId: " + familyCardId);
                Citizen citizen = new Citizen(familyCardId, numberId, name, pobDob);
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

    public CitizenFormDialog getCitizenFormDialogListener(CitizenFormDialogListener citizenFormDialogListener) {
        this.citizenFormDialogListener = citizenFormDialogListener;
        return this;
    }

    private boolean isFilled() {
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
    }

}
