package com.o3dr.solo.android.widget.spinnerWheel;

public class ItemsRange
{
  private int count;
  private int first;

  public ItemsRange()
  {
    this(0, 0);
  }

  public ItemsRange(int paramInt1, int paramInt2)
  {
    this.first = paramInt1;
    this.count = paramInt2;
  }

  public boolean contains(int paramInt)
  {
    return (paramInt >= getFirst()) && (paramInt <= getLast());
  }

  public int getCount()
  {
    return this.count;
  }

  public int getFirst()
  {
    return this.first;
  }

  public int getLast()
  {
    return -1 + (getFirst() + getCount());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.ItemsRange
 * JD-Core Version:    0.6.2
 */