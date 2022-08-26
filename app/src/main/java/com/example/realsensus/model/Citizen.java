package com.example.realsensus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Citizen {

    @SerializedName("number_id")
    private String numberId = "";

    @SerializedName("name")
    String name = "";

    @SerializedName("pob")
    String pob = "";

    @SerializedName("dob")
    Date dob = new Date();

    @SerializedName("address")
    String address = "";

    @SerializedName("religion")
    String religion = "";

    @SerializedName("marriage_state")
    String marriageState = "";

    @SerializedName("type_of_work")
    String typeOfWork = "";

    @SerializedName("citizenship")
    String citizenship = "";

    @SerializedName("valid_until")
    Date validUntil = new Date();

    /*==== this variable not used yet ====*/
    @SerializedName("family_card_id")
    private String familyCardId;

    @SerializedName("family_head_name")
    private String familyHeadName;

    @SerializedName("pob_dob")
    private String pobDob;

    @SerializedName("family_data")
    private ArrayList<FamilyData> familyData;

    public Citizen() {
        //required empty constructor
    }

    public Citizen(String numberId, String name, String pob, Date dob, String address, String religion,
                   String marriageState, String typeOfWork, String citizenship, Date validUntil) {
        this.numberId = numberId;
        this.name = name;
        this.pob = pob;
        this.dob = dob;
        this.address = address;
        this.religion = religion;
        this.marriageState = marriageState;
        this.typeOfWork = typeOfWork;
        this.citizenship = citizenship;
        this.validUntil = validUntil;
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

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMarriageState() {
        return marriageState;
    }

    public void setMarriageState(String marriageState) {
        this.marriageState = marriageState;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public static class Date {

        @SerializedName("date")
        String date = "00";

        @SerializedName("month")
        String month = "00";

        @SerializedName("year")
        String year = "0000";

        public Date() {
            //required empty constructor
        }

        public Date(String date, String month, String year) {
            this.date = date;
            this.month = month;
            this.year = year;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
    public String getFamilyCardId() {
        return familyCardId;
    }

    public void setFamilyCardId(String familyCardId) {
        this.familyCardId = familyCardId;
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
