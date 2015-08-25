package com.nineoldandroids.view;

import android.view.View;
import com.nineoldandroids.view.animation.AnimatorProxy;

public final class ViewHelper
{
  public static float getAlpha(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getAlpha();
    return Honeycomb.getAlpha(paramView);
  }

  public static float getPivotX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getPivotX();
    return Honeycomb.getPivotX(paramView);
  }

  public static float getPivotY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getPivotY();
    return Honeycomb.getPivotY(paramView);
  }

  public static float getRotation(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getRotation();
    return Honeycomb.getRotation(paramView);
  }

  public static float getRotationX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getRotationX();
    return Honeycomb.getRotationX(paramView);
  }

  public static float getRotationY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getRotationY();
    return Honeycomb.getRotationY(paramView);
  }

  public static float getScaleX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getScaleX();
    return Honeycomb.getScaleX(paramView);
  }

  public static float getScaleY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getScaleY();
    return Honeycomb.getScaleY(paramView);
  }

  public static float getScrollX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getScrollX();
    return Honeycomb.getScrollX(paramView);
  }

  public static float getScrollY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getScrollY();
    return Honeycomb.getScrollY(paramView);
  }

  public static float getTranslationX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getTranslationX();
    return Honeycomb.getTranslationX(paramView);
  }

  public static float getTranslationY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getTranslationY();
    return Honeycomb.getTranslationY(paramView);
  }

  public static float getX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getX();
    return Honeycomb.getX(paramView);
  }

  public static float getY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY)
      return AnimatorProxy.wrap(paramView).getY();
    return Honeycomb.getY(paramView);
  }

  public static void setAlpha(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setAlpha(paramFloat);
      return;
    }
    Honeycomb.setAlpha(paramView, paramFloat);
  }

  public static void setPivotX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setPivotX(paramFloat);
      return;
    }
    Honeycomb.setPivotX(paramView, paramFloat);
  }

  public static void setPivotY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setPivotY(paramFloat);
      return;
    }
    Honeycomb.setPivotY(paramView, paramFloat);
  }

  public static void setRotation(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setRotation(paramFloat);
      return;
    }
    Honeycomb.setRotation(paramView, paramFloat);
  }

  public static void setRotationX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setRotationX(paramFloat);
      return;
    }
    Honeycomb.setRotationX(paramView, paramFloat);
  }

  public static void setRotationY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setRotationY(paramFloat);
      return;
    }
    Honeycomb.setRotationY(paramView, paramFloat);
  }

  public static void setScaleX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setScaleX(paramFloat);
      return;
    }
    Honeycomb.setScaleX(paramView, paramFloat);
  }

  public static void setScaleY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setScaleY(paramFloat);
      return;
    }
    Honeycomb.setScaleY(paramView, paramFloat);
  }

  public static void setScrollX(View paramView, int paramInt)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setScrollX(paramInt);
      return;
    }
    Honeycomb.setScrollX(paramView, paramInt);
  }

  public static void setScrollY(View paramView, int paramInt)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setScrollY(paramInt);
      return;
    }
    Honeycomb.setScrollY(paramView, paramInt);
  }

  public static void setTranslationX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setTranslationX(paramFloat);
      return;
    }
    Honeycomb.setTranslationX(paramView, paramFloat);
  }

  public static void setTranslationY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setTranslationY(paramFloat);
      return;
    }
    Honeycomb.setTranslationY(paramView, paramFloat);
  }

  public static void setX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setX(paramFloat);
      return;
    }
    Honeycomb.setX(paramView, paramFloat);
  }

  public static void setY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setY(paramFloat);
      return;
    }
    Honeycomb.setY(paramView, paramFloat);
  }

  private static final class Honeycomb
  {
    static float getAlpha(View paramView)
    {
      return paramView.getAlpha();
    }

    static float getPivotX(View paramView)
    {
      return paramView.getPivotX();
    }

    static float getPivotY(View paramView)
    {
      return paramView.getPivotY();
    }

    static float getRotation(View paramView)
    {
      return paramView.getRotation();
    }

    static float getRotationX(View paramView)
    {
      return paramView.getRotationX();
    }

    static float getRotationY(View paramView)
    {
      return paramView.getRotationY();
    }

    static float getScaleX(View paramView)
    {
      return paramView.getScaleX();
    }

    static float getScaleY(View paramView)
    {
      return paramView.getScaleY();
    }

    static float getScrollX(View paramView)
    {
      return paramView.getScrollX();
    }

    static float getScrollY(View paramView)
    {
      return paramView.getScrollY();
    }

    static float getTranslationX(View paramView)
    {
      return paramView.getTranslationX();
    }

    static float getTranslationY(View paramView)
    {
      return paramView.getTranslationY();
    }

    static float getX(View paramView)
    {
      return paramView.getX();
    }

    static float getY(View paramView)
    {
      return paramView.getY();
    }

    static void setAlpha(View paramView, float paramFloat)
    {
      paramView.setAlpha(paramFloat);
    }

    static void setPivotX(View paramView, float paramFloat)
    {
      paramView.setPivotX(paramFloat);
    }

    static void setPivotY(View paramView, float paramFloat)
    {
      paramView.setPivotY(paramFloat);
    }

    static void setRotation(View paramView, float paramFloat)
    {
      paramView.setRotation(paramFloat);
    }

    static void setRotationX(View paramView, float paramFloat)
    {
      paramView.setRotationX(paramFloat);
    }

    static void setRotationY(View paramView, float paramFloat)
    {
      paramView.setRotationY(paramFloat);
    }

    static void setScaleX(View paramView, float paramFloat)
    {
      paramView.setScaleX(paramFloat);
    }

    static void setScaleY(View paramView, float paramFloat)
    {
      paramView.setScaleY(paramFloat);
    }

    static void setScrollX(View paramView, int paramInt)
    {
      paramView.setScrollX(paramInt);
    }

    static void setScrollY(View paramView, int paramInt)
    {
      paramView.setScrollY(paramInt);
    }

    static void setTranslationX(View paramView, float paramFloat)
    {
      paramView.setTranslationX(paramFloat);
    }

    static void setTranslationY(View paramView, float paramFloat)
    {
      paramView.setTranslationY(paramFloat);
    }

    static void setX(View paramView, float paramFloat)
    {
      paramView.setX(paramFloat);
    }

    static void setY(View paramView, float paramFloat)
    {
      paramView.setY(paramFloat);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.view.ViewHelper
 * JD-Core Version:    0.6.2
 */