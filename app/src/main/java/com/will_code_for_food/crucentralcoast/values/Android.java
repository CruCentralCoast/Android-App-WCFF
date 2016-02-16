package com.will_code_for_food.crucentralcoast.values;

/**
 * Created by MasonJStevenson on 1/20/2016.
 */
public class Android {

    // PREFERENCES FILE
    public static final String PREFS_FILE = "com.will_code_for_food.crucentralcoast_preferences";
    public static final String PREF_CAMPUSES = "pref_campuses";
    public static final String PREF_MINISTRIES = "pref_ministries";
    public static final String PREF_SETUP_COMPLETE = "setup_complete";
    public static final String PREF_CLEAR = "pref_clear";

    // YOUTUBE
    public static final String YOUTUBE_USERNAME = "slocrusade";
    public static final String YOUTUBE_UPLOADS_ID = "UUe-RJ-3Q3tUqJciItiZmjdg";
    public static final String YOUTUBE_MAXRESULTS = "50";
    public static final String YOUTUBE_KEY = "AIzaSyDx-YHJnv8FcLKlzgpD99ZpfLDjICRRFNI";
    public static final String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=";
    // JSON KEYS
    public static final String YOUTUBE_JSON_LIST = "items";
    public static final String YOUTUBE_JSON_SNIPPET = "snippet";
    public static final String YOUTUBE_JSON_DATE = "publishedAt";
    public static final String YOUTUBE_JSON_TITLE = "title";
    public static final String YOUTUBE_JSON_DESCRIPTION = "description";
    public static final String YOUTUBE_JSON_RESOURCE = "resourceId";
    public static final String YOUTUBE_JSON_ID = "videoId";
    public static final String YOUTUBE_JSON_THUMBNAIL = "thumbnails";
    public static final String YOUTUBE_JSON_THUMBNAIL_DEFAULT = "default";
    public static final String YOUTUBE_JSON_THUMBNAIL_URL = "url";
    public static final String YOUTUBE_JSON_NEXTPAGE = "nextPageToken";
    public static final String YOUTUBE_JSON_PREVPAGE = "prevPageToken";
    // JSON QUERIES
    public static final String YOUTUBE_QUERY = "https://www.googleapis.com/youtube/v3/";
    public static final String YOUTUBE_QUERY_CHANNEL = "channels?part=contentDetails";
    public static final String YOUTUBE_QUERY_PLAYLIST = "playlistItems?part=snippet";
    public static final String YOUTUBE_QUERY_PLAYLIST_ID = "&playlistId=";
    public static final String YOUTUBE_QUERY_USERNAME = "&forUsername=";
    public static final String YOUTUBE_QUERY_MAXRESULTS = "&maxResults=";
    public static final String YOUTUBE_QUERY_KEY = "&key=";
    public static final String YOUTUBE_QUERY_PAGE = "&pageToken=";

    public static final String YOUTUBE_QUERY_SLOCRUSADE_CHANNEL =
            "https://www.googleapis.com/youtube/v3/" +
                    "channels?part=contentDetails" +
                    "&forUsername=slocrusade" +
                    "&maxResults=10" +
                    "&key=AIzaSyDx-YHJnv8FcLKlzgpD99ZpfLDjICRRFNI";
    public static final String YOUTUBE_QUERY_SLOCRUSADE_UPLOADS =
            "https://www.googleapis.com/youtube/v3/" +
                    "playlistItems?part=snippet" +
                    "&playlistId=UUe-RJ-3Q3tUqJciItiZmjdg" +
                    "&maxResults=10" +
                    "&key=AIzaSyDx-YHJnv8FcLKlzgpD99ZpfLDjICRRFNI";
}
