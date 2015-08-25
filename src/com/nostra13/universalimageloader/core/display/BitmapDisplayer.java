package com.nostra13.universalimageloader.core.display;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public abstract interface BitmapDisplayer
{
  public abstract void display(Bitmap paramBitmap, ImageAware paramImageAware, LoadedFrom paramLoadedFrom);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.display.BitmapDisplayer
 * JD-Core Version:    0.6.2
 */