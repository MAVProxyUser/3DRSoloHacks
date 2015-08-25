package com.o3dr.services.android.lib.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

public class SpannableUtils
{
  private static CharSequence apply(CharSequence[] paramArrayOfCharSequence, Object[] paramArrayOfObject)
  {
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    openTags(localSpannableStringBuilder, paramArrayOfObject);
    int i = paramArrayOfCharSequence.length;
    for (int j = 0; j < i; j++)
      localSpannableStringBuilder.append(paramArrayOfCharSequence[j]);
    closeTags(localSpannableStringBuilder, paramArrayOfObject);
    return localSpannableStringBuilder;
  }

  public static CharSequence bold(CharSequence[] paramArrayOfCharSequence)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = new StyleSpan(1);
    return apply(paramArrayOfCharSequence, arrayOfObject);
  }

  private static void closeTags(Spannable paramSpannable, Object[] paramArrayOfObject)
  {
    int i = paramSpannable.length();
    int j = paramArrayOfObject.length;
    int k = 0;
    if (k < j)
    {
      Object localObject = paramArrayOfObject[k];
      if (i > 0)
        paramSpannable.setSpan(localObject, 0, i, 33);
      while (true)
      {
        k++;
        break;
        paramSpannable.removeSpan(localObject);
      }
    }
  }

  public static CharSequence color(int paramInt, CharSequence[] paramArrayOfCharSequence)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = new ForegroundColorSpan(paramInt);
    return apply(paramArrayOfCharSequence, arrayOfObject);
  }

  public static CharSequence italic(CharSequence[] paramArrayOfCharSequence)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = new StyleSpan(2);
    return apply(paramArrayOfCharSequence, arrayOfObject);
  }

  public static CharSequence normal(CharSequence[] paramArrayOfCharSequence)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = new StyleSpan(0);
    return apply(paramArrayOfCharSequence, arrayOfObject);
  }

  private static void openTags(Spannable paramSpannable, Object[] paramArrayOfObject)
  {
    int i = paramArrayOfObject.length;
    for (int j = 0; j < i; j++)
      paramSpannable.setSpan(paramArrayOfObject[j], 0, 0, 17);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.util.SpannableUtils
 * JD-Core Version:    0.6.2
 */