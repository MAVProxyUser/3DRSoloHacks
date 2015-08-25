package com.crashlytics.android;

import java.io.InputStream;

public abstract interface PinningInfoProvider
{
  public abstract String getKeyStorePassword();

  public abstract InputStream getKeyStoreStream();

  public abstract String[] getPins();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.PinningInfoProvider
 * JD-Core Version:    0.6.2
 */