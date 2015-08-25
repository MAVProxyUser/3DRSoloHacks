package org.beyene.sius.unit.impl;

import org.beyene.sius.unit.composition.speed.FootPerSecond;
import org.beyene.sius.unit.composition.speed.MeterPerSecond;
import org.beyene.sius.unit.composition.speed.MilesPerHour;

public final class FactorySpeed
{
  private static FootPerSecond ftps = (FootPerSecond)new FootPerSecondImpl(0.0D).valueOf(0.0D);
  private static MilesPerHour mph;
  private static MeterPerSecond mps = (MeterPerSecond)new MeterPerSecondImpl(0.0D).valueOf(0.0D);

  static
  {
    mph = (MilesPerHour)new MilesPerHourImpl(0.0D).valueOf(0.0D);
  }

  public static FootPerSecond ftps(double paramDouble)
  {
    return (FootPerSecond)ftps.valueOf(paramDouble);
  }

  public static MilesPerHour mph(double paramDouble)
  {
    return (MilesPerHour)mph.valueOf(paramDouble);
  }

  public static MeterPerSecond mps(double paramDouble)
  {
    return (MeterPerSecond)mps.valueOf(paramDouble);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.FactorySpeed
 * JD-Core Version:    0.6.2
 */