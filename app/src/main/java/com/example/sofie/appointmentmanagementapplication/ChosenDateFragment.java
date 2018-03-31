package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class ChosenDateFragment extends Fragment {
    public static String sDateOfAppointment;
    public static long ilEventOccursOn;
    public static long ilCurrentTime;
    public static TextView tvChosenDateDeleteActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chosen_date, container, false);

        tvChosenDateDeleteActivity = rootView.findViewById(R.id.tvChosenDateDeleteActivity);
        getTheDateOfTheAppointment();

        return rootView;
    }

    public static void getTheDateOfTheAppointment() {

        Calendar c = Calendar.getInstance();
        // get the current date to use as the default value for the date chosen by the user
        ilCurrentTime = c.get(Calendar.MILLISECOND);
        // get the date for which the user want to delete appointments

        //set the calendar instance to the date chosen by the user
        c.setTimeInMillis(ilEventOccursOn);

        // get the day, mont and year out of the date chosen by the user
        int iYear = c.get(Calendar.YEAR);
        int iMonth = c.get(Calendar.MONTH);
        String sMonth = getMonth(iMonth);
        int iDay = c.get(Calendar.DAY_OF_MONTH);

        // create a string displaying the date in DD:MM:YYYY format
        sDateOfAppointment = iDay + ". " + sMonth + " " + iYear;

        tvChosenDateDeleteActivity.setText(sDateOfAppointment);
    }

    public static String returnChosenDate(){
        getTheDateOfTheAppointment();
        return sDateOfAppointment;
    }

    public static String getMonth(int month) {
        // return the month in string format
        return new DateFormatSymbols().getMonths()[month];
    }
}

