package com.example.ibrahim.falldetection.Service;

/**
 * Created by Ibrahim on 7/10/17.
 */

import java.util.Date;

public class FallDetectionRecord {

    @com.google.gson.annotations.SerializedName("time")
    private Date mTime;
    @com.google.gson.annotations.SerializedName("id")
    private String mId;
    @com.google.gson.annotations.SerializedName("location")
    private String mLocation;
    @com.google.gson.annotations.SerializedName("emergencyContact")
    private String mEmergencyContact;
    @com.google.gson.annotations.SerializedName("checked")
    private boolean checked = false;

    public FallDetectionRecord() {

    }

    public FallDetectionRecord(Date time, String id, String location, String emergencyContact) {
        this.setTime(time);
        this.setId(id);
        this.setLocation(location);
        this.setEmergencyContact(emergencyContact);
    }

    public final void setTime(Date time) {
        mTime = time;
    }
    public final void setId(String id) {
        mId = id;
    }
    public final void setLocation(String location) {
        mLocation = location;
    }
    public final void setEmergencyContact(String emergencyContact) {
        mEmergencyContact = emergencyContact;
    }


}
