package io.fabric.sdk.android.services.events;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPQueueFileEventStorage extends QueueFileEventStorage
{
  public GZIPQueueFileEventStorage(Context paramContext, File paramFile, String paramString1, String paramString2)
    throws IOException
  {
    super(paramContext, paramFile, paramString1, paramString2);
  }

  public OutputStream getMoveOutputStream(File paramFile)
    throws IOException
  {
    return new GZIPOutputStream(new FileOutputStream(paramFile));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.GZIPQueueFileEventStorage
 * JD-Core Version:    0.6.2
 */