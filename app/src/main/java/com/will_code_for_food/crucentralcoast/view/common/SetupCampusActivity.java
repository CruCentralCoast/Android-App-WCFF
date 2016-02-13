package com.will_code_for_food.crucentralcoast.view.common;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
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
 * <p/>
 * Controls the campus setup screen that new users will see following the splashscreen.
 */
public class SetupCampusActivity extends Activity {

    public static List<Campus> selectedCampuses;
    public Campus selectedCampus;
    private ListView campusList;
    private Button nextButton;
    private LinearLayout screen;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_campus);

        initComponents();
        translateTitle();
    }

    /**
     * Initializes UI components.
     */
    private void initComponents() {
        selectedCampuses = new ArrayList<>();

        campusList = (ListView) this.findViewById(R.id.setup_campus_list);
        nextButton = (Button) this.findViewById(R.id.setup_campus_next_button);
        screen = (LinearLayout) this.findViewById(R.id.setup_campus_container);
        title = (TextView) findViewById(R.id.question_campus);

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

    /**
     * Moves the title of this screen from the center to the top of the view.
     */
    private void translateTitle() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                title.animate().translationY(-1 * title.getTop() + UI.SETUP_TITLE_MARGIN).withLayer().setDuration(UI.SETUP_TITLE_TRANSLATE_DURATION);
                delayedListAppearance();
            }
        };

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);

                title.animate().translationY(-1 * title.getTop() + UI.SETUP_TITLE_MARGIN).withLayer().setDuration(0);
                campusList.setVisibility(View.VISIBLE);

                screen.setOnClickListener(null);
            }
        });

        handler.postDelayed(runnable, UI.SETUP_CAMPUS_WAIT_DURATION);
    }

    /**
     * Launches the ministry setup page.
     */
    private void nextScreen() {
        Intent intent = new Intent(this, SetupMinistryActivity.class);
        startActivity(intent);

        //Apply splash exit (fade out) and main entry (fade in) animation transitions.
        overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);
    }

    /**
     * Makes the list of campuses appear as the title translation animation is finishing.
     */
    private void delayedListAppearance() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                campusList.setVisibility(View.VISIBLE);
            }
        }, UI.SETUP_TITLE_TRANSLATE_DURATION);
    }

    /**
     * Adapter for list of campus cards.
     */
    private class CampusAdapter extends ArrayAdapter<Campus> {
        private List<Campus> campusList;
        private Context context;

        private TextView campusName;

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

        /**
         * Updates a single card in the list with the correct name and image.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_campus_setup_card, null);

                campusName = (TextView) convertView.findViewById(R.id.campus_card_text);
                cardImage = (ImageView) convertView.findViewById(R.id.campus_card_image);
                final TextView over = (TextView) convertView.findViewById(R.id.campus_setup_card_over);
                over.setVisibility(View.INVISIBLE);
                final Campus campus = campusList.get(position);
                campusName.setText(campus.getName());

                final RelativeLayout background = (RelativeLayout) convertView.findViewById(R.id.campus_setup_card);

                background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCampus = campus;

                        if (!selectedCampuses.contains(campus)) {
                            selectedCampuses.add(campus);
                            over.setVisibility(View.VISIBLE);
                            over.bringToFront();
                        } else {
                            selectedCampuses.remove(campus);
                            over.setVisibility(View.INVISIBLE);
                        }

                        if (!selectedCampuses.isEmpty()) {
                            nextButton.setEnabled(true);
                            nextButton.setVisibility(View.VISIBLE);
                        } else {
                            nextButton.setEnabled(false);
                        }
                    }
                });

                //load the image
                imageLabel = campus.getImage();
                if (imageLabel != null && !imageLabel.equals("")) {
                    Picasso.with(this.getContext()).load(imageLabel).into(cardImage);
                } else {
                    cardImage.setImageResource(R.drawable.cru_logo_default);
                }
            }

            return convertView;
        }
    }

    /**
     * Asynchronously retrieves a list of campuses from the database and puts them into the ListView
     * for this activity.
     */
    private class SetupCampusTask extends AsyncTask<Void, Void, Void> {

        ArrayList<Campus> campuses = new ArrayList<>();
        Activity parent;

        public SetupCampusTask(Activity newParent) {
            parent = newParent;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Retriever retriever = new SingleRetriever<>(RetrieverSchema.CAMPUS);
            campuses = (ArrayList<Campus>) (List<?>) retriever.getAll().getObjects();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            campusList.setAdapter(new CampusAdapter(campuses, parent));
        }
    }
}
