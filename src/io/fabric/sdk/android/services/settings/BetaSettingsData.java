package io.fabric.sdk.android.services.settings;

public class BetaSettingsData
{
  public final int updateSuspendDurationSeconds;
  public final String updateUrl;

  public BetaSettingsData(String paramString, int paramInt)
  {
    this.updateUrl = paramString;
    this.updateSuspendDurationSeconds = paramInt;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.BetaSettingsData
 * JD-Core Version:    0.6.2
 */