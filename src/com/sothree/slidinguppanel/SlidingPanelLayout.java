package com.sothree.slidinguppanel;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.sothree.slidinguppanel.library.R.styleable;

public abstract class SlidingPanelLayout extends ViewGroup
{
  protected static final float DEFAULT_ANCHOR_POINT = 1.0F;
  protected static final int[] DEFAULT_ATTRS = { 16842927 };
  protected static final boolean DEFAULT_CLIP_PANEL_FLAG = true;
  protected static final int DEFAULT_FADE_COLOR = -1728053248;
  protected static final int DEFAULT_MIN_FLING_VELOCITY = 1200;
  protected static final boolean DEFAULT_OVERLAY_FLAG;
  protected static final int DEFAULT_PARALAX_OFFSET;
  protected static PanelState DEFAULT_SLIDE_STATE = PanelState.COLLAPSED;
  protected float mAnchorPoint = 1.0F;
  protected boolean mClipPanel = true;
  protected int mCoveredFadeColor = -1728053248;
  protected final Paint mCoveredFadePaint = new Paint();
  protected ViewDragHelper mDragHelper;
  protected View mDragView;
  protected int mDragViewResId = -1;
  protected boolean mFirstLayout = true;
  protected float mInitialMotionX;
  protected float mInitialMotionY;
  protected boolean mIsSliding;
  protected boolean mIsTouchEnabled;
  protected boolean mIsUnableToDrag;
  protected boolean mIsUsingDragViewTouchEvents;
  protected View mMainView;
  protected int mMinFlingVelocity = 1200;
  protected boolean mOverlayContent = false;
  protected PanelSlideListener mPanelSlideListener;
  protected int mParallaxOffset = -1;
  protected Drawable mShadowDrawable;
  protected float mSlideOffset;
  protected int mSlideRange;
  protected PanelState mSlideState = PanelState.COLLAPSED;
  protected View mSlideableView;
  protected final Rect mTmpRect = new Rect();

  public SlidingPanelLayout(Context paramContext)
  {
    super(paramContext);
  }

  public SlidingPanelLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public SlidingPanelLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (paramAttributeSet != null)
    {
      TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(paramAttributeSet, DEFAULT_ATTRS);
      if (localTypedArray1 != null)
        setGravity(localTypedArray1.getInt(0, 0));
      localTypedArray1.recycle();
      TypedArray localTypedArray2 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SlidingPanelLayout);
      if (localTypedArray2 != null)
      {
        this.mParallaxOffset = localTypedArray2.getDimensionPixelSize(R.styleable.SlidingPanelLayout_umanoParalaxOffset, -1);
        this.mMinFlingVelocity = localTypedArray2.getInt(R.styleable.SlidingPanelLayout_umanoFlingVelocity, 1200);
        this.mCoveredFadeColor = localTypedArray2.getColor(R.styleable.SlidingPanelLayout_umanoFadeColor, -1728053248);
        this.mDragViewResId = localTypedArray2.getResourceId(R.styleable.SlidingPanelLayout_umanoDragView, -1);
        this.mOverlayContent = localTypedArray2.getBoolean(R.styleable.SlidingPanelLayout_umanoOverlay, false);
        this.mClipPanel = localTypedArray2.getBoolean(R.styleable.SlidingPanelLayout_umanoClipPanel, true);
        this.mAnchorPoint = localTypedArray2.getFloat(R.styleable.SlidingPanelLayout_umanoAnchorPoint, 1.0F);
        this.mSlideState = PanelState.values()[localTypedArray2.getInt(R.styleable.SlidingPanelLayout_umanoInitialState, DEFAULT_SLIDE_STATE.ordinal())];
      }
      localTypedArray2.recycle();
    }
    float f = paramContext.getResources().getDisplayMetrics().density;
    if (this.mParallaxOffset == -1)
      this.mParallaxOffset = ((int)(0.0F * f));
    setWillNotDraw(false);
    this.mIsTouchEnabled = true;
  }

  protected static boolean hasOpaqueBackground(View paramView)
  {
    Drawable localDrawable = paramView.getBackground();
    return (localDrawable != null) && (localDrawable.getOpacity() == -1);
  }

  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramView instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      int i = paramView.getScrollX();
      int j = paramView.getScrollY();
      for (int k = -1 + localViewGroup.getChildCount(); k >= 0; k--)
      {
        View localView = localViewGroup.getChildAt(k);
        if ((paramInt2 + i >= localView.getLeft()) && (paramInt2 + i < localView.getRight()) && (paramInt3 + j >= localView.getTop()) && (paramInt3 + j < localView.getBottom()) && (canScroll(localView, true, paramInt1, paramInt2 + i - localView.getLeft(), paramInt3 + j - localView.getTop())))
          return true;
      }
    }
    return (paramBoolean) && (ViewCompat.canScrollHorizontally(paramView, -paramInt1));
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return ((paramLayoutParams instanceof LayoutParams)) && (super.checkLayoutParams(paramLayoutParams));
  }

  public void computeScroll()
  {
    if ((this.mDragHelper != null) && (this.mDragHelper.continueSettling(true)))
    {
      if (!isEnabled())
        this.mDragHelper.abort();
    }
    else
      return;
    ViewCompat.postInvalidateOnAnimation(this);
  }

  void dispatchOnPanelAnchored(View paramView)
  {
    if (this.mPanelSlideListener != null)
      this.mPanelSlideListener.onPanelAnchored(paramView);
    sendAccessibilityEvent(32);
  }

  void dispatchOnPanelCollapsed(View paramView)
  {
    if (this.mPanelSlideListener != null)
      this.mPanelSlideListener.onPanelCollapsed(paramView);
    sendAccessibilityEvent(32);
  }

  void dispatchOnPanelExpanded(View paramView)
  {
    if (this.mPanelSlideListener != null)
      this.mPanelSlideListener.onPanelExpanded(paramView);
    sendAccessibilityEvent(32);
  }

  void dispatchOnPanelHidden(View paramView)
  {
    if (this.mPanelSlideListener != null)
      this.mPanelSlideListener.onPanelHidden(paramView);
    sendAccessibilityEvent(32);
  }

  void dispatchOnPanelSlide(View paramView)
  {
    if (this.mPanelSlideListener != null)
      this.mPanelSlideListener.onPanelSlide(paramView, this.mSlideOffset);
  }

  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams();
  }

  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }

  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams))
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    return new LayoutParams(paramLayoutParams);
  }

  public float getAnchorPoint()
  {
    return this.mAnchorPoint;
  }

  public int getCoveredFadeColor()
  {
    return this.mCoveredFadeColor;
  }

  public int getCurrentParalaxOffset()
  {
    int i = (int)(this.mParallaxOffset * Math.max(this.mSlideOffset, 0.0F));
    if (this.mIsSliding)
      i = -i;
    return i;
  }

  public int getMinFlingVelocity()
  {
    return this.mMinFlingVelocity;
  }

  public PanelState getPanelState()
  {
    return this.mSlideState;
  }

  public float getSlideOffset()
  {
    return this.mSlideOffset;
  }

  public boolean isClipPanel()
  {
    return this.mClipPanel;
  }

  protected boolean isDragViewUnder(int paramInt1, int paramInt2)
  {
    boolean bool = true;
    if (this.mDragView == null)
      return false;
    int[] arrayOfInt1 = new int[2];
    this.mDragView.getLocationOnScreen(arrayOfInt1);
    int[] arrayOfInt2 = new int[2];
    getLocationOnScreen(arrayOfInt2);
    int i = paramInt1 + arrayOfInt2[0];
    int j = paramInt2 + arrayOfInt2[bool];
    if ((i >= arrayOfInt1[0]) && (i < arrayOfInt1[0] + this.mDragView.getWidth()) && (j >= arrayOfInt1[bool]) && (j < arrayOfInt1[bool] + this.mDragView.getHeight()));
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  public boolean isOverlayed()
  {
    return this.mOverlayContent;
  }

  public boolean isTouchEnabled()
  {
    return (this.mIsTouchEnabled) && (this.mSlideableView != null);
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mFirstLayout = true;
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    if (this.mDragViewResId != -1)
      setDragView(findViewById(this.mDragViewResId));
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mSlideState = localSavedState.mSlideState;
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.mSlideState = this.mSlideState;
    return localSavedState;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((!isEnabled()) || (!isTouchEnabled()))
      return super.onTouchEvent(paramMotionEvent);
    this.mDragHelper.processTouchEvent(paramMotionEvent);
    return true;
  }

  void setAllChildrenVisible()
  {
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() == 4)
        localView.setVisibility(0);
      i++;
    }
  }

  public void setAnchorPoint(float paramFloat)
  {
    if ((paramFloat > 0.0F) && (paramFloat <= 1.0F))
      this.mAnchorPoint = paramFloat;
  }

  public void setClipPanel(boolean paramBoolean)
  {
    this.mClipPanel = paramBoolean;
  }

  public void setCoveredFadeColor(int paramInt)
  {
    this.mCoveredFadeColor = paramInt;
    invalidate();
  }

  public void setDragView(int paramInt)
  {
    this.mDragViewResId = paramInt;
    setDragView(findViewById(paramInt));
  }

  public void setDragView(View paramView)
  {
    if (this.mDragView != null)
      this.mDragView.setOnClickListener(null);
    this.mDragView = paramView;
    if (this.mDragView != null)
    {
      this.mDragView.setClickable(true);
      this.mDragView.setFocusable(false);
      this.mDragView.setFocusableInTouchMode(false);
      this.mDragView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if ((!SlidingPanelLayout.this.isEnabled()) || (!SlidingPanelLayout.this.isTouchEnabled()))
            return;
          if ((SlidingPanelLayout.this.mSlideState != SlidingPanelLayout.PanelState.EXPANDED) && (SlidingPanelLayout.this.mSlideState != SlidingPanelLayout.PanelState.ANCHORED))
          {
            if (SlidingPanelLayout.this.mAnchorPoint < 1.0F)
            {
              SlidingPanelLayout.this.setPanelState(SlidingPanelLayout.PanelState.ANCHORED);
              return;
            }
            SlidingPanelLayout.this.setPanelState(SlidingPanelLayout.PanelState.EXPANDED);
            return;
          }
          SlidingPanelLayout.this.setPanelState(SlidingPanelLayout.PanelState.COLLAPSED);
        }
      });
    }
  }

  public void setEnableDragViewTouchEvents(boolean paramBoolean)
  {
    this.mIsUsingDragViewTouchEvents = paramBoolean;
  }

  public abstract void setGravity(int paramInt);

  public void setMinFlingVelocity(int paramInt)
  {
    this.mMinFlingVelocity = paramInt;
  }

  public void setOverlayed(boolean paramBoolean)
  {
    this.mOverlayContent = paramBoolean;
  }

  public void setPanelSlideListener(PanelSlideListener paramPanelSlideListener)
  {
    this.mPanelSlideListener = paramPanelSlideListener;
  }

  public abstract void setPanelState(PanelState paramPanelState);

  public void setParalaxOffset(int paramInt)
  {
    this.mParallaxOffset = paramInt;
    if (!this.mFirstLayout)
      requestLayout();
  }

  public void setTouchEnabled(boolean paramBoolean)
  {
    this.mIsTouchEnabled = paramBoolean;
  }

  void updateObscuredViewVisibility()
  {
    if (getChildCount() == 0)
      return;
    int i = getPaddingLeft();
    int j = getWidth() - getPaddingRight();
    int k = getPaddingTop();
    int m = getHeight() - getPaddingBottom();
    int i1;
    int i2;
    int i3;
    int n;
    View localView;
    if ((this.mSlideableView != null) && (hasOpaqueBackground(this.mSlideableView)))
    {
      i1 = this.mSlideableView.getLeft();
      i2 = this.mSlideableView.getRight();
      i3 = this.mSlideableView.getTop();
      n = this.mSlideableView.getBottom();
      localView = getChildAt(0);
      int i4 = Math.max(i, localView.getLeft());
      int i5 = Math.max(k, localView.getTop());
      int i6 = Math.min(j, localView.getRight());
      int i7 = Math.min(m, localView.getBottom());
      if ((i4 < i1) || (i5 < i3) || (i6 > i2) || (i7 > n))
        break label198;
    }
    label198: for (int i8 = 4; ; i8 = 0)
    {
      localView.setVisibility(i8);
      return;
      n = 0;
      i1 = 0;
      i2 = 0;
      i3 = 0;
      break;
    }
  }

  public static class LayoutParams extends ViewGroup.MarginLayoutParams
  {
    private static final int[] ATTRS = { 16843137 };

    public LayoutParams()
    {
      super(-1);
    }

    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext.obtainStyledAttributes(paramAttributeSet, ATTRS).recycle();
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }

    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }

    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
    }
  }

  public static abstract interface PanelSlideListener
  {
    public abstract void onPanelAnchored(View paramView);

    public abstract void onPanelCollapsed(View paramView);

    public abstract void onPanelExpanded(View paramView);

    public abstract void onPanelHidden(View paramView);

    public abstract void onPanelSlide(View paramView, float paramFloat);
  }

  public static enum PanelState
  {
    static
    {
      COLLAPSED = new PanelState("COLLAPSED", 1);
      ANCHORED = new PanelState("ANCHORED", 2);
      HIDDEN = new PanelState("HIDDEN", 3);
      DRAGGING = new PanelState("DRAGGING", 4);
      PanelState[] arrayOfPanelState = new PanelState[5];
      arrayOfPanelState[0] = EXPANDED;
      arrayOfPanelState[1] = COLLAPSED;
      arrayOfPanelState[2] = ANCHORED;
      arrayOfPanelState[3] = HIDDEN;
      arrayOfPanelState[4] = DRAGGING;
    }
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public SlidingPanelLayout.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new SlidingPanelLayout.SavedState(paramAnonymousParcel, null);
      }

      public SlidingPanelLayout.SavedState[] newArray(int paramAnonymousInt)
      {
        return new SlidingPanelLayout.SavedState[paramAnonymousInt];
      }
    };
    SlidingPanelLayout.PanelState mSlideState;

    private SavedState(Parcel paramParcel)
    {
      super();
      try
      {
        this.mSlideState = ((SlidingPanelLayout.PanelState)Enum.valueOf(SlidingPanelLayout.PanelState.class, paramParcel.readString()));
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        this.mSlideState = SlidingPanelLayout.PanelState.COLLAPSED;
      }
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeString(this.mSlideState.toString());
    }
  }

  public static class SimplePanelSlideListener
    implements SlidingPanelLayout.PanelSlideListener
  {
    public void onPanelAnchored(View paramView)
    {
    }

    public void onPanelCollapsed(View paramView)
    {
    }

    public void onPanelExpanded(View paramView)
    {
    }

    public void onPanelHidden(View paramView)
    {
    }

    public void onPanelSlide(View paramView, float paramFloat)
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.sothree.slidinguppanel.SlidingPanelLayout
 * JD-Core Version:    0.6.2
 */