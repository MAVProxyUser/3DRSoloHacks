package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

abstract class AbsActionBarView extends ViewGroup
{
  private static final int FADE_DURATION = 200;
  private static final Interpolator sAlphaInterpolator = new DecelerateInterpolator();
  protected ActionMenuPresenter mActionMenuPresenter;
  protected int mContentHeight;
  protected ActionMenuView mMenuView;
  protected final Context mPopupContext;
  protected boolean mSplitActionBar;
  protected ViewGroup mSplitView;
  protected boolean mSplitWhenNarrow;
  protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
  protected ViewPropertyAnimatorCompat mVisibilityAnim;

  AbsActionBarView(Context paramContext)
  {
    this(paramContext, null);
  }

  AbsActionBarView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  AbsActionBarView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedValue localTypedValue = new TypedValue();
    if ((paramContext.getTheme().resolveAttribute(R.attr.actionBarPopupTheme, localTypedValue, true)) && (localTypedValue.resourceId != 0))
    {
      this.mPopupContext = new ContextThemeWrapper(paramContext, localTypedValue.resourceId);
      return;
    }
    this.mPopupContext = paramContext;
  }

  protected static int next(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean)
      return paramInt1 - paramInt2;
    return paramInt1 + paramInt2;
  }

  public void animateToVisibility(int paramInt)
  {
    if (this.mVisibilityAnim != null)
      this.mVisibilityAnim.cancel();
    if (paramInt == 0)
    {
      if (getVisibility() != 0)
      {
        ViewCompat.setAlpha(this, 0.0F);
        if ((this.mSplitView != null) && (this.mMenuView != null))
          ViewCompat.setAlpha(this.mMenuView, 0.0F);
      }
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat3 = ViewCompat.animate(this).alpha(1.0F);
      localViewPropertyAnimatorCompat3.setDuration(200L);
      localViewPropertyAnimatorCompat3.setInterpolator(sAlphaInterpolator);
      if ((this.mSplitView != null) && (this.mMenuView != null))
      {
        ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet2 = new ViewPropertyAnimatorCompatSet();
        ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat4 = ViewCompat.animate(this.mMenuView).alpha(1.0F);
        localViewPropertyAnimatorCompat4.setDuration(200L);
        localViewPropertyAnimatorCompatSet2.setListener(this.mVisAnimListener.withFinalVisibility(localViewPropertyAnimatorCompat3, paramInt));
        localViewPropertyAnimatorCompatSet2.play(localViewPropertyAnimatorCompat3).play(localViewPropertyAnimatorCompat4);
        localViewPropertyAnimatorCompatSet2.start();
        return;
      }
      localViewPropertyAnimatorCompat3.setListener(this.mVisAnimListener.withFinalVisibility(localViewPropertyAnimatorCompat3, paramInt));
      localViewPropertyAnimatorCompat3.start();
      return;
    }
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat1 = ViewCompat.animate(this).alpha(0.0F);
    localViewPropertyAnimatorCompat1.setDuration(200L);
    localViewPropertyAnimatorCompat1.setInterpolator(sAlphaInterpolator);
    if ((this.mSplitView != null) && (this.mMenuView != null))
    {
      ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet1 = new ViewPropertyAnimatorCompatSet();
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat2 = ViewCompat.animate(this.mMenuView).alpha(0.0F);
      localViewPropertyAnimatorCompat2.setDuration(200L);
      localViewPropertyAnimatorCompatSet1.setListener(this.mVisAnimListener.withFinalVisibility(localViewPropertyAnimatorCompat1, paramInt));
      localViewPropertyAnimatorCompatSet1.play(localViewPropertyAnimatorCompat1).play(localViewPropertyAnimatorCompat2);
      localViewPropertyAnimatorCompatSet1.start();
      return;
    }
    localViewPropertyAnimatorCompat1.setListener(this.mVisAnimListener.withFinalVisibility(localViewPropertyAnimatorCompat1, paramInt));
    localViewPropertyAnimatorCompat1.start();
  }

  public boolean canShowOverflowMenu()
  {
    return (isOverflowReserved()) && (getVisibility() == 0);
  }

  public void dismissPopupMenus()
  {
    if (this.mActionMenuPresenter != null)
      this.mActionMenuPresenter.dismissPopupMenus();
  }

  public int getAnimatedVisibility()
  {
    if (this.mVisibilityAnim != null)
      return this.mVisAnimListener.mFinalVisibility;
    return getVisibility();
  }

  public int getContentHeight()
  {
    return this.mContentHeight;
  }

  public boolean hideOverflowMenu()
  {
    if (this.mActionMenuPresenter != null)
      return this.mActionMenuPresenter.hideOverflowMenu();
    return false;
  }

  public boolean isOverflowMenuShowPending()
  {
    if (this.mActionMenuPresenter != null)
      return this.mActionMenuPresenter.isOverflowMenuShowPending();
    return false;
  }

  public boolean isOverflowMenuShowing()
  {
    if (this.mActionMenuPresenter != null)
      return this.mActionMenuPresenter.isOverflowMenuShowing();
    return false;
  }

  public boolean isOverflowReserved()
  {
    return (this.mActionMenuPresenter != null) && (this.mActionMenuPresenter.isOverflowReserved());
  }

  protected int measureChildView(View paramView, int paramInt1, int paramInt2, int paramInt3)
  {
    paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt1, -2147483648), paramInt2);
    return Math.max(0, paramInt1 - paramView.getMeasuredWidth() - paramInt3);
  }

  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 8)
      super.onConfigurationChanged(paramConfiguration);
    TypedArray localTypedArray = getContext().obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
    setContentHeight(localTypedArray.getLayoutDimension(R.styleable.ActionBar_height, 0));
    localTypedArray.recycle();
    if (this.mActionMenuPresenter != null)
      this.mActionMenuPresenter.onConfigurationChanged(paramConfiguration);
  }

  protected int positionChild(View paramView, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    int i = paramView.getMeasuredWidth();
    int j = paramView.getMeasuredHeight();
    int k = paramInt2 + (paramInt3 - j) / 2;
    if (paramBoolean)
      paramView.layout(paramInt1 - i, k, paramInt1, k + j);
    while (true)
    {
      if (paramBoolean)
        i = -i;
      return i;
      paramView.layout(paramInt1, k, paramInt1 + i, k + j);
    }
  }

  public void postShowOverflowMenu()
  {
    post(new Runnable()
    {
      public void run()
      {
        AbsActionBarView.this.showOverflowMenu();
      }
    });
  }

  public void setContentHeight(int paramInt)
  {
    this.mContentHeight = paramInt;
    requestLayout();
  }

  public void setSplitToolbar(boolean paramBoolean)
  {
    this.mSplitActionBar = paramBoolean;
  }

  public void setSplitView(ViewGroup paramViewGroup)
  {
    this.mSplitView = paramViewGroup;
  }

  public void setSplitWhenNarrow(boolean paramBoolean)
  {
    this.mSplitWhenNarrow = paramBoolean;
  }

  public boolean showOverflowMenu()
  {
    if (this.mActionMenuPresenter != null)
      return this.mActionMenuPresenter.showOverflowMenu();
    return false;
  }

  protected class VisibilityAnimListener
    implements ViewPropertyAnimatorListener
  {
    private boolean mCanceled = false;
    int mFinalVisibility;

    protected VisibilityAnimListener()
    {
    }

    public void onAnimationCancel(View paramView)
    {
      this.mCanceled = true;
    }

    public void onAnimationEnd(View paramView)
    {
      if (this.mCanceled);
      do
      {
        return;
        AbsActionBarView.this.mVisibilityAnim = null;
        AbsActionBarView.this.setVisibility(this.mFinalVisibility);
      }
      while ((AbsActionBarView.this.mSplitView == null) || (AbsActionBarView.this.mMenuView == null));
      AbsActionBarView.this.mMenuView.setVisibility(this.mFinalVisibility);
    }

    public void onAnimationStart(View paramView)
    {
      AbsActionBarView.this.setVisibility(0);
      this.mCanceled = false;
    }

    public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, int paramInt)
    {
      AbsActionBarView.this.mVisibilityAnim = paramViewPropertyAnimatorCompat;
      this.mFinalVisibility = paramInt;
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.AbsActionBarView
 * JD-Core Version:    0.6.2
 */