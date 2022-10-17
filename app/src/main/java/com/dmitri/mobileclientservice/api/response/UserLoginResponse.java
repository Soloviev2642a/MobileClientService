package com.dmitri.mobileclientservice.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLoginResponse implements Serializable {
    @SerializedName("Code")
    @Expose
    public String code;
}
