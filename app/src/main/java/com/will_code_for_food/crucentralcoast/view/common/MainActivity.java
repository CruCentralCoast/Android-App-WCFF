package com.will_code_for_food.crucentralcoast.view.common;

import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.will_code_for_food.crucentralcoast.model.common.messaging.RegistrationIntentService;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;
import com.will_code_for_food.crucentralcoast.view.events.EventsActivity;
import com.will_code_for_food.crucentralcoast.view.getinvolved.GetInvolvedActivity;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.view.resources.ResourcesActivity;
import com.will_code_for_food.crucentralcoast.view.ridesharing.RideShareActivity;
import com.will_code_for_food.crucentralcoast.view.ridesharing.WorkaroundMapFragment;
import com.will_code_for_food.crucentralcoast.view.summermissions.SummerMissionsActivity;
import com.will_code_for_food.crucentralcoast.model.common.messaging.Notifier;
import com.will_code_for_food.crucentralcoast.model.resources.TypeFaceUtil;

import java.util.Stack;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    Notifier notifier;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Stack<String> titleStack;
    public static Context context;
    private static boolean doFeedLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
                loadNewScreen(view, position);
            }
        });

        if (doFeedLoad) {
            loadFragmentById(R.layout.fragment_card_list, "Home", new FeedFragment(), this); //Uncomment this for feed main screen
            //loadFragmentById(R.layout.fragment_main, "CruCentralCoast", null, this); //Uncomment this for original main screen
        }

        doFeedLoad = true;



        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent_cruBrightBlue)));
        TypeFaceUtil.overrideFont(getApplicationContext(), getResources().getString(R.string.default_serif), getResources().getString(R.string.new_default));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
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

    private void addDrawerItems() {
        String[] osArray = {"Home", "Events", "Resources", "Summer Missions", "Get Involved",
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

    private void loadNewScreen(View view, int position) {
        int loadId;
        String title = "";

        TextView tView = (TextView) view;
        String viewText = tView.getText().toString();


        switch (viewText) {
            case "Home":
                loadFragmentById(R.layout.fragment_card_list, "Home", new FeedFragment(), this);
                break;
            case "Events":
                newActivity(EventsActivity.class);
                break;
            case "Resources":
                newActivity(ResourcesActivity.class);
                break;
            case "Get Involved":
                newActivity(GetInvolvedActivity.class);
                break;
            case "Summer Missions":
                newActivity(SummerMissionsActivity.class);
                break;
            case "Ride Share":
                newActivity(RideShareActivity.class);
                break;
            case "Settings":
                newActivity(SettingsActivity.class);
                break;
            default:
                loadId = R.layout.fragment_main;
                title = "CruCentralCoast";
                loadFragmentById(loadId, title, null, this);
                break;
        }

        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    // TODO: 1/19/16 Change Settings fragment so it isn't created only programmatically- use XML!
    public void loadFragmentById(int loadId, String newTitle, Fragment fragment, MainActivity parent) {
        FragmentManager fragmentManager = getFragmentManager();

        //if no associated controller code for this fragment
        if (fragment == null) {
            fragment = new CruFragment();
        }

        // the fragment could be a PrefsFragment, so we have to add this check
        if (fragment instanceof CruFragment) {
            ((CruFragment)fragment).setParent(parent);
        }

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

    public void newActivity(Class newClass) {
        doFeedLoad = false;
        Intent intent = new Intent(this, newClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public WorkaroundMapFragment getMapFragment(int id) {
        return (WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(id);
    }

    /**
     * Destroys a map fragment, releasing its fragment id. If you have a mapfragment inside a CruFragment,
     * you need to call this in the CruFragment's onDestroyView() method.
     */
    public void destroyMapFragment(int id) {
        getSupportFragmentManager().beginTransaction().remove(getMapFragment(id)).commitAllowingStateLoss();
    }
}
