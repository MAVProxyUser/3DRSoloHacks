package com.o3dr.solo.android.widget.spinnerWheel.adapters;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.composition.speed.SpeedUnit;

public class SpeedWheelAdapter extends AbstractWheelTextAdapter<SpeedUnit>
{
  private final List<SpeedUnit> unitsList = new ArrayList();

  public SpeedWheelAdapter(Context paramContext, int paramInt, SpeedUnit paramSpeedUnit1, SpeedUnit paramSpeedUnit2)
  {
    super(paramContext, paramInt);
    generateUnits(paramSpeedUnit1, paramSpeedUnit2);
  }

  private void generateUnits(SpeedUnit paramSpeedUnit1, SpeedUnit paramSpeedUnit2)
  {
    if (!paramSpeedUnit1.getClass().equals(paramSpeedUnit2.getClass()))
      paramSpeedUnit2 = (SpeedUnit)Operation.convert(paramSpeedUnit2, paramSpeedUnit1.getIdentifier());
    int i = (int)Math.round(paramSpeedUnit1.getValue());
    int j = (int)Math.round(paramSpeedUnit2.getValue());
    if (i > j)
      throw new IllegalArgumentException("Starting value must be less or equal to the ending value.");
    this.unitsList.clear();
    for (int k = i; k <= j; k++)
      this.unitsList.add((SpeedUnit)paramSpeedUnit1.valueOf(k));
  }

  public SpeedUnit getItem(int paramInt)
  {
    return (SpeedUnit)this.unitsList.get(paramInt);
  }

  public int getItemIndex(SpeedUnit paramSpeedUnit)
  {
    SpeedUnit localSpeedUnit = (SpeedUnit)paramSpeedUnit.valueOf(Math.round(paramSpeedUnit.getValue()));
    return this.unitsList.indexOf(localSpeedUnit);
  }

  protected CharSequence getItemText(int paramInt)
  {
    return ((SpeedUnit)this.unitsList.get(paramInt)).toString();
  }

  public int getItemsCount()
  {
    return this.unitsList.size();
  }

  public SpeedUnit parseItemText(CharSequence paramCharSequence)
  {
    String str = paramCharSequence.toString();
    if (TextUtils.isEmpty(str))
      return (SpeedUnit)((SpeedUnit)this.unitsList.get(0)).valueOf(0.0D);
    return (SpeedUnit)((SpeedUnit)this.unitsList.get(0)).valueOf(Double.parseDouble(str));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.adapters.SpeedWheelAdapter
 * JD-Core Version:    0.6.2
 */