package com.o3dr.solo.android.fragment.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class ProgressDialogFragment extends DialogFragment
{
  protected static final String EXTRA_TITLE = "extra_title";
  private boolean indeterminate = true;
  private int max;
  private int progress;
  private ProgressBar progressBar;

  public static ProgressDialogFragment newInstance(String paramString)
  {
    ProgressDialogFragment localProgressDialogFragment = new ProgressDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("extra_title", paramString);
    localProgressDialogFragment.setArguments(localBundle);
    return localProgressDialogFragment;
  }

  public void dismiss()
  {
    Dialog localDialog = getDialog();
    if (localDialog != null)
      localDialog.dismiss();
  }

  public boolean isShowing()
  {
    Dialog localDialog = getDialog();
    return (localDialog != null) && (localDialog.isShowing());
  }

  public Dialog onCreateDialog(Bundle paramBundle)
  {
    FragmentActivity localFragmentActivity = getActivity();
    String str = "";
    Bundle localBundle = getArguments();
    if (localBundle != null)
      str = localBundle.getString("extra_title", str);
    View localView = localFragmentActivity.getLayoutInflater().inflate(2130903087, null);
    this.progressBar = ((ProgressBar)localView.findViewById(2131493084));
    this.progressBar.setMax(this.max);
    this.progressBar.setProgress(this.progress);
    this.progressBar.setIndeterminate(this.indeterminate);
    return new AlertDialog.Builder(localFragmentActivity).setTitle(str).setView(localView).create();
  }

  public void onStart()
  {
    super.onStart();
    getDialog().setCanceledOnTouchOutside(true);
  }

  public void setIndeterminate(boolean paramBoolean)
  {
    this.indeterminate = paramBoolean;
    if (this.progressBar != null)
      this.progressBar.setIndeterminate(paramBoolean);
  }

  public void setMax(int paramInt)
  {
    this.max = paramInt;
    if (this.progressBar != null)
      this.progressBar.setMax(paramInt);
  }

  public void setProgress(int paramInt)
  {
    this.progress = paramInt;
    if (this.progressBar != null)
      this.progressBar.setProgress(paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.dialogs.ProgressDialogFragment
 * JD-Core Version:    0.6.2
 */