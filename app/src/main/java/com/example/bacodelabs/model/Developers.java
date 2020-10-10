package com.example.bacodelabs.model;

public class Developers {

    private int uniqueId;
    private String name;
    private String birth;
    private String role;

    public Developers(int uniqueId, String name, String birth, String role) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.birth = birth;
        this.role = role;
    }

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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birthdate) {
        this.birth = birthdate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
