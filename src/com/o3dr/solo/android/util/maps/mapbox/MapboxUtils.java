package com.o3dr.solo.android.util.maps.mapbox;

import android.text.TextUtils;
import java.util.Locale;

public class MapboxUtils
{
  public static final String MAPBOX_BASE_URL_V4 = "https://a.tiles.mapbox.com/v4/";
  public static final int TILE_HEIGHT = 512;
  public static final int TILE_WIDTH = 512;

  public static String getMapTileURL(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = paramString1;
    arrayOfObject[1] = Integer.valueOf(paramInt1);
    arrayOfObject[2] = Integer.valueOf(paramInt2);
    arrayOfObject[3] = Integer.valueOf(paramInt3);
    arrayOfObject[4] = "@2x";
    arrayOfObject[5] = "png";
    arrayOfObject[6] = paramString2;
    return String.format(localLocale, "https://a.tiles.mapbox.com/v4/%s/%d/%d/%d%s.%s?access_token=%s", arrayOfObject);
  }

  public static String getUserAgent()
  {
    return "Mapbox Android SDK/0.7.3";
  }

  public static String markerIconURL(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer("pin-");
    String str = paramString2.toLowerCase(Locale.US);
    if (str.charAt(0) == 'l')
    {
      localStringBuffer.append("l");
      if (TextUtils.isEmpty(paramString3))
        break label153;
      localStringBuffer.append(String.format("-%s+", new Object[] { paramString3 }));
    }
    while (true)
    {
      localStringBuffer.append(paramString4.replaceAll("#", ""));
      localStringBuffer.append(".png");
      localStringBuffer.append("?access_token=");
      localStringBuffer.append(paramString1);
      return String.format(Locale.US, "https://a.tiles.mapbox.com/v4/marker/%s", new Object[] { localStringBuffer });
      if (str.charAt(0) == 's')
      {
        localStringBuffer.append("s");
        break;
      }
      localStringBuffer.append("m");
      break;
      label153: localStringBuffer.append("+");
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.maps.mapbox.MapboxUtils
 * JD-Core Version:    0.6.2
 */