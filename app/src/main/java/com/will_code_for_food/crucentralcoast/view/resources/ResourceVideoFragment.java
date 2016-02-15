package com.will_code_for_food.crucentralcoast.view.resources;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.UI;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayla on 2/11/2016.
 */
public class ResourceVideoFragment extends CruFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        new LoadVideosTask(new ArrayList<Video>()).execute();
        return fragmentView;
    }

    /**
     * Loads a list of videos from the Cru YT channel and displays them in cards
     */
    private class LoadVideosTask extends AsyncTask<Void, Void, Void> {

        private List<Video> videos;
        private MainActivity currentActivity;
        private CardFragmentFactory cardFactory;
        // used for scrolling pagination
        private int firstItem, visibleItems, totalItems;

        public LoadVideosTask(List<Video> myVideos) {
            super();
            videos = myVideos;
            currentActivity = (MainActivity) MainActivity.context;
            cardFactory = new VideoCardFactory();
        }

        @Override
        protected Void doInBackground(Void... params) {

            // Load a list of 10 videos
            JsonArray videoArray = RestUtil.getVideos(Android.YOUTUBE_QUERY_SLOCRUSADE_UPLOADS);
            for (int i = 0; i < videoArray.size(); i++) {
                videos.add(new Video(videoArray.get(i).getAsJsonObject()));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListView list = (ListView) currentActivity.findViewById(R.id.list_youtube);

            if ((videos != null) && (!videos.isEmpty())) {
                list.setAdapter(cardFactory.createAdapter(videos));
                list.setOnItemClickListener(cardFactory.createCardListener(currentActivity, videos));
                list.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScroll(AbsListView view,
                                         int first, int visible, int total) {
                        firstItem = first;
                        visibleItems = visible;
                        totalItems = total;
                    }
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        final int lastItem = firstItem + visibleItems;
                        if (lastItem == totalItems && scrollState == SCROLL_STATE_IDLE) {
                            //new LoadVideosTask(videos).execute();
                        }
                    }
                });
            } else {
                //String errorMessage = Util.getString(errorMessageId);
                //Toast.makeText(currentActivity.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }
}