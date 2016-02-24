package com.will_code_for_food.crucentralcoast.controller.retrieval;

import android.util.Log;

import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Video;
import com.will_code_for_food.crucentralcoast.values.Android;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MasonJStevenson on 2/18/2016.
 */
public class VideoRetriever implements Retriever {
    @Override
    public Content<Video> getAll() {

        Playlist videoPlaylist;
        List<Video> videos = null;

        videoPlaylist = RestUtil.getPlaylist(Android.YOUTUBE_QUERY_SLOCRUSADE_UPLOADS);

        if (videoPlaylist != null) {
            videos = videoPlaylist.getVideoList();
        }

        if (videos != null) {
            Log.d("VideoRetriever", "got " + videos.size() + " videos");
            return new Content<Video>(videos, ContentType.LIVE);
        } else {
            videos = new ArrayList<Video>();
            return new Content<Video>(videos, ContentType.CACHED);
        }
    }
}
