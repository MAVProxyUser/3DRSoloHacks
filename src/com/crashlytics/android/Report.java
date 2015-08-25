package com.crashlytics.android;

import java.io.File;
import java.util.Map;

abstract interface Report
{
  public abstract Map<String, String> getCustomHeaders();

  public abstract File getFile();

  public abstract String getFileName();

  public abstract String getIdentifier();

  public abstract boolean remove();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.Report
 * JD-Core Version:    0.6.2
 */