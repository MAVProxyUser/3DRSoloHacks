package com.nineoldandroids.animation;

import android.view.View;
import com.nineoldandroids.util.Property;
import com.nineoldandroids.view.animation.AnimatorProxy;
import java.util.HashMap;
import java.util.Map;

public final class ObjectAnimator extends ValueAnimator
{
  private static final boolean DBG;
  private static final Map<String, Property> PROXY_PROPERTIES = new HashMap();
  private Property mProperty;
  private String mPropertyName;
  private Object mTarget;

  static
  {
    PROXY_PROPERTIES.put("alpha", PreHoneycombCompat.ALPHA);
    PROXY_PROPERTIES.put("pivotX", PreHoneycombCompat.PIVOT_X);
    PROXY_PROPERTIES.put("pivotY", PreHoneycombCompat.PIVOT_Y);
    PROXY_PROPERTIES.put("translationX", PreHoneycombCompat.TRANSLATION_X);
    PROXY_PROPERTIES.put("translationY", PreHoneycombCompat.TRANSLATION_Y);
    PROXY_PROPERTIES.put("rotation", PreHoneycombCompat.ROTATION);
    PROXY_PROPERTIES.put("rotationX", PreHoneycombCompat.ROTATION_X);
    PROXY_PROPERTIES.put("rotationY", PreHoneycombCompat.ROTATION_Y);
    PROXY_PROPERTIES.put("scaleX", PreHoneycombCompat.SCALE_X);
    PROXY_PROPERTIES.put("scaleY", PreHoneycombCompat.SCALE_Y);
    PROXY_PROPERTIES.put("scrollX", PreHoneycombCompat.SCROLL_X);
    PROXY_PROPERTIES.put("scrollY", PreHoneycombCompat.SCROLL_Y);
    PROXY_PROPERTIES.put("x", PreHoneycombCompat.X);
    PROXY_PROPERTIES.put("y", PreHoneycombCompat.Y);
  }

  public ObjectAnimator()
  {
  }

  private <T> ObjectAnimator(T paramT, Property<T, ?> paramProperty)
  {
    this.mTarget = paramT;
    setProperty(paramProperty);
  }

  private ObjectAnimator(Object paramObject, String paramString)
  {
    this.mTarget = paramObject;
    setPropertyName(paramString);
  }

  public static <T> ObjectAnimator ofFloat(T paramT, Property<T, Float> paramProperty, float[] paramArrayOfFloat)
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator(paramT, paramProperty);
    localObjectAnimator.setFloatValues(paramArrayOfFloat);
    return localObjectAnimator;
  }

  public static ObjectAnimator ofFloat(Object paramObject, String paramString, float[] paramArrayOfFloat)
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator(paramObject, paramString);
    localObjectAnimator.setFloatValues(paramArrayOfFloat);
    return localObjectAnimator;
  }

  public static <T> ObjectAnimator ofInt(T paramT, Property<T, Integer> paramProperty, int[] paramArrayOfInt)
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator(paramT, paramProperty);
    localObjectAnimator.setIntValues(paramArrayOfInt);
    return localObjectAnimator;
  }

  public static ObjectAnimator ofInt(Object paramObject, String paramString, int[] paramArrayOfInt)
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator(paramObject, paramString);
    localObjectAnimator.setIntValues(paramArrayOfInt);
    return localObjectAnimator;
  }

  public static <T, V> ObjectAnimator ofObject(T paramT, Property<T, V> paramProperty, TypeEvaluator<V> paramTypeEvaluator, V[] paramArrayOfV)
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator(paramT, paramProperty);
    localObjectAnimator.setObjectValues(paramArrayOfV);
    localObjectAnimator.setEvaluator(paramTypeEvaluator);
    return localObjectAnimator;
  }

  public static ObjectAnimator ofObject(Object paramObject, String paramString, TypeEvaluator paramTypeEvaluator, Object[] paramArrayOfObject)
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator(paramObject, paramString);
    localObjectAnimator.setObjectValues(paramArrayOfObject);
    localObjectAnimator.setEvaluator(paramTypeEvaluator);
    return localObjectAnimator;
  }

  public static ObjectAnimator ofPropertyValuesHolder(Object paramObject, PropertyValuesHolder[] paramArrayOfPropertyValuesHolder)
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator();
    localObjectAnimator.mTarget = paramObject;
    localObjectAnimator.setValues(paramArrayOfPropertyValuesHolder);
    return localObjectAnimator;
  }

  void animateValue(float paramFloat)
  {
    super.animateValue(paramFloat);
    int i = this.mValues.length;
    for (int j = 0; j < i; j++)
      this.mValues[j].setAnimatedValue(this.mTarget);
  }

  public ObjectAnimator clone()
  {
    return (ObjectAnimator)super.clone();
  }

  public String getPropertyName()
  {
    return this.mPropertyName;
  }

  public Object getTarget()
  {
    return this.mTarget;
  }

  void initAnimation()
  {
    if (!this.mInitialized)
    {
      if ((this.mProperty == null) && (AnimatorProxy.NEEDS_PROXY) && ((this.mTarget instanceof View)) && (PROXY_PROPERTIES.containsKey(this.mPropertyName)))
        setProperty((Property)PROXY_PROPERTIES.get(this.mPropertyName));
      int i = this.mValues.length;
      for (int j = 0; j < i; j++)
        this.mValues[j].setupSetterAndGetter(this.mTarget);
      super.initAnimation();
    }
  }

  public ObjectAnimator setDuration(long paramLong)
  {
    super.setDuration(paramLong);
    return this;
  }

  public void setFloatValues(float[] paramArrayOfFloat)
  {
    if ((this.mValues == null) || (this.mValues.length == 0))
    {
      if (this.mProperty != null)
      {
        PropertyValuesHolder[] arrayOfPropertyValuesHolder2 = new PropertyValuesHolder[1];
        arrayOfPropertyValuesHolder2[0] = PropertyValuesHolder.ofFloat(this.mProperty, paramArrayOfFloat);
        setValues(arrayOfPropertyValuesHolder2);
        return;
      }
      PropertyValuesHolder[] arrayOfPropertyValuesHolder1 = new PropertyValuesHolder[1];
      arrayOfPropertyValuesHolder1[0] = PropertyValuesHolder.ofFloat(this.mPropertyName, paramArrayOfFloat);
      setValues(arrayOfPropertyValuesHolder1);
      return;
    }
    super.setFloatValues(paramArrayOfFloat);
  }

  public void setIntValues(int[] paramArrayOfInt)
  {
    if ((this.mValues == null) || (this.mValues.length == 0))
    {
      if (this.mProperty != null)
      {
        PropertyValuesHolder[] arrayOfPropertyValuesHolder2 = new PropertyValuesHolder[1];
        arrayOfPropertyValuesHolder2[0] = PropertyValuesHolder.ofInt(this.mProperty, paramArrayOfInt);
        setValues(arrayOfPropertyValuesHolder2);
        return;
      }
      PropertyValuesHolder[] arrayOfPropertyValuesHolder1 = new PropertyValuesHolder[1];
      arrayOfPropertyValuesHolder1[0] = PropertyValuesHolder.ofInt(this.mPropertyName, paramArrayOfInt);
      setValues(arrayOfPropertyValuesHolder1);
      return;
    }
    super.setIntValues(paramArrayOfInt);
  }

  public void setObjectValues(Object[] paramArrayOfObject)
  {
    if ((this.mValues == null) || (this.mValues.length == 0))
    {
      if (this.mProperty != null)
      {
        PropertyValuesHolder[] arrayOfPropertyValuesHolder2 = new PropertyValuesHolder[1];
        arrayOfPropertyValuesHolder2[0] = PropertyValuesHolder.ofObject(this.mProperty, (TypeEvaluator)null, paramArrayOfObject);
        setValues(arrayOfPropertyValuesHolder2);
        return;
      }
      PropertyValuesHolder[] arrayOfPropertyValuesHolder1 = new PropertyValuesHolder[1];
      arrayOfPropertyValuesHolder1[0] = PropertyValuesHolder.ofObject(this.mPropertyName, (TypeEvaluator)null, paramArrayOfObject);
      setValues(arrayOfPropertyValuesHolder1);
      return;
    }
    super.setObjectValues(paramArrayOfObject);
  }

  public void setProperty(Property paramProperty)
  {
    if (this.mValues != null)
    {
      PropertyValuesHolder localPropertyValuesHolder = this.mValues[0];
      String str = localPropertyValuesHolder.getPropertyName();
      localPropertyValuesHolder.setProperty(paramProperty);
      this.mValuesMap.remove(str);
      this.mValuesMap.put(this.mPropertyName, localPropertyValuesHolder);
    }
    if (this.mProperty != null)
      this.mPropertyName = paramProperty.getName();
    this.mProperty = paramProperty;
    this.mInitialized = false;
  }

  public void setPropertyName(String paramString)
  {
    if (this.mValues != null)
    {
      PropertyValuesHolder localPropertyValuesHolder = this.mValues[0];
      String str = localPropertyValuesHolder.getPropertyName();
      localPropertyValuesHolder.setPropertyName(paramString);
      this.mValuesMap.remove(str);
      this.mValuesMap.put(paramString, localPropertyValuesHolder);
    }
    this.mPropertyName = paramString;
    this.mInitialized = false;
  }

  public void setTarget(Object paramObject)
  {
    if (this.mTarget != paramObject)
    {
      Object localObject = this.mTarget;
      this.mTarget = paramObject;
      if ((localObject == null) || (paramObject == null) || (localObject.getClass() != paramObject.getClass()));
    }
    else
    {
      return;
    }
    this.mInitialized = false;
  }

  public void setupEndValues()
  {
    initAnimation();
    int i = this.mValues.length;
    for (int j = 0; j < i; j++)
      this.mValues[j].setupEndValue(this.mTarget);
  }

  public void setupStartValues()
  {
    initAnimation();
    int i = this.mValues.length;
    for (int j = 0; j < i; j++)
      this.mValues[j].setupStartValue(this.mTarget);
  }

  public void start()
  {
    super.start();
  }

  public String toString()
  {
    String str = "ObjectAnimator@" + Integer.toHexString(hashCode()) + ", target " + this.mTarget;
    if (this.mValues != null)
      for (int i = 0; i < this.mValues.length; i++)
        str = str + "\n    " + this.mValues[i].toString();
    return str;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.animation.ObjectAnimator
 * JD-Core Version:    0.6.2
 */