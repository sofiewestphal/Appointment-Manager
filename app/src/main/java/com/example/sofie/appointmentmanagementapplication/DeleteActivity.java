package com.example.sofie.appointmentmanagementapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;


public class DeleteActivity extends Activity {

    // VARIABLES
    /*
    long ilEventOccursOn;
    long ilCurrentTime;
    TextView tvChosenDateDeleteActivity;*/
    public static String sDateOfAppointment;
    public static int iChosenIdToDelete = 0;
    public static final String sDateSentToDeleteAppointment = "ilEventOccursOn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        setTheDateOfTheAppointment();
        addFragmentMain();
        getTheDateOfTheAppointment();

    }

    public void addFragmentMain(){
        // I use the FragmentManager to load the MainFragment
        Fragment DeleteMainFragment = new DeleteMainFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fl_delete_activity, DeleteMainFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setTheDateOfTheAppointment() {

        ChosenDateFragment.ilEventOccursOn = getIntent().getLongExtra(sDateSentToDeleteAppointment, ChosenDateFragment.ilCurrentTime);

        /*Calendar c = Calendar.getInstance();
        // get the current date to use as the default value for the date chosen by the user
        ilCurrentTime = c.get(Calendar.MILLISECOND);
        // get the date for which the user want to delete appointments
        ilEventOccursOn = getIntent().getLongExtra(sDateSentToDeleteAppointment, ilCurrentTime);

        //set the calendar instance to the date chosen by the user
        c.setTimeInMillis(ilEventOccursOn);

        // get the day, mont and year out of the date chosen by the user
        int iYear = c.get(Calendar.YEAR);
        int iMonth = c.get(Calendar.MONTH);
        String sMonth = getMonth(iMonth);
        int iDay = c.get(Calendar.DAY_OF_MONTH);

        // create a string displaying the date in DD:MM:YYYY format
        sDateOfAppointment = iDay + ". " + sMonth + " " + iYear;
        tvChosenDateDeleteActivity = findViewById(R.id.tvChosenDateDeleteActivity);
        tvChosenDateDeleteActivity.setText(sDateOfAppointment);*/
    }

    /*public String getMonth(int month) {
        // return the month in string format
        return new DateFormatSymbols().getMonths()[month];
    }*/

    public void getTheDateOfTheAppointment(){
        sDateOfAppointment = ChosenDateFragment.returnChosenDate();
    }
}
