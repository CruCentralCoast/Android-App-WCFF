package com.will_code_for_food.crucentralcoast;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.CalendarAccessor;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;
import com.will_code_for_food.crucentralcoast.model.common.common.CalendarEvent;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;
import com.will_code_for_food.crucentralcoast.temp.CampusExampleTask2;

import java.util.Calendar;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Notifier notifier;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Stack<String> titleStack;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notifier = new Notifier();
        titleStack = new Stack<String>();

        context = this;

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
                loadNewFragment(view, position);
            }
        });

        loadFragmentById(R.layout.fragment_main, "CruCentralCoast");
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
        if (getFragmentManager().getBackStackEntryCount() > 0) {
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
        loadFragmentById(R.layout.fragment_campuses, "Select a Campus");
        new CampusExampleTask2().execute();
    }

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

    private void loadNewFragment(View view, int position) {
        int loadId;
        String title = "";

        TextView tView = (TextView) view;
        String viewText = tView.getText().toString();

        switch (viewText) {
            case "Events":
                loadId = R.layout.fragment_event;
                title = "Events";
                break;
            case "Resources":
                loadId = R.layout.fragment_resources;
                title = "Resources";
                break;
            case "Get Involved":
                loadId = R.layout.fragment_get_involved;
                title = "Get Involved";
                break;
            case "Settings":
                loadId = R.layout.fragment_settings;
                title = "Settings";
                break;
            default:
                loadId = R.layout.fragment_main;
                title = "CruCentralCoast";
                break;
        }

        loadFragmentById(loadId, title);
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void loadFragmentById(int loadId, String newTitle) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        titleStack.clear();

        Fragment fragment = new CruFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("id", loadId);
        args.putString("name", newTitle);
        fragment.setArguments(args);

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();

        titleStack.push(getTitle().toString());
        setTitle(newTitle);
    }
}
