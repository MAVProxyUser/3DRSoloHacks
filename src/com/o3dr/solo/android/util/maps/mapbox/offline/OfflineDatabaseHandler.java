package com.o3dr.solo.android.util.maps.mapbox.offline;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OfflineDatabaseHandler extends SQLiteOpenHelper
{
  public static final int DATABASE_VERSION = 1;
  public static final String FIELD_DATA_ID = "id";
  public static final String FIELD_DATA_VALUE = "value";
  public static final String FIELD_RESOURCES_ID = "id";
  public static final String FIELD_RESOURCES_STATUS = "status";
  public static final String FIELD_RESOURCES_URL = "url";
  public static final String TABLE_DATA = "data";
  public static final String TABLE_RESOURCES = "resources";
  private static final String TAG = OfflineDatabaseHandler.class.getSimpleName();

  public OfflineDatabaseHandler(Context paramContext, String paramString)
  {
    super(paramContext, paramString, null, 1);
  }

  public byte[] dataForURL(String paramString)
  {
    return sqliteDataForURL(paramString);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    Log.i(TAG, "onCreate() called... Setting up application's database.");
    paramSQLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
    paramSQLiteDatabase.beginTransaction();
    try
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE data (id INTEGER PRIMARY KEY, value BLOB);");
      paramSQLiteDatabase.execSQL("CREATE TABLE resources (url TEXT UNIQUE, status TEXT, id INTEGER REFERENCES data);");
      paramSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    catch (SQLException localSQLException)
    {
      Log.e(TAG, "Error creating databases", localSQLException);
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    Log.w(TAG, "Upgrading database from version " + paramInt1 + " to " + paramInt2 + ", which will destroy all old data");
    paramSQLiteDatabase.execSQL("PRAGMA foreign_keys=OFF;");
    paramSQLiteDatabase.execSQL("drop table if exists data");
    paramSQLiteDatabase.execSQL("drop table if exists resources");
    onCreate(paramSQLiteDatabase);
  }

  public byte[] sqliteDataForURL(String paramString)
  {
    Cursor localCursor = getReadableDatabase().rawQuery("SELECT value FROM data WHERE id= (SELECT id from resources where url = '" + paramString + "');", null);
    byte[] arrayOfByte = null;
    if (localCursor != null)
    {
      boolean bool = localCursor.moveToFirst();
      arrayOfByte = null;
      if (bool)
        arrayOfByte = localCursor.getBlob(localCursor.getColumnIndex("value"));
      localCursor.close();
    }
    return arrayOfByte;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.maps.mapbox.offline.OfflineDatabaseHandler
 * JD-Core Version:    0.6.2
 */