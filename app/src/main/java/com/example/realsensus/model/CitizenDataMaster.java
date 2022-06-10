package com.example.realsensus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CitizenDataMaster {

   @SerializedName("citizens_data")
    private ArrayList<Citizen> citizensData;

    public ArrayList<Citizen> getCitizensData() {
        return citizensData;
    }

    public void setCitizensData(ArrayList<Citizen> citizensData) {
        this.citizensData = citizensData;
    }
}
