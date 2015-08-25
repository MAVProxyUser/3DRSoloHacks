package com.crashlytics.android.internal.models;

public class SessionEventData
{
  public final BinaryImageData[] binaryImages;
  public final CustomAttributeData[] customAttributes;
  public final SignalData signal;
  public final ThreadData[] threads;
  public final long timestamp;

  public SessionEventData(long paramLong, SignalData paramSignalData, ThreadData[] paramArrayOfThreadData, BinaryImageData[] paramArrayOfBinaryImageData, CustomAttributeData[] paramArrayOfCustomAttributeData)
  {
    this.timestamp = paramLong;
    this.signal = paramSignalData;
    this.threads = paramArrayOfThreadData;
    this.binaryImages = paramArrayOfBinaryImageData;
    this.customAttributes = paramArrayOfCustomAttributeData;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.internal.models.SessionEventData
 * JD-Core Version:    0.6.2
 */