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
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.unit.time.Second;
import org.beyene.sius.util.Preferences;

final class MeterPerSecondImpl extends AbstractUnit<Speed, MeterPerSecond, MeterPerSecond>
  implements MeterPerSecond
{
  private static final transient Cache<Speed, MeterPerSecond, MeterPerSecond> dynamicCache;
  private static final transient StaticCache<Speed, MeterPerSecond, MeterPerSecond> staticCache = null;

  static
  {
    int i = Preferences.getInt("mps.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.METER_PER_SECOND, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("mps.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("mps.cache.static.low", 0), j, MeterPerSecondImpl.class);
      return;
    }
  }

  public MeterPerSecondImpl(double paramDouble)
  {
    super(paramDouble, Speed.INSTANCE, UnitIdentifier.METER_PER_SECOND, MeterPerSecond.class, dynamicCache, staticCache);
  }

  protected MeterPerSecond _new_instance(double paramDouble)
  {
    return new MeterPerSecondImpl(paramDouble);
  }

  protected MeterPerSecond _this()
  {
    return this;
  }

  public MeterPerSecond fromBase(MeterPerSecond paramMeterPerSecond)
  {
    return (MeterPerSecond)valueOf(paramMeterPerSecond.getValue());
  }

  public UnitId<Length, Meter, Meter> getComponentUnit1Id()
  {
    return UnitIdentifier.METER;
  }

  public UnitId<Time, Second, Second> getComponentUnit2Id()
  {
    return UnitIdentifier.SECOND;
  }

  public MeterPerSecond toBase()
  {
    return this;
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f m/s", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.MeterPerSecondImpl
 * JD-Core Version:    0.6.2
 */