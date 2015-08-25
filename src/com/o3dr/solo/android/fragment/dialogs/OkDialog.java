package com.o3dr.solo.android.fragment.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog.Builder;

public class OkDialog extends DialogFragment
{
  protected static final String EXTRA_BUTTON_LABEL = "button_label";
  protected static final String EXTRA_MESSAGE = "message";
  protected static final String EXTRA_TITLE = "title";

  public static OkDialog newInstance(Context paramContext, String paramString1, String paramString2)
  {
    OkDialog localOkDialog = new OkDialog();
    Bundle localBundle = new Bundle();
    localBundle.putString("title", paramString1);
    localBundle.putString("message", paramString2);
    localBundle.putString("button_label", paramContext.getString(17039370));
    localOkDialog.setArguments(localBundle);
    return localOkDialog;
  }

  public Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    String str1 = localBundle.getString("title", "");
    String str2 = localBundle.getString("message", "");
    String str3 = localBundle.getString("button_label", getString(17039370));
    return new AlertDialog.Builder(getActivity()).setTitle(str1).setMessage(str2).setNeutralButton(str3, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        OkDialog.this.dismiss();
      }
    }).create();
  }

  public void onStart()
  {
    super.onStart();
    getDialog().setCanceledOnTouchOutside(true);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.dialogs.OkDialog
 * JD-Core Version:    0.6.2
 */