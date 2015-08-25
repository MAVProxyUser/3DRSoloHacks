package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Time;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.time.Minute;
import org.beyene.sius.unit.time.Second;
import org.beyene.sius.util.Preferences;

final class MinuteImpl extends AbstractUnit<Time, Second, Minute>
  implements Minute
{
  private static final transient Cache<Time, Second, Minute> dynamicCache;
  private static final transient StaticCache<Time, Second, Minute> staticCache = null;

  static
  {
    int i = Preferences.getInt("minute.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.MINUTE, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("minute.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("minute.cache.static.low", 0), j, MinuteImpl.class);
      return;
    }
  }

  public MinuteImpl(double paramDouble)
  {
    super(paramDouble, Time.INSTANCE, UnitIdentifier.MINUTE, Minute.class, dynamicCache, staticCache);
  }

  protected Minute _new_instance(double paramDouble)
  {
    return new MinuteImpl(paramDouble);
  }

  protected Minute _this()
  {
    return this;
  }

  public Minute fromBase(Second paramSecond)
  {
    return (Minute)valueOf(paramSecond.getValue() / 60.0D);
  }

  public Second toBase()
  {
    return FactoryTime.second(60.0D * this.value);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.MinuteImpl
 * JD-Core Version:    0.6.2
 */