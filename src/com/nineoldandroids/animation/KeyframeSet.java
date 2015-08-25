package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Arrays;

class KeyframeSet
{
  TypeEvaluator mEvaluator;
  Keyframe mFirstKeyframe;
  Interpolator mInterpolator;
  ArrayList<Keyframe> mKeyframes;
  Keyframe mLastKeyframe;
  int mNumKeyframes;

  public KeyframeSet(Keyframe[] paramArrayOfKeyframe)
  {
    this.mNumKeyframes = paramArrayOfKeyframe.length;
    this.mKeyframes = new ArrayList();
    this.mKeyframes.addAll(Arrays.asList(paramArrayOfKeyframe));
    this.mFirstKeyframe = ((Keyframe)this.mKeyframes.get(0));
    this.mLastKeyframe = ((Keyframe)this.mKeyframes.get(-1 + this.mNumKeyframes));
    this.mInterpolator = this.mLastKeyframe.getInterpolator();
  }

  public static KeyframeSet ofFloat(float[] paramArrayOfFloat)
  {
    int i = paramArrayOfFloat.length;
    Keyframe.FloatKeyframe[] arrayOfFloatKeyframe = new Keyframe.FloatKeyframe[Math.max(i, 2)];
    if (i == 1)
    {
      arrayOfFloatKeyframe[0] = ((Keyframe.FloatKeyframe)Keyframe.ofFloat(0.0F));
      arrayOfFloatKeyframe[1] = ((Keyframe.FloatKeyframe)Keyframe.ofFloat(1.0F, paramArrayOfFloat[0]));
    }
    while (true)
    {
      return new FloatKeyframeSet(arrayOfFloatKeyframe);
      arrayOfFloatKeyframe[0] = ((Keyframe.FloatKeyframe)Keyframe.ofFloat(0.0F, paramArrayOfFloat[0]));
      for (int j = 1; j < i; j++)
        arrayOfFloatKeyframe[j] = ((Keyframe.FloatKeyframe)Keyframe.ofFloat(j / (i - 1), paramArrayOfFloat[j]));
    }
  }

  public static KeyframeSet ofInt(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    Keyframe.IntKeyframe[] arrayOfIntKeyframe = new Keyframe.IntKeyframe[Math.max(i, 2)];
    if (i == 1)
    {
      arrayOfIntKeyframe[0] = ((Keyframe.IntKeyframe)Keyframe.ofInt(0.0F));
      arrayOfIntKeyframe[1] = ((Keyframe.IntKeyframe)Keyframe.ofInt(1.0F, paramArrayOfInt[0]));
    }
    while (true)
    {
      return new IntKeyframeSet(arrayOfIntKeyframe);
      arrayOfIntKeyframe[0] = ((Keyframe.IntKeyframe)Keyframe.ofInt(0.0F, paramArrayOfInt[0]));
      for (int j = 1; j < i; j++)
        arrayOfIntKeyframe[j] = ((Keyframe.IntKeyframe)Keyframe.ofInt(j / (i - 1), paramArrayOfInt[j]));
    }
  }

  public static KeyframeSet ofKeyframe(Keyframe[] paramArrayOfKeyframe)
  {
    int i = paramArrayOfKeyframe.length;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    if (n < i)
    {
      if ((paramArrayOfKeyframe[n] instanceof Keyframe.FloatKeyframe))
        j = 1;
      while (true)
      {
        n++;
        break;
        if ((paramArrayOfKeyframe[n] instanceof Keyframe.IntKeyframe))
          k = 1;
        else
          m = 1;
      }
    }
    if ((j != 0) && (k == 0) && (m == 0))
    {
      Keyframe.FloatKeyframe[] arrayOfFloatKeyframe = new Keyframe.FloatKeyframe[i];
      for (int i2 = 0; i2 < i; i2++)
        arrayOfFloatKeyframe[i2] = ((Keyframe.FloatKeyframe)paramArrayOfKeyframe[i2]);
      return new FloatKeyframeSet(arrayOfFloatKeyframe);
    }
    if ((k != 0) && (j == 0) && (m == 0))
    {
      Keyframe.IntKeyframe[] arrayOfIntKeyframe = new Keyframe.IntKeyframe[i];
      for (int i1 = 0; i1 < i; i1++)
        arrayOfIntKeyframe[i1] = ((Keyframe.IntKeyframe)paramArrayOfKeyframe[i1]);
      return new IntKeyframeSet(arrayOfIntKeyframe);
    }
    return new KeyframeSet(paramArrayOfKeyframe);
  }

  public static KeyframeSet ofObject(Object[] paramArrayOfObject)
  {
    int i = paramArrayOfObject.length;
    Keyframe.ObjectKeyframe[] arrayOfObjectKeyframe = new Keyframe.ObjectKeyframe[Math.max(i, 2)];
    if (i == 1)
    {
      arrayOfObjectKeyframe[0] = ((Keyframe.ObjectKeyframe)Keyframe.ofObject(0.0F));
      arrayOfObjectKeyframe[1] = ((Keyframe.ObjectKeyframe)Keyframe.ofObject(1.0F, paramArrayOfObject[0]));
    }
    while (true)
    {
      return new KeyframeSet(arrayOfObjectKeyframe);
      arrayOfObjectKeyframe[0] = ((Keyframe.ObjectKeyframe)Keyframe.ofObject(0.0F, paramArrayOfObject[0]));
      for (int j = 1; j < i; j++)
        arrayOfObjectKeyframe[j] = ((Keyframe.ObjectKeyframe)Keyframe.ofObject(j / (i - 1), paramArrayOfObject[j]));
    }
  }

  public KeyframeSet clone()
  {
    ArrayList localArrayList = this.mKeyframes;
    int i = this.mKeyframes.size();
    Keyframe[] arrayOfKeyframe = new Keyframe[i];
    for (int j = 0; j < i; j++)
      arrayOfKeyframe[j] = ((Keyframe)localArrayList.get(j)).clone();
    return new KeyframeSet(arrayOfKeyframe);
  }

  public Object getValue(float paramFloat)
  {
    if (this.mNumKeyframes == 2)
    {
      if (this.mInterpolator != null)
        paramFloat = this.mInterpolator.getInterpolation(paramFloat);
      return this.mEvaluator.evaluate(paramFloat, this.mFirstKeyframe.getValue(), this.mLastKeyframe.getValue());
    }
    if (paramFloat <= 0.0F)
    {
      Keyframe localKeyframe3 = (Keyframe)this.mKeyframes.get(1);
      Interpolator localInterpolator3 = localKeyframe3.getInterpolator();
      if (localInterpolator3 != null)
        paramFloat = localInterpolator3.getInterpolation(paramFloat);
      float f5 = this.mFirstKeyframe.getFraction();
      float f6 = (paramFloat - f5) / (localKeyframe3.getFraction() - f5);
      return this.mEvaluator.evaluate(f6, this.mFirstKeyframe.getValue(), localKeyframe3.getValue());
    }
    if (paramFloat >= 1.0F)
    {
      Keyframe localKeyframe2 = (Keyframe)this.mKeyframes.get(-2 + this.mNumKeyframes);
      Interpolator localInterpolator2 = this.mLastKeyframe.getInterpolator();
      if (localInterpolator2 != null)
        paramFloat = localInterpolator2.getInterpolation(paramFloat);
      float f3 = localKeyframe2.getFraction();
      float f4 = (paramFloat - f3) / (this.mLastKeyframe.getFraction() - f3);
      return this.mEvaluator.evaluate(f4, localKeyframe2.getValue(), this.mLastKeyframe.getValue());
    }
    Object localObject = this.mFirstKeyframe;
    for (int i = 1; i < this.mNumKeyframes; i++)
    {
      Keyframe localKeyframe1 = (Keyframe)this.mKeyframes.get(i);
      if (paramFloat < localKeyframe1.getFraction())
      {
        Interpolator localInterpolator1 = localKeyframe1.getInterpolator();
        if (localInterpolator1 != null)
          paramFloat = localInterpolator1.getInterpolation(paramFloat);
        float f1 = ((Keyframe)localObject).getFraction();
        float f2 = (paramFloat - f1) / (localKeyframe1.getFraction() - f1);
        return this.mEvaluator.evaluate(f2, ((Keyframe)localObject).getValue(), localKeyframe1.getValue());
      }
      localObject = localKeyframe1;
    }
    return this.mLastKeyframe.getValue();
  }

  public void setEvaluator(TypeEvaluator paramTypeEvaluator)
  {
    this.mEvaluator = paramTypeEvaluator;
  }

  public String toString()
  {
    String str = " ";
    for (int i = 0; i < this.mNumKeyframes; i++)
      str = str + ((Keyframe)this.mKeyframes.get(i)).getValue() + "  ";
    return str;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.animation.KeyframeSet
 * JD-Core Version:    0.6.2
 */