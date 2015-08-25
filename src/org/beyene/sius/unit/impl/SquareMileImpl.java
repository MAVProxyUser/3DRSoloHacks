package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.composition.Area;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.area.Constants;
import org.beyene.sius.unit.composition.area.SquareMeter;
import org.beyene.sius.unit.composition.area.SquareMile;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.unit.length.Mile;
import org.beyene.sius.util.Preferences;

public class SquareMileImpl extends AbstractUnit<Area, SquareMeter, SquareMile>
  implements SquareMile
{
  private static final transient Cache<Area, SquareMeter, SquareMile> dynamicCache;
  private static final transient StaticCache<Area, SquareMeter, SquareMile> staticCache = null;

  static
  {
    int i = Preferences.getInt("squaremile.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.SQUARE_MILE, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("squaremile.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("squaremile.cache.static.low", 0), j, SquareMileImpl.class);
      return;
    }
  }

  public SquareMileImpl(double paramDouble)
  {
    super(paramDouble, Area.INSTANCE, UnitIdentifier.SQUARE_MILE, SquareMile.class, dynamicCache, staticCache);
  }

  protected SquareMile _new_instance(double paramDouble)
  {
    return new SquareMileImpl(paramDouble);
  }

  protected SquareMile _this()
  {
    return this;
  }

  public SquareMile fromBase(SquareMeter paramSquareMeter)
  {
    return (SquareMile)valueOf(paramSquareMeter.getValue() / Constants.SQM_PER_SQMILE);
  }

  public UnitId<Length, Meter, Mile> getComponentUnit1Id()
  {
    return UnitIdentifier.MILE;
  }

  public UnitId<Length, Meter, Mile> getComponentUnit2Id()
  {
    return UnitIdentifier.MILE;
  }

  public SquareMeter toBase()
  {
    return FactoryArea.squareMeter(this.value * Constants.SQM_PER_SQMILE);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f mi²", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.SquareMileImpl
 * JD-Core Version:    0.6.2
 */