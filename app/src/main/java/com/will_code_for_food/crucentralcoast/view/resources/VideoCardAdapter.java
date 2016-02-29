package com.will_code_for_food.crucentralcoast.view.resources;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.DatabaseObjectSorter;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.SortMethod;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Youtube;
import com.will_code_for_food.crucentralcoast.view.common.CardAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kayla on 2/29/2016.
 */
public class VideoCardAdapter extends CardAdapter {

    List<Playlist> playlistsTemp;

    public VideoCardAdapter(Context context, int resource, Content content) {
        super(context, resource, content);
        playlistsTemp = new ArrayList<>();
    }

    public List<Playlist> search(List<Playlist> playlists, String phrase) throws ExecutionException, InterruptedException {
        cardsTemp = cards;
        playlistsTemp = playlists;
        List<Playlist> filteredLists = new SearchVideos(playlists).execute(phrase).get();
        this.notifyDataSetChanged();
        return filteredLists;
    }

    public List<Playlist> clearSearch(List<Playlist> playlists) {
        cards.clear();
        for (Playlist playlist : playlistsTemp) {
            cards.addAll(playlist.getVideoContent());
        }
        this.notifyDataSetChanged();
        return playlistsTemp;
    }

    @Override
    public void sortByType() {
        return;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video current = (Video) cards.get(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View hold = inflater.inflate(R.layout.fragment_resources_youtube_card, parent, false);

        // Load card elements with video information
        ImageView thumbnail = (ImageView) hold.findViewById(R.id.card_image);
        Picasso.with(ResourcesActivity.context).load(current.getThumbnailUrl()).fit().into(thumbnail);

        TextView name = (TextView) hold.findViewById(R.id.card_video_name);
        name.setText(current.getTitle());

        TextView date = (TextView) hold.findViewById(R.id.card_video_date);
        date.setText(current.getAgeString());

        return hold;
    }

    private class SearchVideos extends AsyncTask<String, Void, List<Playlist>> {
        private List<Playlist> playlists;

        public SearchVideos(List<Playlist> playlists) {
            this.playlists = playlists;
        }

        @Override
        protected List<Playlist> doInBackground(String... search) {
            List<Playlist> filteredPlaylists = new ArrayList<>();
            String query = Youtube.QUERY + Youtube.QUERY_SEARCH;
            String searchTopic = Youtube.QUERY_SEARCH_TOPIC + search[0];
            cards.clear();

            for (Playlist playlist : playlists) {
                String channelQuery = Youtube.QUERY_CHANNEL_ID + playlist.getChannelId();
                String url = query + searchTopic + channelQuery + Youtube.QUERY_KEY;
                Playlist filtered = RestUtil.getPlaylist(url);
                filteredPlaylists.add(filtered);
                cards.addAll(filtered.getVideoContent());
            }
            return filteredPlaylists;
        }
    }
}