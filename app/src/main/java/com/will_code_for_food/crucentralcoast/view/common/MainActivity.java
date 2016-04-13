package com.will_code_for_food.crucentralcoast.view.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.will_code_for_food.crucentralcoast.model.common.messaging.RegistrationIntentService;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.email.EmailSender;
import com.will_code_for_food.crucentralcoast.controller.crash_reports.CrashReport;
import com.will_code_for_food.crucentralcoast.controller.crash_reports.CrashReportExceptionHandler;
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

    private void handleUncaughtExceptions() {
        final Thread.UncaughtExceptionHandler oldHandler =
                Thread.getDefaultUncaughtExceptionHandler();
        CrashReportExceptionHandler newHandler = new CrashReportExceptionHandler();
        newHandler.setActivityAndHandler(this, oldHandler);
        Thread.setDefaultUncaughtExceptionHandler(newHandler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleUncaughtExceptions();
        sendCachedReports();

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        notifier = new Notifier();
        titleStack = new Stack<>();

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

        //if (doFeedLoad) {
            //loadFragmentById(R.layout.fragment_main, "CruCentralCoast", null, this); //Uncomment this for original main screen
        //}

        doFeedLoad = true;
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent_cruBrightBlue)));
        TypeFaceUtil.overrideFont(getApplicationContext(), getResources().getString(R.string.default_serif), getResources().getString(R.string.new_default));
    }

    public void sendCachedCrashReport() {
        sendCrashReport(null, true);
    }

    public void sendCrashReport() {
        sendCrashReport(null, false);
    }

    private void sendCrashReport(final Throwable ex, final boolean cached) {
        // get xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.fragment_crash_report, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final Activity act = this;
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.cr_user_message);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Bug Report")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!cached) {
                            EmailSender.send(act, new CrashReport(ex, userInput.getText().toString()).asMessage());
                        } else {
                            CrashReport report = CrashReport.loadCachedReport();
                            if (report != null) {
                                EmailSender.send(act, report.asMessage());
                            }
                        }
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CrashReport.deleteCache();
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void sendCachedReports() {
        Log.i("Crash Report", "Checking for cached crash reports");
        if (CrashReport.cacheReportExists()) {
            Log.i("Crash Report", "Found cached report");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bug Report");
            builder.setMessage("Looks like the app crashed the last time you used it! "
                    + "Would you like to send us a quick bug report?");
            builder.setPositiveButton("Sure!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    sendCachedCrashReport();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CrashReport.deleteCache();
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
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
                //loadFragmentById(R.layout.fragment_card_list, "Home", new FeedFragment(), this);
                newActivity(HomeActivity.class);
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
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); //clear all fragments
        Intent intent = new Intent(this, newClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Destroys a support fragment, releasing its fragment id. If you have a mapfragment inside a CruFragment,
     * you need to call this in the CruFragment's onDestroyView() method.
     */
    public void destroySupportFragment(int id) {
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(id);
        getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    /**
     * Destroys a fragment, releasing its fragment id.
     */
    public void destroyFragment(int id) {
        Fragment fragment = getFragmentManager().findFragmentById(id);
        getFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    /**
     * Overrides the touch event to un-focus any objects that
     * weren't clicked on. Could be changed to only apply to
     * specific types of elements, like text fields.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
