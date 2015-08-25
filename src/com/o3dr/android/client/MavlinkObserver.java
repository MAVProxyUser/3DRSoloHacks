package com.o3dr.android.client;

import com.o3dr.services.android.lib.mavlink.MavlinkMessageWrapper;
import com.o3dr.services.android.lib.model.IMavlinkObserver.Stub;

public abstract class MavlinkObserver extends IMavlinkObserver.Stub
{
  public abstract void onMavlinkMessageReceived(MavlinkMessageWrapper paramMavlinkMessageWrapper);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.MavlinkObserver
 * JD-Core Version:    0.6.2
 */