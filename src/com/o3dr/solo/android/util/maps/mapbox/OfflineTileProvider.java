package com.o3dr.solo.android.util.maps.mapbox;

import android.content.Context;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;
import com.o3dr.solo.android.appstate.DatabaseState;
import com.o3dr.solo.android.util.maps.mapbox.offline.OfflineDatabaseHandler;

public class OfflineTileProvider
  implements TileProvider
{
  private static final String TAG = OfflineTileProvider.class.getSimpleName();
  private final Context context;
  private final String mapboxAccessToken;
  private final String mapboxId;

  public OfflineTileProvider(Context paramContext, String paramString1, String paramString2)
  {
    this.context = paramContext;
    this.mapboxId = paramString1;
    this.mapboxAccessToken = paramString2;
  }

  public Tile getTile(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 > 19)
      return TileProvider.NO_TILE;
    String str = MapboxUtils.getMapTileURL(this.mapboxId, this.mapboxAccessToken, paramInt3, paramInt1, paramInt2);
    byte[] arrayOfByte = DatabaseState.getOfflineDatabaseHandlerForMapId(this.context, this.mapboxId).dataForURL(str);
    if ((arrayOfByte == null) || (arrayOfByte.length == 0))
      return TileProvider.NO_TILE;
    return new Tile(512, 512, arrayOfByte);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.maps.mapbox.OfflineTileProvider
 * JD-Core Version:    0.6.2
 */