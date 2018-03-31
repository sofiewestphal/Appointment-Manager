package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DeleteMainFragment extends Fragment {

    // VARIABLES
    AppointmentData appointments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_delete_main, container, false);

        View btnDeleteAll = rootView.findViewById(R.id.btnDeleteAll);
        View btnChooseWhichToDelete = rootView.findViewById(R.id.btnChooseWhichToDelete);

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appointments = new AppointmentData(getActivity());
                try {
                    deleteAppointmentOfTheDay();
                } finally {
                    appointments.close();
                }
            }
        });

        btnChooseWhichToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //use the fragment manager to replace DeleteMainFragment with DeleteChooseFragment
                Fragment DeleteChooseFragment = new DeleteChooseFragment();

                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fl_delete_activity, DeleteChooseFragment);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

    public void deleteAppointmentOfTheDay(){
        // delete all records in the database where the date = the date chosen to delete appointments from
        String sWhereClause = "date=?";

        SQLiteDatabase db = appointments.getWritableDatabase();
        db.delete(AppointmentData.TABLE_NAME, sWhereClause, new String[]{DeleteActivity.sDateOfAppointment});
        confirmDeletion();
    }

    public void confirmDeletion(){

        Context context = getActivity().getApplicationContext();
        CharSequence text = "The appointments have been deleted.";
        int duration = Toast.LENGTH_SHORT;

        Toast tConfirmCreation = Toast.makeText(context, text, duration);
        tConfirmCreation.show();
    }
}
