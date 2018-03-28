package com.example.sofie.appointmentmanagementapplication;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;


public class ThesarusTask implements Runnable{
    private static final String TAG = "ThesarusTask";
    private final CreateActivity suggest;
    private final String word;

    ThesarusTask(CreateActivity context, String word) {
        this.suggest = context;
        this.word = word;
    }

    public void run() {
        // Get suggestions for the original text
        List<String> suggestions = doSuggest(word);
        suggest.setSuggestions(suggestions);
    }

    private List<String> doSuggest(String city) {
        List<String> messages = new LinkedList<String>();
        String error = null;
        HttpURLConnection con = null;
        Log.d(TAG, "doSuggest(" + word + ")");

        try {
            // Check if task has been interrupted
            if (Thread.interrupted())
                throw new InterruptedException();

            // Build RESTful query for Google API
            String key = "t1Hi03C0ugmm4qbKbH7C";
            String language = "en_US";
            String q = URLEncoder.encode(city, "UTF-8");
            URL url = new URL(
                    "http://thesaurus.altervista.org/thesaurus/v1?key=t1Hi03C0ugmm4qbKbH7C&language=en_US&word="
                            + q);
            con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(10000 /* milliseconds */);
            con.setConnectTimeout(15000 /* milliseconds */);
            con.setRequestMethod("GET");
            con.setDoInput(true);

            // Start the query
            con.connect();

            // Check if task has been interrupted
            if (Thread.interrupted())
                throw new InterruptedException();

            // Read results from the query
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(con.getInputStream(), null);
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();

                if (eventType == XmlPullParser.START_TAG && name.equalsIgnoreCase("synonyms")) {
                    messages.add(parser.nextText());
                }

                eventType = parser.next();
            }

            // Check if task has been interrupted
            if (Thread.interrupted())
                throw new InterruptedException();

        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
            error = suggest.getResources().getString(R.string.error)
                    + " " + e.toString();
        } catch (XmlPullParserException e) {
            Log.e(TAG, "XmlPullParserException", e);
            error = suggest.getResources().getString(R.string.error)
                    + " " + e.toString();
        } catch (InterruptedException e) {
            Log.d(TAG, "InterruptedException", e);
            error = suggest.getResources().getString(
                    R.string.interrupted);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // If there was an error, return the error by itself
        if (error != null) {
            messages.clear();
            messages.add(error);
        }

        // Print something if we got nothing
        if (messages.size() == 0) {
            messages.add(suggest.getResources().getString(R.string.no_results));
        }

        // All done
        Log.d(TAG, "   -> returned " + messages);
        return messages;
    }
}
