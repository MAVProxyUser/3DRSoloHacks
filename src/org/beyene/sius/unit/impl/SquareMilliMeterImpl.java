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
import org.beyene.sius.unit.composition.area.SquareMilliMeter;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.unit.length.MilliMeter;
import org.beyene.sius.util.Preferences;

public class SquareMilliMeterImpl extends AbstractUnit<Area, SquareMeter, SquareMilliMeter>
  implements SquareMilliMeter
{
  private static final transient Cache<Area, SquareMeter, SquareMilliMeter> dynamicCache;
  private static final transient StaticCache<Area, SquareMeter, SquareMilliMeter> staticCache = null;

  static
  {
    int i = Preferences.getInt("squaremillimeter.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.SQUARE_MILLIMETER, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("squaremillimeter.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("squaremillimeter.cache.static.low", 0), j, SquareMilliMeterImpl.class);
      return;
    }
  }

  public SquareMilliMeterImpl(double paramDouble)
  {
    super(paramDouble, Area.INSTANCE, UnitIdentifier.SQUARE_MILLIMETER, SquareMilliMeter.class, dynamicCache, staticCache);
  }

  protected SquareMilliMeter _new_instance(double paramDouble)
  {
    return new SquareMilliMeterImpl(paramDouble);
  }

  protected SquareMilliMeter _this()
  {
    return this;
  }

  public SquareMilliMeter fromBase(SquareMeter paramSquareMeter)
  {
    return (SquareMilliMeter)valueOf(paramSquareMeter.getValue() / Constants.SQM_PER_SQMM);
  }

  public UnitId<Length, Meter, MilliMeter> getComponentUnit1Id()
  {
    return UnitIdentifier.MILLIMETER;
  }

  public UnitId<Length, Meter, MilliMeter> getComponentUnit2Id()
  {
    return UnitIdentifier.MILLIMETER;
  }

  public SquareMeter toBase()
  {
    return FactoryArea.squareMeter(this.value * Constants.SQM_PER_SQMM);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f mm²", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.SquareMilliMeterImpl
 * JD-Core Version:    0.6.2
 */