package com.o3dr.services.android.lib.drone.camera;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GoPro
  implements Parcelable
{
  public static final Parcelable.Creator<GoPro> CREATOR = new Parcelable.Creator()
  {
    public GoPro createFromParcel(Parcel paramAnonymousParcel)
    {
      return new GoPro(paramAnonymousParcel, null);
    }

    public GoPro[] newArray(int paramAnonymousInt)
    {
      return new GoPro[paramAnonymousInt];
    }
  };
  private boolean isConnected;
  private boolean isRecording;

  public GoPro()
  {
  }

  private GoPro(Parcel paramParcel)
  {
    boolean bool2;
    if (paramParcel.readByte() != 0)
    {
      bool2 = bool1;
      this.isConnected = bool2;
      if (paramParcel.readByte() == 0)
        break label38;
    }
    while (true)
    {
      this.isRecording = bool1;
      return;
      bool2 = false;
      break;
      label38: bool1 = false;
    }
  }

  public GoPro(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.isConnected = paramBoolean1;
    this.isRecording = paramBoolean2;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean isConnected()
  {
    return this.isConnected;
  }

  public boolean isRecording()
  {
    return this.isRecording;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    byte b1 = 1;
    byte b2;
    if (this.isConnected)
    {
      b2 = b1;
      paramParcel.writeByte(b2);
      if (!this.isRecording)
        break label37;
    }
    while (true)
    {
      paramParcel.writeByte(b1);
      return;
      b2 = 0;
      break;
      label37: b1 = 0;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.camera.GoPro
 * JD-Core Version:    0.6.2
 */