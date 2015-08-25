package org.beyene.sius.unit.composition;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.dimension.composition.util.Composition;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;

public abstract interface CompositeUnitId<DIM_C1 extends Dimension<DIM_C1>, BASE_C1 extends Unit<DIM_C1, BASE_C1, BASE_C1>, UNIT_C1 extends Unit<DIM_C1, BASE_C1, UNIT_C1>, DIM_C2 extends Dimension<DIM_C2>, BASE_C2 extends Unit<DIM_C2, BASE_C2, BASE_C2>, UNIT_C2 extends Unit<DIM_C2, BASE_C2, UNIT_C2>, DIM_COMPOSITION extends Composition<DIM_C1, DIM_C2, DIM_COMPOSITION>, BASE_COMPOSITION extends Unit<DIM_COMPOSITION, BASE_COMPOSITION, BASE_COMPOSITION>, COMPOSITE_UNIT extends Unit<DIM_COMPOSITION, BASE_COMPOSITION, COMPOSITE_UNIT>> extends UnitId<DIM_COMPOSITION, BASE_COMPOSITION, COMPOSITE_UNIT>
{
  public abstract UnitId<DIM_C1, BASE_C1, UNIT_C1> getComponentUnit1Id();

  public abstract UnitId<DIM_C2, BASE_C2, UNIT_C2> getComponentUnit2Id();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.composition.CompositeUnitId
 * JD-Core Version:    0.6.2
 */