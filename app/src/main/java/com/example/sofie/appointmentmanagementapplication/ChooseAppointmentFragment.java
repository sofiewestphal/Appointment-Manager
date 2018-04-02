package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ChooseAppointmentFragment extends Fragment {
    // VARIABLES
    TextView tvDisplayAppointments;
    AppointmentData appointments;
    public static int[] aiAppointmentIds;
    int inumberOfAppointmentEntries;
    public static EditText etNumberToDelete;
    public static String sDateofAppointment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_choose_appointment, container, false);

        tvDisplayAppointments = (TextView) rootView.findViewById(R.id.tvDisplayAppointments);
        etNumberToDelete = (EditText) rootView.findViewById(R.id.etNumberToDelete);

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
        Cursor cursor = db.query(AppointmentData.TABLE_NAME, FROM, WHERE, new String[]{sDateofAppointment}, null,
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
}
