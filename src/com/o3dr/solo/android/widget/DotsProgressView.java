package com.o3dr.solo.android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.o3dr.solo.android.R.styleable;

public class DotsProgressView extends LinearLayout
{
  private boolean autoPlay;
  AnimatorSet completeAnimation;
  private ImageView dotFive;
  private ImageView dotFour;
  private ImageView dotOne;
  private ImageView dotThree;
  private ImageView dotTwo;
  private boolean isPlaying;

  public DotsProgressView(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null);
  }

  public DotsProgressView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }

  public DotsProgressView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }

  private AnimatorSet animateDot(ImageView paramImageView)
  {
    ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(paramImageView, "scaleX", new float[] { 1.0F, 2.0F });
    ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(paramImageView, "scaleY", new float[] { 1.0F, 2.0F });
    localObjectAnimator1.setRepeatMode(2);
    localObjectAnimator1.setRepeatCount(1);
    localObjectAnimator2.setRepeatMode(2);
    localObjectAnimator2.setRepeatCount(1);
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.play(localObjectAnimator1).with(localObjectAnimator2);
    localAnimatorSet.setDuration(150L);
    return localAnimatorSet;
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet != null)
    {
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.WaitingDots);
      this.autoPlay = localTypedArray.getBoolean(3, true);
      localTypedArray.recycle();
    }
    inflate(getContext(), 2130903122, this);
    this.dotOne = ((ImageView)findViewById(2131493345));
    this.dotTwo = ((ImageView)findViewById(2131493346));
    this.dotThree = ((ImageView)findViewById(2131493347));
    this.dotFour = ((ImageView)findViewById(2131493348));
    this.dotFive = ((ImageView)findViewById(2131493349));
    this.completeAnimation = new AnimatorSet();
    this.completeAnimation.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        if (DotsProgressView.this.isPlaying)
          paramAnonymousAnimator.start();
      }
    });
    AnimatorSet localAnimatorSet = this.completeAnimation;
    Animator[] arrayOfAnimator = new Animator[5];
    arrayOfAnimator[0] = animateDot(this.dotOne);
    arrayOfAnimator[1] = animateDot(this.dotTwo);
    arrayOfAnimator[2] = animateDot(this.dotThree);
    arrayOfAnimator[3] = animateDot(this.dotFour);
    arrayOfAnimator[4] = animateDot(this.dotFive);
    localAnimatorSet.playSequentially(arrayOfAnimator);
    if (this.autoPlay)
    {
      setWillNotDraw(false);
      this.completeAnimation.start();
    }
    while (true)
    {
      this.isPlaying = this.autoPlay;
      return;
      tintDots();
    }
  }

  private void removeTint()
  {
    this.dotOne.clearColorFilter();
    this.dotTwo.clearColorFilter();
    this.dotThree.clearColorFilter();
    this.dotFour.clearColorFilter();
    this.dotFive.clearColorFilter();
  }

  private void tintDots()
  {
    this.dotOne.setColorFilter(getResources().getColor(2131427396));
    this.dotTwo.setColorFilter(getResources().getColor(2131427396));
    this.dotThree.setColorFilter(getResources().getColor(2131427396));
    this.dotFour.setColorFilter(getResources().getColor(2131427396));
    this.dotFive.setColorFilter(getResources().getColor(2131427396));
  }

  public void start()
  {
    if (this.completeAnimation.isStarted())
      return;
    this.isPlaying = true;
    setWillNotDraw(false);
    removeTint();
    this.completeAnimation.start();
  }

  public void stop()
  {
    this.isPlaying = false;
    tintDots();
    this.completeAnimation.cancel();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.DotsProgressView
 * JD-Core Version:    0.6.2
 */