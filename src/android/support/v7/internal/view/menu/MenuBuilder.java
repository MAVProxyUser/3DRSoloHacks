package android.support.v7.internal.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.R.bool;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyCharacterMap.KeyData;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuBuilder
  implements SupportMenu
{
  private static final String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";
  private static final String EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview";
  private static final String PRESENTER_KEY = "android:menu:presenters";
  private static final String TAG = "MenuBuilder";
  private static final int[] sCategoryToOrder = { 1, 4, 5, 3, 2, 0 };
  private ArrayList<MenuItemImpl> mActionItems;
  private Callback mCallback;
  private final Context mContext;
  private ContextMenu.ContextMenuInfo mCurrentMenuInfo;
  private int mDefaultShowAsAction = 0;
  private MenuItemImpl mExpandedItem;
  private SparseArray<Parcelable> mFrozenViewStates;
  Drawable mHeaderIcon;
  CharSequence mHeaderTitle;
  View mHeaderView;
  private boolean mIsActionItemsStale;
  private boolean mIsClosing = false;
  private boolean mIsVisibleItemsStale;
  private ArrayList<MenuItemImpl> mItems;
  private boolean mItemsChangedWhileDispatchPrevented = false;
  private ArrayList<MenuItemImpl> mNonActionItems;
  private boolean mOptionalIconsVisible = false;
  private boolean mOverrideVisibleItems;
  private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters = new CopyOnWriteArrayList();
  private boolean mPreventDispatchingItemsChanged = false;
  private boolean mQwertyMode;
  private final Resources mResources;
  private boolean mShortcutsVisible;
  private ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList();
  private ArrayList<MenuItemImpl> mVisibleItems;

  public MenuBuilder(Context paramContext)
  {
    this.mContext = paramContext;
    this.mResources = paramContext.getResources();
    this.mItems = new ArrayList();
    this.mVisibleItems = new ArrayList();
    this.mIsVisibleItemsStale = true;
    this.mActionItems = new ArrayList();
    this.mNonActionItems = new ArrayList();
    this.mIsActionItemsStale = true;
    setShortcutsVisibleInner(true);
  }

  private MenuItem addInternal(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    int i = getOrdering(paramInt3);
    MenuItemImpl localMenuItemImpl = createNewMenuItem(paramInt1, paramInt2, paramInt3, i, paramCharSequence, this.mDefaultShowAsAction);
    if (this.mCurrentMenuInfo != null)
      localMenuItemImpl.setMenuInfo(this.mCurrentMenuInfo);
    this.mItems.add(findInsertIndex(this.mItems, i), localMenuItemImpl);
    onItemsChanged(true);
    return localMenuItemImpl;
  }

  private MenuItemImpl createNewMenuItem(int paramInt1, int paramInt2, int paramInt3, int paramInt4, CharSequence paramCharSequence, int paramInt5)
  {
    return new MenuItemImpl(this, paramInt1, paramInt2, paramInt3, paramInt4, paramCharSequence, paramInt5);
  }

  private void dispatchPresenterUpdate(boolean paramBoolean)
  {
    if (this.mPresenters.isEmpty())
      return;
    stopDispatchingItemsChanged();
    Iterator localIterator = this.mPresenters.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
      if (localMenuPresenter == null)
        this.mPresenters.remove(localWeakReference);
      else
        localMenuPresenter.updateMenuView(paramBoolean);
    }
    startDispatchingItemsChanged();
  }

  private void dispatchRestoreInstanceState(Bundle paramBundle)
  {
    SparseArray localSparseArray = paramBundle.getSparseParcelableArray("android:menu:presenters");
    if ((localSparseArray == null) || (this.mPresenters.isEmpty()));
    while (true)
    {
      return;
      Iterator localIterator = this.mPresenters.iterator();
      while (localIterator.hasNext())
      {
        WeakReference localWeakReference = (WeakReference)localIterator.next();
        MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
        if (localMenuPresenter == null)
        {
          this.mPresenters.remove(localWeakReference);
        }
        else
        {
          int i = localMenuPresenter.getId();
          if (i > 0)
          {
            Parcelable localParcelable = (Parcelable)localSparseArray.get(i);
            if (localParcelable != null)
              localMenuPresenter.onRestoreInstanceState(localParcelable);
          }
        }
      }
    }
  }

  private void dispatchSaveInstanceState(Bundle paramBundle)
  {
    if (this.mPresenters.isEmpty())
      return;
    SparseArray localSparseArray = new SparseArray();
    Iterator localIterator = this.mPresenters.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
      if (localMenuPresenter == null)
      {
        this.mPresenters.remove(localWeakReference);
      }
      else
      {
        int i = localMenuPresenter.getId();
        if (i > 0)
        {
          Parcelable localParcelable = localMenuPresenter.onSaveInstanceState();
          if (localParcelable != null)
            localSparseArray.put(i, localParcelable);
        }
      }
    }
    paramBundle.putSparseParcelableArray("android:menu:presenters", localSparseArray);
  }

  private boolean dispatchSubMenuSelected(SubMenuBuilder paramSubMenuBuilder, MenuPresenter paramMenuPresenter)
  {
    boolean bool;
    if (this.mPresenters.isEmpty())
      bool = false;
    while (true)
    {
      return bool;
      bool = false;
      if (paramMenuPresenter != null)
        bool = paramMenuPresenter.onSubMenuSelected(paramSubMenuBuilder);
      Iterator localIterator = this.mPresenters.iterator();
      while (localIterator.hasNext())
      {
        WeakReference localWeakReference = (WeakReference)localIterator.next();
        MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
        if (localMenuPresenter == null)
          this.mPresenters.remove(localWeakReference);
        else if (!bool)
          bool = localMenuPresenter.onSubMenuSelected(paramSubMenuBuilder);
      }
    }
  }

  private static int findInsertIndex(ArrayList<MenuItemImpl> paramArrayList, int paramInt)
  {
    for (int i = -1 + paramArrayList.size(); i >= 0; i--)
      if (((MenuItemImpl)paramArrayList.get(i)).getOrdering() <= paramInt)
        return i + 1;
    return 0;
  }

  private static int getOrdering(int paramInt)
  {
    int i = (0xFFFF0000 & paramInt) >> 16;
    if ((i < 0) || (i >= sCategoryToOrder.length))
      throw new IllegalArgumentException("order does not contain a valid category.");
    return sCategoryToOrder[i] << 16 | 0xFFFF & paramInt;
  }

  private void removeItemAtInt(int paramInt, boolean paramBoolean)
  {
    if ((paramInt < 0) || (paramInt >= this.mItems.size()));
    do
    {
      return;
      this.mItems.remove(paramInt);
    }
    while (!paramBoolean);
    onItemsChanged(true);
  }

  private void setHeaderInternal(int paramInt1, CharSequence paramCharSequence, int paramInt2, Drawable paramDrawable, View paramView)
  {
    Resources localResources = getResources();
    if (paramView != null)
    {
      this.mHeaderView = paramView;
      this.mHeaderTitle = null;
      this.mHeaderIcon = null;
      onItemsChanged(false);
      return;
    }
    if (paramInt1 > 0)
    {
      this.mHeaderTitle = localResources.getText(paramInt1);
      label47: if (paramInt2 <= 0)
        break label83;
      this.mHeaderIcon = ContextCompat.getDrawable(getContext(), paramInt2);
    }
    while (true)
    {
      this.mHeaderView = null;
      break;
      if (paramCharSequence == null)
        break label47;
      this.mHeaderTitle = paramCharSequence;
      break label47;
      label83: if (paramDrawable != null)
        this.mHeaderIcon = paramDrawable;
    }
  }

  private void setShortcutsVisibleInner(boolean paramBoolean)
  {
    int i = 1;
    if ((paramBoolean) && (this.mResources.getConfiguration().keyboard != i) && (this.mResources.getBoolean(R.bool.abc_config_showMenuShortcutsWhenKeyboardPresent)));
    while (true)
    {
      this.mShortcutsVisible = i;
      return;
      i = 0;
    }
  }

  public MenuItem add(int paramInt)
  {
    return addInternal(0, 0, 0, this.mResources.getString(paramInt));
  }

  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return addInternal(paramInt1, paramInt2, paramInt3, this.mResources.getString(paramInt4));
  }

  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    return addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
  }

  public MenuItem add(CharSequence paramCharSequence)
  {
    return addInternal(0, 0, 0, paramCharSequence);
  }

  public int addIntentOptions(int paramInt1, int paramInt2, int paramInt3, ComponentName paramComponentName, Intent[] paramArrayOfIntent, Intent paramIntent, int paramInt4, MenuItem[] paramArrayOfMenuItem)
  {
    PackageManager localPackageManager = this.mContext.getPackageManager();
    List localList = localPackageManager.queryIntentActivityOptions(paramComponentName, paramArrayOfIntent, paramIntent, 0);
    int i;
    int j;
    label52: ResolveInfo localResolveInfo;
    if (localList != null)
    {
      i = localList.size();
      if ((paramInt4 & 0x1) == 0)
        removeGroup(paramInt1);
      j = 0;
      if (j >= i)
        break label211;
      localResolveInfo = (ResolveInfo)localList.get(j);
      if (localResolveInfo.specificIndex >= 0)
        break label198;
    }
    label198: for (Intent localIntent1 = paramIntent; ; localIntent1 = paramArrayOfIntent[localResolveInfo.specificIndex])
    {
      Intent localIntent2 = new Intent(localIntent1);
      localIntent2.setComponent(new ComponentName(localResolveInfo.activityInfo.applicationInfo.packageName, localResolveInfo.activityInfo.name));
      MenuItem localMenuItem = add(paramInt1, paramInt2, paramInt3, localResolveInfo.loadLabel(localPackageManager)).setIcon(localResolveInfo.loadIcon(localPackageManager)).setIntent(localIntent2);
      if ((paramArrayOfMenuItem != null) && (localResolveInfo.specificIndex >= 0))
        paramArrayOfMenuItem[localResolveInfo.specificIndex] = localMenuItem;
      j++;
      break label52;
      i = 0;
      break;
    }
    label211: return i;
  }

  public void addMenuPresenter(MenuPresenter paramMenuPresenter)
  {
    addMenuPresenter(paramMenuPresenter, this.mContext);
  }

  public void addMenuPresenter(MenuPresenter paramMenuPresenter, Context paramContext)
  {
    this.mPresenters.add(new WeakReference(paramMenuPresenter));
    paramMenuPresenter.initForMenu(paramContext, this);
    this.mIsActionItemsStale = true;
  }

  public SubMenu addSubMenu(int paramInt)
  {
    return addSubMenu(0, 0, 0, this.mResources.getString(paramInt));
  }

  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return addSubMenu(paramInt1, paramInt2, paramInt3, this.mResources.getString(paramInt4));
  }

  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    MenuItemImpl localMenuItemImpl = (MenuItemImpl)addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
    SubMenuBuilder localSubMenuBuilder = new SubMenuBuilder(this.mContext, this, localMenuItemImpl);
    localMenuItemImpl.setSubMenu(localSubMenuBuilder);
    return localSubMenuBuilder;
  }

  public SubMenu addSubMenu(CharSequence paramCharSequence)
  {
    return addSubMenu(0, 0, 0, paramCharSequence);
  }

  public void changeMenuMode()
  {
    if (this.mCallback != null)
      this.mCallback.onMenuModeChange(this);
  }

  public void clear()
  {
    if (this.mExpandedItem != null)
      collapseItemActionView(this.mExpandedItem);
    this.mItems.clear();
    onItemsChanged(true);
  }

  public void clearAll()
  {
    this.mPreventDispatchingItemsChanged = true;
    clear();
    clearHeader();
    this.mPreventDispatchingItemsChanged = false;
    this.mItemsChangedWhileDispatchPrevented = false;
    onItemsChanged(true);
  }

  public void clearHeader()
  {
    this.mHeaderIcon = null;
    this.mHeaderTitle = null;
    this.mHeaderView = null;
    onItemsChanged(false);
  }

  public void close()
  {
    close(true);
  }

  public final void close(boolean paramBoolean)
  {
    if (this.mIsClosing)
      return;
    this.mIsClosing = true;
    Iterator localIterator = this.mPresenters.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
      if (localMenuPresenter == null)
        this.mPresenters.remove(localWeakReference);
      else
        localMenuPresenter.onCloseMenu(this, paramBoolean);
    }
    this.mIsClosing = false;
  }

  public boolean collapseItemActionView(MenuItemImpl paramMenuItemImpl)
  {
    boolean bool;
    if ((this.mPresenters.isEmpty()) || (this.mExpandedItem != paramMenuItemImpl))
      bool = false;
    label98: 
    do
    {
      return bool;
      bool = false;
      stopDispatchingItemsChanged();
      Iterator localIterator = this.mPresenters.iterator();
      do
      {
        MenuPresenter localMenuPresenter;
        while (true)
        {
          if (!localIterator.hasNext())
            break label98;
          WeakReference localWeakReference = (WeakReference)localIterator.next();
          localMenuPresenter = (MenuPresenter)localWeakReference.get();
          if (localMenuPresenter != null)
            break;
          this.mPresenters.remove(localWeakReference);
        }
        bool = localMenuPresenter.collapseItemActionView(this, paramMenuItemImpl);
      }
      while (!bool);
      startDispatchingItemsChanged();
    }
    while (!bool);
    this.mExpandedItem = null;
    return bool;
  }

  boolean dispatchMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    return (this.mCallback != null) && (this.mCallback.onMenuItemSelected(paramMenuBuilder, paramMenuItem));
  }

  public boolean expandItemActionView(MenuItemImpl paramMenuItemImpl)
  {
    boolean bool;
    if (this.mPresenters.isEmpty())
      bool = false;
    label90: 
    do
    {
      return bool;
      bool = false;
      stopDispatchingItemsChanged();
      Iterator localIterator = this.mPresenters.iterator();
      do
      {
        MenuPresenter localMenuPresenter;
        while (true)
        {
          if (!localIterator.hasNext())
            break label90;
          WeakReference localWeakReference = (WeakReference)localIterator.next();
          localMenuPresenter = (MenuPresenter)localWeakReference.get();
          if (localMenuPresenter != null)
            break;
          this.mPresenters.remove(localWeakReference);
        }
        bool = localMenuPresenter.expandItemActionView(this, paramMenuItemImpl);
      }
      while (!bool);
      startDispatchingItemsChanged();
    }
    while (!bool);
    this.mExpandedItem = paramMenuItemImpl;
    return bool;
  }

  public int findGroupIndex(int paramInt)
  {
    return findGroupIndex(paramInt, 0);
  }

  public int findGroupIndex(int paramInt1, int paramInt2)
  {
    int i = size();
    if (paramInt2 < 0)
      paramInt2 = 0;
    for (int j = paramInt2; j < i; j++)
      if (((MenuItemImpl)this.mItems.get(j)).getGroupId() == paramInt1)
        return j;
    return -1;
  }

  public MenuItem findItem(int paramInt)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(j);
      if (localMenuItemImpl.getItemId() == paramInt)
        return localMenuItemImpl;
      if (localMenuItemImpl.hasSubMenu())
      {
        MenuItem localMenuItem = localMenuItemImpl.getSubMenu().findItem(paramInt);
        if (localMenuItem != null)
          return localMenuItem;
      }
    }
    return null;
  }

  public int findItemIndex(int paramInt)
  {
    int i = size();
    for (int j = 0; j < i; j++)
      if (((MenuItemImpl)this.mItems.get(j)).getItemId() == paramInt)
        return j;
    return -1;
  }

  MenuItemImpl findItemWithShortcutForKey(int paramInt, KeyEvent paramKeyEvent)
  {
    ArrayList localArrayList = this.mTempShortcutItemList;
    localArrayList.clear();
    findItemsWithShortcutForKey(localArrayList, paramInt, paramKeyEvent);
    if (localArrayList.isEmpty());
    while (true)
    {
      return null;
      int i = paramKeyEvent.getMetaState();
      KeyCharacterMap.KeyData localKeyData = new KeyCharacterMap.KeyData();
      paramKeyEvent.getKeyData(localKeyData);
      int j = localArrayList.size();
      if (j == 1)
        return (MenuItemImpl)localArrayList.get(0);
      boolean bool = isQwertyMode();
      for (int k = 0; k < j; k++)
      {
        MenuItemImpl localMenuItemImpl = (MenuItemImpl)localArrayList.get(k);
        if (bool);
        for (int m = localMenuItemImpl.getAlphabeticShortcut(); ((m == localKeyData.meta[0]) && ((i & 0x2) == 0)) || ((m == localKeyData.meta[2]) && ((i & 0x2) != 0)) || ((bool) && (m == 8) && (paramInt == 67)); m = localMenuItemImpl.getNumericShortcut())
          return localMenuItemImpl;
      }
    }
  }

  void findItemsWithShortcutForKey(List<MenuItemImpl> paramList, int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = isQwertyMode();
    int i = paramKeyEvent.getMetaState();
    KeyCharacterMap.KeyData localKeyData = new KeyCharacterMap.KeyData();
    if ((!paramKeyEvent.getKeyData(localKeyData)) && (paramInt != 67))
      return;
    int j = this.mItems.size();
    int k = 0;
    label49: MenuItemImpl localMenuItemImpl;
    if (k < j)
    {
      localMenuItemImpl = (MenuItemImpl)this.mItems.get(k);
      if (localMenuItemImpl.hasSubMenu())
        ((MenuBuilder)localMenuItemImpl.getSubMenu()).findItemsWithShortcutForKey(paramList, paramInt, paramKeyEvent);
      if (!bool)
        break label181;
    }
    label181: for (int m = localMenuItemImpl.getAlphabeticShortcut(); ; m = localMenuItemImpl.getNumericShortcut())
    {
      if (((i & 0x5) == 0) && (m != 0) && ((m == localKeyData.meta[0]) || (m == localKeyData.meta[2]) || ((bool) && (m == 8) && (paramInt == 67))) && (localMenuItemImpl.isEnabled()))
        paramList.add(localMenuItemImpl);
      k++;
      break label49;
      break;
    }
  }

  public void flagActionItems()
  {
    ArrayList localArrayList = getVisibleItems();
    if (!this.mIsActionItemsStale)
      return;
    boolean bool = false;
    Iterator localIterator = this.mPresenters.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
      if (localMenuPresenter == null)
        this.mPresenters.remove(localWeakReference);
      else
        bool |= localMenuPresenter.flagActionItems();
    }
    if (bool)
    {
      this.mActionItems.clear();
      this.mNonActionItems.clear();
      int i = localArrayList.size();
      int j = 0;
      if (j < i)
      {
        MenuItemImpl localMenuItemImpl = (MenuItemImpl)localArrayList.get(j);
        if (localMenuItemImpl.isActionButton())
          this.mActionItems.add(localMenuItemImpl);
        while (true)
        {
          j++;
          break;
          this.mNonActionItems.add(localMenuItemImpl);
        }
      }
    }
    else
    {
      this.mActionItems.clear();
      this.mNonActionItems.clear();
      this.mNonActionItems.addAll(getVisibleItems());
    }
    this.mIsActionItemsStale = false;
  }

  public ArrayList<MenuItemImpl> getActionItems()
  {
    flagActionItems();
    return this.mActionItems;
  }

  protected String getActionViewStatesKey()
  {
    return "android:menu:actionviewstates";
  }

  public Context getContext()
  {
    return this.mContext;
  }

  public MenuItemImpl getExpandedItem()
  {
    return this.mExpandedItem;
  }

  public Drawable getHeaderIcon()
  {
    return this.mHeaderIcon;
  }

  public CharSequence getHeaderTitle()
  {
    return this.mHeaderTitle;
  }

  public View getHeaderView()
  {
    return this.mHeaderView;
  }

  public MenuItem getItem(int paramInt)
  {
    return (MenuItem)this.mItems.get(paramInt);
  }

  public ArrayList<MenuItemImpl> getNonActionItems()
  {
    flagActionItems();
    return this.mNonActionItems;
  }

  boolean getOptionalIconsVisible()
  {
    return this.mOptionalIconsVisible;
  }

  Resources getResources()
  {
    return this.mResources;
  }

  public MenuBuilder getRootMenu()
  {
    return this;
  }

  public ArrayList<MenuItemImpl> getVisibleItems()
  {
    if (!this.mIsVisibleItemsStale)
      return this.mVisibleItems;
    this.mVisibleItems.clear();
    int i = this.mItems.size();
    for (int j = 0; j < i; j++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(j);
      if (localMenuItemImpl.isVisible())
        this.mVisibleItems.add(localMenuItemImpl);
    }
    this.mIsVisibleItemsStale = false;
    this.mIsActionItemsStale = true;
    return this.mVisibleItems;
  }

  public boolean hasVisibleItems()
  {
    if (this.mOverrideVisibleItems)
      return true;
    int i = size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label44;
      if (((MenuItemImpl)this.mItems.get(j)).isVisible())
        break;
    }
    label44: return false;
  }

  boolean isQwertyMode()
  {
    return this.mQwertyMode;
  }

  public boolean isShortcutKey(int paramInt, KeyEvent paramKeyEvent)
  {
    return findItemWithShortcutForKey(paramInt, paramKeyEvent) != null;
  }

  public boolean isShortcutsVisible()
  {
    return this.mShortcutsVisible;
  }

  void onItemActionRequestChanged(MenuItemImpl paramMenuItemImpl)
  {
    this.mIsActionItemsStale = true;
    onItemsChanged(true);
  }

  void onItemVisibleChanged(MenuItemImpl paramMenuItemImpl)
  {
    this.mIsVisibleItemsStale = true;
    onItemsChanged(true);
  }

  public void onItemsChanged(boolean paramBoolean)
  {
    if (!this.mPreventDispatchingItemsChanged)
    {
      if (paramBoolean)
      {
        this.mIsVisibleItemsStale = true;
        this.mIsActionItemsStale = true;
      }
      dispatchPresenterUpdate(paramBoolean);
      return;
    }
    this.mItemsChangedWhileDispatchPrevented = true;
  }

  public boolean performIdentifierAction(int paramInt1, int paramInt2)
  {
    return performItemAction(findItem(paramInt1), paramInt2);
  }

  public boolean performItemAction(MenuItem paramMenuItem, int paramInt)
  {
    return performItemAction(paramMenuItem, null, paramInt);
  }

  public boolean performItemAction(MenuItem paramMenuItem, MenuPresenter paramMenuPresenter, int paramInt)
  {
    MenuItemImpl localMenuItemImpl = (MenuItemImpl)paramMenuItem;
    boolean bool;
    if ((localMenuItemImpl == null) || (!localMenuItemImpl.isEnabled()))
      bool = false;
    label92: 
    do
    {
      do
      {
        return bool;
        bool = localMenuItemImpl.invoke();
        ActionProvider localActionProvider = localMenuItemImpl.getSupportActionProvider();
        if ((localActionProvider != null) && (localActionProvider.hasSubMenu()));
        for (int i = 1; ; i = 0)
        {
          if (!localMenuItemImpl.hasCollapsibleActionView())
            break label92;
          bool |= localMenuItemImpl.expandActionView();
          if (!bool)
            break;
          close(true);
          return bool;
        }
        if ((!localMenuItemImpl.hasSubMenu()) && (i == 0))
          break;
        close(false);
        if (!localMenuItemImpl.hasSubMenu())
          localMenuItemImpl.setSubMenu(new SubMenuBuilder(getContext(), this, localMenuItemImpl));
        SubMenuBuilder localSubMenuBuilder = (SubMenuBuilder)localMenuItemImpl.getSubMenu();
        if (i != 0)
          localActionProvider.onPrepareSubMenu(localSubMenuBuilder);
        bool |= dispatchSubMenuSelected(localSubMenuBuilder, paramMenuPresenter);
      }
      while (bool);
      close(true);
      return bool;
    }
    while ((paramInt & 0x1) != 0);
    close(true);
    return bool;
  }

  public boolean performShortcut(int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    MenuItemImpl localMenuItemImpl = findItemWithShortcutForKey(paramInt1, paramKeyEvent);
    boolean bool = false;
    if (localMenuItemImpl != null)
      bool = performItemAction(localMenuItemImpl, paramInt2);
    if ((paramInt2 & 0x2) != 0)
      close(true);
    return bool;
  }

  public void removeGroup(int paramInt)
  {
    int i = findGroupIndex(paramInt);
    if (i >= 0)
    {
      int j = this.mItems.size() - i;
      int m;
      for (int k = 0; ; k = m)
      {
        m = k + 1;
        if ((k >= j) || (((MenuItemImpl)this.mItems.get(i)).getGroupId() != paramInt))
          break;
        removeItemAtInt(i, false);
      }
      onItemsChanged(true);
    }
  }

  public void removeItem(int paramInt)
  {
    removeItemAtInt(findItemIndex(paramInt), true);
  }

  public void removeItemAt(int paramInt)
  {
    removeItemAtInt(paramInt, true);
  }

  public void removeMenuPresenter(MenuPresenter paramMenuPresenter)
  {
    Iterator localIterator = this.mPresenters.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
      if ((localMenuPresenter == null) || (localMenuPresenter == paramMenuPresenter))
        this.mPresenters.remove(localWeakReference);
    }
  }

  public void restoreActionViewStates(Bundle paramBundle)
  {
    if (paramBundle == null);
    MenuItem localMenuItem1;
    do
    {
      int k;
      do
      {
        return;
        SparseArray localSparseArray = paramBundle.getSparseParcelableArray(getActionViewStatesKey());
        int i = size();
        for (int j = 0; j < i; j++)
        {
          MenuItem localMenuItem2 = getItem(j);
          View localView = MenuItemCompat.getActionView(localMenuItem2);
          if ((localView != null) && (localView.getId() != -1))
            localView.restoreHierarchyState(localSparseArray);
          if (localMenuItem2.hasSubMenu())
            ((SubMenuBuilder)localMenuItem2.getSubMenu()).restoreActionViewStates(paramBundle);
        }
        k = paramBundle.getInt("android:menu:expandedactionview");
      }
      while (k <= 0);
      localMenuItem1 = findItem(k);
    }
    while (localMenuItem1 == null);
    MenuItemCompat.expandActionView(localMenuItem1);
  }

  public void restorePresenterStates(Bundle paramBundle)
  {
    dispatchRestoreInstanceState(paramBundle);
  }

  public void saveActionViewStates(Bundle paramBundle)
  {
    SparseArray localSparseArray = null;
    int i = size();
    for (int j = 0; j < i; j++)
    {
      MenuItem localMenuItem = getItem(j);
      View localView = MenuItemCompat.getActionView(localMenuItem);
      if ((localView != null) && (localView.getId() != -1))
      {
        if (localSparseArray == null)
          localSparseArray = new SparseArray();
        localView.saveHierarchyState(localSparseArray);
        if (MenuItemCompat.isActionViewExpanded(localMenuItem))
          paramBundle.putInt("android:menu:expandedactionview", localMenuItem.getItemId());
      }
      if (localMenuItem.hasSubMenu())
        ((SubMenuBuilder)localMenuItem.getSubMenu()).saveActionViewStates(paramBundle);
    }
    if (localSparseArray != null)
      paramBundle.putSparseParcelableArray(getActionViewStatesKey(), localSparseArray);
  }

  public void savePresenterStates(Bundle paramBundle)
  {
    dispatchSaveInstanceState(paramBundle);
  }

  public void setCallback(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }

  public void setCurrentMenuInfo(ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    this.mCurrentMenuInfo = paramContextMenuInfo;
  }

  public MenuBuilder setDefaultShowAsAction(int paramInt)
  {
    this.mDefaultShowAsAction = paramInt;
    return this;
  }

  void setExclusiveItemChecked(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getGroupId();
    int j = this.mItems.size();
    int k = 0;
    if (k < j)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(k);
      if ((localMenuItemImpl.getGroupId() != i) || (!localMenuItemImpl.isExclusiveCheckable()));
      while (!localMenuItemImpl.isCheckable())
      {
        k++;
        break;
      }
      if (localMenuItemImpl == paramMenuItem);
      for (boolean bool = true; ; bool = false)
      {
        localMenuItemImpl.setCheckedInt(bool);
        break;
      }
    }
  }

  public void setGroupCheckable(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = this.mItems.size();
    for (int j = 0; j < i; j++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(j);
      if (localMenuItemImpl.getGroupId() == paramInt)
      {
        localMenuItemImpl.setExclusiveCheckable(paramBoolean2);
        localMenuItemImpl.setCheckable(paramBoolean1);
      }
    }
  }

  public void setGroupEnabled(int paramInt, boolean paramBoolean)
  {
    int i = this.mItems.size();
    for (int j = 0; j < i; j++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(j);
      if (localMenuItemImpl.getGroupId() == paramInt)
        localMenuItemImpl.setEnabled(paramBoolean);
    }
  }

  public void setGroupVisible(int paramInt, boolean paramBoolean)
  {
    int i = this.mItems.size();
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(k);
      if ((localMenuItemImpl.getGroupId() == paramInt) && (localMenuItemImpl.setVisibleInt(paramBoolean)))
        j = 1;
    }
    if (j != 0)
      onItemsChanged(true);
  }

  protected MenuBuilder setHeaderIconInt(int paramInt)
  {
    setHeaderInternal(0, null, paramInt, null, null);
    return this;
  }

  protected MenuBuilder setHeaderIconInt(Drawable paramDrawable)
  {
    setHeaderInternal(0, null, 0, paramDrawable, null);
    return this;
  }

  protected MenuBuilder setHeaderTitleInt(int paramInt)
  {
    setHeaderInternal(paramInt, null, 0, null, null);
    return this;
  }

  protected MenuBuilder setHeaderTitleInt(CharSequence paramCharSequence)
  {
    setHeaderInternal(0, paramCharSequence, 0, null, null);
    return this;
  }

  protected MenuBuilder setHeaderViewInt(View paramView)
  {
    setHeaderInternal(0, null, 0, null, paramView);
    return this;
  }

  void setOptionalIconsVisible(boolean paramBoolean)
  {
    this.mOptionalIconsVisible = paramBoolean;
  }

  public void setOverrideVisibleItems(boolean paramBoolean)
  {
    this.mOverrideVisibleItems = paramBoolean;
  }

  public void setQwertyMode(boolean paramBoolean)
  {
    this.mQwertyMode = paramBoolean;
    onItemsChanged(false);
  }

  public void setShortcutsVisible(boolean paramBoolean)
  {
    if (this.mShortcutsVisible == paramBoolean)
      return;
    setShortcutsVisibleInner(paramBoolean);
    onItemsChanged(false);
  }

  public int size()
  {
    return this.mItems.size();
  }

  public void startDispatchingItemsChanged()
  {
    this.mPreventDispatchingItemsChanged = false;
    if (this.mItemsChangedWhileDispatchPrevented)
    {
      this.mItemsChangedWhileDispatchPrevented = false;
      onItemsChanged(true);
    }
  }

  public void stopDispatchingItemsChanged()
  {
    if (!this.mPreventDispatchingItemsChanged)
    {
      this.mPreventDispatchingItemsChanged = true;
      this.mItemsChangedWhileDispatchPrevented = false;
    }
  }

  public static abstract interface Callback
  {
    public abstract boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem);

    public abstract void onMenuModeChange(MenuBuilder paramMenuBuilder);
  }

  public static abstract interface ItemInvoker
  {
    public abstract boolean invokeItem(MenuItemImpl paramMenuItemImpl);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.MenuBuilder
 * JD-Core Version:    0.6.2
 */