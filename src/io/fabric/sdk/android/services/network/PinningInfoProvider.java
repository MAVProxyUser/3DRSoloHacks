package io.fabric.sdk.android.services.network;

import java.io.InputStream;

public abstract interface PinningInfoProvider
{
  public static final long PIN_CREATION_TIME_UNDEFINED = -1L;

  public abstract String getKeyStorePassword();

  public abstract InputStream getKeyStoreStream();

  public abstract long getPinCreationTimeInMillis();

  public abstract String[] getPins();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.network.PinningInfoProvider
 * JD-Core Version:    0.6.2
 */