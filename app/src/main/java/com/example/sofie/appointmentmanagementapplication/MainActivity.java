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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();
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
                startActivity(intent);
            }
        });

        btnCreateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                intent.putExtra(CreateActivity.sDateSentToCreateAppointment, CalendarFragment.ilEventOccursOn);
                startActivity(intent);
            }
        });

        btnEditAppointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra(ViewActivity.sDateSentToViewAppointment, CalendarFragment.ilEventOccursOn);
                startActivity(intent);
            }
        });

        btnMoveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MoveActivity.class);
                intent.putExtra(MoveActivity.sDateSentToMoveAppointment, CalendarFragment.ilEventOccursOn);
                startActivity(intent);
            }
        });

        btnDeleteAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra(DeleteActivity.sDateSentToDeleteAppointment, CalendarFragment.ilEventOccursOn);
                startActivity(intent);

            }
        });
    }
}
