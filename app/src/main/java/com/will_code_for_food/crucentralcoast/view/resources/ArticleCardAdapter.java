package com.will_code_for_food.crucentralcoast.view.resources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CardAdapter;

import java.util.ArrayList;

/**
 * Created by Kayla on 2/29/2016.
 */
public class ArticleCardAdapter extends CardAdapter {

    public ArticleCardAdapter(Context context, Content content) {
        super(context, android.R.layout.simple_list_item_1, content);
    }

    @Override
    public void sortByType() {
        ArrayList<DatabaseObject> audio = new ArrayList<>();
        ArrayList<DatabaseObject> videos = new ArrayList<>();
        ArrayList<DatabaseObject> articles = new ArrayList<>();

        for (DatabaseObject card : cards) {
            Resource resource = (Resource)card;
            if (resource.getType().equals(Database.RESOURCE_AUDIO)) {
                audio.add(resource);
            } else if (resource.getType().equals(Database.RESOURCE_VIDEO)) {
                videos.add(resource);
            } else if (resource.getType().equals(Database.RESOURCE_ARTICLE)) {
                articles.add(resource);
            }
        }
        cards.clear();
        cards.addAll(articles);
        cards.addAll(videos);
        cards.addAll(audio);

        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Resource current = (Resource) cards.get(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        String imageLabel = current.getImage();
        View hold = inflater.inflate(R.layout.fragment_resources_card, parent, false);

        ImageView imageView = (ImageView) hold.findViewById(R.id.card_image);
        if (imageLabel != null && !imageLabel.equals("")) {
            Picasso.with(ResourcesActivity.context).load(imageLabel).fit().into(imageView);
        } else {
            imageView.setImageResource(R.drawable.crulogo);
        }

        ImageView typeView = (ImageView) hold.findViewById(R.id.resource_type);
        String type = current.getType();
        if (type.equals(Database.RESOURCE_ARTICLE)) {
            typeView.setImageResource(R.drawable.ic_web_black_36dp);
        } else if (type.equals(Database.RESOURCE_VIDEO)) {
            typeView.setImageResource(R.drawable.ic_video);
        } else if (type.equals(Database.RESOURCE_AUDIO)) {
            typeView.setImageResource(R.drawable.ic_volume_up_black_36dp);
        }

        TextView titleView = (TextView) hold.findViewById(R.id.card_text);
        titleView.setText(current.getTitle());

        return hold;
    }
}