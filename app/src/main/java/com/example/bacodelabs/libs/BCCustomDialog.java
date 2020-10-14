package com.example.bacodelabs.libs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bacodelabs.R;
import com.example.bacodelabs.listener.CustomDialogListener;
import com.example.bacodelabs.util.Fonts;

import java.util.ArrayList;

public class BCCustomDialog extends DialogFragment {

    private TextView titleAlert;
    private TextView messageAlert;
    private TextView negativeButton;
    private TextView positiveButton;

    private CustomDialogListener customDialogListener;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String textTitle;
    private static String textMessage;
    private static ArrayList<String> actionList;

    public BCCustomDialog() {

    }

    public static BCCustomDialog newInstance(Context context, String title, String message, ArrayList<String> actions) {
        BCCustomDialog frag = new BCCustomDialog();
        Bundle args = new Bundle();
        mContext = context;
        textTitle = title;
        textMessage = message;
        actionList = actions;

        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_custom_alert_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_custom_dialog));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        titleAlert = view.findViewById(R.id.titleAlert);
        messageAlert = view.findViewById(R.id.messageAlert);
        negativeButton = view.findViewById(R.id.negativeButton);
        positiveButton = view.findViewById(R.id.positiveButton);
        setCosmetic();
        initListener();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CustomDialogListener) {
            customDialogListener = (CustomDialogListener) context;
        }
    }

    private void setCosmetic() {
        Fonts fonts = new Fonts(mContext);
        titleAlert.setTypeface(fonts.stBold());
        messageAlert.setTypeface(fonts.stRegular());
        negativeButton.setTypeface(fonts.stBold());
        positiveButton.setTypeface(fonts.stBold());

        titleAlert.setTextColor(mContext.getResources().getColor(R.color.bacode_black));
        messageAlert.setTextColor(mContext.getResources().getColor(R.color.bacode_black));
        negativeButton.setTextColor(mContext.getResources().getColor(R.color.bacode_blue_dark));
        positiveButton.setTextColor(mContext.getResources().getColor(R.color.bacode_blue_dark));

        titleAlert.setTextSize(17f);
        messageAlert.setTextSize(16f);
        negativeButton.setTextSize(17f);
        positiveButton.setTextSize(17f);

        titleAlert.setText(textTitle);
        messageAlert.setText(textMessage);
        messageAlert.setText(textMessage);
        setButton(actionList);
    }

    private void initListener() {
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                customDialogListener.positiveButtonPressed();
                dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "CANCEL", Toast.LENGTH_SHORT).show();
                customDialogListener.negativeButtonPressed();
                dismiss();
            }
        });
    }

    private void setButton(ArrayList<String> action) {
        if (action.size() == 1) {
            String positive = action.get(0);
            positiveButton.setBackground(mContext.getResources().getDrawable(R.drawable.bg_single_button_custom_dialog));
            positiveButton.setText(positive);
        } else {
            String positive = action.get(0);
            String negative = action.get(1);
            positiveButton.setText(positive);
            negativeButton.setText(negative);
            positiveButton.setBackground(mContext.getResources().getDrawable(R.drawable.bg_positive_btn_rounded));
            negativeButton.setBackground(mContext.getResources().getDrawable(R.drawable.bg_negative_btn_rounded));
            negativeButton.setVisibility(View.VISIBLE);
        }
    }
}
