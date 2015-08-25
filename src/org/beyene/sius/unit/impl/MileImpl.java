package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.unit.length.Mile;
import org.beyene.sius.util.Preferences;

final class MileImpl extends AbstractUnit<Length, Meter, Mile>
  implements Mile
{
  private static final transient Cache<Length, Meter, Mile> dynamicCache;
  private static final transient StaticCache<Length, Meter, Mile> staticCache = null;

  static
  {
    int i = Preferences.getInt("mile.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.MILE, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("mile.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("mile.cache.static.low", 0), j, MileImpl.class);
      return;
    }
  }

  public MileImpl(double paramDouble)
  {
    super(paramDouble, Length.INSTANCE, UnitIdentifier.MILE, Mile.class, dynamicCache, staticCache);
  }

  protected Mile _new_instance(double paramDouble)
  {
    return new MileImpl(paramDouble);
  }

  protected Mile _this()
  {
    return this;
  }

  public Mile fromBase(Meter paramMeter)
  {
    return (Mile)valueOf(paramMeter.getValue() / 1609.3440000000001D);
  }

  public Meter toBase()
  {
    return FactoryLength.meter(1609.3440000000001D * this.value);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f mi", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.MileImpl
 * JD-Core Version:    0.6.2
 */