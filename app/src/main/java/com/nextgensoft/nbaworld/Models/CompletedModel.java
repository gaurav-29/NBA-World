package com.nextgensoft.nbaworld.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CompletedModel {

    // For passing the parameter.
    @SerializedName("exec_id")
    public int exec_id;

    @SerializedName("data")
    public ArrayList<Data> data;
    @SerializedName("message")
    public String message;
    @SerializedName("statusCode")
    public int statusCode;
    @SerializedName("error")
    public boolean error;

    public static class Data {
        @SerializedName("address")
        public String address;
        @SerializedName("mobile_no")
        public String mobile_no;
        @SerializedName("first_name")
        public String first_name;
        @SerializedName("customer_id")
        public String customer_id;
        @SerializedName("prior")
        public String prior;
        @SerializedName("day")
        public String day;
        @SerializedName("punch_out_location")
        public String punch_out_location;
        @SerializedName("punch_in_location")
        public String punch_in_location;
        @SerializedName("punchinlong")
        public String punchinlong;
        @SerializedName("punchinlat")
        public String punchinlat;
        @SerializedName("person_designation")
        public String person_designation;
        @SerializedName("person_name")
        public String person_name;
        @SerializedName("admin_note")
        public String admin_note;
        @SerializedName("status")
        public String status;
        @SerializedName("out_note")
        public String out_note;
        @SerializedName("out_time")
        public String out_time;
        @SerializedName("in_note")
        public String in_note;
        @SerializedName("in_time")
        public String in_time;
        @SerializedName("created_date")
        public String created_date;
        @SerializedName("executive")
        public String executive;
        @SerializedName("reshedule_note")
        public String reshedule_note;
        @SerializedName("note")
        public String note;
        @SerializedName("time")
        public String time;
        @SerializedName("date")
        public String date;
        @SerializedName("customer")
        public String customer;
        @SerializedName("bookcode")
        public String bookcode;
        @SerializedName("appointment_id")
        public String appointment_id;
    }
}
