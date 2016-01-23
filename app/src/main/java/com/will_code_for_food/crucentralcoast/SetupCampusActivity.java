package com.will_code_for_food.crucentralcoast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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
        CheckBox checkBox = (CheckBox) buttonView;

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
        selectedCampuses = new ArrayList<>();

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

        //this.finish();

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

        private ImageView cardImage;
        private String imageLabel = "";

        public CampusAdapter(List<Campus> campusList, Context context) {
            super(context, R.layout.fragment_campus_setup_card, campusList);
            this.campusList = campusList;
            this.context = context;
        }

        @Override
        public int getViewTypeCount() {

            return getCount();
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if(convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.fragment_campus_setup_card, null);

                campusName = (TextView) v.findViewById(R.id.campus_card_text);
                chkBox = (CheckBox) v.findViewById(R.id.campus_setup_chk_box);
                cardImage = (ImageView) v.findViewById(R.id.campus_card_image);

                chkBox.setOnCheckedChangeListener((SetupCampusActivity) context);

                Campus campus = campusList.get(position);
                campusName.setText(campus.getName());
                chkBox.setChecked(false);
                chkBox.setTag(campus);

                imageLabel = campus.getImage();
                if (imageLabel != null && !imageLabel.equals("")) {
                    System.out.println("Image is this: " + imageLabel);
                    Picasso.with(this.getContext()).load(imageLabel).into(cardImage);
                } else {
                    cardImage.setImageResource(R.drawable.cru_logo_default);
                }

                return v;

            }
            else {
                return convertView;
            }
        }
    }

    private class SetupCampusTask extends AsyncTask<Void, Void, Void> {

        ArrayList<Campus> campuses = new ArrayList<>();
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
