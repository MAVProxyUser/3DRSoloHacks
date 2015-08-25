package com.o3dr.solo.android.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.o3dr.solo.android.fragment.wizards.TutorialStep;
import com.o3dr.solo.android.fragment.wizards.Wizard;
import com.o3dr.solo.android.fragment.wizards.WizardFlow;
import com.o3dr.solo.android.fragment.wizards.WizardFlow.Builder;
import com.o3dr.solo.android.fragment.wizards.WizardFragment;
import com.o3dr.solo.android.util.AppPreferences;

public class TutorialWizardFragment extends WizardFragment<Contract>
  implements View.OnClickListener
{
  public static final String WIZARD_SUBTITLE_KEY = "wizard_subtitle_key";
  public static final String WIZARD_TITLE_KEY = "wizard_title_key";
  private AppPreferences appPrefs;
  private ImageButton nextButton;
  private ImageButton previousButton;

  private void updateWizardControls()
  {
    ImageButton localImageButton = this.previousButton;
    if (!this.wizard.isFirstStep());
    for (boolean bool = true; ; bool = false)
    {
      localImageButton.setEnabled(bool);
      return;
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131493340:
      this.wizard.goNext();
      return;
    case 2131493339:
    }
    this.wizard.goBack();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance(true);
  }

  public WizardFlow onSetup()
  {
    Bundle localBundle1 = new Bundle();
    localBundle1.putString("wizard_title_key", getString(2131100055));
    localBundle1.putString("wizard_subtitle_key", getString(2131100054));
    Bundle localBundle2 = new Bundle();
    localBundle2.putString("wizard_title_key", getString(2131100063));
    localBundle2.putString("wizard_subtitle_key", getString(2131100062));
    Bundle localBundle3 = new Bundle();
    localBundle3.putString("wizard_title_key", getString(2131100061));
    localBundle3.putString("wizard_subtitle_key", getString(2131100060));
    Bundle localBundle4 = new Bundle();
    localBundle4.putString("wizard_title_key", getString(2131100053));
    localBundle4.putString("wizard_subtitle_key", getString(2131100052));
    Bundle localBundle5 = new Bundle();
    localBundle5.putString("wizard_title_key", getString(2131100051));
    localBundle5.putString("wizard_subtitle_key", getString(2131100050));
    Bundle localBundle6 = new Bundle();
    localBundle6.putString("wizard_title_key", getString(2131100059));
    localBundle6.putString("wizard_subtitle_key", getString(2131100058));
    Bundle localBundle7 = new Bundle();
    localBundle7.putString("wizard_title_key", getString(2131100057));
    localBundle7.putString("wizard_subtitle_key", getString(2131100056));
    return new WizardFlow.Builder().addStep(TutorialStep.class, localBundle1).addStep(TutorialStep.class, localBundle2).addStep(TutorialStep.class, localBundle3).addStep(TutorialStep.class, localBundle4).addStep(TutorialStep.class, localBundle5).addStep(TutorialStep.class, localBundle7).addStep(TutorialStep.class, localBundle6).create();
  }

  public void onStepChanged(int paramInt)
  {
    if (getContract() != null)
      ((Contract)getContract()).onPageChanged(paramInt);
    updateWizardControls();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.appPrefs = new AppPreferences(getContext());
    this.previousButton = ((ImageButton)paramView.findViewById(2131493339));
    this.previousButton.setOnClickListener(this);
    this.nextButton = ((ImageButton)paramView.findViewById(2131493340));
    this.nextButton.setOnClickListener(this);
  }

  public void onWizardComplete()
  {
    super.onWizardComplete();
    this.appPrefs.setRunWizard(false);
    if (getContract() != null)
      ((Contract)getContract()).closeWizard();
  }

  public void onWizardStarted()
  {
    if (getContract() != null)
      ((Contract)getContract()).firstPageSetup();
  }

  public static abstract interface Contract
  {
    public abstract void closeWizard();

    public abstract void firstPageSetup();

    public abstract void onPageChanged(int paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.TutorialWizardFragment
 * JD-Core Version:    0.6.2
 */