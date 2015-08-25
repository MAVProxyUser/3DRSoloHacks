package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.View;

public class KeyEventCompat
{
  static final KeyEventVersionImpl IMPL = new BaseKeyEventVersionImpl();

  static
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      IMPL = new HoneycombKeyEventVersionImpl();
      return;
    }
  }

  public static boolean dispatch(KeyEvent paramKeyEvent, KeyEvent.Callback paramCallback, Object paramObject1, Object paramObject2)
  {
    return IMPL.dispatch(paramKeyEvent, paramCallback, paramObject1, paramObject2);
  }

  public static Object getKeyDispatcherState(View paramView)
  {
    return IMPL.getKeyDispatcherState(paramView);
  }

  public static boolean hasModifiers(KeyEvent paramKeyEvent, int paramInt)
  {
    return IMPL.metaStateHasModifiers(paramKeyEvent.getMetaState(), paramInt);
  }

  public static boolean hasNoModifiers(KeyEvent paramKeyEvent)
  {
    return IMPL.metaStateHasNoModifiers(paramKeyEvent.getMetaState());
  }

  public static boolean isTracking(KeyEvent paramKeyEvent)
  {
    return IMPL.isTracking(paramKeyEvent);
  }

  public static boolean metaStateHasModifiers(int paramInt1, int paramInt2)
  {
    return IMPL.metaStateHasModifiers(paramInt1, paramInt2);
  }

  public static boolean metaStateHasNoModifiers(int paramInt)
  {
    return IMPL.metaStateHasNoModifiers(paramInt);
  }

  public static int normalizeMetaState(int paramInt)
  {
    return IMPL.normalizeMetaState(paramInt);
  }

  public static void startTracking(KeyEvent paramKeyEvent)
  {
    IMPL.startTracking(paramKeyEvent);
  }

  static class BaseKeyEventVersionImpl
    implements KeyEventCompat.KeyEventVersionImpl
  {
    private static final int META_ALL_MASK = 247;
    private static final int META_MODIFIER_MASK = 247;

    private static int metaStateFilterDirectionalModifiers(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      int i = 1;
      int j;
      int k;
      if ((paramInt2 & paramInt3) != 0)
      {
        j = i;
        k = paramInt4 | paramInt5;
        if ((paramInt2 & k) == 0)
          break label52;
      }
      while (true)
        if (j != 0)
        {
          if (i != 0)
          {
            throw new IllegalArgumentException("bad arguments");
            j = 0;
            break;
            label52: i = 0;
            continue;
          }
          paramInt1 &= (k ^ 0xFFFFFFFF);
        }
      while (i == 0)
        return paramInt1;
      return paramInt1 & (paramInt3 ^ 0xFFFFFFFF);
    }

    public boolean dispatch(KeyEvent paramKeyEvent, KeyEvent.Callback paramCallback, Object paramObject1, Object paramObject2)
    {
      return paramKeyEvent.dispatch(paramCallback);
    }

    public Object getKeyDispatcherState(View paramView)
    {
      return null;
    }

    public boolean isTracking(KeyEvent paramKeyEvent)
    {
      return false;
    }

    public boolean metaStateHasModifiers(int paramInt1, int paramInt2)
    {
      return metaStateFilterDirectionalModifiers(metaStateFilterDirectionalModifiers(0xF7 & normalizeMetaState(paramInt1), paramInt2, 1, 64, 128), paramInt2, 2, 16, 32) == paramInt2;
    }

    public boolean metaStateHasNoModifiers(int paramInt)
    {
      return (0xF7 & normalizeMetaState(paramInt)) == 0;
    }

    public int normalizeMetaState(int paramInt)
    {
      if ((paramInt & 0xC0) != 0)
        paramInt |= 1;
      if ((paramInt & 0x30) != 0)
        paramInt |= 2;
      return paramInt & 0xF7;
    }

    public void startTracking(KeyEvent paramKeyEvent)
    {
    }
  }

  static class EclairKeyEventVersionImpl extends KeyEventCompat.BaseKeyEventVersionImpl
  {
    public boolean dispatch(KeyEvent paramKeyEvent, KeyEvent.Callback paramCallback, Object paramObject1, Object paramObject2)
    {
      return KeyEventCompatEclair.dispatch(paramKeyEvent, paramCallback, paramObject1, paramObject2);
    }

    public Object getKeyDispatcherState(View paramView)
    {
      return KeyEventCompatEclair.getKeyDispatcherState(paramView);
    }

    public boolean isTracking(KeyEvent paramKeyEvent)
    {
      return KeyEventCompatEclair.isTracking(paramKeyEvent);
    }

    public void startTracking(KeyEvent paramKeyEvent)
    {
      KeyEventCompatEclair.startTracking(paramKeyEvent);
    }
  }

  static class HoneycombKeyEventVersionImpl extends KeyEventCompat.EclairKeyEventVersionImpl
  {
    public boolean metaStateHasModifiers(int paramInt1, int paramInt2)
    {
      return KeyEventCompatHoneycomb.metaStateHasModifiers(paramInt1, paramInt2);
    }

    public boolean metaStateHasNoModifiers(int paramInt)
    {
      return KeyEventCompatHoneycomb.metaStateHasNoModifiers(paramInt);
    }

    public int normalizeMetaState(int paramInt)
    {
      return KeyEventCompatHoneycomb.normalizeMetaState(paramInt);
    }
  }

  static abstract interface KeyEventVersionImpl
  {
    public abstract boolean dispatch(KeyEvent paramKeyEvent, KeyEvent.Callback paramCallback, Object paramObject1, Object paramObject2);

    public abstract Object getKeyDispatcherState(View paramView);

    public abstract boolean isTracking(KeyEvent paramKeyEvent);

    public abstract boolean metaStateHasModifiers(int paramInt1, int paramInt2);

    public abstract boolean metaStateHasNoModifiers(int paramInt);

    public abstract int normalizeMetaState(int paramInt);

    public abstract void startTracking(KeyEvent paramKeyEvent);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.KeyEventCompat
 * JD-Core Version:    0.6.2
 */