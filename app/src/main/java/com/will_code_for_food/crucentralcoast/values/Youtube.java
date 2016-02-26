package com.will_code_for_food.crucentralcoast.values;

/**
 * Created by Kayla on 2/24/2016.
 */
public class Youtube {

    // YOUTUBE
    public static final String MINRESULTS = "10";
    public static final String MAXRESULTS = "50";

    // JSON KEYS
    public static final String JSON_LIST = "items";
    public static final String JSON_CONTENT_DETAILS = "contentDetails";
    public static final String JSON_RELATED_PLAYLISTS = "relatedPlaylists";
    public static final String JSON_UPLOADS = "uploads";
    public static final String JSON_SNIPPET = "snippet";
    public static final String JSON_CHANNEL_ID = "channelId";
    public static final String JSON_DATE = "publishedAt";
    public static final String JSON_TITLE = "title";
    public static final String JSON_DESCRIPTION = "description";
    public static final String JSON_RESOURCE = "resourceId";
    public static final String JSON_ID = "id";
    public static final String JSON_VIDEO_ID = "videoId";
    public static final String JSON_THUMBNAIL = "thumbnails";
    public static final String JSON_THUMBNAIL_RES = "medium";
    public static final String JSON_THUMBNAIL_URL = "url";
    public static final String JSON_NEXTPAGE = "nextPageToken";
    public static final String JSON_PREVPAGE = "prevPageToken";

    // JSON QUERIES
    public static final String QUERY = "https://www.googleapis.com/youtube/v3/";
    public static final String QUERY_CHANNEL = "channels?part=contentDetails";
    public static final String QUERY_PLAYLIST = "playlistItems?part=snippet";
    public static final String QUERY_SEARCH = "search?part=snippet";
    public static final String QUERY_PLAYLIST_ID = "&playlistId=";
    public static final String QUERY_CHANNEL_ID = "&channelId=";
    public static final String QUERY_USERNAME = "&forUsername=";
    public static final String QUERY_MAXRESULTS = "&maxResults=";
    public static final String QUERY_KEY = "&key=AIzaSyDx-YHJnv8FcLKlzgpD99ZpfLDjICRRFNI";
    public static final String QUERY_PAGE = "&pageToken=";
    public static final String QUERY_SEARCH_TOPIC = "&q=";
}
