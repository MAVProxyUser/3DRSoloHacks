package org.beyene.sius.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;

public final class Caches
{
  private static String DEFAULT_PROVIDER_NAME;
  private static Map<String, Provider> providers = new ConcurrentHashMap();

  static
  {
    DEFAULT_PROVIDER_NAME = "<def>";
    providers.put(DEFAULT_PROVIDER_NAME, new SimpleProvider());
  }

  public static <D extends Dimension<D>, B extends Unit<D, B, B>, U extends Unit<D, B, U>> Cache<D, B, U> newInstance(String paramString, UnitId<D, B, U> paramUnitId)
  {
    new UnsupportedOperationException("No implementation provided yet!");
    return null;
  }

  public static <D extends Dimension<D>, B extends Unit<D, B, B>, U extends Unit<D, B, U>> Cache<D, B, U> newInstance(UnitId<D, B, U> paramUnitId, int paramInt)
  {
    return ((Provider)providers.get(DEFAULT_PROVIDER_NAME)).newInstance(paramUnitId, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.cache.Caches
 * JD-Core Version:    0.6.2
 */