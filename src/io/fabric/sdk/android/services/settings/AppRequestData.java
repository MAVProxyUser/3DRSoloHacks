package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Kit;
import java.util.Collection;

public class AppRequestData
{
  public final String apiKey;
  public final String appId;
  public final String buildVersion;
  public final String builtSdkVersion;
  public final String displayVersion;
  public final IconRequest icon;
  public final String instanceIdentifier;
  public final String minSdkVersion;
  public final String name;
  public final Collection<Kit> sdkKits;
  public final int source;

  public AppRequestData(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt, String paramString7, String paramString8, IconRequest paramIconRequest, Collection<Kit> paramCollection)
  {
    this.apiKey = paramString1;
    this.appId = paramString2;
    this.displayVersion = paramString3;
    this.buildVersion = paramString4;
    this.instanceIdentifier = paramString5;
    this.name = paramString6;
    this.source = paramInt;
    this.minSdkVersion = paramString7;
    this.builtSdkVersion = paramString8;
    this.icon = paramIconRequest;
    this.sdkKits = paramCollection;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.AppRequestData
 * JD-Core Version:    0.6.2
 */