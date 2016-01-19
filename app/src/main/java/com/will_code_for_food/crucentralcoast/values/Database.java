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

    public static final String JSON_KEY_CAMPUS_URL = "url";

    public static final String JSON_KEY_MINISTRY_CAMPUSES = "campuses";

    public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mmZ";
    public static final String JSON_KEY_EVENT_STARTDATE = "startDate";
    public static final String JSON_KEY_EVENT_LOCATION = "location";
    public static final String JSON_KEY_EVENT_MINISTRIES = "parentMinistries";


    public static final String HTTP_REQUEST_METHOD_GET = "GET";
    public static final String HTTP_REQUEST_METHOD_POST = "POST";
    public static final String REST_QUERY_GET_ALL = "/list";
    public static final String REST_QUERY_FIND = "/find";
}
