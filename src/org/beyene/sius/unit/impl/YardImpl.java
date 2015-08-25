package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.unit.length.Yard;
import org.beyene.sius.util.Preferences;

final class YardImpl extends AbstractUnit<Length, Meter, Yard>
  implements Yard
{
  private static final transient Cache<Length, Meter, Yard> dynamicCache;
  private static final transient StaticCache<Length, Meter, Yard> staticCache = null;

  static
  {
    int i = Preferences.getInt("yard.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.YARD, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("yard.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("yard.cache.static.low", 0), j, YardImpl.class);
      return;
    }
  }

  public YardImpl(double paramDouble)
  {
    super(paramDouble, Length.INSTANCE, UnitIdentifier.YARD, YardImpl.class, dynamicCache, staticCache);
  }

  protected Yard _new_instance(double paramDouble)
  {
    return new YardImpl(paramDouble);
  }

  protected Yard _this()
  {
    return this;
  }

  public Yard fromBase(Meter paramMeter)
  {
    return (Yard)valueOf(paramMeter.getValue() / 0.9144D);
  }

  public Meter toBase()
  {
    return FactoryLength.meter(0.9144D * this.value);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.YardImpl
 * JD-Core Version:    0.6.2
 */