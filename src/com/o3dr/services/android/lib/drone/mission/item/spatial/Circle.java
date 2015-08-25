package com.o3dr.services.android.lib.drone.mission.item.spatial;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;

public class Circle extends BaseSpatialItem
  implements Parcelable
{
  public static final Parcelable.Creator<Circle> CREATOR = new Parcelable.Creator()
  {
    public Circle createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Circle(paramAnonymousParcel, null);
    }

    public Circle[] newArray(int paramAnonymousInt)
    {
      return new Circle[paramAnonymousInt];
    }
  };
  private double radius = 10.0D;
  private int turns = 1;

  public Circle()
  {
    super(MissionItemType.CIRCLE);
  }

  private Circle(Parcel paramParcel)
  {
    super(paramParcel);
    this.radius = paramParcel.readDouble();
    this.turns = paramParcel.readInt();
  }

  public Circle(Circle paramCircle)
  {
    super(paramCircle);
    this.radius = paramCircle.radius;
    this.turns = paramCircle.turns;
  }

  public MissionItem clone()
  {
    return new Circle(this);
  }

  public double getRadius()
  {
    return this.radius;
  }

  public int getTurns()
  {
    return this.turns;
  }

  public void setRadius(double paramDouble)
  {
    this.radius = paramDouble;
  }

  public void setTurns(int paramInt)
  {
    this.turns = paramInt;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeDouble(this.radius);
    paramParcel.writeInt(this.turns);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.spatial.Circle
 * JD-Core Version:    0.6.2
 */