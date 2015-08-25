package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Mass;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.mass.KiloGram;
import org.beyene.sius.unit.mass.Pound;
import org.beyene.sius.util.Preferences;

final class PoundImpl extends AbstractUnit<Mass, KiloGram, Pound>
  implements Pound
{
  private static final transient Cache<Mass, KiloGram, Pound> dynamicCache;
  private static final transient StaticCache<Mass, KiloGram, Pound> staticCache = null;

  static
  {
    int i = Preferences.getInt("pound.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.POUND, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("pound.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("pound.cache.static.low", 0), j, PoundImpl.class);
      return;
    }
  }

  public PoundImpl(double paramDouble)
  {
    super(paramDouble, Mass.INSTANCE, UnitIdentifier.POUND, Pound.class, dynamicCache, staticCache);
  }

  protected Pound _new_instance(double paramDouble)
  {
    return new PoundImpl(paramDouble);
  }

  protected Pound _this()
  {
    return this;
  }

  public Pound fromBase(KiloGram paramKiloGram)
  {
    return (Pound)valueOf(paramKiloGram.getValue() / 0.45359237D);
  }

  public KiloGram toBase()
  {
    return FactoryMass.kg(0.45359237D * this.value);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.PoundImpl
 * JD-Core Version:    0.6.2
 */