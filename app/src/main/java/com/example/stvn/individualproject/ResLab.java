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

//git test commit
//steven testing git pushing

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.stvn.individualproject.ResSchema.ResTable.Cols.R_DATE;
import static com.example.stvn.individualproject.ResSchema.ResTable.Cols.R_LOC;
import static com.example.stvn.individualproject.ResSchema.ResTable.Cols.R_NAME;
import static com.example.stvn.individualproject.ResSchema.ResTable.Cols.R_PH;
import static com.example.stvn.individualproject.ResSchema.ResTable.Cols.R_TIME;


//import com.bhcc.app.pharmtech.data.model.Medicine;

// Singleton + Database
public class ResLab {
    public static ResLab sResLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ResLab get(Context context) {
        if (sResLab == null) {
            sResLab = new ResLab(context);
        }

        return sResLab;
    }

    private ResLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ResBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addRes(Res c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(ResSchema.ResTable.NAME, null, values);
    }

    public List<Res> getRes() {
        List<Res> crimes = new ArrayList<>();
        ResCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getRes());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public Res getRes(UUID id) {
        ResCursorWrapper cursor = queryCrimes(
                R_NAME + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getRes();
        } finally {
            cursor.close();
        }
    }


    private ResCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ResSchema.ResTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new ResCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Res crime) {
        ContentValues values = new ContentValues();
        values.put(R_NAME, crime.getResName());
        values.put(R_DATE, crime.getResDate().toString());
        values.put(R_TIME, crime.getResTime());
        values.put(R_PH, crime.getResPh());
        values.put(R_LOC, crime.getResLoc());

        return values;
    }
}

