package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;

public class ApiKey
{
  static final String CRASHLYTICS_API_KEY = "com.crashlytics.ApiKey";

  private static String buildApiKeyInstructions()
  {
    return "Crashlytics could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"com.crashlytics.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
  }

  public static String getApiKey(Context paramContext)
  {
    return getApiKey(paramContext, false);
  }

  public static String getApiKey(Context paramContext, boolean paramBoolean)
  {
    Object localObject;
    try
    {
      Context localContext = paramContext.getApplicationContext();
      String str1 = localContext.getPackageName();
      Bundle localBundle = localContext.getPackageManager().getApplicationInfo(str1, 128).metaData;
      localObject = null;
      if (localBundle != null)
      {
        String str2 = localBundle.getString("com.crashlytics.ApiKey");
        localObject = str2;
      }
      if (CommonUtils.isNullOrEmpty((String)localObject))
      {
        int i = CommonUtils.getResourcesIdentifier(paramContext, "com.crashlytics.ApiKey", "string");
        if (i != 0)
          localObject = paramContext.getResources().getString(i);
      }
      if (CommonUtils.isNullOrEmpty((String)localObject))
        if ((paramBoolean) || (CommonUtils.isAppDebuggable(paramContext)))
          throw new IllegalArgumentException(buildApiKeyInstructions());
    }
    catch (Exception localException)
    {
      while (true)
      {
        Fabric.getLogger().d("Fabric", "Caught non-fatal exception while retrieving apiKey: " + localException);
        localObject = null;
      }
      Fabric.getLogger().e("Fabric", buildApiKeyInstructions(), null);
    }
    return localObject;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.ApiKey
 * JD-Core Version:    0.6.2
 */