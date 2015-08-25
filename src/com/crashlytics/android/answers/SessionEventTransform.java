package com.crashlytics.android.answers;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import io.fabric.sdk.android.services.events.EventTransform;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class SessionEventTransform
  implements EventTransform<SessionEvent>
{
  static final String ADVERTISING_ID_KEY = "advertisingId";
  static final String ANDROID_ID_KEY = "androidId";
  static final String APP_BUNDLE_ID_KEY = "appBundleId";
  static final String APP_VERSION_CODE_KEY = "appVersionCode";
  static final String APP_VERSION_NAME_KEY = "appVersionName";
  static final String BETA_DEVICE_TOKEN_KEY = "betaDeviceToken";
  static final String BUILD_ID_KEY = "buildId";
  static final String DETAILS_KEY = "details";
  static final String DEVICE_MODEL_KEY = "deviceModel";
  static final String EXECUTION_ID_KEY = "executionId";
  static final String INSTALLATION_ID_KEY = "installationId";
  static final String OS_VERSION_KEY = "osVersion";
  static final String TIMESTAMP_KEY = "timestamp";
  static final String TYPE_KEY = "type";

  private JSONObject buildDetailsJsonFor(Map<String, String> paramMap)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localJSONObject.put((String)localEntry.getKey(), localEntry.getValue());
    }
    return localJSONObject;
  }

  @TargetApi(9)
  public JSONObject buildJsonForEvent(SessionEvent paramSessionEvent)
    throws IOException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      SessionEventMetadata localSessionEventMetadata = paramSessionEvent.sessionEventMetadata;
      localJSONObject.put("appBundleId", localSessionEventMetadata.appBundleId);
      localJSONObject.put("executionId", localSessionEventMetadata.executionId);
      localJSONObject.put("installationId", localSessionEventMetadata.installationId);
      localJSONObject.put("androidId", localSessionEventMetadata.androidId);
      localJSONObject.put("advertisingId", localSessionEventMetadata.advertisingId);
      localJSONObject.put("betaDeviceToken", localSessionEventMetadata.betaDeviceToken);
      localJSONObject.put("buildId", localSessionEventMetadata.buildId);
      localJSONObject.put("osVersion", localSessionEventMetadata.osVersion);
      localJSONObject.put("deviceModel", localSessionEventMetadata.deviceModel);
      localJSONObject.put("appVersionCode", localSessionEventMetadata.appVersionCode);
      localJSONObject.put("appVersionName", localSessionEventMetadata.appVersionName);
      localJSONObject.put("timestamp", paramSessionEvent.timestamp);
      localJSONObject.put("type", paramSessionEvent.type.toString());
      localJSONObject.put("details", buildDetailsJsonFor(paramSessionEvent.details));
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      if (Build.VERSION.SDK_INT >= 9)
        throw new IOException(localJSONException.getMessage(), localJSONException);
      throw new IOException(localJSONException.getMessage());
    }
  }

  public SessionEvent fromBytes(byte[] paramArrayOfByte)
    throws IOException
  {
    return null;
  }

  public byte[] toBytes(SessionEvent paramSessionEvent)
    throws IOException
  {
    return buildJsonForEvent(paramSessionEvent).toString().getBytes("UTF-8");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.answers.SessionEventTransform
 * JD-Core Version:    0.6.2
 */