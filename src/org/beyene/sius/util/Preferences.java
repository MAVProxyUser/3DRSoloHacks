package org.beyene.sius.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Preferences
{
  private static Properties p = new Properties();

  static
  {
    try
    {
      readConfiguration(Preferences.class.getClassLoader().getResourceAsStream("sius.config"));
      return;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }

  private static String _load(String paramString)
  {
    return p.getProperty(paramString);
  }

  public static int getInt(String paramString, int paramInt)
  {
    String str = _load(paramString);
    if (str != null)
      try
      {
        int i = Integer.parseInt(str);
        return i;
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    return paramInt;
  }

  public static void readConfiguration(InputStream paramInputStream)
    throws IOException
  {
    p.load(paramInputStream);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.util.Preferences
 * JD-Core Version:    0.6.2
 */