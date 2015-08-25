package io.fabric.sdk.android.services.persistence;

import java.io.File;

public abstract interface FileStore
{
  public abstract File getCacheDir();

  public abstract File getExternalCacheDir();

  public abstract File getExternalFilesDir();

  public abstract File getFilesDir();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.persistence.FileStore
 * JD-Core Version:    0.6.2
 */