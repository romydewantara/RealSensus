package com.example.bacodelabs.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Developer {

    @SerializedName("data")
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("developer_list")
        List<DeveloperList> developerList;

        public List<DeveloperList> getDeveloperList() {
            return developerList;
        }

        public void setDeveloperList(List<DeveloperList> developerList) {
            this.developerList = developerList;
        }

        public class DeveloperList {
            @SerializedName("unique_id")
            int uniqueId;

            @SerializedName("name")
            String name;

            @SerializedName("first_name")
            String firstName;

            @SerializedName("last_name")
            String lastName;

            @SerializedName("birth_date")
            BirthDate birthDate;

            @SerializedName("gender")
            String gender;

            @SerializedName("contact")
            String contact;

            @SerializedName("email")
            String email;

            @SerializedName("role")
            String role;

            @SerializedName("profile")
            String profile;

            public int getUniqueId() {
                return uniqueId;
            }

            public void setUniqueId(int uniqueId) {
                this.uniqueId = uniqueId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public BirthDate getBirthDate() {
                return birthDate;
            }

            public void setBirthDate(BirthDate birthDate) {
                this.birthDate = birthDate;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getProfile() {
                return profile;
            }

            public void setProfile(String profile) {
                this.profile = profile;
            }

            public class BirthDate {

                @SerializedName("day")
                int day;

                @SerializedName("month")
                int month;

                @SerializedName("year")
                int year;

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }
        }
    }


}
