package android.support.v4.widget;

import android.os.Build.VERSION;
import android.view.View.OnTouchListener;

public class PopupMenuCompat
{
  static final PopupMenuImpl IMPL = new BasePopupMenuImpl();

  static
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      IMPL = new KitKatPopupMenuImpl();
      return;
    }
  }

  public static View.OnTouchListener getDragToOpenListener(Object paramObject)
  {
    return IMPL.getDragToOpenListener(paramObject);
  }

  static class BasePopupMenuImpl
    implements PopupMenuCompat.PopupMenuImpl
  {
    public View.OnTouchListener getDragToOpenListener(Object paramObject)
    {
      return null;
    }
  }

  static class KitKatPopupMenuImpl extends PopupMenuCompat.BasePopupMenuImpl
  {
    public View.OnTouchListener getDragToOpenListener(Object paramObject)
    {
      return PopupMenuCompatKitKat.getDragToOpenListener(paramObject);
    }
  }

  static abstract interface PopupMenuImpl
  {
    public abstract View.OnTouchListener getDragToOpenListener(Object paramObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.widget.PopupMenuCompat
 * JD-Core Version:    0.6.2
 */