package org.beyene.sius.unit.impl;

import org.beyene.sius.unit.mass.KiloGram;
import org.beyene.sius.unit.mass.Pound;

public final class FactoryMass
{
  private static final KiloGram kg = (KiloGram)new KiloGramImpl(0.0D).valueOf(0.0D);
  private static final Pound pound = (Pound)new PoundImpl(0.0D).valueOf(0.0D);

  public static KiloGram kg(double paramDouble)
  {
    return (KiloGram)kg.valueOf(paramDouble);
  }

  public static Pound lb(double paramDouble)
  {
    return (Pound)pound.valueOf(paramDouble);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.FactoryMass
 * JD-Core Version:    0.6.2
 */