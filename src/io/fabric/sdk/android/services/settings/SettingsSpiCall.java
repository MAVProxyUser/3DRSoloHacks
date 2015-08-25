package io.fabric.sdk.android.services.settings;

import org.json.JSONObject;

public abstract interface SettingsSpiCall
{
  public abstract JSONObject invoke(SettingsRequest paramSettingsRequest);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.SettingsSpiCall
 * JD-Core Version:    0.6.2
 */