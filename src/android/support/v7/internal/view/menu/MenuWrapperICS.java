package android.support.v7.internal.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.internal.view.SupportMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

class MenuWrapperICS extends BaseMenuWrapper<SupportMenu>
  implements Menu
{
  MenuWrapperICS(Context paramContext, SupportMenu paramSupportMenu)
  {
    super(paramContext, paramSupportMenu);
  }

  public MenuItem add(int paramInt)
  {
    return getMenuItemWrapper(((SupportMenu)this.mWrappedObject).add(paramInt));
  }

  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return getMenuItemWrapper(((SupportMenu)this.mWrappedObject).add(paramInt1, paramInt2, paramInt3, paramInt4));
  }

  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    return getMenuItemWrapper(((SupportMenu)this.mWrappedObject).add(paramInt1, paramInt2, paramInt3, paramCharSequence));
  }

  public MenuItem add(CharSequence paramCharSequence)
  {
    return getMenuItemWrapper(((SupportMenu)this.mWrappedObject).add(paramCharSequence));
  }

  public int addIntentOptions(int paramInt1, int paramInt2, int paramInt3, ComponentName paramComponentName, Intent[] paramArrayOfIntent, Intent paramIntent, int paramInt4, MenuItem[] paramArrayOfMenuItem)
  {
    MenuItem[] arrayOfMenuItem = null;
    if (paramArrayOfMenuItem != null)
      arrayOfMenuItem = new MenuItem[paramArrayOfMenuItem.length];
    int i = ((SupportMenu)this.mWrappedObject).addIntentOptions(paramInt1, paramInt2, paramInt3, paramComponentName, paramArrayOfIntent, paramIntent, paramInt4, arrayOfMenuItem);
    if (arrayOfMenuItem != null)
    {
      int j = 0;
      int k = arrayOfMenuItem.length;
      while (j < k)
      {
        paramArrayOfMenuItem[j] = getMenuItemWrapper(arrayOfMenuItem[j]);
        j++;
      }
    }
    return i;
  }

  public SubMenu addSubMenu(int paramInt)
  {
    return getSubMenuWrapper(((SupportMenu)this.mWrappedObject).addSubMenu(paramInt));
  }

  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return getSubMenuWrapper(((SupportMenu)this.mWrappedObject).addSubMenu(paramInt1, paramInt2, paramInt3, paramInt4));
  }

  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    return getSubMenuWrapper(((SupportMenu)this.mWrappedObject).addSubMenu(paramInt1, paramInt2, paramInt3, paramCharSequence));
  }

  public SubMenu addSubMenu(CharSequence paramCharSequence)
  {
    return getSubMenuWrapper(((SupportMenu)this.mWrappedObject).addSubMenu(paramCharSequence));
  }

  public void clear()
  {
    internalClear();
    ((SupportMenu)this.mWrappedObject).clear();
  }

  public void close()
  {
    ((SupportMenu)this.mWrappedObject).close();
  }

  public MenuItem findItem(int paramInt)
  {
    return getMenuItemWrapper(((SupportMenu)this.mWrappedObject).findItem(paramInt));
  }

  public MenuItem getItem(int paramInt)
  {
    return getMenuItemWrapper(((SupportMenu)this.mWrappedObject).getItem(paramInt));
  }

  public boolean hasVisibleItems()
  {
    return ((SupportMenu)this.mWrappedObject).hasVisibleItems();
  }

  public boolean isShortcutKey(int paramInt, KeyEvent paramKeyEvent)
  {
    return ((SupportMenu)this.mWrappedObject).isShortcutKey(paramInt, paramKeyEvent);
  }

  public boolean performIdentifierAction(int paramInt1, int paramInt2)
  {
    return ((SupportMenu)this.mWrappedObject).performIdentifierAction(paramInt1, paramInt2);
  }

  public boolean performShortcut(int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    return ((SupportMenu)this.mWrappedObject).performShortcut(paramInt1, paramKeyEvent, paramInt2);
  }

  public void removeGroup(int paramInt)
  {
    internalRemoveGroup(paramInt);
    ((SupportMenu)this.mWrappedObject).removeGroup(paramInt);
  }

  public void removeItem(int paramInt)
  {
    internalRemoveItem(paramInt);
    ((SupportMenu)this.mWrappedObject).removeItem(paramInt);
  }

  public void setGroupCheckable(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    ((SupportMenu)this.mWrappedObject).setGroupCheckable(paramInt, paramBoolean1, paramBoolean2);
  }

  public void setGroupEnabled(int paramInt, boolean paramBoolean)
  {
    ((SupportMenu)this.mWrappedObject).setGroupEnabled(paramInt, paramBoolean);
  }

  public void setGroupVisible(int paramInt, boolean paramBoolean)
  {
    ((SupportMenu)this.mWrappedObject).setGroupVisible(paramInt, paramBoolean);
  }

  public void setQwertyMode(boolean paramBoolean)
  {
    ((SupportMenu)this.mWrappedObject).setQwertyMode(paramBoolean);
  }

  public int size()
  {
    return ((SupportMenu)this.mWrappedObject).size();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.MenuWrapperICS
 * JD-Core Version:    0.6.2
 */