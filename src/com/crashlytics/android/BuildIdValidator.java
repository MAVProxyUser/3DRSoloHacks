package com.crashlytics.android;

import android.util.Log;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;

class BuildIdValidator
{
  private static final String MESSAGE = "This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.";
  private final String buildId;
  private final boolean requiringBuildId;

  public BuildIdValidator(String paramString, boolean paramBoolean)
  {
    this.buildId = paramString;
    this.requiringBuildId = paramBoolean;
  }

  protected String getMessage(String paramString1, String paramString2)
  {
    return "This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.";
  }

  public void validate(String paramString1, String paramString2)
  {
    if ((CommonUtils.isNullOrEmpty(this.buildId)) && (this.requiringBuildId))
    {
      String str = getMessage(paramString1, paramString2);
      Log.e("Fabric", ".");
      Log.e("Fabric", ".     |  | ");
      Log.e("Fabric", ".     |  |");
      Log.e("Fabric", ".     |  |");
      Log.e("Fabric", ".   \\ |  | /");
      Log.e("Fabric", ".    \\    /");
      Log.e("Fabric", ".     \\  /");
      Log.e("Fabric", ".      \\/");
      Log.e("Fabric", ".");
      Log.e("Fabric", str);
      Log.e("Fabric", ".");
      Log.e("Fabric", ".      /\\");
      Log.e("Fabric", ".     /  \\");
      Log.e("Fabric", ".    /    \\");
      Log.e("Fabric", ".   / |  | \\");
      Log.e("Fabric", ".     |  |");
      Log.e("Fabric", ".     |  |");
      Log.e("Fabric", ".     |  |");
      Log.e("Fabric", ".");
      throw new CrashlyticsMissingDependencyException(str);
    }
    if (!this.requiringBuildId)
      Fabric.getLogger().d("Fabric", "Configured not to require a build ID.");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.BuildIdValidator
 * JD-Core Version:    0.6.2
 */