package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import java.util.ArrayList;

public class TitlePageIndicator extends View
  implements PageIndicator
{
  private static final float BOLD_FADE_PERCENTAGE = 0.05F;
  private static final float SELECTION_FADE_PERCENTAGE = 0.25F;
  private int mActivePointerId = -1;
  private boolean mBoldText;
  private final Rect mBounds = new Rect();
  private OnCenterItemClickListener mCenterItemClickListener;
  private float mClipPadding;
  private int mColorSelected;
  private int mColorText;
  private int mCurrentPage = -1;
  private float mFooterIndicatorHeight;
  private IndicatorStyle mFooterIndicatorStyle;
  private float mFooterIndicatorUnderlinePadding;
  private float mFooterLineHeight;
  private float mFooterPadding;
  private boolean mIsDragging;
  private final boolean mIsInEditMode;
  private float mLastMotionX = -1.0F;
  private LinePosition mLinePosition;
  private ViewPager.OnPageChangeListener mListener;
  private float mPageOffset;
  private final Paint mPaintFooterIndicator = new Paint();
  private final Paint mPaintFooterLine = new Paint();
  private final Paint mPaintText = new Paint();
  private Path mPath = new Path();
  private int mScrollState;
  private float mTitlePadding;
  private float mTopPadding;
  private int mTouchSlop;
  private ViewPager mViewPager;

  static
  {
    if (!TitlePageIndicator.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public TitlePageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }

  public TitlePageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.vpiTitlePageIndicatorStyle);
  }

  public TitlePageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = getResources();
    assert (localResources != null);
    int i = localResources.getColor(R.color.default_title_indicator_footer_color);
    float f1 = localResources.getDimension(R.dimen.default_title_indicator_footer_line_height);
    int j = localResources.getInteger(R.integer.default_title_indicator_footer_indicator_style);
    float f2 = localResources.getDimension(R.dimen.default_title_indicator_footer_indicator_height);
    float f3 = localResources.getDimension(R.dimen.default_title_indicator_footer_indicator_underline_padding);
    float f4 = localResources.getDimension(R.dimen.default_title_indicator_footer_padding);
    int k = localResources.getInteger(R.integer.default_title_indicator_line_position);
    int m = localResources.getColor(R.color.default_title_indicator_selected_color);
    boolean bool = localResources.getBoolean(R.bool.default_title_indicator_selected_bold);
    int n = localResources.getColor(R.color.default_title_indicator_text_color);
    float f5 = localResources.getDimension(R.dimen.default_title_indicator_text_size);
    float f6 = localResources.getDimension(R.dimen.default_title_indicator_title_padding);
    float f7 = localResources.getDimension(R.dimen.default_title_indicator_clip_padding);
    float f8 = localResources.getDimension(R.dimen.default_title_indicator_top_padding);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.TitlePageIndicator, paramInt, 0);
    assert (localTypedArray != null);
    this.mFooterLineHeight = localTypedArray.getDimension(6, f1);
    this.mFooterIndicatorStyle = IndicatorStyle.fromValue(localTypedArray.getInteger(7, j));
    this.mFooterIndicatorHeight = localTypedArray.getDimension(8, f2);
    this.mFooterIndicatorUnderlinePadding = localTypedArray.getDimension(9, f3);
    this.mFooterPadding = localTypedArray.getDimension(10, f4);
    this.mLinePosition = LinePosition.fromValue(localTypedArray.getInteger(11, k));
    this.mTopPadding = localTypedArray.getDimension(14, f8);
    this.mTitlePadding = localTypedArray.getDimension(13, f6);
    this.mClipPadding = localTypedArray.getDimension(4, f7);
    this.mColorSelected = localTypedArray.getColor(3, m);
    this.mColorText = localTypedArray.getColor(1, n);
    this.mBoldText = localTypedArray.getBoolean(12, bool);
    float f9 = localTypedArray.getDimension(0, f5);
    int i1 = localTypedArray.getColor(5, i);
    this.mPaintText.setTextSize(f9);
    this.mPaintText.setAntiAlias(true);
    this.mPaintFooterLine.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
    this.mPaintFooterLine.setColor(i1);
    this.mPaintFooterIndicator.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mPaintFooterIndicator.setColor(i1);
    Drawable localDrawable = localTypedArray.getDrawable(2);
    if (localDrawable != null)
      setBackgroundDrawable(localDrawable);
    localTypedArray.recycle();
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
    this.mIsInEditMode = isInEditMode();
    if (this.mIsInEditMode)
      this.mCurrentPage = 2;
  }

  private Rect calcBounds(int paramInt, Paint paramPaint)
  {
    CharSequence localCharSequence = getTitle(paramInt);
    Rect localRect = new Rect();
    localRect.right = ((int)paramPaint.measureText(localCharSequence, 0, localCharSequence.length()));
    localRect.bottom = ((int)(paramPaint.descent() - paramPaint.ascent()));
    return localRect;
  }

  private ArrayList<Rect> calculateAllBounds(Paint paramPaint)
  {
    ArrayList localArrayList = new ArrayList();
    if (this.mIsInEditMode);
    for (int i = 5; ; i = this.mViewPager.getAdapter().getCount())
    {
      int j = getWidth();
      int k = j / 2;
      for (int m = 0; m < i; m++)
      {
        Rect localRect = calcBounds(m, paramPaint);
        int n = localRect.right - localRect.left;
        int i1 = localRect.bottom - localRect.top;
        localRect.left = ((int)(k - n / 2.0F + (m - this.mCurrentPage - this.mPageOffset) * j));
        localRect.right = (n + localRect.left);
        localRect.top = 0;
        localRect.bottom = i1;
        localArrayList.add(localRect);
      }
    }
    return localArrayList;
  }

  private void clipViewOnTheLeft(Rect paramRect, float paramFloat, int paramInt)
  {
    paramRect.left = ((int)(paramInt + this.mClipPadding));
    paramRect.right = ((int)(paramFloat + this.mClipPadding));
  }

  private void clipViewOnTheRight(Rect paramRect, float paramFloat, int paramInt)
  {
    paramRect.right = ((int)(paramInt - this.mClipPadding));
    paramRect.left = ((int)(paramRect.right - paramFloat));
  }

  private CharSequence getTitle(int paramInt)
  {
    Object localObject;
    if (this.mIsInEditMode)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt + 1);
      localObject = String.format("Page %d", arrayOfObject);
    }
    do
    {
      return localObject;
      localObject = this.mViewPager.getAdapter().getPageTitle(paramInt);
    }
    while (localObject != null);
    return EMPTY_TITLE;
  }

  public float getClipPadding()
  {
    return this.mClipPadding;
  }

  public int getFooterColor()
  {
    return this.mPaintFooterLine.getColor();
  }

  public float getFooterIndicatorHeight()
  {
    return this.mFooterIndicatorHeight;
  }

  public float getFooterIndicatorPadding()
  {
    return this.mFooterPadding;
  }

  public IndicatorStyle getFooterIndicatorStyle()
  {
    return this.mFooterIndicatorStyle;
  }

  public float getFooterLineHeight()
  {
    return this.mFooterLineHeight;
  }

  public LinePosition getLinePosition()
  {
    return this.mLinePosition;
  }

  public int getSelectedColor()
  {
    return this.mColorSelected;
  }

  public int getTextColor()
  {
    return this.mColorText;
  }

  public float getTextSize()
  {
    return this.mPaintText.getTextSize();
  }

  public float getTitlePadding()
  {
    return this.mTitlePadding;
  }

  public float getTopPadding()
  {
    return this.mTopPadding;
  }

  public Typeface getTypeface()
  {
    return this.mPaintText.getTypeface();
  }

  public boolean isSelectedBold()
  {
    return this.mBoldText;
  }

  public void notifyDataSetChanged()
  {
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i;
    if (this.mIsInEditMode)
    {
      i = 5;
      if (i != 0)
        break label33;
    }
    label33: ArrayList localArrayList;
    int j;
    int i2;
    int i3;
    label180: float f5;
    label431: label437: label443: label956: float f8;
    label662: label950: float f9;
    do
    {
      return;
      i = this.mViewPager.getAdapter().getCount();
      break;
      if ((this.mCurrentPage == -1) && (this.mViewPager != null))
        this.mCurrentPage = this.mViewPager.getCurrentItem();
      localArrayList = calculateAllBounds(this.mPaintText);
      j = localArrayList.size();
      if (this.mCurrentPage >= j)
      {
        setCurrentItem(j - 1);
        return;
      }
      int k = i - 1;
      float f1 = getWidth() / 2.0F;
      int m = getLeft();
      float f2 = m + this.mClipPadding;
      int n = getWidth();
      int i1 = m + n;
      float f3 = i1 - this.mClipPadding;
      float f4;
      if (this.mPageOffset <= 0.5D)
      {
        i2 = this.mCurrentPage;
        f4 = this.mPageOffset;
        if (f4 > 0.25F)
          break label431;
        i3 = 1;
        if (f4 > 0.05F)
          break label437;
      }
      for (int i4 = 1; ; i4 = 0)
      {
        f5 = (0.25F - f4) / 0.25F;
        Rect localRect1 = (Rect)localArrayList.get(this.mCurrentPage);
        float f6 = localRect1.right - localRect1.left;
        if (localRect1.left < f2)
          clipViewOnTheLeft(localRect1, f6, m);
        if (localRect1.right > f3)
          clipViewOnTheRight(localRect1, f6, i1);
        if (this.mCurrentPage <= 0)
          break label443;
        for (int i13 = -1 + this.mCurrentPage; i13 >= 0; i13--)
        {
          Rect localRect7 = (Rect)localArrayList.get(i13);
          if (localRect7.left < f2)
          {
            int i14 = localRect7.right - localRect7.left;
            clipViewOnTheLeft(localRect7, i14, m);
            Rect localRect8 = (Rect)localArrayList.get(i13 + 1);
            if (localRect7.right + this.mTitlePadding > localRect8.left)
            {
              localRect7.left = ((int)(localRect8.left - i14 - this.mTitlePadding));
              localRect7.right = (i14 + localRect7.left);
            }
          }
        }
        i2 = 1 + this.mCurrentPage;
        f4 = 1.0F - this.mPageOffset;
        break;
        i3 = 0;
        break label180;
      }
      if (this.mCurrentPage < k)
        for (int i11 = 1 + this.mCurrentPage; i11 < i; i11++)
        {
          Rect localRect5 = (Rect)localArrayList.get(i11);
          if (localRect5.right > f3)
          {
            int i12 = localRect5.right - localRect5.left;
            clipViewOnTheRight(localRect5, i12, i1);
            Rect localRect6 = (Rect)localArrayList.get(i11 - 1);
            if (localRect5.left - this.mTitlePadding < localRect6.right)
            {
              localRect5.left = ((int)(localRect6.right + this.mTitlePadding));
              localRect5.right = (i12 + localRect5.left);
            }
          }
        }
      int i5 = this.mColorText >>> 24;
      int i6 = 0;
      if (i6 < i)
      {
        Rect localRect3 = (Rect)localArrayList.get(i6);
        int i8;
        CharSequence localCharSequence;
        Paint localPaint;
        if (((localRect3.left > m) && (localRect3.left < i1)) || ((localRect3.right > m) && (localRect3.right < i1)))
        {
          if (i6 != i2)
            break label950;
          i8 = 1;
          localCharSequence = getTitle(i6);
          localPaint = this.mPaintText;
          if ((i8 == 0) || (i4 == 0) || (!this.mBoldText))
            break label956;
        }
        for (boolean bool = true; ; bool = false)
        {
          localPaint.setFakeBoldText(bool);
          this.mPaintText.setColor(this.mColorText);
          if ((i8 != 0) && (i3 != 0))
            this.mPaintText.setAlpha(i5 - (int)(f5 * i5));
          int i9 = j - 1;
          if (i6 < i9)
          {
            Rect localRect4 = (Rect)localArrayList.get(i6 + 1);
            if (localRect3.right + this.mTitlePadding > localRect4.left)
            {
              int i10 = localRect3.right - localRect3.left;
              localRect3.left = ((int)(localRect4.left - i10 - this.mTitlePadding));
              localRect3.right = (i10 + localRect3.left);
            }
          }
          paramCanvas.drawText(localCharSequence, 0, localCharSequence.length(), localRect3.left, localRect3.bottom + this.mTopPadding, this.mPaintText);
          if ((i8 != 0) && (i3 != 0))
          {
            this.mPaintText.setColor(this.mColorSelected);
            this.mPaintText.setAlpha((int)(f5 * (this.mColorSelected >>> 24)));
            paramCanvas.drawText(localCharSequence, 0, localCharSequence.length(), localRect3.left, localRect3.bottom + this.mTopPadding, this.mPaintText);
          }
          i6++;
          break;
          i8 = 0;
          break label662;
        }
      }
      int i7;
      float f7;
      if (this.mLinePosition == LinePosition.Top)
      {
        i7 = 0;
        f7 = 0.0F;
      }
      for (f8 = 0.0F; ; f8 = this.mFooterIndicatorHeight)
      {
        this.mPath.reset();
        this.mPath.moveTo(0.0F, i7 - f7 / 2.0F);
        this.mPath.lineTo(n, i7 - f7 / 2.0F);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mPaintFooterLine);
        f9 = i7 - f7;
        switch (1.$SwitchMap$com$viewpagerindicator$TitlePageIndicator$IndicatorStyle[this.mFooterIndicatorStyle.ordinal()])
        {
        default:
          return;
        case 1:
          this.mPath.reset();
          this.mPath.moveTo(f1, f9 - f8);
          this.mPath.lineTo(f1 + f8, f9);
          this.mPath.lineTo(f1 - f8, f9);
          this.mPath.close();
          paramCanvas.drawPath(this.mPath, this.mPaintFooterIndicator);
          return;
          i7 = getHeight();
          f7 = this.mFooterLineHeight;
        case 2:
        }
      }
    }
    while ((i3 == 0) || (i2 >= j));
    Rect localRect2 = (Rect)localArrayList.get(i2);
    float f10 = localRect2.right + this.mFooterIndicatorUnderlinePadding;
    float f11 = localRect2.left - this.mFooterIndicatorUnderlinePadding;
    float f12 = f9 - f8;
    this.mPath.reset();
    this.mPath.moveTo(f11, f9);
    this.mPath.lineTo(f10, f9);
    this.mPath.lineTo(f10, f12);
    this.mPath.lineTo(f11, f12);
    this.mPath.close();
    this.mPaintFooterIndicator.setAlpha((int)(255.0F * f5));
    paramCanvas.drawPath(this.mPath, this.mPaintFooterIndicator);
    this.mPaintFooterIndicator.setAlpha(255);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    float f;
    if (View.MeasureSpec.getMode(paramInt2) == 1073741824)
      f = View.MeasureSpec.getSize(paramInt2);
    while (true)
    {
      setMeasuredDimension(i, (int)f);
      return;
      this.mBounds.setEmpty();
      this.mBounds.bottom = ((int)(this.mPaintText.descent() - this.mPaintText.ascent()));
      f = this.mBounds.bottom - this.mBounds.top + this.mFooterLineHeight + this.mFooterPadding + this.mTopPadding;
      if (this.mFooterIndicatorStyle != IndicatorStyle.None)
        f += this.mFooterIndicatorHeight;
    }
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
    if (this.mScrollState == 0)
    {
      this.mCurrentPage = paramInt;
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
        float f6 = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
        float f7 = f6 - this.mLastMotionX;
        if ((!this.mIsDragging) && (Math.abs(f7) > this.mTouchSlop))
          this.mIsDragging = true;
        if (this.mIsDragging)
        {
          this.mLastMotionX = f6;
          if ((this.mViewPager.isFakeDragging()) || (this.mViewPager.beginFakeDrag()))
          {
            this.mViewPager.fakeDragBy(f7);
            continue;
            if (!this.mIsDragging)
            {
              int n = this.mViewPager.getAdapter().getCount();
              int i1 = getWidth();
              float f1 = i1 / 2.0F;
              float f2 = i1 / 6.0F;
              float f3 = f1 - f2;
              float f4 = f1 + f2;
              float f5 = paramMotionEvent.getX();
              if (f5 < f3)
              {
                if (this.mCurrentPage > 0)
                {
                  if (i != 3)
                    this.mViewPager.setCurrentItem(-1 + this.mCurrentPage);
                  return true;
                }
              }
              else if (f5 > f4)
              {
                if (this.mCurrentPage < n - 1)
                {
                  if (i != 3)
                    this.mViewPager.setCurrentItem(1 + this.mCurrentPage);
                  return true;
                }
              }
              else if ((this.mCenterItemClickListener != null) && (i != 3))
                this.mCenterItemClickListener.onCenterItemClick(this.mCurrentPage);
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
        break label472;
    label472: for (int k = 1; ; k = 0)
    {
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, k);
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
      break;
    }
  }

  public void setClipPadding(float paramFloat)
  {
    this.mClipPadding = paramFloat;
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

  public void setFooterColor(int paramInt)
  {
    this.mPaintFooterLine.setColor(paramInt);
    this.mPaintFooterIndicator.setColor(paramInt);
    invalidate();
  }

  public void setFooterIndicatorHeight(float paramFloat)
  {
    this.mFooterIndicatorHeight = paramFloat;
    invalidate();
  }

  public void setFooterIndicatorPadding(float paramFloat)
  {
    this.mFooterPadding = paramFloat;
    invalidate();
  }

  public void setFooterIndicatorStyle(IndicatorStyle paramIndicatorStyle)
  {
    this.mFooterIndicatorStyle = paramIndicatorStyle;
    invalidate();
  }

  public void setFooterLineHeight(float paramFloat)
  {
    this.mFooterLineHeight = paramFloat;
    this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
    invalidate();
  }

  public void setLinePosition(LinePosition paramLinePosition)
  {
    this.mLinePosition = paramLinePosition;
    invalidate();
  }

  public void setOnCenterItemClickListener(OnCenterItemClickListener paramOnCenterItemClickListener)
  {
    this.mCenterItemClickListener = paramOnCenterItemClickListener;
  }

  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }

  public void setSelectedBold(boolean paramBoolean)
  {
    this.mBoldText = paramBoolean;
    invalidate();
  }

  public void setSelectedColor(int paramInt)
  {
    this.mColorSelected = paramInt;
    invalidate();
  }

  public void setTextColor(int paramInt)
  {
    this.mPaintText.setColor(paramInt);
    this.mColorText = paramInt;
    invalidate();
  }

  public void setTextSize(float paramFloat)
  {
    this.mPaintText.setTextSize(paramFloat);
    invalidate();
  }

  public void setTitlePadding(float paramFloat)
  {
    this.mTitlePadding = paramFloat;
    invalidate();
  }

  public void setTopPadding(float paramFloat)
  {
    this.mTopPadding = paramFloat;
    invalidate();
  }

  public void setTypeface(Typeface paramTypeface)
  {
    this.mPaintText.setTypeface(paramTypeface);
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

  public static enum IndicatorStyle
  {
    public final int mValue;

    static
    {
      IndicatorStyle[] arrayOfIndicatorStyle = new IndicatorStyle[3];
      arrayOfIndicatorStyle[0] = None;
      arrayOfIndicatorStyle[1] = Triangle;
      arrayOfIndicatorStyle[2] = Underline;
    }

    private IndicatorStyle(int paramInt)
    {
      this.mValue = paramInt;
    }

    public static IndicatorStyle fromValue(int paramInt)
    {
      for (IndicatorStyle localIndicatorStyle : values())
        if (localIndicatorStyle.mValue == paramInt)
          return localIndicatorStyle;
      return null;
    }
  }

  public static enum LinePosition
  {
    public final int mValue;

    static
    {
      LinePosition[] arrayOfLinePosition = new LinePosition[2];
      arrayOfLinePosition[0] = Bottom;
      arrayOfLinePosition[1] = Top;
    }

    private LinePosition(int paramInt)
    {
      this.mValue = paramInt;
    }

    public static LinePosition fromValue(int paramInt)
    {
      for (LinePosition localLinePosition : values())
        if (localLinePosition.mValue == paramInt)
          return localLinePosition;
      return null;
    }
  }

  public static abstract interface OnCenterItemClickListener
  {
    public abstract void onCenterItemClick(int paramInt);
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public TitlePageIndicator.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new TitlePageIndicator.SavedState(paramAnonymousParcel, null);
      }

      public TitlePageIndicator.SavedState[] newArray(int paramAnonymousInt)
      {
        return new TitlePageIndicator.SavedState[paramAnonymousInt];
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
 * Qualified Name:     com.viewpagerindicator.TitlePageIndicator
 * JD-Core Version:    0.6.2
 */