package org.beyene.sius.unit.time;

import org.beyene.sius.dimension.Time;
import org.beyene.sius.unit.Unit;

abstract interface TimeUnit<U extends TimeUnit<U>> extends Unit<Time, Second, U>
{
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.time.TimeUnit
 * JD-Core Version:    0.6.2
 */