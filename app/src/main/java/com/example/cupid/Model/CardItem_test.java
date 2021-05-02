package com.example.cupid.Model;

import android.graphics.drawable.Drawable;

public class CardItem_test {


    private String name;
    private String age;
    private String dp;
    private String description;
    private String id;
    private String username;
    private String gender;

    public CardItem_test(String name, String dp, String description, String id, String username, String gender, String dob) {
        this.name = name;
        this.dp = dp;
        this.description = description;
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.dob = dob;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    private String dob;


    public CardItem_test(String id, String description, String dp, String name, String age) {
        this.id = id;
        this.description = description;
        this.dp = dp;
        this.name = name;
        this.age = age;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getDp() {
        return dp;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }
}