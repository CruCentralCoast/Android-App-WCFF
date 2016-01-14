package com.will_code_for_food.crucentralcoast;

import android.os.Bundle;
import android.view.View;

import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;

/**
 * Created by mallika on 1/14/16.
 */
public class ResourcesActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_resources, "Resources");
    }

    public void testYoutube(View view) {
        YoutubeViewer.watchYoutubeVideo("hGcmaztq7eU", this);
    }
}
