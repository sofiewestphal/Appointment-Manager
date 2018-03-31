package com.example.sofie.appointmentmanagementapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class SearchActivity extends Activity{
    long ilCurrentTime;
    long ilCurrentTimeMilli;
    String sCurrentDate;
    AppointmentData appointments;
    //TextView tvDisplaySearchedAppointments;
    LinearLayout layoutAppointmentContainer;
    Button btnSearchAppointment;
    EditText etStringToSearch;
    String sSearchedString;
    TextView tvSearchedAppointmentDetails;
    public static int iAppointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //tvDisplaySearchedAppointments = findViewById(R.id.tvDisplaySearchedAppointments);
        btnSearchAppointment = findViewById(R.id.btnSearchAppointment);
        etStringToSearch = findViewById(R.id.etStringToSearch);
        layoutAppointmentContainer = findViewById(R.id.layoutAppointmentContainer);
        appointments = new AppointmentData(this);

        layoutAppointmentContainer.removeAllViews();

        getDate();
        initButton();
    }

    public void getDate(){
        Calendar c = Calendar.getInstance();
        // get the current time to use as default value for the event to be created.
        ilCurrentTime = c.get(Calendar.MILLISECOND);
        ilCurrentTimeMilli = c.getTimeInMillis();

        // set the calendar instance to the chosen date
        c.setTimeInMillis(ilCurrentTime);

        // get the day, month and year out of the time in milliseconds
        int iYear = c.get(Calendar.YEAR);
        int iMonth = c.get(Calendar.MONTH);
        String sMonth = getMonth(iMonth);
        int iDay = c.get(Calendar.DAY_OF_MONTH);

        // create a string that display the day in DD:MM:YYYY format
        sCurrentDate = iDay + ". " + sMonth + " " + iYear;
    }

    public String getMonth(int month) {
        // return the month in string format
        return new DateFormatSymbols().getMonths()[month];
    }

    public void initButton(){
        btnSearchAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutAppointmentContainer.removeAllViews();

                sSearchedString = etStringToSearch.getText().toString().toLowerCase();
                try {
                    Cursor cursor = getAppointments();
                    showAppointments(cursor);
                } finally {
                    appointments.close();
                }
            }
        });
    }

    public Cursor getAppointments(){
        String[] FROM = { "_id", "date", "title", "time", "description, milliseconds", };
        String ORDER_BY = "milliseconds ASC";
        String WHERE = "milliseconds>=?";

        SQLiteDatabase db = appointments.getReadableDatabase();
        Cursor cursor = db.query(AppointmentData.TABLE_NAME, FROM, WHERE, new String[]{String.valueOf(ilCurrentTimeMilli)}, null,
                null, ORDER_BY);

        return cursor;
    }

    private void showAppointments(Cursor cursor) {

        // create a string builder used to display the appointments
        StringBuilder builder;

        while (cursor.moveToNext()) {
            // save the id of the record in the array of ids aiAppointmentIds
            int iId = cursor.getInt(0);
            String sDate = cursor.getString(1);
            String sTitle = cursor.getString(2);
            String sTime = cursor.getString(3);
            String sDetails = cursor.getString(4);
            if (sTitle.toLowerCase().contains(sSearchedString) || sDetails.toLowerCase().contains(sSearchedString)) {
                builder = new StringBuilder();
                builder.append(sDate).append(": ");
                builder.append(sTitle).append(": ");
                builder.append(sTime).append("\n");
                TextView tv = new TextView(this);
                Button btn = new Button(this);
                tv.setId(123 + iId);
                tv.setText(builder);
                btn.setId(iId);
                btn.setText("Show details");
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutAppointmentContainer.addView(tv, layoutParams);
                layoutAppointmentContainer.addView(btn, layoutParams);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iAppointmentId = view.getId();
                        showDetailsOfAppointment();
                    }
                });
            }
        }
    }

    public void showDetailsOfAppointment(){
        // I use the FragmentManager to load the AppointmentDetailsFragment
        Fragment AppointmentDetailsFragment = new AppointmentDetailsFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fl_search_activity, AppointmentDetailsFragment);
        fragmentTransaction.commit();
    }
}
