package org.beyene.sius.unit.impl;

import java.util.Locale;
import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Length;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.length.Inch;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.util.Preferences;

final class InchImpl extends AbstractUnit<Length, Meter, Inch>
  implements Inch
{
  private static final transient Cache<Length, Meter, Inch> dynamicCache;
  private static final transient StaticCache<Length, Meter, Inch> staticCache = null;

  static
  {
    int i = Preferences.getInt("inch.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.INCH, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("inch.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("inch.cache.static.low", 0), j, InchImpl.class);
      return;
    }
  }

  public InchImpl(double paramDouble)
  {
    super(paramDouble, Length.INSTANCE, UnitIdentifier.INCH, Inch.class, dynamicCache, staticCache);
  }

  protected Inch _new_instance(double paramDouble)
  {
    return new InchImpl(paramDouble);
  }

  protected Inch _this()
  {
    return this;
  }

  public Inch fromBase(Meter paramMeter)
  {
    return (Inch)valueOf(paramMeter.getValue() / 0.0254D);
  }

  public Meter toBase()
  {
    return FactoryLength.meter(0.0254D * this.value);
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(this.value);
    return String.format(localLocale, "%2.1f in", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.InchImpl
 * JD-Core Version:    0.6.2
 */