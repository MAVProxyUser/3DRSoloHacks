package com.google.android.gms.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import com.google.android.gms.common.internal.zzv;

public class SupportErrorDialogFragment extends DialogFragment
{
  private Dialog mDialog = null;
  private DialogInterface.OnCancelListener zzOK = null;

  public static SupportErrorDialogFragment newInstance(Dialog paramDialog)
  {
    return newInstance(paramDialog, null);
  }

  public static SupportErrorDialogFragment newInstance(Dialog paramDialog, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    SupportErrorDialogFragment localSupportErrorDialogFragment = new SupportErrorDialogFragment();
    Dialog localDialog = (Dialog)zzv.zzb(paramDialog, "Cannot display null dialog");
    localDialog.setOnCancelListener(null);
    localDialog.setOnDismissListener(null);
    localSupportErrorDialogFragment.mDialog = localDialog;
    if (paramOnCancelListener != null)
      localSupportErrorDialogFragment.zzOK = paramOnCancelListener;
    return localSupportErrorDialogFragment;
  }

  public void onCancel(DialogInterface paramDialogInterface)
  {
    if (this.zzOK != null)
      this.zzOK.onCancel(paramDialogInterface);
  }

  public Dialog onCreateDialog(Bundle paramBundle)
  {
    if (this.mDialog == null)
      setShowsDialog(false);
    return this.mDialog;
  }

  public void show(FragmentManager paramFragmentManager, String paramString)
  {
    super.show(paramFragmentManager, paramString);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.SupportErrorDialogFragment
 * JD-Core Version:    0.6.2
 */