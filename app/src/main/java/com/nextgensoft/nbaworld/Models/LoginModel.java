package com.nextgensoft.nbaworld.Models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("userrole")
    public String userrole;
    @SerializedName("name")
    public String name;
    @SerializedName("userid")
    public String userid;
    @SerializedName("message")
    public String message;
    @SerializedName("statusCode")
    public int statusCode;
    @SerializedName("error")
    public boolean error;

    @Override
    public String toString() {
        return "LoginModel{" +
                "created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", userrole='" + userrole + '\'' +
                ", name='" + name + '\'' +
                ", userid='" + userid + '\'' +
                ", message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", error=" + error +
                '}';
    }
}
