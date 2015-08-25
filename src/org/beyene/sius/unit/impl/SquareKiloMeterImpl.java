package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.composition.Area;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.area.Constants;
import org.beyene.sius.unit.composition.area.SquareKiloMeter;
import org.beyene.sius.unit.composition.area.SquareMeter;
import org.beyene.sius.unit.length.KiloMeter;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.util.Preferences;

public class SquareKiloMeterImpl extends AbstractUnit<Area, SquareMeter, SquareKiloMeter>
  implements SquareKiloMeter
{
  private static final transient Cache<Area, SquareMeter, SquareKiloMeter> dynamicCache;
  private static final transient StaticCache<Area, SquareMeter, SquareKiloMeter> staticCache = null;

  static
  {
    int i = Preferences.getInt("squarekilometer.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.SQUARE_KILOMETER, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("squarekilometer.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("squarekilometer.cache.static.low", 0), j, SquareKiloMeterImpl.class);
      return;
    }
  }

  public SquareKiloMeterImpl(double paramDouble)
  {
    super(paramDouble, Area.INSTANCE, UnitIdentifier.SQUARE_KILOMETER, SquareKiloMeter.class, dynamicCache, staticCache);
  }

  protected SquareKiloMeter _new_instance(double paramDouble)
  {
    return new SquareKiloMeterImpl(paramDouble);
  }

  protected SquareKiloMeter _this()
  {
    return this;
  }

  public SquareKiloMeter fromBase(SquareMeter paramSquareMeter)
  {
    return (SquareKiloMeter)valueOf(paramSquareMeter.getValue() / Constants.SQM_PER_SQKM);
  }

  public UnitId<Length, Meter, KiloMeter> getComponentUnit1Id()
  {
    return UnitIdentifier.KILOMETER;
  }

  public UnitId<Length, Meter, KiloMeter> getComponentUnit2Id()
  {
    return UnitIdentifier.KILOMETER;
  }

  public SquareMeter toBase()
  {
    return FactoryArea.squareMeter(this.value * Constants.SQM_PER_SQKM);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f km²", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.SquareKiloMeterImpl
 * JD-Core Version:    0.6.2
 */