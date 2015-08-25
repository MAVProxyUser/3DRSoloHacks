package com.o3dr.solo.android.util;

import android.content.Context;
import android.text.TextUtils;
import java.util.UUID;

public class IdUtils
{
  public static String getID(Context paramContext)
  {
    AppPreferences localAppPreferences = new AppPreferences(paramContext);
    String str = localAppPreferences.getPrefUuid();
    if (TextUtils.isEmpty(str))
      str = writeInstallationID(localAppPreferences);
    return str;
  }

  private static String writeInstallationID(AppPreferences paramAppPreferences)
  {
    String str = UUID.randomUUID().toString();
    paramAppPreferences.setPrefUuid(str);
    return str;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.IdUtils
 * JD-Core Version:    0.6.2
 */