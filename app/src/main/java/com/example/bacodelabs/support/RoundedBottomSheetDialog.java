package com.example.bacodelabs.support;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bacodelabs.R;
import com.example.bacodelabs.adapter.DevelopersAdapter;
import com.example.bacodelabs.model.Developers;
import com.example.bacodelabs.util.Fonts;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class RoundedBottomSheetDialog extends BottomSheetDialogFragment {

    private Context context;

    public RoundedBottomSheetDialog(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_bottom_sheet_dialog, container, false);
        Fonts fonts = new Fonts(context);
        TextView tvTitleBottomSheet = layout.findViewById(R.id.tvTitleBottomSheet);
        tvTitleBottomSheet.setTypeface(fonts.stMedium());

        // Add Items
        List<Developers> developersList = new ArrayList<>();
        developersList.add(new Developers(0, "Romy Dewantara", "08 October 2020", "Impostor"));
        developersList.add(new Developers(1, "Aditia Nugraha", "09 November 2020", "Crewmate"));
        developersList.add(new Developers(2, "Ari Domero", "10 October 2020", "Crewmate"));
        developersList.add(new Developers(3, "Avip Pebrians", "17 November 2020", "Crewmate"));
        developersList.add(new Developers(4, "Aku Fakhri", "12 November 2020", "Crewmate"));
        developersList.add(new Developers(5, "Budi Stwn", "14 December 2020", "Crewmate"));
        developersList.add(new Developers(6, "Fajar Komar92", "20 December 2020", "Crewmate"));
        developersList.add(new Developers(7, "Jams", "09 October 2020", "Crewmate"));
        developersList.add(new Developers(8, "Wibawa Bangkit", "02 December 2020", "Crewmate"));

        DevelopersAdapter developersAdapter = new DevelopersAdapter(context, developersList);
        ListView listDev = layout.findViewById(R.id.listDevelopers);
        listDev.setAdapter(developersAdapter);

        return layout;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        final Dialog dialog = new BottomSheetDialog(context, getTheme());

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View view, int newState) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View view, float slideOffset) {
                        if (!Double.isNaN(slideOffset)) {
                            dialog.getWindow().setDimAmount(0.5f - ((slideOffset * -1)/2));
                        }
                    }
                };
            }
        });

        return dialog;
    }
}
