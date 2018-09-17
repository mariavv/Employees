package com.maria.employees.model.entity;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Speciality {

    @NonNull
    @SerializedName("specialty_id")
    private String mId;

    @SerializedName("name")
    private String mName;

    public Speciality(@NonNull String id, String name) {
        this.mId = id;
        this.mName = name;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
