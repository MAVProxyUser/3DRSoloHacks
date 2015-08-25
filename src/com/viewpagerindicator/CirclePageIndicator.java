package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
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
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;

public class CirclePageIndicator extends View
  implements PageIndicator
{
  private int mActivePointerId = -1;
  private boolean mCentered;
  private int mCurrentPage;
  private float mGapWidth;
  private boolean mIsDragging;
  private final boolean mIsInEditMode;
  private float mLastMotionX = -1.0F;
  private ViewPager.OnPageChangeListener mListener;
  private int mOrientation;
  private float mPageOffset;
  private final Paint mPaintFill = new Paint(1);
  private final Paint mPaintPageFill = new Paint(1);
  private final Paint mPaintStroke = new Paint(1);
  private float mRadius;
  private int mScrollState;
  private boolean mSnap;
  private int mSnapPage;
  private int mTouchSlop;
  private ViewPager mViewPager;

  static
  {
    if (!CirclePageIndicator.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public CirclePageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }

  public CirclePageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.vpiCirclePageIndicatorStyle);
  }

  public CirclePageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = getResources();
    assert (localResources != null);
    int i = localResources.getColor(R.color.default_circle_indicator_page_color);
    int j = localResources.getColor(R.color.default_circle_indicator_fill_color);
    int k = localResources.getInteger(R.integer.default_circle_indicator_orientation);
    int m = localResources.getColor(R.color.default_circle_indicator_stroke_color);
    float f1 = localResources.getDimension(R.dimen.default_circle_indicator_stroke_width);
    float f2 = localResources.getDimension(R.dimen.default_circle_indicator_radius);
    float f3 = localResources.getDimension(R.dimen.default_circle_indicator_gap_width);
    boolean bool1 = localResources.getBoolean(R.bool.default_circle_indicator_centered);
    boolean bool2 = localResources.getBoolean(R.bool.default_circle_indicator_snap);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CirclePageIndicator, paramInt, 0);
    assert (localTypedArray != null);
    this.mCentered = localTypedArray.getBoolean(2, bool1);
    this.mOrientation = localTypedArray.getInt(0, k);
    this.mPaintPageFill.setStyle(Paint.Style.FILL);
    this.mPaintPageFill.setColor(localTypedArray.getColor(6, i));
    this.mPaintStroke.setStyle(Paint.Style.STROKE);
    this.mPaintStroke.setColor(localTypedArray.getColor(9, m));
    this.mPaintStroke.setStrokeWidth(localTypedArray.getDimension(4, f1));
    this.mPaintFill.setStyle(Paint.Style.FILL);
    this.mPaintFill.setColor(localTypedArray.getColor(5, j));
    this.mRadius = localTypedArray.getDimension(7, f2);
    this.mGapWidth = localTypedArray.getDimension(3, f3);
    this.mSnap = localTypedArray.getBoolean(8, bool2);
    Drawable localDrawable = localTypedArray.getDrawable(1);
    if (localDrawable != null)
      setBackgroundDrawable(localDrawable);
    localTypedArray.recycle();
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
    this.mIsInEditMode = isInEditMode();
    if (this.mIsInEditMode)
    {
      this.mCurrentPage = 2;
      this.mSnapPage = 2;
    }
  }

  private int getCount()
  {
    if (this.mIsInEditMode)
      return 5;
    if (this.mViewPager != null)
    {
      PagerAdapter localPagerAdapter = this.mViewPager.getAdapter();
      if (localPagerAdapter != null)
        return localPagerAdapter.getCount();
    }
    return 0;
  }

  private int measureLong(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k;
    if ((i == 1073741824) || ((this.mViewPager == null) && (!this.mIsInEditMode)))
      k = j;
    do
    {
      return k;
      int m = getCount();
      k = (int)(1.0F + (paramInt2 + m * 2 * this.mRadius + (m - 1) * this.mGapWidth));
    }
    while (i != -2147483648);
    return Math.min(k, j);
  }

  private int measureShort(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k;
    if (i == 1073741824)
      k = j;
    do
    {
      return k;
      k = (int)(1.0F + (2.0F * this.mRadius + paramInt2));
    }
    while (i != -2147483648);
    return Math.min(k, j);
  }

  public int getFillColor()
  {
    return this.mPaintFill.getColor();
  }

  public float getGapWidth()
  {
    return this.mGapWidth;
  }

  public int getOrientation()
  {
    return this.mOrientation;
  }

  public int getPageColor()
  {
    return this.mPaintPageFill.getColor();
  }

  public float getRadius()
  {
    return this.mRadius;
  }

  public int getStrokeColor()
  {
    return this.mPaintStroke.getColor();
  }

  public float getStrokeWidth()
  {
    return this.mPaintStroke.getStrokeWidth();
  }

  public boolean isCentered()
  {
    return this.mCentered;
  }

  public boolean isSnap()
  {
    return this.mSnap;
  }

  public void notifyDataSetChanged()
  {
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getCount();
    if (i == 0)
      return;
    if (this.mCurrentPage >= i)
    {
      setCurrentItem(i - 1);
      return;
    }
    int j;
    int k;
    int m;
    int n;
    float f1;
    float f2;
    float f3;
    float f4;
    int i1;
    label166: float f8;
    float f9;
    if (this.mOrientation == 0)
    {
      j = getWidth();
      k = getPaddingLeft();
      m = getPaddingRight();
      n = getPaddingTop();
      f1 = 2.0F * this.mRadius + this.mGapWidth;
      f2 = n + this.mRadius;
      f3 = k + this.mRadius + this.mGapWidth / 2.0F;
      if (this.mCentered)
        f3 += (j - k - m) / 2.0F - f1 * i / 2.0F;
      f4 = this.mRadius;
      if (this.mPaintStroke.getStrokeWidth() > 0.0F)
        f4 -= this.mPaintStroke.getStrokeWidth() / 2.0F;
      i1 = 0;
      if (i1 >= i)
        break label291;
      f8 = f3 + f1 * i1;
      if (this.mOrientation != 0)
        break label280;
      f9 = f8;
    }
    for (float f10 = f2; ; f10 = f8)
    {
      if (this.mPaintPageFill.getAlpha() > 0)
        paramCanvas.drawCircle(f9, f10, f4, this.mPaintPageFill);
      if (f4 != this.mRadius)
        paramCanvas.drawCircle(f9, f10, this.mRadius, this.mPaintStroke);
      i1++;
      break label166;
      j = getHeight();
      k = getPaddingTop();
      m = getPaddingBottom();
      n = getPaddingLeft();
      break;
      label280: f9 = f2;
    }
    label291: float f5;
    float f6;
    if (this.mSnap)
    {
      f5 = f1 * this.mSnapPage;
      if (this.mOrientation != 0)
        break label361;
      f6 = f3 + f5;
    }
    for (float f7 = f2; ; f7 = f3 + f5)
    {
      paramCanvas.drawCircle(f6, f7, this.mRadius, this.mPaintFill);
      return;
      f5 = f1 * (this.mCurrentPage + this.mPageOffset);
      break;
      label361: f6 = f2;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mOrientation == 0)
    {
      setMeasuredDimension(measureLong(paramInt1, getPaddingLeft() + getPaddingRight()), measureShort(paramInt2, getPaddingTop() + getPaddingBottom()));
      return;
    }
    setMeasuredDimension(measureShort(paramInt1, getPaddingLeft() + getPaddingRight()), measureLong(paramInt2, getPaddingTop() + getPaddingBottom()));
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
    this.mPageOffset = paramFloat;
    invalidate();
    if (this.mListener != null)
      this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
  }

  public void onPageSelected(int paramInt)
  {
    if ((this.mSnap) || (this.mScrollState == 0))
    {
      this.mCurrentPage = paramInt;
      this.mSnapPage = paramInt;
      invalidate();
    }
    if (this.mListener != null)
      this.mListener.onPageSelected(paramInt);
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mCurrentPage = localSavedState.currentPage;
    this.mSnapPage = localSavedState.currentPage;
    requestLayout();
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    if (this.mSnap);
    for (int i = this.mSnapPage; ; i = this.mCurrentPage)
    {
      localSavedState.currentPage = i;
      return localSavedState;
    }
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

  public void setCentered(boolean paramBoolean)
  {
    this.mCentered = paramBoolean;
    invalidate();
  }

  public void setCurrentItem(int paramInt)
  {
    if (this.mViewPager == null)
      throw new IllegalStateException("ViewPager has not been bound.");
    this.mViewPager.setCurrentItem(paramInt);
    this.mCurrentPage = paramInt;
    invalidate();
  }

  public void setFillColor(int paramInt)
  {
    this.mPaintFill.setColor(paramInt);
    invalidate();
  }

  public void setGapWidth(float paramFloat)
  {
    this.mGapWidth = paramFloat;
    invalidate();
  }

  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }

  public void setOrientation(int paramInt)
  {
    switch (paramInt)
    {
    default:
      throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
    case 0:
    case 1:
    }
    this.mOrientation = paramInt;
    requestLayout();
  }

  public void setPageColor(int paramInt)
  {
    this.mPaintPageFill.setColor(paramInt);
    invalidate();
  }

  public void setRadius(float paramFloat)
  {
    this.mRadius = paramFloat;
    invalidate();
  }

  public void setSnap(boolean paramBoolean)
  {
    this.mSnap = paramBoolean;
    invalidate();
  }

  public void setStrokeColor(int paramInt)
  {
    this.mPaintStroke.setColor(paramInt);
    invalidate();
  }

  public void setStrokeWidth(float paramFloat)
  {
    this.mPaintStroke.setStrokeWidth(paramFloat);
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
      public CirclePageIndicator.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new CirclePageIndicator.SavedState(paramAnonymousParcel, null);
      }

      public CirclePageIndicator.SavedState[] newArray(int paramAnonymousInt)
      {
        return new CirclePageIndicator.SavedState[paramAnonymousInt];
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
 * Qualified Name:     com.viewpagerindicator.CirclePageIndicator
 * JD-Core Version:    0.6.2
 */