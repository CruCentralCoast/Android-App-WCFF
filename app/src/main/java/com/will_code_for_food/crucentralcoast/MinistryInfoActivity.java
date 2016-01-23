package com.will_code_for_food.crucentralcoast;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;

/**
 * Created by MasonJStevenson on 1/22/2016.
 */
public class MinistryInfoActivity extends Activity {

    private ImageView imageView;
    private TextView description;
    private TextView campusesView;
    private Ministry ministry;

    private static final String CAMPUSES_TITLE_SINGULAR = "Campus:\n\n";
    private static final String CAMPUSES_TITLE_PLURAL = "Campuses:\n\n";

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ministry_info);

        context = this;
        ministry = SetupMinistryActivity.selecetedMinistry;
        description = (TextView) findViewById(R.id.ministry_info_description);
        campusesView = (TextView) findViewById(R.id.ministry_info_campuses);
        imageView = (ImageView) findViewById(R.id.ministry_info_image);

        updateContents();
    }

    public void updateContents() {

        String campusesText = "";

        if (SetupMinistryActivity.selecetedMinistry != null) {

            ministry = SetupMinistryActivity.selecetedMinistry;

            String imageLabel = ministry.getImage();

            if (imageLabel != null && !imageLabel.equals("")) {
                Picasso.with(this).load(imageLabel).fit().into(imageView);
            }

            description.setText(ministry.getDescription());

            //campusesText = (ministry.getCampuses().size() > 1) ? CAMPUSES_TITLE_PLURAL : CAMPUSES_TITLE_SINGULAR;

            for (String campusID : ministry.getCampuses()) {
                for (Campus campus : SetupCampusActivity.selectedCampuses) {

                    if (campus.getId().equals(campusID)) {
                        campusesText += campus.getName() + "\n";
                    }
                }
            }

            campusesView.setText(campusesText);
        }
    }
}
