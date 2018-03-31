package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DeleteConfirmFragment extends Fragment {

    // VARIABLES
    int iChosenIdToDelete;
    AppointmentData appointments;
    TextView tvDisplayAppointmentToDelete;
    Button btnDeleteYes;
    Button btnDeleteNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_delete_confirm, container, false);

        tvDisplayAppointmentToDelete = (TextView) rootView.findViewById(R.id.tvDisplayAppointmentToDelete);
        btnDeleteYes = (Button) rootView.findViewById(R.id.btnDeleteYes);
        btnDeleteNo = (Button) rootView.findViewById(R.id.btnDeleteNo);
        iChosenIdToDelete = DeleteActivity.iChosenIdToDelete;

        appointments = new AppointmentData(getActivity());
        try {
            Cursor cursor = getAppointmentToDelete();
            showAppointmentToDelete(cursor);
        } finally {
            appointments.close();
        }

        initButtons();

        return rootView;
    }

    public Cursor getAppointmentToDelete(){
        String[] FROM = { "title", };
        String WHERE = "_id=?";

        SQLiteDatabase db = appointments.getReadableDatabase();
        Cursor cursor = db.query(AppointmentData.TABLE_NAME, FROM, WHERE, new String[]{String.valueOf(DeleteActivity.iChosenIdToDelete)}, null,
                null, null);
        return cursor;
    }

    public void showAppointmentToDelete(Cursor cursor) {
        String sTitle = "Title of appointment";
        while (cursor.moveToNext()) {
            sTitle = cursor.getString(0);
        }
        tvDisplayAppointmentToDelete.setText(sTitle);
    }

    public void initButtons() {
        btnDeleteYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete the record where the id = the id chosen by the user to delete
                String sWhereClause = "_id =?";

                SQLiteDatabase db = appointments.getWritableDatabase();
                db.delete(AppointmentData.TABLE_NAME, sWhereClause, new String[]{Integer.toString(iChosenIdToDelete)});

                //retun to main screen
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btnDeleteNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use the fragment manager to replace DeleteConfirmFragment with DeleteChooseFragment
                Fragment DeleteChooseFragment = new DeleteChooseFragment();

                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fl_delete_activity, DeleteChooseFragment);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
