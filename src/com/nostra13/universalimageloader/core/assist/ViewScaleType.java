package com.nostra13.universalimageloader.core.assist;

import android.widget.ImageView;

public enum ViewScaleType
{
  static
  {
    CROP = new ViewScaleType("CROP", 1);
    ViewScaleType[] arrayOfViewScaleType = new ViewScaleType[2];
    arrayOfViewScaleType[0] = FIT_INSIDE;
    arrayOfViewScaleType[1] = CROP;
  }

  public static ViewScaleType fromImageView(ImageView paramImageView)
  {
    switch (1.$SwitchMap$android$widget$ImageView$ScaleType[paramImageView.getScaleType().ordinal()])
    {
    default:
      return CROP;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    return FIT_INSIDE;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.assist.ViewScaleType
 * JD-Core Version:    0.6.2
 */