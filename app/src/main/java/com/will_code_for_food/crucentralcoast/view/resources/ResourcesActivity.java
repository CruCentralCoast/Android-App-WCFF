package com.will_code_for_food.crucentralcoast.view.resources;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.authentication.Authenticator;
import com.will_code_for_food.crucentralcoast.controller.authentication.Credentials;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;
import com.will_code_for_food.crucentralcoast.values.UI;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Displays the UI for resources, including videos and articles
 */
public class ResourcesActivity extends MainActivity {

    protected String title = "Resources";
    protected String video_title = title + " > Videos";
    protected String article_title = title + " > Articles";

    public static Resource selectedResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_resources, title, null, this);
    }

    /**
     * Displays the Cru YouTube videos in a list
     */
    public void viewVideos(View view) {
        loadFragmentById(R.layout.fragment_card_list, video_title,
                new ResourceVideoFragment(), this);
    }

    /**
     * Displays the Cru articles in a list
     */
    public void viewArticles(View view) {
        loadFragmentById(R.layout.fragment_card_list, article_title,
                new ResourceArticleFragment(), this);
    }

    /**
     * Opens the Cru Global Facebook page
     */
    public void openFacebook(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UI.CRU_FACEBOOK_LINK));
        startActivity(browserIntent);
    }

    /**
     * Opens the Cru Global Youtube page
     */
    public void openYoutube(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UI.CRU_YOUTUBE_LINK));
        startActivity(browserIntent);
    }

    /**
     * Opens the Cru Global Twitter page
     */
    public void openTwitter(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UI.CRU_TWITTER_LINK));
        startActivity(browserIntent);
    }

    /**
     * Opens the Cru Global Instagram  page
     */
    public void openInstagram(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UI.CRU_INSTAGRAM_LINK));
        startActivity(browserIntent);
    }
}
