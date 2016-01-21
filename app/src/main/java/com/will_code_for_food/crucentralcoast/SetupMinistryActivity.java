package com.will_code_for_food.crucentralcoast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.UI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MasonJStevenson on 1/20/2016.
 */
public class SetupMinistryActivity extends Activity implements android.widget.CompoundButton.OnCheckedChangeListener {

    private Button finishButton;
    private List<Campus> selectedCampuses;
    private List<Ministry> selectedMinistries;
    private ListView ministryList;

    public SetupMinistryActivity() {
        this.selectedCampuses = SetupCampusActivity.selectedCampuses;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setup_ministry);

        initComponents();
        translateTitle();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Ministry ministry = (Ministry) buttonView.getTag();

        if (isChecked) {
            selectedMinistries.add(ministry);
        }

        else {
            selectedMinistries.remove(ministry);
        }

        if (!selectedMinistries.isEmpty()) {
            finishButton.setEnabled(true);
            finishButton.setVisibility(View.VISIBLE);
        }

        else {
            finishButton.setEnabled(false);
        }
    }

    private void initComponents() {
        selectedMinistries = new ArrayList<Ministry>();

        ministryList = (ListView) this.findViewById(R.id.setup_ministry_list);
        finishButton = (Button) this.findViewById(R.id.setup_ministry_next_button);
        finishButton.setVisibility(View.INVISIBLE);
        ministryList.setVisibility(View.INVISIBLE);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Ministry ministry : selectedMinistries) {
                    Util.saveToSet(Android.PREF_MINISTIES, ministry.getId());
                }

                Util.saveBool(Android.PREF_SETUP_COMPLETE, true);

                nextScreen();
            }
        });

        new SetupMinistryTask(this).execute();
    }

    private void nextScreen() {

        this.finish();

        //Apply splash exit (fade out) and main entry (fade in) animation transitions.
        overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void translateTitle() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView title = (TextView) findViewById(R.id.question_ministry);
                title.animate().translationY(-1 * title.getTop() + UI.SETUP_TITLE_MARGIN).withLayer().setDuration(UI.SETUP_TITLE_TRANSLATE_DURATION);
                delayedListAppearance();
            }
        }, UI.SETUP_MINISTRY_WAIT_DURATION);
    }

    private void delayedListAppearance() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ministryList.setVisibility(View.VISIBLE);
            }
        }, UI.SETUP_TITLE_TRANSLATE_DURATION);
    }

    private class MinistryAdapter extends ArrayAdapter<Ministry> {

        private List<Ministry> ministryList;
        private Context context;

        private TextView ministryName;
        private CheckBox chkBox;

        public MinistryAdapter(List<Ministry> ministryList, Context context) {
            super(context, R.layout.setup_list_item, ministryList);
            this.ministryList = ministryList;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if(convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.setup_list_item, null);

                ministryName = (TextView) v.findViewById(R.id.setup_name);
                chkBox = (CheckBox) v.findViewById(R.id.setup_chk_box);

                chkBox.setOnCheckedChangeListener((SetupMinistryActivity) context);

            }
            else {
                //not sure what to put here...
            }

            Ministry ministry = ministryList.get(position);
            ministryName.setText(ministry.getName());
            chkBox.setChecked(false);
            chkBox.setTag(ministry);

            return v;
        }
    }

    private class SetupMinistryTask extends AsyncTask<Void, Void, Void> {

        List<Ministry> ministries;
        List<Ministry> ministriesFiltered = new ArrayList<Ministry>();

        Activity parent;

        public SetupMinistryTask(Activity newParent) {
            parent = newParent;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Retriever retriever = new SingleRetriever<>(RetrieverSchema.MINISTRY);
            ministries = (List<Ministry>) retriever.getAll();

            for (Ministry ministry : ministries) {
                for (String campus : ministry.getCampuses()) {
                    for (Campus selectedCampus : selectedCampuses) {
                        if (selectedCampus.equals(campus)) {
                            ministriesFiltered.add(ministry);
                        }
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ministryList.setAdapter(new MinistryAdapter(ministriesFiltered, parent));
        }
    }
}
