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

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient; //used to get GPS coords
    public TextView tvLoc;
    public static String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/**
 * USE THIS BLOCK TO GATHER GPS COORDINATES
 */
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //this is a Lint warning that won't be suppressed.
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                ResFragment.mRes.setResLoc(location);

                            }
                        }
                    });


        //frag mgmt
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new ResFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}
