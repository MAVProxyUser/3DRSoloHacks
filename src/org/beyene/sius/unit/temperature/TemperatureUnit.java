package org.beyene.sius.unit.temperature;

import org.beyene.sius.dimension.Temperature;
import org.beyene.sius.unit.Unit;

abstract interface TemperatureUnit<U extends TemperatureUnit<U>> extends Unit<Temperature, Kelvin, U>
{
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.temperature.TemperatureUnit
 * JD-Core Version:    0.6.2
 */