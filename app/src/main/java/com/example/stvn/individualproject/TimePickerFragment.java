/**
 * STEVEN CRUICKSHANK
 * PROJECT THREE - SHOW ME WHAT YOU GOT
 *
 * On the professors suggestion, i took the existing reservation app, and loaded it with new features
 * and a fresh new UI.
 *
 * All of the requirements are met:
 * -SQLite Database ~ Date, Time, Name, Phone, and Location are written to a SQLite database
 *
 * -Location Services ~ When a user makes a reservation, location services takes note of the users
 * last location, and lists those coordinates in the DB. knowing where your reservations are coming
 * from is a fantastic way to target specific areas with advertising.
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
 * All data is preserved on rotation. There is a slight problem with the audio, in that if you
 * rotate and attempt to stop/play, new audioclips will start to play over the existing.
 */

package com.example.stvn.individualproject;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.example.stvn.individualproject.ResFragment.mRes;

/**
 * Created by stvn on 11/4/17.
 */
//very similar to the datepicker fragment
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public static boolean timeFlag = false;
    public ResLab rLab;
    //public static Res mRes;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        timeFlag = true;

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        final Calendar c = Calendar.getInstance();
        //month++;
        //c.set(hourOfDay, minute);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        //c.set(Calendar.DAY_OF_MONTH, day);

        ResFragment.mHour = c.get(Calendar.YEAR);
        ResFragment.mMinute = c.get(Calendar.MONTH);
        //ResFragment.mDay = c.get(Calendar.DAY_OF_MONTH);
        String temp;
        temp = hourOfDay + ":" + minute;


        //sets values passed from the dialog
        //final Calendar c = Calendar.getInstance();
        //c.set(Calendar.MINUTE, minute);
        //c.set(Calendar.HOUR_OF_DAY, hourOfDay);

//        ResFragment.mMinute = c.get(Calendar.MINUTE);
  //      ResFragment.mHour = c.get(Calendar.HOUR_OF_DAY);
    //    String temp = (String.valueOf(c.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(Calendar.MINUTE));
        mRes.setResTime(temp);
        //formats and passes a string to be used in the full reservation textview in the main activity
        if(ResFragment.totalReservation.isEmpty() == true){
            ResFragment.totalReservation =  hourOfDay + ":" + minute + "   @ ";
        } else {

            String date = ResFragment.totalReservation;
            ResFragment.totalReservation = hourOfDay + ":" + minute + "   @ ";
            ResFragment.totalReservation = ResFragment.totalReservation + date;

        }
        timeFlag = true;
        if (FragmentDate.dateFlag == true && timeFlag == true) {
            ResFragment.stringReserv.setText(mRes.getResDate() + " @ " + mRes.getResTime());


        }
    }
}