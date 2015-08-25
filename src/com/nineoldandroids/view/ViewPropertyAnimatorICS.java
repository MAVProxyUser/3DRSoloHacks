package com.nineoldandroids.view;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

class ViewPropertyAnimatorICS extends ViewPropertyAnimator
{
  private static final long RETURN_WHEN_NULL = -1L;
  private final WeakReference<android.view.ViewPropertyAnimator> mNative;

  ViewPropertyAnimatorICS(View paramView)
  {
    this.mNative = new WeakReference(paramView.animate());
  }

  public ViewPropertyAnimator alpha(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.alpha(paramFloat);
    return this;
  }

  public ViewPropertyAnimator alphaBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.alphaBy(paramFloat);
    return this;
  }

  public void cancel()
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.cancel();
  }

  public long getDuration()
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      return localViewPropertyAnimator.getDuration();
    return -1L;
  }

  public long getStartDelay()
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      return localViewPropertyAnimator.getStartDelay();
    return -1L;
  }

  public ViewPropertyAnimator rotation(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.rotation(paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.rotationBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationX(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.rotationX(paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationXBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.rotationXBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationY(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.rotationY(paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationYBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.rotationYBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimator scaleX(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.scaleX(paramFloat);
    return this;
  }

  public ViewPropertyAnimator scaleXBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.scaleXBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimator scaleY(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.scaleY(paramFloat);
    return this;
  }

  public ViewPropertyAnimator scaleYBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.scaleYBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimator setDuration(long paramLong)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.setDuration(paramLong);
    return this;
  }

  public ViewPropertyAnimator setInterpolator(Interpolator paramInterpolator)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.setInterpolator(paramInterpolator);
    return this;
  }

  public ViewPropertyAnimator setListener(final com.nineoldandroids.animation.Animator.AnimatorListener paramAnimatorListener)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
    {
      if (paramAnimatorListener == null)
        localViewPropertyAnimator.setListener(null);
    }
    else
      return this;
    localViewPropertyAnimator.setListener(new android.animation.Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        paramAnimatorListener.onAnimationCancel(null);
      }

      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        paramAnimatorListener.onAnimationEnd(null);
      }

      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        paramAnimatorListener.onAnimationRepeat(null);
      }

      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        paramAnimatorListener.onAnimationStart(null);
      }
    });
    return this;
  }

  public ViewPropertyAnimator setStartDelay(long paramLong)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.setStartDelay(paramLong);
    return this;
  }

  public void start()
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.start();
  }

  public ViewPropertyAnimator translationX(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.translationX(paramFloat);
    return this;
  }

  public ViewPropertyAnimator translationXBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.translationXBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimator translationY(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.translationY(paramFloat);
    return this;
  }

  public ViewPropertyAnimator translationYBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.translationYBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimator x(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.x(paramFloat);
    return this;
  }

  public ViewPropertyAnimator xBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.xBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimator y(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.y(paramFloat);
    return this;
  }

  public ViewPropertyAnimator yBy(float paramFloat)
  {
    android.view.ViewPropertyAnimator localViewPropertyAnimator = (android.view.ViewPropertyAnimator)this.mNative.get();
    if (localViewPropertyAnimator != null)
      localViewPropertyAnimator.yBy(paramFloat);
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.view.ViewPropertyAnimatorICS
 * JD-Core Version:    0.6.2
 */