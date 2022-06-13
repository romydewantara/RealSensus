package com.example.realsensus.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realsensus.MainActivity;
import com.example.realsensus.R;
import com.example.realsensus.adapter.CitizensDataAdapter;
import com.example.realsensus.helper.RSPreference;
import com.example.realsensus.library.CitizenFormBottomSheetDialog;
import com.example.realsensus.library.CitizenFormDialog;
import com.example.realsensus.listener.CitizenFormBottomSheetDialogListener;
import com.example.realsensus.listener.CitizenFormDialogListener;
import com.example.realsensus.listener.FragmentListener;
import com.example.realsensus.model.Citizen;
import com.example.realsensus.model.CitizenDataMaster;
import com.example.realsensus.util.AppUtil;
import com.google.gson.Gson;

/**
 * Created by Muhammad Fakhri Pratama
 * github: ari.japindo@gmail.com | akufakhri61
 */
public class CitizenDataFragment extends Fragment implements CitizensDataAdapter.ClickListener, CitizenFormBottomSheetDialogListener {

    private Context context;
    private AppUtil appUtil;
    private CitizenDataMaster citizenDataMaster;
    private FragmentListener fragmentListener;
    //widget
    private RecyclerView recyclerViewCitizensData;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String previousFragment = "";

    public void addPrevFragmentTag(String previousFragment) {
        this.previousFragment = previousFragment;
    }

    public CitizenDataFragment() {
        // Required empty public constructor
    }

    public static CitizenDataFragment newInstance(String param1, String param2) {
        CitizenDataFragment fragment = new CitizenDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (!previousFragment.equals("")){
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(previousFragment)).commit();
        }

        appUtil = new AppUtil(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_citizen_data, container, false);
        recyclerViewCitizensData = v.findViewById(R.id.recyclerViewCitizensData);
        v.findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onActivityBackPressed();
            }
        });
        v.findViewById(R.id.imageAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCitizenFormDialog("");
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchCitizensData();
        if (previousFragment != null && previousFragment.equalsIgnoreCase(MainActivity.TAG_FRAGMENT_SCANNER)) {
            showCitizenFormDialog(RSPreference.getInstance(context).takeOCRTextResult());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentListener) {
            fragmentListener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentListener");
        }
    }

    private void fetchCitizensData() {
        citizenDataMaster = RSPreference.getInstance(context).loadCitizensDataMaster();
        CitizensDataAdapter citizensDataAdapter = new CitizensDataAdapter(context, citizenDataMaster);
        citizensDataAdapter.setClickListener(CitizenDataFragment.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewCitizensData.setLayoutManager(linearLayoutManager);
        recyclerViewCitizensData.setAdapter(citizensDataAdapter);
    }

    private void showCitizenFormDialog(String familyCardId) {
        FragmentManager fm = getFragmentManager();
        CitizenFormDialog citizenFormDialog = CitizenFormDialog.newInstance(context, familyCardId)
                .getCitizenFormDialogListener(new CitizenFormDialogListener() {
                    @Override
                    public void onButtonSaveClicked() {
                        Citizen citizen = (Citizen) RSPreference.getInstance(context).getCitizen();
                        appUtil.addCitizenDataMaster(citizen);
                        Log.d("CitizenDataFragment", "onButtonSaveClicked - citizen: " + new Gson().toJson(citizen));

                        //fetching…
                        fetchCitizensData();
                    }

                    @Override
                    public void onButtonCancelClicked() {
                    }
                });
        if (fm != null) {
            citizenFormDialog.show(fm, "citizen_form_dialog");
        }
    }

    @Override
    public void onButtonEditClicked(Citizen citizen) {
        Log.d("CitizenDataFragment", "onButtonEditClicked - citizen: " + new Gson().toJson(citizen));
        CitizenFormBottomSheetDialog citizenFormBottomSheetDialog = new CitizenFormBottomSheetDialog(context,
                CitizenDataFragment.this, citizen);
        if (getFragmentManager() != null) {
            citizenFormBottomSheetDialog.setCancelable(false);
            citizenFormBottomSheetDialog.show(getFragmentManager(), getTag());
        }

    }

    @Override
    public void onButtonDeleteClicked() {

    }

    @Override
    public void onButtonSaveClicked() {
        //save changes
    }

    @Override
    public void onButtonCancelClicked() {}
}