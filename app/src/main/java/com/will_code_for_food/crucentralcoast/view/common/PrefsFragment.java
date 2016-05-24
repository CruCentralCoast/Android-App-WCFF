package com.will_code_for_food.crucentralcoast.view.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.email.EmailSender;
import com.will_code_for_food.crucentralcoast.controller.authentication.Authenticator;
import com.will_code_for_food.crucentralcoast.controller.crash_reports.CrashReport;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.common.users.Passenger;
import com.will_code_for_food.crucentralcoast.model.common.messaging.PushUtil;
import com.will_code_for_food.crucentralcoast.values.Android;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Brian on 1/17/2016.
 */
public class PrefsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Activity parent;

    private PreferenceCategory prefsContainer;
    private MultiSelectListPreference campusPref;
    private MultiSelectListPreference ministryPref;
    private Preference clearPref;
    private Preference logoutPref;
    private Preference emailPref;
    private Preference debugPref;
    private CheckBoxPreference setupPref;

    private PrefsFragment thisFrag = this;

    private Set<String> campusSet;
    private Set<String> ministrySet;

    public PrefsFragment() {
        parent = (Activity) SettingsActivity.context;
    }

    private PrefsPasswordDialog popup;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        getComponents();
        setListeners();
        initMinistrySettings();
        initCampusSettings();
        hidePrefs();

        this.getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        //new MinistrySettingsTask().execute();
        //new CampusSettingsTask().execute();
    }

    public void getComponents() {
        prefsContainer = (PreferenceCategory) findPreference(Android.PREF_CONTAINER);
        campusPref = (MultiSelectListPreference) findPreference(Android.PREF_CAMPUSES);
        clearPref = findPreference(Android.PREF_CLEAR);
        logoutPref = findPreference(Android.PREF_LOGOUT);
        emailPref = findPreference(Android.PREF_EMAIL);
        debugPref = findPreference(Android.PREF_DEBUG);
        setupPref = (CheckBoxPreference) findPreference(Android.PREF_SETUP_COMPLETE);
        campusPref = (MultiSelectListPreference) getPreferenceManager().findPreference(Android.PREF_CAMPUSES);
        ministryPref = (MultiSelectListPreference) getPreferenceManager().findPreference(Android.PREF_MINISTRIES);

        campusSet = campusPref.getValues();
        ministrySet = ministryPref.getValues();
    }

    public void setListeners() {

        logoutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Authenticator.logOut();
                Toast.makeText(parent, Util.getString(R.string.logout_message),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        emailPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Logger.i("Bug Report", "Submitting manually");
                ((MainActivity) getActivity()).sendCrashReport();
                return true;
            }
        });



        clearPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                boolean debugEnabled = getPreferenceManager().getSharedPreferences().getBoolean(Android.PREF_DEBUG, false);

                getPreferenceManager().getSharedPreferences().edit().clear().commit();
                getPreferenceManager().getSharedPreferences().edit().putBoolean(Android.PREF_DEBUG, debugEnabled).commit(); //keep debug value
                reload();
                PushUtil.clearPushChannels();
                return false;
            }
        });

        debugPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                //check for pw eventually
                popup = new PrefsPasswordDialog(thisFrag);
                FragmentManager manager = getFragmentManager();
                popup.show(manager, "prefs_enter_password");

                return false;
            }
        });

        ministryPref.setOnPreferenceChangeListener(new MinistryPreferenceListener());
        campusPref.setOnPreferenceChangeListener(new CampusPreferenceListener());
    }

    /**
     * If given the correct password, the preferences page will display debug settings
     */
    public void enableDeveloperOptions(String password) {

        if (popup != null) {
            popup.dismiss();
        }

        if (password.equals(Android.DEBUG_PW)) {
            getPreferenceManager().getSharedPreferences().edit().putBoolean(Android.PREF_DEBUG, true).commit();
            reload();
        } else {
            Toast.makeText(parent, "incorrect password", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Displays either an option to enable debug prefs, or the debug prefs themselves.
     */
    public void hidePrefs() {
        boolean debugEnabled = getPreferenceManager().getSharedPreferences().getBoolean(Android.PREF_DEBUG, false);

        if (debugEnabled) {
            prefsContainer.removePreference(debugPref);
        } else {
            prefsContainer.removePreference(setupPref);
            prefsContainer.removePreference(clearPref);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //reload();
    }

    /**
     * Refreshes the page.
     */
    private void reload() {
        parent.recreate();
    }

    /**
     * Loads content for the ministry settings dialog.
     */
    private void initMinistrySettings() {
        List<Ministry> ministries = DBObjectLoader.getMinistries();

        Set<String> selectedCampuses = Util.loadStringSet(Android.PREF_CAMPUSES);
        ArrayList<String> displayedMinistryIds = new ArrayList<String>();
        ArrayList<String> displayedMinistryNames = new ArrayList<String>();

        if (selectedCampuses != null && !selectedCampuses.isEmpty()) {
            ministryPref.setEnabled(true);

            for (String campus : selectedCampuses) {
                for (Ministry ministry : ministries) {
                    if (ministry.getCampuses().contains(campus)) {
                        displayedMinistryIds.add(ministry.getId());
                        displayedMinistryNames.add(ministry.getName());
                    }
                }
            }

            ministryPref.setEntries(displayedMinistryNames.toArray(new CharSequence[displayedMinistryNames.size()]));
            ministryPref.setEntryValues(displayedMinistryIds.toArray(new CharSequence[displayedMinistryIds.size()]));
        }

        else {
            ministryPref.setEnabled(false);
        }
    }

    /**
     * Loads content for the campus settings dialog.
     */
    private void initCampusSettings() {
        List<Campus> campuses = DBObjectLoader.getCampuses();

        CharSequence[] ids = new CharSequence[campuses.size()];
        CharSequence[] names = new CharSequence[campuses.size()];
        int idIdx = 0, nameIdx = 0;

        for (Campus campus : campuses) {
            ids[idIdx++] = campus.getId();
            names[nameIdx++] = campus.getName();
        }

        campusPref.setEntries(names);
        campusPref.setEntryValues(ids);
    }

    /**
     * Listener for performing actions when ministries are (un)subscribed to
     */
    private class MinistryPreferenceListener implements Preference.OnPreferenceChangeListener{
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            ministrySet = (HashSet<String>) newValue;
            Set<String> oldSubscribed = ((MultiSelectListPreference) preference).getValues();

            //Subscibe to new ministries
            for (String ministry : ministrySet) {
                if (!oldSubscribed.contains(ministry)) {
                    PushUtil.subscribe(ministry);
                }
            }

            //Unsubscribe from old ministries
            for (String ministry : oldSubscribed) {
                if (!ministrySet.contains(ministry)) {
                    PushUtil.unsubscribe(ministry);
                }
            }

            reload();
            return true;
        }
    }
    
    private class CampusPreferenceListener implements Preference.OnPreferenceChangeListener {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            int campusCount;
            String ministriesRemovedString = "";

            //get currently subscribed campuses
            campusSet = (HashSet<String>) newValue;

            //get ministries
            List<Ministry> ministries = DBObjectLoader.getMinistries();
            List<Ministry> myMinistries = new ArrayList<Ministry>();

            for (Ministry ministry : ministries) {
                if (ministrySet.contains(ministry.getId())) {
                    myMinistries.add(ministry);
                }
            }

            //figure out if any of them need to be unsubscribed to
            for (Ministry ministry : myMinistries) {
                campusCount = 0;

                for (String campusId : ministry.getCampuses()) {
                    if (campusSet.contains(campusId)) {
                        campusCount++;
                    }
                }

                //campusCount == 0 means there are no subscribed campuses associated with this ministry
                if (campusCount == 0) {

                    //remove ministry from preferences
                    Logger.i("PrefsFragment", "removing ministry " + ministry.getId());
                    ministrySet.remove(ministry.getId());
                    ministriesRemovedString += ministry.getName() + "; ";
                }
            }

            //notify the user of change
            if (!ministriesRemovedString.equals("")) {
                getPreferenceManager().getSharedPreferences().edit().remove(Android.PREF_MINISTRIES).commit();
                getPreferenceManager().getSharedPreferences().edit().putStringSet(Android.PREF_MINISTRIES, ministrySet).commit();
                notifyUserOfChange(ministriesRemovedString);
            }

            reload();
            return true;
        }

        private void notifyUserOfChange(String ministriesRemoved) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(parent);
            builder.setMessage("you were unsubscribed from the following ministries:\n\n" + ministriesRemoved).setPositiveButton("Ok", dialogClickListener).show();
        }
    }
}
