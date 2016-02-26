package com.will_code_for_food.crucentralcoast.model.resources;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.values.Youtube;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayla on 2/15/2016.
 */
public class Playlist extends DatabaseObject {

    private String baseUrl;    // base url to load videos from
    private JsonObject fields; // holds the fields
    private String nextPage;   // holds the next page token
    private Content<Video> videoContent;

    public Playlist(JsonObject jsonObject, String url) {
        super(jsonObject);
        baseUrl = url;
        fields = jsonObject;
        nextPage = getNextPage();

        JsonArray videos = fields.get(Youtube.JSON_LIST).getAsJsonArray();
        videoContent = new Content<>(null, ContentType.LIVE);
        for (int i = 0; i < videos.size(); i++) {
            videoContent.add(new Video(videos.get(i).getAsJsonObject()));
        }
    }

    public Content<Video> getVideoContent() {
        return videoContent;
    }

    public String getChannelId() {
        JsonArray videos = fields.get(Youtube.JSON_LIST).getAsJsonArray();
        JsonObject video = videos.get(0).getAsJsonObject();
        JsonObject snippet = video.get(Youtube.JSON_SNIPPET).getAsJsonObject();
        return snippet.get(Youtube.JSON_CHANNEL_ID).getAsString();
    }

    public void loadMore() {
        new UpdatePlaylist().execute();
    }

    private void loadNextPage() {
        if (nextPage != "") {
            String query = baseUrl;
            String page = Youtube.QUERY_PAGE + nextPage;
            Playlist newPlaylist = RestUtil.getPlaylist(query + page);

            if (newPlaylist != null) {
                fields = newPlaylist.fields;
                nextPage = newPlaylist.getNextPage();
                videoContent.addAll(newPlaylist.getVideoContent());
            }
        }
    }

    private String getNextPage() {
        if (fields.get(Youtube.JSON_NEXTPAGE) != null)
            return fields.get(Youtube.JSON_NEXTPAGE).getAsString();
        return "";
    }

    /**
     * Update the videos in the existing playlist by loading more
     */
    private class UpdatePlaylist extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            loadNextPage();
            return null;
        }
    }
}