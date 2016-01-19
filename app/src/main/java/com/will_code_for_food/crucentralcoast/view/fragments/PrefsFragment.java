package com.will_code_for_food.crucentralcoast.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceFragment;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 1/17/2016.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        new MinistrySettingsTask().execute();
    }

    private class MinistrySettingsTask extends AsyncTask<Void, Void, List<Ministry>>{

        @Override
        protected List<Ministry> doInBackground(Void... params) {
            Retriever retriever = new SingleRetriever<Ministry>(RetrieverSchema.MINISTRY);
            return retriever.getAll();
        }

        @Override
        protected void onPostExecute(List<Ministry> ministries) {
            CharSequence[] ids = new CharSequence[ministries.size()];
            CharSequence[] names = new CharSequence[ministries.size()];
            int idIdx = 0, nameIdx = 0;

            for (Ministry ministry : ministries)
            {
                ids[idIdx++] = ministry.getId();
                names[nameIdx++] = ministry.getName();
            }
            MultiSelectListPreference ministryPref =
                    (MultiSelectListPreference) getPreferenceManager().findPreference("pref_ministries");
            ministryPref.setEntries(names);
            ministryPref.setEntryValues(ids);
        }
    }
}
