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

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.messaging.PushUtil;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.UI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MasonJStevenson on 1/20/2016.
 * <p/>
 * Controls the ministry setup screen that new users will see following the campus setup screen.
 */
public class SetupMinistryActivity extends Activity {

    private Button finishButton;
    private List<Campus> selectedCampuses;
    private List<Ministry> selectedMinistries;
    private ListView ministryList;
    private LinearLayout screen;
    private TextView title;

    public static Context context;
    public static Ministry selectedMinistry;

    public SetupMinistryActivity() {
        this.selectedCampuses = SetupCampusActivity.selectedCampuses;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_ministry);

        context = this;

        initComponents();
        translateTitle();
    }

    /**
     * Initializes UI components.
     */
    private void initComponents() {
        selectedMinistries = new ArrayList<>();

        ministryList = (ListView) this.findViewById(R.id.setup_ministry_list);
        finishButton = (Button) this.findViewById(R.id.setup_ministry_next_button);
        finishButton.setVisibility(View.INVISIBLE);
        ministryList.setVisibility(View.INVISIBLE);
        title = (TextView) findViewById(R.id.question_ministry);
        screen = (LinearLayout) findViewById(R.id.setup_ministry_container);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Ministry ministry : selectedMinistries) {
                    Util.saveToSet(Android.PREF_MINISTRIES, ministry.getId());
                    PushUtil.subscribe(ministry.getId());
                }

                Util.saveBool(Android.PREF_SETUP_COMPLETE, true);

                nextScreen();
            }
        });

        new SetupMinistryTask(this).execute();
    }

    /**
     * Loads the main page.
     */
    private void nextScreen() {

        //Finish this activity so user cant go back to it.
        finish();

        //Apply splash exit (fade out) and main entry (fade in) animation transitions.
        overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

        //makes the translation animation skippable.
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);

                title.animate().translationY(-1 * title.getTop() + UI.SETUP_TITLE_MARGIN).withLayer().setDuration(0);
                ministryList.setVisibility(View.VISIBLE);

                screen.setOnClickListener(null);
            }
        });

        new Handler().postDelayed(runnable, UI.SETUP_MINISTRY_WAIT_DURATION);
    }

    /**
     * Makes the list of ministries appear as the title translation animation is finishing.
     */
    private void delayedListAppearance() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ministryList.setVisibility(View.VISIBLE);
            }
        }, UI.SETUP_TITLE_TRANSLATE_DURATION);
    }

    /**
     * Adapter for a list of ministry cards.
     */
    private class MinistryAdapter extends ArrayAdapter<Ministry> {

        private List<Ministry> ministryList;
        private Context context;

        private TextView ministryName;
        private ImageView cardImage;
        private String imageLabel = "";
        private RelativeLayout background;

        public MinistryAdapter(List<Ministry> ministryList, Context context) {
            super(context, R.layout.fragment_ministry_setup_card, ministryList);
            this.ministryList = ministryList;
            this.context = context;
        }

        /**
         * Fixes a weird bug in ListView that makes the list items change around when you scroll.
         */
        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        /**
         * Fixes a weird bug in ListView that makes the list items change around when you scroll.
         */
        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Ministry ministry = ministryList.get(position);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_ministry_setup_card, null);

                ministryName = (TextView) convertView.findViewById(R.id.ministry_card_text);
                cardImage = (ImageView) convertView.findViewById(R.id.ministry_card_image);
                background = (RelativeLayout) convertView.findViewById(R.id.ministry_setup_card_background);
                final TextView learnMore = (TextView) convertView.findViewById(R.id.ministry_learn_more);
                final TextView over = (TextView) convertView.findViewById(R.id.ministry_setup_card_over);
                over.setVisibility(View.INVISIBLE);

                ministryName.setOnClickListener(getMinistryListener(ministry, over));
                cardImage.setOnClickListener(getMinistryListener(ministry, over));
                learnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedMinistry = ministry;
                        Intent intent = new Intent(context, MinistryInfoActivity.class);
                        startActivity(intent);
                    }
                });

                ministryName.setText(ministry.getName());

                //load image
                imageLabel = ministry.getImage();
                if (imageLabel != null && !imageLabel.equals("")) {
                    Picasso.with(this.getContext()).load(imageLabel).fit().centerInside().into(cardImage);
                } else {
                    cardImage.setImageResource(R.drawable.cru_logo_default);
                }
            }

            return convertView;
        }
    }

    /**
     * Creates on-click listener for selecting Ministries
     */
    private View.OnClickListener getMinistryListener(final Ministry ministry, final TextView over) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedMinistries.contains(ministry)) {
                    selectedMinistries.add(ministry);
                    over.setVisibility(View.VISIBLE);
                    over.bringToFront();
                } else {
                    selectedMinistries.remove(ministry);
                    over.setVisibility(View.INVISIBLE);
                }

                if (!selectedMinistries.isEmpty()) {
                    finishButton.setEnabled(true);
                    finishButton.setVisibility(View.VISIBLE);
                } else {
                    finishButton.setEnabled(false);
                }
            }
        };
    }

    /**
     * Asynchronously retrieves a list of ministries from the database and puts them into the ListView
     * for this activity.
     */
    private class SetupMinistryTask extends AsyncTask<Void, Void, Void> {

        List<Ministry> ministries;
        List<Ministry> ministriesFiltered = new ArrayList<>();

        Activity parent;

        public SetupMinistryTask(Activity newParent) {
            parent = newParent;
        }

        @Override
        protected Void doInBackground(Void... params) {

            //Retriever retriever = new SingleRetriever<>(RetrieverSchema.MINISTRY);
            //ministries = (List<Ministry>) retriever.getAll().getObjects();
            ministries = DBObjectLoader.getMinistries();

            //Filter the ministry list based on the selected campuses from the previous screen.
            for (Ministry ministry : ministries) {
                if (ministry != null) {
                    for (String campus : ministry.getCampuses()) {
                        for (Campus selectedCampus : selectedCampuses) {
                            if (selectedCampus.equals(campus)) {
                                ministriesFiltered.add(ministry);
                            }
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
