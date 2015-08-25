package com.o3dr.solo.android.util.maps.mapbox;

import android.util.Log;
import com.google.android.gms.maps.model.UrlTileProvider;
import java.net.MalformedURLException;
import java.net.URL;

public class MapboxTileProvider extends UrlTileProvider
{
  private static final String TAG = MapboxTileProvider.class.getSimpleName();
  private final String mapboxAccessToken;
  private final String mapboxId;

  public MapboxTileProvider(String paramString1, String paramString2)
  {
    super(512, 512);
    this.mapboxId = paramString1;
    this.mapboxAccessToken = paramString2;
  }

  public URL getTileUrl(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 <= 19)
    {
      String str = MapboxUtils.getMapTileURL(this.mapboxId, this.mapboxAccessToken, paramInt3, paramInt1, paramInt2);
      try
      {
        URL localURL = new URL(str);
        return localURL;
      }
      catch (MalformedURLException localMalformedURLException)
      {
        Log.e(TAG, "Error while building url for mapbox map tile.", localMalformedURLException);
      }
    }
    return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.maps.mapbox.MapboxTileProvider
 * JD-Core Version:    0.6.2
 */