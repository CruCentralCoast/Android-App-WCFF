package com.will_code_for_food.crucentralcoast.view.resources;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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
public class ResourceVideoFragment extends CruFragment implements TextView.OnEditorActionListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        new LoadVideosTask().execute(Android.YOUTUBE_QUERY_SLOCRUSADE_UPLOADS);
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.options_menu, menu);
        menu.findItem(R.id.search).setActionView(R.layout.action_search);
        EditText search = (EditText)menu.findItem(R.id.search).getActionView().findViewById(R.id.text);
        search.setOnEditorActionListener(this);
        search.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event == null || event.getAction() == KeyEvent.KEYCODE_ENTER) {
            new LoadVideosTask().execute(Android.YOUTUBE_QUERY_SLOCRUSADE_SEARCH + v.getText());
        }
        return true;
    }

    /**
     * Loads a list of videos from the Cru YT channel and displays them in cards
     */
    private class LoadVideosTask extends AsyncTask<String, Void, Void> {

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
        protected Void doInBackground(String... queryUrl) {
            videoPlaylist = RestUtil.getPlaylist(queryUrl[0]);
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