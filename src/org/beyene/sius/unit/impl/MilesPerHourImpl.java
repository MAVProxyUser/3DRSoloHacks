package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.Time;
import org.beyene.sius.dimension.composition.Speed;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.speed.MeterPerSecond;
import org.beyene.sius.unit.composition.speed.MilesPerHour;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.unit.length.Mile;
import org.beyene.sius.unit.time.Hour;
import org.beyene.sius.unit.time.Second;
import org.beyene.sius.util.Preferences;

final class MilesPerHourImpl extends AbstractUnit<Speed, MeterPerSecond, MilesPerHour>
  implements MilesPerHour
{
  private static final transient Cache<Speed, MeterPerSecond, MilesPerHour> dynamicCache;
  private static final transient StaticCache<Speed, MeterPerSecond, MilesPerHour> staticCache = null;

  static
  {
    int i = Preferences.getInt("mph.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.MILES_PER_HOUR, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("mph.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("mph.cache.static.low", 0), j, MilesPerHourImpl.class);
      return;
    }
  }

  public MilesPerHourImpl(double paramDouble)
  {
    super(paramDouble, Speed.INSTANCE, UnitIdentifier.MILES_PER_HOUR, MilesPerHour.class, dynamicCache, staticCache);
  }

  protected MilesPerHour _new_instance(double paramDouble)
  {
    return new MilesPerHourImpl(paramDouble);
  }

  protected MilesPerHour _this()
  {
    return this;
  }

  public MilesPerHour fromBase(MeterPerSecond paramMeterPerSecond)
  {
    return (MilesPerHour)valueOf(paramMeterPerSecond.getValue() / 0.44704D);
  }

  public UnitId<Length, Meter, Mile> getComponentUnit1Id()
  {
    return UnitIdentifier.MILE;
  }

  public UnitId<Time, Second, Hour> getComponentUnit2Id()
  {
    return UnitIdentifier.HOUR;
  }

  public MeterPerSecond toBase()
  {
    return FactorySpeed.mps(0.44704D * this.value);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f mph", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.MilesPerHourImpl
 * JD-Core Version:    0.6.2
 */