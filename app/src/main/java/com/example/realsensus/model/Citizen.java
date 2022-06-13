package com.example.realsensus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Citizen {

    @SerializedName("family_card_id")
    private String familyCardId;

    @SerializedName("number_id")
    private String numberId;

    @SerializedName("family_head_name")
    private String familyHeadName;

    @SerializedName("pob_dob")
    private String pobDob;

    @SerializedName("family_data")
    private ArrayList<FamilyData> familyData = new ArrayList<>();

    public Citizen(String familyCardId, String numberId, String familyHeadName, String pobDob) {
        this.familyCardId = familyCardId;
        this.numberId = numberId;
        this.familyHeadName = familyHeadName;
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

    public String getFamilyHeadName() {
        return familyHeadName;
    }

    public void setFamilyHeadName(String familyHeadName) {
        this.familyHeadName = familyHeadName;
    }

    public String getPobDob() {
        return pobDob;
    }

    public void setPobDob(String pobDob) {
        this.pobDob = pobDob;
    }

    public ArrayList<FamilyData> getFamilyData() {
        return familyData;
    }

    public void setFamilyData(ArrayList<FamilyData> familyData) {
        this.familyData = familyData;
    }

    public class FamilyData {

        @SerializedName("number_id")
        String numberId;

        @SerializedName("name")
        String name;

        @SerializedName("pob")
        String pob;

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

        public String getPob() {
            return pob;
        }

        public void setPob(String pob) {
            this.pob = pob;
        }
    }
}
