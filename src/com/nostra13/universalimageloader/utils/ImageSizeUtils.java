package com.nostra13.universalimageloader.utils;

import android.opengl.GLES10;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public final class ImageSizeUtils
{
  private static final int DEFAULT_MAX_BITMAP_DIMENSION = 2048;
  private static ImageSize maxBitmapSize = new ImageSize(i, i);

  static
  {
    int[] arrayOfInt = new int[1];
    GLES10.glGetIntegerv(3379, arrayOfInt, 0);
    int i = Math.max(arrayOfInt[0], 2048);
  }

  public static int computeImageSampleSize(ImageSize paramImageSize1, ImageSize paramImageSize2, ViewScaleType paramViewScaleType, boolean paramBoolean)
  {
    int i = paramImageSize1.getWidth();
    int j = paramImageSize1.getHeight();
    int k = paramImageSize2.getWidth();
    int m = paramImageSize2.getHeight();
    int n = 1;
    switch (1.$SwitchMap$com$nostra13$universalimageloader$core$assist$ViewScaleType[paramViewScaleType.ordinal()])
    {
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      if (n < 1)
        n = 1;
      return considerMaxTextureSize(i, j, n, paramBoolean);
      if (paramBoolean)
      {
        int i3 = i / 2;
        int i4 = j / 2;
        while ((i3 / n > k) || (i4 / n > m))
          n *= 2;
      }
      n = Math.max(i / k, j / m);
      continue;
      if (paramBoolean)
      {
        int i1 = i / 2;
        int i2 = j / 2;
        while ((i1 / n > k) && (i2 / n > m))
          n *= 2;
      }
      else
      {
        n = Math.min(i / k, j / m);
      }
    }
  }

  public static float computeImageScale(ImageSize paramImageSize1, ImageSize paramImageSize2, ViewScaleType paramViewScaleType, boolean paramBoolean)
  {
    int i = paramImageSize1.getWidth();
    int j = paramImageSize1.getHeight();
    int k = paramImageSize2.getWidth();
    int m = paramImageSize2.getHeight();
    float f1 = i / k;
    float f2 = j / m;
    int n;
    if (((paramViewScaleType == ViewScaleType.FIT_INSIDE) && (f1 >= f2)) || ((paramViewScaleType == ViewScaleType.CROP) && (f1 < f2)))
      n = k;
    for (int i1 = (int)(j / f1); ; i1 = m)
    {
      float f3 = 1.0F;
      if (((!paramBoolean) && (n < i) && (i1 < j)) || ((paramBoolean) && (n != i) && (i1 != j)))
        f3 = n / i;
      return f3;
      n = (int)(i / f2);
    }
  }

  public static int computeMinImageSampleSize(ImageSize paramImageSize)
  {
    int i = paramImageSize.getWidth();
    int j = paramImageSize.getHeight();
    int k = maxBitmapSize.getWidth();
    int m = maxBitmapSize.getHeight();
    return Math.max((int)Math.ceil(i / k), (int)Math.ceil(j / m));
  }

  private static int considerMaxTextureSize(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    int i = maxBitmapSize.getWidth();
    int j = maxBitmapSize.getHeight();
    while ((paramInt1 / paramInt3 > i) || (paramInt2 / paramInt3 > j))
      if (paramBoolean)
        paramInt3 *= 2;
      else
        paramInt3++;
    return paramInt3;
  }

  public static ImageSize defineTargetSizeForView(ImageAware paramImageAware, ImageSize paramImageSize)
  {
    int i = paramImageAware.getWidth();
    if (i <= 0)
      i = paramImageSize.getWidth();
    int j = paramImageAware.getHeight();
    if (j <= 0)
      j = paramImageSize.getHeight();
    return new ImageSize(i, j);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.utils.ImageSizeUtils
 * JD-Core Version:    0.6.2
 */