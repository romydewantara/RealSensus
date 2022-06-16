package com.example.realsensus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realsensus.R;
import com.example.realsensus.model.Citizen;
import com.example.realsensus.model.CitizenDataMaster;
import com.google.gson.Gson;

import java.util.Collections;

public class CitizensDataAdapter extends RecyclerView.Adapter<CitizensDataAdapter.CitizensDataViewHolder> {

    private final Context context;
    private final CitizenDataMaster citizenDataMaster;
    private ClickListener clickListener;

    public CitizensDataAdapter(Context context, CitizenDataMaster citizenDataMaster) {
        this.context = context;
        this.citizenDataMaster = citizenDataMaster;
        Collections.reverse(citizenDataMaster.getCitizensData());
    }

    @NonNull
    @Override
    public CitizensDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_family_card_id, parent, false);
        return new CitizensDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitizensDataViewHolder holder, int position) {
        holder.textViewFamilyCardId.setText(familyCardIdWithSpace(position));
        holder.textViewFamilyHeadName.setText(citizenDataMaster.getCitizensData().get(position).getFamilyHeadName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        Log.d("citizensData", "onBindViewHolder - family_data: " + new Gson().toJson(citizenDataMaster.getCitizensData()));
        FamilyDataAdapter familyDataAdapter = new FamilyDataAdapter(context, citizenDataMaster.getCitizensData().get(position).getFamilyData());
        holder.recyclerViewFamilyCardId.setLayoutManager(linearLayoutManager);
        holder.recyclerViewFamilyCardId.setAdapter(familyDataAdapter);
    }

    @Override
    public int getItemCount() {
        return citizenDataMaster.getCitizensData().size();
    }

    private String familyCardIdWithSpace(int position) {
        StringBuilder familyCardId = new StringBuilder();
        for (int i = 0; i < citizenDataMaster.getCitizensData().get(position).getFamilyCardId().length(); i++) {
            familyCardId.append(citizenDataMaster.getCitizensData().get(position).getFamilyCardId().charAt(i));
            if (i < (citizenDataMaster.getCitizensData().get(position).getFamilyCardId().length() - 1)) familyCardId.append(" ");
        }
        return familyCardId.toString();
    }

    class CitizensDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatTextView textViewCountryName;
        AppCompatTextView textViewFamilyCardIdTitle;
        AppCompatTextView textViewFamilyCardId;
        AppCompatTextView textViewFamilyHeadTitle;
        AppCompatTextView textViewFamilyHeadName;
        AppCompatTextView textViewMembershipFamily;
        RecyclerView recyclerViewFamilyCardId;
        LinearLayout linearLayoutButtons;
        ImageView imageBurgerMenu;
        Button buttonEdit;
        Button buttonDelete;
        boolean isShowButton = false;

        public CitizensDataViewHolder(View view) {
            super(view);
            textViewCountryName = view.findViewById(R.id.textViewCountryName);
            textViewFamilyCardIdTitle = view.findViewById(R.id.textViewFamilyCardIdTitle);
            textViewFamilyCardId = view.findViewById(R.id.textViewFamilyCardId);
            textViewFamilyHeadTitle = view.findViewById(R.id.textViewFamilyHeadTitle);
            textViewFamilyHeadName = view.findViewById(R.id.textViewFamilyHeadName);
            textViewMembershipFamily = view.findViewById(R.id.textViewMembershipFamily);
            recyclerViewFamilyCardId = view.findViewById(R.id.recyclerViewFamilyCardId);
            linearLayoutButtons = view.findViewById(R.id.linearLayoutButtons);
            imageBurgerMenu = view.findViewById(R.id.imageBurgerMenu);
            buttonEdit = view.findViewById(R.id.buttonEdit);
            buttonDelete = view.findViewById(R.id.buttonDelete);

            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    textViewCountryName, 1, 10, 1, TypedValue.COMPLEX_UNIT_SP);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    textViewFamilyCardId, 1, 16, 1, TypedValue.COMPLEX_UNIT_SP);
            imageBurgerMenu.setOnClickListener(this);
            buttonEdit.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageBurgerMenu:
                    if (!isShowButton) {
                        isShowButton = true;
                        linearLayoutButtons.setVisibility(View.VISIBLE);
                    } else {
                        isShowButton = false;
                        linearLayoutButtons.setVisibility(View.GONE);
                    }
                    break;
                case R.id.buttonEdit:
                    clickListener.onButtonEditClicked(citizenDataMaster.getCitizensData().get(getAdapterPosition()));
                    break;
                case R.id.buttonDelete:
                    clickListener.onButtonDeleteClicked();
                    break;
            }
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onButtonEditClicked(Citizen citizen);
        void onButtonDeleteClicked();
    }

}
