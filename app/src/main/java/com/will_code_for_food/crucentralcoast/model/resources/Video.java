package com.will_code_for_food.crucentralcoast.model.resources;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.values.Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Video represents a YouTube video loaded from a Json query
 * Used to retrieve information needed to play and display the YouTube video
 */
public class Video extends Resource {

    private JsonObject fields;

    public Video (JsonObject json) {
        super();
        fields = json;
    }

    private JsonObject getSnippet() {
        return fields.get(Android.YOUTUBE_JSON_SNIPPET).getAsJsonObject();
    }

    // The title of the video
    public String getTitle() {
        return getSnippet().get(Android.YOUTUBE_JSON_TITLE).getAsString();
    }

    // The description of the video
    public String getDescription() {
        return getSnippet().get(Android.YOUTUBE_JSON_DESCRIPTION).getAsString();
    }

    // The video id of the video
    public String getId() {
        JsonObject resourceId = getSnippet().get(Android.YOUTUBE_JSON_RESOURCE).getAsJsonObject();
        return resourceId.get(Android.YOUTUBE_JSON_ID).getAsString();
    }

    // The date the video was uploaded
    public String getPublishDate() {
        return getSnippet().get(Android.YOUTUBE_JSON_DATE).getAsString();
    }

    // How long ago the video was created (ex: 1 day ago)
    public String getAge() {
        int videoAgeInDays;

        try {
            DateFormat dateFormat = new SimpleDateFormat(Database.ISO_FORMAT);
            Date published = dateFormat.parse(getPublishDate());
            Date current = new Date();
            videoAgeInDays = (int)((current.getTime() - published.getTime()) / (1000*60*60*24l));
        } catch (Exception e) {
            videoAgeInDays = 0;
        }

        return videoAgeInDays + " days ago";
    }

    // The image url for the default thumbnail
    public String getThumbnailUrl() {
        JsonObject imageData = getSnippet().getAsJsonObject(Android.YOUTUBE_JSON_THUMBNAIL);
        JsonObject defaultImage = imageData.getAsJsonObject(Android.YOUTUBE_JSON_THUMBNAIL_DEFAULT);
        return defaultImage.get(Android.YOUTUBE_JSON_THUMBNAIL_URL).getAsString();
    }
}
