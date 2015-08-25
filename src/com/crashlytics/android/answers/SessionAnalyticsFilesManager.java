package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.events.EventsStorage;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.io.IOException;
import java.util.UUID;

class SessionAnalyticsFilesManager extends EventsFilesManager
{
  private static final String SESSION_ANALYTICS_TO_SEND_FILE_PREFIX = "sa";
  private AnalyticsSettingsData analyticsSettingsData;

  SessionAnalyticsFilesManager(Context paramContext, SessionEventTransform paramSessionEventTransform, CurrentTimeProvider paramCurrentTimeProvider, EventsStorage paramEventsStorage)
    throws IOException
  {
    this(paramContext, paramSessionEventTransform, paramCurrentTimeProvider, paramEventsStorage, 100);
  }

  SessionAnalyticsFilesManager(Context paramContext, SessionEventTransform paramSessionEventTransform, CurrentTimeProvider paramCurrentTimeProvider, EventsStorage paramEventsStorage, int paramInt)
    throws IOException
  {
    super(paramContext, paramSessionEventTransform, paramCurrentTimeProvider, paramEventsStorage, paramInt);
  }

  protected String generateUniqueRollOverFileName()
  {
    UUID localUUID = UUID.randomUUID();
    return "sa" + "_" + localUUID.toString() + "_" + this.currentTimeProvider.getCurrentTimeMillis() + ".tap";
  }

  protected int getMaxByteSizePerFile()
  {
    if (this.analyticsSettingsData == null)
      return 8000;
    return this.analyticsSettingsData.maxByteSizePerFile;
  }

  void setAnalyticsSettingsData(AnalyticsSettingsData paramAnalyticsSettingsData)
  {
    this.analyticsSettingsData = paramAnalyticsSettingsData;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.answers.SessionAnalyticsFilesManager
 * JD-Core Version:    0.6.2
 */