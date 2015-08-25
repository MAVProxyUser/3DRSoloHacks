package android.support.v7.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.app.WindowDecorActionBar;
import android.support.v7.internal.view.SupportMenuInflater;
import android.support.v7.internal.view.WindowCallbackWrapper;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;

abstract class AppCompatDelegateImplBase extends AppCompatDelegate
{
  private ActionBar mActionBar;
  final AppCompatCallback mAppCompatCallback;
  final Context mContext;
  boolean mHasActionBar;
  private boolean mIsDestroyed;
  boolean mIsFloating;
  private MenuInflater mMenuInflater;
  final Window.Callback mOriginalWindowCallback;
  boolean mOverlayActionBar;
  boolean mOverlayActionMode;
  private CharSequence mTitle;
  final Window mWindow;
  boolean mWindowNoTitle;

  AppCompatDelegateImplBase(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    this.mContext = paramContext;
    this.mWindow = paramWindow;
    this.mAppCompatCallback = paramAppCompatCallback;
    this.mOriginalWindowCallback = this.mWindow.getCallback();
    if ((this.mOriginalWindowCallback instanceof AppCompatWindowCallbackBase))
      throw new IllegalStateException("AppCompat has already installed itself into the Window");
    this.mWindow.setCallback(wrapWindowCallback(this.mOriginalWindowCallback));
  }

  abstract ActionBar createSupportActionBar();

  abstract boolean dispatchKeyEvent(KeyEvent paramKeyEvent);

  final Context getActionBarThemedContext()
  {
    ActionBar localActionBar = getSupportActionBar();
    Context localContext = null;
    if (localActionBar != null)
      localContext = localActionBar.getThemedContext();
    if (localContext == null)
      localContext = this.mContext;
    return localContext;
  }

  public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate()
  {
    return new ActionBarDrawableToggleImpl(null);
  }

  public MenuInflater getMenuInflater()
  {
    if (this.mMenuInflater == null)
      this.mMenuInflater = new SupportMenuInflater(getActionBarThemedContext());
    return this.mMenuInflater;
  }

  public ActionBar getSupportActionBar()
  {
    if (this.mHasActionBar)
      if (this.mActionBar == null)
        this.mActionBar = createSupportActionBar();
    while (true)
    {
      return this.mActionBar;
      if ((this.mActionBar instanceof WindowDecorActionBar))
        this.mActionBar = null;
    }
  }

  final CharSequence getTitle()
  {
    if ((this.mOriginalWindowCallback instanceof Activity))
      return ((Activity)this.mOriginalWindowCallback).getTitle();
    return this.mTitle;
  }

  final Window.Callback getWindowCallback()
  {
    return this.mWindow.getCallback();
  }

  final boolean isDestroyed()
  {
    return this.mIsDestroyed;
  }

  public boolean isHandleNativeActionModesEnabled()
  {
    return false;
  }

  public void onCreate(Bundle paramBundle)
  {
    TypedArray localTypedArray = this.mContext.obtainStyledAttributes(R.styleable.Theme);
    if (!localTypedArray.hasValue(R.styleable.Theme_windowActionBar))
    {
      localTypedArray.recycle();
      throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
    }
    if (localTypedArray.getBoolean(R.styleable.Theme_windowActionBar, false))
      this.mHasActionBar = true;
    if (localTypedArray.getBoolean(R.styleable.Theme_windowActionBarOverlay, false))
      this.mOverlayActionBar = true;
    if (localTypedArray.getBoolean(R.styleable.Theme_windowActionModeOverlay, false))
      this.mOverlayActionMode = true;
    this.mIsFloating = localTypedArray.getBoolean(R.styleable.Theme_android_windowIsFloating, false);
    this.mWindowNoTitle = localTypedArray.getBoolean(R.styleable.Theme_windowNoTitle, false);
    localTypedArray.recycle();
  }

  public final void onDestroy()
  {
    this.mIsDestroyed = true;
  }

  abstract boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent);

  abstract boolean onMenuOpened(int paramInt, Menu paramMenu);

  abstract boolean onPanelClosed(int paramInt, Menu paramMenu);

  abstract void onTitleChanged(CharSequence paramCharSequence);

  final ActionBar peekSupportActionBar()
  {
    return this.mActionBar;
  }

  public void setHandleNativeActionModesEnabled(boolean paramBoolean)
  {
  }

  final void setSupportActionBar(ActionBar paramActionBar)
  {
    this.mActionBar = paramActionBar;
  }

  public final void setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    onTitleChanged(paramCharSequence);
  }

  abstract ActionMode startSupportActionModeFromWindow(ActionMode.Callback paramCallback);

  Window.Callback wrapWindowCallback(Window.Callback paramCallback)
  {
    return new AppCompatWindowCallbackBase(paramCallback);
  }

  private class ActionBarDrawableToggleImpl
    implements ActionBarDrawerToggle.Delegate
  {
    private ActionBarDrawableToggleImpl()
    {
    }

    public Context getActionBarThemedContext()
    {
      return AppCompatDelegateImplBase.this.getActionBarThemedContext();
    }

    public Drawable getThemeUpIndicator()
    {
      Context localContext = getActionBarThemedContext();
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = R.attr.homeAsUpIndicator;
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(localContext, null, arrayOfInt);
      Drawable localDrawable = localTintTypedArray.getDrawable(0);
      localTintTypedArray.recycle();
      return localDrawable;
    }

    public boolean isNavigationVisible()
    {
      ActionBar localActionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
      return (localActionBar != null) && ((0x4 & localActionBar.getDisplayOptions()) != 0);
    }

    public void setActionBarDescription(int paramInt)
    {
      ActionBar localActionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
      if (localActionBar != null)
        localActionBar.setHomeActionContentDescription(paramInt);
    }

    public void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
    {
      ActionBar localActionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
      if (localActionBar != null)
      {
        localActionBar.setHomeAsUpIndicator(paramDrawable);
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
  }

  class AppCompatWindowCallbackBase extends WindowCallbackWrapper
  {
    AppCompatWindowCallbackBase(Window.Callback arg2)
    {
      super();
    }

    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      if (super.dispatchKeyEvent(paramKeyEvent))
        return true;
      return AppCompatDelegateImplBase.this.dispatchKeyEvent(paramKeyEvent);
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent paramKeyEvent)
    {
      if (AppCompatDelegateImplBase.this.onKeyShortcut(paramKeyEvent.getKeyCode(), paramKeyEvent))
        return true;
      return super.dispatchKeyShortcutEvent(paramKeyEvent);
    }

    public void onContentChanged()
    {
    }

    public boolean onCreatePanelMenu(int paramInt, Menu paramMenu)
    {
      if ((paramInt == 0) && (!(paramMenu instanceof MenuBuilder)))
        return false;
      return super.onCreatePanelMenu(paramInt, paramMenu);
    }

    public boolean onMenuOpened(int paramInt, Menu paramMenu)
    {
      if (AppCompatDelegateImplBase.this.onMenuOpened(paramInt, paramMenu))
        return true;
      return super.onMenuOpened(paramInt, paramMenu);
    }

    public void onPanelClosed(int paramInt, Menu paramMenu)
    {
      if (AppCompatDelegateImplBase.this.onPanelClosed(paramInt, paramMenu))
        return;
      super.onPanelClosed(paramInt, paramMenu);
    }

    public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
    {
      MenuBuilder localMenuBuilder;
      boolean bool;
      if ((paramMenu instanceof MenuBuilder))
      {
        localMenuBuilder = (MenuBuilder)paramMenu;
        if ((paramInt != 0) || (localMenuBuilder != null))
          break label34;
        bool = false;
      }
      label34: 
      do
      {
        return bool;
        localMenuBuilder = null;
        break;
        if (localMenuBuilder != null)
          localMenuBuilder.setOverrideVisibleItems(true);
        bool = super.onPreparePanel(paramInt, paramView, paramMenu);
      }
      while (localMenuBuilder == null);
      localMenuBuilder.setOverrideVisibleItems(false);
      return bool;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.app.AppCompatDelegateImplBase
 * JD-Core Version:    0.6.2
 */