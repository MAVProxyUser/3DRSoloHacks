package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;
import com.google.android.gms.R.color;
import com.google.android.gms.R.drawable;
import com.google.android.gms.R.string;

public final class zzz extends Button
{
  public zzz(Context paramContext)
  {
    this(paramContext, null);
  }

  public zzz(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 16842824);
  }

  private void zza(Resources paramResources)
  {
    setTypeface(Typeface.DEFAULT_BOLD);
    setTextSize(14.0F);
    float f = paramResources.getDisplayMetrics().density;
    setMinHeight((int)(0.5F + f * 48.0F));
    setMinWidth((int)(0.5F + f * 48.0F));
  }

  private int zzb(int paramInt1, int paramInt2, int paramInt3)
  {
    switch (paramInt1)
    {
    default:
      throw new IllegalStateException("Unknown color scheme: " + paramInt1);
    case 1:
      paramInt2 = paramInt3;
    case 0:
    }
    return paramInt2;
  }

  private void zzb(Resources paramResources, int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default:
      throw new IllegalStateException("Unknown button size: " + paramInt1);
    case 0:
    case 1:
    case 2:
    }
    for (int i = zzb(paramInt2, R.drawable.common_signin_btn_text_dark, R.drawable.common_signin_btn_text_light); i == -1; i = zzb(paramInt2, R.drawable.common_signin_btn_icon_dark, R.drawable.common_signin_btn_icon_light))
      throw new IllegalStateException("Could not find background resource!");
    setBackgroundDrawable(paramResources.getDrawable(i));
  }

  private void zzc(Resources paramResources, int paramInt1, int paramInt2)
  {
    setTextColor(paramResources.getColorStateList(zzb(paramInt2, R.color.common_signin_btn_text_dark, R.color.common_signin_btn_text_light)));
    switch (paramInt1)
    {
    default:
      throw new IllegalStateException("Unknown button size: " + paramInt1);
    case 0:
      setText(paramResources.getString(R.string.common_signin_button_text));
      return;
    case 1:
      setText(paramResources.getString(R.string.common_signin_button_text_long));
      return;
    case 2:
    }
    setText(null);
  }

  public void zza(Resources paramResources, int paramInt1, int paramInt2)
  {
    boolean bool1;
    if ((paramInt1 >= 0) && (paramInt1 < 3))
    {
      bool1 = true;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(paramInt1);
      zzv.zza(bool1, "Unknown button size %d", arrayOfObject1);
      if ((paramInt2 < 0) || (paramInt2 >= 2))
        break label96;
    }
    label96: for (boolean bool2 = true; ; bool2 = false)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(paramInt2);
      zzv.zza(bool2, "Unknown color scheme %s", arrayOfObject2);
      zza(paramResources);
      zzb(paramResources, paramInt1, paramInt2);
      zzc(paramResources, paramInt1, paramInt2);
      return;
      bool1 = false;
      break;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzz
 * JD-Core Version:    0.6.2
 */