package org.beyene.sius.dimension.composition.util;

import org.beyene.sius.dimension.Dimension;

public abstract interface Composition<C1 extends Dimension<C1>, C2 extends Dimension<C2>, COMPOSITION extends Composition<C1, C2, COMPOSITION>> extends Dimension<COMPOSITION>
{
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.dimension.composition.util.Composition
 * JD-Core Version:    0.6.2
 */