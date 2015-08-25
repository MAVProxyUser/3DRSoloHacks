package com.o3dr.solo.android.widget.spinnerWheel.adapters;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.length.LengthUnit;

public class LengthWheelAdapter extends AbstractWheelTextAdapter<LengthUnit>
{
  private final List<LengthUnit> unitsList = new ArrayList();

  public LengthWheelAdapter(Context paramContext, int paramInt1, int paramInt2, LengthUnit paramLengthUnit1, LengthUnit paramLengthUnit2)
  {
    super(paramContext, paramInt1, paramInt2);
    generateUnits(paramLengthUnit1, paramLengthUnit2);
  }

  public LengthWheelAdapter(Context paramContext, int paramInt, LengthUnit paramLengthUnit1, LengthUnit paramLengthUnit2)
  {
    super(paramContext, paramInt);
    generateUnits(paramLengthUnit1, paramLengthUnit2);
  }

  private void generateUnits(LengthUnit paramLengthUnit1, LengthUnit paramLengthUnit2)
  {
    if (!paramLengthUnit1.getClass().equals(paramLengthUnit2.getClass()))
      paramLengthUnit2 = (LengthUnit)Operation.convert(paramLengthUnit2, paramLengthUnit1.getIdentifier());
    int i = (int)Math.round(paramLengthUnit1.getValue());
    int j = (int)Math.round(paramLengthUnit2.getValue());
    if (i > j)
      throw new IllegalArgumentException("Starting value must be less or equal to the ending value");
    this.unitsList.clear();
    for (int k = i; k <= j; k++)
      this.unitsList.add((LengthUnit)paramLengthUnit1.valueOf(k));
  }

  public LengthUnit getItem(int paramInt)
  {
    return (LengthUnit)this.unitsList.get(paramInt);
  }

  public int getItemIndex(LengthUnit paramLengthUnit)
  {
    LengthUnit localLengthUnit = (LengthUnit)paramLengthUnit.valueOf(Math.round(paramLengthUnit.getValue()));
    return this.unitsList.indexOf(localLengthUnit);
  }

  protected CharSequence getItemText(int paramInt)
  {
    return ((LengthUnit)this.unitsList.get(paramInt)).toString();
  }

  public int getItemsCount()
  {
    return this.unitsList.size();
  }

  public LengthUnit parseItemText(CharSequence paramCharSequence)
  {
    String str = paramCharSequence.toString();
    if (TextUtils.isEmpty(str))
      return (LengthUnit)((LengthUnit)this.unitsList.get(0)).valueOf(0.0D);
    return (LengthUnit)((LengthUnit)this.unitsList.get(0)).valueOf(Double.parseDouble(str));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.adapters.LengthWheelAdapter
 * JD-Core Version:    0.6.2
 */