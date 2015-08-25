package org.beyene.sius.unit.composition.speed;

import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.Time;
import org.beyene.sius.dimension.composition.Speed;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.composition.FractionUnit;
import org.beyene.sius.unit.length.Meter;
import org.beyene.sius.unit.time.Second;

public abstract interface SpeedUnit<NUMERATOR extends Unit<Length, Meter, NUMERATOR>, DENOMINATOR extends Unit<Time, Second, DENOMINATOR>, T extends SpeedUnit<NUMERATOR, DENOMINATOR, T>> extends FractionUnit<Length, Meter, NUMERATOR, Time, Second, DENOMINATOR, Speed, MeterPerSecond, T>
{
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.composition.speed.SpeedUnit
 * JD-Core Version:    0.6.2
 */