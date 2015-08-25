package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Time;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.time.Second;
import org.beyene.sius.util.Preferences;

final class SecondImpl extends AbstractUnit<Time, Second, Second>
  implements Second
{
  private static final transient Cache<Time, Second, Second> dynamicCache;
  private static final transient StaticCache<Time, Second, Second> staticCache = null;

  static
  {
    int i = Preferences.getInt("second.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.SECOND, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("second.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("second.cache.static.low", 0), j, SecondImpl.class);
      return;
    }
  }

  public SecondImpl(double paramDouble)
  {
    super(paramDouble, Time.INSTANCE, UnitIdentifier.SECOND, Second.class, dynamicCache, staticCache);
  }

  protected Second _new_instance(double paramDouble)
  {
    return new SecondImpl(paramDouble);
  }

  protected Second _this()
  {
    return this;
  }

  public Second fromBase(Second paramSecond)
  {
    return (Second)valueOf(paramSecond.getValue());
  }

  public Second toBase()
  {
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.SecondImpl
 * JD-Core Version:    0.6.2
 */