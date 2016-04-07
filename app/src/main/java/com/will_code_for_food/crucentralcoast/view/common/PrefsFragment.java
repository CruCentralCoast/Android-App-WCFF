package com.will_code_for_food.crucentralcoast.view.common;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.email.EmailSender;
import com.will_code_for_food.crucentralcoast.controller.authentication.Authenticator;
import com.will_code_for_food.crucentralcoast.controller.crash_reports.CrashReport;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
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
    static Activity parent;

    public PrefsFragment() {
        parent = (Activity) SettingsActivity.context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        MultiSelectListPreference campusPref = (MultiSelectListPreference) findPreference(Android.PREF_CAMPUSES);

        Preference clearPref = findPreference(Android.PREF_CLEAR);

        clearPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getPreferenceManager().getSharedPreferences().edit().clear().commit();
                reload();
                PushUtil.clearPushChannels();
                return false;
            }
        });

        Preference logoutPref = findPreference(Android.PREF_LOGOUT);
        logoutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Authenticator.logOut();
                Toast.makeText(parent, Util.getString(R.string.logout_message),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Preference emailPref = findPreference(Android.PREF_EMAIL);
        emailPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.e("Bug Report", "Submitting manually");
                EmailSender.send(getActivity(),
                        new CrashReport(new NullPointerException(), "It all broke :(").asMessage());
                return true;
            }
        });

        clearPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getPreferenceManager().getSharedPreferences().edit().clear().commit();
                reload();
                PushUtil.clearPushChannels();
                return false;
            }
        });

        this.getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        new MinistrySettingsTask().execute();
        new CampusSettingsTask().execute();
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
