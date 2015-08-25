package com.nostra13.universalimageloader.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.IOException;

public final class StorageUtils
{
  private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
  private static final String INDIVIDUAL_DIR_NAME = "uil-images";

  public static File getCacheDirectory(Context paramContext)
  {
    return getCacheDirectory(paramContext, true);
  }

  public static File getCacheDirectory(Context paramContext, boolean paramBoolean)
  {
    try
    {
      String str3 = Environment.getExternalStorageState();
      str1 = str3;
      File localFile = null;
      if (paramBoolean)
      {
        boolean bool1 = "mounted".equals(str1);
        localFile = null;
        if (bool1)
        {
          boolean bool2 = hasExternalStoragePermission(paramContext);
          localFile = null;
          if (bool2)
            localFile = getExternalCacheDir(paramContext);
        }
      }
      if (localFile == null)
        localFile = paramContext.getCacheDir();
      if (localFile == null)
      {
        String str2 = "/data/data/" + paramContext.getPackageName() + "/cache/";
        L.w("Can't define system cache directory! '%s' will be used.", new Object[] { str2 });
        localFile = new File(str2);
      }
      return localFile;
    }
    catch (NullPointerException localNullPointerException)
    {
      while (true)
        String str1 = "";
    }
  }

  private static File getExternalCacheDir(Context paramContext)
  {
    File localFile = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data"), paramContext.getPackageName()), "cache");
    if (!localFile.exists())
    {
      if (!localFile.mkdirs())
      {
        L.w("Unable to create external cache directory", new Object[0]);
        localFile = null;
      }
    }
    else
      return localFile;
    try
    {
      new File(localFile, ".nomedia").createNewFile();
      return localFile;
    }
    catch (IOException localIOException)
    {
      L.i("Can't create \".nomedia\" file in application external cache directory", new Object[0]);
    }
    return localFile;
  }

  public static File getIndividualCacheDirectory(Context paramContext)
  {
    File localFile1 = getCacheDirectory(paramContext);
    File localFile2 = new File(localFile1, "uil-images");
    if ((!localFile2.exists()) && (!localFile2.mkdir()))
      localFile2 = localFile1;
    return localFile2;
  }

  public static File getOwnCacheDirectory(Context paramContext, String paramString)
  {
    boolean bool1 = "mounted".equals(Environment.getExternalStorageState());
    File localFile = null;
    if (bool1)
    {
      boolean bool2 = hasExternalStoragePermission(paramContext);
      localFile = null;
      if (bool2)
        localFile = new File(Environment.getExternalStorageDirectory(), paramString);
    }
    if ((localFile == null) || ((!localFile.exists()) && (!localFile.mkdirs())))
      localFile = paramContext.getCacheDir();
    return localFile;
  }

  private static boolean hasExternalStoragePermission(Context paramContext)
  {
    return paramContext.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.utils.StorageUtils
 * JD-Core Version:    0.6.2
 */