package com.example.realsensus.library;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.realsensus.R;
import com.example.realsensus.listener.CitizenFormBottomSheetDialogListener;
import com.example.realsensus.model.Citizen;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CitizenFormBottomSheetDialog extends BottomSheetDialogFragment {

    private final Context context;
    private final CitizenFormBottomSheetDialogListener citizenFormBottomSheetDialogListener;
    private final Citizen citizen;

    public CitizenFormBottomSheetDialog(Context context, CitizenFormBottomSheetDialogListener citizenFormBottomSheetDialogListener, Citizen citizen) {
        this.context = context;
        this.citizenFormBottomSheetDialogListener = citizenFormBottomSheetDialogListener;
        this.citizen = citizen;
        //familyCardId data assignment
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_bottom_sheet_form_citizen, container, false);

        AppCompatEditText editTextFamilyCardId = view.findViewById(R.id.editTextFamilyCardId);
        AppCompatEditText editTextNumberId = view.findViewById(R.id.editTextNumberId);
        AppCompatEditText editTextName = view.findViewById(R.id.editTextName);
        AppCompatEditText editTextPobDob = view.findViewById(R.id.editTextPobDob);

        for (int i = 0; i < citizen.getFamilyData().size(); i++) {
            if (citizen.getFamilyData().get(i).getName().equalsIgnoreCase(citizen.getFamilyHeadName())) {
                Citizen.FamilyData familyData = citizen.getFamilyData().get(i);
                editTextFamilyCardId.setText(citizen.getFamilyCardId());
                editTextNumberId.setText(familyData.getNumberId());
                editTextName.setText(citizen.getFamilyHeadName());
                editTextPobDob.setText(familyData.getPob());
            }
        }

        view.findViewById(R.id.buttonSave).setOnClickListener(v -> {
            dismiss();
            citizenFormBottomSheetDialogListener.onButtonSaveClicked();
        });
        view.findViewById(R.id.buttonCancel).setOnClickListener(v -> {
            dismiss();
            citizenFormBottomSheetDialogListener.onButtonCancelClicked();
        });
        view.findViewById(R.id.textViewClose).setOnClickListener(v -> {
            this.dismiss();
        });
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(dialogInterface -> {
            FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                //BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(false);
            }
        });
        return bottomSheetDialog;
    }
}
