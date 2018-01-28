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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ResBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Reservations.db";

    public ResBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the db!
        Log.d("CriminalIntent", Log.getStackTraceString(new Exception()));

        db.execSQL("create table " + ResSchema.ResTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ResSchema.ResTable.Cols.R_NAME + ", " +
                ResSchema.ResTable.Cols.R_DATE + ", " +
                ResSchema.ResTable.Cols.R_TIME + ", " +
                ResSchema.ResTable.Cols.R_LOC + ", " +
                ResSchema.ResTable.Cols.R_PH +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}