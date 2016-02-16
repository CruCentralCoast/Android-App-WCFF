package com.will_code_for_food.crucentralcoast.model.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.values.Android;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayla on 2/15/2016.
 */
public class Playlist extends DatabaseObject {

    private JsonObject fields; // holds the fields
    private String prevPage;   // holds the prev page token
    private String nextPage;   // holds the next page token

    private List<Video> videoList;
    private Content<Video> videoContent;

    public Playlist(JsonObject jsonObject) {
        super(jsonObject);
        fields = jsonObject;
        prevPage = getPrevPage();
        nextPage = getNextPage();

        JsonArray videos = fields.get(Android.YOUTUBE_JSON_LIST).getAsJsonArray();
        videoList = new ArrayList<>();
        for (int i = 0; i < videos.size(); i++) {
            videoList.add(new Video(videos.get(i).getAsJsonObject()));
        }
        videoContent = new Content<>(videoList, ContentType.LIVE);
    }

    public String getPrevPage() {
        if (fields.get(Android.YOUTUBE_JSON_PREVPAGE) != null)
            return fields.get(Android.YOUTUBE_JSON_PREVPAGE).getAsString();
        return "";
    }

    public String getNextPage() {
        if (fields.get(Android.YOUTUBE_JSON_NEXTPAGE) != null)
            return fields.get(Android.YOUTUBE_JSON_NEXTPAGE).getAsString();
        return "";
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public Content<Video> getVideoContent() {
        return videoContent;
    }

    public void loadNextPage() {
        if (nextPage != "") {
            String query = Android.YOUTUBE_QUERY_SLOCRUSADE_UPLOADS;
            String page = Android.YOUTUBE_QUERY_PAGE + nextPage;
            Playlist newPlaylist = RestUtil.getPlaylist(query + page);

            if (newPlaylist != null) {
                fields = newPlaylist.fields;
                nextPage = newPlaylist.getNextPage();
                prevPage = newPlaylist.getPrevPage();
                videoList.addAll(newPlaylist.getVideoList());
                videoContent.setObjects(videoList);
            }
        }
    }
}