package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import static java.lang.System.currentTimeMillis;

public class CalendarFragment extends Fragment {

    //VARIABLES
    public static long ilEventOccursOn;
    public static long ilCurrentTime;
    public static String sDateOfAppointment;
    CalendarView cvCalenderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        cvCalenderView = (CalendarView) rootView.findViewById(R.id.cvCalenderView);
        initCalendar();


        return rootView;

    }

    public void initCalendar(){
        Calendar c = Calendar.getInstance();
        // get the current time
        ilCurrentTime = currentTimeMillis ();
        // init the calender with the current day selected
        cvCalenderView.setDate(ilCurrentTime);
        // init the variable, which stores the time of the event with the current day
        ilEventOccursOn = ilCurrentTime;

        int iYear = c.get(Calendar.YEAR);
        int iMonth = c.get(Calendar.MONTH);
        String sMonth = getMonth(iMonth);
        int iDay = c.get(Calendar.DAY_OF_MONTH);

        // create a string that display the day in DD:MM:YYYY format
        sDateOfAppointment = iDay + ". " + sMonth + " " + iYear;

        // listen on change of selected day in the calendar
        cvCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                // save the new chosen day in the variable, which stores the time of the event
                ilEventOccursOn=c.getTimeInMillis();

                // get the day, month and year out of the time in milliseconds
                String sMonth = getMonth(month);

                // create a string that display the day in DD:MM:YYYY format
                sDateOfAppointment = day + ". " + sMonth + " " + year;
            }

        });
    }

    public String getMonth(int month) {
        // return the month in string format
        return new DateFormatSymbols().getMonths()[month];
    }
}
