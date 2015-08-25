package org.beyene.sius.unit.impl;

import org.beyene.sius.unit.time.Hour;
import org.beyene.sius.unit.time.Minute;
import org.beyene.sius.unit.time.Second;

public final class FactoryTime
{
  private static final Hour hour = (Hour)new HourImpl(0.0D).valueOf(0.0D);
  private static final Minute minute;
  private static final Second second = (Second)new SecondImpl(0.0D).valueOf(0.0D);

  static
  {
    minute = (Minute)new MinuteImpl(0.0D).valueOf(0.0D);
  }

  public static Hour hour(double paramDouble)
  {
    return (Hour)hour.valueOf(paramDouble);
  }

  public static Minute minute(double paramDouble)
  {
    return (Minute)minute.valueOf(paramDouble);
  }

  public static Second second(double paramDouble)
  {
    return (Second)second.valueOf(paramDouble);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.FactoryTime
 * JD-Core Version:    0.6.2
 */