package android.support.v7.internal.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import java.lang.reflect.Constructor;
import java.util.Map;

public class AppCompatViewInflater
{
  private static final String LOG_TAG = "AppCompatViewInflater";
  private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
  static final Class<?>[] sConstructorSignature = { Context.class, AttributeSet.class };
  private final Object[] mConstructorArgs = new Object[2];

  private View createView(Context paramContext, String paramString1, String paramString2)
    throws ClassNotFoundException, InflateException
  {
    Constructor localConstructor = (Constructor)sConstructorMap.get(paramString1);
    if (localConstructor == null);
    try
    {
      ClassLoader localClassLoader = paramContext.getClassLoader();
      if (paramString2 != null);
      for (String str = paramString2 + paramString1; ; str = paramString1)
      {
        localConstructor = localClassLoader.loadClass(str).asSubclass(View.class).getConstructor(sConstructorSignature);
        sConstructorMap.put(paramString1, localConstructor);
        localConstructor.setAccessible(true);
        View localView = (View)localConstructor.newInstance(this.mConstructorArgs);
        return localView;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private View createViewFromTag(Context paramContext, String paramString, AttributeSet paramAttributeSet)
  {
    if (paramString.equals("view"))
      paramString = paramAttributeSet.getAttributeValue(null, "class");
    try
    {
      this.mConstructorArgs[0] = paramContext;
      this.mConstructorArgs[1] = paramAttributeSet;
      if (-1 == paramString.indexOf('.'))
      {
        View localView2 = createView(paramContext, paramString, "android.widget.");
        return localView2;
      }
      View localView1 = createView(paramContext, paramString, null);
      return localView1;
    }
    catch (Exception localException)
    {
      return null;
    }
    finally
    {
      this.mConstructorArgs[0] = null;
      this.mConstructorArgs[1] = null;
    }
  }

  private static Context themifyContext(Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.View, 0, 0);
    int i = 0;
    if (paramBoolean1)
      i = localTypedArray.getResourceId(R.styleable.View_android_theme, 0);
    if ((paramBoolean2) && (i == 0))
    {
      i = localTypedArray.getResourceId(R.styleable.View_theme, 0);
      if (i != 0)
        Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
    }
    localTypedArray.recycle();
    if ((i != 0) && ((!(paramContext instanceof ContextThemeWrapper)) || (((ContextThemeWrapper)paramContext).getThemeResId() != i)))
      paramContext = new ContextThemeWrapper(paramContext, i);
    return paramContext;
  }

  public final View createView(View paramView, String paramString, @NonNull Context paramContext, @NonNull AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    Context localContext = paramContext;
    if ((paramBoolean1) && (paramView != null))
      paramContext = paramView.getContext();
    if ((paramBoolean2) || (paramBoolean3))
      paramContext = themifyContext(paramContext, paramAttributeSet, paramBoolean2, paramBoolean3);
    int i = -1;
    switch (paramString.hashCode())
    {
    default:
    case 1666676343:
    case -339785223:
    case 1601505219:
    case 776382189:
    case -1455429095:
    case 1413872058:
    case -1346021293:
    case -1946472170:
    case 2001146706:
    case -938935918:
    }
    while (true)
      switch (i)
      {
      default:
        if (localContext == paramContext)
          break label471;
        return createViewFromTag(paramContext, paramString, paramAttributeSet);
        if (paramString.equals("EditText"))
        {
          i = 0;
          continue;
          if (paramString.equals("Spinner"))
          {
            i = 1;
            continue;
            if (paramString.equals("CheckBox"))
            {
              i = 2;
              continue;
              if (paramString.equals("RadioButton"))
              {
                i = 3;
                continue;
                if (paramString.equals("CheckedTextView"))
                {
                  i = 4;
                  continue;
                  if (paramString.equals("AutoCompleteTextView"))
                  {
                    i = 5;
                    continue;
                    if (paramString.equals("MultiAutoCompleteTextView"))
                    {
                      i = 6;
                      continue;
                      if (paramString.equals("RatingBar"))
                      {
                        i = 7;
                        continue;
                        if (paramString.equals("Button"))
                        {
                          i = 8;
                          continue;
                          if (paramString.equals("TextView"))
                            i = 9;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        break;
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      }
    return new AppCompatEditText(paramContext, paramAttributeSet);
    return new AppCompatSpinner(paramContext, paramAttributeSet);
    return new AppCompatCheckBox(paramContext, paramAttributeSet);
    return new AppCompatRadioButton(paramContext, paramAttributeSet);
    return new AppCompatCheckedTextView(paramContext, paramAttributeSet);
    return new AppCompatAutoCompleteTextView(paramContext, paramAttributeSet);
    return new AppCompatMultiAutoCompleteTextView(paramContext, paramAttributeSet);
    return new AppCompatRatingBar(paramContext, paramAttributeSet);
    return new AppCompatButton(paramContext, paramAttributeSet);
    return new AppCompatTextView(paramContext, paramAttributeSet);
    label471: return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.app.AppCompatViewInflater
 * JD-Core Version:    0.6.2
 */