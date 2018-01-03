package com.example.im.music.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

/**
 * Created by Im on 21-11-2017.
 */

public class Registrationinfo {
    // Serializing Json.
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;

    //constructor
    public Registrationinfo(String username, String password, String phone) throws JSONException {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

//    @Override
//    public String toString() {
//        return "Post{" +
//                "username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", phone=" + phone +
//                '}';
//    }

}