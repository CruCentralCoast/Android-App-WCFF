package com.will_code_for_food.crucentralcoast.view.resources;

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
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.tasks.SummerMissionViewTask;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.view.summermissions.SummerMissionsActivity;

import java.util.List;

/**
 * Created by Brian on 2/16/2016.
 */
public class ArticleCardFactory implements CardFragmentFactory {
    @Override
    public boolean include(DatabaseObject object) {
        return true;
    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return new ResourceAdapter(ResourcesActivity.context, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(final MainActivity currentActivity,
                                                              final Content myDBObjects) {
        return new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                ResourcesActivity.selectedResource = (Resource) myDBObjects.getObjects().get(position);
                currentActivity.loadFragmentById(R.layout.fragment_article,
                        currentActivity.getTitle() + " > " + ResourcesActivity.selectedResource.getTitle(),
                        new ViewArticleFragment(), currentActivity);
            }
        } ;
    }

    private class ResourceAdapter extends ArrayAdapter<SummerMission> {
        private List<Resource> cards;

        public ResourceAdapter(Context context, Content content) {
            super(context, android.R.layout.simple_list_item_1, content.getObjects());
            cards = content.getObjects();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Resource current = cards.get(position);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            String imageLabel = current.getImage();
            View hold = inflater.inflate(R.layout.fragment_resources_card, parent, false);

            ImageView imageView = (ImageView) hold.findViewById(R.id.card_image);
            if (imageLabel != null && !imageLabel.equals("")) {
                Picasso.with(SummerMissionsActivity.context).load(imageLabel).fit().into(imageView);
            } else {
                System.out.println("Image is this: " + imageLabel);
                imageView.setImageResource(R.drawable.crulogo);
            }

            TextView titleView = (TextView) hold.findViewById(R.id.card_text);
            titleView.setText(current.getTitle());

            return hold;
        }
    }
}
