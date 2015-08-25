package android.support.v7.internal.view.menu;

import android.graphics.drawable.Drawable;

public abstract interface MenuView
{
  public abstract int getWindowAnimations();

  public abstract void initialize(MenuBuilder paramMenuBuilder);

  public static abstract interface ItemView
  {
    public abstract MenuItemImpl getItemData();

    public abstract void initialize(MenuItemImpl paramMenuItemImpl, int paramInt);

    public abstract boolean prefersCondensedTitle();

    public abstract void setCheckable(boolean paramBoolean);

    public abstract void setChecked(boolean paramBoolean);

    public abstract void setEnabled(boolean paramBoolean);

    public abstract void setIcon(Drawable paramDrawable);

    public abstract void setShortcut(boolean paramBoolean, char paramChar);

    public abstract void setTitle(CharSequence paramCharSequence);

    public abstract boolean showsIcon();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.MenuView
 * JD-Core Version:    0.6.2
 */