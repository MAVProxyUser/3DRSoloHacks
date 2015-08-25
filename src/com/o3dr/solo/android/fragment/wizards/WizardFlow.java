package com.o3dr.solo.android.fragment.wizards;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class WizardFlow
{
  private static final String KEY_WIZARD_FLOW = "key_wizard_flow";
  private ArrayList<StepMetaData> steps;

  private WizardFlow(ArrayList<StepMetaData> paramArrayList)
  {
    this.steps = paramArrayList;
  }

  public List<StepMetaData> getSteps()
  {
    return this.steps;
  }

  public int getStepsCount()
  {
    return this.steps.size();
  }

  final void loadFlow(Bundle paramBundle)
  {
    this.steps = paramBundle.getParcelableArrayList("key_wizard_flow");
  }

  final void persistFlow(Bundle paramBundle)
  {
    paramBundle.putParcelableArrayList("key_wizard_flow", this.steps);
  }

  public static class Builder
  {
    private ArrayList<WizardFlow.StepMetaData> wizardSteps = new ArrayList();

    public Builder addStep(Class<? extends TutorialStep> paramClass, Bundle paramBundle)
    {
      this.wizardSteps.add(new WizardFlow.StepMetaData(paramClass, paramBundle, null));
      return this;
    }

    public WizardFlow create()
    {
      if (this.wizardSteps.size() > 0)
        return new WizardFlow(this.wizardSteps, null);
      throw new RuntimeException("Cannot create WizardFlow. No step has been added! Call Builder#addStep(stepClass) to add steps to the wizard flow.");
    }
  }

  public static class StepMetaData
    implements Parcelable
  {
    public static final Parcelable.Creator<StepMetaData> CREATOR = new Parcelable.Creator()
    {
      public WizardFlow.StepMetaData createFromParcel(Parcel paramAnonymousParcel)
      {
        return new WizardFlow.StepMetaData(paramAnonymousParcel, null);
      }

      public WizardFlow.StepMetaData[] newArray(int paramAnonymousInt)
      {
        return new WizardFlow.StepMetaData[paramAnonymousInt];
      }
    };
    private Bundle arguments;
    private boolean completed;
    private Class<? extends TutorialStep> stepClass;

    private StepMetaData(Parcel paramParcel)
    {
      if (paramParcel.readByte() != 0);
      for (boolean bool = true; ; bool = false)
      {
        this.completed = bool;
        this.stepClass = ((Class)paramParcel.readSerializable());
        this.arguments = paramParcel.readBundle();
        return;
      }
    }

    private StepMetaData(Class<? extends TutorialStep> paramClass, Bundle paramBundle)
    {
      this.stepClass = paramClass;
      this.arguments = paramBundle;
    }

    public int describeContents()
    {
      return 0;
    }

    public Bundle getArguments()
    {
      return this.arguments;
    }

    public Class<? extends TutorialStep> getStepClass()
    {
      return this.stepClass;
    }

    public boolean isCompleted()
    {
      return this.completed;
    }

    public void setCompleted(boolean paramBoolean)
    {
      this.completed = paramBoolean;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      if (this.completed);
      for (byte b = 1; ; b = 0)
      {
        paramParcel.writeByte(b);
        paramParcel.writeSerializable(this.stepClass);
        paramParcel.writeBundle(this.arguments);
        return;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.wizards.WizardFlow
 * JD-Core Version:    0.6.2
 */