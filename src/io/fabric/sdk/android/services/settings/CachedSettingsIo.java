package io.fabric.sdk.android.services.settings;

import org.json.JSONObject;

public abstract interface CachedSettingsIo
{
  public abstract JSONObject readCachedSettings();

  public abstract void writeCachedSettings(long paramLong, JSONObject paramJSONObject);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.CachedSettingsIo
 * JD-Core Version:    0.6.2
 */