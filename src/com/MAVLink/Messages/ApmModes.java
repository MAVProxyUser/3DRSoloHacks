package com.MAVLink.Messages;

import java.util.ArrayList;
import java.util.List;

public enum ApmModes
{
  private final String name;
  private final long number;
  private final int type;

  static
  {
    FIXED_WING_CIRCLE = new ApmModes("FIXED_WING_CIRCLE", 1, 1L, "Circle", 1);
    FIXED_WING_STABILIZE = new ApmModes("FIXED_WING_STABILIZE", 2, 2L, "Stabilize", 1);
    FIXED_WING_TRAINING = new ApmModes("FIXED_WING_TRAINING", 3, 3L, "Training", 1);
    FIXED_WING_FLY_BY_WIRE_A = new ApmModes("FIXED_WING_FLY_BY_WIRE_A", 4, 5L, "FBW A", 1);
    FIXED_WING_FLY_BY_WIRE_B = new ApmModes("FIXED_WING_FLY_BY_WIRE_B", 5, 6L, "FBW B", 1);
    FIXED_WING_AUTO = new ApmModes("FIXED_WING_AUTO", 6, 10L, "Auto", 1);
    FIXED_WING_RTL = new ApmModes("FIXED_WING_RTL", 7, 11L, "RTL", 1);
    FIXED_WING_LOITER = new ApmModes("FIXED_WING_LOITER", 8, 12L, "Loiter", 1);
    FIXED_WING_GUIDED = new ApmModes("FIXED_WING_GUIDED", 9, 15L, "Guided", 1);
    ROTOR_STABILIZE = new ApmModes("ROTOR_STABILIZE", 10, 0L, "Stabilize", 2);
    ROTOR_ACRO = new ApmModes("ROTOR_ACRO", 11, 1L, "Acro", 2);
    ROTOR_ALT_HOLD = new ApmModes("ROTOR_ALT_HOLD", 12, 2L, "Alt Hold", 2);
    ROTOR_AUTO = new ApmModes("ROTOR_AUTO", 13, 3L, "Auto", 2);
    ROTOR_GUIDED = new ApmModes("ROTOR_GUIDED", 14, 4L, "Guided", 2);
    ROTOR_LOITER = new ApmModes("ROTOR_LOITER", 15, 5L, "Loiter", 2);
    ROTOR_RTL = new ApmModes("ROTOR_RTL", 16, 6L, "RTL", 2);
    ROTOR_CIRCLE = new ApmModes("ROTOR_CIRCLE", 17, 7L, "Circle", 2);
    ROTOR_LAND = new ApmModes("ROTOR_LAND", 18, 9L, "Land", 2);
    ROTOR_TOY = new ApmModes("ROTOR_TOY", 19, 11L, "Drift", 2);
    ROTOR_SPORT = new ApmModes("ROTOR_SPORT", 20, 13L, "Sport", 2);
    ROTOR_AUTOTUNE = new ApmModes("ROTOR_AUTOTUNE", 21, 15L, "Autotune", 2);
    ROTOR_POSHOLD = new ApmModes("ROTOR_POSHOLD", 22, 16L, "PosHold", 2);
    ROTOR_BRAKE = new ApmModes("ROTOR_BRAKE", 23, 17L, "Brake", 2);
    ROVER_MANUAL = new ApmModes("ROVER_MANUAL", 24, 0L, "MANUAL", 10);
    ROVER_LEARNING = new ApmModes("ROVER_LEARNING", 25, 2L, "LEARNING", 10);
    ROVER_STEERING = new ApmModes("ROVER_STEERING", 26, 3L, "STEERING", 10);
    ROVER_HOLD = new ApmModes("ROVER_HOLD", 27, 4L, "HOLD", 10);
    ROVER_AUTO = new ApmModes("ROVER_AUTO", 28, 10L, "AUTO", 10);
    ROVER_RTL = new ApmModes("ROVER_RTL", 29, 11L, "RTL", 10);
    ROVER_GUIDED = new ApmModes("ROVER_GUIDED", 30, 15L, "GUIDED", 10);
    ROVER_INITIALIZING = new ApmModes("ROVER_INITIALIZING", 31, 16L, "INITIALIZING", 10);
    UNKNOWN = new ApmModes("UNKNOWN", 32, -1L, "Unknown", 0);
    ApmModes[] arrayOfApmModes = new ApmModes[33];
    arrayOfApmModes[0] = FIXED_WING_MANUAL;
    arrayOfApmModes[1] = FIXED_WING_CIRCLE;
    arrayOfApmModes[2] = FIXED_WING_STABILIZE;
    arrayOfApmModes[3] = FIXED_WING_TRAINING;
    arrayOfApmModes[4] = FIXED_WING_FLY_BY_WIRE_A;
    arrayOfApmModes[5] = FIXED_WING_FLY_BY_WIRE_B;
    arrayOfApmModes[6] = FIXED_WING_AUTO;
    arrayOfApmModes[7] = FIXED_WING_RTL;
    arrayOfApmModes[8] = FIXED_WING_LOITER;
    arrayOfApmModes[9] = FIXED_WING_GUIDED;
    arrayOfApmModes[10] = ROTOR_STABILIZE;
    arrayOfApmModes[11] = ROTOR_ACRO;
    arrayOfApmModes[12] = ROTOR_ALT_HOLD;
    arrayOfApmModes[13] = ROTOR_AUTO;
    arrayOfApmModes[14] = ROTOR_GUIDED;
    arrayOfApmModes[15] = ROTOR_LOITER;
    arrayOfApmModes[16] = ROTOR_RTL;
    arrayOfApmModes[17] = ROTOR_CIRCLE;
    arrayOfApmModes[18] = ROTOR_LAND;
    arrayOfApmModes[19] = ROTOR_TOY;
    arrayOfApmModes[20] = ROTOR_SPORT;
    arrayOfApmModes[21] = ROTOR_AUTOTUNE;
    arrayOfApmModes[22] = ROTOR_POSHOLD;
    arrayOfApmModes[23] = ROTOR_BRAKE;
    arrayOfApmModes[24] = ROVER_MANUAL;
    arrayOfApmModes[25] = ROVER_LEARNING;
    arrayOfApmModes[26] = ROVER_STEERING;
    arrayOfApmModes[27] = ROVER_HOLD;
    arrayOfApmModes[28] = ROVER_AUTO;
    arrayOfApmModes[29] = ROVER_RTL;
    arrayOfApmModes[30] = ROVER_GUIDED;
    arrayOfApmModes[31] = ROVER_INITIALIZING;
    arrayOfApmModes[32] = UNKNOWN;
  }

  private ApmModes(long paramLong, String paramString, int paramInt)
  {
    this.number = paramLong;
    this.name = paramString;
    this.type = paramInt;
  }

  public static ApmModes getMode(long paramLong, int paramInt)
  {
    if (isCopter(paramInt))
      paramInt = 2;
    for (ApmModes localApmModes : values())
      if ((paramLong == localApmModes.getNumber()) && (paramInt == localApmModes.getType()))
        return localApmModes;
    return UNKNOWN;
  }

  public static ApmModes getMode(String paramString, int paramInt)
  {
    if (isCopter(paramInt))
      paramInt = 2;
    for (ApmModes localApmModes : values())
      if ((paramString.equals(localApmModes.getName())) && (paramInt == localApmModes.getType()))
        return localApmModes;
    return UNKNOWN;
  }

  public static List<ApmModes> getModeList(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    if (isCopter(paramInt))
      paramInt = 2;
    for (ApmModes localApmModes : values())
      if (localApmModes.getType() == paramInt)
        localArrayList.add(localApmModes);
    return localArrayList;
  }

  public static boolean isCopter(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return false;
    case 2:
    case 4:
    case 13:
    case 14:
    case 15:
    }
    return true;
  }

  public static boolean isValid(ApmModes paramApmModes)
  {
    return paramApmModes != UNKNOWN;
  }

  public String getName()
  {
    return this.name;
  }

  public long getNumber()
  {
    return this.number;
  }

  public int getType()
  {
    return this.type;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.Messages.ApmModes
 * JD-Core Version:    0.6.2
 */