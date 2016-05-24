package com.will_code_for_food.crucentralcoast.view.dynamic_form;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.community_groups.CommunityGroupForm;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.MinistryInfoActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by mallika on 5/11/16.
 */
public class FormListFragment extends CruFragment {

    HashMap<String, CommunityGroupForm> forms;
    ArrayList<String> ministryList;
    ListView list;
    List<Campus> selectedCampuses;
    List<Ministry> selectedMinistries;

    public FormListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main = super.onCreateView(inflater, container, savedInstanceState);
        list = (ListView) main.findViewById(R.id.list_cards);

        Bundle args = getArguments();
        ministryList = args.getStringArrayList("ministryList");
        forms = (HashMap<String, CommunityGroupForm>) args.getSerializable("formsMap");

        new MinistryListTask(getActivity()).execute();

        return main;
    }

    private class FormListAdapter extends ArrayAdapter<Ministry> {

        private List<Ministry> ministryList;
        private Context context;

        private TextView ministryName;
        private TextView campusName;
        private ImageView cardImage;
        private String imageLabel = "";
        private RelativeLayout background;

        public FormListAdapter(List<Ministry> ministryList, Context context) {
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
            final String campus = ministry.getCampuses().get(0);
            String campusNameStr = "";
            for (Campus campusId : selectedCampuses){
                if(campus.equals(campusId.getId())) {
                    campusNameStr = campusId.getName();
                }
            }

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_ministry_setup_card, null);
            }

            campusName = (TextView) convertView.findViewById(R.id.ministry_card_campus);
            ministryName = (TextView) convertView.findViewById(R.id.ministry_card_text);
            cardImage = (ImageView) convertView.findViewById(R.id.ministry_card_image);
            background = (RelativeLayout) convertView.findViewById(R.id.ministry_setup_card_background);
            TextView over = (TextView) convertView.findViewById(R.id.ministry_setup_card_over);
            over.setVisibility(View.INVISIBLE);
            TextView learnMore = (TextView) convertView.findViewById(R.id.ministry_learn_more);
            learnMore.setVisibility(View.INVISIBLE);

            campusName.setOnClickListener(getMinistryListener(ministry));
            ministryName.setOnClickListener(getMinistryListener(ministry));
            cardImage.setOnClickListener(getMinistryListener(ministry));

            campusName.setText(campusNameStr);
            ministryName.setText(ministry.getName());

            //load image
            imageLabel = ministry.getImage();
            if (imageLabel != null && !imageLabel.equals("")) {
                Picasso.with(this.getContext()).load(imageLabel).fit().centerInside().into(cardImage);
            } else {
                cardImage.setImageResource(R.drawable.cru_logo_default);
            }

            return convertView;
        }

        /**
         * Creates on-click listener for selecting Ministries
         */
        private View.OnClickListener getMinistryListener(final Ministry ministry) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FormFragment formFragment = new FormFragment();
                    Bundle args = new Bundle();
                    args.putString("ministryID", ministry.getId());
                    formFragment.setArguments(args);
                    String name = ministry.getName() + " Form";
                    getParent().loadFragmentById(R.layout.fragment_community_group_form, name, formFragment, getParent());
                }
            };
        }


    }

    /**
     * Asynchronously retrieves a list of ministries from the database and puts them into the ListView
     * for this activity.
     */
    private class MinistryListTask extends AsyncTask<Void, Void, Void> {

        List<Ministry> ministries;
        List<Ministry> ministriesFiltered = new ArrayList<>();
        List<Campus> campuses;
        List<Campus> campusesFiltered = new ArrayList<>();
        Activity parent;

        public MinistryListTask(Activity newParent) {
            parent = newParent;
        }

        @Override
        protected Void doInBackground(Void... params) {

            //Retriever retriever = new SingleRetriever<>(RetrieverSchema.MINISTRY);
            //ministries = (List<Ministry>) retriever.getAll().getObjects();
            ministries = DBObjectLoader.getMinistries();
            campuses = DBObjectLoader.getCampuses();
            ArrayList<String> campusIDs = new ArrayList<String>(Util.loadStringSet(Android.PREF_CAMPUSES));
            ArrayList<String> ministryIDs = new ArrayList<>(Util.loadStringSet(Android.PREF_MINISTRIES));

            //Filter the ministry list based on the ministry ids
            for (Ministry ministry : ministries) {
                if (ministryIDs.contains(ministry.getId())) {
                    ministriesFiltered.add(ministry);
                }
            }

            //Filter the campus list based on the campus ids
            for (Campus campus : campuses) {
                if (campusIDs.contains(campus.getId())) {
                    campusesFiltered.add(campus);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            selectedCampuses = campusesFiltered;
            list.setAdapter(new FormListAdapter(ministriesFiltered, parent));
        }
    }

}
