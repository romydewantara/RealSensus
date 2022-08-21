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

    //private AppCompatEditText editTextFamilyCardId;
    //private AppCompatEditText editTextNumberId;
    //private AppCompatEditText editTextName;
    //private AppCompatEditText editTextPobDob;
    //private AppCompatTextView textViewFamilyCard;
    //private AppCompatTextView textViewNumberId;
    //private AppCompatTextView textViewName;
    //private AppCompatTextView textViewPobDob;
    private AppCompatTextView textViewTitleBottomSheetCitizen;
    private AppCompatTextView buttonClose;
    private AppCompatTextView textNumberId;
    private AppCompatTextView textName;
    private AppCompatTextView textPob;
    private AppCompatTextView textDob;
    private AppCompatTextView textReligion;
    private AppCompatTextView textMarriageState;
    private AppCompatTextView textTypeOfWork;
    private AppCompatTextView textCitizens;
    private AppCompatTextView textValidUntil;

    private String oldFamilyCardId = "", familyCardId = "", numberId = "", name = "", pobDob = "";

    public CitizenFormBottomSheetDialog(Context context, CitizenFormBottomSheetDialogListener citizenFormBottomSheetDialogListener, Citizen citizen) {
        this.context = context;
        this.citizenFormBottomSheetDialogListener = citizenFormBottomSheetDialogListener;
        this.citizen = citizen;
        //this.oldFamilyCardId = citizen.getFamilyCardId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_bottom_sheet_form_citizen, container, false);

        textViewTitleBottomSheetCitizen = view.findViewById(R.id.textViewTitleBottomSheetCitizen);
        buttonClose = view.findViewById(R.id.buttonClose);
        textNumberId = view.findViewById(R.id.textNumberId);
        textName = view.findViewById(R.id.textName);
        textPob = view.findViewById(R.id.textPob);
        textDob = view.findViewById(R.id.textDob);
        textReligion = view.findViewById(R.id.textReligion);
        textMarriageState = view.findViewById(R.id.textMarriageState);
        textTypeOfWork = view.findViewById(R.id.textTypeOfWork);
        textCitizens = view.findViewById(R.id.textCitizens);
        textValidUntil = view.findViewById(R.id.textValidUntil);

        buttonClose.setOnClickListener(v -> {
            dismiss();
            citizenFormBottomSheetDialogListener.onButtonCancelClicked();
        });
        /*for (int i = 0; i < citizen.getFamilyData().size(); i++) {
            if (citizen.getFamilyData().get(i).getName().equalsIgnoreCase(citizen.getFamilyHeadName())) {
                Citizen.FamilyData familyData = citizen.getFamilyData().get(i);
                if (!familyData.getNumberId().equalsIgnoreCase("")) citizen.setNumberId(familyData.getNumberId());
                if (!familyData.getPob().equalsIgnoreCase("")) citizen.setPobDob(familyData.getPob());
                //set family data here…
            }
        }
        editTextFamilyCardId.setText(citizen.getFamilyCardId());
        editTextNumberId.setText(citizen.getNumberId());
        editTextName.setText(citizen.getFamilyHeadName());
        editTextPobDob.setText(citizen.getPobDob());
        initListener(view);*/
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String dateOfBirth = citizen.getDob().getDate() + "-" + citizen.getDob().getMonth() + "-" + citizen.getDob().getYear();
        String validUntil = citizen.getValidUntil().getDate() + "-" + citizen.getValidUntil().getMonth() + "-" + citizen.getValidUntil().getYear();
        textNumberId.setText(citizen.getNumberId());
        textName.setText(citizen.getName());
        textPob.setText(citizen.getPob());
        textDob.setText(dateOfBirth);
        textReligion.setText(citizen.getReligion());
        textMarriageState.setText(citizen.getMarriageState());
        textTypeOfWork.setText(citizen.getTypeOfWork());
        textCitizens.setText(citizen.getCitizenship());
        textValidUntil.setText("Seumur Hidup");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(dialogInterface -> {
            FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                setCancelable(false);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                BottomSheetBehavior.from(bottomSheet).setDraggable(false);
            }
        });
        return bottomSheetDialog;
    }

    private void initListener(View view) {
        view.findViewById(R.id.buttonSave).setOnClickListener(v -> {
            /*if (isFilled()) {
                citizen = new Citizen(familyCardId, numberId, name, pobDob, citizen.getFamilyData());
                new AppUtil(context).updateCitizensDataMaster(oldFamilyCardId, citizen);
            }*/
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
