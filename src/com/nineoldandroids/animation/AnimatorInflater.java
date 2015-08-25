package com.nineoldandroids.animation;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflater
{
  private static final int[] Animator = { 16843073, 16843160, 16843198, 16843199, 16843200, 16843486, 16843487, 16843488 };
  private static final int[] AnimatorSet = { 16843490 };
  private static final int AnimatorSet_ordering = 0;
  private static final int Animator_duration = 1;
  private static final int Animator_interpolator = 0;
  private static final int Animator_repeatCount = 3;
  private static final int Animator_repeatMode = 4;
  private static final int Animator_startOffset = 2;
  private static final int Animator_valueFrom = 5;
  private static final int Animator_valueTo = 6;
  private static final int Animator_valueType = 7;
  private static final int[] PropertyAnimator = { 16843489 };
  private static final int PropertyAnimator_propertyName;
  private static final int TOGETHER;
  private static final int VALUE_TYPE_FLOAT;

  private static Animator createAnimatorFromXml(Context paramContext, XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, IOException
  {
    return createAnimatorFromXml(paramContext, paramXmlPullParser, Xml.asAttributeSet(paramXmlPullParser), null, 0);
  }

  private static Animator createAnimatorFromXml(Context paramContext, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, AnimatorSet paramAnimatorSet, int paramInt)
    throws XmlPullParserException, IOException
  {
    Object localObject = null;
    ArrayList localArrayList = null;
    int i = paramXmlPullParser.getDepth();
    int j;
    do
    {
      j = paramXmlPullParser.next();
      if (((j == 3) && (paramXmlPullParser.getDepth() <= i)) || (j == 1))
        break;
    }
    while (j != 2);
    String str = paramXmlPullParser.getName();
    if (str.equals("objectAnimator"))
      localObject = loadObjectAnimator(paramContext, paramAttributeSet);
    label76: TypedArray localTypedArray;
    TypedValue localTypedValue;
    while (true)
      if (paramAnimatorSet != null)
      {
        if (localArrayList == null)
          localArrayList = new ArrayList();
        localArrayList.add(localObject);
        break;
        if (str.equals("animator"))
        {
          localObject = loadAnimator(paramContext, paramAttributeSet, null);
        }
        else
        {
          if (!str.equals("set"))
            break label218;
          localObject = new AnimatorSet();
          localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, AnimatorSet);
          localTypedValue = new TypedValue();
          localTypedArray.getValue(0, localTypedValue);
          if (localTypedValue.type != 16)
            break label212;
        }
      }
    label212: for (int n = localTypedValue.data; ; n = 0)
    {
      createAnimatorFromXml(paramContext, paramXmlPullParser, paramAttributeSet, (AnimatorSet)localObject, n);
      localTypedArray.recycle();
      break label76;
      break;
    }
    label218: throw new RuntimeException("Unknown animator name: " + paramXmlPullParser.getName());
    Animator[] arrayOfAnimator;
    if ((paramAnimatorSet != null) && (localArrayList != null))
    {
      arrayOfAnimator = new Animator[localArrayList.size()];
      int k = 0;
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        Animator localAnimator = (Animator)localIterator.next();
        int m = k + 1;
        arrayOfAnimator[k] = localAnimator;
        k = m;
      }
      if (paramInt == 0)
        paramAnimatorSet.playTogether(arrayOfAnimator);
    }
    else
    {
      return localObject;
    }
    paramAnimatorSet.playSequentially(arrayOfAnimator);
    return localObject;
  }

  // ERROR //
  public static Animator loadAnimator(Context paramContext, int paramInt)
    throws Resources.NotFoundException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: invokevirtual 180	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   6: iload_1
    //   7: invokevirtual 186	android/content/res/Resources:getAnimation	(I)Landroid/content/res/XmlResourceParser;
    //   10: astore_2
    //   11: aload_0
    //   12: aload_2
    //   13: invokestatic 188	com/nineoldandroids/animation/AnimatorInflater:createAnimatorFromXml	(Landroid/content/Context;Lorg/xmlpull/v1/XmlPullParser;)Lcom/nineoldandroids/animation/Animator;
    //   16: astore 10
    //   18: aload_2
    //   19: ifnull +9 -> 28
    //   22: aload_2
    //   23: invokeinterface 193 1 0
    //   28: aload 10
    //   30: areturn
    //   31: astore 7
    //   33: new 176	android/content/res/Resources$NotFoundException
    //   36: dup
    //   37: new 135	java/lang/StringBuilder
    //   40: dup
    //   41: invokespecial 136	java/lang/StringBuilder:<init>	()V
    //   44: ldc 195
    //   46: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: iload_1
    //   50: invokestatic 201	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   53: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: invokevirtual 145	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   59: invokespecial 202	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   62: astore 8
    //   64: aload 8
    //   66: aload 7
    //   68: invokevirtual 206	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   71: pop
    //   72: aload 8
    //   74: athrow
    //   75: astore 6
    //   77: aload_2
    //   78: ifnull +9 -> 87
    //   81: aload_2
    //   82: invokeinterface 193 1 0
    //   87: aload 6
    //   89: athrow
    //   90: astore_3
    //   91: new 176	android/content/res/Resources$NotFoundException
    //   94: dup
    //   95: new 135	java/lang/StringBuilder
    //   98: dup
    //   99: invokespecial 136	java/lang/StringBuilder:<init>	()V
    //   102: ldc 195
    //   104: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: iload_1
    //   108: invokestatic 201	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   111: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: invokevirtual 145	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   117: invokespecial 202	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   120: astore 4
    //   122: aload 4
    //   124: aload_3
    //   125: invokevirtual 206	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   128: pop
    //   129: aload 4
    //   131: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   2	18	31	org/xmlpull/v1/XmlPullParserException
    //   2	18	75	finally
    //   33	75	75	finally
    //   91	132	75	finally
    //   2	18	90	java/io/IOException
  }

  private static ValueAnimator loadAnimator(Context paramContext, AttributeSet paramAttributeSet, ValueAnimator paramValueAnimator)
    throws Resources.NotFoundException
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, Animator);
    long l1 = localTypedArray.getInt(1, 0);
    long l2 = localTypedArray.getInt(2, 0);
    int i = localTypedArray.getInt(7, 0);
    if (paramValueAnimator == null)
      paramValueAnimator = new ValueAnimator();
    int j;
    int k;
    label71: int m;
    label83: int n;
    label99: int i1;
    label111: float f2;
    label191: float f3;
    if (i == 0)
    {
      j = 1;
      TypedValue localTypedValue1 = localTypedArray.peekValue(5);
      if (localTypedValue1 == null)
        break label328;
      k = 1;
      if (k == 0)
        break label334;
      m = localTypedValue1.type;
      TypedValue localTypedValue2 = localTypedArray.peekValue(6);
      if (localTypedValue2 == null)
        break label340;
      n = 1;
      if (n == 0)
        break label346;
      i1 = localTypedValue2.type;
      if (((k != 0) && (m >= 28) && (m <= 31)) || ((n != 0) && (i1 >= 28) && (i1 <= 31)))
      {
        j = 0;
        ArgbEvaluator localArgbEvaluator = new ArgbEvaluator();
        paramValueAnimator.setEvaluator(localArgbEvaluator);
      }
      if (j == 0)
        break label440;
      if (k == 0)
        break label394;
      if (m != 5)
        break label352;
      f2 = localTypedArray.getDimension(5, 0.0F);
      if (n == 0)
        break label375;
      if (i1 != 5)
        break label363;
      f3 = localTypedArray.getDimension(6, 0.0F);
      label211: float[] arrayOfFloat3 = { f2, f3 };
      paramValueAnimator.setFloatValues(arrayOfFloat3);
    }
    label328: label334: label340: label346: label352: label363: label375: 
    do
      while (true)
      {
        paramValueAnimator.setDuration(l1);
        paramValueAnimator.setStartDelay(l2);
        if (localTypedArray.hasValue(3))
        {
          int i5 = localTypedArray.getInt(3, 0);
          paramValueAnimator.setRepeatCount(i5);
        }
        if (localTypedArray.hasValue(4))
        {
          int i4 = localTypedArray.getInt(4, 1);
          paramValueAnimator.setRepeatMode(i4);
        }
        int i3 = localTypedArray.getResourceId(0, 0);
        if (i3 > 0)
        {
          Interpolator localInterpolator = AnimationUtils.loadInterpolator(paramContext, i3);
          paramValueAnimator.setInterpolator(localInterpolator);
        }
        localTypedArray.recycle();
        return paramValueAnimator;
        j = 0;
        break;
        k = 0;
        break label71;
        m = 0;
        break label83;
        n = 0;
        break label99;
        i1 = 0;
        break label111;
        f2 = localTypedArray.getFloat(5, 0.0F);
        break label191;
        f3 = localTypedArray.getFloat(6, 0.0F);
        break label211;
        float[] arrayOfFloat2 = { f2 };
        paramValueAnimator.setFloatValues(arrayOfFloat2);
        continue;
        if (i1 == 5);
        for (float f1 = localTypedArray.getDimension(6, 0.0F); ; f1 = localTypedArray.getFloat(6, 0.0F))
        {
          float[] arrayOfFloat1 = { f1 };
          paramValueAnimator.setFloatValues(arrayOfFloat1);
          break;
        }
        if (k == 0)
          break label598;
        int i6;
        int i7;
        if (m == 5)
        {
          i6 = (int)localTypedArray.getDimension(5, 0.0F);
          if (n == 0)
            break label579;
          if (i1 != 5)
            break label541;
          i7 = (int)localTypedArray.getDimension(6, 0.0F);
        }
        while (true)
        {
          int[] arrayOfInt3 = { i6, i7 };
          paramValueAnimator.setIntValues(arrayOfInt3);
          break;
          if ((m >= 28) && (m <= 31))
          {
            i6 = localTypedArray.getColor(5, 0);
            break label460;
          }
          i6 = localTypedArray.getInt(5, 0);
          break label460;
          if ((i1 >= 28) && (i1 <= 31))
            i7 = localTypedArray.getColor(6, 0);
          else
            i7 = localTypedArray.getInt(6, 0);
        }
        int[] arrayOfInt2 = { i6 };
        paramValueAnimator.setIntValues(arrayOfInt2);
      }
    while (n == 0);
    label394: label440: label460: label598: int i2;
    label541: label579: if (i1 == 5)
      i2 = (int)localTypedArray.getDimension(6, 0.0F);
    while (true)
    {
      int[] arrayOfInt1 = { i2 };
      paramValueAnimator.setIntValues(arrayOfInt1);
      break;
      if ((i1 >= 28) && (i1 <= 31))
        i2 = localTypedArray.getColor(6, 0);
      else
        i2 = localTypedArray.getInt(6, 0);
    }
  }

  private static ObjectAnimator loadObjectAnimator(Context paramContext, AttributeSet paramAttributeSet)
    throws Resources.NotFoundException
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator();
    loadAnimator(paramContext, paramAttributeSet, localObjectAnimator);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, PropertyAnimator);
    localObjectAnimator.setPropertyName(localTypedArray.getString(0));
    localTypedArray.recycle();
    return localObjectAnimator;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.animation.AnimatorInflater
 * JD-Core Version:    0.6.2
 */