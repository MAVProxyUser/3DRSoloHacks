package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Temperature;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.temperature.Celsius;
import org.beyene.sius.unit.temperature.Kelvin;
import org.beyene.sius.util.Preferences;

final class CelsiusImpl extends AbstractUnit<Temperature, Kelvin, Celsius>
  implements Celsius
{
  private static final transient Cache<Temperature, Kelvin, Celsius> dynamicCache;
  private static final transient StaticCache<Temperature, Kelvin, Celsius> staticCache = null;

  static
  {
    int i = Preferences.getInt("celsius.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.CELSIUS, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("celsius.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("celsius.cache.static.low", 0), j, CelsiusImpl.class);
      return;
    }
  }

  public CelsiusImpl(double paramDouble)
  {
    super(paramDouble, Temperature.INSTANCE, UnitIdentifier.CELSIUS, Celsius.class, dynamicCache, staticCache);
  }

  protected Celsius _new_instance(double paramDouble)
  {
    return new CelsiusImpl(paramDouble);
  }

  protected Celsius _this()
  {
    return this;
  }

  public Celsius fromBase(Kelvin paramKelvin)
  {
    return (Celsius)valueOf(paramKelvin.getValue() - 273.14999999999998D);
  }

  public Kelvin toBase()
  {
    return FactoryTemperature.kelvin(273.14999999999998D + this.value);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.CelsiusImpl
 * JD-Core Version:    0.6.2
 */