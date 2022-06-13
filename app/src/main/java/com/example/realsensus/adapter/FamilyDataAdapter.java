package com.example.realsensus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realsensus.R;
import com.example.realsensus.model.Citizen;

import java.util.ArrayList;

public class FamilyDataAdapter extends RecyclerView.Adapter<FamilyDataAdapter.FamilyDataViewHolder> {

    private final Context context;
    private final ArrayList<Citizen.FamilyData> familyData;

    public FamilyDataAdapter(Context context, ArrayList<Citizen.FamilyData> familyData) {
        this.context = context;
        this.familyData = familyData;
    }

    @NonNull
    @Override
    public FamilyDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_number_id, parent, false);
        return new FamilyDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyDataViewHolder holder, int position) {
        holder.textViewNumberIdName.setText(familyData.get(position).getName());
        holder.textViewNumberId.setText(familyData.get(position).getNumberId());
    }

    @Override
    public int getItemCount() {
        return familyData.size();
    }

    static class FamilyDataViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewNumberIdName;
        AppCompatTextView textViewNumberId;

       public FamilyDataViewHolder(@NonNull View view) {
           super(view);

           textViewNumberIdName = view.findViewById(R.id.textViewNumberIdName);
           textViewNumberId = view.findViewById(R.id.textViewNumberId);
       }
   }
}
