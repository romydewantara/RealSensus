package com.example.realsensus.model;

import com.google.gson.annotations.SerializedName;

public class Citizen {

    @SerializedName("family_card_id")
    private String familyCardId = "";

    @SerializedName("number_id")
    private String numberId = "";

    @SerializedName("name")
    private String name = "";

    @SerializedName("pob_dob")
    private String pobDob = "";

    public Citizen(String familyCardId, String numberId, String name, String pobDob) {
        this.familyCardId = familyCardId;
        this.numberId = numberId;
        this.name = name;
        this.pobDob = pobDob;
    }

    public String getFamilyCardId() {
        return familyCardId;
    }

    public void setFamilyCardId(String familyCardId) {
        this.familyCardId = familyCardId;
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPobDob() {
        return pobDob;
    }

    public void setPobDob(String pobDob) {
        this.pobDob = pobDob;
    }
}
