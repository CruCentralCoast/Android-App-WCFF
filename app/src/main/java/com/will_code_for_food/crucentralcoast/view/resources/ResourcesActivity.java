package com.will_code_for_food.crucentralcoast.view.resources;

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
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by mallika on 1/14/16.
 */
public class ResourcesActivity extends MainActivity {

    public static Resource selectedResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_resources, "Resources", null, this);
    }

    public void testYoutube(View view) {
        YoutubeViewer.watchYoutubeVideo("hGcmaztq7eU", this);
    }

    public void testArticle(View view) {
        new ArticleTask(this).execute();
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

            loadFragmentById(R.layout.fragment_article, "Article", new ViewArticleFragment(), parent);
        }
    }
}
