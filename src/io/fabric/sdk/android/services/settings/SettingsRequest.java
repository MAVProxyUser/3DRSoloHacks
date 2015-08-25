package io.fabric.sdk.android.services.settings;

public class SettingsRequest
{
  public final String apiKey;
  public final String buildVersion;
  public final String deviceId;
  public final String displayVersion;
  public final String iconHash;
  public final String instanceId;
  public final int source;

  public SettingsRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, String paramString6)
  {
    this.apiKey = paramString1;
    this.deviceId = paramString2;
    this.instanceId = paramString3;
    this.displayVersion = paramString4;
    this.buildVersion = paramString5;
    this.source = paramInt;
    this.iconHash = paramString6;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.SettingsRequest
 * JD-Core Version:    0.6.2
 */