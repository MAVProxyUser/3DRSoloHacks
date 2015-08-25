package io.fabric.sdk.android.services.settings;

public class SessionSettingsData
{
  public final int identifierMask;
  public final int logBufferSize;
  public final int maxChainedExceptionDepth;
  public final int maxCustomExceptionEvents;
  public final int maxCustomKeyValuePairs;
  public final boolean sendSessionWithoutCrash;

  public SessionSettingsData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    this.logBufferSize = paramInt1;
    this.maxChainedExceptionDepth = paramInt2;
    this.maxCustomExceptionEvents = paramInt3;
    this.maxCustomKeyValuePairs = paramInt4;
    this.identifierMask = paramInt5;
    this.sendSessionWithoutCrash = paramBoolean;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.SessionSettingsData
 * JD-Core Version:    0.6.2
 */