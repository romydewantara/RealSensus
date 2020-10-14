package com.example.bacodelabs.support;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bacodelabs.model.Developer;

/**
 * Created by: kamikaze
 * on October, 12 2020
 * */

public class DevelopersBottomSheet extends RoundedBottomSheetDialog {

    public DevelopersBottomSheet(Context context, Developer developer) {
        super(context, developer);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
