package org.beyene.sius.unit;

import org.beyene.sius.dimension.Dimension;

public abstract interface Unit<D extends Dimension<D>, BASE extends Unit<D, BASE, BASE>, SELF extends Unit<D, BASE, SELF>>
{
  public abstract boolean equals(Object paramObject);

  public abstract SELF fromBase(BASE paramBASE);

  public abstract D getDimension();

  public abstract UnitId<D, BASE, SELF> getIdentifier();

  public abstract double getValue();

  public abstract int hashCode();

  public abstract BASE toBase();

  public abstract String toString();

  public abstract SELF valueOf(double paramDouble);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.Unit
 * JD-Core Version:    0.6.2
 */