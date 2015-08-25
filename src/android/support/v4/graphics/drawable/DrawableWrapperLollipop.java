package android.support.v4.graphics.drawable;

import android.content.res.Resources.Theme;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

class DrawableWrapperLollipop extends DrawableWrapperKitKat
{
  DrawableWrapperLollipop(Drawable paramDrawable)
  {
    super(paramDrawable);
  }

  public void applyTheme(Resources.Theme paramTheme)
  {
    this.mDrawable.applyTheme(paramTheme);
  }

  public boolean canApplyTheme()
  {
    return this.mDrawable.canApplyTheme();
  }

  public Rect getDirtyBounds()
  {
    return this.mDrawable.getDirtyBounds();
  }

  public void getOutline(Outline paramOutline)
  {
    this.mDrawable.getOutline(paramOutline);
  }

  public void setHotspot(float paramFloat1, float paramFloat2)
  {
    this.mDrawable.setHotspot(paramFloat1, paramFloat2);
  }

  public void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mDrawable.setHotspotBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.graphics.drawable.DrawableWrapperLollipop
 * JD-Core Version:    0.6.2
 */