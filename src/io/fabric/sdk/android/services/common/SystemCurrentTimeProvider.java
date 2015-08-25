package io.fabric.sdk.android.services.common;

public class SystemCurrentTimeProvider
  implements CurrentTimeProvider
{
  public long getCurrentTimeMillis()
  {
    return System.currentTimeMillis();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.SystemCurrentTimeProvider
 * JD-Core Version:    0.6.2
 */