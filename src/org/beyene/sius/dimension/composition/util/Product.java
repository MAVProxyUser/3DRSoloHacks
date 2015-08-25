package org.beyene.sius.dimension.composition.util;

import org.beyene.sius.dimension.Dimension;

public abstract interface Product<FACTOR1 extends Dimension<FACTOR1>, FACTOR2 extends Dimension<FACTOR2>, PRODUCT extends Product<FACTOR1, FACTOR2, PRODUCT>> extends Composition<FACTOR1, FACTOR2, PRODUCT>
{
  public abstract FACTOR1 getFactor1();

  public abstract FACTOR2 getFactor2();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.dimension.composition.util.Product
 * JD-Core Version:    0.6.2
 */