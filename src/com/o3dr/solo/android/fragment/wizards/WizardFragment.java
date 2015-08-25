package com.o3dr.solo.android.fragment.wizards;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.o3dr.solo.android.fragment.TutorialWizardFragment.Contract;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.fragment.base.BaseWizardPager;
import com.viewpagerindicator.CirclePageIndicator;

public abstract class WizardFragment<T> extends BaseFragment
  implements Wizard.WizardCallbacks
{
  private static final String TAG = WizardFragment.class.getSimpleName();
  private T contract;
  private WizardFlow flow;
  private BaseWizardPager viewPager;
  protected Wizard wizard;

  public final T getContract()
  {
    return this.contract;
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof TutorialWizardFragment.Contract))
      throw new IllegalStateException("Parent activity must implement " + WizardFragment.class.getSimpleName());
    this.flow = onSetup();
    if (this.flow == null)
      throw new IllegalArgumentException("Error setting up the Wizard's flow. You must override WizardFragment#onSetup and use WizardFlow.Builder to create the Wizard's flow followed by WizardFragment#super.onSetup(flow)");
    this.contract = paramActivity;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903120, paramViewGroup, false);
  }

  public void onDetach()
  {
    super.onDetach();
    this.contract = null;
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.flow.persistFlow(paramBundle);
  }

  public abstract WizardFlow onSetup();

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.viewPager = ((BaseWizardPager)paramView.findViewById(2131493338));
    if (paramBundle != null)
      this.flow.loadFlow(paramBundle);
    CirclePageIndicator localCirclePageIndicator = (CirclePageIndicator)getView().findViewById(2131493341);
    this.wizard = new Wizard(this.flow, this, this.viewPager, getChildFragmentManager(), localCirclePageIndicator);
  }

  public void onWizardComplete()
  {
    Log.d(TAG, "Wizard Completed");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.wizards.WizardFragment
 * JD-Core Version:    0.6.2
 */