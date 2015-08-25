package io.fabric.sdk.android.services.common;

class AdvertisingInfo
{
  public final String advertisingId;
  public final boolean limitAdTrackingEnabled;

  AdvertisingInfo(String paramString, boolean paramBoolean)
  {
    this.advertisingId = paramString;
    this.limitAdTrackingEnabled = paramBoolean;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    AdvertisingInfo localAdvertisingInfo;
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass()))
        return false;
      localAdvertisingInfo = (AdvertisingInfo)paramObject;
      if (this.limitAdTrackingEnabled != localAdvertisingInfo.limitAdTrackingEnabled)
        return false;
      if (this.advertisingId == null)
        break;
    }
    while (this.advertisingId.equals(localAdvertisingInfo.advertisingId));
    while (true)
    {
      return false;
      if (localAdvertisingInfo.advertisingId == null)
        break;
    }
  }

  public int hashCode()
  {
    if (this.advertisingId != null);
    for (int i = this.advertisingId.hashCode(); ; i = 0)
    {
      int j = i * 31;
      boolean bool = this.limitAdTrackingEnabled;
      int k = 0;
      if (bool)
        k = 1;
      return j + k;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.AdvertisingInfo
 * JD-Core Version:    0.6.2
 */