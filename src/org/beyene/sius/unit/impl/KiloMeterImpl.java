package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.length.KiloMeter;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.util.Preferences;

public class KiloMeterImpl extends AbstractUnit<Length, Meter, KiloMeter>
  implements KiloMeter
{
  private static final transient Cache<Length, Meter, KiloMeter> dynamicCache;
  private static final transient StaticCache<Length, Meter, KiloMeter> staticCache = null;

  static
  {
    int i = Preferences.getInt("kilometer.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.KILOMETER, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("kilometer.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("kilometer.cache.static.low", 0), j, KiloMeterImpl.class);
      return;
    }
  }

  public KiloMeterImpl(double paramDouble)
  {
    super(paramDouble, Length.INSTANCE, UnitIdentifier.KILOMETER, KiloMeter.class, dynamicCache, staticCache);
  }

  KiloMeterImpl(double paramDouble, Length paramLength, UnitId<Length, Meter, KiloMeter> paramUnitId, Class<? extends KiloMeter> paramClass, Cache<Length, Meter, KiloMeter> paramCache, StaticCache<Length, Meter, KiloMeter> paramStaticCache)
  {
    super(paramDouble, paramLength, paramUnitId, paramClass, paramCache, paramStaticCache);
  }

  protected KiloMeter _new_instance(double paramDouble)
  {
    return new KiloMeterImpl(paramDouble);
  }

  protected KiloMeter _this()
  {
    return this;
  }

  public KiloMeter fromBase(Meter paramMeter)
  {
    return (KiloMeter)valueOf(paramMeter.getValue() / 1000.0D);
  }

  public Meter toBase()
  {
    return FactoryLength.meter(1000.0D * this.value);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f km", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.KiloMeterImpl
 * JD-Core Version:    0.6.2
 */