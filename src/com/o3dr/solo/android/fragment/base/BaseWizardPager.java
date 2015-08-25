package com.o3dr.solo.android.fragment.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BaseWizardPager extends ViewPager
{
  private OnSwipeOutListener mListener;
  private float mStartDragX;

  public BaseWizardPager(Context paramContext)
  {
    super(paramContext);
  }

  public BaseWizardPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    float f;
    if (getCurrentItem() == -1 + getAdapter().getCount())
    {
      int i = paramMotionEvent.getAction();
      f = paramMotionEvent.getX();
      switch (i & 0xFF)
      {
      default:
      case 0:
      case 1:
      }
    }
    while (true)
    {
      return super.onTouchEvent(paramMotionEvent);
      this.mStartDragX = f;
      continue;
      if (f < this.mStartDragX)
      {
        if (this.mListener != null)
          this.mListener.onSwipeOutAtEnd();
      }
      else
      {
        this.mStartDragX = 0.0F;
        continue;
        this.mStartDragX = 0.0F;
      }
    }
  }

  public void setOnSwipeOutListener(OnSwipeOutListener paramOnSwipeOutListener)
  {
    this.mListener = paramOnSwipeOutListener;
  }

  public static abstract interface OnSwipeOutListener
  {
    public abstract void onSwipeOutAtEnd();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.base.BaseWizardPager
 * JD-Core Version:    0.6.2
 */