package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.tasks.MinistryTeamInfoTask;
import com.will_code_for_food.crucentralcoast.tasks.SummerMissionViewTask;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.summermissions.SummerMissionsActivity;

import java.util.List;

/**
 * Created by Brian on 2/19/2016.
 */
public class MinistryTeamCardFactory implements CardFragmentFactory<MinistryTeam> {
    @Override
    public boolean include(MinistryTeam object) {
        return true;
    }

    @Override
    public ArrayAdapter createAdapter(Content<MinistryTeam> cardObjects) {
        return new SummerMissionAdapter(GetInvolvedActivity.context,
                android.R.layout.simple_list_item_1, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(final MainActivity currentActivity,
                                                              final Content<MinistryTeam> myDBObjects) {

        return new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                MinistryTeam selectedMinistryTeam = (MinistryTeam) myDBObjects.getObjects().get(position);
                currentActivity.loadFragmentById(R.layout.fragment_summermission,
                        currentActivity.getTitle() + " > " + selectedMinistryTeam.getName(), null, currentActivity);
                new MinistryTeamInfoTask().execute(selectedMinistryTeam);
            }
        } ;
    }

    private class SummerMissionAdapter extends ArrayAdapter<SummerMission>{
        private List<SummerMission> cards;

        public SummerMissionAdapter(Context context, int resource, Content content) {
            super(context, resource, content.getObjects());
            cards = content.getObjects();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            SummerMission current = cards.get(position);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            String imageLabel = current.getImage();
            View hold = inflater.inflate(R.layout.fragment_summermission_card, parent, false);

            ImageView imageView = (ImageView) hold.findViewById(R.id.mt_image);
            if (imageLabel != null && !imageLabel.equals("")) {
                Picasso.with(SummerMissionsActivity.context).load(imageLabel).fit().into(imageView);
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
