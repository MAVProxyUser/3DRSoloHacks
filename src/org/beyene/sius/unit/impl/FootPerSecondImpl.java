package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.Time;
import org.beyene.sius.dimension.composition.Speed;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.speed.FootPerSecond;
import org.beyene.sius.unit.composition.speed.MeterPerSecond;
import org.beyene.sius.unit.length.Foot;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.unit.time.Second;
import org.beyene.sius.util.Preferences;

public class FootPerSecondImpl extends AbstractUnit<Speed, MeterPerSecond, FootPerSecond>
  implements FootPerSecond
{
  private static final transient Cache<Speed, MeterPerSecond, FootPerSecond> dynamicCache;
  private static final transient StaticCache<Speed, MeterPerSecond, FootPerSecond> staticCache = null;

  static
  {
    int i = Preferences.getInt("ftps.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.FOOT_PER_SECOND, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("ftps.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("ftps.cache.static.low", 0), j, FootPerSecondImpl.class);
      return;
    }
  }

  public FootPerSecondImpl(double paramDouble)
  {
    super(paramDouble, Speed.INSTANCE, UnitIdentifier.FOOT_PER_SECOND, FootPerSecond.class, dynamicCache, staticCache);
  }

  protected FootPerSecond _new_instance(double paramDouble)
  {
    return new FootPerSecondImpl(paramDouble);
  }

  protected FootPerSecond _this()
  {
    return this;
  }

  public FootPerSecond fromBase(MeterPerSecond paramMeterPerSecond)
  {
    return (FootPerSecond)valueOf(paramMeterPerSecond.getValue() / 0.3048D);
  }

  public UnitId<Length, Meter, Foot> getComponentUnit1Id()
  {
    return UnitIdentifier.FOOT;
  }

  public UnitId<Time, Second, Second> getComponentUnit2Id()
  {
    return UnitIdentifier.SECOND;
  }

  public MeterPerSecond toBase()
  {
    return FactorySpeed.mps(0.3048D * this.value);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f ft/s", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.FootPerSecondImpl
 * JD-Core Version:    0.6.2
 */