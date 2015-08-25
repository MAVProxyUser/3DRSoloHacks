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
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;

public class LinePageIndicator extends View
  implements PageIndicator
{
  private int mActivePointerId = -1;
  private boolean mCentered;
  private int mCurrentPage;
  private float mGapWidth;
  private boolean mIsDragging;
  private final boolean mIsInEditMode;
  private float mLastMotionX = -1.0F;
  private float mLineWidth;
  private ViewPager.OnPageChangeListener mListener;
  private final Paint mPaintSelected = new Paint(1);
  private final Paint mPaintUnselected = new Paint(1);
  private int mTouchSlop;
  private ViewPager mViewPager;

  static
  {
    if (!LinePageIndicator.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public LinePageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }

  public LinePageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.vpiLinePageIndicatorStyle);
  }

  public LinePageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = getResources();
    assert (localResources != null);
    int i = localResources.getColor(R.color.default_line_indicator_selected_color);
    int j = localResources.getColor(R.color.default_line_indicator_unselected_color);
    float f1 = localResources.getDimension(R.dimen.default_line_indicator_line_width);
    float f2 = localResources.getDimension(R.dimen.default_line_indicator_gap_width);
    float f3 = localResources.getDimension(R.dimen.default_line_indicator_stroke_width);
    boolean bool = localResources.getBoolean(R.bool.default_line_indicator_centered);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.LinePageIndicator, paramInt, 0);
    assert (localTypedArray != null);
    this.mCentered = localTypedArray.getBoolean(1, bool);
    this.mLineWidth = localTypedArray.getDimension(6, f1);
    this.mGapWidth = localTypedArray.getDimension(2, f2);
    setStrokeWidth(localTypedArray.getDimension(4, f3));
    this.mPaintUnselected.setColor(localTypedArray.getColor(5, j));
    this.mPaintSelected.setColor(localTypedArray.getColor(3, i));
    Drawable localDrawable = localTypedArray.getDrawable(0);
    if (localDrawable != null)
      setBackgroundDrawable(localDrawable);
    localTypedArray.recycle();
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
    this.mIsInEditMode = isInEditMode();
    if (this.mIsInEditMode)
      this.mCurrentPage = 2;
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

  private int measureHeight(int paramInt)
  {
    int i = View.MeasureSpec.getMode(paramInt);
    int j = View.MeasureSpec.getSize(paramInt);
    float f;
    if (i == 1073741824)
      f = j;
    while (true)
    {
      return (int)FloatMath.ceil(f);
      f = this.mPaintSelected.getStrokeWidth() + getPaddingTop() + getPaddingBottom();
      if (i == -2147483648)
        f = Math.min(f, j);
    }
  }

  private int measureWidth(int paramInt)
  {
    int i = View.MeasureSpec.getMode(paramInt);
    int j = View.MeasureSpec.getSize(paramInt);
    float f;
    if ((i == 1073741824) || ((this.mViewPager == null) && (!this.mIsInEditMode)))
      f = j;
    while (true)
    {
      return (int)FloatMath.ceil(f);
      int k = getCount();
      f = getPaddingLeft() + getPaddingRight() + k * this.mLineWidth + (k - 1) * this.mGapWidth;
      if (i == -2147483648)
        f = Math.min(f, j);
    }
  }

  public float getGapWidth()
  {
    return this.mGapWidth;
  }

  public float getLineWidth()
  {
    return this.mLineWidth;
  }

  public int getSelectedColor()
  {
    return this.mPaintSelected.getColor();
  }

  public float getStrokeWidth()
  {
    return this.mPaintSelected.getStrokeWidth();
  }

  public int getUnselectedColor()
  {
    return this.mPaintUnselected.getColor();
  }

  public boolean isCentered()
  {
    return this.mCentered;
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
    float f1 = this.mLineWidth + this.mGapWidth;
    float f2 = f1 * i - this.mGapWidth;
    float f3 = getPaddingTop();
    float f4 = getPaddingLeft();
    float f5 = getPaddingRight();
    float f6 = f3 + (getHeight() - f3 - getPaddingBottom()) / 2.0F;
    float f7 = f4;
    if (this.mCentered)
      f7 += (getWidth() - f4 - f5) / 2.0F - f2 / 2.0F;
    int j = 0;
    label131: float f8;
    float f9;
    if (j < i)
    {
      f8 = f7 + f1 * j;
      f9 = f8 + this.mLineWidth;
      if (j != this.mCurrentPage)
        break label191;
    }
    label191: for (Paint localPaint = this.mPaintSelected; ; localPaint = this.mPaintUnselected)
    {
      paramCanvas.drawLine(f8, f6, f9, f6, localPaint);
      j++;
      break label131;
      break;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(measureWidth(paramInt1), measureHeight(paramInt2));
  }

  public void onPageScrollStateChanged(int paramInt)
  {
    if (this.mListener != null)
      this.mListener.onPageScrollStateChanged(paramInt);
  }

  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    if (this.mListener != null)
      this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
  }

  public void onPageSelected(int paramInt)
  {
    this.mCurrentPage = paramInt;
    invalidate();
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

  public void setGapWidth(float paramFloat)
  {
    this.mGapWidth = paramFloat;
    invalidate();
  }

  public void setLineWidth(float paramFloat)
  {
    this.mLineWidth = paramFloat;
    invalidate();
  }

  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }

  public void setSelectedColor(int paramInt)
  {
    this.mPaintSelected.setColor(paramInt);
    invalidate();
  }

  public void setStrokeWidth(float paramFloat)
  {
    this.mPaintSelected.setStrokeWidth(paramFloat);
    this.mPaintUnselected.setStrokeWidth(paramFloat);
    invalidate();
  }

  public void setUnselectedColor(int paramInt)
  {
    this.mPaintUnselected.setColor(paramInt);
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
      public LinePageIndicator.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new LinePageIndicator.SavedState(paramAnonymousParcel, null);
      }

      public LinePageIndicator.SavedState[] newArray(int paramAnonymousInt)
      {
        return new LinePageIndicator.SavedState[paramAnonymousInt];
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
 * Qualified Name:     com.viewpagerindicator.LinePageIndicator
 * JD-Core Version:    0.6.2
 */