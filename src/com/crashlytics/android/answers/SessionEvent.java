package com.crashlytics.android.answers;

import android.app.Activity;
import java.util.Collections;
import java.util.Map;

final class SessionEvent
{
  public final Map<String, String> details;
  public final SessionEventMetadata sessionEventMetadata;
  private String stringRepresentation;
  public final long timestamp;
  public final Type type;

  private SessionEvent(SessionEventMetadata paramSessionEventMetadata, long paramLong, Type paramType, Map<String, String> paramMap)
  {
    this.sessionEventMetadata = paramSessionEventMetadata;
    this.timestamp = paramLong;
    this.type = paramType;
    this.details = paramMap;
  }

  public static SessionEvent buildActivityLifecycleEvent(SessionEventMetadata paramSessionEventMetadata, Type paramType, Activity paramActivity)
  {
    return buildEvent(paramSessionEventMetadata, paramType, Collections.singletonMap("activity", paramActivity.getClass().getName()));
  }

  public static SessionEvent buildCrashEvent(SessionEventMetadata paramSessionEventMetadata, String paramString)
  {
    Map localMap = Collections.singletonMap("sessionId", paramString);
    return buildEvent(paramSessionEventMetadata, Type.CRASH, localMap);
  }

  public static SessionEvent buildErrorEvent(SessionEventMetadata paramSessionEventMetadata, String paramString)
  {
    Map localMap = Collections.singletonMap("sessionId", paramString);
    return buildEvent(paramSessionEventMetadata, Type.ERROR, localMap);
  }

  public static SessionEvent buildEvent(SessionEventMetadata paramSessionEventMetadata, Type paramType, Map<String, String> paramMap)
  {
    return new SessionEvent(paramSessionEventMetadata, System.currentTimeMillis(), paramType, paramMap);
  }

  public String toString()
  {
    if (this.stringRepresentation == null)
      this.stringRepresentation = ("[" + getClass().getSimpleName() + ": appBundleId=" + this.sessionEventMetadata.appBundleId + ", executionId=" + this.sessionEventMetadata.executionId + ", installationId=" + this.sessionEventMetadata.installationId + ", androidId=" + this.sessionEventMetadata.androidId + ", advertisingId=" + this.sessionEventMetadata.advertisingId + ", betaDeviceToken=" + this.sessionEventMetadata.betaDeviceToken + ", buildId=" + this.sessionEventMetadata.buildId + ", osVersion=" + this.sessionEventMetadata.osVersion + ", deviceModel=" + this.sessionEventMetadata.deviceModel + ", appVersionCode=" + this.sessionEventMetadata.appVersionCode + ", appVersionName=" + this.sessionEventMetadata.appVersionName + ", timestamp=" + this.timestamp + ", type=" + this.type + ", details=" + this.details.toString() + "]");
    return this.stringRepresentation;
  }

  static enum Type
  {
    static
    {
      RESUME = new Type("RESUME", 2);
      SAVE_INSTANCE_STATE = new Type("SAVE_INSTANCE_STATE", 3);
      PAUSE = new Type("PAUSE", 4);
      STOP = new Type("STOP", 5);
      DESTROY = new Type("DESTROY", 6);
      ERROR = new Type("ERROR", 7);
      CRASH = new Type("CRASH", 8);
      INSTALL = new Type("INSTALL", 9);
      Type[] arrayOfType = new Type[10];
      arrayOfType[0] = CREATE;
      arrayOfType[1] = START;
      arrayOfType[2] = RESUME;
      arrayOfType[3] = SAVE_INSTANCE_STATE;
      arrayOfType[4] = PAUSE;
      arrayOfType[5] = STOP;
      arrayOfType[6] = DESTROY;
      arrayOfType[7] = ERROR;
      arrayOfType[8] = CRASH;
      arrayOfType[9] = INSTALL;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.answers.SessionEvent
 * JD-Core Version:    0.6.2
 */