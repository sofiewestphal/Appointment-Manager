package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class DeleteChooseFragment extends Fragment {

    // VARIABLES
    TextView tvDisplayAppointments;
    AppointmentData appointments;
    int[] aiAppointmentIds;
    int inumberOfAppointmentEntries;
    View btnDeleteChosenAppointment;
    EditText etNumberToDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_delete_choose, container, false);

        tvDisplayAppointments = (TextView) rootView.findViewById(R.id.tvDisplayAppointments);
        btnDeleteChosenAppointment = rootView.findViewById(R.id.btnDeleteChosenAppointment);
        etNumberToDelete = (EditText) rootView.findViewById(R.id.etNumberToDelete);

        initButtons();

        appointments = new AppointmentData(getActivity());
        try {
            Cursor cursor = getAppointmentOfTheDay();
            showAppointmentsOfTheDay(cursor);
        } finally {
            appointments.close();
        }

        return rootView;
    }

    private Cursor getAppointmentOfTheDay() {
        String[] FROM = { "_id", "date", "title", "time", "description", };
        String ORDER_BY = "time ASC";
        String WHERE = "date=?";

        SQLiteDatabase db = appointments.getReadableDatabase();
        Cursor cursor = db.query(AppointmentData.TABLE_NAME, FROM, WHERE, new String[]{DeleteActivity.sDateOfAppointment}, null,
                null, ORDER_BY);

        long ilnumberOfAppointmentEntries = DatabaseUtils.queryNumEntries(db, AppointmentData.TABLE_NAME);
        inumberOfAppointmentEntries = (int) ilnumberOfAppointmentEntries;
        return cursor;
    }

    private void showAppointmentsOfTheDay(Cursor cursor) {

        // create a string builder used to display the appointments
        StringBuilder builder = new StringBuilder();
        int iCounterAppointments = 0;
        //create a new array to store the ids of the appointments
        aiAppointmentIds = new int[inumberOfAppointmentEntries];

        while (cursor.moveToNext()) {
            // save the id of the record in the array of ids aiAppointmentIds
            int iId = cursor.getInt(0);
            aiAppointmentIds[iCounterAppointments] = iId;

            //get the columns of the record from the cursor and add them to the string builder
            iCounterAppointments++;
            String sTitle = cursor.getString(2);
            String sTime = cursor.getString(3);
            String sDetails = cursor.getString(4);
            builder.append(iCounterAppointments).append(". ");;
            builder.append(sTitle).append(": ");
            builder.append(sTime).append(": ");
            builder.append(sDetails).append("\n");
        }
        // Display the string with the appiontments on the screen
        tvDisplayAppointments.setText(builder);
    }

    public void initButtons(){
        btnDeleteChosenAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the number of the appointment the user has chosen to delete
                String siChosenIdToDelete = etNumberToDelete.getText().toString();
                int iChosenIdToDelete = Integer.parseInt(siChosenIdToDelete);

                // replace the DeleteChooseFragment with DeleteConfirmFragment
                Fragment DeleteConfirmFragment = new DeleteConfirmFragment();
                // set the variable iChosenIdToDelete in DeleteActivity equal to the id of the appointment the user
                // wants to delete
                DeleteActivity.iChosenIdToDelete = aiAppointmentIds[iChosenIdToDelete - 1];
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fl_delete_activity, DeleteConfirmFragment);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
