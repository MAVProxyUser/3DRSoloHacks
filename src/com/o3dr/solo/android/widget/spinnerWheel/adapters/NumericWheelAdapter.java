package com.o3dr.solo.android.widget.spinnerWheel.adapters;

import android.content.Context;
import android.text.TextUtils;

public class NumericWheelAdapter extends AbstractWheelTextAdapter<Integer>
{
  public static final int DEFAULT_MAX_VALUE = 9;
  private static final int DEFAULT_MIN_VALUE;
  private String format;
  private int maxValue;
  private int minValue;

  public NumericWheelAdapter(Context paramContext)
  {
    this(paramContext, 0, 9);
  }

  public NumericWheelAdapter(Context paramContext, int paramInt1, int paramInt2)
  {
    this(paramContext, paramInt1, paramInt2, null);
  }

  public NumericWheelAdapter(Context paramContext, int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    super(paramContext, paramInt1);
    this.minValue = paramInt2;
    this.maxValue = paramInt3;
    this.format = paramString;
  }

  public NumericWheelAdapter(Context paramContext, int paramInt1, int paramInt2, String paramString)
  {
    this(paramContext, -1, paramInt1, paramInt2, paramString);
  }

  public Integer getItem(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < getItemsCount()))
      return Integer.valueOf(paramInt + this.minValue);
    throw new IllegalArgumentException("Index is out of range.");
  }

  public int getItemIndex(Integer paramInteger)
  {
    if ((paramInteger.intValue() < this.minValue) || (paramInteger.intValue() > this.maxValue))
      return -1;
    return paramInteger.intValue() - this.minValue;
  }

  public CharSequence getItemText(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < getItemsCount()))
    {
      int i = paramInt + this.minValue;
      if (this.format != null)
      {
        String str = this.format;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i);
        return String.format(str, arrayOfObject);
      }
      return Integer.toString(i);
    }
    return null;
  }

  public int getItemsCount()
  {
    return 1 + (this.maxValue - this.minValue);
  }

  public Integer parseItemText(CharSequence paramCharSequence)
  {
    String str = paramCharSequence.toString();
    if (TextUtils.isEmpty(str))
      return Integer.valueOf(0);
    return Integer.valueOf(Integer.parseInt(str));
  }

  public void setMaxValue(int paramInt)
  {
    this.maxValue = paramInt;
    notifyDataInvalidatedEvent();
  }

  public void setMinValue(int paramInt)
  {
    this.minValue = paramInt;
    notifyDataInvalidatedEvent();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.adapters.NumericWheelAdapter
 * JD-Core Version:    0.6.2
 */