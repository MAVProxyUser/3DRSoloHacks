package android.support.v7.internal.widget;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.id;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window.Callback;

public class ActionBarOverlayLayout extends ViewGroup
  implements DecorContentParent, NestedScrollingParent
{
  static final int[] ATTRS = arrayOfInt;
  private static final String TAG = "ActionBarOverlayLayout";
  private final int ACTION_BAR_ANIMATE_DELAY = 600;
  private ActionBarContainer mActionBarBottom;
  private int mActionBarHeight;
  private ActionBarContainer mActionBarTop;
  private ActionBarVisibilityCallback mActionBarVisibilityCallback;
  private final Runnable mAddActionBarHideOffset = new Runnable()
  {
    public void run()
    {
      ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
      ActionBarOverlayLayout.access$002(ActionBarOverlayLayout.this, ViewCompat.animate(ActionBarOverlayLayout.this.mActionBarTop).translationY(-ActionBarOverlayLayout.this.mActionBarTop.getHeight()).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener));
      if ((ActionBarOverlayLayout.this.mActionBarBottom != null) && (ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() != 8))
        ActionBarOverlayLayout.access$202(ActionBarOverlayLayout.this, ViewCompat.animate(ActionBarOverlayLayout.this.mActionBarBottom).translationY(ActionBarOverlayLayout.this.mActionBarBottom.getHeight()).setListener(ActionBarOverlayLayout.this.mBottomAnimatorListener));
    }
  };
  private boolean mAnimatingForFling;
  private final Rect mBaseContentInsets = new Rect();
  private final Rect mBaseInnerInsets = new Rect();
  private final ViewPropertyAnimatorListener mBottomAnimatorListener = new ViewPropertyAnimatorListenerAdapter()
  {
    public void onAnimationCancel(View paramAnonymousView)
    {
      ActionBarOverlayLayout.access$202(ActionBarOverlayLayout.this, null);
      ActionBarOverlayLayout.access$102(ActionBarOverlayLayout.this, false);
    }

    public void onAnimationEnd(View paramAnonymousView)
    {
      ActionBarOverlayLayout.access$202(ActionBarOverlayLayout.this, null);
      ActionBarOverlayLayout.access$102(ActionBarOverlayLayout.this, false);
    }
  };
  private ContentFrameLayout mContent;
  private final Rect mContentInsets = new Rect();
  private ViewPropertyAnimatorCompat mCurrentActionBarBottomAnimator;
  private ViewPropertyAnimatorCompat mCurrentActionBarTopAnimator;
  private DecorToolbar mDecorToolbar;
  private ScrollerCompat mFlingEstimator;
  private boolean mHasNonEmbeddedTabs;
  private boolean mHideOnContentScroll;
  private int mHideOnContentScrollReference;
  private boolean mIgnoreWindowContentOverlay;
  private final Rect mInnerInsets = new Rect();
  private final Rect mLastBaseContentInsets = new Rect();
  private final Rect mLastInnerInsets = new Rect();
  private int mLastSystemUiVisibility;
  private boolean mOverlayMode;
  private final NestedScrollingParentHelper mParentHelper;
  private final Runnable mRemoveActionBarHideOffset = new Runnable()
  {
    public void run()
    {
      ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
      ActionBarOverlayLayout.access$002(ActionBarOverlayLayout.this, ViewCompat.animate(ActionBarOverlayLayout.this.mActionBarTop).translationY(0.0F).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener));
      if ((ActionBarOverlayLayout.this.mActionBarBottom != null) && (ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() != 8))
        ActionBarOverlayLayout.access$202(ActionBarOverlayLayout.this, ViewCompat.animate(ActionBarOverlayLayout.this.mActionBarBottom).translationY(0.0F).setListener(ActionBarOverlayLayout.this.mBottomAnimatorListener));
    }
  };
  private final ViewPropertyAnimatorListener mTopAnimatorListener = new ViewPropertyAnimatorListenerAdapter()
  {
    public void onAnimationCancel(View paramAnonymousView)
    {
      ActionBarOverlayLayout.access$002(ActionBarOverlayLayout.this, null);
      ActionBarOverlayLayout.access$102(ActionBarOverlayLayout.this, false);
    }

    public void onAnimationEnd(View paramAnonymousView)
    {
      ActionBarOverlayLayout.access$002(ActionBarOverlayLayout.this, null);
      ActionBarOverlayLayout.access$102(ActionBarOverlayLayout.this, false);
    }
  };
  private Drawable mWindowContentOverlay;
  private int mWindowVisibility = 0;

  static
  {
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = R.attr.actionBarSize;
    arrayOfInt[1] = 16842841;
  }

  public ActionBarOverlayLayout(Context paramContext)
  {
    this(paramContext, null);
  }

  public ActionBarOverlayLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
    this.mParentHelper = new NestedScrollingParentHelper(this);
  }

  private void addActionBarHideOffset()
  {
    haltActionBarHideOffsetAnimations();
    this.mAddActionBarHideOffset.run();
  }

  private boolean applyInsets(View paramView, Rect paramRect, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    boolean bool = false;
    if (paramBoolean1)
    {
      int i = localLayoutParams.leftMargin;
      int j = paramRect.left;
      bool = false;
      if (i != j)
      {
        bool = true;
        localLayoutParams.leftMargin = paramRect.left;
      }
    }
    if ((paramBoolean2) && (localLayoutParams.topMargin != paramRect.top))
    {
      bool = true;
      localLayoutParams.topMargin = paramRect.top;
    }
    if ((paramBoolean4) && (localLayoutParams.rightMargin != paramRect.right))
    {
      bool = true;
      localLayoutParams.rightMargin = paramRect.right;
    }
    if ((paramBoolean3) && (localLayoutParams.bottomMargin != paramRect.bottom))
    {
      bool = true;
      localLayoutParams.bottomMargin = paramRect.bottom;
    }
    return bool;
  }

  private DecorToolbar getDecorToolbar(View paramView)
  {
    if ((paramView instanceof DecorToolbar))
      return (DecorToolbar)paramView;
    if ((paramView instanceof Toolbar))
      return ((Toolbar)paramView).getWrapper();
    throw new IllegalStateException("Can't make a decor toolbar out of " + paramView.getClass().getSimpleName());
  }

  private void haltActionBarHideOffsetAnimations()
  {
    removeCallbacks(this.mRemoveActionBarHideOffset);
    removeCallbacks(this.mAddActionBarHideOffset);
    if (this.mCurrentActionBarTopAnimator != null)
      this.mCurrentActionBarTopAnimator.cancel();
    if (this.mCurrentActionBarBottomAnimator != null)
      this.mCurrentActionBarBottomAnimator.cancel();
  }

  private void init(Context paramContext)
  {
    int i = 1;
    TypedArray localTypedArray = getContext().getTheme().obtainStyledAttributes(ATTRS);
    this.mActionBarHeight = localTypedArray.getDimensionPixelSize(0, 0);
    this.mWindowContentOverlay = localTypedArray.getDrawable(i);
    if (this.mWindowContentOverlay == null)
    {
      int j = i;
      setWillNotDraw(j);
      localTypedArray.recycle();
      if (paramContext.getApplicationInfo().targetSdkVersion >= 19)
        break label87;
    }
    while (true)
    {
      this.mIgnoreWindowContentOverlay = i;
      this.mFlingEstimator = ScrollerCompat.create(paramContext);
      return;
      int k = 0;
      break;
      label87: i = 0;
    }
  }

  private void postAddActionBarHideOffset()
  {
    haltActionBarHideOffsetAnimations();
    postDelayed(this.mAddActionBarHideOffset, 600L);
  }

  private void postRemoveActionBarHideOffset()
  {
    haltActionBarHideOffsetAnimations();
    postDelayed(this.mRemoveActionBarHideOffset, 600L);
  }

  private void removeActionBarHideOffset()
  {
    haltActionBarHideOffsetAnimations();
    this.mRemoveActionBarHideOffset.run();
  }

  private boolean shouldHideActionBarOnFling(float paramFloat1, float paramFloat2)
  {
    this.mFlingEstimator.fling(0, 0, 0, (int)paramFloat2, 0, 0, -2147483648, 2147483647);
    int i = this.mFlingEstimator.getFinalY();
    int j = this.mActionBarTop.getHeight();
    boolean bool = false;
    if (i > j)
      bool = true;
    return bool;
  }

  public boolean canShowOverflowMenu()
  {
    pullChildren();
    return this.mDecorToolbar.canShowOverflowMenu();
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }

  public void dismissPopups()
  {
    pullChildren();
    this.mDecorToolbar.dismissPopupMenus();
  }

  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    if ((this.mWindowContentOverlay != null) && (!this.mIgnoreWindowContentOverlay))
      if (this.mActionBarTop.getVisibility() != 0)
        break label82;
    label82: for (int i = (int)(0.5F + (this.mActionBarTop.getBottom() + ViewCompat.getTranslationY(this.mActionBarTop))); ; i = 0)
    {
      this.mWindowContentOverlay.setBounds(0, i, getWidth(), i + this.mWindowContentOverlay.getIntrinsicHeight());
      this.mWindowContentOverlay.draw(paramCanvas);
      return;
    }
  }

  protected boolean fitSystemWindows(Rect paramRect)
  {
    pullChildren();
    if ((0x100 & ViewCompat.getWindowSystemUiVisibility(this)) != 0);
    while (true)
    {
      boolean bool = applyInsets(this.mActionBarTop, paramRect, true, true, false, true);
      if (this.mActionBarBottom != null)
        bool |= applyInsets(this.mActionBarBottom, paramRect, true, false, true, true);
      this.mBaseInnerInsets.set(paramRect);
      ViewUtils.computeFitSystemWindows(this, this.mBaseInnerInsets, this.mBaseContentInsets);
      if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets))
      {
        bool = true;
        this.mLastBaseContentInsets.set(this.mBaseContentInsets);
      }
      if (bool)
        requestLayout();
      return true;
    }
  }

  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-1, -1);
  }

  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }

  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }

  public int getActionBarHideOffset()
  {
    if (this.mActionBarTop != null)
      return -(int)ViewCompat.getTranslationY(this.mActionBarTop);
    return 0;
  }

  public int getNestedScrollAxes()
  {
    return this.mParentHelper.getNestedScrollAxes();
  }

  public CharSequence getTitle()
  {
    pullChildren();
    return this.mDecorToolbar.getTitle();
  }

  public boolean hasIcon()
  {
    pullChildren();
    return this.mDecorToolbar.hasIcon();
  }

  public boolean hasLogo()
  {
    pullChildren();
    return this.mDecorToolbar.hasLogo();
  }

  public boolean hideOverflowMenu()
  {
    pullChildren();
    return this.mDecorToolbar.hideOverflowMenu();
  }

  public void initFeature(int paramInt)
  {
    pullChildren();
    switch (paramInt)
    {
    default:
      return;
    case 2:
      this.mDecorToolbar.initProgress();
      return;
    case 5:
      this.mDecorToolbar.initIndeterminateProgress();
      return;
    case 9:
    }
    setOverlayMode(true);
  }

  public boolean isHideOnContentScrollEnabled()
  {
    return this.mHideOnContentScroll;
  }

  public boolean isInOverlayMode()
  {
    return this.mOverlayMode;
  }

  public boolean isOverflowMenuShowPending()
  {
    pullChildren();
    return this.mDecorToolbar.isOverflowMenuShowPending();
  }

  public boolean isOverflowMenuShowing()
  {
    pullChildren();
    return this.mDecorToolbar.isOverflowMenuShowing();
  }

  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 8)
      super.onConfigurationChanged(paramConfiguration);
    init(getContext());
    ViewCompat.requestApplyInsets(this);
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    haltActionBarHideOffsetAnimations();
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    int j = getPaddingLeft();
    (paramInt3 - paramInt1 - getPaddingRight());
    int k = getPaddingTop();
    int m = paramInt4 - paramInt2 - getPaddingBottom();
    int n = 0;
    if (n < i)
    {
      View localView = getChildAt(n);
      LayoutParams localLayoutParams;
      int i1;
      int i2;
      int i3;
      if (localView.getVisibility() != 8)
      {
        localLayoutParams = (LayoutParams)localView.getLayoutParams();
        i1 = localView.getMeasuredWidth();
        i2 = localView.getMeasuredHeight();
        i3 = j + localLayoutParams.leftMargin;
        if (localView != this.mActionBarBottom)
          break label148;
      }
      label148: for (int i4 = m - i2 - localLayoutParams.bottomMargin; ; i4 = k + localLayoutParams.topMargin)
      {
        localView.layout(i3, i4, i3 + i1, i4 + i2);
        n++;
        break;
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    pullChildren();
    measureChildWithMargins(this.mActionBarTop, paramInt1, 0, paramInt2, 0);
    LayoutParams localLayoutParams1 = (LayoutParams)this.mActionBarTop.getLayoutParams();
    int i = Math.max(0, this.mActionBarTop.getMeasuredWidth() + localLayoutParams1.leftMargin + localLayoutParams1.rightMargin);
    int j = Math.max(0, this.mActionBarTop.getMeasuredHeight() + localLayoutParams1.topMargin + localLayoutParams1.bottomMargin);
    int k = ViewUtils.combineMeasuredStates(0, ViewCompat.getMeasuredState(this.mActionBarTop));
    if (this.mActionBarBottom != null)
    {
      measureChildWithMargins(this.mActionBarBottom, paramInt1, 0, paramInt2, 0);
      LayoutParams localLayoutParams3 = (LayoutParams)this.mActionBarBottom.getLayoutParams();
      i = Math.max(i, this.mActionBarBottom.getMeasuredWidth() + localLayoutParams3.leftMargin + localLayoutParams3.rightMargin);
      j = Math.max(j, this.mActionBarBottom.getMeasuredHeight() + localLayoutParams3.topMargin + localLayoutParams3.bottomMargin);
      k = ViewUtils.combineMeasuredStates(k, ViewCompat.getMeasuredState(this.mActionBarBottom));
    }
    int m;
    int i1;
    label234: int i2;
    label278: Rect localRect4;
    if ((0x100 & ViewCompat.getWindowSystemUiVisibility(this)) != 0)
    {
      m = 1;
      if (m == 0)
        break label565;
      i1 = this.mActionBarHeight;
      if ((this.mHasNonEmbeddedTabs) && (this.mActionBarTop.getTabContainer() != null))
        i1 += this.mActionBarHeight;
      boolean bool = this.mDecorToolbar.isSplit();
      i2 = 0;
      if (bool)
      {
        ActionBarContainer localActionBarContainer = this.mActionBarBottom;
        i2 = 0;
        if (localActionBarContainer != null)
        {
          if (m == 0)
            break label596;
          i2 = this.mActionBarHeight;
        }
      }
      this.mContentInsets.set(this.mBaseContentInsets);
      this.mInnerInsets.set(this.mBaseInnerInsets);
      if ((this.mOverlayMode) || (m != 0))
        break label608;
      Rect localRect3 = this.mContentInsets;
      localRect3.top = (i1 + localRect3.top);
      localRect4 = this.mContentInsets;
    }
    label565: label596: label608: Rect localRect2;
    for (localRect4.bottom = (i2 + localRect4.bottom); ; localRect2.bottom = (i2 + localRect2.bottom))
    {
      applyInsets(this.mContent, this.mContentInsets, true, true, true, true);
      if (!this.mLastInnerInsets.equals(this.mInnerInsets))
      {
        this.mLastInnerInsets.set(this.mInnerInsets);
        this.mContent.dispatchFitSystemWindows(this.mInnerInsets);
      }
      measureChildWithMargins(this.mContent, paramInt1, 0, paramInt2, 0);
      LayoutParams localLayoutParams2 = (LayoutParams)this.mContent.getLayoutParams();
      int i3 = Math.max(i, this.mContent.getMeasuredWidth() + localLayoutParams2.leftMargin + localLayoutParams2.rightMargin);
      int i4 = Math.max(j, this.mContent.getMeasuredHeight() + localLayoutParams2.topMargin + localLayoutParams2.bottomMargin);
      int i5 = ViewUtils.combineMeasuredStates(k, ViewCompat.getMeasuredState(this.mContent));
      int i6 = i3 + (getPaddingLeft() + getPaddingRight());
      int i7 = Math.max(i4 + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight());
      setMeasuredDimension(ViewCompat.resolveSizeAndState(Math.max(i6, getSuggestedMinimumWidth()), paramInt1, i5), ViewCompat.resolveSizeAndState(i7, paramInt2, i5 << 16));
      return;
      m = 0;
      break;
      int n = this.mActionBarTop.getVisibility();
      i1 = 0;
      if (n == 8)
        break label234;
      i1 = this.mActionBarTop.getMeasuredHeight();
      break label234;
      i2 = this.mActionBarBottom.getMeasuredHeight();
      break label278;
      Rect localRect1 = this.mInnerInsets;
      localRect1.top = (i1 + localRect1.top);
      localRect2 = this.mInnerInsets;
    }
  }

  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if ((!this.mHideOnContentScroll) || (!paramBoolean))
      return false;
    if (shouldHideActionBarOnFling(paramFloat1, paramFloat2))
      addActionBarHideOffset();
    while (true)
    {
      this.mAnimatingForFling = true;
      return true;
      removeActionBarHideOffset();
    }
  }

  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
  }

  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mHideOnContentScrollReference = (paramInt2 + this.mHideOnContentScrollReference);
    setActionBarHideOffset(this.mHideOnContentScrollReference);
  }

  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt)
  {
    this.mParentHelper.onNestedScrollAccepted(paramView1, paramView2, paramInt);
    this.mHideOnContentScrollReference = getActionBarHideOffset();
    haltActionBarHideOffsetAnimations();
    if (this.mActionBarVisibilityCallback != null)
      this.mActionBarVisibilityCallback.onContentScrollStarted();
  }

  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt)
  {
    if (((paramInt & 0x2) == 0) || (this.mActionBarTop.getVisibility() != 0))
      return false;
    return this.mHideOnContentScroll;
  }

  public void onStopNestedScroll(View paramView)
  {
    if ((this.mHideOnContentScroll) && (!this.mAnimatingForFling))
    {
      if (this.mHideOnContentScrollReference > this.mActionBarTop.getHeight())
        break label49;
      postRemoveActionBarHideOffset();
    }
    while (true)
    {
      if (this.mActionBarVisibilityCallback != null)
        this.mActionBarVisibilityCallback.onContentScrollStopped();
      return;
      label49: postAddActionBarHideOffset();
    }
  }

  public void onWindowSystemUiVisibilityChanged(int paramInt)
  {
    boolean bool1 = true;
    if (Build.VERSION.SDK_INT >= 16)
      super.onWindowSystemUiVisibilityChanged(paramInt);
    pullChildren();
    int i = paramInt ^ this.mLastSystemUiVisibility;
    this.mLastSystemUiVisibility = paramInt;
    boolean bool2;
    boolean bool3;
    if ((paramInt & 0x4) == 0)
    {
      bool2 = bool1;
      if ((paramInt & 0x100) == 0)
        break label122;
      bool3 = bool1;
      label51: if (this.mActionBarVisibilityCallback != null)
      {
        ActionBarVisibilityCallback localActionBarVisibilityCallback = this.mActionBarVisibilityCallback;
        if (bool3)
          break label128;
        label69: localActionBarVisibilityCallback.enableContentAnimations(bool1);
        if ((!bool2) && (bool3))
          break label133;
        this.mActionBarVisibilityCallback.showForSystem();
      }
    }
    while (true)
    {
      if (((i & 0x100) != 0) && (this.mActionBarVisibilityCallback != null))
        ViewCompat.requestApplyInsets(this);
      return;
      bool2 = false;
      break;
      label122: bool3 = false;
      break label51;
      label128: bool1 = false;
      break label69;
      label133: this.mActionBarVisibilityCallback.hideForSystem();
    }
  }

  protected void onWindowVisibilityChanged(int paramInt)
  {
    super.onWindowVisibilityChanged(paramInt);
    this.mWindowVisibility = paramInt;
    if (this.mActionBarVisibilityCallback != null)
      this.mActionBarVisibilityCallback.onWindowVisibilityChanged(paramInt);
  }

  void pullChildren()
  {
    if (this.mContent == null)
    {
      this.mContent = ((ContentFrameLayout)findViewById(R.id.action_bar_activity_content));
      this.mActionBarTop = ((ActionBarContainer)findViewById(R.id.action_bar_container));
      this.mDecorToolbar = getDecorToolbar(findViewById(R.id.action_bar));
      this.mActionBarBottom = ((ActionBarContainer)findViewById(R.id.split_action_bar));
    }
  }

  public void restoreToolbarHierarchyState(SparseArray<Parcelable> paramSparseArray)
  {
    pullChildren();
    this.mDecorToolbar.restoreHierarchyState(paramSparseArray);
  }

  public void saveToolbarHierarchyState(SparseArray<Parcelable> paramSparseArray)
  {
    pullChildren();
    this.mDecorToolbar.saveHierarchyState(paramSparseArray);
  }

  public void setActionBarHideOffset(int paramInt)
  {
    haltActionBarHideOffsetAnimations();
    int i = this.mActionBarTop.getHeight();
    int j = Math.max(0, Math.min(paramInt, i));
    ViewCompat.setTranslationY(this.mActionBarTop, -j);
    if ((this.mActionBarBottom != null) && (this.mActionBarBottom.getVisibility() != 8))
    {
      int k = (int)(j / i * this.mActionBarBottom.getHeight());
      ViewCompat.setTranslationY(this.mActionBarBottom, k);
    }
  }

  public void setActionBarVisibilityCallback(ActionBarVisibilityCallback paramActionBarVisibilityCallback)
  {
    this.mActionBarVisibilityCallback = paramActionBarVisibilityCallback;
    if (getWindowToken() != null)
    {
      this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
      if (this.mLastSystemUiVisibility != 0)
      {
        onWindowSystemUiVisibilityChanged(this.mLastSystemUiVisibility);
        ViewCompat.requestApplyInsets(this);
      }
    }
  }

  public void setHasNonEmbeddedTabs(boolean paramBoolean)
  {
    this.mHasNonEmbeddedTabs = paramBoolean;
  }

  public void setHideOnContentScrollEnabled(boolean paramBoolean)
  {
    if (paramBoolean != this.mHideOnContentScroll)
    {
      this.mHideOnContentScroll = paramBoolean;
      if (!paramBoolean)
      {
        haltActionBarHideOffsetAnimations();
        setActionBarHideOffset(0);
      }
    }
  }

  public void setIcon(int paramInt)
  {
    pullChildren();
    this.mDecorToolbar.setIcon(paramInt);
  }

  public void setIcon(Drawable paramDrawable)
  {
    pullChildren();
    this.mDecorToolbar.setIcon(paramDrawable);
  }

  public void setLogo(int paramInt)
  {
    pullChildren();
    this.mDecorToolbar.setLogo(paramInt);
  }

  public void setMenu(Menu paramMenu, MenuPresenter.Callback paramCallback)
  {
    pullChildren();
    this.mDecorToolbar.setMenu(paramMenu, paramCallback);
  }

  public void setMenuPrepared()
  {
    pullChildren();
    this.mDecorToolbar.setMenuPrepared();
  }

  public void setOverlayMode(boolean paramBoolean)
  {
    this.mOverlayMode = paramBoolean;
    if ((paramBoolean) && (getContext().getApplicationInfo().targetSdkVersion < 19));
    for (boolean bool = true; ; bool = false)
    {
      this.mIgnoreWindowContentOverlay = bool;
      return;
    }
  }

  public void setShowingForActionMode(boolean paramBoolean)
  {
  }

  public void setUiOptions(int paramInt)
  {
  }

  public void setWindowCallback(Window.Callback paramCallback)
  {
    pullChildren();
    this.mDecorToolbar.setWindowCallback(paramCallback);
  }

  public void setWindowTitle(CharSequence paramCharSequence)
  {
    pullChildren();
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }

  public boolean shouldDelayChildPressedState()
  {
    return false;
  }

  public boolean showOverflowMenu()
  {
    pullChildren();
    return this.mDecorToolbar.showOverflowMenu();
  }

  public static abstract interface ActionBarVisibilityCallback
  {
    public abstract void enableContentAnimations(boolean paramBoolean);

    public abstract void hideForSystem();

    public abstract void onContentScrollStarted();

    public abstract void onContentScrollStopped();

    public abstract void onWindowVisibilityChanged(int paramInt);

    public abstract void showForSystem();
  }

  public static class LayoutParams extends ViewGroup.MarginLayoutParams
  {
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }

    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ActionBarOverlayLayout
 * JD-Core Version:    0.6.2
 */