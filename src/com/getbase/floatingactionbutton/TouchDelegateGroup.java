package com.getbase.floatingactionbutton;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import java.util.ArrayList;

public class TouchDelegateGroup extends TouchDelegate
{
  private static final Rect USELESS_HACKY_RECT = new Rect();
  private TouchDelegate mCurrentTouchDelegate;
  private boolean mEnabled;
  private final ArrayList<TouchDelegate> mTouchDelegates = new ArrayList();

  public TouchDelegateGroup(View paramView)
  {
    super(USELESS_HACKY_RECT, paramView);
  }

  public void addTouchDelegate(@NonNull TouchDelegate paramTouchDelegate)
  {
    this.mTouchDelegates.add(paramTouchDelegate);
  }

  public void clearTouchDelegates()
  {
    this.mTouchDelegates.clear();
    this.mCurrentTouchDelegate = null;
  }

  public boolean onTouchEvent(@NonNull MotionEvent paramMotionEvent)
  {
    boolean bool = true;
    if (!this.mEnabled)
      bool = false;
    while (true)
    {
      return bool;
      int i = paramMotionEvent.getAction();
      TouchDelegate localTouchDelegate1 = null;
      switch (i)
      {
      default:
      case 0:
      case 2:
      case 1:
      case 3:
      }
      while ((localTouchDelegate1 == null) || (!localTouchDelegate1.onTouchEvent(paramMotionEvent)))
      {
        return false;
        for (int j = 0; ; j++)
        {
          int k = this.mTouchDelegates.size();
          localTouchDelegate1 = null;
          if (j >= k)
            break;
          TouchDelegate localTouchDelegate2 = (TouchDelegate)this.mTouchDelegates.get(j);
          if (localTouchDelegate2.onTouchEvent(paramMotionEvent))
          {
            this.mCurrentTouchDelegate = localTouchDelegate2;
            return bool;
          }
        }
        localTouchDelegate1 = this.mCurrentTouchDelegate;
        continue;
        localTouchDelegate1 = this.mCurrentTouchDelegate;
        this.mCurrentTouchDelegate = null;
      }
    }
  }

  public void removeTouchDelegate(TouchDelegate paramTouchDelegate)
  {
    this.mTouchDelegates.remove(paramTouchDelegate);
    if (this.mCurrentTouchDelegate == paramTouchDelegate)
      this.mCurrentTouchDelegate = null;
  }

  public void setEnabled(boolean paramBoolean)
  {
    this.mEnabled = paramBoolean;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.getbase.floatingactionbutton.TouchDelegateGroup
 * JD-Core Version:    0.6.2
 */