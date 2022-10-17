package com.dmitri.mobileclientservice.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @ColumnInfo(name = "user")
    @SerializedName("User")
    @Expose
    private String user;

    @PrimaryKey
    @ColumnInfo(name = "uid")
    @NonNull
    @SerializedName("Uid")
    @Expose
    private String uid;

    @ColumnInfo(name = "language")
    @SerializedName("Language")
    @Expose
    private String language;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
