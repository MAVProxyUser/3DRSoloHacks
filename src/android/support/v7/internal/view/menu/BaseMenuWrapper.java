package android.support.v7.internal.view.menu;

import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.support.v4.util.ArrayMap;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class BaseMenuWrapper<T> extends BaseWrapper<T>
{
  final Context mContext;
  private Map<SupportMenuItem, MenuItem> mMenuItems;
  private Map<SupportSubMenu, SubMenu> mSubMenus;

  BaseMenuWrapper(Context paramContext, T paramT)
  {
    super(paramT);
    this.mContext = paramContext;
  }

  final MenuItem getMenuItemWrapper(MenuItem paramMenuItem)
  {
    if ((paramMenuItem instanceof SupportMenuItem))
    {
      SupportMenuItem localSupportMenuItem = (SupportMenuItem)paramMenuItem;
      if (this.mMenuItems == null)
        this.mMenuItems = new ArrayMap();
      MenuItem localMenuItem = (MenuItem)this.mMenuItems.get(paramMenuItem);
      if (localMenuItem == null)
      {
        localMenuItem = MenuWrapperFactory.wrapSupportMenuItem(this.mContext, localSupportMenuItem);
        this.mMenuItems.put(localSupportMenuItem, localMenuItem);
      }
      return localMenuItem;
    }
    return paramMenuItem;
  }

  final SubMenu getSubMenuWrapper(SubMenu paramSubMenu)
  {
    if ((paramSubMenu instanceof SupportSubMenu))
    {
      SupportSubMenu localSupportSubMenu = (SupportSubMenu)paramSubMenu;
      if (this.mSubMenus == null)
        this.mSubMenus = new ArrayMap();
      SubMenu localSubMenu = (SubMenu)this.mSubMenus.get(localSupportSubMenu);
      if (localSubMenu == null)
      {
        localSubMenu = MenuWrapperFactory.wrapSupportSubMenu(this.mContext, localSupportSubMenu);
        this.mSubMenus.put(localSupportSubMenu, localSubMenu);
      }
      return localSubMenu;
    }
    return paramSubMenu;
  }

  final void internalClear()
  {
    if (this.mMenuItems != null)
      this.mMenuItems.clear();
    if (this.mSubMenus != null)
      this.mSubMenus.clear();
  }

  final void internalRemoveGroup(int paramInt)
  {
    if (this.mMenuItems == null);
    while (true)
    {
      return;
      Iterator localIterator = this.mMenuItems.keySet().iterator();
      while (localIterator.hasNext())
        if (paramInt == ((MenuItem)localIterator.next()).getGroupId())
          localIterator.remove();
    }
  }

  final void internalRemoveItem(int paramInt)
  {
    if (this.mMenuItems == null);
    Iterator localIterator;
    do
    {
      return;
      while (!localIterator.hasNext())
        localIterator = this.mMenuItems.keySet().iterator();
    }
    while (paramInt != ((MenuItem)localIterator.next()).getItemId());
    localIterator.remove();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.BaseMenuWrapper
 * JD-Core Version:    0.6.2
 */