package com.dmitri.mobileclientservice.api.response;

import com.dmitri.mobileclientservice.data.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {
    @SerializedName("ListUsers")
    @Expose
    public List<User> listUsers;
}
