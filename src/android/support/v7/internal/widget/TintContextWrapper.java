package android.support.v7.internal.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;

public class TintContextWrapper extends ContextWrapper
{
  private Resources mResources;

  private TintContextWrapper(Context paramContext)
  {
    super(paramContext);
  }

  public static Context wrap(Context paramContext)
  {
    if (!(paramContext instanceof TintContextWrapper))
      paramContext = new TintContextWrapper(paramContext);
    return paramContext;
  }

  public Resources getResources()
  {
    if (this.mResources == null)
      this.mResources = new TintContextWrapper.TintResources(super.getResources(), TintManager.get(this));
    return this.mResources;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.TintContextWrapper
 * JD-Core Version:    0.6.2
 */