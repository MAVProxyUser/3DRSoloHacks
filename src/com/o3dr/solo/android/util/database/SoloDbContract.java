package com.o3dr.solo.android.util.database;

import android.provider.BaseColumns;

public final class SoloDbContract
{
  public static final String DB_NAME = "solo_db";
  public static final int DB_VERSION = 1;
  private static final String TAG = SoloDbContract.class.getSimpleName();

  public static String[] getSqlCreateEntries()
  {
    return new String[] { "CREATE TABLE IF NOT EXISTS versions (_id INTEGER PRIMARY KEY,wifi_ssid TEXT UNIQUE NOT NULL,vehicle TEXT,autopilot TEXT,controller TEXT,controller_firmware TEXT )", "CREATE TABLE IF NOT EXISTS videos (_id INTEGER PRIMARY KEY,url TEXT UNIQUE NOT NULL,date INTEGER NOT NULL )" };
  }

  public static String[] getSqlDeleteEntries()
  {
    return new String[] { "DROP TABLE IF EXISTS versions", "DROP TABLE IF EXISTS videos" };
  }

  public static final class Versions
    implements BaseColumns
  {
    public static final String COLUMN_NAME_AUTOPILOT = "autopilot";
    public static final String COLUMN_NAME_CONTROLLER = "controller";
    public static final String COLUMN_NAME_CONTROLLER_FIRMWARE = "controller_firmware";
    public static final String COLUMN_NAME_VEHICLE = "vehicle";
    public static final String COLUMN_NAME_WIFI_SSID = "wifi_ssid";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS versions (_id INTEGER PRIMARY KEY,wifi_ssid TEXT UNIQUE NOT NULL,vehicle TEXT,autopilot TEXT,controller TEXT,controller_firmware TEXT )";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS versions";
    public static final String TABLE_NAME = "versions";
  }

  public static final class Videos
    implements BaseColumns
  {
    public static final String COLUMN_VIDEO_DATE = "date";
    public static final String COLUMN_VIDEO_URL = "url";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS videos (_id INTEGER PRIMARY KEY,url TEXT UNIQUE NOT NULL,date INTEGER NOT NULL )";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS videos";
    public static final String TABLE_NAME = "videos";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.database.SoloDbContract
 * JD-Core Version:    0.6.2
 */