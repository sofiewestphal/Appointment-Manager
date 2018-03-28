package com.example.sofie.appointmentmanagementapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import java.util.Calendar;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends Activity {

    // VARIABLES
    long ilEventOccursOn;
    long ilCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView cvCalenderView = (CalendarView) findViewById(R.id.cvCalenderView);
        // get the current time
        ilCurrentTime = currentTimeMillis ();
        // init the calender with the current day selected
        cvCalenderView.setDate(ilCurrentTime);
        // init the variable, which stores the time of the event with the current day
        ilEventOccursOn = ilCurrentTime;

        initButtons();

        // listen on change of selected day in the calendar
        cvCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                // save the new chosen day in the variable, which stores the time of the event
                ilEventOccursOn=c.getTimeInMillis();
            }
        });
    }

    public void initButtons(){
        View btnSearch = findViewById(R.id.btnSearch);
        View btnCreateAppointment = findViewById(R.id.btnCreateAppointment);
        View btnEditAppointment = findViewById(R.id.btnEditAppointment);
        View btnMoveAppointment = findViewById(R.id.btnMoveAppointment);
        View btnDeleteAppointment = findViewById(R.id.btnDeleteAppointment);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(SearchActivity.sDateSentToSearchAppointment, ilEventOccursOn);
                startActivity(intent);
            }
        });

        btnCreateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                intent.putExtra(CreateActivity.sDateSentToCreateAppointment, ilEventOccursOn);
                startActivity(intent);
            }
        });

        btnEditAppointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            }
        });

        btnMoveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnDeleteAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra(DeleteActivity.sDateSentToDeleteAppointment, ilEventOccursOn);
                startActivity(intent);

            }
        });
    }
}
