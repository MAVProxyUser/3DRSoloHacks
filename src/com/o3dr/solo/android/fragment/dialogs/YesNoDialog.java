package com.o3dr.solo.android.fragment.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog.Builder;

public class YesNoDialog extends DialogFragment
{
  protected static final String EXTRA_MESSAGE = "message";
  protected static final String EXTRA_NEGATIVE_LABEL = "negative_label";
  protected static final String EXTRA_POSITIVE_LABEL = "positive_label";
  protected static final String EXTRA_TITLE = "title";
  protected Listener mListener;

  public static YesNoDialog newInstance(Context paramContext, String paramString1, String paramString2, Listener paramListener)
  {
    YesNoDialog localYesNoDialog = new YesNoDialog();
    Bundle localBundle = new Bundle();
    localBundle.putString("title", paramString1);
    localBundle.putString("message", paramString2);
    localBundle.putString("positive_label", paramContext.getString(17039379));
    localBundle.putString("negative_label", paramContext.getString(17039369));
    localYesNoDialog.setArguments(localBundle);
    localYesNoDialog.mListener = paramListener;
    return localYesNoDialog;
  }

  public Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    String str1 = localBundle.getString("title", "");
    String str2 = localBundle.getString("message", "");
    String str3 = localBundle.getString("positive_label", getString(17039379));
    String str4 = localBundle.getString("negative_label", getString(17039369));
    return new AlertDialog.Builder(getActivity()).setTitle(str1).setMessage(str2).setPositiveButton(str3, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        YesNoDialog.this.mListener.onYes();
        YesNoDialog.this.dismiss();
      }
    }).setNegativeButton(str4, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        YesNoDialog.this.mListener.onNo();
        YesNoDialog.this.dismiss();
      }
    }).create();
  }

  public void onStart()
  {
    super.onStart();
    getDialog().setCanceledOnTouchOutside(true);
  }

  public static abstract interface Listener
  {
    public abstract void onNo();

    public abstract void onYes();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.dialogs.YesNoDialog
 * JD-Core Version:    0.6.2
 */