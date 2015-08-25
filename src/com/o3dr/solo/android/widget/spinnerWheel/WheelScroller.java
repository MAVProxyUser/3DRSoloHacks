package com.o3dr.solo.android.widget.spinnerWheel;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public abstract class WheelScroller
{
  public static final int MIN_DELTA_FOR_SCROLLING = 1;
  private static final int SCROLLING_DURATION = 400;
  private final int MESSAGE_JUSTIFY = 1;
  private final int MESSAGE_SCROLL = 0;
  private Handler animationHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      WheelScroller.this.scroller.computeScrollOffset();
      int i = WheelScroller.this.getCurrentScrollerPosition();
      int j = WheelScroller.this.lastScrollPosition - i;
      WheelScroller.access$002(WheelScroller.this, i);
      if (j != 0)
        WheelScroller.this.listener.onScroll(j);
      if (Math.abs(i - WheelScroller.this.getFinalScrollerPosition()) < 1)
        WheelScroller.this.scroller.forceFinished(true);
      if (!WheelScroller.this.scroller.isFinished())
      {
        WheelScroller.this.animationHandler.sendEmptyMessage(paramAnonymousMessage.what);
        return;
      }
      if (paramAnonymousMessage.what == 0)
      {
        WheelScroller.this.justify();
        return;
      }
      WheelScroller.this.finishScrolling();
    }
  };
  private Context context;
  private GestureDetector gestureDetector = new GestureDetector(paramContext, new GestureDetector.SimpleOnGestureListener()
  {
    public boolean onFling(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
    {
      WheelScroller.access$002(WheelScroller.this, 0);
      WheelScroller.this.scrollerFling(WheelScroller.this.lastScrollPosition, (int)paramAnonymousFloat1, (int)paramAnonymousFloat2);
      WheelScroller.this.setNextMessage(0);
      return true;
    }

    public boolean onScroll(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
    {
      return true;
    }
  });
  private boolean isScrollingPerformed;
  private int lastScrollPosition;
  private float lastTouchedPositionX;
  private float lastTouchedPositionY;
  private ScrollingListener listener;
  protected Scroller scroller;

  public WheelScroller(Context paramContext, ScrollingListener paramScrollingListener)
  {
    this.gestureDetector.setIsLongpressEnabled(false);
    this.scroller = new Scroller(paramContext);
    this.listener = paramScrollingListener;
    this.context = paramContext;
  }

  private void clearMessages()
  {
    this.animationHandler.removeMessages(0);
    this.animationHandler.removeMessages(1);
  }

  private void justify()
  {
    this.listener.onJustify();
    setNextMessage(1);
  }

  private void setNextMessage(int paramInt)
  {
    clearMessages();
    this.animationHandler.sendEmptyMessage(paramInt);
  }

  private void startScrolling()
  {
    if (!this.isScrollingPerformed)
    {
      this.isScrollingPerformed = true;
      this.listener.onStarted();
    }
  }

  protected void finishScrolling()
  {
    if (this.isScrollingPerformed)
    {
      this.listener.onFinished();
      this.isScrollingPerformed = false;
    }
  }

  protected abstract int getCurrentScrollerPosition();

  protected abstract int getFinalScrollerPosition();

  protected abstract float getMotionDistance(MotionEvent paramMotionEvent, float paramFloat1, float paramFloat2);

  public boolean onTouchEvent(ViewParent paramViewParent, MotionEvent paramMotionEvent)
  {
    boolean bool = true;
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      if ((bool) && (!this.gestureDetector.onTouchEvent(paramMotionEvent)) && (paramMotionEvent.getAction() == 1))
        justify();
      return bool;
      if (paramViewParent != null)
        paramViewParent.requestDisallowInterceptTouchEvent(true);
      this.lastTouchedPositionX = paramMotionEvent.getX();
      this.lastTouchedPositionY = paramMotionEvent.getY();
      this.scroller.forceFinished(true);
      clearMessages();
      this.listener.onTouch();
      continue;
      if (this.scroller.isFinished())
      {
        this.listener.onTouchUp();
        continue;
        int i = (int)getMotionDistance(paramMotionEvent, this.lastTouchedPositionX, this.lastTouchedPositionY);
        if (i != 0)
        {
          if (paramViewParent != null)
            paramViewParent.requestDisallowInterceptTouchEvent(true);
          startScrolling();
          this.listener.onScroll(i);
          this.lastTouchedPositionX = paramMotionEvent.getX();
          this.lastTouchedPositionY = paramMotionEvent.getY();
        }
        else
        {
          if (paramViewParent != null)
            paramViewParent.requestDisallowInterceptTouchEvent(false);
          bool = false;
        }
      }
    }
  }

  public void scroll(int paramInt1, int paramInt2)
  {
    this.scroller.forceFinished(true);
    this.lastScrollPosition = 0;
    if (paramInt2 != 0);
    while (true)
    {
      scrollerStartScroll(paramInt1, paramInt2);
      setNextMessage(0);
      startScrolling();
      return;
      paramInt2 = 400;
    }
  }

  protected abstract void scrollerFling(int paramInt1, int paramInt2, int paramInt3);

  protected abstract void scrollerStartScroll(int paramInt1, int paramInt2);

  public void setInterpolator(Interpolator paramInterpolator)
  {
    this.scroller.forceFinished(true);
    this.scroller = new Scroller(this.context, paramInterpolator);
  }

  public void stopScrolling()
  {
    this.scroller.forceFinished(true);
  }

  public static abstract interface ScrollingListener
  {
    public abstract void onFinished();

    public abstract void onJustify();

    public abstract void onScroll(int paramInt);

    public abstract void onStarted();

    public abstract void onTouch();

    public abstract void onTouchUp();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.WheelScroller
 * JD-Core Version:    0.6.2
 */