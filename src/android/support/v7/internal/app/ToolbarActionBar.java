package android.support.v7.internal.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBar.OnMenuVisibilityListener;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.layout;
import android.support.v7.appcompat.R.style;
import android.support.v7.internal.view.WindowCallbackWrapper;
import android.support.v7.internal.view.menu.ListMenuPresenter;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.widget.DecorToolbar;
import android.support.v7.internal.widget.ToolbarWidgetWrapper;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.Window.Callback;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;

public class ToolbarActionBar extends ActionBar
{
  private DecorToolbar mDecorToolbar;
  private boolean mLastMenuVisibility;
  private ListMenuPresenter mListMenuPresenter;
  private boolean mMenuCallbackSet;
  private final Toolbar.OnMenuItemClickListener mMenuClicker = new Toolbar.OnMenuItemClickListener()
  {
    public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
    {
      return ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, paramAnonymousMenuItem);
    }
  };
  private final Runnable mMenuInvalidator = new Runnable()
  {
    public void run()
    {
      ToolbarActionBar.this.populateOptionsMenu();
    }
  };
  private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
  private boolean mToolbarMenuPrepared;
  private Window mWindow;
  private Window.Callback mWindowCallback;

  public ToolbarActionBar(Toolbar paramToolbar, CharSequence paramCharSequence, Window paramWindow)
  {
    this.mDecorToolbar = new ToolbarWidgetWrapper(paramToolbar, false);
    this.mWindowCallback = new ToolbarCallbackWrapper(paramWindow.getCallback());
    this.mDecorToolbar.setWindowCallback(this.mWindowCallback);
    paramToolbar.setOnMenuItemClickListener(this.mMenuClicker);
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
    this.mWindow = paramWindow;
  }

  private void ensureListMenuPresenter(Menu paramMenu)
  {
    MenuBuilder localMenuBuilder;
    Context localContext;
    Resources.Theme localTheme;
    if ((this.mListMenuPresenter == null) && ((paramMenu instanceof MenuBuilder)))
    {
      localMenuBuilder = (MenuBuilder)paramMenu;
      localContext = this.mDecorToolbar.getContext();
      TypedValue localTypedValue = new TypedValue();
      localTheme = localContext.getResources().newTheme();
      localTheme.setTo(localContext.getTheme());
      localTheme.resolveAttribute(R.attr.panelMenuListTheme, localTypedValue, true);
      if (localTypedValue.resourceId == 0)
        break label149;
      localTheme.applyStyle(localTypedValue.resourceId, true);
    }
    while (true)
    {
      ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(localContext, 0);
      localContextThemeWrapper.getTheme().setTo(localTheme);
      this.mListMenuPresenter = new ListMenuPresenter(localContextThemeWrapper, R.layout.abc_list_menu_item_layout);
      this.mListMenuPresenter.setCallback(new PanelMenuPresenterCallback(null));
      localMenuBuilder.addMenuPresenter(this.mListMenuPresenter);
      return;
      label149: localTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
    }
  }

  private View getListMenuView(Menu paramMenu)
  {
    ensureListMenuPresenter(paramMenu);
    if ((paramMenu == null) || (this.mListMenuPresenter == null));
    while (this.mListMenuPresenter.getAdapter().getCount() <= 0)
      return null;
    return (View)this.mListMenuPresenter.getMenuView(this.mDecorToolbar.getViewGroup());
  }

  private Menu getMenu()
  {
    if (!this.mMenuCallbackSet)
    {
      this.mDecorToolbar.setMenuCallbacks(new ActionMenuPresenterCallback(null), new MenuBuilderCallback(null));
      this.mMenuCallbackSet = true;
    }
    return this.mDecorToolbar.getMenu();
  }

  public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    this.mMenuVisibilityListeners.add(paramOnMenuVisibilityListener);
  }

  public void addTab(ActionBar.Tab paramTab)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public void addTab(ActionBar.Tab paramTab, int paramInt)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public void addTab(ActionBar.Tab paramTab, int paramInt, boolean paramBoolean)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public void addTab(ActionBar.Tab paramTab, boolean paramBoolean)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public boolean collapseActionView()
  {
    if (this.mDecorToolbar.hasExpandedActionView())
    {
      this.mDecorToolbar.collapseActionView();
      return true;
    }
    return false;
  }

  public void dispatchMenuVisibilityChanged(boolean paramBoolean)
  {
    if (paramBoolean == this.mLastMenuVisibility);
    while (true)
    {
      return;
      this.mLastMenuVisibility = paramBoolean;
      int i = this.mMenuVisibilityListeners.size();
      for (int j = 0; j < i; j++)
        ((ActionBar.OnMenuVisibilityListener)this.mMenuVisibilityListeners.get(j)).onMenuVisibilityChanged(paramBoolean);
    }
  }

  public View getCustomView()
  {
    return this.mDecorToolbar.getCustomView();
  }

  public int getDisplayOptions()
  {
    return this.mDecorToolbar.getDisplayOptions();
  }

  public float getElevation()
  {
    return ViewCompat.getElevation(this.mDecorToolbar.getViewGroup());
  }

  public int getHeight()
  {
    return this.mDecorToolbar.getHeight();
  }

  public int getNavigationItemCount()
  {
    return 0;
  }

  public int getNavigationMode()
  {
    return 0;
  }

  public int getSelectedNavigationIndex()
  {
    return -1;
  }

  public ActionBar.Tab getSelectedTab()
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public CharSequence getSubtitle()
  {
    return this.mDecorToolbar.getSubtitle();
  }

  public ActionBar.Tab getTabAt(int paramInt)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public int getTabCount()
  {
    return 0;
  }

  public Context getThemedContext()
  {
    return this.mDecorToolbar.getContext();
  }

  public CharSequence getTitle()
  {
    return this.mDecorToolbar.getTitle();
  }

  public Window.Callback getWrappedWindowCallback()
  {
    return this.mWindowCallback;
  }

  public void hide()
  {
    this.mDecorToolbar.setVisibility(8);
  }

  public boolean invalidateOptionsMenu()
  {
    this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
    ViewCompat.postOnAnimation(this.mDecorToolbar.getViewGroup(), this.mMenuInvalidator);
    return true;
  }

  public boolean isShowing()
  {
    return this.mDecorToolbar.getVisibility() == 0;
  }

  public boolean isTitleTruncated()
  {
    return super.isTitleTruncated();
  }

  public ActionBar.Tab newTab()
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  public boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    Menu localMenu = getMenu();
    boolean bool = false;
    if (localMenu != null)
      bool = localMenu.performShortcut(paramInt, paramKeyEvent, 0);
    return bool;
  }

  public boolean onMenuKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 1)
      openOptionsMenu();
    return true;
  }

  public boolean openOptionsMenu()
  {
    return this.mDecorToolbar.showOverflowMenu();
  }

  void populateOptionsMenu()
  {
    Menu localMenu = getMenu();
    boolean bool = localMenu instanceof MenuBuilder;
    MenuBuilder localMenuBuilder = null;
    if (bool)
      localMenuBuilder = (MenuBuilder)localMenu;
    if (localMenuBuilder != null)
      localMenuBuilder.stopDispatchingItemsChanged();
    try
    {
      localMenu.clear();
      if ((!this.mWindowCallback.onCreatePanelMenu(0, localMenu)) || (!this.mWindowCallback.onPreparePanel(0, null, localMenu)))
        localMenu.clear();
      return;
    }
    finally
    {
      if (localMenuBuilder != null)
        localMenuBuilder.startDispatchingItemsChanged();
    }
  }

  public void removeAllTabs()
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    this.mMenuVisibilityListeners.remove(paramOnMenuVisibilityListener);
  }

  public void removeTab(ActionBar.Tab paramTab)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public void removeTabAt(int paramInt)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public void selectTab(ActionBar.Tab paramTab)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }

  public void setBackgroundDrawable(@Nullable Drawable paramDrawable)
  {
    this.mDecorToolbar.setBackgroundDrawable(paramDrawable);
  }

  public void setCustomView(int paramInt)
  {
    setCustomView(LayoutInflater.from(this.mDecorToolbar.getContext()).inflate(paramInt, this.mDecorToolbar.getViewGroup(), false));
  }

  public void setCustomView(View paramView)
  {
    setCustomView(paramView, new ActionBar.LayoutParams(-2, -2));
  }

  public void setCustomView(View paramView, ActionBar.LayoutParams paramLayoutParams)
  {
    paramView.setLayoutParams(paramLayoutParams);
    this.mDecorToolbar.setCustomView(paramView);
  }

  public void setDefaultDisplayHomeAsUpEnabled(boolean paramBoolean)
  {
  }

  public void setDisplayHomeAsUpEnabled(boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 4; ; i = 0)
    {
      setDisplayOptions(i, 4);
      return;
    }
  }

  public void setDisplayOptions(int paramInt)
  {
    setDisplayOptions(paramInt, -1);
  }

  public void setDisplayOptions(int paramInt1, int paramInt2)
  {
    int i = this.mDecorToolbar.getDisplayOptions();
    this.mDecorToolbar.setDisplayOptions(paramInt1 & paramInt2 | i & (paramInt2 ^ 0xFFFFFFFF));
  }

  public void setDisplayShowCustomEnabled(boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 16; ; i = 0)
    {
      setDisplayOptions(i, 16);
      return;
    }
  }

  public void setDisplayShowHomeEnabled(boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 2; ; i = 0)
    {
      setDisplayOptions(i, 2);
      return;
    }
  }

  public void setDisplayShowTitleEnabled(boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 8; ; i = 0)
    {
      setDisplayOptions(i, 8);
      return;
    }
  }

  public void setDisplayUseLogoEnabled(boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      setDisplayOptions(i, 1);
      return;
    }
  }

  public void setElevation(float paramFloat)
  {
    ViewCompat.setElevation(this.mDecorToolbar.getViewGroup(), paramFloat);
  }

  public void setHomeActionContentDescription(int paramInt)
  {
    this.mDecorToolbar.setNavigationContentDescription(paramInt);
  }

  public void setHomeActionContentDescription(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setNavigationContentDescription(paramCharSequence);
  }

  public void setHomeAsUpIndicator(int paramInt)
  {
    this.mDecorToolbar.setNavigationIcon(paramInt);
  }

  public void setHomeAsUpIndicator(Drawable paramDrawable)
  {
    this.mDecorToolbar.setNavigationIcon(paramDrawable);
  }

  public void setHomeButtonEnabled(boolean paramBoolean)
  {
  }

  public void setIcon(int paramInt)
  {
    this.mDecorToolbar.setIcon(paramInt);
  }

  public void setIcon(Drawable paramDrawable)
  {
    this.mDecorToolbar.setIcon(paramDrawable);
  }

  public void setListNavigationCallbacks(SpinnerAdapter paramSpinnerAdapter, ActionBar.OnNavigationListener paramOnNavigationListener)
  {
    this.mDecorToolbar.setDropdownParams(paramSpinnerAdapter, new NavItemSelectedListener(paramOnNavigationListener));
  }

  public void setLogo(int paramInt)
  {
    this.mDecorToolbar.setLogo(paramInt);
  }

  public void setLogo(Drawable paramDrawable)
  {
    this.mDecorToolbar.setLogo(paramDrawable);
  }

  public void setNavigationMode(int paramInt)
  {
    if (paramInt == 2)
      throw new IllegalArgumentException("Tabs not supported in this configuration");
    this.mDecorToolbar.setNavigationMode(paramInt);
  }

  public void setSelectedNavigationItem(int paramInt)
  {
    switch (this.mDecorToolbar.getNavigationMode())
    {
    default:
      throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
    case 1:
    }
    this.mDecorToolbar.setDropdownSelectedPosition(paramInt);
  }

  public void setShowHideAnimationEnabled(boolean paramBoolean)
  {
  }

  public void setSplitBackgroundDrawable(Drawable paramDrawable)
  {
  }

  public void setStackedBackgroundDrawable(Drawable paramDrawable)
  {
  }

  public void setSubtitle(int paramInt)
  {
    DecorToolbar localDecorToolbar = this.mDecorToolbar;
    if (paramInt != 0);
    for (CharSequence localCharSequence = this.mDecorToolbar.getContext().getText(paramInt); ; localCharSequence = null)
    {
      localDecorToolbar.setSubtitle(localCharSequence);
      return;
    }
  }

  public void setSubtitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setSubtitle(paramCharSequence);
  }

  public void setTitle(int paramInt)
  {
    DecorToolbar localDecorToolbar = this.mDecorToolbar;
    if (paramInt != 0);
    for (CharSequence localCharSequence = this.mDecorToolbar.getContext().getText(paramInt); ; localCharSequence = null)
    {
      localDecorToolbar.setTitle(localCharSequence);
      return;
    }
  }

  public void setTitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setTitle(paramCharSequence);
  }

  public void setWindowTitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }

  public void show()
  {
    this.mDecorToolbar.setVisibility(0);
  }

  private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    private boolean mClosingActionMenu;

    private ActionMenuPresenterCallback()
    {
    }

    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      if (this.mClosingActionMenu)
        return;
      this.mClosingActionMenu = true;
      ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus();
      if (ToolbarActionBar.this.mWindowCallback != null)
        ToolbarActionBar.this.mWindowCallback.onPanelClosed(8, paramMenuBuilder);
      this.mClosingActionMenu = false;
    }

    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if (ToolbarActionBar.this.mWindowCallback != null)
      {
        ToolbarActionBar.this.mWindowCallback.onMenuOpened(8, paramMenuBuilder);
        return true;
      }
      return false;
    }
  }

  private final class MenuBuilderCallback
    implements MenuBuilder.Callback
  {
    private MenuBuilderCallback()
    {
    }

    public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
    {
      return false;
    }

    public void onMenuModeChange(MenuBuilder paramMenuBuilder)
    {
      if (ToolbarActionBar.this.mWindowCallback != null)
      {
        if (!ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing())
          break label41;
        ToolbarActionBar.this.mWindowCallback.onPanelClosed(8, paramMenuBuilder);
      }
      label41: 
      while (!ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, paramMenuBuilder))
        return;
      ToolbarActionBar.this.mWindowCallback.onMenuOpened(8, paramMenuBuilder);
    }
  }

  private final class PanelMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    private PanelMenuPresenterCallback()
    {
    }

    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      if (ToolbarActionBar.this.mWindowCallback != null)
        ToolbarActionBar.this.mWindowCallback.onPanelClosed(0, paramMenuBuilder);
    }

    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if ((paramMenuBuilder == null) && (ToolbarActionBar.this.mWindowCallback != null))
        ToolbarActionBar.this.mWindowCallback.onMenuOpened(0, paramMenuBuilder);
      return true;
    }
  }

  private class ToolbarCallbackWrapper extends WindowCallbackWrapper
  {
    public ToolbarCallbackWrapper(Window.Callback arg2)
    {
      super();
    }

    public View onCreatePanelView(int paramInt)
    {
      switch (paramInt)
      {
      default:
      case 0:
      }
      Menu localMenu;
      do
      {
        return super.onCreatePanelView(paramInt);
        localMenu = ToolbarActionBar.this.mDecorToolbar.getMenu();
      }
      while ((!onPreparePanel(paramInt, null, localMenu)) || (!onMenuOpened(paramInt, localMenu)));
      return ToolbarActionBar.this.getListMenuView(localMenu);
    }

    public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
    {
      boolean bool = super.onPreparePanel(paramInt, paramView, paramMenu);
      if ((bool) && (!ToolbarActionBar.this.mToolbarMenuPrepared))
      {
        ToolbarActionBar.this.mDecorToolbar.setMenuPrepared();
        ToolbarActionBar.access$202(ToolbarActionBar.this, true);
      }
      return bool;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.app.ToolbarActionBar
 * JD-Core Version:    0.6.2
 */