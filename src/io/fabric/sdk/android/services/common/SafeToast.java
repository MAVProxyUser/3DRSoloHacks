package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import io.fabric.sdk.android.services.concurrency.PriorityRunnable;

public class SafeToast extends Toast
{
  public SafeToast(Context paramContext)
  {
    super(paramContext);
  }

  public static Toast makeText(Context paramContext, int paramInt1, int paramInt2)
    throws Resources.NotFoundException
  {
    return makeText(paramContext, paramContext.getResources().getText(paramInt1), paramInt2);
  }

  public static Toast makeText(Context paramContext, CharSequence paramCharSequence, int paramInt)
  {
    Toast localToast = Toast.makeText(paramContext, paramCharSequence, paramInt);
    SafeToast localSafeToast = new SafeToast(paramContext);
    localSafeToast.setView(localToast.getView());
    localSafeToast.setDuration(localToast.getDuration());
    return localSafeToast;
  }

  public void show()
  {
    if (Looper.myLooper() == Looper.getMainLooper())
    {
      super.show();
      return;
    }
    new Handler(Looper.getMainLooper()).post(new PriorityRunnable()
    {
      public void run()
      {
        SafeToast.this.show();
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.SafeToast
 * JD-Core Version:    0.6.2
 */