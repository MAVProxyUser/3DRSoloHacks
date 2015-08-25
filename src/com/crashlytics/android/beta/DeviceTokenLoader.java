package com.crashlytics.android.beta;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.cache.ValueLoader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeviceTokenLoader
  implements ValueLoader<String>
{
  private static final String DIRFACTOR_DEVICE_TOKEN_PREFIX = "assets/com.crashlytics.android.beta/dirfactor-device-token=";

  String determineDeviceToken(ZipInputStream paramZipInputStream)
    throws IOException
  {
    String str;
    do
    {
      ZipEntry localZipEntry = paramZipInputStream.getNextEntry();
      if (localZipEntry == null)
        break;
      str = localZipEntry.getName();
    }
    while (!str.startsWith("assets/com.crashlytics.android.beta/dirfactor-device-token="));
    return str.substring("assets/com.crashlytics.android.beta/dirfactor-device-token=".length(), -1 + str.length());
    return "";
  }

  ZipInputStream getZipInputStreamOfAppApkFrom(Context paramContext)
    throws PackageManager.NameNotFoundException, FileNotFoundException
  {
    return new ZipInputStream(new FileInputStream(paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 0).sourceDir));
  }

  public String load(Context paramContext)
    throws Exception
  {
    long l = System.nanoTime();
    Object localObject1 = "";
    ZipInputStream localZipInputStream = null;
    try
    {
      localZipInputStream = getZipInputStreamOfAppApkFrom(paramContext);
      String str = determineDeviceToken(localZipInputStream);
      localObject1 = str;
      if (localZipInputStream != null);
      try
      {
        localZipInputStream.close();
        double d = (System.nanoTime() - l) / 1000000.0D;
        Fabric.getLogger().d("Beta", "Beta device token load took " + d + "ms");
        return localObject1;
      }
      catch (IOException localIOException6)
      {
        while (true)
          Fabric.getLogger().e("Beta", "Failed to close the APK file", localIOException6);
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
      {
        Fabric.getLogger().e("Beta", "Failed to find this app in the PackageManager", localNameNotFoundException);
        if (localZipInputStream != null)
          try
          {
            localZipInputStream.close();
          }
          catch (IOException localIOException5)
          {
            Fabric.getLogger().e("Beta", "Failed to close the APK file", localIOException5);
          }
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
      {
        Fabric.getLogger().e("Beta", "Failed to find the APK file", localFileNotFoundException);
        if (localZipInputStream != null)
          try
          {
            localZipInputStream.close();
          }
          catch (IOException localIOException4)
          {
            Fabric.getLogger().e("Beta", "Failed to close the APK file", localIOException4);
          }
      }
    }
    catch (IOException localIOException2)
    {
      while (true)
      {
        Fabric.getLogger().e("Beta", "Failed to read the APK file", localIOException2);
        if (localZipInputStream != null)
          try
          {
            localZipInputStream.close();
          }
          catch (IOException localIOException3)
          {
            Fabric.getLogger().e("Beta", "Failed to close the APK file", localIOException3);
          }
      }
    }
    finally
    {
      if (localZipInputStream == null);
    }
    try
    {
      localZipInputStream.close();
      throw localObject2;
    }
    catch (IOException localIOException1)
    {
      while (true)
        Fabric.getLogger().e("Beta", "Failed to close the APK file", localIOException1);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.beta.DeviceTokenLoader
 * JD-Core Version:    0.6.2
 */