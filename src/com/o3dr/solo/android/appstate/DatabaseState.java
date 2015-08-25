package com.o3dr.solo.android.appstate;

import android.content.Context;
import android.text.TextUtils;
import com.o3dr.solo.android.util.maps.mapbox.offline.OfflineDatabaseHandler;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseState
{
  private static final ConcurrentHashMap<String, OfflineDatabaseHandler> databaseHandlers = new ConcurrentHashMap();

  public static void deleteDatabase(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
      return;
    String str = paramString.toLowerCase(Locale.US);
    OfflineDatabaseHandler localOfflineDatabaseHandler = (OfflineDatabaseHandler)databaseHandlers.remove(str);
    if (localOfflineDatabaseHandler != null)
      localOfflineDatabaseHandler.close();
    paramContext.deleteDatabase(str);
  }

  public static OfflineDatabaseHandler getOfflineDatabaseHandlerForMapId(Context paramContext, String paramString)
  {
    String str = paramString.toLowerCase(Locale.US);
    if (databaseHandlers.containsKey(str))
      return (OfflineDatabaseHandler)databaseHandlers.get(paramString);
    OfflineDatabaseHandler localOfflineDatabaseHandler = new OfflineDatabaseHandler(paramContext, str);
    databaseHandlers.put(str, localOfflineDatabaseHandler);
    return localOfflineDatabaseHandler;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.appstate.DatabaseState
 * JD-Core Version:    0.6.2
 */