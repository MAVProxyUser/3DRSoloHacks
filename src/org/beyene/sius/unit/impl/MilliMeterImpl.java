package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.unit.length.MilliMeter;
import org.beyene.sius.util.Preferences;

public class MilliMeterImpl extends AbstractUnit<Length, Meter, MilliMeter>
  implements MilliMeter
{
  private static final transient Cache<Length, Meter, MilliMeter> dynamicCache;
  private static final transient StaticCache<Length, Meter, MilliMeter> staticCache = null;

  static
  {
    int i = Preferences.getInt("millimeter.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.MILLIMETER, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("millimeter.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("millimeter.cache.static.low", 0), j, MilliMeterImpl.class);
      return;
    }
  }

  public MilliMeterImpl(double paramDouble)
  {
    super(paramDouble, Length.INSTANCE, UnitIdentifier.MILLIMETER, MilliMeter.class, dynamicCache, staticCache);
  }

  public MilliMeterImpl(double paramDouble, Length paramLength, UnitId<Length, Meter, MilliMeter> paramUnitId, Class<? extends MilliMeter> paramClass, Cache<Length, Meter, MilliMeter> paramCache, StaticCache<Length, Meter, MilliMeter> paramStaticCache)
  {
    super(paramDouble, paramLength, paramUnitId, paramClass, paramCache, paramStaticCache);
  }

  protected MilliMeter _new_instance(double paramDouble)
  {
    return new MilliMeterImpl(paramDouble);
  }

  protected MilliMeter _this()
  {
    return this;
  }

  public MilliMeter fromBase(Meter paramMeter)
  {
    return (MilliMeter)valueOf(paramMeter.getValue() / 0.001D);
  }

  public Meter toBase()
  {
    return FactoryLength.meter(0.001D * this.value);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f mm", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.MilliMeterImpl
 * JD-Core Version:    0.6.2
 */