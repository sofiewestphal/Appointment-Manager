package com.example.sofie.appointmentmanagementapplication;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

public class MoveActivity extends Activity {
    // VARIABLES
    public static String sDateOfAppointment;
    public static int iChosenIdToDelete = 0;
    public static final String sDateSentToMoveAppointment = "ilEventOccursOn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        addFragmentMain();
        setTheDateOfTheAppointment();
        getTheDateOfTheAppointment();
        setDate();

    }

    public void addFragmentMain(){
        // I use the FragmentManager to load the MainFragment
        Fragment MoveMainFragment = new MoveMainFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fl_move_activity, MoveMainFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setTheDateOfTheAppointment() {
        ChosenDateFragment.ilEventOccursOn = getIntent().getLongExtra(sDateSentToMoveAppointment, ChosenDateFragment.ilCurrentTime);
    }

    public void getTheDateOfTheAppointment(){
        sDateOfAppointment = ChosenDateFragment.returnChosenDate();
    }

    public void setDate(){
        ChooseAppointmentFragment.sDateofAppointment = sDateOfAppointment;
    }
}
