package com.will_code_for_food.crucentralcoast.view.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.SettingsActivity;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 * Created by Brian on 1/17/2016.
 */
public class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    static Activity parent;

    public PrefsFragment() {
        parent = (Activity) SettingsActivity.context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        MultiSelectListPreference campusPref = (MultiSelectListPreference) findPreference("pref_campuses");
        Preference clearPref = (Preference) findPreference("pref_clear");

        clearPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getPreferenceManager().getSharedPreferences().edit().clear().commit();
                reload();
                return false;
            }
        });

        this.getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        new MinistrySettingsTask().execute();
        new CampusSettingsTask().execute();
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
            return retriever.getAll();
        }

        @Override
        protected void onPostExecute(List<Ministry> ministries) {
            Set<String> selectedCampuses = Util.loadStringSet("pref_campuses");
            List<String> filteredIds = new ArrayList<String>();
            List<String> filteredNames = new ArrayList<String>();

            MultiSelectListPreference ministryPref = (MultiSelectListPreference) getPreferenceManager().findPreference("pref_ministries");

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
            return retriever.getAll();
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
                    (MultiSelectListPreference) getPreferenceManager().findPreference("pref_campuses");
            ministryPref.setEntries(names);
            ministryPref.setEntryValues(ids);
        }
    }
}
