package org.beyene.sius.dimension.composition;

import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.Time;
import org.beyene.sius.dimension.composition.util.Fraction;

public enum Speed
  implements Fraction<Length, Time, Speed>
{
  static
  {
    Speed[] arrayOfSpeed = new Speed[1];
    arrayOfSpeed[0] = INSTANCE;
  }

  public Time getDenominator()
  {
    return Time.INSTANCE;
  }

  public Length getNumerator()
  {
    return Length.INSTANCE;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.dimension.composition.Speed
 * JD-Core Version:    0.6.2
 */