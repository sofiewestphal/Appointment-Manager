package com.example.sofie.appointmentmanagementapplication;

import android.app.Activity;
import android.os.Bundle;

public class SearchActivity extends Activity{
    public static String sDateSentToSearchAppointment = "searchAppointmentsFrom";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }
}
