package com.example.realsensus.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.realsensus.R;
import com.example.realsensus.listener.PopUpDialogListener;

public class PopUpDialog extends DialogFragment {

    private TextView titleAlert;
    private TextView messageAlert;
    private TextView negativeButton;
    private TextView positiveButton;
    private boolean shown = false;

    private CharSequence pButtonText = "";
    private CharSequence nButtonText = "";

    private PopUpDialogListener popUpDialogListener;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String textTitle = "";
    private static String textMessage = "";

    public PopUpDialog() {

    }

    public static PopUpDialog newInstance(Context context, String title, String message) {
        PopUpDialog frag = new PopUpDialog();
        Bundle args = new Bundle();
        mContext = context;
        textTitle = title;
        textMessage = message;

        args.putString("title", title);
        frag.setArguments(args);
        return frag;
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
        return inflater.inflate(R.layout.layout_dialog_pop_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_dialog_popup_rounded));
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
        Log.d("Xnull", "onAttach - context: " + mContext);
        if (mContext.getApplicationContext() instanceof PopUpDialogListener) {
            popUpDialogListener = (PopUpDialogListener) mContext;
        }
        Log.d("Xnull", "onAttach - customDialogListener: " + popUpDialogListener);
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
            Log.d("PopUpDialog", "Exception: ", e);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        shown = false;
        super.onDismiss(dialog);
    }

    private void setCosmetic() {
        if (!textTitle.equals("")) {
            titleAlert.setVisibility(View.VISIBLE);
        }
        if (!textMessage.equals("")) {
            messageAlert.setVisibility(View.VISIBLE);
        }

        titleAlert.setText(textTitle);
        messageAlert.setText(textMessage);
        setButton();
    }

    private void initListener() {
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialogListener.onPositiveButtonClicked();
                dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialogListener.onNegativeButtonClicked();
                dismiss();
            }
        });
    }

    private void setButton() {
        if (!pButtonText.equals("")) {
            positiveButton.setText(pButtonText);
            positiveButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_dialog_single_button, null));
            positiveButton.setVisibility(View.VISIBLE);
        }

        if (!nButtonText.equals("")) {
            positiveButton.setText(pButtonText);
            positiveButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_dialog_positive_button, null));
            positiveButton.setVisibility(View.VISIBLE);
            negativeButton.setText(nButtonText);
            negativeButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_dialog_negative_button, null));
            negativeButton.setVisibility(View.VISIBLE);
        }
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public PopUpDialog setButton(CharSequence positiveText, CharSequence negativeText, PopUpDialogListener listener) {
        pButtonText = positiveText;
        nButtonText = negativeText;
        this.popUpDialogListener = listener;
        return this;
    }
}