package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.cache.Caches;
import org.beyene.sius.dimension.Mass;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.mass.KiloGram;
import org.beyene.sius.util.Preferences;

final class KiloGramImpl extends AbstractUnit<Mass, KiloGram, KiloGram>
  implements KiloGram
{
  private static final transient Cache<Mass, KiloGram, KiloGram> dynamicCache;
  private static final transient StaticCache<Mass, KiloGram, KiloGram> staticCache = null;

  static
  {
    int i = Preferences.getInt("kg.cache.dynamic.size", 0);
    if (i > 0);
    for (dynamicCache = Caches.newInstance(UnitIdentifier.KILOGRAM, Math.abs(i)); ; dynamicCache = null)
    {
      int j = Preferences.getInt("kg.cache.static.size", 1);
      if (j <= 0)
        break;
      staticCache = new StaticCache(Preferences.getInt("kg.cache.static.low", 0), j, KiloGramImpl.class);
      return;
    }
  }

  public KiloGramImpl(double paramDouble)
  {
    super(paramDouble, Mass.INSTANCE, UnitIdentifier.KILOGRAM, KiloGram.class, dynamicCache, staticCache);
  }

  protected KiloGram _new_instance(double paramDouble)
  {
    return new KiloGramImpl(paramDouble);
  }

  protected KiloGram _this()
  {
    return this;
  }

  public KiloGram fromBase(KiloGram paramKiloGram)
  {
    return (KiloGram)valueOf(paramKiloGram.getValue());
  }

  public KiloGram toBase()
  {
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.KiloGramImpl
 * JD-Core Version:    0.6.2
 */