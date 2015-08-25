package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Temperature;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.temperature.Fahrenheit;
import org.beyene.sius.unit.temperature.Kelvin;
import org.beyene.sius.util.Preferences;

final class FahrenheitImpl extends AbstractUnit<Temperature, Kelvin, Fahrenheit>
  implements Fahrenheit
{
  private static final transient Cache<Temperature, Kelvin, Fahrenheit> dynamicCache;
  private static final transient StaticCache<Temperature, Kelvin, Fahrenheit> staticCache = null;

  static
  {
    int i = Preferences.getInt("fahrenheit.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.FAHRENHEIT, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("fahrenheit.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("fahrenheit.cache.static.low", 0), j, FahrenheitImpl.class);
      return;
    }
  }

  public FahrenheitImpl(double paramDouble)
  {
    super(paramDouble, Temperature.INSTANCE, UnitIdentifier.FAHRENHEIT, Fahrenheit.class, dynamicCache, staticCache);
  }

  protected Fahrenheit _new_instance(double paramDouble)
  {
    return new FahrenheitImpl(paramDouble);
  }

  protected Fahrenheit _this()
  {
    return this;
  }

  public Fahrenheit fromBase(Kelvin paramKelvin)
  {
    return (Fahrenheit)valueOf(1.8D * paramKelvin.getValue() - 459.67000000000002D);
  }

  public Kelvin toBase()
  {
    return FactoryTemperature.kelvin(0.5555555555555556D * (459.67000000000002D + this.value));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.FahrenheitImpl
 * JD-Core Version:    0.6.2
 */