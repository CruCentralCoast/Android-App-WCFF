package com.will_code_for_food.crucentralcoast.values;

/**
 * Created by MasonJStevenson on 1/16/2016.
 */
public class Database {

    public static final String DB_URL = "http://ec2-52-91-235-48.compute-1.amazonaws.com:3000/api/";
    public static final int DB_TIMEOUT = 2000;

    public static final String REST_MINISTRY = "ministry";
    public static final String REST_CAMPUS = "campus";
    public static final String REST_EVENT = "event";
    public static final String REST_SUMMER_MISSION = "summermission";
    public static final String REST_RIDE = "ride";
    public static final String REST_PASSENGER = "passenger";

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
    public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String JSON_KEY_CAMPUS_URL = "url";

    public static final String JSON_KEY_MINISTRY_CAMPUSES = "campuses";

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
    public static final String JSON_KEY_RIDE_GCM = "gcmAPIKey";
    public static final String JSON_KEY_RIDE_LOCATION = "location";
    public static final String JSON_KEY_RIDE_TIME = "time";
    public static final String JSON_KEY_RIDE_RADIUS ="radius";
    public static final String JSON_KEY_RIDE_SEATS = "seats";
    public static final String JSON_KEY_RIDE_DIRECTION = "direction";
    public static final String JSON_KEY_RIDE_GENDER = "gender";

    public static final String JSON_KEY_USER_NAME = "name";
    public static final String JSON_KEY_USER_PHONE = "phone";
    public static final String JSON_KEY_USER_GCM = "gcm_id";
    public static final String JSON_KEY_USER_DIRECTION = "direction";

    public static final String HTTP_REQUEST_METHOD_GET = "GET";
    public static final String HTTP_REQUEST_METHOD_POST = "POST";
    public static final String REST_QUERY_GET_ALL = "/list";
    public static final String REST_QUERY_FIND = "/find";

    public static final String GOOGLE_MAP = "http://maps.google.co.in/maps?q=";
}
