package com.google.android.gms.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import com.google.android.gms.common.internal.zzv;

public class ErrorDialogFragment extends DialogFragment
{
  private Dialog mDialog = null;
  private DialogInterface.OnCancelListener zzOK = null;

  public static ErrorDialogFragment newInstance(Dialog paramDialog)
  {
    return newInstance(paramDialog, null);
  }

  public static ErrorDialogFragment newInstance(Dialog paramDialog, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    ErrorDialogFragment localErrorDialogFragment = new ErrorDialogFragment();
    Dialog localDialog = (Dialog)zzv.zzb(paramDialog, "Cannot display null dialog");
    localDialog.setOnCancelListener(null);
    localDialog.setOnDismissListener(null);
    localErrorDialogFragment.mDialog = localDialog;
    if (paramOnCancelListener != null)
      localErrorDialogFragment.zzOK = paramOnCancelListener;
    return localErrorDialogFragment;
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
 * Qualified Name:     com.google.android.gms.common.ErrorDialogFragment
 * JD-Core Version:    0.6.2
 */