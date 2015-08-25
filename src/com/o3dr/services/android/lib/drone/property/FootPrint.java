package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.util.MathUtils;
import java.util.ArrayList;
import java.util.List;

public class FootPrint
  implements Parcelable
{
  public static final Parcelable.Creator<FootPrint> CREATOR = new Parcelable.Creator()
  {
    public FootPrint createFromParcel(Parcel paramAnonymousParcel)
    {
      return new FootPrint(paramAnonymousParcel, null);
    }

    public FootPrint[] newArray(int paramAnonymousInt)
    {
      return new FootPrint[paramAnonymousInt];
    }
  };
  private double meanGSD;
  private List<LatLong> vertex = new ArrayList();

  public FootPrint()
  {
  }

  public FootPrint(double paramDouble, List<LatLong> paramList)
  {
    this.meanGSD = paramDouble;
    this.vertex = paramList;
  }

  private FootPrint(Parcel paramParcel)
  {
    this.meanGSD = paramParcel.readDouble();
    paramParcel.readTypedList(this.vertex, LatLong.CREATOR);
  }

  public int describeContents()
  {
    return 0;
  }

  public double getLateralSize()
  {
    return (MathUtils.getDistance2D((LatLong)this.vertex.get(0), (LatLong)this.vertex.get(1)) + MathUtils.getDistance2D((LatLong)this.vertex.get(2), (LatLong)this.vertex.get(3))) / 2.0D;
  }

  public double getLongitudinalSize()
  {
    return (MathUtils.getDistance2D((LatLong)this.vertex.get(0), (LatLong)this.vertex.get(3)) + MathUtils.getDistance2D((LatLong)this.vertex.get(1), (LatLong)this.vertex.get(2))) / 2.0D;
  }

  public double getMeanGSD()
  {
    return this.meanGSD;
  }

  public List<LatLong> getVertexInGlobalFrame()
  {
    return this.vertex;
  }

  public void setMeanGSD(double paramDouble)
  {
    this.meanGSD = paramDouble;
  }

  public void setVertex(List<LatLong> paramList)
  {
    this.vertex = paramList;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.meanGSD);
    paramParcel.writeTypedList(this.vertex);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.FootPrint
 * JD-Core Version:    0.6.2
 */