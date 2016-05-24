package com.will_code_for_food.crucentralcoast.model.resources;

import com.google.gson.JsonObject;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.model.common.common.JsonDatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.values.Youtube;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Video represents a YouTube video loaded from a Json query
 * Used to retrieve information needed to play and display the YouTube video
 */
public class Video extends JsonDatabaseObject implements Comparable {

    JsonObject snippet;

    public Video (JsonObject json) {
        super(json);
        fields = json;
        snippet = getField(Youtube.JSON_SNIPPET).getAsJsonObject();
    }

    //for compatibility
    @Override
    public String getName() {
        return getTitle();
    }

    // The title of the video
    public String getTitle() {
        return snippet.get(Youtube.JSON_TITLE).getAsString();
    }

    // The description of the video
    public String getDescription() {
        return snippet.get(Youtube.JSON_DESCRIPTION).getAsString();
    }

    // The video id of the video
    public String getId() {
        JsonObject resourceId;
        String id = "";

        if (snippet.get(Youtube.JSON_RESOURCE) != null) {
            resourceId = snippet.get(Youtube.JSON_RESOURCE).getAsJsonObject();
            id = resourceId.get(Youtube.JSON_VIDEO_ID).getAsString();
        } else if (fields.get(Youtube.JSON_ID) != null) {
            resourceId = fields.get(Youtube.JSON_ID).getAsJsonObject();
            id = resourceId.get(Youtube.JSON_VIDEO_ID).getAsString();
        }

        return id;
    }

    // The date the video was uploaded
    public String getPublishDate() {
        return snippet.get(Youtube.JSON_DATE).getAsString();
    }

    // How long ago the video was created (ex: 1 day ago)
    public int getAge() {
        int videoAgeInDays;

        try {
            DateFormat dateFormat = new SimpleDateFormat(Database.ISO_FORMAT);
            Date published = dateFormat.parse(getPublishDate());
            Date current = new Date();
            videoAgeInDays = (int)((current.getTime() - published.getTime()) / (1000*60*60*24l));
        } catch (Exception e) {
            videoAgeInDays = 0;
        }

        return videoAgeInDays;
    }

    public String getAgeString() {
        return getAge() + " " + Util.getString(R.string.days);
    }

    // The image url for the default thumbnail
    public String getThumbnailUrl() {
        JsonObject imageData = snippet.getAsJsonObject(Youtube.JSON_THUMBNAIL);
        JsonObject defaultImage = imageData.getAsJsonObject(Youtube.JSON_THUMBNAIL_RES);
        return defaultImage.get(Youtube.JSON_THUMBNAIL_URL).getAsString();
    }

    @Override
    public int compareTo(Object another) {
        Video other = (Video) another;
        int myAge = getAge();
        int theirAge = other.getAge();

        if (myAge > theirAge) {
            return 1;
        }
        if (myAge < theirAge) {
            return -1;
        }
        return 0;
    }
}
