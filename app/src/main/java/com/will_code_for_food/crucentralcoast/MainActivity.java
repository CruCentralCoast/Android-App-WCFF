package com.will_code_for_food.crucentralcoast;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;
import com.will_code_for_food.crucentralcoast.model.common.components.CalendarEvent;
import com.will_code_for_food.crucentralcoast.model.common.components.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.components.RestUtil;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Notifier notifier;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Stack<String> titleStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notifier = new Notifier();
        titleStack = new Stack<String>();

        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();
        mDrawerList.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupDrawer();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadNewFragment(position);
            }
        });

        Fragment fragment = new CruFragment(R.layout.fragment_main, "CruCentralCoast");

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
            //if (getActiveFragment() != null) {
                setTitle(titleStack.pop());
            //}

        } else {
            super.onBackPressed();
        }
    }

    public void testCalendar(View view) {
        // building test event
        CalendarEvent event = new CalendarEvent("Leave for CRU Event", "This is a cru event " +
                "that should be added to the users calendar at this exact time.", "PAC Circle",
                Calendar.getInstance());
        CalendarAccessor.addEventToCalendar(event, this);
    }

    public void testCalendarEdit(View view) {
        // building test event
        CalendarEvent event = new CalendarEvent("New Title!", "This is a cru event " +
                "that should be added to the users calendar at this exact time.", "PAC Circle",
                Calendar.getInstance());
        CalendarAccessor.editExistingEvent(event, "Leave for CRU Event", this);
    }

    public void testSMS(View view) {
        //test event for SMS
        SMSHandler.sendSMS(this);
    }

    public void testDB(View view) {
        //Toast.makeText(getApplicationContext(), "first toast", Toast.LENGTH_LONG).show();
        loadFragmentById(R.layout.fragment_ministries, "Ministries");
        new dbTestTask().execute();
    }

    private class dbTestTask extends AsyncTask<Void, Void, Void> {
        //String message = "Ministries:";
        ArrayList<Ministry> ministries;
        ListView ministriesList;
        ArrayList<String> minstriesStrings;

        @Override
        protected Void doInBackground(Void... params) {
            //message += "hello there!";
            ministries = RestUtil.getMinistries();
            minstriesStrings = new ArrayList<String>();

            for (Ministry ministry : ministries) {
                //message += ("\n" + ministry.getName());
                minstriesStrings.add(ministry.getName());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ministriesList = (ListView) findViewById(R.id.ministries_list);

            if ((minstriesStrings != null) && (!minstriesStrings.isEmpty())) {
                ministriesList.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, minstriesStrings));
            }

            else {
                Toast.makeText(getApplicationContext(), "Unable to access ministries", Toast.LENGTH_LONG).show();
            }

            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    /*
    private class MinistriesAdapter extends ArrayAdapter<Ministry> {

        public MinistriesAdapter(Context context, ArrayList<Ministry> ministries) {
            super(context, android.R.layout.simple_list_item_1, ministries);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Ministry ministry = getItem(position);

            return convertView;
        }

    }*/

    public void testYoutube(View view) {
        YoutubeViewer.watchYoutubeVideo("hGcmaztq7eU", this);
    }

    public void testNotifier(View view) {
        notifier.createNotification("title", "text", getApplicationContext());
    }

    private void addDrawerItems() {
        String[] osArray = {"Events", "Resources", "Summer Missions", "Get Involved",
                "Ride Share", "Settings"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void loadNewFragment(int position) {
        loadFragment(position);
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void loadFragment(int position) {
        int loadId;
        String title = "";

        switch (position) {
            case 0:
                loadId = R.layout.fragment_event;
                title = "Events";
                break;
            case 1:
                loadId = R.layout.fragment_resources;
                title = "Resources";
                break;
            case 3:
                loadId = R.layout.fragment_get_involved;
                title = "Get Involved";
                break;
            default:
                loadId = R.layout.fragment_main;
                title = "CruCentralCoast";
                break;
        }

        Fragment fragment = new CruFragment(loadId, title);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        titleStack.clear();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();

        titleStack.push(getTitle().toString());
        setTitle(title);

    }

    private void loadFragmentById(int loadId, String newTitle) {
        Fragment fragment = new CruFragment(loadId, newTitle);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();

        titleStack.push(getTitle().toString());
        setTitle(newTitle);
    }

    /*public CruFragment getActiveFragment() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }

        System.out.println("Count is: " + getFragmentManager().getBackStackEntryCount());

        String id = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();
        System.out.println("tag is: " + id);

        if (getFragmentManager().findFragmentByTag(id) == null) {
            System.out.println("couldn't find fragment");
        }

        return (CruFragment) getFragmentManager().findFragmentByTag(id);
    }*/
}
