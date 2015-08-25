package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.length.Foot;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.util.Preferences;

final class FootImpl extends AbstractUnit<Length, Meter, Foot>
  implements Foot
{
  private static final transient Cache<Length, Meter, Foot> dynamicCache;
  private static final transient StaticCache<Length, Meter, Foot> staticCache = null;

  static
  {
    int i = Preferences.getInt("foot.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.FOOT, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("foot.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("foot.cache.static.low", 0), j, FootImpl.class);
      return;
    }
  }

  public FootImpl(double paramDouble)
  {
    super(paramDouble, Length.INSTANCE, UnitIdentifier.FOOT, Foot.class, dynamicCache, staticCache);
  }

  protected Foot _new_instance(double paramDouble)
  {
    return new FootImpl(paramDouble);
  }

  protected Foot _this()
  {
    return this;
  }

  public Foot fromBase(Meter paramMeter)
  {
    return (Foot)valueOf(paramMeter.getValue() / 0.3048D);
  }

  public Meter toBase()
  {
    return FactoryLength.meter(0.3048D * this.value);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f ft", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.FootImpl
 * JD-Core Version:    0.6.2
 */