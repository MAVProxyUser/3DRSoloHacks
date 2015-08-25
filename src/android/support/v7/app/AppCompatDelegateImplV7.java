package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.color;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.layout;
import android.support.v7.appcompat.R.style;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.app.AppCompatViewInflater;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.internal.app.WindowDecorActionBar;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.internal.view.StandaloneActionMode;
import android.support.v7.internal.view.menu.ListMenuPresenter;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.widget.ActionBarContextView;
import android.support.v7.internal.widget.ContentFrameLayout;
import android.support.v7.internal.widget.DecorContentParent;
import android.support.v7.internal.widget.FitWindowsViewGroup;
import android.support.v7.internal.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.ViewStubCompat;
import android.support.v7.internal.widget.ViewUtils;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

class AppCompatDelegateImplV7 extends AppCompatDelegateImplBase
  implements MenuBuilder.Callback, LayoutInflaterFactory
{
  private ActionMenuPresenterCallback mActionMenuPresenterCallback;
  ActionMode mActionMode;
  PopupWindow mActionModePopup;
  ActionBarContextView mActionModeView;
  private AppCompatViewInflater mAppCompatViewInflater;
  private boolean mClosingActionMenu;
  private DecorContentParent mDecorContentParent;
  private boolean mEnableDefaultActionBarUp;
  private boolean mFeatureIndeterminateProgress;
  private boolean mFeatureProgress;
  private int mInvalidatePanelMenuFeatures;
  private boolean mInvalidatePanelMenuPosted;
  private final Runnable mInvalidatePanelMenuRunnable = new Runnable()
  {
    public void run()
    {
      if ((0x1 & AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures) != 0)
        AppCompatDelegateImplV7.this.doInvalidatePanelMenu(0);
      if ((0x100 & AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures) != 0)
        AppCompatDelegateImplV7.this.doInvalidatePanelMenu(8);
      AppCompatDelegateImplV7.access$202(AppCompatDelegateImplV7.this, false);
      AppCompatDelegateImplV7.access$002(AppCompatDelegateImplV7.this, 0);
    }
  };
  private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
  private PanelFeatureState[] mPanels;
  private PanelFeatureState mPreparedPanel;
  Runnable mShowActionModePopup;
  private View mStatusGuard;
  private ViewGroup mSubDecor;
  private boolean mSubDecorInstalled;
  private Rect mTempRect1;
  private Rect mTempRect2;
  private TextView mTitleView;
  private ViewGroup mWindowDecor;

  AppCompatDelegateImplV7(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    super(paramContext, paramWindow, paramAppCompatCallback);
  }

  private void applyFixedSizeWindow(ContentFrameLayout paramContentFrameLayout)
  {
    paramContentFrameLayout.setDecorPadding(this.mWindowDecor.getPaddingLeft(), this.mWindowDecor.getPaddingTop(), this.mWindowDecor.getPaddingRight(), this.mWindowDecor.getPaddingBottom());
    TypedArray localTypedArray = this.mContext.obtainStyledAttributes(R.styleable.Theme);
    localTypedArray.getValue(R.styleable.Theme_windowMinWidthMajor, paramContentFrameLayout.getMinWidthMajor());
    localTypedArray.getValue(R.styleable.Theme_windowMinWidthMinor, paramContentFrameLayout.getMinWidthMinor());
    if (localTypedArray.hasValue(R.styleable.Theme_windowFixedWidthMajor))
      localTypedArray.getValue(R.styleable.Theme_windowFixedWidthMajor, paramContentFrameLayout.getFixedWidthMajor());
    if (localTypedArray.hasValue(R.styleable.Theme_windowFixedWidthMinor))
      localTypedArray.getValue(R.styleable.Theme_windowFixedWidthMinor, paramContentFrameLayout.getFixedWidthMinor());
    if (localTypedArray.hasValue(R.styleable.Theme_windowFixedHeightMajor))
      localTypedArray.getValue(R.styleable.Theme_windowFixedHeightMajor, paramContentFrameLayout.getFixedHeightMajor());
    if (localTypedArray.hasValue(R.styleable.Theme_windowFixedHeightMinor))
      localTypedArray.getValue(R.styleable.Theme_windowFixedHeightMinor, paramContentFrameLayout.getFixedHeightMinor());
    localTypedArray.recycle();
    paramContentFrameLayout.requestLayout();
  }

  private void callOnPanelClosed(int paramInt, PanelFeatureState paramPanelFeatureState, Menu paramMenu)
  {
    if (paramMenu == null)
    {
      if ((paramPanelFeatureState == null) && (paramInt >= 0) && (paramInt < this.mPanels.length))
        paramPanelFeatureState = this.mPanels[paramInt];
      if (paramPanelFeatureState != null)
        paramMenu = paramPanelFeatureState.menu;
    }
    if ((paramPanelFeatureState != null) && (!paramPanelFeatureState.isOpen));
    Window.Callback localCallback;
    do
    {
      return;
      localCallback = getWindowCallback();
    }
    while (localCallback == null);
    localCallback.onPanelClosed(paramInt, paramMenu);
  }

  private void checkCloseActionMenu(MenuBuilder paramMenuBuilder)
  {
    if (this.mClosingActionMenu)
      return;
    this.mClosingActionMenu = true;
    this.mDecorContentParent.dismissPopups();
    Window.Callback localCallback = getWindowCallback();
    if ((localCallback != null) && (!isDestroyed()))
      localCallback.onPanelClosed(8, paramMenuBuilder);
    this.mClosingActionMenu = false;
  }

  private void closePanel(int paramInt)
  {
    closePanel(getPanelState(paramInt, true), true);
  }

  private void closePanel(PanelFeatureState paramPanelFeatureState, boolean paramBoolean)
  {
    if ((paramBoolean) && (paramPanelFeatureState.featureId == 0) && (this.mDecorContentParent != null) && (this.mDecorContentParent.isOverflowMenuShowing()))
      checkCloseActionMenu(paramPanelFeatureState.menu);
    do
    {
      return;
      boolean bool = paramPanelFeatureState.isOpen;
      WindowManager localWindowManager = (WindowManager)this.mContext.getSystemService("window");
      if ((localWindowManager != null) && (bool) && (paramPanelFeatureState.decorView != null))
        localWindowManager.removeView(paramPanelFeatureState.decorView);
      paramPanelFeatureState.isPrepared = false;
      paramPanelFeatureState.isHandled = false;
      paramPanelFeatureState.isOpen = false;
      if ((bool) && (paramBoolean))
        callOnPanelClosed(paramPanelFeatureState.featureId, paramPanelFeatureState, null);
      paramPanelFeatureState.shownPanelView = null;
      paramPanelFeatureState.refreshDecorView = true;
    }
    while (this.mPreparedPanel != paramPanelFeatureState);
    this.mPreparedPanel = null;
  }

  private void doInvalidatePanelMenu(int paramInt)
  {
    PanelFeatureState localPanelFeatureState1 = getPanelState(paramInt, true);
    if (localPanelFeatureState1.menu != null)
    {
      Bundle localBundle = new Bundle();
      localPanelFeatureState1.menu.saveActionViewStates(localBundle);
      if (localBundle.size() > 0)
        localPanelFeatureState1.frozenActionViewState = localBundle;
      localPanelFeatureState1.menu.stopDispatchingItemsChanged();
      localPanelFeatureState1.menu.clear();
    }
    localPanelFeatureState1.refreshMenuContent = true;
    localPanelFeatureState1.refreshDecorView = true;
    if (((paramInt == 8) || (paramInt == 0)) && (this.mDecorContentParent != null))
    {
      PanelFeatureState localPanelFeatureState2 = getPanelState(0, false);
      if (localPanelFeatureState2 != null)
      {
        localPanelFeatureState2.isPrepared = false;
        preparePanel(localPanelFeatureState2, null);
      }
    }
  }

  private void ensureSubDecor()
  {
    if (!this.mSubDecorInstalled)
    {
      LayoutInflater localLayoutInflater = LayoutInflater.from(this.mContext);
      if (!this.mWindowNoTitle)
        if (this.mIsFloating)
        {
          this.mSubDecor = ((ViewGroup)localLayoutInflater.inflate(R.layout.abc_dialog_title_material, null));
          this.mOverlayActionBar = false;
          this.mHasActionBar = false;
        }
      while (this.mSubDecor == null)
      {
        throw new IllegalArgumentException("AppCompat does not support the current theme features");
        if (this.mHasActionBar)
        {
          TypedValue localTypedValue = new TypedValue();
          this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, localTypedValue, true);
          if (localTypedValue.resourceId != 0);
          for (Object localObject = new ContextThemeWrapper(this.mContext, localTypedValue.resourceId); ; localObject = this.mContext)
          {
            this.mSubDecor = ((ViewGroup)LayoutInflater.from((Context)localObject).inflate(R.layout.abc_screen_toolbar, null));
            this.mDecorContentParent = ((DecorContentParent)this.mSubDecor.findViewById(R.id.decor_content_parent));
            this.mDecorContentParent.setWindowCallback(getWindowCallback());
            if (this.mOverlayActionBar)
              this.mDecorContentParent.initFeature(9);
            if (this.mFeatureProgress)
              this.mDecorContentParent.initFeature(2);
            if (!this.mFeatureIndeterminateProgress)
              break;
            this.mDecorContentParent.initFeature(5);
            break;
          }
          if (this.mOverlayActionMode);
          for (this.mSubDecor = ((ViewGroup)localLayoutInflater.inflate(R.layout.abc_screen_simple_overlay_action_mode, null)); ; this.mSubDecor = ((ViewGroup)localLayoutInflater.inflate(R.layout.abc_screen_simple, null)))
          {
            if (Build.VERSION.SDK_INT < 21)
              break label310;
            ViewCompat.setOnApplyWindowInsetsListener(this.mSubDecor, new OnApplyWindowInsetsListener()
            {
              public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
              {
                int i = paramAnonymousWindowInsetsCompat.getSystemWindowInsetTop();
                int j = AppCompatDelegateImplV7.this.updateStatusGuard(i);
                if (i != j)
                  paramAnonymousWindowInsetsCompat = paramAnonymousWindowInsetsCompat.replaceSystemWindowInsets(paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft(), j, paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight(), paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom());
                return ViewCompat.onApplyWindowInsets(paramAnonymousView, paramAnonymousWindowInsetsCompat);
              }
            });
            break;
          }
          label310: ((FitWindowsViewGroup)this.mSubDecor).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener()
          {
            public void onFitSystemWindows(Rect paramAnonymousRect)
            {
              paramAnonymousRect.top = AppCompatDelegateImplV7.this.updateStatusGuard(paramAnonymousRect.top);
            }
          });
        }
      }
      if (this.mDecorContentParent == null)
        this.mTitleView = ((TextView)this.mSubDecor.findViewById(R.id.title));
      ViewUtils.makeOptionalFitsSystemWindows(this.mSubDecor);
      ViewGroup localViewGroup = (ViewGroup)this.mWindow.findViewById(16908290);
      ContentFrameLayout localContentFrameLayout = (ContentFrameLayout)this.mSubDecor.findViewById(R.id.action_bar_activity_content);
      while (localViewGroup.getChildCount() > 0)
      {
        View localView = localViewGroup.getChildAt(0);
        localViewGroup.removeViewAt(0);
        localContentFrameLayout.addView(localView);
      }
      this.mWindow.setContentView(this.mSubDecor);
      localViewGroup.setId(-1);
      localContentFrameLayout.setId(16908290);
      if ((localViewGroup instanceof FrameLayout))
        ((FrameLayout)localViewGroup).setForeground(null);
      CharSequence localCharSequence = getTitle();
      if (!TextUtils.isEmpty(localCharSequence))
        onTitleChanged(localCharSequence);
      applyFixedSizeWindow(localContentFrameLayout);
      onSubDecorInstalled(this.mSubDecor);
      this.mSubDecorInstalled = true;
      PanelFeatureState localPanelFeatureState = getPanelState(0, false);
      if ((!isDestroyed()) && ((localPanelFeatureState == null) || (localPanelFeatureState.menu == null)))
        invalidatePanelMenu(8);
    }
  }

  private PanelFeatureState findMenuPanel(Menu paramMenu)
  {
    PanelFeatureState[] arrayOfPanelFeatureState = this.mPanels;
    int i;
    if (arrayOfPanelFeatureState != null)
      i = arrayOfPanelFeatureState.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label55;
      PanelFeatureState localPanelFeatureState = arrayOfPanelFeatureState[j];
      if ((localPanelFeatureState != null) && (localPanelFeatureState.menu == paramMenu))
      {
        return localPanelFeatureState;
        i = 0;
        break;
      }
    }
    label55: return null;
  }

  private PanelFeatureState getPanelState(int paramInt, boolean paramBoolean)
  {
    Object localObject = this.mPanels;
    if ((localObject == null) || (localObject.length <= paramInt))
    {
      PanelFeatureState[] arrayOfPanelFeatureState = new PanelFeatureState[paramInt + 1];
      if (localObject != null)
        System.arraycopy(localObject, 0, arrayOfPanelFeatureState, 0, localObject.length);
      localObject = arrayOfPanelFeatureState;
      this.mPanels = arrayOfPanelFeatureState;
    }
    PanelFeatureState localPanelFeatureState = localObject[paramInt];
    if (localPanelFeatureState == null)
    {
      localPanelFeatureState = new PanelFeatureState(paramInt);
      localObject[paramInt] = localPanelFeatureState;
    }
    return localPanelFeatureState;
  }

  private boolean initializePanelContent(PanelFeatureState paramPanelFeatureState)
  {
    if (paramPanelFeatureState.createdPanelView != null)
      paramPanelFeatureState.shownPanelView = paramPanelFeatureState.createdPanelView;
    do
    {
      return true;
      if (paramPanelFeatureState.menu == null)
        return false;
      if (this.mPanelMenuPresenterCallback == null)
        this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback(null);
      paramPanelFeatureState.shownPanelView = ((View)paramPanelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback));
    }
    while (paramPanelFeatureState.shownPanelView != null);
    return false;
  }

  private boolean initializePanelDecor(PanelFeatureState paramPanelFeatureState)
  {
    paramPanelFeatureState.setStyle(getActionBarThemedContext());
    paramPanelFeatureState.decorView = new ListMenuDecorView(paramPanelFeatureState.listPresenterContext);
    paramPanelFeatureState.gravity = 81;
    return true;
  }

  private boolean initializePanelMenu(PanelFeatureState paramPanelFeatureState)
  {
    Object localObject = this.mContext;
    TypedValue localTypedValue;
    Resources.Theme localTheme1;
    Resources.Theme localTheme2;
    if (((paramPanelFeatureState.featureId == 0) || (paramPanelFeatureState.featureId == 8)) && (this.mDecorContentParent != null))
    {
      localTypedValue = new TypedValue();
      localTheme1 = ((Context)localObject).getTheme();
      localTheme1.resolveAttribute(R.attr.actionBarTheme, localTypedValue, true);
      if (localTypedValue.resourceId == 0)
        break label188;
      localTheme2 = ((Context)localObject).getResources().newTheme();
      localTheme2.setTo(localTheme1);
      localTheme2.applyStyle(localTypedValue.resourceId, true);
      localTheme2.resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
    }
    while (true)
    {
      if (localTypedValue.resourceId != 0)
      {
        if (localTheme2 == null)
        {
          localTheme2 = ((Context)localObject).getResources().newTheme();
          localTheme2.setTo(localTheme1);
        }
        localTheme2.applyStyle(localTypedValue.resourceId, true);
      }
      if (localTheme2 != null)
      {
        ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper((Context)localObject, 0);
        localContextThemeWrapper.getTheme().setTo(localTheme2);
        localObject = localContextThemeWrapper;
      }
      MenuBuilder localMenuBuilder = new MenuBuilder((Context)localObject);
      localMenuBuilder.setCallback(this);
      paramPanelFeatureState.setMenu(localMenuBuilder);
      return true;
      label188: localTheme1.resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
      localTheme2 = null;
    }
  }

  private void invalidatePanelMenu(int paramInt)
  {
    this.mInvalidatePanelMenuFeatures |= 1 << paramInt;
    if ((!this.mInvalidatePanelMenuPosted) && (this.mWindowDecor != null))
    {
      ViewCompat.postOnAnimation(this.mWindowDecor, this.mInvalidatePanelMenuRunnable);
      this.mInvalidatePanelMenuPosted = true;
    }
  }

  private boolean onKeyDownPanel(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getRepeatCount() == 0)
    {
      PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
      if (!localPanelFeatureState.isOpen)
        return preparePanel(localPanelFeatureState, paramKeyEvent);
    }
    return false;
  }

  private void onKeyUpPanel(int paramInt, KeyEvent paramKeyEvent)
  {
    if (this.mActionMode != null);
    while (true)
    {
      return;
      PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
      boolean bool1;
      if ((paramInt == 0) && (this.mDecorContentParent != null) && (this.mDecorContentParent.canShowOverflowMenu()) && (!ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this.mContext))))
        if (!this.mDecorContentParent.isOverflowMenuShowing())
        {
          boolean bool4 = isDestroyed();
          bool1 = false;
          if (!bool4)
          {
            boolean bool5 = preparePanel(localPanelFeatureState, paramKeyEvent);
            bool1 = false;
            if (bool5)
              bool1 = this.mDecorContentParent.showOverflowMenu();
          }
        }
      while (bool1)
      {
        AudioManager localAudioManager = (AudioManager)this.mContext.getSystemService("audio");
        if (localAudioManager == null)
          break label236;
        localAudioManager.playSoundEffect(0);
        return;
        bool1 = this.mDecorContentParent.hideOverflowMenu();
        continue;
        if ((localPanelFeatureState.isOpen) || (localPanelFeatureState.isHandled))
        {
          bool1 = localPanelFeatureState.isOpen;
          closePanel(localPanelFeatureState, true);
        }
        else
        {
          boolean bool2 = localPanelFeatureState.isPrepared;
          bool1 = false;
          if (bool2)
          {
            boolean bool3 = true;
            if (localPanelFeatureState.refreshMenuContent)
            {
              localPanelFeatureState.isPrepared = false;
              bool3 = preparePanel(localPanelFeatureState, paramKeyEvent);
            }
            bool1 = false;
            if (bool3)
            {
              openPanel(localPanelFeatureState, paramKeyEvent);
              bool1 = true;
            }
          }
        }
      }
    }
    label236: Log.w("AppCompatDelegate", "Couldn't get audio manager");
  }

  private void openPanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if ((paramPanelFeatureState.isOpen) || (isDestroyed()));
    label108: label114: label118: label120: WindowManager localWindowManager;
    int i;
    do
    {
      do
      {
        while (true)
        {
          return;
          int k;
          if (paramPanelFeatureState.featureId == 0)
          {
            Context localContext = this.mContext;
            if ((0xF & localContext.getResources().getConfiguration().screenLayout) != 4)
              break label108;
            k = 1;
            if (localContext.getApplicationInfo().targetSdkVersion < 11)
              break label114;
          }
          for (int m = 1; ; m = 0)
          {
            if ((k != 0) && (m != 0))
              break label118;
            Window.Callback localCallback = getWindowCallback();
            if ((localCallback == null) || (localCallback.onMenuOpened(paramPanelFeatureState.featureId, paramPanelFeatureState.menu)))
              break label120;
            closePanel(paramPanelFeatureState, true);
            return;
            k = 0;
            break;
          }
        }
        localWindowManager = (WindowManager)this.mContext.getSystemService("window");
      }
      while ((localWindowManager == null) || (!preparePanel(paramPanelFeatureState, paramKeyEvent)));
      i = -2;
      if ((paramPanelFeatureState.decorView != null) && (!paramPanelFeatureState.refreshDecorView))
        break label408;
      if (paramPanelFeatureState.decorView != null)
        break;
    }
    while ((!initializePanelDecor(paramPanelFeatureState)) || (paramPanelFeatureState.decorView == null));
    label188: if ((initializePanelContent(paramPanelFeatureState)) && (paramPanelFeatureState.hasPanelItems()))
    {
      ViewGroup.LayoutParams localLayoutParams1 = paramPanelFeatureState.shownPanelView.getLayoutParams();
      if (localLayoutParams1 == null)
        localLayoutParams1 = new ViewGroup.LayoutParams(-2, -2);
      int j = paramPanelFeatureState.background;
      paramPanelFeatureState.decorView.setBackgroundResource(j);
      ViewParent localViewParent = paramPanelFeatureState.shownPanelView.getParent();
      if ((localViewParent != null) && ((localViewParent instanceof ViewGroup)))
        ((ViewGroup)localViewParent).removeView(paramPanelFeatureState.shownPanelView);
      paramPanelFeatureState.decorView.addView(paramPanelFeatureState.shownPanelView, localLayoutParams1);
      if (!paramPanelFeatureState.shownPanelView.hasFocus())
        paramPanelFeatureState.shownPanelView.requestFocus();
    }
    while (true)
    {
      paramPanelFeatureState.isHandled = false;
      WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams(i, -2, paramPanelFeatureState.x, paramPanelFeatureState.y, 1002, 8519680, -3);
      localLayoutParams.gravity = paramPanelFeatureState.gravity;
      localLayoutParams.windowAnimations = paramPanelFeatureState.windowAnimations;
      localWindowManager.addView(paramPanelFeatureState.decorView, localLayoutParams);
      paramPanelFeatureState.isOpen = true;
      return;
      if ((!paramPanelFeatureState.refreshDecorView) || (paramPanelFeatureState.decorView.getChildCount() <= 0))
        break label188;
      paramPanelFeatureState.decorView.removeAllViews();
      break label188;
      break;
      label408: if (paramPanelFeatureState.createdPanelView != null)
      {
        ViewGroup.LayoutParams localLayoutParams2 = paramPanelFeatureState.createdPanelView.getLayoutParams();
        if ((localLayoutParams2 != null) && (localLayoutParams2.width == -1))
          i = -1;
      }
    }
  }

  private boolean performPanelShortcut(PanelFeatureState paramPanelFeatureState, int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    boolean bool1;
    if (paramKeyEvent.isSystem())
      bool1 = false;
    do
    {
      return bool1;
      if (!paramPanelFeatureState.isPrepared)
      {
        boolean bool2 = preparePanel(paramPanelFeatureState, paramKeyEvent);
        bool1 = false;
        if (!bool2);
      }
      else
      {
        MenuBuilder localMenuBuilder = paramPanelFeatureState.menu;
        bool1 = false;
        if (localMenuBuilder != null)
          bool1 = paramPanelFeatureState.menu.performShortcut(paramInt1, paramKeyEvent, paramInt2);
      }
    }
    while ((!bool1) || ((paramInt2 & 0x1) != 0) || (this.mDecorContentParent != null));
    closePanel(paramPanelFeatureState, true);
    return bool1;
  }

  private boolean preparePanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if (isDestroyed())
      return false;
    if (paramPanelFeatureState.isPrepared)
      return true;
    if ((this.mPreparedPanel != null) && (this.mPreparedPanel != paramPanelFeatureState))
      closePanel(this.mPreparedPanel, false);
    Window.Callback localCallback = getWindowCallback();
    if (localCallback != null)
      paramPanelFeatureState.createdPanelView = localCallback.onCreatePanelView(paramPanelFeatureState.featureId);
    if ((paramPanelFeatureState.featureId == 0) || (paramPanelFeatureState.featureId == 8));
    for (int i = 1; ; i = 0)
    {
      if ((i != 0) && (this.mDecorContentParent != null))
        this.mDecorContentParent.setMenuPrepared();
      if (paramPanelFeatureState.createdPanelView != null)
        break label397;
      if ((paramPanelFeatureState.menu != null) && (!paramPanelFeatureState.refreshMenuContent))
        break label265;
      if ((paramPanelFeatureState.menu == null) && ((!initializePanelMenu(paramPanelFeatureState)) || (paramPanelFeatureState.menu == null)))
        break;
      if ((i != 0) && (this.mDecorContentParent != null))
      {
        if (this.mActionMenuPresenterCallback == null)
          this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback(null);
        this.mDecorContentParent.setMenu(paramPanelFeatureState.menu, this.mActionMenuPresenterCallback);
      }
      paramPanelFeatureState.menu.stopDispatchingItemsChanged();
      if (localCallback.onCreatePanelMenu(paramPanelFeatureState.featureId, paramPanelFeatureState.menu))
        break label260;
      paramPanelFeatureState.setMenu(null);
      if ((i == 0) || (this.mDecorContentParent == null))
        break;
      this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
      return false;
    }
    label260: paramPanelFeatureState.refreshMenuContent = false;
    label265: paramPanelFeatureState.menu.stopDispatchingItemsChanged();
    if (paramPanelFeatureState.frozenActionViewState != null)
    {
      paramPanelFeatureState.menu.restoreActionViewStates(paramPanelFeatureState.frozenActionViewState);
      paramPanelFeatureState.frozenActionViewState = null;
    }
    if (!localCallback.onPreparePanel(0, paramPanelFeatureState.createdPanelView, paramPanelFeatureState.menu))
    {
      if ((i != 0) && (this.mDecorContentParent != null))
        this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
      paramPanelFeatureState.menu.startDispatchingItemsChanged();
      return false;
    }
    int j;
    if (paramKeyEvent != null)
    {
      j = paramKeyEvent.getDeviceId();
      if (KeyCharacterMap.load(j).getKeyboardType() == 1)
        break label420;
    }
    label397: label420: for (boolean bool = true; ; bool = false)
    {
      paramPanelFeatureState.qwertyMode = bool;
      paramPanelFeatureState.menu.setQwertyMode(paramPanelFeatureState.qwertyMode);
      paramPanelFeatureState.menu.startDispatchingItemsChanged();
      paramPanelFeatureState.isPrepared = true;
      paramPanelFeatureState.isHandled = false;
      this.mPreparedPanel = paramPanelFeatureState;
      return true;
      j = -1;
      break;
    }
  }

  private void reopenMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    if ((this.mDecorContentParent != null) && (this.mDecorContentParent.canShowOverflowMenu()) && ((!ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this.mContext))) || (this.mDecorContentParent.isOverflowMenuShowPending())))
    {
      Window.Callback localCallback = getWindowCallback();
      if ((!this.mDecorContentParent.isOverflowMenuShowing()) || (!paramBoolean))
        if ((localCallback != null) && (!isDestroyed()))
        {
          if ((this.mInvalidatePanelMenuPosted) && ((0x1 & this.mInvalidatePanelMenuFeatures) != 0))
          {
            this.mWindowDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
          }
          PanelFeatureState localPanelFeatureState2 = getPanelState(0, true);
          if ((localPanelFeatureState2.menu != null) && (!localPanelFeatureState2.refreshMenuContent) && (localCallback.onPreparePanel(0, localPanelFeatureState2.createdPanelView, localPanelFeatureState2.menu)))
          {
            localCallback.onMenuOpened(8, localPanelFeatureState2.menu);
            this.mDecorContentParent.showOverflowMenu();
          }
        }
      do
      {
        return;
        this.mDecorContentParent.hideOverflowMenu();
      }
      while (isDestroyed());
      localCallback.onPanelClosed(8, getPanelState(0, true).menu);
      return;
    }
    PanelFeatureState localPanelFeatureState1 = getPanelState(0, true);
    localPanelFeatureState1.refreshDecorView = true;
    closePanel(localPanelFeatureState1, false);
    openPanel(localPanelFeatureState1, null);
  }

  private void throwFeatureRequestIfSubDecorInstalled()
  {
    if (this.mSubDecorInstalled)
      throw new AndroidRuntimeException("Window feature must be requested before adding content");
  }

  private int updateStatusGuard(int paramInt)
  {
    ActionBarContextView localActionBarContextView = this.mActionModeView;
    int i = 0;
    ViewGroup.MarginLayoutParams localMarginLayoutParams;
    int n;
    int m;
    if (localActionBarContextView != null)
    {
      boolean bool = this.mActionModeView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams;
      i = 0;
      if (bool)
      {
        localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mActionModeView.getLayoutParams();
        if (!this.mActionModeView.isShown())
          break label325;
        if (this.mTempRect1 == null)
        {
          this.mTempRect1 = new Rect();
          this.mTempRect2 = new Rect();
        }
        Rect localRect1 = this.mTempRect1;
        Rect localRect2 = this.mTempRect2;
        localRect1.set(0, paramInt, 0, 0);
        ViewUtils.computeFitSystemWindows(this.mSubDecor, localRect1, localRect2);
        if (localRect2.top != 0)
          break label278;
        n = paramInt;
        int i1 = localMarginLayoutParams.topMargin;
        m = 0;
        if (i1 != n)
        {
          m = 1;
          localMarginLayoutParams.topMargin = paramInt;
          if (this.mStatusGuard != null)
            break label284;
          this.mStatusGuard = new View(this.mContext);
          this.mStatusGuard.setBackgroundColor(this.mContext.getResources().getColor(R.color.abc_input_method_navigation_guard));
          this.mSubDecor.addView(this.mStatusGuard, -1, new ViewGroup.LayoutParams(-1, paramInt));
        }
        label213: if (this.mStatusGuard == null)
          break label320;
        i = 1;
        label222: if ((!this.mOverlayActionMode) && (i != 0))
          paramInt = 0;
        label235: if (m != 0)
          this.mActionModeView.setLayoutParams(localMarginLayoutParams);
      }
    }
    View localView;
    int j;
    if (this.mStatusGuard != null)
    {
      localView = this.mStatusGuard;
      j = 0;
      if (i == 0)
        break label356;
    }
    while (true)
    {
      localView.setVisibility(j);
      return paramInt;
      label278: n = 0;
      break;
      label284: ViewGroup.LayoutParams localLayoutParams = this.mStatusGuard.getLayoutParams();
      if (localLayoutParams.height == paramInt)
        break label213;
      localLayoutParams.height = paramInt;
      this.mStatusGuard.setLayoutParams(localLayoutParams);
      break label213;
      label320: i = 0;
      break label222;
      label325: int k = localMarginLayoutParams.topMargin;
      m = 0;
      i = 0;
      if (k == 0)
        break label235;
      m = 1;
      localMarginLayoutParams.topMargin = 0;
      i = 0;
      break label235;
      label356: j = 8;
    }
  }

  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ((ViewGroup)this.mSubDecor.findViewById(16908290)).addView(paramView, paramLayoutParams);
    this.mOriginalWindowCallback.onContentChanged();
  }

  View callActivityOnCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    if ((this.mOriginalWindowCallback instanceof LayoutInflater.Factory))
    {
      View localView = ((LayoutInflater.Factory)this.mOriginalWindowCallback).onCreateView(paramString, paramContext, paramAttributeSet);
      if (localView != null)
        return localView;
    }
    return null;
  }

  public ActionBar createSupportActionBar()
  {
    ensureSubDecor();
    WindowDecorActionBar localWindowDecorActionBar;
    if ((this.mOriginalWindowCallback instanceof Activity))
      localWindowDecorActionBar = new WindowDecorActionBar((Activity)this.mOriginalWindowCallback, this.mOverlayActionBar);
    while (true)
    {
      if (localWindowDecorActionBar != null)
        localWindowDecorActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
      return localWindowDecorActionBar;
      boolean bool = this.mOriginalWindowCallback instanceof Dialog;
      localWindowDecorActionBar = null;
      if (bool)
        localWindowDecorActionBar = new WindowDecorActionBar((Dialog)this.mOriginalWindowCallback);
    }
  }

  public View createView(View paramView, String paramString, @NonNull Context paramContext, @NonNull AttributeSet paramAttributeSet)
  {
    boolean bool1;
    if (Build.VERSION.SDK_INT < 21)
    {
      bool1 = true;
      if (this.mAppCompatViewInflater == null)
        this.mAppCompatViewInflater = new AppCompatViewInflater();
      if ((!bool1) || (!this.mSubDecorInstalled) || (paramView == null) || (paramView.getId() == 16908290) || (ViewCompat.isAttachedToWindow(paramView)))
        break label89;
    }
    label89: for (boolean bool2 = true; ; bool2 = false)
    {
      return this.mAppCompatViewInflater.createView(paramView, paramString, paramContext, paramAttributeSet, bool2, bool1, true);
      bool1 = false;
      break;
    }
  }

  boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    if (paramKeyEvent.getAction() == 0);
    for (int j = 1; j != 0; j = 0)
      return onKeyDown(i, paramKeyEvent);
    return onKeyUp(i, paramKeyEvent);
  }

  ViewGroup getSubDecor()
  {
    return this.mSubDecor;
  }

  public void installViewFactory()
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(this.mContext);
    if (localLayoutInflater.getFactory() == null)
    {
      LayoutInflaterCompat.setFactory(localLayoutInflater, this);
      return;
    }
    Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
  }

  public void invalidateOptionsMenu()
  {
    ActionBar localActionBar = getSupportActionBar();
    if ((localActionBar != null) && (localActionBar.invalidateOptionsMenu()))
      return;
    invalidatePanelMenu(0);
  }

  boolean onBackPressed()
  {
    if (this.mActionMode != null)
      this.mActionMode.finish();
    ActionBar localActionBar;
    do
    {
      return true;
      localActionBar = getSupportActionBar();
    }
    while ((localActionBar != null) && (localActionBar.collapseActionView()));
    return false;
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if ((this.mHasActionBar) && (this.mSubDecorInstalled))
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null)
        localActionBar.onConfigurationChanged(paramConfiguration);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mWindowDecor = ((ViewGroup)this.mWindow.getDecorView());
    ActionBar localActionBar;
    if (((this.mOriginalWindowCallback instanceof Activity)) && (NavUtils.getParentActivityName((Activity)this.mOriginalWindowCallback) != null))
    {
      localActionBar = peekSupportActionBar();
      if (localActionBar == null)
        this.mEnableDefaultActionBarUp = true;
    }
    else
    {
      return;
    }
    localActionBar.setDefaultDisplayHomeAsUpEnabled(true);
  }

  public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    View localView = callActivityOnCreateView(paramView, paramString, paramContext, paramAttributeSet);
    if (localView != null)
      return localView;
    return createView(paramView, paramString, paramContext, paramAttributeSet);
  }

  boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default:
      int i = Build.VERSION.SDK_INT;
      boolean bool = false;
      if (i < 11)
        bool = onKeyShortcut(paramInt, paramKeyEvent);
      return bool;
    case 82:
    }
    onKeyDownPanel(0, paramKeyEvent);
    return true;
  }

  boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    ActionBar localActionBar = getSupportActionBar();
    if ((localActionBar != null) && (localActionBar.onKeyShortcut(paramInt, paramKeyEvent)));
    boolean bool;
    do
    {
      do
      {
        return true;
        if ((this.mPreparedPanel == null) || (!performPanelShortcut(this.mPreparedPanel, paramKeyEvent.getKeyCode(), paramKeyEvent, 1)))
          break;
      }
      while (this.mPreparedPanel == null);
      this.mPreparedPanel.isHandled = true;
      return true;
      if (this.mPreparedPanel != null)
        break;
      PanelFeatureState localPanelFeatureState = getPanelState(0, true);
      preparePanel(localPanelFeatureState, paramKeyEvent);
      bool = performPanelShortcut(localPanelFeatureState, paramKeyEvent.getKeyCode(), paramKeyEvent, 1);
      localPanelFeatureState.isPrepared = false;
    }
    while (bool);
    return false;
  }

  boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default:
    case 82:
    case 4:
    }
    do
    {
      return false;
      onKeyUpPanel(0, paramKeyEvent);
      return true;
      PanelFeatureState localPanelFeatureState = getPanelState(0, false);
      if ((localPanelFeatureState != null) && (localPanelFeatureState.isOpen))
      {
        closePanel(localPanelFeatureState, true);
        return true;
      }
    }
    while (!onBackPressed());
    return true;
  }

  public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    Window.Callback localCallback = getWindowCallback();
    if ((localCallback != null) && (!isDestroyed()))
    {
      PanelFeatureState localPanelFeatureState = findMenuPanel(paramMenuBuilder.getRootMenu());
      if (localPanelFeatureState != null)
        return localCallback.onMenuItemSelected(localPanelFeatureState.featureId, paramMenuItem);
    }
    return false;
  }

  public void onMenuModeChange(MenuBuilder paramMenuBuilder)
  {
    reopenMenu(paramMenuBuilder, true);
  }

  boolean onMenuOpened(int paramInt, Menu paramMenu)
  {
    if (paramInt == 8)
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null)
        localActionBar.dispatchMenuVisibilityChanged(true);
      return true;
    }
    return false;
  }

  boolean onPanelClosed(int paramInt, Menu paramMenu)
  {
    if (paramInt == 8)
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null)
        localActionBar.dispatchMenuVisibilityChanged(false);
      return true;
    }
    if (paramInt == 0)
    {
      PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
      if (localPanelFeatureState.isOpen)
        closePanel(localPanelFeatureState, false);
    }
    return false;
  }

  public void onPostCreate(Bundle paramBundle)
  {
    ensureSubDecor();
  }

  public void onPostResume()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
      localActionBar.setShowHideAnimationEnabled(true);
  }

  public void onStop()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
      localActionBar.setShowHideAnimationEnabled(false);
  }

  void onSubDecorInstalled(ViewGroup paramViewGroup)
  {
  }

  void onTitleChanged(CharSequence paramCharSequence)
  {
    if (this.mDecorContentParent != null)
      this.mDecorContentParent.setWindowTitle(paramCharSequence);
    do
    {
      return;
      if (peekSupportActionBar() != null)
      {
        peekSupportActionBar().setWindowTitle(paramCharSequence);
        return;
      }
    }
    while (this.mTitleView == null);
    this.mTitleView.setText(paramCharSequence);
  }

  public boolean requestWindowFeature(int paramInt)
  {
    switch (paramInt)
    {
    case 3:
    case 4:
    case 6:
    case 7:
    default:
      return this.mWindow.requestFeature(paramInt);
    case 8:
      throwFeatureRequestIfSubDecorInstalled();
      this.mHasActionBar = true;
      return true;
    case 9:
      throwFeatureRequestIfSubDecorInstalled();
      this.mOverlayActionBar = true;
      return true;
    case 10:
      throwFeatureRequestIfSubDecorInstalled();
      this.mOverlayActionMode = true;
      return true;
    case 2:
      throwFeatureRequestIfSubDecorInstalled();
      this.mFeatureProgress = true;
      return true;
    case 5:
      throwFeatureRequestIfSubDecorInstalled();
      this.mFeatureIndeterminateProgress = true;
      return true;
    case 1:
    }
    throwFeatureRequestIfSubDecorInstalled();
    this.mWindowNoTitle = true;
    return true;
  }

  public void setContentView(int paramInt)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    LayoutInflater.from(this.mContext).inflate(paramInt, localViewGroup);
    this.mOriginalWindowCallback.onContentChanged();
  }

  public void setContentView(View paramView)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView);
    this.mOriginalWindowCallback.onContentChanged();
  }

  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView, paramLayoutParams);
    this.mOriginalWindowCallback.onContentChanged();
  }

  public void setSupportActionBar(Toolbar paramToolbar)
  {
    if (!(this.mOriginalWindowCallback instanceof Activity))
      return;
    if ((getSupportActionBar() instanceof WindowDecorActionBar))
      throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
    ToolbarActionBar localToolbarActionBar = new ToolbarActionBar(paramToolbar, ((Activity)this.mContext).getTitle(), this.mWindow);
    setSupportActionBar(localToolbarActionBar);
    this.mWindow.setCallback(localToolbarActionBar.getWrappedWindowCallback());
    localToolbarActionBar.invalidateOptionsMenu();
  }

  public ActionMode startSupportActionMode(ActionMode.Callback paramCallback)
  {
    if (paramCallback == null)
      throw new IllegalArgumentException("ActionMode callback can not be null.");
    if (this.mActionMode != null)
      this.mActionMode.finish();
    ActionModeCallbackWrapperV7 localActionModeCallbackWrapperV7 = new ActionModeCallbackWrapperV7(paramCallback);
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
    {
      this.mActionMode = localActionBar.startActionMode(localActionModeCallbackWrapperV7);
      if ((this.mActionMode != null) && (this.mAppCompatCallback != null))
        this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode);
    }
    if (this.mActionMode == null)
      this.mActionMode = startSupportActionModeFromWindow(localActionModeCallbackWrapperV7);
    return this.mActionMode;
  }

  ActionMode startSupportActionModeFromWindow(ActionMode.Callback paramCallback)
  {
    if (this.mActionMode != null)
      this.mActionMode.finish();
    ActionModeCallbackWrapperV7 localActionModeCallbackWrapperV7 = new ActionModeCallbackWrapperV7(paramCallback);
    AppCompatCallback localAppCompatCallback = this.mAppCompatCallback;
    Object localObject1 = null;
    if (localAppCompatCallback != null)
    {
      boolean bool2 = isDestroyed();
      localObject1 = null;
      if (bool2);
    }
    try
    {
      ActionMode localActionMode = this.mAppCompatCallback.onWindowStartingSupportActionMode(localActionModeCallbackWrapperV7);
      localObject1 = localActionMode;
      if (localObject1 != null)
        this.mActionMode = localObject1;
      while (true)
      {
        if ((this.mActionMode != null) && (this.mAppCompatCallback != null))
          this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode);
        return this.mActionMode;
        Object localObject2;
        label215: Context localContext;
        label327: ActionBarContextView localActionBarContextView;
        if (this.mActionModeView == null)
        {
          if (!this.mIsFloating)
            break label492;
          TypedValue localTypedValue = new TypedValue();
          Resources.Theme localTheme1 = this.mContext.getTheme();
          localTheme1.resolveAttribute(R.attr.actionBarTheme, localTypedValue, true);
          if (localTypedValue.resourceId != 0)
          {
            Resources.Theme localTheme2 = this.mContext.getResources().newTheme();
            localTheme2.setTo(localTheme1);
            localTheme2.applyStyle(localTypedValue.resourceId, true);
            localObject2 = new ContextThemeWrapper(this.mContext, 0);
            ((Context)localObject2).getTheme().setTo(localTheme2);
            this.mActionModeView = new ActionBarContextView((Context)localObject2);
            this.mActionModePopup = new PopupWindow((Context)localObject2, null, R.attr.actionModePopupWindowStyle);
            this.mActionModePopup.setContentView(this.mActionModeView);
            this.mActionModePopup.setWidth(-1);
            ((Context)localObject2).getTheme().resolveAttribute(R.attr.actionBarSize, localTypedValue, true);
            int i = TypedValue.complexToDimensionPixelSize(localTypedValue.data, ((Context)localObject2).getResources().getDisplayMetrics());
            this.mActionModeView.setContentHeight(i);
            this.mActionModePopup.setHeight(-2);
            this.mShowActionModePopup = new Runnable()
            {
              public void run()
              {
                AppCompatDelegateImplV7.this.mActionModePopup.showAtLocation(AppCompatDelegateImplV7.this.mActionModeView, 55, 0, 0);
              }
            };
          }
        }
        else
        {
          if (this.mActionModeView == null)
            break label537;
          this.mActionModeView.killMode();
          localContext = this.mActionModeView.getContext();
          localActionBarContextView = this.mActionModeView;
          if (this.mActionModePopup != null)
            break label539;
        }
        label537: label539: for (boolean bool1 = true; ; bool1 = false)
        {
          StandaloneActionMode localStandaloneActionMode = new StandaloneActionMode(localContext, localActionBarContextView, localActionModeCallbackWrapperV7, bool1);
          if (!paramCallback.onCreateActionMode(localStandaloneActionMode, localStandaloneActionMode.getMenu()))
            break label545;
          localStandaloneActionMode.invalidate();
          this.mActionModeView.initForMode(localStandaloneActionMode);
          this.mActionModeView.setVisibility(0);
          this.mActionMode = localStandaloneActionMode;
          if (this.mActionModePopup != null)
            this.mWindow.getDecorView().post(this.mShowActionModePopup);
          this.mActionModeView.sendAccessibilityEvent(32);
          if (this.mActionModeView.getParent() == null)
            break;
          ViewCompat.requestApplyInsets((View)this.mActionModeView.getParent());
          break;
          localObject2 = this.mContext;
          break label215;
          label492: ViewStubCompat localViewStubCompat = (ViewStubCompat)this.mSubDecor.findViewById(R.id.action_mode_bar_stub);
          if (localViewStubCompat == null)
            break label327;
          localViewStubCompat.setLayoutInflater(LayoutInflater.from(getActionBarThemedContext()));
          this.mActionModeView = ((ActionBarContextView)localViewStubCompat.inflate());
          break label327;
          break;
        }
        label545: this.mActionMode = null;
      }
    }
    catch (AbstractMethodError localAbstractMethodError)
    {
      while (true)
        localObject1 = null;
    }
  }

  private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    private ActionMenuPresenterCallback()
    {
    }

    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      AppCompatDelegateImplV7.this.checkCloseActionMenu(paramMenuBuilder);
    }

    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      Window.Callback localCallback = AppCompatDelegateImplV7.this.getWindowCallback();
      if (localCallback != null)
        localCallback.onMenuOpened(8, paramMenuBuilder);
      return true;
    }
  }

  class ActionModeCallbackWrapperV7
    implements ActionMode.Callback
  {
    private ActionMode.Callback mWrapped;

    public ActionModeCallbackWrapperV7(ActionMode.Callback arg2)
    {
      Object localObject;
      this.mWrapped = localObject;
    }

    public boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      return this.mWrapped.onActionItemClicked(paramActionMode, paramMenuItem);
    }

    public boolean onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mWrapped.onCreateActionMode(paramActionMode, paramMenu);
    }

    public void onDestroyActionMode(ActionMode paramActionMode)
    {
      this.mWrapped.onDestroyActionMode(paramActionMode);
      if (AppCompatDelegateImplV7.this.mActionModePopup != null)
      {
        AppCompatDelegateImplV7.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImplV7.this.mShowActionModePopup);
        AppCompatDelegateImplV7.this.mActionModePopup.dismiss();
      }
      while (true)
      {
        if (AppCompatDelegateImplV7.this.mActionModeView != null)
          AppCompatDelegateImplV7.this.mActionModeView.removeAllViews();
        if (AppCompatDelegateImplV7.this.mAppCompatCallback != null)
          AppCompatDelegateImplV7.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImplV7.this.mActionMode);
        AppCompatDelegateImplV7.this.mActionMode = null;
        return;
        if (AppCompatDelegateImplV7.this.mActionModeView != null)
        {
          AppCompatDelegateImplV7.this.mActionModeView.setVisibility(8);
          if (AppCompatDelegateImplV7.this.mActionModeView.getParent() != null)
            ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV7.this.mActionModeView.getParent());
        }
      }
    }

    public boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mWrapped.onPrepareActionMode(paramActionMode, paramMenu);
    }
  }

  private class ListMenuDecorView extends FrameLayout
  {
    public ListMenuDecorView(Context arg2)
    {
      super();
    }

    private boolean isOutOfBounds(int paramInt1, int paramInt2)
    {
      return (paramInt1 < -5) || (paramInt2 < -5) || (paramInt1 > 5 + getWidth()) || (paramInt2 > 5 + getHeight());
    }

    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      return AppCompatDelegateImplV7.this.dispatchKeyEvent(paramKeyEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      if ((paramMotionEvent.getAction() == 0) && (isOutOfBounds((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())))
      {
        AppCompatDelegateImplV7.this.closePanel(0);
        return true;
      }
      return super.onInterceptTouchEvent(paramMotionEvent);
    }

    public void setBackgroundResource(int paramInt)
    {
      setBackgroundDrawable(TintManager.getDrawable(getContext(), paramInt));
    }
  }

  private static final class PanelFeatureState
  {
    int background;
    View createdPanelView;
    ViewGroup decorView;
    int featureId;
    Bundle frozenActionViewState;
    Bundle frozenMenuState;
    int gravity;
    boolean isHandled;
    boolean isOpen;
    boolean isPrepared;
    ListMenuPresenter listMenuPresenter;
    Context listPresenterContext;
    MenuBuilder menu;
    public boolean qwertyMode;
    boolean refreshDecorView;
    boolean refreshMenuContent;
    View shownPanelView;
    boolean wasLastOpen;
    int windowAnimations;
    int x;
    int y;

    PanelFeatureState(int paramInt)
    {
      this.featureId = paramInt;
      this.refreshDecorView = false;
    }

    void applyFrozenState()
    {
      if ((this.menu != null) && (this.frozenMenuState != null))
      {
        this.menu.restorePresenterStates(this.frozenMenuState);
        this.frozenMenuState = null;
      }
    }

    public void clearMenuPresenters()
    {
      if (this.menu != null)
        this.menu.removeMenuPresenter(this.listMenuPresenter);
      this.listMenuPresenter = null;
    }

    MenuView getListMenuView(MenuPresenter.Callback paramCallback)
    {
      if (this.menu == null)
        return null;
      if (this.listMenuPresenter == null)
      {
        this.listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout);
        this.listMenuPresenter.setCallback(paramCallback);
        this.menu.addMenuPresenter(this.listMenuPresenter);
      }
      return this.listMenuPresenter.getMenuView(this.decorView);
    }

    public boolean hasPanelItems()
    {
      boolean bool = true;
      if (this.shownPanelView == null)
        bool = false;
      while ((this.createdPanelView != null) || (this.listMenuPresenter.getAdapter().getCount() > 0))
        return bool;
      return false;
    }

    void onRestoreInstanceState(Parcelable paramParcelable)
    {
      SavedState localSavedState = (SavedState)paramParcelable;
      this.featureId = localSavedState.featureId;
      this.wasLastOpen = localSavedState.isOpen;
      this.frozenMenuState = localSavedState.menuState;
      this.shownPanelView = null;
      this.decorView = null;
    }

    Parcelable onSaveInstanceState()
    {
      SavedState localSavedState = new SavedState(null);
      localSavedState.featureId = this.featureId;
      localSavedState.isOpen = this.isOpen;
      if (this.menu != null)
      {
        localSavedState.menuState = new Bundle();
        this.menu.savePresenterStates(localSavedState.menuState);
      }
      return localSavedState;
    }

    void setMenu(MenuBuilder paramMenuBuilder)
    {
      if (paramMenuBuilder == this.menu);
      do
      {
        return;
        if (this.menu != null)
          this.menu.removeMenuPresenter(this.listMenuPresenter);
        this.menu = paramMenuBuilder;
      }
      while ((paramMenuBuilder == null) || (this.listMenuPresenter == null));
      paramMenuBuilder.addMenuPresenter(this.listMenuPresenter);
    }

    void setStyle(Context paramContext)
    {
      TypedValue localTypedValue = new TypedValue();
      Resources.Theme localTheme = paramContext.getResources().newTheme();
      localTheme.setTo(paramContext.getTheme());
      localTheme.resolveAttribute(R.attr.actionBarPopupTheme, localTypedValue, true);
      if (localTypedValue.resourceId != 0)
        localTheme.applyStyle(localTypedValue.resourceId, true);
      localTheme.resolveAttribute(R.attr.panelMenuListTheme, localTypedValue, true);
      if (localTypedValue.resourceId != 0)
        localTheme.applyStyle(localTypedValue.resourceId, true);
      while (true)
      {
        ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(paramContext, 0);
        localContextThemeWrapper.getTheme().setTo(localTheme);
        this.listPresenterContext = localContextThemeWrapper;
        TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(R.styleable.Theme);
        this.background = localTypedArray.getResourceId(R.styleable.Theme_panelBackground, 0);
        this.windowAnimations = localTypedArray.getResourceId(R.styleable.Theme_android_windowAnimationStyle, 0);
        localTypedArray.recycle();
        return;
        localTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
      }
    }

    private static class SavedState
      implements Parcelable
    {
      public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
      {
        public AppCompatDelegateImplV7.PanelFeatureState.SavedState createFromParcel(Parcel paramAnonymousParcel)
        {
          return AppCompatDelegateImplV7.PanelFeatureState.SavedState.readFromParcel(paramAnonymousParcel);
        }

        public AppCompatDelegateImplV7.PanelFeatureState.SavedState[] newArray(int paramAnonymousInt)
        {
          return new AppCompatDelegateImplV7.PanelFeatureState.SavedState[paramAnonymousInt];
        }
      };
      int featureId;
      boolean isOpen;
      Bundle menuState;

      private static SavedState readFromParcel(Parcel paramParcel)
      {
        int i = 1;
        SavedState localSavedState = new SavedState();
        localSavedState.featureId = paramParcel.readInt();
        if (paramParcel.readInt() == i);
        while (true)
        {
          localSavedState.isOpen = i;
          if (localSavedState.isOpen)
            localSavedState.menuState = paramParcel.readBundle();
          return localSavedState;
          i = 0;
        }
      }

      public int describeContents()
      {
        return 0;
      }

      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        paramParcel.writeInt(this.featureId);
        if (this.isOpen);
        for (int i = 1; ; i = 0)
        {
          paramParcel.writeInt(i);
          if (this.isOpen)
            paramParcel.writeBundle(this.menuState);
          return;
        }
      }
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
      MenuBuilder localMenuBuilder = paramMenuBuilder.getRootMenu();
      if (localMenuBuilder != paramMenuBuilder);
      AppCompatDelegateImplV7.PanelFeatureState localPanelFeatureState;
      for (int i = 1; ; i = 0)
      {
        AppCompatDelegateImplV7 localAppCompatDelegateImplV7 = AppCompatDelegateImplV7.this;
        if (i != 0)
          paramMenuBuilder = localMenuBuilder;
        localPanelFeatureState = localAppCompatDelegateImplV7.findMenuPanel(paramMenuBuilder);
        if (localPanelFeatureState != null)
        {
          if (i == 0)
            break;
          AppCompatDelegateImplV7.this.callOnPanelClosed(localPanelFeatureState.featureId, localPanelFeatureState, localMenuBuilder);
          AppCompatDelegateImplV7.this.closePanel(localPanelFeatureState, true);
        }
        return;
      }
      AppCompatDelegateImplV7.this.closePanel(localPanelFeatureState, paramBoolean);
    }

    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if ((paramMenuBuilder == null) && (AppCompatDelegateImplV7.this.mHasActionBar))
      {
        Window.Callback localCallback = AppCompatDelegateImplV7.this.getWindowCallback();
        if ((localCallback != null) && (!AppCompatDelegateImplV7.this.isDestroyed()))
          localCallback.onMenuOpened(8, paramMenuBuilder);
      }
      return true;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.app.AppCompatDelegateImplV7
 * JD-Core Version:    0.6.2
 */