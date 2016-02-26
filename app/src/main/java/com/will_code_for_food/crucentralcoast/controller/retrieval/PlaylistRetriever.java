package com.will_code_for_food.crucentralcoast.controller.retrieval;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MasonJStevenson on 2/18/2016.
 */
public class PlaylistRetriever implements Retriever {

    @Override
    public Content<Playlist> getAll() {

        List<Playlist> playlists = null;

        //TODO: load video content from user's ministry youtube accounts
        Playlist sloPlaylist = RestUtil.getPlaylistFromUser("slocrusade");
        Playlist globalPlaylist = RestUtil.getPlaylistFromUser("cruglobal");

        if (sloPlaylist != null || globalPlaylist != null) {
            playlists = new ArrayList<>();
            playlists.add(sloPlaylist);
            playlists.add(globalPlaylist);
        }

        if (playlists != null) {
            Log.d("PlaylistRetriever", "got " + playlists.size() + " playlists");
            return new Content<>(playlists, ContentType.LIVE);
        } else {
            playlists = new ArrayList<>();
            return new Content<>(playlists, ContentType.CACHED);
        }
    }
}
