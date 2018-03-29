package com.example.sofie.appointmentmanagementapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AppointmentDetailsFragment extends Fragment {
    AppointmentData appointments;
    public static TextView tvSearchedAppointmentDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_appointment_details, container, false);
        tvSearchedAppointmentDetails = rootView.findViewById(R.id.tvSearchedAppointmentDetails);
        appointments = new AppointmentData(getActivity());
        try {
            Cursor cursor = getAppointmentDetails();
            showAppointmentDetails(cursor);
        } finally {
            appointments.close();
        }

        return rootView;
    }

    public Cursor getAppointmentDetails(){
        String[] FROM = { "_id", "date", "title", "time", "description", };
        String WHERE = "_id=?";

        SQLiteDatabase db = appointments.getReadableDatabase();
        Cursor cursor = db.query(AppointmentData.TABLE_NAME, FROM, WHERE, new String[]{String.valueOf(SearchActivity.iAppointmentId)}, null,
                null, null);

        return cursor;
    }

    public void showAppointmentDetails(Cursor cursor){
        // create a string builder used to display the appointments
        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()) {
            // save the id of the record in the array of ids aiAppointmentIds
            String sDate = cursor.getString(1);
            String sTitle = cursor.getString(2);
            String sTime = cursor.getString(3);
            String sDetails = cursor.getString(4);
            builder.append(sDate).append(": ");
            builder.append(sTitle).append(": ");
            builder.append(sTime).append(": ");
            builder.append(sDetails).append("\n");
        }
        Log.v("from db", builder+"");
        /*int resId = getResources().getIdentifier(123 + SearchActivity.iAppointmentId+"", "id", "com.example.sofie.appointmentmanagementapplication");
        TextView tv = getView().findViewById(resId);
        tv.setText(builder);*/
        //tvSearchedAppointmentDetails.setText(builder);

        createDialog(builder);

    }

    public void createDialog(final StringBuilder sDetails){

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(sDetails).setPositiveButton(R.string.lblOk, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        // Create the AlertDialog object and show it
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
