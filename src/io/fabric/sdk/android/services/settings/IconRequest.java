package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;

public class IconRequest
{
  public final String hash;
  public final int height;
  public final int iconResourceId;
  public final int width;

  public IconRequest(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this.hash = paramString;
    this.iconResourceId = paramInt1;
    this.width = paramInt2;
    this.height = paramInt3;
  }

  public static IconRequest build(Context paramContext, String paramString)
  {
    Object localObject = null;
    if (paramString != null);
    try
    {
      int i = CommonUtils.getAppIconResourceId(paramContext);
      Fabric.getLogger().d("Fabric", "App icon resource ID is " + i);
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      BitmapFactory.decodeResource(paramContext.getResources(), i, localOptions);
      IconRequest localIconRequest = new IconRequest(paramString, i, localOptions.outWidth, localOptions.outHeight);
      localObject = localIconRequest;
      return localObject;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Failed to load icon", localException);
    }
    return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.IconRequest
 * JD-Core Version:    0.6.2
 */