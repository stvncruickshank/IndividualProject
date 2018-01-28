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

import android.database.Cursor;
import android.database.CursorWrapper;

//import com.bhcc.app.pharmtech.data.model.Res;

public class ResCursorWrapper extends CursorWrapper {

    public ResCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //get the vals directly from the db table
    public Res getRes() {
        String rName = getString(getColumnIndex(ResSchema.ResTable.Cols.R_NAME));
        String rDate = getString(
                getColumnIndex(ResSchema.ResTable.Cols.R_DATE));
        String rTime = getString(getColumnIndex(ResSchema.ResTable.Cols.R_TIME));
        String rPhone = getString(getColumnIndex(ResSchema.ResTable.Cols.R_PH));
        String rLocation = getString(getColumnIndex(ResSchema.ResTable.Cols.R_LOC));


        Res reserv = new Res(rName, rDate, rTime, rLocation, rPhone);

        return reserv;
    }
}
