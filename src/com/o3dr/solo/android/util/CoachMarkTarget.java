package com.o3dr.solo.android.util;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;

public class CoachMarkTarget
{
  private Point location;
  private final View mView;

  public CoachMarkTarget(int paramInt, Activity paramActivity)
  {
    this.mView = paramActivity.findViewById(paramInt);
    int[] arrayOfInt = new int[2];
    this.mView.getLocationInWindow(arrayOfInt);
    this.location = new Point(arrayOfInt[0], arrayOfInt[1]);
  }

  public CoachMarkTarget(View paramView)
  {
    this.mView = paramView;
    int[] arrayOfInt = new int[2];
    this.mView.getLocationInWindow(arrayOfInt);
    this.location = new Point(arrayOfInt[0], arrayOfInt[1]);
  }

  public Point getAbsoluteCenter()
  {
    return new Point(this.location.x + this.mView.getWidth() / 2, this.location.y + this.mView.getHeight() / 2);
  }

  public Point getPoint()
  {
    return new Point(this.location.x + this.mView.getWidth() / 2, this.location.y + this.mView.getHeight());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.CoachMarkTarget
 * JD-Core Version:    0.6.2
 */