package com.will_code_for_food.crucentralcoast.view.resources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.view.common.CardAdapter;

import java.util.List;

/**
 * Created by Kayla on 2/29/2016.
 */
public class ArticleCardAdapter extends CardAdapter {

    public ArticleCardAdapter(Context context, Content content) {
        super(context, android.R.layout.simple_list_item_1, content);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Resource current = (Resource) cards.get(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        String imageLabel = current.getImage();
        View hold = inflater.inflate(R.layout.fragment_resources_article_card, parent, false);

        ImageView imageView = (ImageView) hold.findViewById(R.id.card_image);
        if (imageLabel != null && !imageLabel.equals("")) {
            Picasso.with(ResourcesActivity.context).load(imageLabel).fit().into(imageView);
        } else {
            System.out.println("Image is this: " + imageLabel);
            imageView.setImageResource(R.drawable.crulogo);
        }

        TextView titleView = (TextView) hold.findViewById(R.id.card_text);
        titleView.setText(current.getTitle());

        return hold;
    }
}