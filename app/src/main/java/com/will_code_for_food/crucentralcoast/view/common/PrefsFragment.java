package com.will_code_for_food.crucentralcoast.view.common;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
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
    private Preference clearPref;
    private Preference logoutPref;
    private Preference emailPref;
    private Preference debugPref;
    private CheckBoxPreference setupPref;

    private PrefsFragment thisFrag = this;

    public PrefsFragment() {
        parent = (Activity) SettingsActivity.context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        getComponents();
        setListeners();
        hidePrefs();

        this.getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        new MinistrySettingsTask().execute();
        new CampusSettingsTask().execute();
    }

    public void getComponents() {
        prefsContainer = (PreferenceCategory) findPreference(Android.PREF_CONTAINER);
        campusPref = (MultiSelectListPreference) findPreference(Android.PREF_CAMPUSES);
        clearPref = findPreference(Android.PREF_CLEAR);
        logoutPref = findPreference(Android.PREF_LOGOUT);
        emailPref = findPreference(Android.PREF_EMAIL);
        debugPref = findPreference(Android.PREF_DEBUG);
        setupPref = (CheckBoxPreference) findPreference(Android.PREF_SETUP_COMPLETE);
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
                PrefsPasswordDialog popup = new PrefsPasswordDialog(thisFrag);
                FragmentManager manager = getFragmentManager();
                popup.show(manager, "prefs_enter_password");

                return false;
            }
        });
    }

    public void enableDeveloperOptions(String password) {
        if (password.equals(Android.DEBUG_PW)) {
            getPreferenceManager().getSharedPreferences().edit().putBoolean(Android.PREF_DEBUG, true).commit();
            reload();
        } else {
            Toast.makeText(parent, "incorrect password", Toast.LENGTH_SHORT).show();
        }
    }

    public void hidePrefs() {
        boolean debugEnabled = getPreferenceManager().getSharedPreferences().getBoolean(Android.PREF_DEBUG, false);

        if (debugEnabled) {
            prefsContainer.removePreference(debugPref);
        } else {
            prefsContainer.removePreference(setupPref);
            prefsContainer.removePreference(clearPref);
        }
    }

    /**
     * Listener for performing actions when ministries are (un)subscribed to
     */
    private class MinistryPreferenceListener implements Preference.OnPreferenceChangeListener{
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            Set<String> newSubscribed = (HashSet<String>) newValue;
            Set<String> oldSubscribed = ((MultiSelectListPreference) preference).getValues();
            //Subscibe to new ministries
            for (String ministry : newSubscribed)
                if (!oldSubscribed.contains(ministry))
                    PushUtil.subscribe(ministry);

            //Unsubscribe from old ministries
            for (String ministry : oldSubscribed)
                if (!newSubscribed.contains(ministry))
                    PushUtil.unsubscribe(ministry);

            return true;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        reload();
    }

    private void reload() {
        parent.finish();
        parent.startActivity(parent.getIntent());
    }

    private class MinistrySettingsTask extends AsyncTask<Void, Void, List<Ministry>>{

        @Override
        protected List<Ministry> doInBackground(Void... params) {
            Retriever retriever = new SingleRetriever<Ministry>(RetrieverSchema.MINISTRY);
            return retriever.getAll().getObjects();
        }

        @Override
        protected void onPostExecute(List<Ministry> ministries) {
            Set<String> selectedCampuses = Util.loadStringSet(Android.PREF_CAMPUSES);
            List<String> filteredIds = new ArrayList<String>();
            List<String> filteredNames = new ArrayList<String>();

            MultiSelectListPreference ministryPref =
                    (MultiSelectListPreference) getPreferenceManager().findPreference(Android.PREF_MINISTRIES);
            ministryPref.setOnPreferenceChangeListener(new MinistryPreferenceListener());

            if (selectedCampuses != null && !selectedCampuses.isEmpty()) {
                ministryPref.setEnabled(true);

                for (String campus : selectedCampuses) {
                    for (Ministry ministry : ministries) {
                        if (ministry.getCampuses().contains(campus)) {
                            filteredIds.add(ministry.getId());
                            filteredNames.add(ministry.getName());
                        }
                    }
                }

                ministryPref.setEntries(filteredNames.toArray(new CharSequence[filteredNames.size()]));
                ministryPref.setEntryValues(filteredIds.toArray(new CharSequence[filteredIds.size()]));
            }

            else {
                ministryPref.setEnabled(false);
            }
        }
    }

    private class CampusSettingsTask extends AsyncTask<Void, Void, List<Campus>>{

        @Override
        protected List<Campus> doInBackground(Void... params) {
            Retriever retriever = new SingleRetriever<Ministry>(RetrieverSchema.CAMPUS);
            return retriever.getAll().getObjects();
        }

        @Override
        protected void onPostExecute(List<Campus> campuses) {
            CharSequence[] ids = new CharSequence[campuses.size()];
            CharSequence[] names = new CharSequence[campuses.size()];
            int idIdx = 0, nameIdx = 0;

            for (Campus campus : campuses)
            {
                ids[idIdx++] = campus.getId();
                names[nameIdx++] = campus.getName();
            }
            MultiSelectListPreference ministryPref =
                    (MultiSelectListPreference) getPreferenceManager().findPreference(Android.PREF_CAMPUSES);
            ministryPref.setEntries(names);
            ministryPref.setEntryValues(ids);
        }
    }
}
