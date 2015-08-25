package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultSettingsJsonTransform
  implements SettingsJsonTransform
{
  private AnalyticsSettingsData buildAnalyticsSessionDataFrom(JSONObject paramJSONObject)
  {
    return new AnalyticsSettingsData(paramJSONObject.optString("url", "https://e.crashlytics.com/spi/v2/events"), paramJSONObject.optInt("flush_interval_secs", 600), paramJSONObject.optInt("max_byte_size_per_file", 8000), paramJSONObject.optInt("max_file_count_per_send", 1), paramJSONObject.optInt("max_pending_send_file_count", 100));
  }

  private AppSettingsData buildAppDataFrom(JSONObject paramJSONObject)
    throws JSONException
  {
    String str1 = paramJSONObject.getString("identifier");
    String str2 = paramJSONObject.getString("status");
    String str3 = paramJSONObject.getString("url");
    String str4 = paramJSONObject.getString("reports_url");
    boolean bool1 = paramJSONObject.optBoolean("update_required", false);
    boolean bool2 = paramJSONObject.has("icon");
    AppIconSettingsData localAppIconSettingsData = null;
    if (bool2)
    {
      boolean bool3 = paramJSONObject.getJSONObject("icon").has("hash");
      localAppIconSettingsData = null;
      if (bool3)
        localAppIconSettingsData = buildIconDataFrom(paramJSONObject.getJSONObject("icon"));
    }
    return new AppSettingsData(str1, str2, str3, str4, bool1, localAppIconSettingsData);
  }

  private BetaSettingsData buildBetaSettingsDataFrom(JSONObject paramJSONObject)
    throws JSONException
  {
    return new BetaSettingsData(paramJSONObject.optString("update_endpoint", SettingsJsonConstants.BETA_UPDATE_ENDPOINT_DEFAULT), paramJSONObject.optInt("update_suspend_duration", 3600));
  }

  private FeaturesSettingsData buildFeaturesSessionDataFrom(JSONObject paramJSONObject)
  {
    return new FeaturesSettingsData(paramJSONObject.optBoolean("prompt_enabled", false), paramJSONObject.optBoolean("collect_logged_exceptions", true), paramJSONObject.optBoolean("collect_reports", true), paramJSONObject.optBoolean("collect_analytics", false));
  }

  private AppIconSettingsData buildIconDataFrom(JSONObject paramJSONObject)
    throws JSONException
  {
    return new AppIconSettingsData(paramJSONObject.getString("hash"), paramJSONObject.getInt("width"), paramJSONObject.getInt("height"));
  }

  private PromptSettingsData buildPromptDataFrom(JSONObject paramJSONObject)
    throws JSONException
  {
    return new PromptSettingsData(paramJSONObject.optString("title", "Send Crash Report?"), paramJSONObject.optString("message", "Looks like we crashed! Please help us fix the problem by sending a crash report."), paramJSONObject.optString("send_button_title", "Send"), paramJSONObject.optBoolean("show_cancel_button", true), paramJSONObject.optString("cancel_button_title", "Don't Send"), paramJSONObject.optBoolean("show_always_send_button", true), paramJSONObject.optString("always_send_button_title", "Always Send"));
  }

  private SessionSettingsData buildSessionDataFrom(JSONObject paramJSONObject)
    throws JSONException
  {
    return new SessionSettingsData(paramJSONObject.optInt("log_buffer_size", 64000), paramJSONObject.optInt("max_chained_exception_depth", 8), paramJSONObject.optInt("max_custom_exception_events", 64), paramJSONObject.optInt("max_custom_key_value_pairs", 64), paramJSONObject.optInt("identifier_mask", 255), paramJSONObject.optBoolean("send_session_without_crash", false));
  }

  private long getExpiresAtFrom(CurrentTimeProvider paramCurrentTimeProvider, long paramLong, JSONObject paramJSONObject)
    throws JSONException
  {
    if (paramJSONObject.has("expires_at"))
      return paramJSONObject.getLong("expires_at");
    return paramCurrentTimeProvider.getCurrentTimeMillis() + 1000L * paramLong;
  }

  private JSONObject toAnalyticsJson(AnalyticsSettingsData paramAnalyticsSettingsData)
    throws JSONException
  {
    return new JSONObject().put("url", paramAnalyticsSettingsData.analyticsURL).put("flush_interval_secs", paramAnalyticsSettingsData.flushIntervalSeconds).put("max_byte_size_per_file", paramAnalyticsSettingsData.maxByteSizePerFile).put("max_file_count_per_send", paramAnalyticsSettingsData.maxFileCountPerSend).put("max_pending_send_file_count", paramAnalyticsSettingsData.maxPendingSendFileCount);
  }

  private JSONObject toAppJson(AppSettingsData paramAppSettingsData)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject().put("identifier", paramAppSettingsData.identifier).put("status", paramAppSettingsData.status).put("url", paramAppSettingsData.url).put("reports_url", paramAppSettingsData.reportsUrl).put("update_required", paramAppSettingsData.updateRequired);
    if (paramAppSettingsData.icon != null)
      localJSONObject.put("icon", toIconJson(paramAppSettingsData.icon));
    return localJSONObject;
  }

  private JSONObject toBetaJson(BetaSettingsData paramBetaSettingsData)
    throws JSONException
  {
    return new JSONObject().put("update_endpoint", paramBetaSettingsData.updateUrl).put("update_suspend_duration", paramBetaSettingsData.updateSuspendDurationSeconds);
  }

  private JSONObject toFeaturesJson(FeaturesSettingsData paramFeaturesSettingsData)
    throws JSONException
  {
    return new JSONObject().put("collect_logged_exceptions", paramFeaturesSettingsData.collectLoggedException).put("collect_reports", paramFeaturesSettingsData.collectReports).put("collect_analytics", paramFeaturesSettingsData.collectAnalytics);
  }

  private JSONObject toIconJson(AppIconSettingsData paramAppIconSettingsData)
    throws JSONException
  {
    return new JSONObject().put("hash", paramAppIconSettingsData.hash).put("width", paramAppIconSettingsData.width).put("height", paramAppIconSettingsData.height);
  }

  private JSONObject toPromptJson(PromptSettingsData paramPromptSettingsData)
    throws JSONException
  {
    return new JSONObject().put("title", paramPromptSettingsData.title).put("message", paramPromptSettingsData.message).put("send_button_title", paramPromptSettingsData.sendButtonTitle).put("show_cancel_button", paramPromptSettingsData.showCancelButton).put("cancel_button_title", paramPromptSettingsData.cancelButtonTitle).put("show_always_send_button", paramPromptSettingsData.showAlwaysSendButton).put("always_send_button_title", paramPromptSettingsData.alwaysSendButtonTitle);
  }

  private JSONObject toSessionJson(SessionSettingsData paramSessionSettingsData)
    throws JSONException
  {
    return new JSONObject().put("log_buffer_size", paramSessionSettingsData.logBufferSize).put("max_chained_exception_depth", paramSessionSettingsData.maxChainedExceptionDepth).put("max_custom_exception_events", paramSessionSettingsData.maxCustomExceptionEvents).put("max_custom_key_value_pairs", paramSessionSettingsData.maxCustomKeyValuePairs).put("identifier_mask", paramSessionSettingsData.identifierMask).put("send_session_without_crash", paramSessionSettingsData.sendSessionWithoutCrash);
  }

  public SettingsData buildFromJson(CurrentTimeProvider paramCurrentTimeProvider, JSONObject paramJSONObject)
    throws JSONException
  {
    int i = paramJSONObject.optInt("settings_version", 0);
    int j = paramJSONObject.optInt("cache_duration", 3600);
    AppSettingsData localAppSettingsData = buildAppDataFrom(paramJSONObject.getJSONObject("app"));
    SessionSettingsData localSessionSettingsData = buildSessionDataFrom(paramJSONObject.getJSONObject("session"));
    PromptSettingsData localPromptSettingsData = buildPromptDataFrom(paramJSONObject.getJSONObject("prompt"));
    FeaturesSettingsData localFeaturesSettingsData = buildFeaturesSessionDataFrom(paramJSONObject.getJSONObject("features"));
    AnalyticsSettingsData localAnalyticsSettingsData = buildAnalyticsSessionDataFrom(paramJSONObject.getJSONObject("analytics"));
    BetaSettingsData localBetaSettingsData = buildBetaSettingsDataFrom(paramJSONObject.getJSONObject("beta"));
    return new SettingsData(getExpiresAtFrom(paramCurrentTimeProvider, j, paramJSONObject), localAppSettingsData, localSessionSettingsData, localPromptSettingsData, localFeaturesSettingsData, localAnalyticsSettingsData, localBetaSettingsData, i, j);
  }

  public JSONObject sanitizeTraceInfo(JSONObject paramJSONObject)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject(paramJSONObject.toString());
    localJSONObject.getJSONObject("features").remove("collect_analytics");
    localJSONObject.remove("analytics");
    return localJSONObject;
  }

  public JSONObject toJson(SettingsData paramSettingsData)
    throws JSONException
  {
    return new JSONObject().put("expires_at", paramSettingsData.expiresAtMillis).put("cache_duration", paramSettingsData.cacheDuration).put("settings_version", paramSettingsData.settingsVersion).put("features", toFeaturesJson(paramSettingsData.featuresData)).put("analytics", toAnalyticsJson(paramSettingsData.analyticsSettingsData)).put("beta", toBetaJson(paramSettingsData.betaSettingsData)).put("app", toAppJson(paramSettingsData.appData)).put("session", toSessionJson(paramSettingsData.sessionData)).put("prompt", toPromptJson(paramSettingsData.promptData));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.DefaultSettingsJsonTransform
 * JD-Core Version:    0.6.2
 */