package com.example.sofie.appointmentmanagementapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class DeleteActivity extends Activity {

    // VARIABLES
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
        setDate();

    }

    public void addFragmentMain(){
        // I use the FragmentManager to load the MainFragment
        Fragment DeleteMainFragment = new DeleteMainFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fl_delete_activity, DeleteMainFragment);
        fragmentTransaction.commit();
    }

    public void setTheDateOfTheAppointment() {
        ChosenDateFragment.ilEventOccursOn = getIntent().getLongExtra(sDateSentToDeleteAppointment, ChosenDateFragment.ilCurrentTime);
    }

    public void getTheDateOfTheAppointment(){
        sDateOfAppointment = ChosenDateFragment.returnChosenDate();
    }

    public void setDate(){
        ChooseAppointmentFragment.sDateofAppointment = sDateOfAppointment;
    }
}
