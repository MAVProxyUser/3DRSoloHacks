package com.nostra13.universalimageloader.core.listener;

import android.graphics.Bitmap;
import android.view.View;
import com.nostra13.universalimageloader.core.assist.FailReason;

public abstract interface ImageLoadingListener
{
  public abstract void onLoadingCancelled(String paramString, View paramView);

  public abstract void onLoadingComplete(String paramString, View paramView, Bitmap paramBitmap);

  public abstract void onLoadingFailed(String paramString, View paramView, FailReason paramFailReason);

  public abstract void onLoadingStarted(String paramString, View paramView);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.listener.ImageLoadingListener
 * JD-Core Version:    0.6.2
 */