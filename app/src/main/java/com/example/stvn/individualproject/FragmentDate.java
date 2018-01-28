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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import static com.example.stvn.individualproject.ResFragment.mRes;

/**
 * Created by stvn on 11/1/17.
 */

public class FragmentDate extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static int mday;
    public static int mmonth;
    public static int myear;
    public static String temp;
    public static Calendar myCal;
    public static boolean dateFlag = false;
    public ResLab rLab;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        Calendar c = Calendar.getInstance();
        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);
        dateFlag = true;

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, myear, mmonth, mday);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        //set calendar dates to the values passed from the dialog
        final Calendar c = Calendar.getInstance();

        month++; //adjust the month index

        c.set(year, month, day);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        ResFragment.mYear = c.get(Calendar.YEAR);
        ResFragment.mMonth = c.get(Calendar.MONTH);
        ResFragment.mDay = c.get(Calendar.DAY_OF_MONTH);

        temp = day + "/" + month + "/" + year;

        //format a string to be displayed as the full reservation in the main activity
        if(ResFragment.totalReservation.isEmpty() == true){

            ResFragment.totalReservation = day + "/" + month + "/" + year;
            mRes.setResDate(temp);

        } else {

            ResFragment.totalReservation += temp;//ResFragment.totalReservation;
            mRes.setResDate(temp);

        }

        //display text in the reservation string
        if (dateFlag == true && TimePickerFragment.timeFlag == true) {
            ResFragment.stringReserv.setText(mRes.getResDate() + " @ " + mRes.getResTime());
        }

    }

    //minor accessors and mutators
    private static int getDay(){
        return mday;
    }
    private static int getMonth(){
        return mmonth;
    }
    private static int getYear(){
        return myear;
    }
    public static String getString(){
        return getDay() + "/" + getMonth() + "/" + getYear();
    }
}