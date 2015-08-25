package com.o3dr.solo.android.fragment;

import android.support.annotation.StringRes;

public abstract interface TitleUpdater
{
  public abstract void updateTitle(@StringRes int paramInt);

  public abstract void updateTitle(CharSequence paramCharSequence);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.TitleUpdater
 * JD-Core Version:    0.6.2
 */