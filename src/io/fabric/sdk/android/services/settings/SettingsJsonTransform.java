package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import org.json.JSONException;
import org.json.JSONObject;

public abstract interface SettingsJsonTransform
{
  public abstract SettingsData buildFromJson(CurrentTimeProvider paramCurrentTimeProvider, JSONObject paramJSONObject)
    throws JSONException;

  public abstract JSONObject sanitizeTraceInfo(JSONObject paramJSONObject)
    throws JSONException;

  public abstract JSONObject toJson(SettingsData paramSettingsData)
    throws JSONException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.SettingsJsonTransform
 * JD-Core Version:    0.6.2
 */