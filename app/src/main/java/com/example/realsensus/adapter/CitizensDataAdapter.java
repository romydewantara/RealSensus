package com.example.realsensus.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realsensus.R;
import com.example.realsensus.model.Citizen;
import com.example.realsensus.model.CitizenDataMaster;

import java.util.Collections;

public class CitizensDataAdapter extends RecyclerView.Adapter<CitizensDataAdapter.CitizensDataViewHolder> {

    private final Context context;
    private final CitizenDataMaster citizenDataMaster;
    private final ClickListener clickListener;

    public CitizensDataAdapter(Context context, CitizenDataMaster citizenDataMaster, ClickListener clickListener) {
        this.context = context;
        this.citizenDataMaster = citizenDataMaster;
        this.clickListener = clickListener;
        Collections.reverse(citizenDataMaster.getCitizensData());
    }

    @NonNull
    @Override
    public CitizensDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_citizen_id, parent, false);
        return new CitizensDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitizensDataViewHolder holder, int position) {
        holder.textViewCitizenId.setText(citizenDataMaster.getCitizensData().get(position).getNumberId());
        holder.textViewCitizenName.setText(citizenDataMaster.getCitizensData().get(position).getName());
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(holder.textViewCountryName, 1, 10, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(holder.textViewCitizenId, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);

        holder.cardViewCitizensDataItem.setOnClickListener(v -> {
            clickListener.onCitizenDetails(citizenDataMaster.getCitizensData().get(position));
        });
        holder.imageBurgerMenu.setOnClickListener(v -> {
            if (holder.linearLayoutButtons.getVisibility() == View.INVISIBLE || holder.linearLayoutButtons.getVisibility() == View.GONE)
                holder.linearLayoutButtons.setVisibility(View.VISIBLE); else holder.linearLayoutButtons.setVisibility(View.GONE);
        });
        holder.buttonEdit.setOnClickListener(v -> clickListener.onButtonEditClicked(citizenDataMaster.getCitizensData().get(position)));
        holder.buttonDelete.setOnClickListener(v -> clickListener.onButtonDeleteClicked(citizenDataMaster.getCitizensData().get(position)));
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

    static class CitizensDataViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardViewCitizensDataItem;
        private final AppCompatTextView textViewCountryName;
        private final AppCompatTextView textViewCitizenId;
        private final AppCompatTextView textViewCitizenName;
        private final LinearLayout linearLayoutButtons;
        private final ImageView imageBurgerMenu;
        private final Button buttonEdit;
        private final Button buttonDelete;

        public CitizensDataViewHolder(View view) {
            super(view);
            cardViewCitizensDataItem = view.findViewById(R.id.cardViewCitizensDataItem);
            textViewCountryName = view.findViewById(R.id.textViewCountryName);
            textViewCitizenId = view.findViewById(R.id.textViewCitizenId);
            textViewCitizenName = view.findViewById(R.id.textViewCitizenName);
            linearLayoutButtons = view.findViewById(R.id.linearLayoutButtons);
            imageBurgerMenu = view.findViewById(R.id.imageBurgerMenu);
            buttonEdit = view.findViewById(R.id.buttonEdit);
            buttonDelete = view.findViewById(R.id.buttonDelete);
        }

    }

    public interface ClickListener {
        void onCitizenDetails(Citizen citizen);
        void onButtonEditClicked(Citizen citizen);
        void onButtonDeleteClicked(Citizen citizen);
    }

}
