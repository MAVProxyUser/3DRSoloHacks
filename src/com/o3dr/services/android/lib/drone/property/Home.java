package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;

public class Home
  implements Parcelable
{
  public static final Parcelable.Creator<Home> CREATOR = new Parcelable.Creator()
  {
    public Home createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Home(paramAnonymousParcel, null);
    }

    public Home[] newArray(int paramAnonymousInt)
    {
      return new Home[paramAnonymousInt];
    }
  };
  private LatLongAlt mCoordinate;

  public Home()
  {
  }

  public Home(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.mCoordinate = new LatLongAlt(paramDouble1, paramDouble2, paramDouble3);
  }

  private Home(Parcel paramParcel)
  {
    this.mCoordinate = ((LatLongAlt)paramParcel.readParcelable(LatLongAlt.class.getClassLoader()));
  }

  public Home(LatLongAlt paramLatLongAlt)
  {
    this.mCoordinate = paramLatLongAlt;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Home localHome;
    do
    {
      return true;
      if (!(paramObject instanceof Home))
        return false;
      localHome = (Home)paramObject;
      if (this.mCoordinate == null)
        break;
    }
    while (this.mCoordinate.equals(localHome.mCoordinate));
    while (localHome.mCoordinate != null)
      return false;
    return true;
  }

  public LatLongAlt getCoordinate()
  {
    return this.mCoordinate;
  }

  public int hashCode()
  {
    if (this.mCoordinate != null)
      return this.mCoordinate.hashCode();
    return 0;
  }

  public boolean isValid()
  {
    return this.mCoordinate != null;
  }

  public void setCoordinate(LatLongAlt paramLatLongAlt)
  {
    this.mCoordinate = paramLatLongAlt;
  }

  public String toString()
  {
    return "LaunchPad{mCoordinate=" + this.mCoordinate + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(this.mCoordinate, 0);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Home
 * JD-Core Version:    0.6.2
 */