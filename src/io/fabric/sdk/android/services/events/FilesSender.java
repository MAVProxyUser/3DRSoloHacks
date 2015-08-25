package io.fabric.sdk.android.services.events;

import java.io.File;
import java.util.List;

public abstract interface FilesSender
{
  public abstract boolean send(List<File> paramList);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.FilesSender
 * JD-Core Version:    0.6.2
 */