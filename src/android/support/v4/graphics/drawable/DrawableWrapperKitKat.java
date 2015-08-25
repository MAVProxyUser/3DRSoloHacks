package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableWrapperKitKat extends DrawableWrapperHoneycomb
{
  DrawableWrapperKitKat(Drawable paramDrawable)
  {
    super(paramDrawable);
  }

  public boolean isAutoMirrored()
  {
    return this.mDrawable.isAutoMirrored();
  }

  public void setAutoMirrored(boolean paramBoolean)
  {
    this.mDrawable.setAutoMirrored(paramBoolean);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.graphics.drawable.DrawableWrapperKitKat
 * JD-Core Version:    0.6.2
 */