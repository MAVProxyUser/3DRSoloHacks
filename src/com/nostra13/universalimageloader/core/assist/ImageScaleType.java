package com.nostra13.universalimageloader.core.assist;

public enum ImageScaleType
{
  static
  {
    IN_SAMPLE_POWER_OF_2 = new ImageScaleType("IN_SAMPLE_POWER_OF_2", 2);
    IN_SAMPLE_INT = new ImageScaleType("IN_SAMPLE_INT", 3);
    EXACTLY = new ImageScaleType("EXACTLY", 4);
    EXACTLY_STRETCHED = new ImageScaleType("EXACTLY_STRETCHED", 5);
    ImageScaleType[] arrayOfImageScaleType = new ImageScaleType[6];
    arrayOfImageScaleType[0] = NONE;
    arrayOfImageScaleType[1] = NONE_SAFE;
    arrayOfImageScaleType[2] = IN_SAMPLE_POWER_OF_2;
    arrayOfImageScaleType[3] = IN_SAMPLE_INT;
    arrayOfImageScaleType[4] = EXACTLY;
    arrayOfImageScaleType[5] = EXACTLY_STRETCHED;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.assist.ImageScaleType
 * JD-Core Version:    0.6.2
 */