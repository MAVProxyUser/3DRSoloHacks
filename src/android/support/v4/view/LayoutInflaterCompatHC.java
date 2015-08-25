package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import java.lang.reflect.Field;

class LayoutInflaterCompatHC
{
  private static final String TAG = "LayoutInflaterCompatHC";
  private static boolean sCheckedField;
  private static Field sLayoutInflaterFactory2Field;

  static void forceSetFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
  {
    if (!sCheckedField);
    try
    {
      sLayoutInflaterFactory2Field = LayoutInflater.class.getDeclaredField("mFactory2");
      sLayoutInflaterFactory2Field.setAccessible(true);
      sCheckedField = true;
      if (sLayoutInflaterFactory2Field == null);
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      try
      {
        sLayoutInflaterFactory2Field.set(paramLayoutInflater, paramFactory2);
        return;
        localNoSuchFieldException = localNoSuchFieldException;
        Log.e("LayoutInflaterCompatHC", "forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results.", localNoSuchFieldException);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Log.e("LayoutInflaterCompatHC", "forceSetFactory2 could not set the Factory2 on LayoutInflater " + paramLayoutInflater + "; inflation may have unexpected results.", localIllegalAccessException);
      }
    }
  }

  static void setFactory(LayoutInflater paramLayoutInflater, LayoutInflaterFactory paramLayoutInflaterFactory)
  {
    if (paramLayoutInflaterFactory != null);
    for (FactoryWrapperHC localFactoryWrapperHC = new FactoryWrapperHC(paramLayoutInflaterFactory); ; localFactoryWrapperHC = null)
    {
      paramLayoutInflater.setFactory2(localFactoryWrapperHC);
      LayoutInflater.Factory localFactory = paramLayoutInflater.getFactory();
      if (!(localFactory instanceof LayoutInflater.Factory2))
        break;
      forceSetFactory2(paramLayoutInflater, (LayoutInflater.Factory2)localFactory);
      return;
    }
    forceSetFactory2(paramLayoutInflater, localFactoryWrapperHC);
  }

  static class FactoryWrapperHC extends LayoutInflaterCompatBase.FactoryWrapper
    implements LayoutInflater.Factory2
  {
    FactoryWrapperHC(LayoutInflaterFactory paramLayoutInflaterFactory)
    {
      super();
    }

    public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
    {
      return this.mDelegateFactory.onCreateView(paramView, paramString, paramContext, paramAttributeSet);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.LayoutInflaterCompatHC
 * JD-Core Version:    0.6.2
 */