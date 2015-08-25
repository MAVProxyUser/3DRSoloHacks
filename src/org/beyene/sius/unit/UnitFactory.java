package org.beyene.sius.unit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.impl.FactoryArea;
import org.beyene.sius.unit.impl.FactoryLength;
import org.beyene.sius.unit.impl.FactoryMass;
import org.beyene.sius.unit.impl.FactorySpeed;
import org.beyene.sius.unit.impl.FactoryTemperature;
import org.beyene.sius.unit.impl.FactoryTime;

public final class UnitFactory
{
  private static final Map<UnitId<?, ?, ?>, Unit<?, ?, ?>> instanceMap = Collections.unmodifiableMap(instanceMapMutable);
  private static final Map<UnitId<?, ?, ?>, Unit<?, ?, ?>> instanceMapMutable = new HashMap();

  static
  {
    instanceMapMutable.put(UnitIdentifier.KILOGRAM, FactoryMass.kg(0.0D));
    instanceMapMutable.put(UnitIdentifier.POUND, FactoryMass.lb(0.0D));
    instanceMapMutable.put(UnitIdentifier.KILOMETER, FactoryLength.kilometer(0.0D));
    instanceMapMutable.put(UnitIdentifier.MILLIMETER, FactoryLength.millimeter(0.0D));
    instanceMapMutable.put(UnitIdentifier.METER, FactoryLength.meter(0.0D));
    instanceMapMutable.put(UnitIdentifier.MILE, FactoryLength.mile(0.0D));
    instanceMapMutable.put(UnitIdentifier.YARD, FactoryLength.yard(0.0D));
    instanceMapMutable.put(UnitIdentifier.FOOT, FactoryLength.foot(0.0D));
    instanceMapMutable.put(UnitIdentifier.INCH, FactoryLength.inch(0.0D));
    instanceMapMutable.put(UnitIdentifier.SECOND, FactoryTime.second(0.0D));
    instanceMapMutable.put(UnitIdentifier.MINUTE, FactoryTime.minute(0.0D));
    instanceMapMutable.put(UnitIdentifier.HOUR, FactoryTime.hour(0.0D));
    instanceMapMutable.put(UnitIdentifier.KELVIN, FactoryTemperature.kelvin(0.0D));
    instanceMapMutable.put(UnitIdentifier.CELSIUS, FactoryTemperature.celsius(0.0D));
    instanceMapMutable.put(UnitIdentifier.FAHRENHEIT, FactoryTemperature.fahrenheit(0.0D));
    instanceMapMutable.put(UnitIdentifier.SQUARE_MILLIMETER, FactoryArea.squareMilliMeter(0.0D));
    instanceMapMutable.put(UnitIdentifier.SQUARE_INCH, FactoryArea.squareInch(0.0D));
    instanceMapMutable.put(UnitIdentifier.SQUARE_METER, FactoryArea.squareMeter(0.0D));
    instanceMapMutable.put(UnitIdentifier.SQUARE_FOOT, FactoryArea.squareFoot(0.0D));
    instanceMapMutable.put(UnitIdentifier.SQUARE_KILOMETER, FactoryArea.squareKiloMeter(0.0D));
    instanceMapMutable.put(UnitIdentifier.SQUARE_MILE, FactoryArea.squareMile(0.0D));
    instanceMapMutable.put(UnitIdentifier.METER_PER_SECOND, FactorySpeed.mps(0.0D));
    instanceMapMutable.put(UnitIdentifier.MILES_PER_HOUR, FactorySpeed.mph(0.0D));
    instanceMapMutable.put(UnitIdentifier.FOOT_PER_SECOND, FactorySpeed.ftps(0.0D));
  }

  public static <D extends Dimension<D>, B extends Unit<D, B, B>, U extends Unit<D, B, U>, UID extends UnitId<D, B, U>> U valueOf(double paramDouble, UID paramUID)
  {
    Unit localUnit = (Unit)instanceMap.get(paramUID);
    if (localUnit == null)
      throw new UnsupportedUnitException(paramUID);
    return localUnit.valueOf(paramDouble);
  }

  public static class UnsupportedUnitException extends RuntimeException
  {
    private static final long serialVersionUID = 1L;

    public UnsupportedUnitException(UnitId<?, ?, ?> paramUnitId)
    {
      super();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.UnitFactory
 * JD-Core Version:    0.6.2
 */