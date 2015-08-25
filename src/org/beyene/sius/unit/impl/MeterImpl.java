package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.util.Preferences;

final class MeterImpl extends AbstractUnit<Length, Meter, Meter>
  implements Meter
{
  private static final transient Cache<Length, Meter, Meter> dynamicCache;
  private static final transient StaticCache<Length, Meter, Meter> staticCache = null;

  static
  {
    int i = Preferences.getInt("meter.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.METER, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("meter.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("meter.cache.static.low", 0), j, MeterImpl.class);
      return;
    }
  }

  public MeterImpl(double paramDouble)
  {
    super(paramDouble, Length.INSTANCE, UnitIdentifier.METER, Meter.class, dynamicCache, staticCache);
  }

  MeterImpl(double paramDouble, Length paramLength, UnitId<Length, Meter, Meter> paramUnitId, Class<? extends Meter> paramClass, Cache<Length, Meter, Meter> paramCache, StaticCache<Length, Meter, Meter> paramStaticCache)
  {
    super(paramDouble, paramLength, paramUnitId, paramClass, paramCache, paramStaticCache);
  }

  protected Meter _new_instance(double paramDouble)
  {
    return new MeterImpl(paramDouble);
  }

  protected Meter _this()
  {
    return this;
  }

  public Meter fromBase(Meter paramMeter)
  {
    return (Meter)valueOf(paramMeter.getValue());
  }

  public Meter toBase()
  {
    return this;
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f m", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.MeterImpl
 * JD-Core Version:    0.6.2
 */