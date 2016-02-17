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
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
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
        new LoadVideosTask().execute();
        return fragmentView;
    }

    /**
     * Loads a list of videos from the Cru YT channel and displays them in cards
     */
    private class LoadVideosTask extends AsyncTask<Void, Void, Void> {

        private Playlist videoPlaylist;
        private List<Video> videos;
        private MainActivity currentActivity;
        private CardFragmentFactory cardFactory;
        private ListView list;

        // used for scrolling pagination
        private int firstItem, visibleItems, totalItems;

        public LoadVideosTask() {
            super();
            currentActivity = (MainActivity) MainActivity.context;
            cardFactory = new VideoCardFactory();
        }

        @Override
        protected Void doInBackground(Void... params) {
            videoPlaylist = RestUtil.getPlaylist(Android.YOUTUBE_QUERY_SLOCRUSADE_UPLOADS);
            videos = videoPlaylist.getVideoList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            list = (ListView) currentActivity.findViewById(R.id.list_cards);

            if ((videos != null) && (!videos.isEmpty())) {
                final Content<Video> videoContent = videoPlaylist.getVideoContent();
                list.setAdapter(cardFactory.createAdapter(videoContent));
                list.setOnItemClickListener(cardFactory.createCardListener(currentActivity, videoContent));
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
                            try {
                                videoPlaylist = new UpdatePlaylist().execute(videoPlaylist).get();
                                final Content<Video> videoContent = videoPlaylist.getVideoContent();
                                list.setAdapter(cardFactory.createAdapter(videoContent));
                                list.setOnItemClickListener(cardFactory.createCardListener(currentActivity, videoContent));
                                list.setSelection(firstItem);
                            } catch (Exception e) {
                                String errorMessage = Util.getString(R.string.toast_no_videos);
                                Toast.makeText(currentActivity.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            } else {
                String errorMessage = Util.getString(R.string.toast_no_videos);
                Toast.makeText(currentActivity.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Update the videos in the existing playlist by loading more
     */
    private class UpdatePlaylist extends AsyncTask<Playlist, Void, Playlist> {
        @Override
        protected Playlist doInBackground(Playlist... params) {
            Playlist playlist = params[0];
            playlist.loadNextPage();
            return playlist;
        }
    }
}