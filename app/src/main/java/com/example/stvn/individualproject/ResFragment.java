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

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.stvn.individualproject.TimePickerFragment.timeFlag;

public class ResFragment extends DialogFragment {

    public static String STATE_COUNTER = "counter";
    public static String RES_NAME = "RES NAME";
    public static String RES_PHONE = "RES PHONE";

    //member variables
    public static Res mRes;

    private EditText mPhoneField;
    private EditText mNameField;

    public static String testString;
    public static String totalReservation = "";

    public static int mDay;
    public static int mMonth;
    public static int mYear;
    public static int mMinute;
    public static int mHour;

    public static TextView stringReserv;

    public Button mDateFrag;
    public Button mTimeFrag;
    public Button mMessageBt;
    public Button mPhoneCall;
    public Button mAddRes;

    public VideoView mFoodVid;
    public static MediaPlayer mFoodSounds;

    public Menu mIconChangeMenu;

    public boolean isTimeSet = false;
    public boolean isDateSet = false;

    public int mCounter;

    //onCreate sets bool flags to false
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRes = new Res();
        timeFlag = false;
        FragmentDate.dateFlag = false;
        isTimeSet = false;
        isDateSet = false;
        setHasOptionsMenu(true);
        setRetainInstance(true);

        if (savedInstanceState != null) {
            mNameField.setText(savedInstanceState.getString(RES_NAME));
            mPhoneField.setText(savedInstanceState.getString(RES_PHONE));
            mIconChangeMenu.getItem(0).setIcon(ContextCompat.getDrawable(this.getActivity(), R.drawable.ic_volume_up));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_COUNTER, mCounter);
        outState.putString(RES_NAME, mRes.getResName());
        outState.putString(RES_PHONE, mRes.getResPh());
        mFoodSounds.pause(); //pauses audio on saved instance
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.res_menu_items, menu);
        mIconChangeMenu = menu;
        Log.e(TAG,"menu error");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Log.e(TAG,"menu error");
        switch(item.getItemId()){
            //if the audio is selected, an if block determins whether audio was playing, and what to do
            case R.id.mod_sound:
                if (mFoodSounds.isPlaying()) {
                    mFoodSounds.pause();

                    mIconChangeMenu.getItem(0).setIcon(ContextCompat.getDrawable(this.getActivity(), R.drawable.ic_volume_up));
                } else {
                    mFoodSounds.start();
                    mIconChangeMenu.getItem(0).setIcon(ContextCompat.getDrawable(this.getActivity(), R.drawable.ic_volume_off));

                }

                return true;
            //clear all the fields, and reset flags
            case R.id.clear:
                mRes.setResName("");
                mRes.setResDate("");
                mRes.setResTime("");
                mRes.setResPh("");
                mPhoneField.setText("");
                mNameField.setText("");
                mTimeFrag.setEnabled(true);
                mTimeFrag.setText("Set Time");
                mDateFrag.setEnabled(true);
                mDateFrag.setText("Set Date");
                stringReserv.setText("");
                timeFlag = false;
                FragmentDate.dateFlag = false;
                isTimeSet = false;
                isDateSet = false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //inflates the fragment, while instantiating all the widgets
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        //handles video and audio
        mFoodSounds = MediaPlayer.create(this.getActivity(), R.raw.foodnoise);
        mFoodSounds.setLooping(true);
        mFoodSounds.start();

        mFoodVid = (VideoView)v.findViewById(R.id.v_view_food);
        Uri uri = Uri.parse("android.resource://"+ this.getActivity().getPackageName() +"/"+R.raw.food);
        mFoodVid.setVideoURI(uri);
        mFoodVid.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            public void onPrepared(MediaPlayer mp){
                mp.setLooping(true);
            }
        });
        mFoodVid.start();

        //allRes = ResLab.get(getActivity()).getSpecificRes(null, null, ResSchema.ResTable.Cols.R_DATE);


        //phone num field enters characters as user types them.
        //validation makes sure the phone number is of proper length
        //before displaying buttons allowing call/message

        mNameField = (EditText)v.findViewById(R.id.name_edit);
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRes.setResName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        mPhoneField = (EditText) v.findViewById(R.id.phone_num);
        mPhoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRes.setResPh(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mRes.getResPh() != null && mRes.getResPh().length() == 10) {
                    mPhoneCall.setEnabled(true);
                    mMessageBt.setEnabled(true);
                }
                }
        });

        //message button which sends a prewritten message to the users number
        //includes validation to make sure the user isn't entering data out of order
        mMessageBt = (Button) v.findViewById(R.id.buttonMessage);
        mMessageBt.setEnabled(false);
        mMessageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDateSet && isTimeSet) {
                    if (mRes.getResPh() != null && mRes.getResPh().length() == 10
                            && isDateSet && isTimeSet) {
                        String messageToSend = "Hello! Your reservation is scheduled for: " +
                                totalReservation;

                        Uri uri = Uri.parse("smsto:" + mRes.getResPh());
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body", messageToSend);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Retry your phone number.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Set a date, and time",
                            Toast.LENGTH_LONG).show();
                }
            }

        });
        //similarly, phone call button is equipped with the same validation,
        //but displays the ACTION_DIAL, rather than actually calling the person
        mPhoneCall = (Button) v.findViewById(R.id.buttonPhoneCall);
        mPhoneCall.setEnabled(false);
        mPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDateSet && isTimeSet) {
                    if (mRes.getResPh() != null && mRes.getResPh().length() == 10) {
                        Intent callintent = new Intent(Intent.ACTION_DIAL);
                        callintent.setData(Uri.parse("tel:" + mRes.getResPh()));
                        startActivity(callintent);
                    } else {
                        Toast.makeText(getActivity(), "Retry your phone number.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Set your date and time.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        //button to display the datepicker fragment
        //text field to display the full reservation
        mDateFrag = (Button) v.findViewById(R.id.buttonDateFrag);
        stringReserv = (TextView)v.findViewById(R.id.resstring);

        mDateFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    DialogFragment picker = new FragmentDate();
                    picker.show(getFragmentManager(), "datePicker");
                    isDateSet = true;
                    if (mRes.getResDate()!=null){
                        mDateFrag.setEnabled(false);
                        mDateFrag.setText("Date Set!");
                    }
                    stringReserv.setText(testString);


            }

        });

        //opens a timepicker fragment if the user has entered a phone number
        mTimeFrag = (Button) v.findViewById(R.id.buttonTimeFrag);
        mTimeFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (mRes.getPhoneNum() != null && mRes.getPhoneNum().length() == 10) {

                    DialogFragment newFragment = new TimePickerFragment();
                    newFragment.show(getFragmentManager(), "TimePicker");
                    isTimeSet = true;
                if (mRes.getResTime()!=null){
                    mTimeFrag.setEnabled(false);
                    mTimeFrag.setText("Time Set!");

                }

            }

        });

        //add reservation to database
        mAddRes = (Button) v.findViewById(R.id.buttonAddRes);
        mAddRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResLab.get(getActivity()).addRes(mRes);


            }

        });

        return v;
    }
}
