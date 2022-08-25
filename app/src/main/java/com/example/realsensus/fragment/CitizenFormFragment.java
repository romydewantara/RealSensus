package com.example.realsensus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.realsensus.MainActivity;
import com.example.realsensus.R;
import com.example.realsensus.listener.FragmentListener;
import com.example.realsensus.model.Citizen;
import com.example.realsensus.util.AppUtil;

public class CitizenFormFragment extends Fragment {

    private Context context;
    private Citizen citizen;
    private AppCompatEditText editTextNumberId;
    private AppCompatEditText editTextName;
    private AppCompatEditText editTextPob;
    private AppCompatEditText editTextDob;
    private AppCompatEditText editTextAddress;
    private AppCompatEditText editTextReligion;
    private AppCompatEditText editTextMarriageState;
    private AppCompatEditText editTextTypeOfWork;
    private AppCompatEditText editTextCitizens;
    private AppCompatEditText editTextValidUntil;
    private Button buttonSave;
    private Button buttonCancel;
    private LottieAnimationView loading;
    private View view;

    private FragmentListener fragmentListener;

    private String oldNumberId = "";
    private String
            numberId = "",
            name = "",
            pob = "",
            dob = "",
            address = "",
            religion = "",
            marriageState = "",
            typeOfWork = "",
            citizenship = "",
            validUntil = "";

    private final long DELAY_MILLIS = 1500L;
    private Animation animFadeIn, animFadeOut;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String previousFragment = "";

    public CitizenFormFragment() {
        // Required empty public constructor
    }

    public CitizenFormFragment(Context context, Citizen citizen) {
        this.context = context;
        this.citizen = citizen;
    }

    public static CitizenFormFragment newInstance(String param1, String param2) {
        CitizenFormFragment fragment = new CitizenFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void addPrevFragmentTag(String previousFragment) {
        this.previousFragment = previousFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            fragmentListener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_citizen_form, container, false);
        editTextNumberId = view.findViewById(R.id.editTextNumberId);
        editTextName = view.findViewById(R.id.editTextName);
        editTextPob = view.findViewById(R.id.editTextPob);
        editTextDob = view.findViewById(R.id.editTextDob);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextReligion = view.findViewById(R.id.editTextReligion);
        editTextMarriageState = view.findViewById(R.id.editTextMarriageState);
        editTextTypeOfWork = view.findViewById(R.id.editTextTypeOfWork);
        editTextCitizens = view.findViewById(R.id.editTextCitizens);
        editTextValidUntil = view.findViewById(R.id.editTextValidUntil);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        loading = view.findViewById(R.id.loading);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numberId = !citizen.getNumberId().equalsIgnoreCase("") ? citizen.getNumberId() : "";
        oldNumberId = numberId;
        name = !citizen.getName().equalsIgnoreCase("") ? citizen.getName() : "";
        pob = !citizen.getPob().equalsIgnoreCase("") ? citizen.getPob() : "";
        address = !citizen.getAddress().equalsIgnoreCase("") ? citizen.getAddress() : "";
        religion = !citizen.getReligion().equalsIgnoreCase("") ? citizen.getReligion() : "";
        marriageState = !citizen.getMarriageState().equalsIgnoreCase("") ? citizen.getMarriageState() : "";
        typeOfWork = !citizen.getTypeOfWork().equalsIgnoreCase("") ? citizen.getTypeOfWork() : "";
        citizenship = !citizen.getCitizenship().equalsIgnoreCase("") ? citizen.getCitizenship() : "";
        dob = !citizen.getDob().getDate().equalsIgnoreCase("") ?
                citizen.getDob().getDate() + "-" + citizen.getDob().getMonth() + "-" + citizen.getDob().getYear() : "";
        validUntil = !citizen.getValidUntil().getDate().equalsIgnoreCase("") ?
                citizen.getValidUntil().getDate() + "-" + citizen.getValidUntil().getMonth() + "-" + citizen.getValidUntil().getYear() : "";

        //set text with String variable above
        editTextNumberId.setText(numberId);
        editTextName.setText(name);
        editTextPob.setText(pob);
        editTextDob.setText(dob);
        editTextReligion.setText(religion);
        editTextMarriageState.setText(marriageState);
        editTextTypeOfWork.setText(typeOfWork);
        editTextCitizens.setText(citizenship);
        editTextValidUntil.setText(validUntil);

        initListener();
    }

    private void initListener() {
        buttonSave.setOnClickListener(v -> {
            AppUtil.hideKeyboardFrom(context, view);
            showLoading();
        });
        buttonCancel.setOnClickListener(v -> {
            fragmentListener.onFragmentFinish(CitizenFormFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_CITIZEN, false);
        });
    }

    private void showLoading() {
        loading.setRepeatCount(LottieDrawable.INFINITE);
        loading.playAnimation();
        loading.setVisibility(View.VISIBLE);
        loading.startAnimation(animFadeIn);
        buttonSave.setEnabled(false);
        buttonCancel.setEnabled(false);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            loading.pauseAnimation();
            loading.setVisibility(View.GONE);
            loading.startAnimation(animFadeOut);

            Citizen.Date dobObject = new Citizen.Date();
            if (editTextDob.getText() != null && !editTextDob.getText().toString().equalsIgnoreCase("")) {
                String dateOfBirth = editTextDob.getText().toString();
                Log.d("dateOfBirth", "showLoading - dob: " + dateOfBirth);
                dobObject = new Citizen.Date(
                        dateOfBirth.split("-")[0],
                        dateOfBirth.split("-")[1],
                        dateOfBirth.split("-")[2]);
            }
            Citizen.Date validUntilObject = new Citizen.Date();
            if (editTextValidUntil.getText() != null && !editTextValidUntil.getText().toString().equalsIgnoreCase("")) {
                String validUntil = editTextValidUntil.getText().toString();
                validUntilObject = new Citizen.Date(
                        validUntil.split("-")[0],
                        validUntil.split("-")[1],
                        validUntil.split("-")[2]);
            }

            Citizen citizen = new Citizen(
                    editTextNumberId.getText().toString(),
                    editTextName.getText().toString(),
                    editTextPob.getText().toString(),
                    dobObject,
                    editTextAddress.getText().toString(),
                    editTextReligion.getText().toString(),
                    editTextMarriageState.getText().toString(),
                    editTextTypeOfWork.getText().toString(),
                    editTextCitizens.getText().toString(),
                    validUntilObject);
            new AppUtil(context).updateCitizensDataMaster(oldNumberId, citizen);
            fragmentListener.onFragmentFinish(CitizenFormFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_CITIZEN, false);

        }, DELAY_MILLIS);
    }

}