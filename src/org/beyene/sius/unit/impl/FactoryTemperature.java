package org.beyene.sius.unit.impl;

import org.beyene.sius.unit.temperature.Celsius;
import org.beyene.sius.unit.temperature.Fahrenheit;
import org.beyene.sius.unit.temperature.Kelvin;

public final class FactoryTemperature
{
  private static final Celsius celsius = (Celsius)new CelsiusImpl(0.0D).valueOf(0.0D);
  private static final Fahrenheit fahrenheit = (Fahrenheit)new FahrenheitImpl(0.0D).valueOf(0.0D);
  private static final Kelvin kelvin = (Kelvin)new KelvinImpl(0.0D).valueOf(0.0D);

  public static Celsius celsius(double paramDouble)
  {
    return (Celsius)celsius.valueOf(paramDouble);
  }

  public static Fahrenheit fahrenheit(double paramDouble)
  {
    return (Fahrenheit)fahrenheit.valueOf(paramDouble);
  }

  public static Kelvin kelvin(double paramDouble)
  {
    return (Kelvin)kelvin.valueOf(paramDouble);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.FactoryTemperature
 * JD-Core Version:    0.6.2
 */