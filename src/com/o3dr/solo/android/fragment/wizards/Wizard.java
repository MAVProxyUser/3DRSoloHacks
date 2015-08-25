package com.o3dr.solo.android.fragment.wizards;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import com.o3dr.solo.android.fragment.base.BaseWizardPager;
import com.o3dr.solo.android.fragment.base.BaseWizardPager.OnSwipeOutListener;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.List;

public class Wizard
  implements BaseWizardPager.OnSwipeOutListener
{
  private static final String TAG = Wizard.class.getSimpleName();
  private int backStackEntryCount;
  private final WizardCallbacks callbacks;
  private boolean firstTime = true;
  private final BaseWizardPager mPager;
  private final WizardFlow wizardFlow;

  public Wizard(WizardFlow paramWizardFlow, final WizardCallbacks paramWizardCallbacks, final BaseWizardPager paramBaseWizardPager, final FragmentManager paramFragmentManager, CirclePageIndicator paramCirclePageIndicator)
  {
    this.wizardFlow = paramWizardFlow;
    this.callbacks = paramWizardCallbacks;
    this.mPager = paramBaseWizardPager;
    this.mPager.setAdapter(new WizardPagerAdapter(paramFragmentManager));
    this.mPager.setOnSwipeOutListener(this);
    paramCirclePageIndicator.setViewPager(this.mPager);
    this.backStackEntryCount = paramFragmentManager.getBackStackEntryCount();
    paramFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
    {
      public void onBackStackChanged()
      {
        Wizard.access$002(Wizard.this, paramFragmentManager.getBackStackEntryCount());
        if (Wizard.this.backStackEntryCount < Wizard.this.getCurrentStepPosition())
          paramBaseWizardPager.setCurrentItem(-1 + Wizard.this.getCurrentStepPosition());
      }
    });
    paramCirclePageIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
    {
      public void onPageSelected(int paramAnonymousInt)
      {
        paramWizardCallbacks.onStepChanged(paramAnonymousInt);
      }
    });
  }

  public int getCurrentStepPosition()
  {
    return this.mPager.getCurrentItem();
  }

  public void goBack()
  {
    if (!isFirstStep())
      setCurrentStep(-1 + this.mPager.getCurrentItem());
  }

  public void goNext()
  {
    if (!isLastStep())
    {
      setCurrentStep(1 + this.mPager.getCurrentItem());
      return;
    }
    this.callbacks.onWizardComplete();
  }

  public boolean isFirstStep()
  {
    return this.mPager.getCurrentItem() == 0;
  }

  public boolean isLastStep()
  {
    return this.mPager.getCurrentItem() == -1 + this.wizardFlow.getStepsCount();
  }

  public void onSwipeOutAtEnd()
  {
    this.callbacks.onWizardComplete();
  }

  public void setCurrentStep(int paramInt)
  {
    this.mPager.setCurrentItem(paramInt);
  }

  public static abstract interface WizardCallbacks
  {
    public abstract void onStepChanged(int paramInt);

    public abstract void onWizardComplete();

    public abstract void onWizardStarted();
  }

  public class WizardPagerAdapter extends FragmentStatePagerAdapter
  {
    public WizardPagerAdapter(FragmentManager arg2)
    {
      super();
    }

    public int getCount()
    {
      return Wizard.this.wizardFlow.getSteps().size();
    }

    public Fragment getItem(int paramInt)
    {
      TutorialStep localTutorialStep = null;
      try
      {
        WizardFlow.StepMetaData localStepMetaData = (WizardFlow.StepMetaData)Wizard.this.wizardFlow.getSteps().get(paramInt);
        localTutorialStep = (TutorialStep)localStepMetaData.getStepClass().newInstance();
        localTutorialStep.setArguments(localStepMetaData.getArguments());
        if ((paramInt == 0) && (Wizard.this.firstTime))
        {
          Wizard.this.callbacks.onWizardStarted();
          Wizard.access$202(Wizard.this, false);
        }
        return localTutorialStep;
      }
      catch (InstantiationException localInstantiationException)
      {
        Log.e(Wizard.TAG, "Unable to instantiate a new wizard step.", localInstantiationException);
        return localTutorialStep;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Log.e(Wizard.TAG, "Unable to access wizard step", localIllegalAccessException);
      }
      return localTutorialStep;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.wizards.Wizard
 * JD-Core Version:    0.6.2
 */