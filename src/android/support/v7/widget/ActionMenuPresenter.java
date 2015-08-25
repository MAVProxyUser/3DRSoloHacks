package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.ActionProvider.SubUiVisibilityListener;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.integer;
import android.support.v7.appcompat.R.layout;
import android.support.v7.internal.transition.ActionBarTransition;
import android.support.v7.internal.view.ActionBarPolicy;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.internal.view.menu.ActionMenuItemView.PopupCallback;
import android.support.v7.internal.view.menu.BaseMenuPresenter;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.view.menu.MenuView.ItemView;
import android.support.v7.internal.view.menu.SubMenuBuilder;
import android.support.v7.internal.widget.TintImageView;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;

public class ActionMenuPresenter extends BaseMenuPresenter
  implements ActionProvider.SubUiVisibilityListener
{
  private static final String TAG = "ActionMenuPresenter";
  private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
  private ActionButtonSubmenu mActionButtonPopup;
  private int mActionItemWidthLimit;
  private boolean mExpandedActionViewsExclusive;
  private int mMaxItems;
  private boolean mMaxItemsSet;
  private int mMinCellSize;
  int mOpenSubMenuId;
  private View mOverflowButton;
  private OverflowPopup mOverflowPopup;
  private ActionMenuPopupCallback mPopupCallback;
  final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback(null);
  private OpenOverflowRunnable mPostedOpenRunnable;
  private boolean mReserveOverflow;
  private boolean mReserveOverflowSet;
  private View mScrapActionButtonView;
  private boolean mStrictWidthLimit;
  private int mWidthLimit;
  private boolean mWidthLimitSet;

  public ActionMenuPresenter(Context paramContext)
  {
    super(paramContext, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
  }

  private View findViewForItem(MenuItem paramMenuItem)
  {
    ViewGroup localViewGroup = (ViewGroup)this.mMenuView;
    View localView;
    if (localViewGroup == null)
    {
      localView = null;
      return localView;
    }
    int i = localViewGroup.getChildCount();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label68;
      localView = localViewGroup.getChildAt(j);
      if (((localView instanceof MenuView.ItemView)) && (((MenuView.ItemView)localView).getItemData() == paramMenuItem))
        break;
    }
    label68: return null;
  }

  public void bindItemView(MenuItemImpl paramMenuItemImpl, MenuView.ItemView paramItemView)
  {
    paramItemView.initialize(paramMenuItemImpl, 0);
    ActionMenuView localActionMenuView = (ActionMenuView)this.mMenuView;
    ActionMenuItemView localActionMenuItemView = (ActionMenuItemView)paramItemView;
    localActionMenuItemView.setItemInvoker(localActionMenuView);
    if (this.mPopupCallback == null)
      this.mPopupCallback = new ActionMenuPopupCallback(null);
    localActionMenuItemView.setPopupCallback(this.mPopupCallback);
  }

  public boolean dismissPopupMenus()
  {
    return hideOverflowMenu() | hideSubMenus();
  }

  public boolean filterLeftoverView(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramViewGroup.getChildAt(paramInt) == this.mOverflowButton)
      return false;
    return super.filterLeftoverView(paramViewGroup, paramInt);
  }

  public boolean flagActionItems()
  {
    ArrayList localArrayList = this.mMenu.getVisibleItems();
    int i = localArrayList.size();
    int j = this.mMaxItems;
    int k = this.mActionItemWidthLimit;
    int m = View.MeasureSpec.makeMeasureSpec(0, 0);
    ViewGroup localViewGroup = (ViewGroup)this.mMenuView;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    if (i4 < i)
    {
      MenuItemImpl localMenuItemImpl3 = (MenuItemImpl)localArrayList.get(i4);
      if (localMenuItemImpl3.requiresActionButton())
        n++;
      while (true)
      {
        if ((this.mExpandedActionViewsExclusive) && (localMenuItemImpl3.isActionViewExpanded()))
          j = 0;
        i4++;
        break;
        if (localMenuItemImpl3.requestsActionButton())
          i1++;
        else
          i3 = 1;
      }
    }
    if ((this.mReserveOverflow) && ((i3 != 0) || (n + i1 > j)))
      j--;
    int i5 = j - n;
    SparseBooleanArray localSparseBooleanArray = this.mActionButtonGroups;
    localSparseBooleanArray.clear();
    boolean bool1 = this.mStrictWidthLimit;
    int i6 = 0;
    int i7 = 0;
    if (bool1)
    {
      i7 = k / this.mMinCellSize;
      int i15 = k % this.mMinCellSize;
      i6 = this.mMinCellSize + i15 / i7;
    }
    int i8 = 0;
    if (i8 < i)
    {
      MenuItemImpl localMenuItemImpl1 = (MenuItemImpl)localArrayList.get(i8);
      View localView2;
      if (localMenuItemImpl1.requiresActionButton())
      {
        localView2 = getItemView(localMenuItemImpl1, this.mScrapActionButtonView, localViewGroup);
        if (this.mScrapActionButtonView == null)
          this.mScrapActionButtonView = localView2;
        if (this.mStrictWidthLimit)
        {
          i7 -= ActionMenuView.measureChildForCells(localView2, i6, i7, m, 0);
          label293: int i13 = localView2.getMeasuredWidth();
          k -= i13;
          if (i2 == 0)
            i2 = i13;
          int i14 = localMenuItemImpl1.getGroupId();
          if (i14 != 0)
            localSparseBooleanArray.put(i14, true);
          localMenuItemImpl1.setIsActionButton(true);
        }
      }
      while (true)
      {
        i8++;
        break;
        localView2.measure(m, m);
        break label293;
        if (localMenuItemImpl1.requestsActionButton())
        {
          int i9 = localMenuItemImpl1.getGroupId();
          boolean bool2 = localSparseBooleanArray.get(i9);
          boolean bool3;
          label414: View localView1;
          label482: boolean bool5;
          if (((i5 > 0) || (bool2)) && (k > 0) && ((!this.mStrictWidthLimit) || (i7 > 0)))
          {
            bool3 = true;
            if (bool3)
            {
              localView1 = getItemView(localMenuItemImpl1, this.mScrapActionButtonView, localViewGroup);
              if (this.mScrapActionButtonView == null)
                this.mScrapActionButtonView = localView1;
              if (!this.mStrictWidthLimit)
                break label569;
              int i12 = ActionMenuView.measureChildForCells(localView1, i6, i7, m, 0);
              i7 -= i12;
              if (i12 == 0)
                bool3 = false;
              int i11 = localView1.getMeasuredWidth();
              k -= i11;
              if (i2 == 0)
                i2 = i11;
              if (!this.mStrictWidthLimit)
                break label587;
              if (k < 0)
                break label581;
              bool5 = true;
              label520: bool3 &= bool5;
            }
            if ((!bool3) || (i9 == 0))
              break label614;
            localSparseBooleanArray.put(i9, true);
          }
          while (true)
          {
            if (bool3)
              i5--;
            localMenuItemImpl1.setIsActionButton(bool3);
            break;
            bool3 = false;
            break label414;
            label569: localView1.measure(m, m);
            break label482;
            label581: bool5 = false;
            break label520;
            label587: if (k + i2 > 0);
            for (boolean bool4 = true; ; bool4 = false)
            {
              bool3 &= bool4;
              break;
            }
            label614: if (bool2)
            {
              localSparseBooleanArray.put(i9, false);
              for (int i10 = 0; i10 < i8; i10++)
              {
                MenuItemImpl localMenuItemImpl2 = (MenuItemImpl)localArrayList.get(i10);
                if (localMenuItemImpl2.getGroupId() == i9)
                {
                  if (localMenuItemImpl2.isActionButton())
                    i5++;
                  localMenuItemImpl2.setIsActionButton(false);
                }
              }
            }
          }
        }
        localMenuItemImpl1.setIsActionButton(false);
      }
    }
    return true;
  }

  public View getItemView(MenuItemImpl paramMenuItemImpl, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramMenuItemImpl.getActionView();
    if ((localView == null) || (paramMenuItemImpl.hasCollapsibleActionView()))
      localView = super.getItemView(paramMenuItemImpl, paramView, paramViewGroup);
    if (paramMenuItemImpl.isActionViewExpanded());
    for (int i = 8; ; i = 0)
    {
      localView.setVisibility(i);
      ActionMenuView localActionMenuView = (ActionMenuView)paramViewGroup;
      ViewGroup.LayoutParams localLayoutParams = localView.getLayoutParams();
      if (!localActionMenuView.checkLayoutParams(localLayoutParams))
        localView.setLayoutParams(localActionMenuView.generateLayoutParams(localLayoutParams));
      return localView;
    }
  }

  public MenuView getMenuView(ViewGroup paramViewGroup)
  {
    MenuView localMenuView = super.getMenuView(paramViewGroup);
    ((ActionMenuView)localMenuView).setPresenter(this);
    return localMenuView;
  }

  public boolean hideOverflowMenu()
  {
    if ((this.mPostedOpenRunnable != null) && (this.mMenuView != null))
    {
      ((View)this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
      this.mPostedOpenRunnable = null;
      return true;
    }
    OverflowPopup localOverflowPopup = this.mOverflowPopup;
    if (localOverflowPopup != null)
    {
      localOverflowPopup.dismiss();
      return true;
    }
    return false;
  }

  public boolean hideSubMenus()
  {
    if (this.mActionButtonPopup != null)
    {
      this.mActionButtonPopup.dismiss();
      return true;
    }
    return false;
  }

  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    super.initForMenu(paramContext, paramMenuBuilder);
    Resources localResources = paramContext.getResources();
    ActionBarPolicy localActionBarPolicy = ActionBarPolicy.get(paramContext);
    if (!this.mReserveOverflowSet)
      this.mReserveOverflow = localActionBarPolicy.showsOverflowMenuButton();
    if (!this.mWidthLimitSet)
      this.mWidthLimit = localActionBarPolicy.getEmbeddedMenuWidthLimit();
    if (!this.mMaxItemsSet)
      this.mMaxItems = localActionBarPolicy.getMaxActionButtons();
    int i = this.mWidthLimit;
    if (this.mReserveOverflow)
    {
      if (this.mOverflowButton == null)
      {
        this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
        int j = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mOverflowButton.measure(j, j);
      }
      i -= this.mOverflowButton.getMeasuredWidth();
    }
    while (true)
    {
      this.mActionItemWidthLimit = i;
      this.mMinCellSize = ((int)(56.0F * localResources.getDisplayMetrics().density));
      this.mScrapActionButtonView = null;
      return;
      this.mOverflowButton = null;
    }
  }

  public boolean isOverflowMenuShowPending()
  {
    return (this.mPostedOpenRunnable != null) || (isOverflowMenuShowing());
  }

  public boolean isOverflowMenuShowing()
  {
    return (this.mOverflowPopup != null) && (this.mOverflowPopup.isShowing());
  }

  public boolean isOverflowReserved()
  {
    return this.mReserveOverflow;
  }

  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    dismissPopupMenus();
    super.onCloseMenu(paramMenuBuilder, paramBoolean);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (!this.mMaxItemsSet)
      this.mMaxItems = this.mContext.getResources().getInteger(R.integer.abc_max_action_buttons);
    if (this.mMenu != null)
      this.mMenu.onItemsChanged(true);
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    if (localSavedState.openSubMenuId > 0)
    {
      MenuItem localMenuItem = this.mMenu.findItem(localSavedState.openSubMenuId);
      if (localMenuItem != null)
        onSubMenuSelected((SubMenuBuilder)localMenuItem.getSubMenu());
    }
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState();
    localSavedState.openSubMenuId = this.mOpenSubMenuId;
    return localSavedState;
  }

  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    if (!paramSubMenuBuilder.hasVisibleItems());
    do
    {
      return false;
      for (SubMenuBuilder localSubMenuBuilder = paramSubMenuBuilder; localSubMenuBuilder.getParentMenu() != this.mMenu; localSubMenuBuilder = (SubMenuBuilder)localSubMenuBuilder.getParentMenu());
      localView = findViewForItem(localSubMenuBuilder.getItem());
      if (localView != null)
        break;
    }
    while (this.mOverflowButton == null);
    View localView = this.mOverflowButton;
    this.mOpenSubMenuId = paramSubMenuBuilder.getItem().getItemId();
    this.mActionButtonPopup = new ActionButtonSubmenu(this.mContext, paramSubMenuBuilder);
    this.mActionButtonPopup.setAnchorView(localView);
    this.mActionButtonPopup.show();
    super.onSubMenuSelected(paramSubMenuBuilder);
    return true;
  }

  public void onSubUiVisibilityChanged(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      super.onSubMenuSelected(null);
      return;
    }
    this.mMenu.close(false);
  }

  public void setExpandedActionViewsExclusive(boolean paramBoolean)
  {
    this.mExpandedActionViewsExclusive = paramBoolean;
  }

  public void setItemLimit(int paramInt)
  {
    this.mMaxItems = paramInt;
    this.mMaxItemsSet = true;
  }

  public void setMenuView(ActionMenuView paramActionMenuView)
  {
    this.mMenuView = paramActionMenuView;
    paramActionMenuView.initialize(this.mMenu);
  }

  public void setReserveOverflow(boolean paramBoolean)
  {
    this.mReserveOverflow = paramBoolean;
    this.mReserveOverflowSet = true;
  }

  public void setWidthLimit(int paramInt, boolean paramBoolean)
  {
    this.mWidthLimit = paramInt;
    this.mStrictWidthLimit = paramBoolean;
    this.mWidthLimitSet = true;
  }

  public boolean shouldIncludeItem(int paramInt, MenuItemImpl paramMenuItemImpl)
  {
    return paramMenuItemImpl.isActionButton();
  }

  public boolean showOverflowMenu()
  {
    if ((this.mReserveOverflow) && (!isOverflowMenuShowing()) && (this.mMenu != null) && (this.mMenuView != null) && (this.mPostedOpenRunnable == null) && (!this.mMenu.getNonActionItems().isEmpty()))
    {
      this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true));
      ((View)this.mMenuView).post(this.mPostedOpenRunnable);
      super.onSubMenuSelected(null);
      return true;
    }
    return false;
  }

  public void updateMenuView(boolean paramBoolean)
  {
    ViewGroup localViewGroup1 = (ViewGroup)((View)this.mMenuView).getParent();
    if (localViewGroup1 != null)
      ActionBarTransition.beginDelayedTransition(localViewGroup1);
    super.updateMenuView(paramBoolean);
    ((View)this.mMenuView).requestLayout();
    if (this.mMenu != null)
    {
      ArrayList localArrayList2 = this.mMenu.getActionItems();
      int k = localArrayList2.size();
      for (int m = 0; m < k; m++)
      {
        ActionProvider localActionProvider = ((MenuItemImpl)localArrayList2.get(m)).getSupportActionProvider();
        if (localActionProvider != null)
          localActionProvider.setSubUiVisibilityListener(this);
      }
    }
    ArrayList localArrayList1;
    int i;
    int j;
    if (this.mMenu != null)
    {
      localArrayList1 = this.mMenu.getNonActionItems();
      boolean bool = this.mReserveOverflow;
      i = 0;
      if (bool)
      {
        i = 0;
        if (localArrayList1 != null)
        {
          j = localArrayList1.size();
          if (j != 1)
            break label279;
          if (((MenuItemImpl)localArrayList1.get(0)).isActionViewExpanded())
            break label273;
          i = 1;
        }
      }
      label167: if (i == 0)
        break label296;
      if (this.mOverflowButton == null)
        this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
      ViewGroup localViewGroup2 = (ViewGroup)this.mOverflowButton.getParent();
      if (localViewGroup2 != this.mMenuView)
      {
        if (localViewGroup2 != null)
          localViewGroup2.removeView(this.mOverflowButton);
        ActionMenuView localActionMenuView = (ActionMenuView)this.mMenuView;
        localActionMenuView.addView(this.mOverflowButton, localActionMenuView.generateOverflowButtonLayoutParams());
      }
    }
    while (true)
    {
      ((ActionMenuView)this.mMenuView).setOverflowReserved(this.mReserveOverflow);
      return;
      localArrayList1 = null;
      break;
      label273: i = 0;
      break label167;
      label279: if (j > 0);
      for (i = 1; ; i = 0)
        break;
      label296: if ((this.mOverflowButton != null) && (this.mOverflowButton.getParent() == this.mMenuView))
        ((ViewGroup)this.mMenuView).removeView(this.mOverflowButton);
    }
  }

  private class ActionButtonSubmenu extends MenuPopupHelper
  {
    private SubMenuBuilder mSubMenu;

    public ActionButtonSubmenu(Context paramSubMenuBuilder, SubMenuBuilder arg3)
    {
      super(localMenuBuilder, null, false, R.attr.actionOverflowMenuStyle);
      this.mSubMenu = localMenuBuilder;
      View localView;
      int i;
      if (!((MenuItemImpl)localMenuBuilder.getItem()).isActionButton())
      {
        if (ActionMenuPresenter.this.mOverflowButton == null)
        {
          localView = (View)ActionMenuPresenter.this.mMenuView;
          setAnchorView(localView);
        }
      }
      else
      {
        setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        i = localMenuBuilder.size();
      }
      for (int j = 0; ; j++)
      {
        boolean bool = false;
        if (j < i)
        {
          MenuItem localMenuItem = localMenuBuilder.getItem(j);
          if ((localMenuItem.isVisible()) && (localMenuItem.getIcon() != null))
            bool = true;
        }
        else
        {
          setForceShowIcon(bool);
          return;
          localView = ActionMenuPresenter.this.mOverflowButton;
          break;
        }
      }
    }

    public void onDismiss()
    {
      super.onDismiss();
      ActionMenuPresenter.access$702(ActionMenuPresenter.this, null);
      ActionMenuPresenter.this.mOpenSubMenuId = 0;
    }
  }

  private class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback
  {
    private ActionMenuPopupCallback()
    {
    }

    public ListPopupWindow getPopup()
    {
      if (ActionMenuPresenter.this.mActionButtonPopup != null)
        return ActionMenuPresenter.this.mActionButtonPopup.getPopup();
      return null;
    }
  }

  private class OpenOverflowRunnable
    implements Runnable
  {
    private ActionMenuPresenter.OverflowPopup mPopup;

    public OpenOverflowRunnable(ActionMenuPresenter.OverflowPopup arg2)
    {
      Object localObject;
      this.mPopup = localObject;
    }

    public void run()
    {
      ActionMenuPresenter.this.mMenu.changeMenuMode();
      View localView = (View)ActionMenuPresenter.this.mMenuView;
      if ((localView != null) && (localView.getWindowToken() != null) && (this.mPopup.tryShow()))
        ActionMenuPresenter.access$202(ActionMenuPresenter.this, this.mPopup);
      ActionMenuPresenter.access$302(ActionMenuPresenter.this, null);
    }
  }

  private class OverflowMenuButton extends TintImageView
    implements ActionMenuView.ActionMenuChildView
  {
    private final float[] mTempPts = new float[2];

    public OverflowMenuButton(Context arg2)
    {
      super(null, R.attr.actionOverflowButtonStyle);
      setClickable(true);
      setFocusable(true);
      setVisibility(0);
      setEnabled(true);
      setOnTouchListener(new ListPopupWindow.ForwardingListener(this)
      {
        public ListPopupWindow getPopup()
        {
          if (ActionMenuPresenter.this.mOverflowPopup == null)
            return null;
          return ActionMenuPresenter.this.mOverflowPopup.getPopup();
        }

        public boolean onForwardingStarted()
        {
          ActionMenuPresenter.this.showOverflowMenu();
          return true;
        }

        public boolean onForwardingStopped()
        {
          if (ActionMenuPresenter.this.mPostedOpenRunnable != null)
            return false;
          ActionMenuPresenter.this.hideOverflowMenu();
          return true;
        }
      });
    }

    public boolean needsDividerAfter()
    {
      return false;
    }

    public boolean needsDividerBefore()
    {
      return false;
    }

    public boolean performClick()
    {
      if (super.performClick())
        return true;
      playSoundEffect(0);
      ActionMenuPresenter.this.showOverflowMenu();
      return true;
    }

    protected boolean setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      boolean bool = super.setFrame(paramInt1, paramInt2, paramInt3, paramInt4);
      Drawable localDrawable1 = getDrawable();
      Drawable localDrawable2 = getBackground();
      if ((localDrawable1 != null) && (localDrawable2 != null))
      {
        int i = getWidth();
        int j = getHeight();
        int k = Math.max(i, j) / 2;
        int m = getPaddingLeft() - getPaddingRight();
        int n = getPaddingTop() - getPaddingBottom();
        int i1 = (i + m) / 2;
        int i2 = (j + n) / 2;
        DrawableCompat.setHotspotBounds(localDrawable2, i1 - k, i2 - k, i1 + k, i2 + k);
      }
      return bool;
    }
  }

  private class OverflowPopup extends MenuPopupHelper
  {
    public OverflowPopup(Context paramMenuBuilder, MenuBuilder paramView, View paramBoolean, boolean arg5)
    {
      super(paramView, paramBoolean, bool, R.attr.actionOverflowMenuStyle);
      setGravity(8388613);
      setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
    }

    public void onDismiss()
    {
      super.onDismiss();
      ActionMenuPresenter.this.mMenu.close();
      ActionMenuPresenter.access$202(ActionMenuPresenter.this, null);
    }
  }

  private class PopupPresenterCallback
    implements MenuPresenter.Callback
  {
    private PopupPresenterCallback()
    {
    }

    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      if ((paramMenuBuilder instanceof SubMenuBuilder))
        ((SubMenuBuilder)paramMenuBuilder).getRootMenu().close(false);
      MenuPresenter.Callback localCallback = ActionMenuPresenter.this.getCallback();
      if (localCallback != null)
        localCallback.onCloseMenu(paramMenuBuilder, paramBoolean);
    }

    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if (paramMenuBuilder == null)
        return false;
      ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)paramMenuBuilder).getItem().getItemId();
      MenuPresenter.Callback localCallback = ActionMenuPresenter.this.getCallback();
      if (localCallback != null);
      for (boolean bool = localCallback.onOpenSubMenu(paramMenuBuilder); ; bool = false)
        return bool;
    }
  }

  private static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public ActionMenuPresenter.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new ActionMenuPresenter.SavedState(paramAnonymousParcel);
      }

      public ActionMenuPresenter.SavedState[] newArray(int paramAnonymousInt)
      {
        return new ActionMenuPresenter.SavedState[paramAnonymousInt];
      }
    };
    public int openSubMenuId;

    SavedState()
    {
    }

    SavedState(Parcel paramParcel)
    {
      this.openSubMenuId = paramParcel.readInt();
    }

    public int describeContents()
    {
      return 0;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.openSubMenuId);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.ActionMenuPresenter
 * JD-Core Version:    0.6.2
 */