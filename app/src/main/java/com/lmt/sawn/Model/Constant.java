package com.lmt.sawn.Model;

/**
 * Created by lwinmoethu on 5/22/16.
 */
public interface Constant {
    public static final String PREFERENCE_KEY = "com.lmt.sawn";
    public static final String TAG_ID = "id";
    public static final String JSON_URL="https://spreadsheets.google.com/feeds/list/13Sfc35dMYC_tqXh4BFPIyVXSO-E0qJg4f9-mV2ipC1k/1/public/basic?alt=json";


    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_SWIYAM="SWIYAM";
    public static final String TABLE_TIMETABLE="TIMETABLE";
    public static final String TABLE_SWIYAM_HEADER="SWIYAM_HEADER";

    // Login Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_LID = "lid";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_DIVISION = "division";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_TOWN = "town";
    public static final String KEY_DAY="day";
    public static final String KEY_CLOSE="close";
    public static final String KEY_OPEN="open";

    public static final String KEY_COUNTRY_NAME="country_name";
    public static final String KEY_COUNTRY_CODE="country_code";
    public static final String KEY_URL="url";

}
