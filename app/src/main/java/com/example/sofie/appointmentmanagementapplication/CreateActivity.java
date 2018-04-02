package com.example.sofie.appointmentmanagementapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

public class CreateActivity extends Activity {

    // VARIABLES
    long ilCurrentTime;
    long ilEventOccursOn;
    long ilEventOccursMilli;
    AppointmentData appointments;
    public static String sDateSentToCreateAppointment = "ilEventOccursOn";

    public static String sDateOfAppointment;
    EditText inputTitleOfAppointment;
    EditText inputTimeOfAppointment;
    EditText inputDetailsOfAppointment;
    Button btnSaveAppointment;
    boolean bTitleExists = false;


    private EditText etInputCity;
    private ListView suggList;
    private Button btnThesaurus;
    private Handler guiThread;
    private ExecutorService suggThread;
    private Runnable updateTask;
    private Future<?> suggPending;
    private List<String> items;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        modifyEditText();
        initSaveButton();
        setTheDateOfTheAppointment();
        getTheDateOfTheAppointment();
        setDate();

        initThesatus();
        setAdapters();
        initThreading();
    }

    public void modifyEditText(){
        inputDetailsOfAppointment = findViewById(R.id.inputDetailsOfAppointment);
        inputDetailsOfAppointment.setImeOptions(EditorInfo.IME_ACTION_DONE);
        inputDetailsOfAppointment.setRawInputType(InputType.TYPE_CLASS_TEXT);

        etInputCity = (EditText) findViewById(R.id.etInputCity);
        etInputCity.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etInputCity.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    public void setTheDateOfTheAppointment() {
        ChosenDateFragment.ilEventOccursOn = getIntent().getLongExtra(sDateSentToCreateAppointment, ChosenDateFragment.ilCurrentTime);
        ilEventOccursOn = ChosenDateFragment.ilEventOccursOn;
    }

    public void getTheDateOfTheAppointment(){
        sDateOfAppointment = ChosenDateFragment.returnChosenDate();
    }

    public void setDate(){
        ChooseAppointmentFragment.sDateofAppointment = sDateOfAppointment;
    }

    public void initSaveButton(){
        btnSaveAppointment = findViewById(R.id.btnSaveAppointment);

        btnSaveAppointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                inputTitleOfAppointment = findViewById(R.id.inputTitleOfAppointment);
                String sEventTitle = inputTitleOfAppointment.getText().toString();

                appointments = new AppointmentData(CreateActivity.this);
                try {
                    Cursor cursor = getTitlesOfAppointments(sDateOfAppointment);
                    bTitleExists = checkTitlesOfAppointments(cursor, sEventTitle);
                } finally {
                    appointments.close();
                }

                // loop through the titles of the appointments on the current day
                // if a title equals the title the user wants to save:
                    // show pop up error window sayin the appointment already exist
                if(bTitleExists){
                    Context context = getApplicationContext();
                    CharSequence text = "The title already exists";
                    int duration = Toast.LENGTH_SHORT;

                    Toast tConfirmCreation = Toast.makeText(context, text, duration);
                    tConfirmCreation.show();
                }else{
                    // Get the user input with information about the appointment she/he wants to create

                    inputTimeOfAppointment = findViewById(R.id.inputTimeOfAppointment);
                    String sEventTime = inputTimeOfAppointment.getText().toString();
                    String sEventDescription = inputDetailsOfAppointment.getText().toString();

                    appointments = new AppointmentData(CreateActivity.this);
                    try {
                        addAppointment(sDateOfAppointment, sEventTitle, sEventTime, sEventDescription, ilEventOccursOn);
                        confirmCreation();
                    } finally {
                        appointments.close();
                    }
                }
            }
        });
    }

    public Cursor getTitlesOfAppointments(String sDateOfAppointment){
        // Get the title of all the appointments for the current day
        SQLiteDatabase db = appointments.getReadableDatabase();
        // Get the title of the appointment the user want to create
        String[] FROM = { "title", };
        String WHERE = "date=?";
        Cursor cursor = db.query(AppointmentData.TABLE_NAME, FROM, WHERE, new String[]{sDateOfAppointment}, null,
                null, null);
        return cursor;
    }

    public boolean checkTitlesOfAppointments(Cursor cursor, String sEventTitle){
        boolean bTitleExists = false;
        while (cursor.moveToNext()) {
            String sTitle = cursor.getString(0);
            if (sTitle.equals(sEventTitle)){
                bTitleExists = true;
            }
        }
        return bTitleExists;
    }

    public void confirmCreation(){
        inputTitleOfAppointment.setText("");
        inputTimeOfAppointment.setText("");
        inputDetailsOfAppointment.setText("");
        Context context = getApplicationContext();
        CharSequence text = "Your appointment has been saved.";
        int duration = Toast.LENGTH_SHORT;

        Toast tConfirmCreation = Toast.makeText(context, text, duration);
        tConfirmCreation.show();
    }

    public void addAppointment(String sDateOfAppointment, String sEventTitle, String sEventTime, String sEventDescription, long ilMilliSeconds){

        // get a new writable instance of the database
        SQLiteDatabase db = appointments.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", sDateOfAppointment);
        values.put("title", sEventTitle);
        values.put("time", sEventTime);
        values.put("description", sEventDescription);
        values.put("milliseconds", ilMilliSeconds);

        db.insertOrThrow(AppointmentData.TABLE_NAME, null, values);
    }

    private void setAdapters() {
        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        suggList.setAdapter(adapter);
    }

    public void initThesatus(){
        suggList = (ListView) findViewById(R.id.lvResultList);
        btnThesaurus = (Button) findViewById(R.id.btnThesaurus);

        btnThesaurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queueUpdate(0 /* milliseconds */);
            }
        });
    }

    private void initThreading() {
        guiThread = new Handler();
        suggThread = Executors.newSingleThreadExecutor();

        // This task gets suggestions and updates the screen
        updateTask = new Runnable() {
            public void run() {
                // Get text to suggest
                String word = etInputCity.getText().toString().trim();
                // Cancel previous suggestion if there was one
                if (suggPending != null){
                    suggPending.cancel(true);
                }

                // Check to make sure there is text to work on
                if (word.length() != 0) {
                    // Let user know we're doing something
                    setText(R.string.working);
                    // Begin suggestion now but don't wait for it
                    try {
                        ThesarusTask thesarusTask = new ThesarusTask(CreateActivity.this, word);
                        suggPending = suggThread.submit(thesarusTask);
                    } catch (RejectedExecutionException e) {
                        // Unable to start new task
                        setText(R.string.error);
                    }
                }
            }
        };
    }

    /** Request an update to start after a short delay */
    private void queueUpdate(long delayMillis) {
        // Cancel previous update if it hasn't started yet
        guiThread.removeCallbacks(updateTask);
        // Start an update if nothing happens after a 1000 milliseconds
        guiThread.postDelayed(updateTask, delayMillis);
    }

    /** Modify list on the screen (called from another thread) */
    public void setSuggestions(List<String> suggestions) {
        guiSetList(suggList, suggestions);
    }

    /** All changes to the GUI must be done in the GUI thread */
    private void guiSetList(final ListView view, final List<String> list) {
        guiThread.post(new Runnable() {
            public void run() {
                setList(list);
            }
        });
    }

    /** Display a message */
    private void setText(int id) {
        adapter.clear();
        adapter.add(getResources().getString(id));
    }

    /** Display a list */
    private void setList(List<String> list) {
        adapter.clear();
        adapter.addAll(list);
    }
}
