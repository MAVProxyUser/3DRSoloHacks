package org.beyene.sius.unit.composition.area;

import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.composition.Area;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.composition.ProductUnit;
import org.beyene.sius.unit.length.Meter;

public abstract interface AreaUnit<FACTOR1 extends Unit<Length, Meter, FACTOR1>, FACTOR2 extends Unit<Length, Meter, FACTOR2>, T extends AreaUnit<FACTOR1, FACTOR2, T>> extends ProductUnit<Length, Meter, FACTOR1, Length, Meter, FACTOR2, Area, SquareMeter, T>
{
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.composition.area.AreaUnit
 * JD-Core Version:    0.6.2
 */