package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.events.EventsStrategy;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;

abstract interface SessionAnalyticsManagerStrategy<T> extends EventsStrategy<T>
{
  public abstract void setAnalyticsSettingsData(AnalyticsSettingsData paramAnalyticsSettingsData, String paramString);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.answers.SessionAnalyticsManagerStrategy
 * JD-Core Version:    0.6.2
 */