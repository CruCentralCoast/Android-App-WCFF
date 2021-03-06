package com.will_code_for_food.crucentralcoast.view.resources;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.authentication.Authenticator;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.resources.LeaderArticleCardFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import com.will_code_for_food.crucentralcoast.values.UI;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;

import android.net.Uri;
import android.os.AsyncTask;

/**
 * Created by mallika on 1/14/16.
 */
public class ResourcesActivity extends MainActivity {
    protected String title = "Resources";
    protected String video_title = title + " > Videos";
    protected String article_title = title + " > Articles";

    public static Resource selectedResource;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_resources, title, new ResourcesFragment(), this);
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && Authenticator.isUserLoggedIn()) {
            goToLeaderResources();
        }
    }*/

    /**
     * Displays the Cru YouTube videos in a list
     */
    public void viewVideos() {
        loadFragmentById(R.layout.fragment_card_list, video_title, new ViewVideosFragment(), this);
    }

    /**
     * Displays the Cru articles in a list
     */
    public void viewArticles() {
        ResourceArticleFragment fragment = new ResourceArticleFragment();
        fragment.setFactory(new ArticleCardFactory());
        loadFragmentById(R.layout.fragment_card_list, article_title, fragment, this);
    }

    public void viewLeaderResources() {
        if (!Authenticator.isUserLoggedIn() && !Authenticator.logIn()) {
            //startActivityForResult(new Intent(this, LogInActivity.class), 1);
            loadFragmentById(R.layout.fragment_login_form, Util.getString(context, R.string.login_title), new LogInFragment(), this);
        } else {
            goToLeaderResources();
        }
    }

    private void goToLeaderResources() {
        ResourceArticleFragment fragment = new ResourceArticleFragment();
        fragment.setFactory(new LeaderArticleCardFactory());
        loadFragmentById(R.layout.fragment_card_list, article_title, fragment, this);
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