package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomView extends RelativeLayout
{
  static final String ANDROIDXML = "http://schemas.android.com/apk/res/android";
  static final String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
  int beforeBackground;
  final int disabledBackgroundColor = Color.parseColor("#E2E2E2");
  public boolean isLastTouch = false;

  public CustomView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    if (paramBoolean)
      setBackgroundColor(this.beforeBackground);
    while (true)
    {
      invalidate();
      return;
      setBackgroundColor(this.disabledBackgroundColor);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.CustomView
 * JD-Core Version:    0.6.2
 */