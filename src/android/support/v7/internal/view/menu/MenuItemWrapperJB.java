package android.support.v7.internal.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

@TargetApi(16)
class MenuItemWrapperJB extends MenuItemWrapperICS
{
  MenuItemWrapperJB(Context paramContext, SupportMenuItem paramSupportMenuItem)
  {
    super(paramContext, paramSupportMenuItem);
  }

  MenuItemWrapperICS.ActionProviderWrapper createActionProviderWrapper(ActionProvider paramActionProvider)
  {
    return new ActionProviderWrapperJB(this.mContext, paramActionProvider);
  }

  class ActionProviderWrapperJB extends MenuItemWrapperICS.ActionProviderWrapper
    implements android.view.ActionProvider.VisibilityListener
  {
    android.support.v4.view.ActionProvider.VisibilityListener mListener;

    public ActionProviderWrapperJB(Context paramActionProvider, ActionProvider arg3)
    {
      super(paramActionProvider, localActionProvider);
    }

    public boolean isVisible()
    {
      return this.mInner.isVisible();
    }

    public void onActionProviderVisibilityChanged(boolean paramBoolean)
    {
      if (this.mListener != null)
        this.mListener.onActionProviderVisibilityChanged(paramBoolean);
    }

    public View onCreateActionView(MenuItem paramMenuItem)
    {
      return this.mInner.onCreateActionView(paramMenuItem);
    }

    public boolean overridesItemVisibility()
    {
      return this.mInner.overridesItemVisibility();
    }

    public void refreshVisibility()
    {
      this.mInner.refreshVisibility();
    }

    public void setVisibilityListener(android.support.v4.view.ActionProvider.VisibilityListener paramVisibilityListener)
    {
      this.mListener = paramVisibilityListener;
      ActionProvider localActionProvider = this.mInner;
      if (paramVisibilityListener != null);
      while (true)
      {
        localActionProvider.setVisibilityListener(this);
        return;
        this = null;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.MenuItemWrapperJB
 * JD-Core Version:    0.6.2
 */