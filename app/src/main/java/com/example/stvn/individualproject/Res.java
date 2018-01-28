/**
 * STEVEN CRUICKSHANK
 * PROJECT THREE - Reservation App
 *
 * Features:
 * -SQLite Database ~ Date, Time, Name, Phone, and Location are written to a SQLite database
 *
 * -Location Services ~ When a user makes a reservation, location services takes note of the users
 * last location, and lists those coordinates in the DB. knowing where your reservations are coming
 * from is a fantastic way to target specific areas with advertising/marketing.
 *
 * -Audio/Video ~ jazzy piano and background noises (clanking, light chatting) play while the user makes
 * their reservation. At the very top is a looping video of a nameless couple enjoying a meal.
 *
 * -Multiple Fragments/Activites
 *
 * -Splash Screen - a blue color will pop up while the App loads. For some reason I was having trouble
 * projecting the icon on top of that lbue screen.
 *
 * -Ability to communicate - send the reservation to the phone number, or go to an ACTION DIAL
 * prompt, via intents
 *
 * -UI that works in landscape - Designed a landscape layout for the UI that looks very nice.
 * All data is preserved on rotation.
 */

package com.example.stvn.individualproject;


import android.location.Location;

import java.io.Serializable;

public class Res implements Serializable {
    Location resLoc;
    String resName;
    String resDate;
    String resTime;
    String rStringLocation;
    String resPh;
    public String longitude;
    public String lattitude;

    //constructor
    public Res() {
    }

    //constructor with args
    public Res(String rName, String rTime, String rDate, Location rLoc, String phNum) {
        this.resName = rName;
        this.resTime = rTime;
        this.resDate = rDate;
        this.resLoc = rLoc;
        this.resPh = phNum;
    }

    public Res(String rName, String rTime, String rDate, String rLoc, String phNum) {
        this.resName = rName;
        this.resTime = rTime;
        this.resDate = rDate;
        this.rStringLocation = rLoc;
        this.resPh = phNum;
    }




    // getters

    public String getResName(){
        if (resName != null){
            return resName;
        } else {
            return "NULL";
        }
    }

    public String getResDate() {

        if (resDate != null) {
            return resDate;
        } else {
            return "NULL";
        }
    }


    public String getResTime() {
        if (resTime != null) {
            return resTime;
        } else {
            return "NULL";
        }
    }


    public String getResLoc() {
        if (resLoc != null) {
            return Double.toString(resLoc.getLatitude()) + ", " + Double.toString(resLoc.getLongitude());
        } else {
            return "NULL";
        }
    }

    public String getResPh() {
        if (resPh != null) {
            return resPh;
        } else {
            return "NULL";
        }
    }
    // setters

    public void setResName(String rName){ this.resName = rName; }
    public void setResDate(String rDate) {
        this.resDate = rDate;
    }
    public void setResTime(String rTime) {
        this.resTime = rTime;
    }
    public void setResPh(String rPh) {
        this.resPh = rPh;
    }
    public void setResLoc(Location rLoc) {
        this.resLoc = rLoc;
    }

}
