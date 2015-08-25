package android.support.v7.internal.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.dimen;
import android.support.v7.appcompat.R.layout;
import android.support.v7.widget.ListPopupWindow;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import java.util.ArrayList;

public class MenuPopupHelper
  implements AdapterView.OnItemClickListener, View.OnKeyListener, ViewTreeObserver.OnGlobalLayoutListener, PopupWindow.OnDismissListener, MenuPresenter
{
  static final int ITEM_LAYOUT = 0;
  private static final String TAG = "MenuPopupHelper";
  private final MenuAdapter mAdapter;
  private View mAnchorView;
  private int mContentWidth;
  private final Context mContext;
  private int mDropDownGravity = 0;
  boolean mForceShowIcon;
  private boolean mHasContentWidth;
  private final LayoutInflater mInflater;
  private ViewGroup mMeasureParent;
  private final MenuBuilder mMenu;
  private final boolean mOverflowOnly;
  private ListPopupWindow mPopup;
  private final int mPopupMaxWidth;
  private final int mPopupStyleAttr;
  private final int mPopupStyleRes;
  private MenuPresenter.Callback mPresenterCallback;
  private ViewTreeObserver mTreeObserver;

  public MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    this(paramContext, paramMenuBuilder, null, false, R.attr.popupMenuStyle);
  }

  public MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder, View paramView)
  {
    this(paramContext, paramMenuBuilder, paramView, false, R.attr.popupMenuStyle);
  }

  public MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder, View paramView, boolean paramBoolean, int paramInt)
  {
    this(paramContext, paramMenuBuilder, paramView, paramBoolean, paramInt, 0);
  }

  public MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder, View paramView, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(paramContext);
    this.mMenu = paramMenuBuilder;
    this.mAdapter = new MenuAdapter(this.mMenu);
    this.mOverflowOnly = paramBoolean;
    this.mPopupStyleAttr = paramInt1;
    this.mPopupStyleRes = paramInt2;
    Resources localResources = paramContext.getResources();
    this.mPopupMaxWidth = Math.max(localResources.getDisplayMetrics().widthPixels / 2, localResources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    this.mAnchorView = paramView;
    paramMenuBuilder.addMenuPresenter(this, paramContext);
  }

  private int measureContentWidth()
  {
    int i = 0;
    View localView = null;
    int j = 0;
    MenuAdapter localMenuAdapter = this.mAdapter;
    int k = View.MeasureSpec.makeMeasureSpec(0, 0);
    int m = View.MeasureSpec.makeMeasureSpec(0, 0);
    int n = localMenuAdapter.getCount();
    for (int i1 = 0; ; i1++)
    {
      int i3;
      if (i1 < n)
      {
        int i2 = localMenuAdapter.getItemViewType(i1);
        if (i2 != j)
        {
          j = i2;
          localView = null;
        }
        if (this.mMeasureParent == null)
          this.mMeasureParent = new FrameLayout(this.mContext);
        localView = localMenuAdapter.getView(i1, localView, this.mMeasureParent);
        localView.measure(k, m);
        i3 = localView.getMeasuredWidth();
        if (i3 >= this.mPopupMaxWidth)
          i = this.mPopupMaxWidth;
      }
      else
      {
        return i;
      }
      if (i3 > i)
        i = i3;
    }
  }

  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }

  public void dismiss()
  {
    if (isShowing())
      this.mPopup.dismiss();
  }

  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }

  public boolean flagActionItems()
  {
    return false;
  }

  public int getId()
  {
    return 0;
  }

  public MenuView getMenuView(ViewGroup paramViewGroup)
  {
    throw new UnsupportedOperationException("MenuPopupHelpers manage their own views");
  }

  public ListPopupWindow getPopup()
  {
    return this.mPopup;
  }

  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
  }

  public boolean isShowing()
  {
    return (this.mPopup != null) && (this.mPopup.isShowing());
  }

  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    if (paramMenuBuilder != this.mMenu);
    do
    {
      return;
      dismiss();
    }
    while (this.mPresenterCallback == null);
    this.mPresenterCallback.onCloseMenu(paramMenuBuilder, paramBoolean);
  }

  public void onDismiss()
  {
    this.mPopup = null;
    this.mMenu.close();
    if (this.mTreeObserver != null)
    {
      if (!this.mTreeObserver.isAlive())
        this.mTreeObserver = this.mAnchorView.getViewTreeObserver();
      this.mTreeObserver.removeGlobalOnLayoutListener(this);
      this.mTreeObserver = null;
    }
  }

  public void onGlobalLayout()
  {
    if (isShowing())
    {
      View localView = this.mAnchorView;
      if ((localView != null) && (localView.isShown()))
        break label28;
      dismiss();
    }
    label28: 
    while (!isShowing())
      return;
    this.mPopup.show();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    MenuAdapter localMenuAdapter = this.mAdapter;
    localMenuAdapter.mAdapterMenu.performItemAction(localMenuAdapter.getItem(paramInt), 0);
  }

  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getAction() == 1) && (paramInt == 82))
    {
      dismiss();
      return true;
    }
    return false;
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
  }

  public Parcelable onSaveInstanceState()
  {
    return null;
  }

  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    if (paramSubMenuBuilder.hasVisibleItems())
    {
      MenuPopupHelper localMenuPopupHelper = new MenuPopupHelper(this.mContext, paramSubMenuBuilder, this.mAnchorView);
      localMenuPopupHelper.setCallback(this.mPresenterCallback);
      int i = paramSubMenuBuilder.size();
      for (int j = 0; ; j++)
      {
        boolean bool = false;
        if (j < i)
        {
          MenuItem localMenuItem = paramSubMenuBuilder.getItem(j);
          if ((localMenuItem.isVisible()) && (localMenuItem.getIcon() != null))
            bool = true;
        }
        else
        {
          localMenuPopupHelper.setForceShowIcon(bool);
          if (!localMenuPopupHelper.tryShow())
            break;
          if (this.mPresenterCallback != null)
            this.mPresenterCallback.onOpenSubMenu(paramSubMenuBuilder);
          return true;
        }
      }
    }
    return false;
  }

  public void setAnchorView(View paramView)
  {
    this.mAnchorView = paramView;
  }

  public void setCallback(MenuPresenter.Callback paramCallback)
  {
    this.mPresenterCallback = paramCallback;
  }

  public void setForceShowIcon(boolean paramBoolean)
  {
    this.mForceShowIcon = paramBoolean;
  }

  public void setGravity(int paramInt)
  {
    this.mDropDownGravity = paramInt;
  }

  public void show()
  {
    if (!tryShow())
      throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
  }

  public boolean tryShow()
  {
    this.mPopup = new ListPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
    this.mPopup.setOnDismissListener(this);
    this.mPopup.setOnItemClickListener(this);
    this.mPopup.setAdapter(this.mAdapter);
    this.mPopup.setModal(true);
    View localView = this.mAnchorView;
    if (localView != null)
    {
      ViewTreeObserver localViewTreeObserver = this.mTreeObserver;
      int i = 0;
      if (localViewTreeObserver == null)
        i = 1;
      this.mTreeObserver = localView.getViewTreeObserver();
      if (i != 0)
        this.mTreeObserver.addOnGlobalLayoutListener(this);
      this.mPopup.setAnchorView(localView);
      this.mPopup.setDropDownGravity(this.mDropDownGravity);
      if (!this.mHasContentWidth)
      {
        this.mContentWidth = measureContentWidth();
        this.mHasContentWidth = true;
      }
      this.mPopup.setContentWidth(this.mContentWidth);
      this.mPopup.setInputMethodMode(2);
      this.mPopup.show();
      this.mPopup.getListView().setOnKeyListener(this);
      return true;
    }
    return false;
  }

  public void updateMenuView(boolean paramBoolean)
  {
    this.mHasContentWidth = false;
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
  }

  private class MenuAdapter extends BaseAdapter
  {
    private MenuBuilder mAdapterMenu;
    private int mExpandedIndex = -1;

    public MenuAdapter(MenuBuilder arg2)
    {
      Object localObject;
      this.mAdapterMenu = localObject;
      findExpandedIndex();
    }

    void findExpandedIndex()
    {
      MenuItemImpl localMenuItemImpl = MenuPopupHelper.this.mMenu.getExpandedItem();
      if (localMenuItemImpl != null)
      {
        ArrayList localArrayList = MenuPopupHelper.this.mMenu.getNonActionItems();
        int i = localArrayList.size();
        for (int j = 0; j < i; j++)
          if ((MenuItemImpl)localArrayList.get(j) == localMenuItemImpl)
          {
            this.mExpandedIndex = j;
            return;
          }
      }
      this.mExpandedIndex = -1;
    }

    public int getCount()
    {
      if (MenuPopupHelper.this.mOverflowOnly);
      for (ArrayList localArrayList = this.mAdapterMenu.getNonActionItems(); this.mExpandedIndex < 0; localArrayList = this.mAdapterMenu.getVisibleItems())
        return localArrayList.size();
      return -1 + localArrayList.size();
    }

    public MenuItemImpl getItem(int paramInt)
    {
      if (MenuPopupHelper.this.mOverflowOnly);
      for (ArrayList localArrayList = this.mAdapterMenu.getNonActionItems(); ; localArrayList = this.mAdapterMenu.getVisibleItems())
      {
        if ((this.mExpandedIndex >= 0) && (paramInt >= this.mExpandedIndex))
          paramInt++;
        return (MenuItemImpl)localArrayList.get(paramInt);
      }
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
        paramView = MenuPopupHelper.this.mInflater.inflate(MenuPopupHelper.ITEM_LAYOUT, paramViewGroup, false);
      MenuView.ItemView localItemView = (MenuView.ItemView)paramView;
      if (MenuPopupHelper.this.mForceShowIcon)
        ((ListMenuItemView)paramView).setForceShowIcon(true);
      localItemView.initialize(getItem(paramInt), 0);
      return paramView;
    }

    public void notifyDataSetChanged()
    {
      findExpandedIndex();
      super.notifyDataSetChanged();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.MenuPopupHelper
 * JD-Core Version:    0.6.2
 */