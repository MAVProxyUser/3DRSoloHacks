package com.o3dr.solo.android.util.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

public class SoloDb extends SQLiteOpenHelper
{
  private static final String TAG = SoloDb.class.getSimpleName();

  public SoloDb(Context paramContext)
  {
    super(paramContext, "solo_db", null, 1);
  }

  public String getControllerVersion(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = { "controller" };
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramString);
    Cursor localCursor = localSQLiteDatabase.query("versions", arrayOfString1, "wifi_ssid = ?", arrayOfString2, null, null, null);
    if (localCursor.moveToFirst());
    for (String str = localCursor.getString(localCursor.getColumnIndex("controller")); ; str = "")
    {
      localCursor.close();
      return str;
    }
  }

  public String getPixhawkVersion(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = { "autopilot" };
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramString);
    Cursor localCursor = localSQLiteDatabase.query("versions", arrayOfString1, "wifi_ssid = ?", arrayOfString2, null, null, null);
    if (localCursor.moveToFirst());
    for (String str = localCursor.getString(localCursor.getColumnIndex("autopilot")); ; str = "")
    {
      localCursor.close();
      return str;
    }
  }

  public String getStm32Version(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = { "controller_firmware" };
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramString);
    Cursor localCursor = localSQLiteDatabase.query("versions", arrayOfString1, "wifi_ssid = ?", arrayOfString2, null, null, null);
    if (localCursor.moveToFirst());
    for (String str = localCursor.getString(localCursor.getColumnIndex("controller_firmware")); ; str = "")
    {
      localCursor.close();
      return str;
    }
  }

  public String getVehicleVersion(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = { "vehicle" };
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramString);
    Cursor localCursor = localSQLiteDatabase.query("versions", arrayOfString1, "wifi_ssid = ?", arrayOfString2, null, null, null);
    if (localCursor.moveToFirst());
    for (String str = localCursor.getString(localCursor.getColumnIndex("vehicle")); ; str = "")
    {
      localCursor.close();
      return str;
    }
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    Log.d(TAG, "Creating solo database.");
    String[] arrayOfString = SoloDbContract.getSqlCreateEntries();
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
      paramSQLiteDatabase.execSQL(arrayOfString[j]);
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    Log.d(TAG, "Deleting solo database.");
    String[] arrayOfString = SoloDbContract.getSqlDeleteEntries();
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
      paramSQLiteDatabase.execSQL(arrayOfString[j]);
    onCreate(paramSQLiteDatabase);
  }

  public boolean wasVideoSeen(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = { "url" };
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramString);
    Cursor localCursor = localSQLiteDatabase.query("videos", arrayOfString1, "url = ?", arrayOfString2, null, null, null);
    if (localCursor.moveToFirst());
    for (String str = localCursor.getString(localCursor.getColumnIndex("url")); ; str = "")
    {
      localCursor.close();
      if (TextUtils.isEmpty(str))
        break;
      return true;
    }
    return false;
  }

  public void writeVersionInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("wifi_ssid", paramString1);
    localContentValues.put("vehicle", paramString2);
    localContentValues.put("autopilot", paramString3);
    localContentValues.put("controller", paramString4);
    localContentValues.put("controller_firmware", paramString5);
    localSQLiteDatabase.insert("versions", null, localContentValues);
  }

  public void writeVideoInfo(long paramLong, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("url", paramString);
    localContentValues.put("date", Long.valueOf(paramLong));
    localSQLiteDatabase.insert("videos", null, localContentValues);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.database.SoloDb
 * JD-Core Version:    0.6.2
 */