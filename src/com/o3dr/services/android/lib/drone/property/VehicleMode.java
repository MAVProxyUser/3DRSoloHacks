package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public enum VehicleMode
  implements Parcelable
{
  public static final Parcelable.Creator<VehicleMode> CREATOR = new Parcelable.Creator()
  {
    public VehicleMode createFromParcel(Parcel paramAnonymousParcel)
    {
      return VehicleMode.valueOf(paramAnonymousParcel.readString());
    }

    public VehicleMode[] newArray(int paramAnonymousInt)
    {
      return new VehicleMode[paramAnonymousInt];
    }
  };
  private final int droneType;
  private final String label;
  private final int mode;

  static
  {
    PLANE_CIRCLE = new VehicleMode("PLANE_CIRCLE", 1, 1, 1, "Circle");
    PLANE_STABILIZE = new VehicleMode("PLANE_STABILIZE", 2, 2, 1, "Stabilize");
    PLANE_TRAINING = new VehicleMode("PLANE_TRAINING", 3, 3, 1, "Training");
    PLANE_ACRO = new VehicleMode("PLANE_ACRO", 4, 4, 1, "Acro");
    PLANE_FLY_BY_WIRE_A = new VehicleMode("PLANE_FLY_BY_WIRE_A", 5, 5, 1, "FBW A");
    PLANE_FLY_BY_WIRE_B = new VehicleMode("PLANE_FLY_BY_WIRE_B", 6, 6, 1, "FBW B");
    PLANE_CRUISE = new VehicleMode("PLANE_CRUISE", 7, 7, 1, "Cruise");
    PLANE_AUTOTUNE = new VehicleMode("PLANE_AUTOTUNE", 8, 8, 1, "Autotune");
    PLANE_AUTO = new VehicleMode("PLANE_AUTO", 9, 10, 1, "Auto");
    PLANE_RTL = new VehicleMode("PLANE_RTL", 10, 11, 1, "RTL");
    PLANE_LOITER = new VehicleMode("PLANE_LOITER", 11, 12, 1, "Loiter");
    PLANE_GUIDED = new VehicleMode("PLANE_GUIDED", 12, 15, 1, "Guided");
    COPTER_STABILIZE = new VehicleMode("COPTER_STABILIZE", 13, 0, 2, "Stabilize");
    COPTER_ACRO = new VehicleMode("COPTER_ACRO", 14, 1, 2, "Acro");
    COPTER_ALT_HOLD = new VehicleMode("COPTER_ALT_HOLD", 15, 2, 2, "Alt Hold");
    COPTER_AUTO = new VehicleMode("COPTER_AUTO", 16, 3, 2, "Auto");
    COPTER_GUIDED = new VehicleMode("COPTER_GUIDED", 17, 4, 2, "Guided");
    COPTER_LOITER = new VehicleMode("COPTER_LOITER", 18, 5, 2, "Loiter");
    COPTER_RTL = new VehicleMode("COPTER_RTL", 19, 6, 2, "RTL");
    COPTER_CIRCLE = new VehicleMode("COPTER_CIRCLE", 20, 7, 2, "Circle");
    COPTER_LAND = new VehicleMode("COPTER_LAND", 21, 9, 2, "Land");
    COPTER_DRIFT = new VehicleMode("COPTER_DRIFT", 22, 11, 2, "Drift");
    COPTER_SPORT = new VehicleMode("COPTER_SPORT", 23, 13, 2, "Sport");
    COPTER_FLIP = new VehicleMode("COPTER_FLIP", 24, 14, 2, "Flip");
    COPTER_AUTOTUNE = new VehicleMode("COPTER_AUTOTUNE", 25, 15, 2, "Autotune");
    COPTER_POSHOLD = new VehicleMode("COPTER_POSHOLD", 26, 16, 2, "PosHold");
    COPTER_BRAKE = new VehicleMode("COPTER_BRAKE", 27, 17, 2, "Brake");
    ROVER_MANUAL = new VehicleMode("ROVER_MANUAL", 28, 0, 10, "Manual");
    ROVER_LEARNING = new VehicleMode("ROVER_LEARNING", 29, 2, 10, "Learning");
    ROVER_STEERING = new VehicleMode("ROVER_STEERING", 30, 3, 10, "Steering");
    ROVER_HOLD = new VehicleMode("ROVER_HOLD", 31, 4, 10, "Hold");
    ROVER_AUTO = new VehicleMode("ROVER_AUTO", 32, 10, 10, "Auto");
    ROVER_RTL = new VehicleMode("ROVER_RTL", 33, 11, 10, "RTL");
    ROVER_GUIDED = new VehicleMode("ROVER_GUIDED", 34, 15, 10, "Guided");
    ROVER_INITIALIZING = new VehicleMode("ROVER_INITIALIZING", 35, 16, 10, "Initializing");
    UNKNOWN = new VehicleMode("UNKNOWN", 36, -1, -1, "Unknown");
    VehicleMode[] arrayOfVehicleMode = new VehicleMode[37];
    arrayOfVehicleMode[0] = PLANE_MANUAL;
    arrayOfVehicleMode[1] = PLANE_CIRCLE;
    arrayOfVehicleMode[2] = PLANE_STABILIZE;
    arrayOfVehicleMode[3] = PLANE_TRAINING;
    arrayOfVehicleMode[4] = PLANE_ACRO;
    arrayOfVehicleMode[5] = PLANE_FLY_BY_WIRE_A;
    arrayOfVehicleMode[6] = PLANE_FLY_BY_WIRE_B;
    arrayOfVehicleMode[7] = PLANE_CRUISE;
    arrayOfVehicleMode[8] = PLANE_AUTOTUNE;
    arrayOfVehicleMode[9] = PLANE_AUTO;
    arrayOfVehicleMode[10] = PLANE_RTL;
    arrayOfVehicleMode[11] = PLANE_LOITER;
    arrayOfVehicleMode[12] = PLANE_GUIDED;
    arrayOfVehicleMode[13] = COPTER_STABILIZE;
    arrayOfVehicleMode[14] = COPTER_ACRO;
    arrayOfVehicleMode[15] = COPTER_ALT_HOLD;
    arrayOfVehicleMode[16] = COPTER_AUTO;
    arrayOfVehicleMode[17] = COPTER_GUIDED;
    arrayOfVehicleMode[18] = COPTER_LOITER;
    arrayOfVehicleMode[19] = COPTER_RTL;
    arrayOfVehicleMode[20] = COPTER_CIRCLE;
    arrayOfVehicleMode[21] = COPTER_LAND;
    arrayOfVehicleMode[22] = COPTER_DRIFT;
    arrayOfVehicleMode[23] = COPTER_SPORT;
    arrayOfVehicleMode[24] = COPTER_FLIP;
    arrayOfVehicleMode[25] = COPTER_AUTOTUNE;
    arrayOfVehicleMode[26] = COPTER_POSHOLD;
    arrayOfVehicleMode[27] = COPTER_BRAKE;
    arrayOfVehicleMode[28] = ROVER_MANUAL;
    arrayOfVehicleMode[29] = ROVER_LEARNING;
    arrayOfVehicleMode[30] = ROVER_STEERING;
    arrayOfVehicleMode[31] = ROVER_HOLD;
    arrayOfVehicleMode[32] = ROVER_AUTO;
    arrayOfVehicleMode[33] = ROVER_RTL;
    arrayOfVehicleMode[34] = ROVER_GUIDED;
    arrayOfVehicleMode[35] = ROVER_INITIALIZING;
    arrayOfVehicleMode[36] = UNKNOWN;
  }

  private VehicleMode(int paramInt1, int paramInt2, String paramString)
  {
    this.mode = paramInt1;
    this.droneType = paramInt2;
    this.label = paramString;
  }

  public static List<VehicleMode> getVehicleModePerDroneType(int paramInt)
  {
    VehicleMode[] arrayOfVehicleMode = values();
    ArrayList localArrayList = new ArrayList(arrayOfVehicleMode.length);
    int i = arrayOfVehicleMode.length;
    for (int j = 0; j < i; j++)
    {
      VehicleMode localVehicleMode = arrayOfVehicleMode[j];
      if (localVehicleMode.getDroneType() == paramInt)
        localArrayList.add(localVehicleMode);
    }
    return localArrayList;
  }

  public int describeContents()
  {
    return 0;
  }

  public int getDroneType()
  {
    return this.droneType;
  }

  public String getLabel()
  {
    return this.label;
  }

  public int getMode()
  {
    return this.mode;
  }

  public String toString()
  {
    return getLabel();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(name());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.VehicleMode
 * JD-Core Version:    0.6.2
 */