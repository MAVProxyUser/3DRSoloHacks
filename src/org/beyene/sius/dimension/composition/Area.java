package org.beyene.sius.dimension.composition;

import org.beyene.sius.dimension.Length;
import org.beyene.sius.dimension.composition.util.Product;

public enum Area
  implements Product<Length, Length, Area>
{
  static
  {
    Area[] arrayOfArea = new Area[1];
    arrayOfArea[0] = INSTANCE;
  }

  public Length getFactor1()
  {
    return Length.INSTANCE;
  }

  public Length getFactor2()
  {
    return Length.INSTANCE;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.dimension.composition.Area
 * JD-Core Version:    0.6.2
 */