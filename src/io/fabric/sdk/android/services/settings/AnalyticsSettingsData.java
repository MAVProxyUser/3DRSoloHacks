package io.fabric.sdk.android.services.settings;

public class AnalyticsSettingsData
{
  public final String analyticsURL;
  public final int flushIntervalSeconds;
  public final int maxByteSizePerFile;
  public final int maxFileCountPerSend;
  public final int maxPendingSendFileCount;

  public AnalyticsSettingsData(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.analyticsURL = paramString;
    this.flushIntervalSeconds = paramInt1;
    this.maxByteSizePerFile = paramInt2;
    this.maxFileCountPerSend = paramInt3;
    this.maxPendingSendFileCount = paramInt4;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.AnalyticsSettingsData
 * JD-Core Version:    0.6.2
 */