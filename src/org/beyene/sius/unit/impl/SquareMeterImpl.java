package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.composition.Area;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.area.SquareMeter;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.util.Preferences;

public class SquareMeterImpl extends AbstractUnit<Area, SquareMeter, SquareMeter>
  implements SquareMeter
{
  private static final transient Cache<Area, SquareMeter, SquareMeter> dynamicCache;
  private static final transient StaticCache<Area, SquareMeter, SquareMeter> staticCache = null;

  static
  {
    int i = Preferences.getInt("squaremeter.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.SQUARE_METER, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("squaremeter.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("squaremeter.cache.static.low", 0), j, SquareMeterImpl.class);
      return;
    }
  }

  public SquareMeterImpl(double paramDouble)
  {
    super(paramDouble, Area.INSTANCE, UnitIdentifier.SQUARE_METER, SquareMeter.class, dynamicCache, staticCache);
  }

  public SquareMeterImpl(double paramDouble, Area paramArea, UnitId<Area, SquareMeter, SquareMeter> paramUnitId, Class<? extends SquareMeter> paramClass, Cache<Area, SquareMeter, SquareMeter> paramCache, StaticCache<Area, SquareMeter, SquareMeter> paramStaticCache)
  {
    super(paramDouble, paramArea, paramUnitId, paramClass, paramCache, paramStaticCache);
  }

  protected SquareMeter _new_instance(double paramDouble)
  {
    return new SquareMeterImpl(paramDouble);
  }

  protected SquareMeter _this()
  {
    return this;
  }

  public SquareMeter fromBase(SquareMeter paramSquareMeter)
  {
    return (SquareMeter)valueOf(paramSquareMeter.getValue());
  }

  public UnitId<Length, Meter, Meter> getComponentUnit1Id()
  {
    return UnitIdentifier.METER;
  }

  public UnitId<Length, Meter, Meter> getComponentUnit2Id()
  {
    return UnitIdentifier.METER;
  }

  public SquareMeter toBase()
  {
    return this;
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f m²", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.SquareMeterImpl
 * JD-Core Version:    0.6.2
 */