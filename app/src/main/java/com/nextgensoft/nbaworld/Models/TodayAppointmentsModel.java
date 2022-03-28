package com.nextgensoft.nbaworld.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TodayAppointmentsModel {

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

    @Override
    public String toString() {
        return "TodayAppointmentsModel{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", error=" + error +
                '}';
    }

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

        @Override
        public String toString() {
            return "Data{" +
                    "address='" + address + '\'' +
                    ", mobile_no='" + mobile_no + '\'' +
                    ", first_name='" + first_name + '\'' +
                    ", customer_id='" + customer_id + '\'' +
                    ", prior='" + prior + '\'' +
                    ", day='" + day + '\'' +
                    ", punch_out_location='" + punch_out_location + '\'' +
                    ", punch_in_location='" + punch_in_location + '\'' +
                    ", punchinlong='" + punchinlong + '\'' +
                    ", punchinlat='" + punchinlat + '\'' +
                    ", person_designation='" + person_designation + '\'' +
                    ", person_name='" + person_name + '\'' +
                    ", admin_note='" + admin_note + '\'' +
                    ", status='" + status + '\'' +
                    ", out_note='" + out_note + '\'' +
                    ", out_time='" + out_time + '\'' +
                    ", in_note='" + in_note + '\'' +
                    ", in_time='" + in_time + '\'' +
                    ", created_date='" + created_date + '\'' +
                    ", executive='" + executive + '\'' +
                    ", reshedule_note='" + reshedule_note + '\'' +
                    ", note='" + note + '\'' +
                    ", time='" + time + '\'' +
                    ", date='" + date + '\'' +
                    ", customer='" + customer + '\'' +
                    ", bookcode='" + bookcode + '\'' +
                    ", appointment_id='" + appointment_id + '\'' +
                    '}';
        }
    }
}
