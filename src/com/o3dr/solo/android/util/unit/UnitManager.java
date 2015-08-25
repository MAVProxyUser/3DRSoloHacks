package com.o3dr.solo.android.util.unit;

import android.content.Context;
import com.o3dr.solo.android.util.AppPreferences;
import com.o3dr.solo.android.util.unit.systems.ImperialUnitSystem;
import com.o3dr.solo.android.util.unit.systems.MetricUnitSystem;
import com.o3dr.solo.android.util.unit.systems.UnitSystem;
import java.util.Locale;

public class UnitManager
{
  private static AppPreferences appPrefs;
  private static ImperialUnitSystem imperialUnitSystem;
  private static MetricUnitSystem metricUnitSystem;

  public static UnitSystem getUnitSystem(Context paramContext)
  {
    if (appPrefs == null)
      appPrefs = new AppPreferences(paramContext);
    switch (appPrefs.getUnitSystemType())
    {
    default:
      Locale localLocale = Locale.getDefault();
      if (Locale.US.equals(localLocale))
      {
        if (imperialUnitSystem == null)
          imperialUnitSystem = new ImperialUnitSystem();
        return imperialUnitSystem;
      }
      if (metricUnitSystem == null)
        metricUnitSystem = new MetricUnitSystem();
      return metricUnitSystem;
    case 1:
      if (metricUnitSystem == null)
        metricUnitSystem = new MetricUnitSystem();
      return metricUnitSystem;
    case 2:
    }
    if (imperialUnitSystem == null)
      imperialUnitSystem = new ImperialUnitSystem();
    return imperialUnitSystem;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.UnitManager
 * JD-Core Version:    0.6.2
 */