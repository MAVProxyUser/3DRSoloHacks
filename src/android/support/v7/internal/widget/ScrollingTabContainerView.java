package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.appcompat.R.attr;
import android.support.v7.internal.view.ActionBarPolicy;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ScrollingTabContainerView extends HorizontalScrollView
  implements AdapterViewCompat.OnItemClickListener
{
  private static final int FADE_DURATION = 200;
  private static final String TAG = "ScrollingTabContainerView";
  private static final Interpolator sAlphaInterpolator = new DecelerateInterpolator();
  private boolean mAllowCollapse;
  private int mContentHeight;
  int mMaxTabWidth;
  private int mSelectedTabIndex;
  int mStackedTabMaxWidth;
  private TabClickListener mTabClickListener;
  private LinearLayoutCompat mTabLayout;
  Runnable mTabSelector;
  private SpinnerCompat mTabSpinner;
  protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
  protected ViewPropertyAnimatorCompat mVisibilityAnim;

  public ScrollingTabContainerView(Context paramContext)
  {
    super(paramContext);
    setHorizontalScrollBarEnabled(false);
    ActionBarPolicy localActionBarPolicy = ActionBarPolicy.get(paramContext);
    setContentHeight(localActionBarPolicy.getTabContainerHeight());
    this.mStackedTabMaxWidth = localActionBarPolicy.getStackedTabMaxWidth();
    this.mTabLayout = createTabLayout();
    addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
  }

  private SpinnerCompat createSpinner()
  {
    SpinnerCompat localSpinnerCompat = new SpinnerCompat(getContext(), null, R.attr.actionDropDownStyle);
    localSpinnerCompat.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
    localSpinnerCompat.setOnItemClickListenerInt(this);
    return localSpinnerCompat;
  }

  private LinearLayoutCompat createTabLayout()
  {
    LinearLayoutCompat localLinearLayoutCompat = new LinearLayoutCompat(getContext(), null, R.attr.actionBarTabBarStyle);
    localLinearLayoutCompat.setMeasureWithLargestChildEnabled(true);
    localLinearLayoutCompat.setGravity(17);
    localLinearLayoutCompat.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
    return localLinearLayoutCompat;
  }

  private TabView createTabView(ActionBar.Tab paramTab, boolean paramBoolean)
  {
    TabView localTabView = new TabView(getContext(), paramTab, paramBoolean);
    if (paramBoolean)
    {
      localTabView.setBackgroundDrawable(null);
      localTabView.setLayoutParams(new AbsListView.LayoutParams(-1, this.mContentHeight));
      return localTabView;
    }
    localTabView.setFocusable(true);
    if (this.mTabClickListener == null)
      this.mTabClickListener = new TabClickListener(null);
    localTabView.setOnClickListener(this.mTabClickListener);
    return localTabView;
  }

  private boolean isCollapsed()
  {
    return (this.mTabSpinner != null) && (this.mTabSpinner.getParent() == this);
  }

  private void performCollapse()
  {
    if (isCollapsed())
      return;
    if (this.mTabSpinner == null)
      this.mTabSpinner = createSpinner();
    removeView(this.mTabLayout);
    addView(this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
    if (this.mTabSpinner.getAdapter() == null)
      this.mTabSpinner.setAdapter(new TabAdapter(null));
    if (this.mTabSelector != null)
    {
      removeCallbacks(this.mTabSelector);
      this.mTabSelector = null;
    }
    this.mTabSpinner.setSelection(this.mSelectedTabIndex);
  }

  private boolean performExpand()
  {
    if (!isCollapsed())
      return false;
    removeView(this.mTabSpinner);
    addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
    setTabSelected(this.mTabSpinner.getSelectedItemPosition());
    return false;
  }

  public void addTab(ActionBar.Tab paramTab, int paramInt, boolean paramBoolean)
  {
    TabView localTabView = createTabView(paramTab, false);
    this.mTabLayout.addView(localTabView, paramInt, new LinearLayoutCompat.LayoutParams(0, -1, 1.0F));
    if (this.mTabSpinner != null)
      ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
    if (paramBoolean)
      localTabView.setSelected(true);
    if (this.mAllowCollapse)
      requestLayout();
  }

  public void addTab(ActionBar.Tab paramTab, boolean paramBoolean)
  {
    TabView localTabView = createTabView(paramTab, false);
    this.mTabLayout.addView(localTabView, new LinearLayoutCompat.LayoutParams(0, -1, 1.0F));
    if (this.mTabSpinner != null)
      ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
    if (paramBoolean)
      localTabView.setSelected(true);
    if (this.mAllowCollapse)
      requestLayout();
  }

  public void animateToTab(int paramInt)
  {
    final View localView = this.mTabLayout.getChildAt(paramInt);
    if (this.mTabSelector != null)
      removeCallbacks(this.mTabSelector);
    this.mTabSelector = new Runnable()
    {
      public void run()
      {
        int i = localView.getLeft() - (ScrollingTabContainerView.this.getWidth() - localView.getWidth()) / 2;
        ScrollingTabContainerView.this.smoothScrollTo(i, 0);
        ScrollingTabContainerView.this.mTabSelector = null;
      }
    };
    post(this.mTabSelector);
  }

  public void animateToVisibility(int paramInt)
  {
    if (this.mVisibilityAnim != null)
      this.mVisibilityAnim.cancel();
    if (paramInt == 0)
    {
      if (getVisibility() != 0)
        ViewCompat.setAlpha(this, 0.0F);
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat2 = ViewCompat.animate(this).alpha(1.0F);
      localViewPropertyAnimatorCompat2.setDuration(200L);
      localViewPropertyAnimatorCompat2.setInterpolator(sAlphaInterpolator);
      localViewPropertyAnimatorCompat2.setListener(this.mVisAnimListener.withFinalVisibility(localViewPropertyAnimatorCompat2, paramInt));
      localViewPropertyAnimatorCompat2.start();
      return;
    }
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat1 = ViewCompat.animate(this).alpha(0.0F);
    localViewPropertyAnimatorCompat1.setDuration(200L);
    localViewPropertyAnimatorCompat1.setInterpolator(sAlphaInterpolator);
    localViewPropertyAnimatorCompat1.setListener(this.mVisAnimListener.withFinalVisibility(localViewPropertyAnimatorCompat1, paramInt));
    localViewPropertyAnimatorCompat1.start();
  }

  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mTabSelector != null)
      post(this.mTabSelector);
  }

  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 8)
      super.onConfigurationChanged(paramConfiguration);
    ActionBarPolicy localActionBarPolicy = ActionBarPolicy.get(getContext());
    setContentHeight(localActionBarPolicy.getTabContainerHeight());
    this.mStackedTabMaxWidth = localActionBarPolicy.getStackedTabMaxWidth();
  }

  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mTabSelector != null)
      removeCallbacks(this.mTabSelector);
  }

  public void onItemClick(AdapterViewCompat<?> paramAdapterViewCompat, View paramView, int paramInt, long paramLong)
  {
    ((TabView)paramView).getTab().select();
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    boolean bool;
    label70: label85: int k;
    int m;
    if (i == 1073741824)
    {
      bool = true;
      setFillViewport(bool);
      int j = this.mTabLayout.getChildCount();
      if ((j <= 1) || ((i != 1073741824) && (i != -2147483648)))
        break label204;
      if (j <= 2)
        break label191;
      this.mMaxTabWidth = ((int)(0.4F * View.MeasureSpec.getSize(paramInt1)));
      this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
      k = View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
      if ((bool) || (!this.mAllowCollapse))
        break label212;
      m = 1;
      label112: if (m == 0)
        break label226;
      this.mTabLayout.measure(0, k);
      if (this.mTabLayout.getMeasuredWidth() <= View.MeasureSpec.getSize(paramInt1))
        break label218;
      performCollapse();
    }
    while (true)
    {
      int n = getMeasuredWidth();
      super.onMeasure(paramInt1, k);
      int i1 = getMeasuredWidth();
      if ((bool) && (n != i1))
        setTabSelected(this.mSelectedTabIndex);
      return;
      bool = false;
      break;
      label191: this.mMaxTabWidth = (View.MeasureSpec.getSize(paramInt1) / 2);
      break label70;
      label204: this.mMaxTabWidth = -1;
      break label85;
      label212: m = 0;
      break label112;
      label218: performExpand();
      continue;
      label226: performExpand();
    }
  }

  public void removeAllTabs()
  {
    this.mTabLayout.removeAllViews();
    if (this.mTabSpinner != null)
      ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
    if (this.mAllowCollapse)
      requestLayout();
  }

  public void removeTabAt(int paramInt)
  {
    this.mTabLayout.removeViewAt(paramInt);
    if (this.mTabSpinner != null)
      ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
    if (this.mAllowCollapse)
      requestLayout();
  }

  public void setAllowCollapse(boolean paramBoolean)
  {
    this.mAllowCollapse = paramBoolean;
  }

  public void setContentHeight(int paramInt)
  {
    this.mContentHeight = paramInt;
    requestLayout();
  }

  public void setTabSelected(int paramInt)
  {
    this.mSelectedTabIndex = paramInt;
    int i = this.mTabLayout.getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView = this.mTabLayout.getChildAt(j);
      if (j == paramInt);
      for (boolean bool = true; ; bool = false)
      {
        localView.setSelected(bool);
        if (bool)
          animateToTab(paramInt);
        j++;
        break;
      }
    }
    if ((this.mTabSpinner != null) && (paramInt >= 0))
      this.mTabSpinner.setSelection(paramInt);
  }

  public void updateTab(int paramInt)
  {
    ((TabView)this.mTabLayout.getChildAt(paramInt)).update();
    if (this.mTabSpinner != null)
      ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
    if (this.mAllowCollapse)
      requestLayout();
  }

  private class TabAdapter extends BaseAdapter
  {
    private TabAdapter()
    {
    }

    public int getCount()
    {
      return ScrollingTabContainerView.this.mTabLayout.getChildCount();
    }

    public Object getItem(int paramInt)
    {
      return ((ScrollingTabContainerView.TabView)ScrollingTabContainerView.this.mTabLayout.getChildAt(paramInt)).getTab();
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
        return ScrollingTabContainerView.this.createTabView((ActionBar.Tab)getItem(paramInt), true);
      ((ScrollingTabContainerView.TabView)paramView).bindTab((ActionBar.Tab)getItem(paramInt));
      return paramView;
    }
  }

  private class TabClickListener
    implements View.OnClickListener
  {
    private TabClickListener()
    {
    }

    public void onClick(View paramView)
    {
      ((ScrollingTabContainerView.TabView)paramView).getTab().select();
      int i = ScrollingTabContainerView.this.mTabLayout.getChildCount();
      int j = 0;
      if (j < i)
      {
        View localView = ScrollingTabContainerView.this.mTabLayout.getChildAt(j);
        if (localView == paramView);
        for (boolean bool = true; ; bool = false)
        {
          localView.setSelected(bool);
          j++;
          break;
        }
      }
    }
  }

  private class TabView extends LinearLayoutCompat
    implements View.OnLongClickListener
  {
    private final int[] BG_ATTRS = { 16842964 };
    private View mCustomView;
    private ImageView mIconView;
    private ActionBar.Tab mTab;
    private TextView mTextView;

    public TabView(Context paramTab, ActionBar.Tab paramBoolean, boolean arg4)
    {
      super(null, R.attr.actionBarTabStyle);
      this.mTab = paramBoolean;
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramTab, null, this.BG_ATTRS, R.attr.actionBarTabStyle, 0);
      if (localTintTypedArray.hasValue(0))
        setBackgroundDrawable(localTintTypedArray.getDrawable(0));
      localTintTypedArray.recycle();
      int i;
      if (i != 0)
        setGravity(8388627);
      update();
    }

    public void bindTab(ActionBar.Tab paramTab)
    {
      this.mTab = paramTab;
      update();
    }

    public ActionBar.Tab getTab()
    {
      return this.mTab;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(ActionBar.Tab.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      if (Build.VERSION.SDK_INT >= 14)
        paramAccessibilityNodeInfo.setClassName(ActionBar.Tab.class.getName());
    }

    public boolean onLongClick(View paramView)
    {
      int[] arrayOfInt = new int[2];
      getLocationOnScreen(arrayOfInt);
      Context localContext = getContext();
      int i = getWidth();
      int j = getHeight();
      int k = localContext.getResources().getDisplayMetrics().widthPixels;
      Toast localToast = Toast.makeText(localContext, this.mTab.getContentDescription(), 0);
      localToast.setGravity(49, arrayOfInt[0] + i / 2 - k / 2, j);
      localToast.show();
      return true;
    }

    public void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if ((ScrollingTabContainerView.this.mMaxTabWidth > 0) && (getMeasuredWidth() > ScrollingTabContainerView.this.mMaxTabWidth))
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(ScrollingTabContainerView.this.mMaxTabWidth, 1073741824), paramInt2);
    }

    public void setSelected(boolean paramBoolean)
    {
      if (isSelected() != paramBoolean);
      for (int i = 1; ; i = 0)
      {
        super.setSelected(paramBoolean);
        if ((i != 0) && (paramBoolean))
          sendAccessibilityEvent(4);
        return;
      }
    }

    public void update()
    {
      ActionBar.Tab localTab = this.mTab;
      View localView = localTab.getCustomView();
      if (localView != null)
      {
        ViewParent localViewParent = localView.getParent();
        if (localViewParent != this)
        {
          if (localViewParent != null)
            ((ViewGroup)localViewParent).removeView(localView);
          addView(localView);
        }
        this.mCustomView = localView;
        if (this.mTextView != null)
          this.mTextView.setVisibility(8);
        if (this.mIconView != null)
        {
          this.mIconView.setVisibility(8);
          this.mIconView.setImageDrawable(null);
        }
        return;
      }
      if (this.mCustomView != null)
      {
        removeView(this.mCustomView);
        this.mCustomView = null;
      }
      Drawable localDrawable = localTab.getIcon();
      CharSequence localCharSequence = localTab.getText();
      int i;
      if (localDrawable != null)
      {
        if (this.mIconView == null)
        {
          ImageView localImageView = new ImageView(getContext());
          LinearLayoutCompat.LayoutParams localLayoutParams2 = new LinearLayoutCompat.LayoutParams(-2, -2);
          localLayoutParams2.gravity = 16;
          localImageView.setLayoutParams(localLayoutParams2);
          addView(localImageView, 0);
          this.mIconView = localImageView;
        }
        this.mIconView.setImageDrawable(localDrawable);
        this.mIconView.setVisibility(0);
        if (TextUtils.isEmpty(localCharSequence))
          break label372;
        i = 1;
        label213: if (i == 0)
          break label378;
        if (this.mTextView == null)
        {
          AppCompatTextView localAppCompatTextView = new AppCompatTextView(getContext(), null, R.attr.actionBarTabTextStyle);
          localAppCompatTextView.setEllipsize(TextUtils.TruncateAt.END);
          LinearLayoutCompat.LayoutParams localLayoutParams1 = new LinearLayoutCompat.LayoutParams(-2, -2);
          localLayoutParams1.gravity = 16;
          localAppCompatTextView.setLayoutParams(localLayoutParams1);
          addView(localAppCompatTextView);
          this.mTextView = localAppCompatTextView;
        }
        this.mTextView.setText(localCharSequence);
        this.mTextView.setVisibility(0);
      }
      while (true)
      {
        if (this.mIconView != null)
          this.mIconView.setContentDescription(localTab.getContentDescription());
        if ((i != 0) || (TextUtils.isEmpty(localTab.getContentDescription())))
          break label405;
        setOnLongClickListener(this);
        return;
        if (this.mIconView == null)
          break;
        this.mIconView.setVisibility(8);
        this.mIconView.setImageDrawable(null);
        break;
        label372: i = 0;
        break label213;
        label378: if (this.mTextView != null)
        {
          this.mTextView.setVisibility(8);
          this.mTextView.setText(null);
        }
      }
      label405: setOnLongClickListener(null);
      setLongClickable(false);
    }
  }

  protected class VisibilityAnimListener
    implements ViewPropertyAnimatorListener
  {
    private boolean mCanceled = false;
    private int mFinalVisibility;

    protected VisibilityAnimListener()
    {
    }

    public void onAnimationCancel(View paramView)
    {
      this.mCanceled = true;
    }

    public void onAnimationEnd(View paramView)
    {
      if (this.mCanceled)
        return;
      ScrollingTabContainerView.this.mVisibilityAnim = null;
      ScrollingTabContainerView.this.setVisibility(this.mFinalVisibility);
    }

    public void onAnimationStart(View paramView)
    {
      ScrollingTabContainerView.this.setVisibility(0);
      this.mCanceled = false;
    }

    public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, int paramInt)
    {
      this.mFinalVisibility = paramInt;
      ScrollingTabContainerView.this.mVisibilityAnim = paramViewPropertyAnimatorCompat;
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ScrollingTabContainerView
 * JD-Core Version:    0.6.2
 */