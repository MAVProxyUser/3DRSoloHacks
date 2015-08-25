package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.composition.Area;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.area.Constants;
import org.beyene.sius.unit.composition.area.SquareInch;
import org.beyene.sius.unit.composition.area.SquareMeter;
import org.beyene.sius.unit.length.Inch;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.util.Preferences;

public class SquareInchImpl extends AbstractUnit<Area, SquareMeter, SquareInch>
  implements SquareInch
{
  private static final transient Cache<Area, SquareMeter, SquareInch> dynamicCache;
  private static final transient StaticCache<Area, SquareMeter, SquareInch> staticCache = null;

  static
  {
    int i = Preferences.getInt("squareinch.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.SQUARE_INCH, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("squareinch.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("squareinch.cache.static.low", 0), j, SquareInchImpl.class);
      return;
    }
  }

  public SquareInchImpl(double paramDouble)
  {
    super(paramDouble, Area.INSTANCE, UnitIdentifier.SQUARE_INCH, SquareInch.class, dynamicCache, staticCache);
  }

  protected SquareInch _new_instance(double paramDouble)
  {
    return new SquareInchImpl(paramDouble);
  }

  protected SquareInch _this()
  {
    return this;
  }

  public SquareInch fromBase(SquareMeter paramSquareMeter)
  {
    return (SquareInch)valueOf(paramSquareMeter.getValue() / Constants.SQM_PER_SQINCH);
  }

  public UnitId<Length, Meter, Inch> getComponentUnit1Id()
  {
    return UnitIdentifier.INCH;
  }

  public UnitId<Length, Meter, Inch> getComponentUnit2Id()
  {
    return UnitIdentifier.INCH;
  }

  public SquareMeter toBase()
  {
    return FactoryArea.squareMeter(this.value * Constants.SQM_PER_SQINCH);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.2f in²", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.SquareInchImpl
 * JD-Core Version:    0.6.2
 */