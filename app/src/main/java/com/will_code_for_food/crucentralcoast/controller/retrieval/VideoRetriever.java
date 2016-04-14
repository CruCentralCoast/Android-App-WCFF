package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Video;

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
        }

        Logger.d("VideoRetriever", "got " + videos.size() + " videos");
        return new Content<>(videos, ContentType.LIVE);
    }
}
