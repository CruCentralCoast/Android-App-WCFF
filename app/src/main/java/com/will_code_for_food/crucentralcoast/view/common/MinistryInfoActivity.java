package com.will_code_for_food.crucentralcoast.view.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;

/**
 * Created by MasonJStevenson on 1/22/2016.
 * <p/>
 * Controls the ministry info screen reachable from the ministry setup screen as part of the inital setup.
 */
public class MinistryInfoActivity extends Activity {

    private ImageView imageView;
    private TextView description;
    private TextView campusesView;
    private Ministry ministry;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ministry_info);

        context = this;
        ministry = SetupMinistryActivity.selectedMinistry;
        description = (TextView) findViewById(R.id.ministry_info_description);
        campusesView = (TextView) findViewById(R.id.ministry_info_campuses);
        imageView = (ImageView) findViewById(R.id.ministry_info_image);

        updateContents();
    }

    /**
     * Pulls image and description from the selected ministry and displays them.
     */
    public void updateContents() {

        String campusesText = "";

        if (SetupMinistryActivity.selectedMinistry != null) {

            ministry = SetupMinistryActivity.selectedMinistry;

            //load image
            String imageLabel = ministry.getImage();
            if (imageLabel != null && !imageLabel.equals("")) {
                Picasso.with(this).load(imageLabel).fit().into(imageView);
            }

            description.setText(ministry.getDescription());

            //display associated campuses
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
