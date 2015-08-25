package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Time;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.time.Hour;
import org.beyene.sius.unit.time.Second;
import org.beyene.sius.util.Preferences;

final class HourImpl extends AbstractUnit<Time, Second, Hour>
  implements Hour
{
  private static final transient Cache<Time, Second, Hour> dynamicCache;
  private static final transient StaticCache<Time, Second, Hour> staticCache = null;

  static
  {
    int i = Preferences.getInt("hour.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.HOUR, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("hour.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("hour.cache.static.low", 0), j, HourImpl.class);
      return;
    }
  }

  public HourImpl(double paramDouble)
  {
    super(paramDouble, Time.INSTANCE, UnitIdentifier.HOUR, HourImpl.class, dynamicCache, staticCache);
  }

  protected Hour _new_instance(double paramDouble)
  {
    return new HourImpl(paramDouble);
  }

  protected Hour _this()
  {
    return this;
  }

  public Hour fromBase(Second paramSecond)
  {
    return (Hour)valueOf(paramSecond.getValue() / 3600.0D);
  }

  public Second toBase()
  {
    return FactoryTime.second(3600.0D * this.value);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.HourImpl
 * JD-Core Version:    0.6.2
 */