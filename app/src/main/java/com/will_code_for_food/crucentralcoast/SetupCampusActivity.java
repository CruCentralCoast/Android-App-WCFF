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
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.UI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MasonJStevenson on 1/19/2016.
 */
public class SetupCampusActivity extends Activity implements android.widget.CompoundButton.OnCheckedChangeListener {

    public static List<Campus> selectedCampuses;
    private ListView campusList;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        initComponents();
        translateTitle();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Campus campus = (Campus) buttonView.getTag();

        if (isChecked) {
            selectedCampuses.add(campus);
        }

        else {
            selectedCampuses.remove(campus);
        }

        if (!selectedCampuses.isEmpty()) {
            nextButton.setEnabled(true);
            nextButton.setVisibility(View.VISIBLE);
        }

        else {
            nextButton.setEnabled(false);
        }
    }

    private void initComponents() {
        selectedCampuses = new ArrayList<Campus>();

        campusList = (ListView) this.findViewById(R.id.setup_campus_list);
        nextButton = (Button) this.findViewById(R.id.setup_campus_next_button);
        nextButton.setEnabled(false);
        campusList.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Campus campus : selectedCampuses) {
                    Util.saveToSet(Android.PREF_CAMPUSES, campus.getId());
                }

                nextScreen();
            }
        });

        new SetupCampusTask(this).execute();
    }

    private void translateTitle() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView title = (TextView) findViewById(R.id.question_campus);
                title.animate().translationY(-1 * title.getTop() + UI.SETUP_TITLE_MARGIN).withLayer().setDuration(UI.SETUP_TITLE_TRANSLATE_DURATION);
                delayedListAppearance();
            }
        }, UI.SETUP_CAMPUS_WAIT_DURATION);
    }

    private void nextScreen() {

        this.finish();

        //Apply splash exit (fade out) and main entry (fade in) animation transitions.
        overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);

        Intent intent = new Intent(this, SetupMinistryActivity.class);
        startActivity(intent);
    }

    private void delayedListAppearance() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                campusList.setVisibility(View.VISIBLE);
            }
        }, UI.SETUP_TITLE_TRANSLATE_DURATION);
    }

    private class CampusAdapter extends ArrayAdapter<Campus> {

        private List<Campus> campusList;
        private Context context;

        private TextView campusName;
        private CheckBox chkBox;

        public CampusAdapter(List<Campus> campusList, Context context) {
            super(context, R.layout.setup_list_item, campusList);
            this.campusList = campusList;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if(convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.setup_list_item, null);

                campusName = (TextView) v.findViewById(R.id.setup_name);
                chkBox = (CheckBox) v.findViewById(R.id.setup_chk_box);

                chkBox.setOnCheckedChangeListener((SetupCampusActivity) context);

            }
            else {
                //not sure what should go here...
            }

            Campus campus = campusList.get(position);
            campusName.setText(campus.getName());
            chkBox.setChecked(false);
            chkBox.setTag(campus);

            return v;
        }
    }

    private class SetupCampusTask extends AsyncTask<Void, Void, Void> {

        ArrayList<Campus> campuses;
        Activity parent;

        public SetupCampusTask(Activity newParent) {
            parent = newParent;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Retriever retriever = new SingleRetriever<>(RetrieverSchema.CAMPUS);
            campuses = (ArrayList<Campus>) (List<?>) retriever.getAll();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            campusList.setAdapter(new CampusAdapter(campuses, parent));
        }
    }
}
