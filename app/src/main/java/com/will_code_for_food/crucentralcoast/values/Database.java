package com.will_code_for_food.crucentralcoast.values;

/**
 * Created by MasonJStevenson on 1/16/2016.
 */
public class Database {

    public static final String DB_URL = "http://ec2-52-91-235-48.compute-1.amazonaws.com:3001/api/";
    public static final int DB_TIMEOUT = 2000;

    public static final String REST_MINISTRY = "ministry";
    public static final String REST_CAMPUS = "campus";
    public static final String REST_EVENT = "event";
    public static final String REST_SUMMER_MISSION = "summermission";
    public static final String REST_RIDE = "ride";
    public static final String REST_PASSENGER = "passenger";
    public static final String REST_RESOURCE = "resource";
    public static final String PLAYLISTS = "playlists";
    public static final String VIDEOS = "videos";
    public static final String MINISTRY_TEAM = "ministryteam";

    public static final String JSON_KEY_COMMON_ID = "_id";
    public static final String JSON_KEY_COMMON_IMAGE = "image";
    public static final String JSON_KEY_COMMON_DESCRIPTION = "description";
    public static final String JSON_KEY_COMMON_NAME = "name";
    public static final String JSON_KEY_COMMON_URL = "url";
    public static final String JSON_KEY_COMMON_IMAGE_URL = "url";
    public static final String JSON_KEY_COMMON_LOCATION = "location";
    public static final String JSON_KEY_COMMON_LOCATION_POSTCODE = "postcode";
    public static final String JSON_KEY_COMMON_LOCATION_STATE = "state";
    public static final String JSON_KEY_COMMON_LOCATION_SUBURB = "suburb";
    public static final String JSON_KEY_COMMON_LOCATION_STREET = "street1";
    public static final String JSON_KEY_COMMON_LOCATION_COUNTRY = "country";
    public static final String JSON_KEY_COMMON_LOCATION_LATITUDE = "latitude";
    public static final String JSON_KEY_COMMON_LOCATION_LONGITUDE = "longitude";
    public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String RESOURCE_ARTICLE = "article";
    public static final String RESOURCE_VIDEO = "video";
    public static final String RESOURCE_AUDIO = "audio";

    public static final String JSON_KEY_CAMPUS_URL = "url";
    public static final String JSON_KEY_MINISTRY_CAMPUSES = "campuses";
    public static final String JSON_KEY_TEAM_MINISTRY = "parentMinistry";

    public static final String JSON_KEY_MISSION_COST = "cost";
    public static final String JSON_KEY_MISSION_NAME = "name";
    public static final String MISSION_DATE_FORMAT = "MMMM dd, yyyy";

    public static final String JSON_KEY_EVENT_STARTDATE = "startDate";
    public static final String JSON_KEY_EVENT_ENDDATE = "endDate";
    public static final String JSON_KEY_EVENT_LOCATION = "location";
    public static final String JSON_KEY_EVENT_MINISTRIES = "parentMinistries";
    public static final String JSON_KEY_EVENT_REMINDER = "notificationDate";
    public static final String JSON_KEY_EVENT_HASRIDES = "rideSharingEnabled";
    public static final String EVENT_BAD_LOCATION = "TBD";
    public static final String EVENT_DATE_FORMAT = "MMM dd, h:mma";

    public static final String JSON_KEY_RIDE_EVENT = "event";
    public static final String JSON_KEY_RIDE_DRIVER_NAME = "driverName";
    public static final String JSON_KEY_RIDE_DRIVER_NUMBER = "driverNumber";
    public static final String JSON_KEY_RIDE_PASSENGERS = "passengers";
    public static final String JSON_KEY_RIDE_GCM = "gcm_id";
    public static final String JSON_KEY_RIDE_LOCATION = "location";
    public static final String JSON_KEY_RIDE_TIME = "time";
    public static final String JSON_KEY_RIDE_RADIUS ="radius";
    public static final String JSON_KEY_RIDE_SEATS = "seats";
    public static final String JSON_KEY_RIDE_DIRECTION = "direction";
    public static final String JSON_KEY_RIDE_GENDER = "gender";
    public static final String RIDE_TIME_FORMAT = "h:mm a";
    public static final String RIDE_DATE_FORMAT = "M/d/yy";

    public static final String JSON_KEY_USER_NAME = "name";
    public static final String JSON_KEY_USER_PHONE = "phone";
    public static final String JSON_KEY_USER_GCM = "gcm_id";
    public static final String JSON_KEY_USER_DIRECTION = "direction";

    public static final String HTTP_REQUEST_METHOD_GET = "GET";
    public static final String HTTP_REQUEST_METHOD_POST = "POST";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";
    public static final String REST_QUERY_GET_ALL = "/list";
    public static final String REST_QUERY_FIND = "/find";
    public static final String REST_QUERY_CREATE = "/create";
    public static final String REST_QUERY_ADD_PASSENGER = "/addPassenger";
    public static final String REST_QUERY_DROP_PASSENGER = "/dropPassenger";
    public static final String REST_QUERY_UPDATE = "/update";

    public static final String GOOGLE_MAP = "http://maps.google.co.in/maps?q=";
}
