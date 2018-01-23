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
    public static ResLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ResLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new ResLab(context);
        }

        return sCrimeLab;
    }

    private ResLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ResBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addCrime(Res c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(ResSchema.ResTable.NAME, null, values);
    }

    public List<Res> getCrimes() {
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

    public Res getCrime(UUID id) {
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

    public void updateCrime(Res crime) {
        //String uuidString = crime.getId().toString();
        //ContentValues values = getContentValues(crime);
        //mDatabase.update(ResSchema.ResTable.NAME, values,
          //      ResSchema.ResTable.Cols.UUID + " = ?",
            //    new String[]{uuidString});
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

