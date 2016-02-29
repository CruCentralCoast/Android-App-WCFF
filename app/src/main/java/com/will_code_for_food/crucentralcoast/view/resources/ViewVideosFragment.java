package com.will_code_for_food.crucentralcoast.view.resources;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Youtube;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.FeedCardAdapter;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kayla on 2/11/2016.
 */
public class ViewVideosFragment extends CruFragment implements TextView.OnEditorActionListener {

    private List<Playlist> playlists;
    private Content<Video> videos;
    private CardFragmentFactory cardFactory;
    private ListView list;
    private MainActivity currentActivity;

    // used for scrolling pagination
    private int firstItem, visibleItems, totalItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        // Load and display the videos
        cardFactory = new VideoCardFactory();
        currentActivity = (MainActivity) MainActivity.context;
        list = (ListView) fragmentView.findViewById(R.id.list_cards);
        LoadVideos();
        DisplayVideos();

        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setActionView(R.layout.action_search);
        final MenuItem sortItem = menu.findItem(R.id.sort);
        final EditText search = (EditText) searchItem.getActionView().findViewById(R.id.text);
        search.setOnEditorActionListener(this);
        search.setImeActionLabel(Util.getString(R.string.search_title), KeyEvent.KEYCODE_ENTER);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                sortItem.setVisible(false);
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                playlists = ((VideoCardAdapter) list.getAdapter()).clearSearch(null);
                search.setText("");
                sortItem.setVisible(true);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_newest) {
            ((VideoCardAdapter) list.getAdapter()).sortByNewest();
            return true;
        } else if (item.getItemId() == R.id.sort_oldest) {
            ((VideoCardAdapter) list.getAdapter()).sortByOldest();
            return true;
        } else if (item.getItemId() == R.id.sort_type) {
            ((VideoCardAdapter) list.getAdapter()).sortByType();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event == null || event.getAction() == KeyEvent.KEYCODE_ENTER) {
            try {
                playlists = ((VideoCardAdapter) list.getAdapter()).search(playlists, v.getText().toString());
            } catch (Exception e) {
                String errorMessage = Util.getString(R.string.toast_no_videos);
                Toast.makeText(currentActivity.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    private void LoadVideos() {
        playlists = DBObjectLoader.getPlaylists();
        videos = new Content<>(null, ContentType.LIVE);

        for (Playlist playlist : playlists) {
            videos.addAll(playlist.getVideoContent());
        }
    }

    private void DisplayVideos() {
        if ((videos != null) && (!videos.isEmpty())) {
            Collections.sort(videos);
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
                        videos.clear();
                        for (Playlist playlist : playlists) {
                            playlist.loadMore();
                            videos.addAll(playlist.getVideoContent());
                        }
                        Collections.sort(videos);
                        list.setAdapter(cardFactory.createAdapter(videos));
                        list.setOnItemClickListener(cardFactory.createCardListener(currentActivity, videos));
                        list.setSelection(firstItem);
                    }
                }
            });
        } else {
            String errorMessage = Util.getString(R.string.toast_no_videos);
            Toast.makeText(currentActivity.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}