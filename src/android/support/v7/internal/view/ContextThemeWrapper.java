package android.support.v7.internal.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.support.v7.appcompat.R.style;
import android.view.LayoutInflater;

public class ContextThemeWrapper extends ContextWrapper
{
  private LayoutInflater mInflater;
  private Resources.Theme mTheme;
  private int mThemeResource;

  public ContextThemeWrapper(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mThemeResource = paramInt;
  }

  private void initializeTheme()
  {
    if (this.mTheme == null);
    for (boolean bool = true; ; bool = false)
    {
      if (bool)
      {
        this.mTheme = getResources().newTheme();
        Resources.Theme localTheme = getBaseContext().getTheme();
        if (localTheme != null)
          this.mTheme.setTo(localTheme);
      }
      onApplyThemeResource(this.mTheme, this.mThemeResource, bool);
      return;
    }
  }

  public Object getSystemService(String paramString)
  {
    if ("layout_inflater".equals(paramString))
    {
      if (this.mInflater == null)
        this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
      return this.mInflater;
    }
    return getBaseContext().getSystemService(paramString);
  }

  public Resources.Theme getTheme()
  {
    if (this.mTheme != null)
      return this.mTheme;
    if (this.mThemeResource == 0)
      this.mThemeResource = R.style.Theme_AppCompat_Light;
    initializeTheme();
    return this.mTheme;
  }

  public int getThemeResId()
  {
    return this.mThemeResource;
  }

  protected void onApplyThemeResource(Resources.Theme paramTheme, int paramInt, boolean paramBoolean)
  {
    paramTheme.applyStyle(paramInt, true);
  }

  public void setTheme(int paramInt)
  {
    this.mThemeResource = paramInt;
    initializeTheme();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.ContextThemeWrapper
 * JD-Core Version:    0.6.2
 */