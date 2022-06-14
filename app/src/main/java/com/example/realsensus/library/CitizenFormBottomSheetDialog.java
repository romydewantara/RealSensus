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
import androidx.appcompat.widget.AppCompatTextView;

import com.example.realsensus.R;
import com.example.realsensus.listener.CitizenFormBottomSheetDialogListener;
import com.example.realsensus.model.Citizen;
import com.example.realsensus.util.AppUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CitizenFormBottomSheetDialog extends BottomSheetDialogFragment {

    private final Context context;
    private final CitizenFormBottomSheetDialogListener citizenFormBottomSheetDialogListener;
    private Citizen citizen;

    private AppCompatEditText editTextFamilyCardId;
    private AppCompatEditText editTextNumberId;
    private AppCompatEditText editTextName;
    private AppCompatEditText editTextPobDob;
    private AppCompatTextView textViewFamilyCard;
    private AppCompatTextView textViewNumberId;
    private AppCompatTextView textViewName;
    private AppCompatTextView textViewPobDob;
    private String familyCardId = "", numberId = "", name = "", pobDob = "";

    public CitizenFormBottomSheetDialog(Context context, CitizenFormBottomSheetDialogListener citizenFormBottomSheetDialogListener, Citizen citizen) {
        this.context = context;
        this.citizenFormBottomSheetDialogListener = citizenFormBottomSheetDialogListener;
        this.citizen = citizen;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_bottom_sheet_form_citizen, container, false);

        editTextFamilyCardId = view.findViewById(R.id.editTextFamilyCardId);
        editTextNumberId = view.findViewById(R.id.editTextNumberId);
        editTextName = view.findViewById(R.id.editTextName);
        editTextPobDob = view.findViewById(R.id.editTextPobDob);
        textViewFamilyCard = view.findViewById(R.id.textViewInfoFamilyCard);
        textViewNumberId = view.findViewById(R.id.textViewInfoNumberId);
        textViewName = view.findViewById(R.id.textViewInfoName);
        textViewPobDob = view.findViewById(R.id.textViewInfoPobDob);

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
            if (isFilled()) {
                citizen = new Citizen(familyCardId, numberId, name, pobDob, citizen.getFamilyData());
                new AppUtil(context).updateCitizensDataMaster(citizen);
                dismiss();
                citizenFormBottomSheetDialogListener.onButtonSaveClicked();
            }
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
