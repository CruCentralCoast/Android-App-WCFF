package com.will_code_for_food.crucentralcoast.view.resources;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.authentication.Authenticator;
import com.will_code_for_food.crucentralcoast.controller.authentication.Credentials;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;
import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import com.will_code_for_food.crucentralcoast.model.ridesharing.DriverForm;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;
import com.will_code_for_food.crucentralcoast.values.UI;

import com.will_code_for_food.crucentralcoast.controller.authentication.Credentials;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;

import android.content.Intent;
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
        loadFragmentById(R.layout.fragment_resources, "Resources", null, this);
    }

    public void clickLeaderResources(View view) {
        if (!Authenticator.isUserLoggedIn() && !Authenticator.logIn()) {
            startActivityForResult(new Intent(this, LogInActivity.class), 1);
        } else {
            goToLeaderResources();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && Authenticator.isUserLoggedIn()) {
            goToLeaderResources();
        }
    }

    private void goToLeaderResources() {
        // TODO actually show leader resources
        Toast.makeText(context, "Show leader resources...", Toast.LENGTH_LONG).show();
    }

    /**
     * Displays the Cru YouTube videos in a list
     */
    public void viewVideos(View view) {
        loadFragmentById(R.layout.fragment_card_list, video_title, new ResourceVideoFragment(), this);
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
    
    //TODO delete this and use RetrievalTask/ResourceCardFactory
    private class ArticleTask extends AsyncTask<Void, Void, Void> {

        MainActivity parent;

        public ArticleTask(MainActivity parent) {
            this.parent = parent;
        }
        @Override
        protected Void doInBackground(Void... params) {
            SingleRetriever<Resource> retriever = new SingleRetriever<Resource>(RetrieverSchema.RESOURCE);
            Content<Resource> resOjbects = retriever.getAll();

            if (!resOjbects.isEmpty()) {
                selectedResource = resOjbects.get(0);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadFragmentById(R.layout.fragment_article, article_title, new ViewArticleFragment(), parent);
        }
    }
}
