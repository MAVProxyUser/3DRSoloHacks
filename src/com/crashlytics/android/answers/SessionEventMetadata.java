package com.crashlytics.android.answers;

public final class SessionEventMetadata
{
  public final String advertisingId;
  public final String androidId;
  public final String appBundleId;
  public final String appVersionCode;
  public final String appVersionName;
  public final String betaDeviceToken;
  public final String buildId;
  public final String deviceModel;
  public final String executionId;
  public final String installationId;
  public final String osVersion;

  public SessionEventMetadata(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11)
  {
    this.appBundleId = paramString1;
    this.executionId = paramString2;
    this.installationId = paramString3;
    this.androidId = paramString4;
    this.advertisingId = paramString5;
    this.betaDeviceToken = paramString6;
    this.buildId = paramString7;
    this.osVersion = paramString8;
    this.deviceModel = paramString9;
    this.appVersionCode = paramString10;
    this.appVersionName = paramString11;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.answers.SessionEventMetadata
 * JD-Core Version:    0.6.2
 */