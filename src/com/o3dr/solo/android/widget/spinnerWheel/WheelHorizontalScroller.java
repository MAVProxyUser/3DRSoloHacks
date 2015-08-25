package com.o3dr.solo.android.widget.spinnerWheel;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Scroller;

public class WheelHorizontalScroller extends WheelScroller
{
  public WheelHorizontalScroller(Context paramContext, WheelScroller.ScrollingListener paramScrollingListener)
  {
    super(paramContext, paramScrollingListener);
  }

  protected int getCurrentScrollerPosition()
  {
    return this.scroller.getCurrX();
  }

  protected int getFinalScrollerPosition()
  {
    return this.scroller.getFinalX();
  }

  protected float getMotionDistance(MotionEvent paramMotionEvent, float paramFloat1, float paramFloat2)
  {
    float f1 = paramMotionEvent.getX() - paramFloat1;
    float f2 = paramMotionEvent.getY() - paramFloat2;
    if (Math.abs(f1) > Math.abs(f2))
      return f1;
    return 0.0F;
  }

  protected void scrollerFling(int paramInt1, int paramInt2, int paramInt3)
  {
    this.scroller.fling(paramInt1, 0, -paramInt2, 0, -2147483647, 2147483647, 0, 0);
  }

  protected void scrollerStartScroll(int paramInt1, int paramInt2)
  {
    this.scroller.startScroll(0, 0, paramInt1, 0, paramInt2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.WheelHorizontalScroller
 * JD-Core Version:    0.6.2
 */