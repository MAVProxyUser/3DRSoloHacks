package com.sothree.slidinguppanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.sothree.slidinguppanel.library.R.drawable;
import com.sothree.slidinguppanel.library.R.styleable;

public class HorizontalSlidingPanelLayout extends SlidingPanelLayout
{
  private static final int DEFAULT_PANEL_WIDTH = 68;
  private static final int DEFAULT_SHADOW_WIDTH = 4;
  private static final String TAG = HorizontalSlidingPanelLayout.class.getSimpleName();
  private int mPanelWidth = -1;
  private int mShadowWidth = -1;

  public HorizontalSlidingPanelLayout(Context paramContext)
  {
    this(paramContext, null);
  }

  public HorizontalSlidingPanelLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public HorizontalSlidingPanelLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (isInEditMode())
    {
      this.mShadowDrawable = null;
      this.mDragHelper = null;
      return;
    }
    if (paramAttributeSet != null)
    {
      paramContext.obtainStyledAttributes(paramAttributeSet, DEFAULT_ATTRS).recycle();
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.HorizontalSlidingPanelLayout);
      if (localTypedArray != null)
      {
        this.mPanelWidth = localTypedArray.getDimensionPixelSize(R.styleable.HorizontalSlidingPanelLayout_umanoPanelWidth, -1);
        this.mShadowWidth = localTypedArray.getDimensionPixelSize(R.styleable.HorizontalSlidingPanelLayout_umanoShadowWidth, -1);
      }
      localTypedArray.recycle();
    }
    float f = paramContext.getResources().getDisplayMetrics().density;
    if (this.mPanelWidth == -1)
      this.mPanelWidth = ((int)(0.5F + 68.0F * f));
    if (this.mShadowWidth == -1)
      this.mShadowWidth = ((int)(0.5F + 4.0F * f));
    if (this.mShadowWidth > 0)
      if (this.mIsSliding)
        this.mShadowDrawable = getResources().getDrawable(R.drawable.to_start_of_shadow);
    while (true)
    {
      this.mDragHelper = ViewDragHelper.create(this, 0.5F, new DragHelperCallback(null));
      this.mDragHelper.setMinVelocity(f * this.mMinFlingVelocity);
      return;
      this.mShadowDrawable = getResources().getDrawable(R.drawable.to_end_of_shadow);
      continue;
      this.mShadowDrawable = null;
    }
  }

  private int computePanelStartPosition(float paramFloat)
  {
    if (this.mSlideableView != null);
    int j;
    for (int i = this.mSlideableView.getMeasuredWidth(); ; i = 0)
    {
      j = (int)(paramFloat * this.mSlideRange);
      if (!this.mIsSliding)
        break;
      return getMeasuredWidth() - getPaddingRight() - this.mPanelWidth - j;
    }
    return j + (getPaddingLeft() - i + this.mPanelWidth);
  }

  private float computeSlideOffset(int paramInt)
  {
    int i = computePanelStartPosition(0.0F);
    if (this.mIsSliding)
      return (i - paramInt) / this.mSlideRange;
    return (paramInt - i) / this.mSlideRange;
  }

  @SuppressLint({"NewApi"})
  private void onPanelDragged(int paramInt)
  {
    this.mSlideState = SlidingPanelLayout.PanelState.DRAGGING;
    this.mSlideOffset = computeSlideOffset(paramInt);
    int k;
    SlidingPanelLayout.LayoutParams localLayoutParams;
    int i;
    if ((this.mParallaxOffset > 0) && (this.mSlideOffset >= 0.0F))
    {
      k = getCurrentParalaxOffset();
      if (Build.VERSION.SDK_INT >= 11)
        this.mMainView.setTranslationX(k);
    }
    else
    {
      dispatchOnPanelSlide(this.mSlideableView);
      localLayoutParams = (SlidingPanelLayout.LayoutParams)this.mMainView.getLayoutParams();
      i = getWidth() - getPaddingEnd() - getPaddingStart() - this.mPanelWidth;
      if ((this.mSlideOffset > 0.0F) || (this.mOverlayContent))
        break label180;
      if (!this.mIsSliding)
        break label156;
      j = paramInt - getPaddingEnd();
      localLayoutParams.width = j;
      this.mMainView.requestLayout();
    }
    label156: label180: 
    while ((localLayoutParams.width == i) || (this.mOverlayContent))
      while (true)
      {
        return;
        AnimatorProxy.wrap(this.mMainView).setTranslationX(k);
        break;
        int j = getWidth() - getPaddingEnd() - this.mSlideableView.getMeasuredWidth() - paramInt;
      }
    localLayoutParams.width = i;
    this.mMainView.requestLayout();
  }

  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int i;
    int j;
    int k;
    if (this.mShadowDrawable != null)
    {
      i = this.mSlideableView.getTop();
      j = this.mSlideableView.getBottom();
      if (!this.mIsSliding)
        break label80;
      k = this.mSlideableView.getLeft() - this.mShadowWidth;
    }
    for (int m = this.mSlideableView.getRight(); ; m = this.mSlideableView.getRight() + this.mShadowWidth)
    {
      this.mShadowDrawable.setBounds(k, i, m, j);
      this.mShadowDrawable.draw(paramCanvas);
      return;
      label80: k = this.mSlideableView.getRight();
    }
  }

  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    int i = paramCanvas.save(2);
    boolean bool;
    if (this.mSlideableView != paramView)
    {
      paramCanvas.getClipBounds(this.mTmpRect);
      if (!this.mOverlayContent)
      {
        if (this.mIsSliding)
          this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
      }
      else
      {
        if (this.mClipPanel)
          paramCanvas.clipRect(this.mTmpRect);
        bool = super.drawChild(paramCanvas, paramView, paramLong);
        if ((this.mCoveredFadeColor != 0) && (this.mSlideOffset > 0.0F))
        {
          int j = (int)(((0xFF000000 & this.mCoveredFadeColor) >>> 24) * this.mSlideOffset) << 24 | 0xFFFFFF & this.mCoveredFadeColor;
          this.mCoveredFadePaint.setColor(j);
          paramCanvas.drawRect(this.mTmpRect, this.mCoveredFadePaint);
        }
      }
    }
    while (true)
    {
      paramCanvas.restoreToCount(i);
      return bool;
      this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight());
      break;
      bool = super.drawChild(paramCanvas, paramView, paramLong);
    }
  }

  public int getPanelWidth()
  {
    return this.mPanelWidth;
  }

  public int getShadowWidth()
  {
    return this.mShadowWidth;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    if ((!isEnabled()) || (!isTouchEnabled()) || ((this.mIsUnableToDrag) && (i != 0)))
    {
      this.mDragHelper.cancel();
      return super.onInterceptTouchEvent(paramMotionEvent);
    }
    if ((i == 3) || (i == 1))
    {
      this.mDragHelper.cancel();
      return false;
    }
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    switch (i)
    {
    case 1:
    default:
    case 0:
    case 2:
    }
    float f3;
    float f4;
    int j;
    do
    {
      while (true)
      {
        return this.mDragHelper.shouldInterceptTouchEvent(paramMotionEvent);
        this.mIsUnableToDrag = false;
        this.mInitialMotionX = f1;
        this.mInitialMotionY = f2;
      }
      f3 = Math.abs(f1 - this.mInitialMotionX);
      f4 = Math.abs(f2 - this.mInitialMotionY);
      j = this.mDragHelper.getTouchSlop();
      if ((this.mIsUsingDragViewTouchEvents) && (f3 < j) && (f4 > j))
        return super.onInterceptTouchEvent(paramMotionEvent);
    }
    while (((f3 <= j) || (f4 <= f3)) && (isDragViewUnder((int)this.mInitialMotionX, (int)this.mInitialMotionY)));
    this.mDragHelper.cancel();
    this.mIsUnableToDrag = true;
    return false;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getPaddingLeft();
    int j = getPaddingTop();
    int k = getChildCount();
    if (this.mFirstLayout);
    int m;
    label72: View localView;
    switch (1.$SwitchMap$com$sothree$slidinguppanel$SlidingPanelLayout$PanelState[this.mSlideState.ordinal()])
    {
    default:
      this.mSlideOffset = 0.0F;
      m = 0;
      if (m >= k)
        break label280;
      localView = getChildAt(m);
      if ((localView.getVisibility() != 8) || ((m != 0) && (!this.mFirstLayout)))
        break;
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      m++;
      break label72;
      this.mSlideOffset = 1.0F;
      break;
      this.mSlideOffset = this.mAnchorPoint;
      break;
      int i3 = computePanelStartPosition(0.0F);
      if (this.mIsSliding);
      for (int i4 = this.mPanelWidth; ; i4 = -this.mPanelWidth)
      {
        this.mSlideOffset = computeSlideOffset(i3 + i4);
        break;
      }
      int n = localView.getMeasuredWidth();
      int i1 = i;
      if (localView == this.mSlideableView)
        i1 = computePanelStartPosition(this.mSlideOffset);
      if ((!this.mIsSliding) && (localView == this.mMainView) && (!this.mOverlayContent))
        i1 = computePanelStartPosition(this.mSlideOffset) + this.mSlideableView.getMeasuredWidth();
      int i2 = j + localView.getMeasuredHeight();
      localView.layout(i1, j, i1 + n, i2);
    }
    label280: if (this.mFirstLayout)
      updateObscuredViewVisibility();
    this.mFirstLayout = false;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = View.MeasureSpec.getSize(paramInt2);
    if (i != 1073741824)
      throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
    if (k != 1073741824)
      throw new IllegalStateException("Height must have an exact value or MATCH_PARENT");
    int n = getChildCount();
    if (n != 2)
      throw new IllegalStateException("Sliding up panel layout must have exactly 2 children!");
    this.mMainView = getChildAt(0);
    this.mSlideableView = getChildAt(1);
    if (this.mDragView == null)
      setDragView(this.mSlideableView);
    if (this.mSlideableView.getVisibility() != 0)
      this.mSlideState = SlidingPanelLayout.PanelState.HIDDEN;
    int i1 = j - getPaddingLeft() - getPaddingRight();
    int i2 = 0;
    while (i2 < n)
    {
      View localView = getChildAt(i2);
      SlidingPanelLayout.LayoutParams localLayoutParams = (SlidingPanelLayout.LayoutParams)localView.getLayoutParams();
      if ((localView.getVisibility() == 8) && (i2 == 0))
      {
        i2++;
      }
      else
      {
        int i3 = i1;
        if ((localView == this.mMainView) && (!this.mOverlayContent) && (this.mSlideState != SlidingPanelLayout.PanelState.HIDDEN))
          i3 -= this.mPanelWidth;
        int i4;
        label255: int i5;
        if (localLayoutParams.width == -2)
        {
          i4 = View.MeasureSpec.makeMeasureSpec(i3, -2147483648);
          if (localLayoutParams.height != -2)
            break label350;
          i5 = View.MeasureSpec.makeMeasureSpec(m, -2147483648);
        }
        while (true)
        {
          localView.measure(i4, i5);
          if (localView != this.mSlideableView)
            break;
          this.mSlideRange = (this.mSlideableView.getMeasuredWidth() - this.mPanelWidth);
          break;
          if (localLayoutParams.width == -1)
          {
            i4 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
            break label255;
          }
          i4 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824);
          break label255;
          label350: if (localLayoutParams.height == -1)
            i5 = View.MeasureSpec.makeMeasureSpec(m, 1073741824);
          else
            i5 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824);
        }
      }
    }
    setMeasuredDimension(j, m);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3)
      this.mFirstLayout = true;
  }

  public void setGravity(int paramInt)
  {
    if ((paramInt != 3) && (paramInt != 5))
      throw new IllegalArgumentException("gravity must be set to either left or right");
    if (paramInt == 5);
    for (boolean bool = true; ; bool = false)
    {
      this.mIsSliding = bool;
      if (!this.mFirstLayout)
        requestLayout();
      return;
    }
  }

  public void setPanelState(SlidingPanelLayout.PanelState paramPanelState)
  {
    if ((paramPanelState == null) || (paramPanelState == SlidingPanelLayout.PanelState.DRAGGING))
      throw new IllegalArgumentException("Panel state cannot be null or DRAGGING.");
    if ((!isEnabled()) || (this.mSlideableView == null) || (paramPanelState == this.mSlideState) || (this.mSlideState == SlidingPanelLayout.PanelState.DRAGGING))
      return;
    if (this.mSlideState == SlidingPanelLayout.PanelState.HIDDEN)
    {
      this.mSlideableView.setVisibility(0);
      requestLayout();
    }
    switch (1.$SwitchMap$com$sothree$slidinguppanel$SlidingPanelLayout$PanelState[paramPanelState.ordinal()])
    {
    default:
    case 2:
    case 4:
    case 1:
      while (true)
      {
        this.mSlideState = paramPanelState;
        return;
        smoothSlideTo(this.mAnchorPoint, 0);
        continue;
        smoothSlideTo(0.0F, 0);
        continue;
        smoothSlideTo(1.0F, 0);
      }
    case 3:
    }
    int i = computePanelStartPosition(0.0F);
    if (this.mIsSliding);
    for (int j = this.mPanelWidth; ; j = -this.mPanelWidth)
    {
      smoothSlideTo(computeSlideOffset(i + j), 0);
      break;
    }
  }

  public void setPanelWidth(int paramInt)
  {
    this.mPanelWidth = paramInt;
    if (!this.mFirstLayout)
      requestLayout();
  }

  public void setShadowWidth(int paramInt)
  {
    this.mShadowWidth = paramInt;
    if (!this.mFirstLayout)
      invalidate();
  }

  boolean smoothSlideTo(float paramFloat, int paramInt)
  {
    if (!isEnabled());
    int i;
    do
    {
      return false;
      i = computePanelStartPosition(paramFloat);
    }
    while (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, i, this.mSlideableView.getTop()));
    setAllChildrenVisible();
    ViewCompat.postInvalidateOnAnimation(this);
    return true;
  }

  private class DragHelperCallback extends ViewDragHelper.Callback
  {
    private DragHelperCallback()
    {
    }

    public int clampViewPositionHorizontal(View paramView, int paramInt1, int paramInt2)
    {
      int i = HorizontalSlidingPanelLayout.this.computePanelStartPosition(0.0F);
      int j = HorizontalSlidingPanelLayout.this.computePanelStartPosition(1.0F);
      if (HorizontalSlidingPanelLayout.this.mIsSliding)
        return Math.min(Math.max(paramInt1, j), i);
      return Math.min(Math.max(paramInt1, i), j);
    }

    public int getViewHorizontalDragRange(View paramView)
    {
      return HorizontalSlidingPanelLayout.this.mSlideRange;
    }

    public void onViewCaptured(View paramView, int paramInt)
    {
      HorizontalSlidingPanelLayout.this.setAllChildrenVisible();
    }

    public void onViewDragStateChanged(int paramInt)
    {
      if (HorizontalSlidingPanelLayout.this.mDragHelper.getViewDragState() == 0)
      {
        HorizontalSlidingPanelLayout.this.mSlideOffset = HorizontalSlidingPanelLayout.this.computeSlideOffset(HorizontalSlidingPanelLayout.this.mSlideableView.getLeft());
        if (HorizontalSlidingPanelLayout.this.mSlideOffset != 1.0F)
          break label94;
        if (HorizontalSlidingPanelLayout.this.mSlideState != SlidingPanelLayout.PanelState.EXPANDED)
        {
          HorizontalSlidingPanelLayout.this.updateObscuredViewVisibility();
          HorizontalSlidingPanelLayout.this.mSlideState = SlidingPanelLayout.PanelState.EXPANDED;
          HorizontalSlidingPanelLayout.this.dispatchOnPanelExpanded(HorizontalSlidingPanelLayout.this.mSlideableView);
        }
      }
      label94: 
      do
      {
        do
        {
          return;
          if (HorizontalSlidingPanelLayout.this.mSlideOffset != 0.0F)
            break;
        }
        while (HorizontalSlidingPanelLayout.this.mSlideState == SlidingPanelLayout.PanelState.COLLAPSED);
        HorizontalSlidingPanelLayout.this.mSlideState = SlidingPanelLayout.PanelState.COLLAPSED;
        HorizontalSlidingPanelLayout.this.dispatchOnPanelCollapsed(HorizontalSlidingPanelLayout.this.mSlideableView);
        return;
        if (HorizontalSlidingPanelLayout.this.mSlideOffset < 0.0F)
        {
          HorizontalSlidingPanelLayout.this.mSlideState = SlidingPanelLayout.PanelState.HIDDEN;
          HorizontalSlidingPanelLayout.this.mSlideableView.setVisibility(4);
          HorizontalSlidingPanelLayout.this.dispatchOnPanelHidden(HorizontalSlidingPanelLayout.this.mSlideableView);
          return;
        }
      }
      while (HorizontalSlidingPanelLayout.this.mSlideState == SlidingPanelLayout.PanelState.ANCHORED);
      HorizontalSlidingPanelLayout.this.updateObscuredViewVisibility();
      HorizontalSlidingPanelLayout.this.mSlideState = SlidingPanelLayout.PanelState.ANCHORED;
      HorizontalSlidingPanelLayout.this.dispatchOnPanelAnchored(HorizontalSlidingPanelLayout.this.mSlideableView);
    }

    public void onViewPositionChanged(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      HorizontalSlidingPanelLayout.this.onPanelDragged(paramInt1);
      HorizontalSlidingPanelLayout.this.invalidate();
    }

    public void onViewReleased(View paramView, float paramFloat1, float paramFloat2)
    {
      float f;
      int i;
      if (HorizontalSlidingPanelLayout.this.mIsSliding)
      {
        f = -paramFloat1;
        if (f <= 0.0F)
          break label62;
        i = HorizontalSlidingPanelLayout.this.computePanelStartPosition(1.0F);
      }
      while (true)
      {
        HorizontalSlidingPanelLayout.this.mDragHelper.settleCapturedViewAt(i, paramView.getTop());
        HorizontalSlidingPanelLayout.this.invalidate();
        return;
        f = paramFloat1;
        break;
        label62: if (f < 0.0F)
          i = HorizontalSlidingPanelLayout.this.computePanelStartPosition(0.0F);
        else if ((HorizontalSlidingPanelLayout.this.mAnchorPoint != 1.0F) && (HorizontalSlidingPanelLayout.this.mSlideOffset >= (1.0F + HorizontalSlidingPanelLayout.this.mAnchorPoint) / 2.0F))
          i = HorizontalSlidingPanelLayout.this.computePanelStartPosition(1.0F);
        else if ((HorizontalSlidingPanelLayout.this.mAnchorPoint == 1.0F) && (HorizontalSlidingPanelLayout.this.mSlideOffset >= 0.5F))
          i = HorizontalSlidingPanelLayout.this.computePanelStartPosition(1.0F);
        else if ((HorizontalSlidingPanelLayout.this.mAnchorPoint != 1.0F) && (HorizontalSlidingPanelLayout.this.mSlideOffset >= HorizontalSlidingPanelLayout.this.mAnchorPoint))
          i = HorizontalSlidingPanelLayout.this.computePanelStartPosition(HorizontalSlidingPanelLayout.this.mAnchorPoint);
        else if ((HorizontalSlidingPanelLayout.this.mAnchorPoint != 1.0F) && (HorizontalSlidingPanelLayout.this.mSlideOffset >= HorizontalSlidingPanelLayout.this.mAnchorPoint / 2.0F))
          i = HorizontalSlidingPanelLayout.this.computePanelStartPosition(HorizontalSlidingPanelLayout.this.mAnchorPoint);
        else
          i = HorizontalSlidingPanelLayout.this.computePanelStartPosition(0.0F);
      }
    }

    public boolean tryCaptureView(View paramView, int paramInt)
    {
      if (HorizontalSlidingPanelLayout.this.mIsUnableToDrag);
      while (paramView != HorizontalSlidingPanelLayout.this.mSlideableView)
        return false;
      return true;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.sothree.slidinguppanel.HorizontalSlidingPanelLayout
 * JD-Core Version:    0.6.2
 */