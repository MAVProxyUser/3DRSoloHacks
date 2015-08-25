package com.crashlytics.android.internal;

import com.crashlytics.android.internal.models.SessionEventData;

public abstract interface CrashEventDataProvider
{
  public abstract SessionEventData getCrashEventData();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.internal.CrashEventDataProvider
 * JD-Core Version:    0.6.2
 */