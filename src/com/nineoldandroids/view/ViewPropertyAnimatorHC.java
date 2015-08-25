package com.nineoldandroids.view;

import android.view.View;
import android.view.animation.Interpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

class ViewPropertyAnimatorHC extends ViewPropertyAnimator
{
  private static final int ALPHA = 512;
  private static final int NONE = 0;
  private static final int ROTATION = 16;
  private static final int ROTATION_X = 32;
  private static final int ROTATION_Y = 64;
  private static final int SCALE_X = 4;
  private static final int SCALE_Y = 8;
  private static final int TRANSFORM_MASK = 511;
  private static final int TRANSLATION_X = 1;
  private static final int TRANSLATION_Y = 2;
  private static final int X = 128;
  private static final int Y = 256;
  private Runnable mAnimationStarter = new Runnable()
  {
    public void run()
    {
      ViewPropertyAnimatorHC.this.startAnimation();
    }
  };
  private AnimatorEventListener mAnimatorEventListener = new AnimatorEventListener(null);
  private HashMap<Animator, PropertyBundle> mAnimatorMap = new HashMap();
  private long mDuration;
  private boolean mDurationSet = false;
  private Interpolator mInterpolator;
  private boolean mInterpolatorSet = false;
  private Animator.AnimatorListener mListener = null;
  ArrayList<NameValuesHolder> mPendingAnimations = new ArrayList();
  private long mStartDelay = 0L;
  private boolean mStartDelaySet = false;
  private final WeakReference<View> mView;

  ViewPropertyAnimatorHC(View paramView)
  {
    this.mView = new WeakReference(paramView);
  }

  private void animateProperty(int paramInt, float paramFloat)
  {
    float f = getValue(paramInt);
    animatePropertyBy(paramInt, f, paramFloat - f);
  }

  private void animatePropertyBy(int paramInt, float paramFloat)
  {
    animatePropertyBy(paramInt, getValue(paramInt), paramFloat);
  }

  private void animatePropertyBy(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (this.mAnimatorMap.size() > 0)
    {
      Iterator localIterator = this.mAnimatorMap.keySet().iterator();
      Animator localAnimator;
      PropertyBundle localPropertyBundle;
      do
      {
        boolean bool = localIterator.hasNext();
        localObject = null;
        if (!bool)
          break;
        localAnimator = (Animator)localIterator.next();
        localPropertyBundle = (PropertyBundle)this.mAnimatorMap.get(localAnimator);
      }
      while ((!localPropertyBundle.cancel(paramInt)) || (localPropertyBundle.mPropertyMask != 0));
      Object localObject = localAnimator;
      if (localObject != null)
        localObject.cancel();
    }
    NameValuesHolder localNameValuesHolder = new NameValuesHolder(paramInt, paramFloat1, paramFloat2);
    this.mPendingAnimations.add(localNameValuesHolder);
    View localView = (View)this.mView.get();
    if (localView != null)
    {
      localView.removeCallbacks(this.mAnimationStarter);
      localView.post(this.mAnimationStarter);
    }
  }

  private float getValue(int paramInt)
  {
    View localView = (View)this.mView.get();
    if (localView != null);
    switch (paramInt)
    {
    default:
      return 0.0F;
    case 1:
      return localView.getTranslationX();
    case 2:
      return localView.getTranslationY();
    case 16:
      return localView.getRotation();
    case 32:
      return localView.getRotationX();
    case 64:
      return localView.getRotationY();
    case 4:
      return localView.getScaleX();
    case 8:
      return localView.getScaleY();
    case 128:
      return localView.getX();
    case 256:
      return localView.getY();
    case 512:
    }
    return localView.getAlpha();
  }

  private void setValue(int paramInt, float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null);
    switch (paramInt)
    {
    default:
      return;
    case 1:
      localView.setTranslationX(paramFloat);
      return;
    case 2:
      localView.setTranslationY(paramFloat);
      return;
    case 16:
      localView.setRotation(paramFloat);
      return;
    case 32:
      localView.setRotationX(paramFloat);
      return;
    case 64:
      localView.setRotationY(paramFloat);
      return;
    case 4:
      localView.setScaleX(paramFloat);
      return;
    case 8:
      localView.setScaleY(paramFloat);
      return;
    case 128:
      localView.setX(paramFloat);
      return;
    case 256:
      localView.setY(paramFloat);
      return;
    case 512:
    }
    localView.setAlpha(paramFloat);
  }

  private void startAnimation()
  {
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { 1.0F });
    ArrayList localArrayList = (ArrayList)this.mPendingAnimations.clone();
    this.mPendingAnimations.clear();
    int i = 0;
    int j = localArrayList.size();
    for (int k = 0; k < j; k++)
      i |= ((NameValuesHolder)localArrayList.get(k)).mNameConstant;
    this.mAnimatorMap.put(localValueAnimator, new PropertyBundle(i, localArrayList));
    localValueAnimator.addUpdateListener(this.mAnimatorEventListener);
    localValueAnimator.addListener(this.mAnimatorEventListener);
    if (this.mStartDelaySet)
      localValueAnimator.setStartDelay(this.mStartDelay);
    if (this.mDurationSet)
      localValueAnimator.setDuration(this.mDuration);
    if (this.mInterpolatorSet)
      localValueAnimator.setInterpolator(this.mInterpolator);
    localValueAnimator.start();
  }

  public ViewPropertyAnimator alpha(float paramFloat)
  {
    animateProperty(512, paramFloat);
    return this;
  }

  public ViewPropertyAnimator alphaBy(float paramFloat)
  {
    animatePropertyBy(512, paramFloat);
    return this;
  }

  public void cancel()
  {
    if (this.mAnimatorMap.size() > 0)
    {
      Iterator localIterator = ((HashMap)this.mAnimatorMap.clone()).keySet().iterator();
      while (localIterator.hasNext())
        ((Animator)localIterator.next()).cancel();
    }
    this.mPendingAnimations.clear();
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.removeCallbacks(this.mAnimationStarter);
  }

  public long getDuration()
  {
    if (this.mDurationSet)
      return this.mDuration;
    return new ValueAnimator().getDuration();
  }

  public long getStartDelay()
  {
    if (this.mStartDelaySet)
      return this.mStartDelay;
    return 0L;
  }

  public ViewPropertyAnimator rotation(float paramFloat)
  {
    animateProperty(16, paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationBy(float paramFloat)
  {
    animatePropertyBy(16, paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationX(float paramFloat)
  {
    animateProperty(32, paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationXBy(float paramFloat)
  {
    animatePropertyBy(32, paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationY(float paramFloat)
  {
    animateProperty(64, paramFloat);
    return this;
  }

  public ViewPropertyAnimator rotationYBy(float paramFloat)
  {
    animatePropertyBy(64, paramFloat);
    return this;
  }

  public ViewPropertyAnimator scaleX(float paramFloat)
  {
    animateProperty(4, paramFloat);
    return this;
  }

  public ViewPropertyAnimator scaleXBy(float paramFloat)
  {
    animatePropertyBy(4, paramFloat);
    return this;
  }

  public ViewPropertyAnimator scaleY(float paramFloat)
  {
    animateProperty(8, paramFloat);
    return this;
  }

  public ViewPropertyAnimator scaleYBy(float paramFloat)
  {
    animatePropertyBy(8, paramFloat);
    return this;
  }

  public ViewPropertyAnimator setDuration(long paramLong)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("Animators cannot have negative duration: " + paramLong);
    this.mDurationSet = true;
    this.mDuration = paramLong;
    return this;
  }

  public ViewPropertyAnimator setInterpolator(Interpolator paramInterpolator)
  {
    this.mInterpolatorSet = true;
    this.mInterpolator = paramInterpolator;
    return this;
  }

  public ViewPropertyAnimator setListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.mListener = paramAnimatorListener;
    return this;
  }

  public ViewPropertyAnimator setStartDelay(long paramLong)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("Animators cannot have negative duration: " + paramLong);
    this.mStartDelaySet = true;
    this.mStartDelay = paramLong;
    return this;
  }

  public void start()
  {
    startAnimation();
  }

  public ViewPropertyAnimator translationX(float paramFloat)
  {
    animateProperty(1, paramFloat);
    return this;
  }

  public ViewPropertyAnimator translationXBy(float paramFloat)
  {
    animatePropertyBy(1, paramFloat);
    return this;
  }

  public ViewPropertyAnimator translationY(float paramFloat)
  {
    animateProperty(2, paramFloat);
    return this;
  }

  public ViewPropertyAnimator translationYBy(float paramFloat)
  {
    animatePropertyBy(2, paramFloat);
    return this;
  }

  public ViewPropertyAnimator x(float paramFloat)
  {
    animateProperty(128, paramFloat);
    return this;
  }

  public ViewPropertyAnimator xBy(float paramFloat)
  {
    animatePropertyBy(128, paramFloat);
    return this;
  }

  public ViewPropertyAnimator y(float paramFloat)
  {
    animateProperty(256, paramFloat);
    return this;
  }

  public ViewPropertyAnimator yBy(float paramFloat)
  {
    animatePropertyBy(256, paramFloat);
    return this;
  }

  private class AnimatorEventListener
    implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener
  {
    private AnimatorEventListener()
    {
    }

    public void onAnimationCancel(Animator paramAnimator)
    {
      if (ViewPropertyAnimatorHC.this.mListener != null)
        ViewPropertyAnimatorHC.this.mListener.onAnimationCancel(paramAnimator);
    }

    public void onAnimationEnd(Animator paramAnimator)
    {
      if (ViewPropertyAnimatorHC.this.mListener != null)
        ViewPropertyAnimatorHC.this.mListener.onAnimationEnd(paramAnimator);
      ViewPropertyAnimatorHC.this.mAnimatorMap.remove(paramAnimator);
      if (ViewPropertyAnimatorHC.this.mAnimatorMap.isEmpty())
        ViewPropertyAnimatorHC.access$202(ViewPropertyAnimatorHC.this, null);
    }

    public void onAnimationRepeat(Animator paramAnimator)
    {
      if (ViewPropertyAnimatorHC.this.mListener != null)
        ViewPropertyAnimatorHC.this.mListener.onAnimationRepeat(paramAnimator);
    }

    public void onAnimationStart(Animator paramAnimator)
    {
      if (ViewPropertyAnimatorHC.this.mListener != null)
        ViewPropertyAnimatorHC.this.mListener.onAnimationStart(paramAnimator);
    }

    public void onAnimationUpdate(ValueAnimator paramValueAnimator)
    {
      float f1 = paramValueAnimator.getAnimatedFraction();
      ViewPropertyAnimatorHC.PropertyBundle localPropertyBundle = (ViewPropertyAnimatorHC.PropertyBundle)ViewPropertyAnimatorHC.this.mAnimatorMap.get(paramValueAnimator);
      if ((0x1FF & localPropertyBundle.mPropertyMask) != 0)
      {
        View localView2 = (View)ViewPropertyAnimatorHC.this.mView.get();
        if (localView2 != null)
          localView2.invalidate();
      }
      ArrayList localArrayList = localPropertyBundle.mNameValuesHolder;
      if (localArrayList != null)
      {
        int i = localArrayList.size();
        for (int j = 0; j < i; j++)
        {
          ViewPropertyAnimatorHC.NameValuesHolder localNameValuesHolder = (ViewPropertyAnimatorHC.NameValuesHolder)localArrayList.get(j);
          float f2 = localNameValuesHolder.mFromValue + f1 * localNameValuesHolder.mDeltaValue;
          ViewPropertyAnimatorHC.this.setValue(localNameValuesHolder.mNameConstant, f2);
        }
      }
      View localView1 = (View)ViewPropertyAnimatorHC.this.mView.get();
      if (localView1 != null)
        localView1.invalidate();
    }
  }

  private static class NameValuesHolder
  {
    float mDeltaValue;
    float mFromValue;
    int mNameConstant;

    NameValuesHolder(int paramInt, float paramFloat1, float paramFloat2)
    {
      this.mNameConstant = paramInt;
      this.mFromValue = paramFloat1;
      this.mDeltaValue = paramFloat2;
    }
  }

  private static class PropertyBundle
  {
    ArrayList<ViewPropertyAnimatorHC.NameValuesHolder> mNameValuesHolder;
    int mPropertyMask;

    PropertyBundle(int paramInt, ArrayList<ViewPropertyAnimatorHC.NameValuesHolder> paramArrayList)
    {
      this.mPropertyMask = paramInt;
      this.mNameValuesHolder = paramArrayList;
    }

    boolean cancel(int paramInt)
    {
      if (((paramInt & this.mPropertyMask) != 0) && (this.mNameValuesHolder != null))
      {
        int i = this.mNameValuesHolder.size();
        for (int j = 0; j < i; j++)
          if (((ViewPropertyAnimatorHC.NameValuesHolder)this.mNameValuesHolder.get(j)).mNameConstant == paramInt)
          {
            this.mNameValuesHolder.remove(j);
            this.mPropertyMask &= (paramInt ^ 0xFFFFFFFF);
            return true;
          }
      }
      return false;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.view.ViewPropertyAnimatorHC
 * JD-Core Version:    0.6.2
 */