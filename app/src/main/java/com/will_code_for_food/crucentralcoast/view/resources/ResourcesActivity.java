package com.will_code_for_food.crucentralcoast.view.resources;

import android.os.Bundle;
import android.view.View;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by mallika on 1/14/16.
 */
public class ResourcesActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_resources, "Resources", null, this);
    }

    public void testYoutube(View view) {
        DriverForm form = new DriverForm("id");
        form.answerQuestion(0, "gavin");
        form.submit();
        //YoutubeViewer.watchYoutubeVideo("hGcmaztq7eU", this);
    }
}
