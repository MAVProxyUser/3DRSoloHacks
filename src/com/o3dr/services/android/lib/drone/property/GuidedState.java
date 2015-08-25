package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;

public class GuidedState
  implements Parcelable
{
  public static final Parcelable.Creator<GuidedState> CREATOR = new Parcelable.Creator()
  {
    public GuidedState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new GuidedState(paramAnonymousParcel, null);
    }

    public GuidedState[] newArray(int paramAnonymousInt)
    {
      return new GuidedState[paramAnonymousInt];
    }
  };
  public static final int STATE_ACTIVE = 2;
  public static final int STATE_IDLE = 1;
  public static final int STATE_UNINITIALIZED;
  private LatLongAlt coordinate;
  private int state;

  public GuidedState()
  {
  }

  public GuidedState(int paramInt, LatLongAlt paramLatLongAlt)
  {
    this.state = paramInt;
    this.coordinate = paramLatLongAlt;
  }

  private GuidedState(Parcel paramParcel)
  {
    this.state = paramParcel.readInt();
    this.coordinate = ((LatLongAlt)paramParcel.readParcelable(LatLongAlt.class.getClassLoader()));
  }

  public int describeContents()
  {
    return 0;
  }

  public LatLongAlt getCoordinate()
  {
    return this.coordinate;
  }

  public boolean isActive()
  {
    return this.state == 2;
  }

  public boolean isIdle()
  {
    return this.state == 1;
  }

  public boolean isInitialized()
  {
    return this.state != 0;
  }

  public void setCoordinate(LatLongAlt paramLatLongAlt)
  {
    this.coordinate = paramLatLongAlt;
  }

  public void setState(int paramInt)
  {
    this.state = paramInt;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.state);
    paramParcel.writeParcelable(this.coordinate, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.GuidedState
 * JD-Core Version:    0.6.2
 */