package io.fabric.sdk.android.services.settings;

public abstract interface SettingsController
{
  public abstract SettingsData loadSettingsData();

  public abstract SettingsData loadSettingsData(SettingsCacheBehavior paramSettingsCacheBehavior);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.SettingsController
 * JD-Core Version:    0.6.2
 */