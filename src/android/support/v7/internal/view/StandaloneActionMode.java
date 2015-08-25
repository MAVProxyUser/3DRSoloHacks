package android.support.v7.internal.view;

import android.content.Context;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.internal.view.menu.SubMenuBuilder;
import android.support.v7.internal.widget.ActionBarContextView;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

public class StandaloneActionMode extends ActionMode
  implements MenuBuilder.Callback
{
  private ActionMode.Callback mCallback;
  private Context mContext;
  private ActionBarContextView mContextView;
  private WeakReference<View> mCustomView;
  private boolean mFinished;
  private boolean mFocusable;
  private MenuBuilder mMenu;

  public StandaloneActionMode(Context paramContext, ActionBarContextView paramActionBarContextView, ActionMode.Callback paramCallback, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mContextView = paramActionBarContextView;
    this.mCallback = paramCallback;
    this.mMenu = new MenuBuilder(paramActionBarContextView.getContext()).setDefaultShowAsAction(1);
    this.mMenu.setCallback(this);
    this.mFocusable = paramBoolean;
  }

  public void finish()
  {
    if (this.mFinished)
      return;
    this.mFinished = true;
    this.mContextView.sendAccessibilityEvent(32);
    this.mCallback.onDestroyActionMode(this);
  }

  public View getCustomView()
  {
    if (this.mCustomView != null)
      return (View)this.mCustomView.get();
    return null;
  }

  public Menu getMenu()
  {
    return this.mMenu;
  }

  public MenuInflater getMenuInflater()
  {
    return new MenuInflater(this.mContextView.getContext());
  }

  public CharSequence getSubtitle()
  {
    return this.mContextView.getSubtitle();
  }

  public CharSequence getTitle()
  {
    return this.mContextView.getTitle();
  }

  public void invalidate()
  {
    this.mCallback.onPrepareActionMode(this, this.mMenu);
  }

  public boolean isTitleOptional()
  {
    return this.mContextView.isTitleOptional();
  }

  public boolean isUiFocusable()
  {
    return this.mFocusable;
  }

  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
  }

  public void onCloseSubMenu(SubMenuBuilder paramSubMenuBuilder)
  {
  }

  public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    return this.mCallback.onActionItemClicked(this, paramMenuItem);
  }

  public void onMenuModeChange(MenuBuilder paramMenuBuilder)
  {
    invalidate();
    this.mContextView.showOverflowMenu();
  }

  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    if (!paramSubMenuBuilder.hasVisibleItems())
      return true;
    new MenuPopupHelper(this.mContextView.getContext(), paramSubMenuBuilder).show();
    return true;
  }

  public void setCustomView(View paramView)
  {
    this.mContextView.setCustomView(paramView);
    if (paramView != null);
    for (WeakReference localWeakReference = new WeakReference(paramView); ; localWeakReference = null)
    {
      this.mCustomView = localWeakReference;
      return;
    }
  }

  public void setSubtitle(int paramInt)
  {
    setSubtitle(this.mContext.getString(paramInt));
  }

  public void setSubtitle(CharSequence paramCharSequence)
  {
    this.mContextView.setSubtitle(paramCharSequence);
  }

  public void setTitle(int paramInt)
  {
    setTitle(this.mContext.getString(paramInt));
  }

  public void setTitle(CharSequence paramCharSequence)
  {
    this.mContextView.setTitle(paramCharSequence);
  }

  public void setTitleOptionalHint(boolean paramBoolean)
  {
    super.setTitleOptionalHint(paramBoolean);
    this.mContextView.setTitleOptional(paramBoolean);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.StandaloneActionMode
 * JD-Core Version:    0.6.2
 */