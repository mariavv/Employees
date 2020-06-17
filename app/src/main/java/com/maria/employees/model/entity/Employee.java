package com.maria.employees.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class Employee {

    @SerializedName("f_name")
    private String mName;

    @SerializedName("l_name")
    private String mLastName;

    @Nullable
    @SerializedName("birthday")
    private String mBirthDate;

    @Nullable
    @SerializedName("avatr_url")
    private String mAvatar;

    @SerializedName("specialty")
    private List<Speciality> mSpecialities;

    public Employee(String name, String lastName, String birthDate, String avatar, List<Speciality> specialities) {
        this.mName = name;
        this.mLastName = lastName;
        this.mBirthDate = birthDate;
        this.mAvatar = avatar;
        this.mSpecialities = specialities;
    }

    public String getName() {
        return mName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getBirthDate() {
        return mBirthDate;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public List<Speciality> getSpecialities() {
        return mSpecialities;
    }
}
