package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.internal.zzl;
import com.google.android.gms.maps.model.internal.zzl.zza;

public final class TileOverlayOptions
  implements SafeParcelable
{
  public static final zzn CREATOR = new zzn();
  private zzl zzavJ;
  private TileProvider zzavK;
  private boolean zzavL = true;
  private float zzavd;
  private boolean zzave = true;
  private final int zzzH;

  public TileOverlayOptions()
  {
    this.zzzH = 1;
  }

  TileOverlayOptions(int paramInt, IBinder paramIBinder, boolean paramBoolean1, float paramFloat, boolean paramBoolean2)
  {
    this.zzzH = paramInt;
    this.zzavJ = zzl.zza.zzcd(paramIBinder);
    if (this.zzavJ == null);
    for (TileProvider local1 = null; ; local1 = new TileProvider()
    {
      private final zzl zzavM = TileOverlayOptions.zza(TileOverlayOptions.this);

      public Tile getTile(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        try
        {
          Tile localTile = this.zzavM.getTile(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
          return localTile;
        }
        catch (RemoteException localRemoteException)
        {
        }
        return null;
      }
    })
    {
      this.zzavK = local1;
      this.zzave = paramBoolean1;
      this.zzavd = paramFloat;
      this.zzavL = paramBoolean2;
      return;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public TileOverlayOptions fadeIn(boolean paramBoolean)
  {
    this.zzavL = paramBoolean;
    return this;
  }

  public boolean getFadeIn()
  {
    return this.zzavL;
  }

  public TileProvider getTileProvider()
  {
    return this.zzavK;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public float getZIndex()
  {
    return this.zzavd;
  }

  public boolean isVisible()
  {
    return this.zzave;
  }

  public TileOverlayOptions tileProvider(final TileProvider paramTileProvider)
  {
    this.zzavK = paramTileProvider;
    if (this.zzavK == null);
    for (zzl.zza local2 = null; ; local2 = new zzl.zza()
    {
      public Tile getTile(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        return paramTileProvider.getTile(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
      }
    })
    {
      this.zzavJ = local2;
      return this;
    }
  }

  public TileOverlayOptions visible(boolean paramBoolean)
  {
    this.zzave = paramBoolean;
    return this;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzn.zza(this, paramParcel, paramInt);
  }

  public TileOverlayOptions zIndex(float paramFloat)
  {
    this.zzavd = paramFloat;
    return this;
  }

  IBinder zztU()
  {
    return this.zzavJ.asBinder();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.TileOverlayOptions
 * JD-Core Version:    0.6.2
 */