package com.nostra13.universalimageloader.core.imageaware;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.utils.L;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class ViewAware
  implements ImageAware
{
  public static final String WARN_CANT_SET_BITMAP = "Can't set a bitmap into view. You should call ImageLoader on UI thread for it.";
  public static final String WARN_CANT_SET_DRAWABLE = "Can't set a drawable into view. You should call ImageLoader on UI thread for it.";
  protected boolean checkActualViewSize;
  protected Reference<View> viewRef;

  public ViewAware(View paramView)
  {
    this(paramView, true);
  }

  public ViewAware(View paramView, boolean paramBoolean)
  {
    if (paramView == null)
      throw new IllegalArgumentException("view must not be null");
    this.viewRef = new WeakReference(paramView);
    this.checkActualViewSize = paramBoolean;
  }

  public int getHeight()
  {
    View localView = (View)this.viewRef.get();
    if (localView != null)
    {
      ViewGroup.LayoutParams localLayoutParams = localView.getLayoutParams();
      boolean bool = this.checkActualViewSize;
      int i = 0;
      if (bool)
      {
        i = 0;
        if (localLayoutParams != null)
        {
          int j = localLayoutParams.height;
          i = 0;
          if (j != -2)
            i = localView.getHeight();
        }
      }
      if ((i <= 0) && (localLayoutParams != null))
        i = localLayoutParams.height;
      return i;
    }
    return 0;
  }

  public int getId()
  {
    View localView = (View)this.viewRef.get();
    if (localView == null)
      return super.hashCode();
    return localView.hashCode();
  }

  public ViewScaleType getScaleType()
  {
    return ViewScaleType.CROP;
  }

  public int getWidth()
  {
    View localView = (View)this.viewRef.get();
    if (localView != null)
    {
      ViewGroup.LayoutParams localLayoutParams = localView.getLayoutParams();
      boolean bool = this.checkActualViewSize;
      int i = 0;
      if (bool)
      {
        i = 0;
        if (localLayoutParams != null)
        {
          int j = localLayoutParams.width;
          i = 0;
          if (j != -2)
            i = localView.getWidth();
        }
      }
      if ((i <= 0) && (localLayoutParams != null))
        i = localLayoutParams.width;
      return i;
    }
    return 0;
  }

  public View getWrappedView()
  {
    return (View)this.viewRef.get();
  }

  public boolean isCollected()
  {
    return this.viewRef.get() == null;
  }

  public boolean setImageBitmap(Bitmap paramBitmap)
  {
    if (Looper.myLooper() == Looper.getMainLooper())
    {
      View localView = (View)this.viewRef.get();
      boolean bool = false;
      if (localView != null)
      {
        setImageBitmapInto(paramBitmap, localView);
        bool = true;
      }
      return bool;
    }
    L.w("Can't set a bitmap into view. You should call ImageLoader on UI thread for it.", new Object[0]);
    return false;
  }

  protected abstract void setImageBitmapInto(Bitmap paramBitmap, View paramView);

  public boolean setImageDrawable(Drawable paramDrawable)
  {
    if (Looper.myLooper() == Looper.getMainLooper())
    {
      View localView = (View)this.viewRef.get();
      boolean bool = false;
      if (localView != null)
      {
        setImageDrawableInto(paramDrawable, localView);
        bool = true;
      }
      return bool;
    }
    L.w("Can't set a drawable into view. You should call ImageLoader on UI thread for it.", new Object[0]);
    return false;
  }

  protected abstract void setImageDrawableInto(Drawable paramDrawable, View paramView);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.imageaware.ViewAware
 * JD-Core Version:    0.6.2
 */