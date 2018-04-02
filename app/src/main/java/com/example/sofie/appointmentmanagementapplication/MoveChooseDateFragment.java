package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MoveChooseDateFragment extends Fragment {
    //VARIABLES
    AppointmentData appointments;
    int iChosenIdToDelete;
    TextView tvAppointmentToMove;
    Button btnConfirmMove;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_move_choose_date, container, false);

        tvAppointmentToMove = rootView.findViewById(R.id.tvAppointmentToMove);
        btnConfirmMove = rootView.findViewById(R.id.btnConfirmMove);

        iChosenIdToDelete = MoveActivity.iChosenIdToDelete;
        appointments = new AppointmentData(getActivity());
        try {
            Cursor cursor = getAppointmentToMove();
            showAppointmentToMove(cursor);
        } finally {
            appointments.close();
        }

        initBtnMove();
        return rootView;
    }

    public Cursor getAppointmentToMove(){
        String[] FROM = { "title", };
        String WHERE = "_id=?";

        SQLiteDatabase db = appointments.getReadableDatabase();
        Cursor cursor = db.query(AppointmentData.TABLE_NAME, FROM, WHERE, new String[]{String.valueOf(MoveActivity.iChosenIdToDelete)}, null,
                null, null);
        return cursor;
    }

    public void showAppointmentToMove(Cursor cursor){
        String sTitle = "Title of appointment";
        while (cursor.moveToNext()) {
            sTitle = cursor.getString(0);
        }
        tvAppointmentToMove.setText(sTitle);

    }

    public void initBtnMove(){
        btnConfirmMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long ilEventOccursOn = CalendarFragment.ilEventOccursOn;
                String sDateOfAppointment = CalendarFragment.sDateOfAppointment;
                Log.v("new date is", sDateOfAppointment);

                ContentValues cv = new ContentValues();
                cv.put("date",sDateOfAppointment);
                cv.put("milliseconds",ilEventOccursOn);
                String sWhereClause = "_id =?";

                SQLiteDatabase db = appointments.getWritableDatabase();
                db.update(AppointmentData.TABLE_NAME, cv, sWhereClause, new String[]{Integer.toString(iChosenIdToDelete)});

                //retun to main screen
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }
}
