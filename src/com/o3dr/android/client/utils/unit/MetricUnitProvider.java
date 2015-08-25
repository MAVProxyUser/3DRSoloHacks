package com.o3dr.android.client.utils.unit;

import java.util.Locale;

public class MetricUnitProvider
  implements UnitProvider
{
  public String areaToString(double paramDouble)
  {
    double d = Math.abs(paramDouble);
    if (d >= 100000.0D)
    {
      Locale localLocale3 = Locale.US;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Double.valueOf(paramDouble / 1000000.0D);
      return String.format(localLocale3, "%2.1f km²", arrayOfObject3);
    }
    if (d >= 1.0D)
    {
      Locale localLocale2 = Locale.US;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Double.valueOf(paramDouble);
      return String.format(localLocale2, "%2.1f m²", arrayOfObject2);
    }
    if (d >= 1.E-05D)
    {
      Locale localLocale1 = Locale.US;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Double.valueOf(10000.0D * paramDouble);
      return String.format(localLocale1, "%2.2f cm²", arrayOfObject1);
    }
    return paramDouble + " m" + "²";
  }

  public String distanceToString(double paramDouble)
  {
    double d = Math.abs(paramDouble);
    if (d >= 1000.0D)
    {
      Locale localLocale3 = Locale.US;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Double.valueOf(paramDouble / 1000.0D);
      return String.format(localLocale3, "%2.1f km", arrayOfObject3);
    }
    if (d >= 0.1D)
    {
      Locale localLocale2 = Locale.US;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Double.valueOf(paramDouble);
      return String.format(localLocale2, "%2.1f m", arrayOfObject2);
    }
    if (d >= 0.001D)
    {
      Locale localLocale1 = Locale.US;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Double.valueOf(1000.0D * paramDouble);
      return String.format(localLocale1, "%2.1f mm", arrayOfObject1);
    }
    return paramDouble + " m";
  }

  public String electricChargeToString(double paramDouble)
  {
    if (Math.abs(paramDouble) >= 1000.0D)
    {
      Locale localLocale2 = Locale.US;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Double.valueOf(paramDouble / 1000.0D);
      return String.format(localLocale2, "%2.0f Ah", arrayOfObject2);
    }
    Locale localLocale1 = Locale.ENGLISH;
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Double.valueOf(paramDouble);
    return String.format(localLocale1, "%2.0f mAh", arrayOfObject1);
  }

  public String speedToString(double paramDouble)
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Double.valueOf(paramDouble);
    return String.format(localLocale, "%2.1f m/s", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.utils.unit.MetricUnitProvider
 * JD-Core Version:    0.6.2
 */