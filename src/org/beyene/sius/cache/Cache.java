package org.beyene.sius.cache;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;

public abstract interface Cache<D extends Dimension<D>, B extends Unit<D, B, B>, U extends Unit<D, B, U>>
{
  public abstract U lookUp(double paramDouble);

  public abstract boolean put(U paramU);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.cache.Cache
 * JD-Core Version:    0.6.2
 */