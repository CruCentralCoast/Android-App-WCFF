package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.tasks.MinistryTeamInfoTask;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.summermissions.SummerMissionsActivity;

import java.util.List;
import java.util.Set;

/**
 * Created by Brian on 2/19/2016.
 */
public class MinistryTeamCardFactory implements CardFragmentFactory<MinistryTeam> {
    private Set<String> myMinistries;

    public MinistryTeamCardFactory(){
        // Only display dbObjects for the ministry
        myMinistries = Util.loadStringSet(Android.PREF_MINISTRIES);
    }

    @Override
    public boolean include(MinistryTeam object) {
        JsonElement parentMinistry = object.getField(Database.JSON_KEY_TEAM_MINISTRY);

        //Check if the user is subscribed to the ministry for the team
        if (myMinistries.contains(parentMinistry.getAsString())) {
            if (object instanceof MinistryTeam) {
                return true;
            }
        }

        // Return false if this ministry is not for user's ministries
        return false;
    }

    @Override
    public ArrayAdapter createAdapter(Content<MinistryTeam> cardObjects) {
        return new MinistryTeamAdapter(GetInvolvedActivity.context,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(final MainActivity currentActivity,
                                                              final Content<MinistryTeam> myDBObjects) {

        return new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                MinistryTeam selectedMinistryTeam = (MinistryTeam) myDBObjects.getObjects().get(position);
                currentActivity.loadFragmentById(R.layout.fragment_ministryteam,
                        currentActivity.getTitle() + " > " + selectedMinistryTeam.getName(), null, currentActivity);
                new MinistryTeamInfoTask().execute(selectedMinistryTeam);
            }
        } ;
    }

    private class MinistryTeamAdapter extends ArrayAdapter<SummerMission>{
        private List<MinistryTeam> cards;

        public MinistryTeamAdapter(Context context, int resource, Content content) {
            super(context, resource, content.getObjects());
            cards = content.getObjects();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            MinistryTeam current = cards.get(position);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            String imageLabel = current.getImage();
            View hold = inflater.inflate(R.layout.fragment_ministryteam_card, parent, false);

            ImageView imageView = (ImageView) hold.findViewById(R.id.mt_image);
            if (imageLabel != null && !imageLabel.equals("")) {
                Picasso.with(GetInvolvedActivity.context).load(imageLabel).fit().into(imageView);
            }
            else {
                System.out.println("Image is this: " + imageLabel);
                imageView.setImageResource(R.drawable.crulogo);
            }

            TextView titleView = (TextView) hold.findViewById(R.id.mt_text);
            titleView.setText(current.getName());

            return hold;
        }
    }
}
