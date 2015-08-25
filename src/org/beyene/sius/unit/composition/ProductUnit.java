package org.beyene.sius.unit.composition;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.dimension.composition.util.Product;
import org.beyene.sius.unit.Unit;

public abstract interface ProductUnit<DIM_FACTOR1 extends Dimension<DIM_FACTOR1>, BASE_FACTOR1 extends Unit<DIM_FACTOR1, BASE_FACTOR1, BASE_FACTOR1>, UNIT_FACTOR1 extends Unit<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1>, DIM_FACTOR2 extends Dimension<DIM_FACTOR2>, BASE_FACTOR2 extends Unit<DIM_FACTOR2, BASE_FACTOR2, BASE_FACTOR2>, UNIT_FACTOR2 extends Unit<DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2>, DIM_PRODUCT extends Product<DIM_FACTOR1, DIM_FACTOR2, DIM_PRODUCT>, BASE_P extends Unit<DIM_PRODUCT, BASE_P, BASE_P>, UNIT_PRODUCT extends ProductUnit<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1, DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2, DIM_PRODUCT, BASE_P, UNIT_PRODUCT>> extends CompositeUnit<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1, DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2, DIM_PRODUCT, BASE_P, UNIT_PRODUCT>
{
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.composition.ProductUnit
 * JD-Core Version:    0.6.2
 */