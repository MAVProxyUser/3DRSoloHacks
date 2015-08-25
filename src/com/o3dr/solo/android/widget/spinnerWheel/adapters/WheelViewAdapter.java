package com.o3dr.solo.android.widget.spinnerWheel.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public abstract interface WheelViewAdapter
{
  public abstract View getEmptyItem(View paramView, ViewGroup paramViewGroup);

  public abstract View getItem(int paramInt, View paramView, ViewGroup paramViewGroup);

  public abstract int getItemsCount();

  public abstract void registerDataSetObserver(DataSetObserver paramDataSetObserver);

  public abstract void unregisterDataSetObserver(DataSetObserver paramDataSetObserver);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.adapters.WheelViewAdapter
 * JD-Core Version:    0.6.2
 */