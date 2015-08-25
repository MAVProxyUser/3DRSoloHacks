package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.composition.Area;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.area.Constants;
import org.beyene.sius.unit.composition.area.SquareFoot;
import org.beyene.sius.unit.composition.area.SquareMeter;
import org.beyene.sius.unit.length.Foot;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.util.Preferences;

public class SquareFootImpl extends AbstractUnit<Area, SquareMeter, SquareFoot>
  implements SquareFoot
{
  private static final transient Cache<Area, SquareMeter, SquareFoot> dynamicCache;
  private static final transient StaticCache<Area, SquareMeter, SquareFoot> staticCache = null;

  static
  {
    int i = Preferences.getInt("squarefoot.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.SQUARE_FOOT, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("squarefoot.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("squarefoot.cache.static.low", 0), j, SquareFootImpl.class);
      return;
    }
  }

  public SquareFootImpl(double paramDouble)
  {
    super(paramDouble, Area.INSTANCE, UnitIdentifier.SQUARE_FOOT, SquareFoot.class, dynamicCache, staticCache);
  }

  protected SquareFoot _new_instance(double paramDouble)
  {
    return new SquareFootImpl(paramDouble);
  }

  protected SquareFoot _this()
  {
    return this;
  }

  public SquareFoot fromBase(SquareMeter paramSquareMeter)
  {
    return (SquareFoot)valueOf(paramSquareMeter.getValue() / Constants.SQM_PER_SQFOOT);
  }

  public UnitId<Length, Meter, Foot> getComponentUnit1Id()
  {
    return UnitIdentifier.FOOT;
  }

  public UnitId<Length, Meter, Foot> getComponentUnit2Id()
  {
    return UnitIdentifier.FOOT;
  }

  public SquareMeter toBase()
  {
    return FactoryArea.squareMeter(this.value * Constants.SQM_PER_SQFOOT);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f ft²", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.SquareFootImpl
 * JD-Core Version:    0.6.2
 */