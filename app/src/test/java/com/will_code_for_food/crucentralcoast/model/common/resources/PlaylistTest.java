package com.will_code_for_food.crucentralcoast.model.common.resources;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.RestUtil;
import com.will_code_for_food.crucentralcoast.model.resources.Playlist;
import com.will_code_for_food.crucentralcoast.model.resources.Video;

import junit.framework.TestCase;

/**
 * Created by Kayla on 3/9/2016.
 */
public class PlaylistTest extends TestCase {

    public void testPlaylist() {
        Playlist testPlaylist = RestUtil.getPlaylistFromUser("slocrusade");
        Content<Video> testVideos = testPlaylist.getVideoContent();

        assertTrue(testPlaylist != null);
        assertTrue(testVideos != null);

        assertEquals(10, testVideos.size());
        assertEquals(-1, testVideos.get(0).compareTo(testVideos.get(1)));
    }
}