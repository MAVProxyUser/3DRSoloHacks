package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Temperature;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.temperature.Kelvin;
import org.beyene.sius.util.Preferences;

final class KelvinImpl extends AbstractUnit<Temperature, Kelvin, Kelvin>
  implements Kelvin
{
  private static final transient Cache<Temperature, Kelvin, Kelvin> dynamicCache;
  private static final transient StaticCache<Temperature, Kelvin, Kelvin> staticCache = null;

  static
  {
    int i = Preferences.getInt("kelvin.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.KELVIN, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("kelvin.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("kelvin.cache.static.low", 0), j, KelvinImpl.class);
      return;
    }
  }

  public KelvinImpl(double paramDouble)
  {
    super(paramDouble, Temperature.INSTANCE, UnitIdentifier.KELVIN, Kelvin.class, dynamicCache, staticCache);
  }

  protected Kelvin _new_instance(double paramDouble)
  {
    return new KelvinImpl(paramDouble);
  }

  protected Kelvin _this()
  {
    return this;
  }

  public Kelvin fromBase(Kelvin paramKelvin)
  {
    return (Kelvin)valueOf(paramKelvin.getValue());
  }

  public Kelvin toBase()
  {
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.KelvinImpl
 * JD-Core Version:    0.6.2
 */