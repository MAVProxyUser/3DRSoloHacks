package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewConfiguration;

public class UnderlinePageIndicator extends View
  implements PageIndicator
{
  private static final int FADE_FRAME_MS = 30;
  private int mActivePointerId = -1;
  private int mCurrentPage;
  private int mFadeBy;
  private int mFadeDelay;
  private int mFadeLength;
  private final Runnable mFadeRunnable = new Runnable()
  {
    public void run()
    {
      if (!UnderlinePageIndicator.this.mFades);
      int i;
      do
      {
        return;
        i = Math.max(UnderlinePageIndicator.this.mPaint.getAlpha() - UnderlinePageIndicator.this.mFadeBy, 0);
        UnderlinePageIndicator.this.mPaint.setAlpha(i);
        UnderlinePageIndicator.this.invalidate();
      }
      while (i <= 0);
      UnderlinePageIndicator.this.postDelayed(this, 30L);
    }
  };
  private boolean mFades;
  private boolean mIsDragging;
  private final boolean mIsInEditMode;
  private float mLastMotionX = -1.0F;
  private ViewPager.OnPageChangeListener mListener;
  private final Paint mPaint = new Paint(1);
  private float mPositionOffset;
  private int mScrollState;
  private int mTouchSlop;
  private ViewPager mViewPager;

  static
  {
    if (!UnderlinePageIndicator.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public UnderlinePageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }

  public UnderlinePageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.vpiUnderlinePageIndicatorStyle);
  }

  public UnderlinePageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = getResources();
    assert (localResources != null);
    boolean bool = localResources.getBoolean(R.bool.default_underline_indicator_fades);
    int i = localResources.getInteger(R.integer.default_underline_indicator_fade_delay);
    int j = localResources.getInteger(R.integer.default_underline_indicator_fade_length);
    int k = localResources.getColor(R.color.default_underline_indicator_selected_color);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.UnderlinePageIndicator, paramInt, 0);
    assert (localTypedArray != null);
    setFades(localTypedArray.getBoolean(2, bool));
    setSelectedColor(localTypedArray.getColor(1, k));
    setFadeDelay(localTypedArray.getInteger(3, i));
    setFadeLength(localTypedArray.getInteger(4, j));
    Drawable localDrawable = localTypedArray.getDrawable(0);
    if (localDrawable != null)
      setBackgroundDrawable(localDrawable);
    localTypedArray.recycle();
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
    this.mIsInEditMode = isInEditMode();
    if (this.mIsInEditMode)
      this.mCurrentPage = 2;
  }

  public int getFadeDelay()
  {
    return this.mFadeDelay;
  }

  public int getFadeLength()
  {
    return this.mFadeLength;
  }

  public boolean getFades()
  {
    return this.mFades;
  }

  public int getSelectedColor()
  {
    return this.mPaint.getColor();
  }

  public void notifyDataSetChanged()
  {
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mIsInEditMode);
    for (int i = 5; i == 0; i = this.mViewPager.getAdapter().getCount())
      return;
    if (this.mCurrentPage >= i)
    {
      setCurrentItem(i - 1);
      return;
    }
    int j = getPaddingLeft();
    float f1 = (getWidth() - j - getPaddingRight()) / (1.0F * i);
    float f2 = j + f1 * (this.mCurrentPage + this.mPositionOffset);
    float f3 = f2 + f1;
    paramCanvas.drawRect(f2, getPaddingTop(), f3, getHeight() - getPaddingBottom(), this.mPaint);
  }

  public void onPageScrollStateChanged(int paramInt)
  {
    this.mScrollState = paramInt;
    if (this.mListener != null)
      this.mListener.onPageScrollStateChanged(paramInt);
  }

  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    this.mCurrentPage = paramInt1;
    this.mPositionOffset = paramFloat;
    if (this.mFades)
    {
      if (paramInt2 <= 0)
        break label64;
      removeCallbacks(this.mFadeRunnable);
      this.mPaint.setAlpha(255);
    }
    while (true)
    {
      invalidate();
      if (this.mListener != null)
        this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
      return;
      label64: if (this.mScrollState != 1)
        postDelayed(this.mFadeRunnable, this.mFadeDelay);
    }
  }

  public void onPageSelected(int paramInt)
  {
    if (this.mScrollState == 0)
    {
      this.mCurrentPage = paramInt;
      this.mPositionOffset = 0.0F;
      invalidate();
      this.mFadeRunnable.run();
    }
    if (this.mListener != null)
      this.mListener.onPageSelected(paramInt);
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mCurrentPage = localSavedState.currentPage;
    requestLayout();
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.currentPage = this.mCurrentPage;
    return localSavedState;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (super.onTouchEvent(paramMotionEvent))
      return true;
    if ((this.mViewPager == null) || (this.mViewPager.getAdapter().getCount() == 0))
      return false;
    int i = 0xFF & paramMotionEvent.getAction();
    switch (i)
    {
    case 4:
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    case 5:
      while (true)
      {
        return true;
        this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
        this.mLastMotionX = paramMotionEvent.getX();
        continue;
        float f3 = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
        float f4 = f3 - this.mLastMotionX;
        if ((!this.mIsDragging) && (Math.abs(f4) > this.mTouchSlop))
          this.mIsDragging = true;
        if (this.mIsDragging)
        {
          this.mLastMotionX = f3;
          if ((this.mViewPager.isFakeDragging()) || (this.mViewPager.beginFakeDrag()))
          {
            this.mViewPager.fakeDragBy(f4);
            continue;
            if (!this.mIsDragging)
            {
              int n = this.mViewPager.getAdapter().getCount();
              int i1 = getWidth();
              float f1 = i1 / 2.0F;
              float f2 = i1 / 6.0F;
              if ((this.mCurrentPage > 0) && (paramMotionEvent.getX() < f1 - f2))
              {
                if (i != 3)
                  this.mViewPager.setCurrentItem(-1 + this.mCurrentPage);
                return true;
              }
              if ((this.mCurrentPage < n - 1) && (paramMotionEvent.getX() > f1 + f2))
              {
                if (i != 3)
                  this.mViewPager.setCurrentItem(1 + this.mCurrentPage);
                return true;
              }
            }
            this.mIsDragging = false;
            this.mActivePointerId = -1;
            if (this.mViewPager.isFakeDragging())
            {
              this.mViewPager.endFakeDrag();
              continue;
              int m = MotionEventCompat.getActionIndex(paramMotionEvent);
              this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, m);
              this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, m);
            }
          }
        }
      }
    case 6:
    }
    int j = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (MotionEventCompat.getPointerId(paramMotionEvent, j) == this.mActivePointerId)
      if (j != 0)
        break label437;
    label437: for (int k = 1; ; k = 0)
    {
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, k);
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
      break;
    }
  }

  public void setCurrentItem(int paramInt)
  {
    if (this.mViewPager == null)
      throw new IllegalStateException("ViewPager has not been bound.");
    this.mViewPager.setCurrentItem(paramInt);
    this.mCurrentPage = paramInt;
    invalidate();
  }

  public void setFadeDelay(int paramInt)
  {
    this.mFadeDelay = paramInt;
  }

  public void setFadeLength(int paramInt)
  {
    this.mFadeLength = paramInt;
    this.mFadeBy = (255 / (this.mFadeLength / 30));
  }

  public void setFades(boolean paramBoolean)
  {
    if (paramBoolean != this.mFades)
    {
      this.mFades = paramBoolean;
      if (paramBoolean)
        post(this.mFadeRunnable);
    }
    else
    {
      return;
    }
    removeCallbacks(this.mFadeRunnable);
    this.mPaint.setAlpha(255);
    invalidate();
  }

  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }

  public void setSelectedColor(int paramInt)
  {
    this.mPaint.setColor(paramInt);
    invalidate();
  }

  public void setViewPager(ViewPager paramViewPager)
  {
    if (this.mViewPager == paramViewPager)
      return;
    if (this.mViewPager != null)
      this.mViewPager.setOnPageChangeListener(null);
    if (paramViewPager.getAdapter() == null)
      throw new IllegalStateException("ViewPager does not have adapter instance.");
    this.mViewPager = paramViewPager;
    this.mViewPager.setOnPageChangeListener(this);
    invalidate();
    post(new Runnable()
    {
      public void run()
      {
        if (UnderlinePageIndicator.this.mFades)
          UnderlinePageIndicator.this.post(UnderlinePageIndicator.this.mFadeRunnable);
      }
    });
  }

  public void setViewPager(ViewPager paramViewPager, int paramInt)
  {
    setViewPager(paramViewPager);
    setCurrentItem(paramInt);
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public UnderlinePageIndicator.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new UnderlinePageIndicator.SavedState(paramAnonymousParcel, null);
      }

      public UnderlinePageIndicator.SavedState[] newArray(int paramAnonymousInt)
      {
        return new UnderlinePageIndicator.SavedState[paramAnonymousInt];
      }
    };
    int currentPage;

    private SavedState(Parcel paramParcel)
    {
      super();
      this.currentPage = paramParcel.readInt();
    }

    public SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.currentPage);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.viewpagerindicator.UnderlinePageIndicator
 * JD-Core Version:    0.6.2
 */