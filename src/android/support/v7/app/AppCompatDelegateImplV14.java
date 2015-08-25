package android.support.v7.app;

import android.content.Context;
import android.support.v7.internal.view.SupportActionModeWrapper;
import android.support.v7.internal.view.SupportActionModeWrapper.CallbackWrapper;
import android.view.ActionMode.Callback;
import android.view.Window;
import android.view.Window.Callback;

class AppCompatDelegateImplV14 extends AppCompatDelegateImplV11
{
  private boolean mHandleNativeActionModes = true;

  AppCompatDelegateImplV14(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    super(paramContext, paramWindow, paramAppCompatCallback);
  }

  public boolean isHandleNativeActionModesEnabled()
  {
    return this.mHandleNativeActionModes;
  }

  public void setHandleNativeActionModesEnabled(boolean paramBoolean)
  {
    this.mHandleNativeActionModes = paramBoolean;
  }

  Window.Callback wrapWindowCallback(Window.Callback paramCallback)
  {
    return new AppCompatWindowCallbackV14(paramCallback);
  }

  class AppCompatWindowCallbackV14 extends AppCompatDelegateImplBase.AppCompatWindowCallbackBase
  {
    AppCompatWindowCallbackV14(Window.Callback arg2)
    {
      super(localCallback);
    }

    public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback paramCallback)
    {
      if (AppCompatDelegateImplV14.this.mHandleNativeActionModes)
        return startAsSupportActionMode(paramCallback);
      return super.onWindowStartingActionMode(paramCallback);
    }

    final android.view.ActionMode startAsSupportActionMode(ActionMode.Callback paramCallback)
    {
      SupportActionModeWrapper.CallbackWrapper localCallbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImplV14.this.mContext, paramCallback);
      android.support.v7.view.ActionMode localActionMode = AppCompatDelegateImplV14.this.startSupportActionMode(localCallbackWrapper);
      if (localActionMode != null)
      {
        SupportActionModeWrapper localSupportActionModeWrapper = new SupportActionModeWrapper(AppCompatDelegateImplV14.this.mContext, localActionMode);
        localCallbackWrapper.addActionModeWrapper(localSupportActionModeWrapper);
        return localSupportActionModeWrapper;
      }
      return null;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.app.AppCompatDelegateImplV14
 * JD-Core Version:    0.6.2
 */