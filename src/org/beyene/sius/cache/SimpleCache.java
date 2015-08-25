package org.beyene.sius.cache;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;

final class SimpleCache<D extends Dimension<D>, B extends Unit<D, B, B>, U extends Unit<D, B, U>>
  implements Cache<D, B, U>
{
  private final int capacity;
  private final Map<Double, U> map;

  SimpleCache(int paramInt)
  {
    this.capacity = paramInt;
    this.map = Collections.synchronizedMap(new WeakHashMap());
  }

  public U lookUp(double paramDouble)
  {
    return (Unit)this.map.get(Double.valueOf(paramDouble));
  }

  public boolean put(U paramU)
  {
    if ((this.map.size() >= this.capacity) && (!this.map.containsKey(Double.valueOf(paramU.getValue()))))
      return false;
    this.map.put(Double.valueOf(paramU.getValue()), paramU);
    return true;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.cache.SimpleCache
 * JD-Core Version:    0.6.2
 */