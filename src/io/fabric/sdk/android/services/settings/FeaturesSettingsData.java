package io.fabric.sdk.android.services.settings;

public class FeaturesSettingsData
{
  public final boolean collectAnalytics;
  public final boolean collectLoggedException;
  public final boolean collectReports;
  public final boolean promptEnabled;

  public FeaturesSettingsData(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.promptEnabled = paramBoolean1;
    this.collectLoggedException = paramBoolean2;
    this.collectReports = paramBoolean3;
    this.collectAnalytics = paramBoolean4;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.FeaturesSettingsData
 * JD-Core Version:    0.6.2
 */