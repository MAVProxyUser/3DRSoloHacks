package io.fabric.sdk.android.services.settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultSettingsController
  implements SettingsController
{
  private static final String LOAD_ERROR_MESSAGE = "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.";
  private static final String PREFS_BUILD_INSTANCE_IDENTIFIER = "existing_instance_identifier";
  private final CachedSettingsIo cachedSettingsIo;
  private final CurrentTimeProvider currentTimeProvider;
  private final Kit kit;
  private final PreferenceStore preferenceStore;
  private final SettingsJsonTransform settingsJsonTransform;
  private final SettingsRequest settingsRequest;
  private final SettingsSpiCall settingsSpiCall;

  public DefaultSettingsController(Kit paramKit, SettingsRequest paramSettingsRequest, CurrentTimeProvider paramCurrentTimeProvider, SettingsJsonTransform paramSettingsJsonTransform, CachedSettingsIo paramCachedSettingsIo, SettingsSpiCall paramSettingsSpiCall)
  {
    this.kit = paramKit;
    this.settingsRequest = paramSettingsRequest;
    this.currentTimeProvider = paramCurrentTimeProvider;
    this.settingsJsonTransform = paramSettingsJsonTransform;
    this.cachedSettingsIo = paramCachedSettingsIo;
    this.settingsSpiCall = paramSettingsSpiCall;
    this.preferenceStore = new PreferenceStoreImpl(this.kit);
  }

  private SettingsData getCachedSettingsData(SettingsCacheBehavior paramSettingsCacheBehavior)
  {
    Object localObject = null;
    try
    {
      boolean bool1 = SettingsCacheBehavior.SKIP_CACHE_LOOKUP.equals(paramSettingsCacheBehavior);
      localObject = null;
      if (bool1)
        break label177;
      JSONObject localJSONObject = this.cachedSettingsIo.readCachedSettings();
      localObject = null;
      if (localJSONObject == null)
        break label165;
      SettingsData localSettingsData = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, localJSONObject);
      if (localSettingsData != null)
      {
        logSettings(localJSONObject, "Loaded cached settings: ");
        long l = this.currentTimeProvider.getCurrentTimeMillis();
        boolean bool2 = SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION.equals(paramSettingsCacheBehavior);
        localObject = null;
        if ((bool2) || (!localSettingsData.isExpired(l)))
        {
          localObject = localSettingsData;
          Fabric.getLogger().d("Fabric", "Returning cached settings.");
          return localObject;
        }
        Fabric.getLogger().d("Fabric", "Cached settings have expired.");
        return null;
      }
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Failed to get cached settings", localException);
      return localObject;
    }
    Fabric.getLogger().e("Fabric", "Failed to transform cached settings data.", null);
    return null;
    label165: Fabric.getLogger().d("Fabric", "No cached settings data found.");
    label177: return null;
  }

  private void logSettings(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    if (!CommonUtils.isClsTrace(this.kit.getContext()))
      paramJSONObject = this.settingsJsonTransform.sanitizeTraceInfo(paramJSONObject);
    Fabric.getLogger().d("Fabric", paramString + paramJSONObject.toString());
  }

  boolean buildInstanceIdentifierChanged()
  {
    return !getStoredBuildInstanceIdentifier().equals(getBuildInstanceIdentifierFromContext());
  }

  String getBuildInstanceIdentifierFromContext()
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = CommonUtils.resolveBuildId(this.kit.getContext());
    return CommonUtils.createInstanceIdFrom(arrayOfString);
  }

  String getStoredBuildInstanceIdentifier()
  {
    return this.preferenceStore.get().getString("existing_instance_identifier", "");
  }

  public SettingsData loadSettingsData()
  {
    return loadSettingsData(SettingsCacheBehavior.USE_CACHE);
  }

  public SettingsData loadSettingsData(SettingsCacheBehavior paramSettingsCacheBehavior)
  {
    Object localObject = null;
    try
    {
      boolean bool1 = Fabric.isDebuggable();
      localObject = null;
      if (!bool1)
      {
        boolean bool2 = buildInstanceIdentifierChanged();
        localObject = null;
        if (!bool2)
          localObject = getCachedSettingsData(paramSettingsCacheBehavior);
      }
      if (localObject == null)
      {
        JSONObject localJSONObject = this.settingsSpiCall.invoke(this.settingsRequest);
        if (localJSONObject != null)
        {
          localObject = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, localJSONObject);
          this.cachedSettingsIo.writeCachedSettings(((SettingsData)localObject).expiresAtMillis, localJSONObject);
          logSettings(localJSONObject, "Loaded settings: ");
          setStoredBuildInstanceIdentifier(getBuildInstanceIdentifierFromContext());
        }
      }
      if (localObject == null)
      {
        SettingsData localSettingsData = getCachedSettingsData(SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION);
        localObject = localSettingsData;
      }
      return localObject;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", localException);
    }
    return localObject;
  }

  @SuppressLint({"CommitPrefEdits"})
  boolean setStoredBuildInstanceIdentifier(String paramString)
  {
    SharedPreferences.Editor localEditor = this.preferenceStore.edit();
    localEditor.putString("existing_instance_identifier", paramString);
    return this.preferenceStore.save(localEditor);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.DefaultSettingsController
 * JD-Core Version:    0.6.2
 */