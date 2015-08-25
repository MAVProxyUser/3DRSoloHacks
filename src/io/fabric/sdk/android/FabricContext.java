package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import java.io.File;

class FabricContext extends ContextWrapper
{
  private final String componentName;
  private final String componentPath;

  public FabricContext(Context paramContext, String paramString1, String paramString2)
  {
    super(paramContext);
    this.componentName = paramString1;
    this.componentPath = paramString2;
  }

  public File getCacheDir()
  {
    return new File(super.getCacheDir(), this.componentPath);
  }

  public File getDatabasePath(String paramString)
  {
    File localFile = new File(super.getDatabasePath(paramString).getParentFile(), this.componentPath);
    localFile.mkdirs();
    return new File(localFile, paramString);
  }

  @TargetApi(8)
  public File getExternalCacheDir()
  {
    return new File(super.getExternalCacheDir(), this.componentPath);
  }

  @TargetApi(8)
  public File getExternalFilesDir(String paramString)
  {
    return new File(super.getExternalFilesDir(paramString), this.componentPath);
  }

  public File getFilesDir()
  {
    return new File(super.getFilesDir(), this.componentPath);
  }

  public SharedPreferences getSharedPreferences(String paramString, int paramInt)
  {
    return super.getSharedPreferences(this.componentName + ":" + paramString, paramInt);
  }

  public SQLiteDatabase openOrCreateDatabase(String paramString, int paramInt, SQLiteDatabase.CursorFactory paramCursorFactory)
  {
    return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(paramString), paramCursorFactory);
  }

  @TargetApi(11)
  public SQLiteDatabase openOrCreateDatabase(String paramString, int paramInt, SQLiteDatabase.CursorFactory paramCursorFactory, DatabaseErrorHandler paramDatabaseErrorHandler)
  {
    return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(paramString).getPath(), paramCursorFactory, paramDatabaseErrorHandler);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.FabricContext
 * JD-Core Version:    0.6.2
 */