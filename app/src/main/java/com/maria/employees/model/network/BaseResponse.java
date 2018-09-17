package com.maria.employees.model.network;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("response")
    protected T data;

    public T getData() {
        return data;
    }
}