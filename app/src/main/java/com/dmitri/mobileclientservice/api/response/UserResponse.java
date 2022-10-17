package com.dmitri.mobileclientservice.api.response;

import com.dmitri.mobileclientservice.api.response.Users;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("Users")
    @Expose
    public Users users;
}
