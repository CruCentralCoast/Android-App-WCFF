package com.will_code_for_food.crucentralcoast.controller.retrieval;

import android.util.Log;

import com.google.gson.JsonElement;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Youtube;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayla on 2/25/2016.
 */
public class VideoRetriever implements Retriever {

    @Override
    public Content<Video> getAll() {

        PlaylistRetriever playlistRetriever = new PlaylistRetriever();
        List<Playlist> playlists = playlistRetriever.getAll();
        List<Video> videos = new ArrayList<>();

        for (Playlist playlist : playlists) {
            videos.addAll(playlist.getVideoContent());

            /*
            System.out.println("*****************************************************************************************************************");
            for (JsonElement e : playlist.getVideosJSON()) {
                System.out.println(e.toString());
            }
            System.out.println("*****************************************************************************************************************");
            */
        }

        Log.d("VideoRetriever", "got " + videos.size() + " videos");
        return new Content<>(videos, ContentType.LIVE);
    }
}
