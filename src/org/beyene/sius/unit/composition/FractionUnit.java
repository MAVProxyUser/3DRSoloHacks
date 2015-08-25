package org.beyene.sius.unit.composition;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.dimension.composition.util.Fraction;
import org.beyene.sius.unit.Unit;

public abstract interface FractionUnit<DIM_NUMERATOR extends Dimension<DIM_NUMERATOR>, BASE_NUMERATOR extends Unit<DIM_NUMERATOR, BASE_NUMERATOR, BASE_NUMERATOR>, UNIT_NUMERATOR extends Unit<DIM_NUMERATOR, BASE_NUMERATOR, UNIT_NUMERATOR>, DIM_DENOMINATOR extends Dimension<DIM_DENOMINATOR>, BASE_DENOMINATOR extends Unit<DIM_DENOMINATOR, BASE_DENOMINATOR, BASE_DENOMINATOR>, UNIT_DENOMINATOR extends Unit<DIM_DENOMINATOR, BASE_DENOMINATOR, UNIT_DENOMINATOR>, DIM_FRACTION extends Fraction<DIM_NUMERATOR, DIM_DENOMINATOR, DIM_FRACTION>, BASE_F extends Unit<DIM_FRACTION, BASE_F, BASE_F>, UNIT_FRACTION extends FractionUnit<DIM_NUMERATOR, BASE_NUMERATOR, UNIT_NUMERATOR, DIM_DENOMINATOR, BASE_DENOMINATOR, UNIT_DENOMINATOR, DIM_FRACTION, BASE_F, UNIT_FRACTION>> extends CompositeUnit<DIM_NUMERATOR, BASE_NUMERATOR, UNIT_NUMERATOR, DIM_DENOMINATOR, BASE_DENOMINATOR, UNIT_DENOMINATOR, DIM_FRACTION, BASE_F, UNIT_FRACTION>
{
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.composition.FractionUnit
 * JD-Core Version:    0.6.2
 */