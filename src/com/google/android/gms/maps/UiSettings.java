package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class UiSettings
{
  private final IUiSettingsDelegate zzauO;

  UiSettings(IUiSettingsDelegate paramIUiSettingsDelegate)
  {
    this.zzauO = paramIUiSettingsDelegate;
  }

  public boolean isCompassEnabled()
  {
    try
    {
      boolean bool = this.zzauO.isCompassEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isIndoorLevelPickerEnabled()
  {
    try
    {
      boolean bool = this.zzauO.isIndoorLevelPickerEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isMapToolbarEnabled()
  {
    try
    {
      boolean bool = this.zzauO.isMapToolbarEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isMyLocationButtonEnabled()
  {
    try
    {
      boolean bool = this.zzauO.isMyLocationButtonEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isRotateGesturesEnabled()
  {
    try
    {
      boolean bool = this.zzauO.isRotateGesturesEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isScrollGesturesEnabled()
  {
    try
    {
      boolean bool = this.zzauO.isScrollGesturesEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isTiltGesturesEnabled()
  {
    try
    {
      boolean bool = this.zzauO.isTiltGesturesEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isZoomControlsEnabled()
  {
    try
    {
      boolean bool = this.zzauO.isZoomControlsEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isZoomGesturesEnabled()
  {
    try
    {
      boolean bool = this.zzauO.isZoomGesturesEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setAllGesturesEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setAllGesturesEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setCompassEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setCompassEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setIndoorLevelPickerEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setIndoorLevelPickerEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setMapToolbarEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setMapToolbarEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setMyLocationButtonEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setMyLocationButtonEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setRotateGesturesEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setRotateGesturesEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setScrollGesturesEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setScrollGesturesEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setTiltGesturesEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setTiltGesturesEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setZoomControlsEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setZoomControlsEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setZoomGesturesEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzauO.setZoomGesturesEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.UiSettings
 * JD-Core Version:    0.6.2
 */