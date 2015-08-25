package android.support.v7.internal.text;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import java.util.Locale;

public class AllCapsTransformationMethod
  implements TransformationMethod
{
  private Locale mLocale;

  public AllCapsTransformationMethod(Context paramContext)
  {
    this.mLocale = paramContext.getResources().getConfiguration().locale;
  }

  public CharSequence getTransformation(CharSequence paramCharSequence, View paramView)
  {
    if (paramCharSequence != null)
      return paramCharSequence.toString().toUpperCase(this.mLocale);
    return null;
  }

  public void onFocusChanged(View paramView, CharSequence paramCharSequence, boolean paramBoolean, int paramInt, Rect paramRect)
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.text.AllCapsTransformationMethod
 * JD-Core Version:    0.6.2
 */