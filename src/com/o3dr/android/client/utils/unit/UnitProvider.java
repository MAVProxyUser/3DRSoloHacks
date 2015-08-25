package com.o3dr.android.client.utils.unit;

public abstract interface UnitProvider
{
  public static final String SQUARE_SYMBOL = "²";

  public abstract String areaToString(double paramDouble);

  public abstract String distanceToString(double paramDouble);

  public abstract String electricChargeToString(double paramDouble);

  public abstract String speedToString(double paramDouble);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.utils.unit.UnitProvider
 * JD-Core Version:    0.6.2
 */