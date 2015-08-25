package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class ScrollView extends android.widget.ScrollView
{
  public ScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 0;
    while (i < ((ViewGroup)getChildAt(0)).getChildCount())
      try
      {
        CustomView localCustomView = (CustomView)((ViewGroup)getChildAt(0)).getChildAt(i);
        if (localCustomView.isLastTouch)
        {
          localCustomView.onTouchEvent(paramMotionEvent);
          return true;
        }
      }
      catch (ClassCastException localClassCastException)
      {
        i++;
      }
    return super.onTouchEvent(paramMotionEvent);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ScrollView
 * JD-Core Version:    0.6.2
 */