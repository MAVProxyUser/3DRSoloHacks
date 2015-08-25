package org.beyene.sius.dimension.composition.util;

import org.beyene.sius.dimension.Dimension;

public abstract interface Fraction<NUMERATOR extends Dimension<NUMERATOR>, DENOMINATOR extends Dimension<DENOMINATOR>, F extends Fraction<NUMERATOR, DENOMINATOR, F>> extends Composition<NUMERATOR, DENOMINATOR, F>
{
  public abstract DENOMINATOR getDenominator();

  public abstract NUMERATOR getNumerator();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.dimension.composition.util.Fraction
 * JD-Core Version:    0.6.2
 */