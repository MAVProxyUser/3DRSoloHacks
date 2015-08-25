package com.nineoldandroids.animation;

public class ArgbEvaluator
  implements TypeEvaluator
{
  public Object evaluate(float paramFloat, Object paramObject1, Object paramObject2)
  {
    int i = ((Integer)paramObject1).intValue();
    int j = i >> 24;
    int k = 0xFF & i >> 16;
    int m = 0xFF & i >> 8;
    int n = i & 0xFF;
    int i1 = ((Integer)paramObject2).intValue();
    int i2 = i1 >> 24;
    int i3 = 0xFF & i1 >> 16;
    int i4 = 0xFF & i1 >> 8;
    int i5 = i1 & 0xFF;
    return Integer.valueOf(j + (int)(paramFloat * (i2 - j)) << 24 | k + (int)(paramFloat * (i3 - k)) << 16 | m + (int)(paramFloat * (i4 - m)) << 8 | n + (int)(paramFloat * (i5 - n)));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.animation.ArgbEvaluator
 * JD-Core Version:    0.6.2
 */