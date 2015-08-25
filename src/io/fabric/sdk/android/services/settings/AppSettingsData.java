package io.fabric.sdk.android.services.settings;

public class AppSettingsData
{
  public static final String STATUS_ACTIVATED = "activated";
  public static final String STATUS_CONFIGURED = "configured";
  public static final String STATUS_NEW = "new";
  public final AppIconSettingsData icon;
  public final String identifier;
  public final String reportsUrl;
  public final String status;
  public final boolean updateRequired;
  public final String url;

  public AppSettingsData(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, AppIconSettingsData paramAppIconSettingsData)
  {
    this.identifier = paramString1;
    this.status = paramString2;
    this.url = paramString3;
    this.reportsUrl = paramString4;
    this.updateRequired = paramBoolean;
    this.icon = paramAppIconSettingsData;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.AppSettingsData
 * JD-Core Version:    0.6.2
 */