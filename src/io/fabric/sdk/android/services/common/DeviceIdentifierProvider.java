package io.fabric.sdk.android.services.common;

import java.util.Map;

public abstract interface DeviceIdentifierProvider
{
  public abstract Map<IdManager.DeviceIdentifierType, String> getDeviceIdentifiers();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.DeviceIdentifierProvider
 * JD-Core Version:    0.6.2
 */