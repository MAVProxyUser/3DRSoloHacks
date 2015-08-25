package com.o3dr.solo.android.fragment.settings.solo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.o3dr.android.client.Drone;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.dialogs.YesNoDialog;
import com.o3dr.solo.android.fragment.dialogs.YesNoDialog.Listener;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.service.artoo.ArtooLinkManager;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.util.AppAnalytics;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.regex.Pattern;

public class SoloWifiFragment extends SettingsDetailFragment
{
  private static final String ACCEPTABLE_WIFI_CHARACTERS;
  public static final int MAX_SSID_LENGTH = 32;
  public static final int MIN_SSID_PASSWORD_LENGTH = 8;
  public static final String SOLO_LINK_WIFI_PREFIX = "SoloLink_";
  private static final String TAG = SoloWifiFragment.class.getSimpleName();
  private static final String WIFI_SET = "Wifi Set";
  private static final IntentFilter filter;
  private AppAnalytics appAnalytics;
  private Button applyWifiButton;
  private final YesNoDialog.Listener confirmListener = new YesNoDialog.Listener()
  {
    public void onNo()
    {
    }

    public void onYes()
    {
      if (SoloWifiFragment.this.updateTask != null)
        SoloWifiFragment.this.updateTask.cancel(true);
      SoloWifiFragment.access$102(SoloWifiFragment.this, new SoloWifiFragment.UpdateWifiTask(SoloWifiFragment.this));
      SoloWifiFragment.this.updateTask.execute(new Void[0]);
    }
  };
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case 933556871:
      }
      while (true)
        switch (i)
        {
        default:
          return;
          if (str.equals("com.o3dr.solo.android.action.SOLOLINK_WIFI_INFO_UPDATED"))
            i = 0;
          break;
        case 0:
        }
      SoloWifiFragment.this.refreshWifiInfo();
    }
  };
  private EditText soloWifiNameView;
  private EditText soloWifiPasswordView;
  private View soloWifiUpdateProgress;
  private UpdateWifiTask updateTask;

  static
  {
    ACCEPTABLE_WIFI_CHARACTERS = "[" + Pattern.quote("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890$@^`,|%;.~()/\\{}:?[]=-+_#!") + "]";
    filter = new IntentFilter();
    filter.addAction("com.o3dr.solo.android.action.SOLOLINK_WIFI_INFO_UPDATED");
  }

  private void disableButton()
  {
    if (this.applyWifiButton != null)
    {
      this.applyWifiButton.setEnabled(false);
      this.applyWifiButton.setText(2131099846);
    }
  }

  private void enableButton()
  {
    if (this.applyWifiButton != null)
    {
      this.applyWifiButton.setEnabled(true);
      this.applyWifiButton.setText(2131099847);
    }
  }

  private void enableProgressView(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.soloWifiUpdateProgress.setVisibility(0);
      return;
    }
    this.soloWifiUpdateProgress.setVisibility(8);
  }

  private void enabledSaveMenuIfValid(boolean paramBoolean)
  {
    if (this.applyWifiButton == null);
    do
    {
      String str1;
      String str2;
      do
      {
        return;
        str1 = getCurrentWifiSsid();
        str2 = getCurrentWifiPassword();
      }
      while ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)));
      if ((!this.soloWifiNameView.getText().toString().equals(str1)) || (!this.soloWifiPasswordView.getText().toString().equals(str2)))
        break;
    }
    while (!paramBoolean);
    disableButton();
    return;
    enableButton();
  }

  private String getCurrentWifiPassword()
  {
    ArtooLinkManager localArtooLinkManager = getArtooLinkManager();
    if (localArtooLinkManager == null)
      return null;
    return (String)localArtooLinkManager.getSoloLinkWifiInfo().second;
  }

  private String getCurrentWifiSsid()
  {
    ArtooLinkManager localArtooLinkManager = getArtooLinkManager();
    if (localArtooLinkManager == null)
      return null;
    return ((String)localArtooLinkManager.getSoloLinkWifiInfo().first).replace("SoloLink_", "");
  }

  private void refreshWifiInfo()
  {
    String str1 = getCurrentWifiSsid();
    String str2 = getCurrentWifiPassword();
    if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)))
      return;
    this.soloWifiNameView.setText(str1);
    this.soloWifiPasswordView.setText(str2);
  }

  private boolean updateSololinkWifi()
  {
    if (getContext() == null);
    SoloLinkManager localSoloLinkManager;
    ArtooLinkManager localArtooLinkManager;
    do
    {
      return false;
      if ((!isArtooLinkConnected()) || (!isSoloLinkConnected()))
      {
        Log.w(TAG, "Vehicle disconnected. Unable to update wifi settings.");
        return false;
      }
      localSoloLinkManager = getSoloLinkManager();
      localArtooLinkManager = getArtooLinkManager();
    }
    while ((localSoloLinkManager == null) || (localArtooLinkManager == null));
    String str = "SoloLink_" + this.soloWifiNameView.getText();
    Editable localEditable = this.soloWifiPasswordView.getText();
    Log.d(TAG, String.format(Locale.US, "Updating sololink wifi to %s with password %s", new Object[] { str, localEditable }));
    if ((localSoloLinkManager.updateSololinkWifi(str, localEditable)) && (localArtooLinkManager.updateSololinkWifi(str, localEditable)))
    {
      Log.d(TAG, "Sololink wifi update successful.");
      this.appAnalytics.track("Settings", "Wifi Set", str);
      return true;
    }
    Log.d(TAG, "Sololink wifi update failed.");
    return false;
  }

  private Boolean validateField(EditText paramEditText)
  {
    paramEditText.setText(paramEditText.getText());
    if (paramEditText.getError() != null)
      return Boolean.valueOf(false);
    return Boolean.valueOf(true);
  }

  public int getSettingDetailTitle()
  {
    return 2131099940;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903117, paramViewGroup, false);
  }

  protected void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    refreshWifiInfo();
  }

  public void onStart()
  {
    super.onStart();
    refreshWifiInfo();
    getBroadcastManager().registerReceiver(this.receiver, filter);
  }

  public void onStop()
  {
    super.onStop();
    getBroadcastManager().unregisterReceiver(this.receiver);
    if (this.updateTask != null)
    {
      this.updateTask.cancel(true);
      this.updateTask = null;
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.appAnalytics = getApplication().getAppAnalytics();
    this.soloWifiNameView = ((EditText)paramView.findViewById(2131493311));
    this.soloWifiNameView.addTextChangedListener(new TextValidator(this.soloWifiNameView)
    {
      public void validate(TextView paramAnonymousTextView, String paramAnonymousString)
      {
        if ((TextUtils.isEmpty(paramAnonymousString)) || (paramAnonymousString.length() > 32 - "SoloLink_".length()) || (paramAnonymousString.split(SoloWifiFragment.ACCEPTABLE_WIFI_CHARACTERS).length > 0))
        {
          paramAnonymousTextView.setError("Please enter a valid wifi ssid.");
          SoloWifiFragment.this.disableButton();
          return;
        }
        paramAnonymousTextView.setError(null);
        SoloWifiFragment.this.enabledSaveMenuIfValid(true);
      }
    });
    this.soloWifiPasswordView = ((EditText)paramView.findViewById(2131493315));
    this.soloWifiPasswordView.addTextChangedListener(new TextValidator(this.soloWifiPasswordView)
    {
      public void validate(TextView paramAnonymousTextView, String paramAnonymousString)
      {
        if ((TextUtils.isEmpty(paramAnonymousString)) || (paramAnonymousString.length() < 8))
        {
          paramAnonymousTextView.setError("Password must be at least 8 characters.");
          SoloWifiFragment.this.disableButton();
          return;
        }
        paramAnonymousTextView.setError(null);
        SoloWifiFragment.this.enabledSaveMenuIfValid(true);
      }
    });
    this.soloWifiUpdateProgress = paramView.findViewById(2131493317);
    this.soloWifiUpdateProgress.setVisibility(8);
    this.applyWifiButton = ((Button)paramView.findViewById(2131493316));
    this.applyWifiButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if ((SoloWifiFragment.this.validateField(SoloWifiFragment.this.soloWifiNameView).booleanValue()) && (SoloWifiFragment.this.validateField(SoloWifiFragment.this.soloWifiPasswordView).booleanValue()))
        {
          Context localContext = SoloWifiFragment.this.getContext();
          String str = SoloWifiFragment.this.getString(2131099848);
          SoloWifiFragment localSoloWifiFragment = SoloWifiFragment.this;
          Object[] arrayOfObject = new Object[3];
          arrayOfObject[0] = "SoloLink_";
          arrayOfObject[1] = SoloWifiFragment.this.soloWifiNameView.getText();
          arrayOfObject[2] = SoloWifiFragment.this.soloWifiPasswordView.getText();
          YesNoDialog.newInstance(localContext, str, localSoloWifiFragment.getString(2131099964, arrayOfObject), SoloWifiFragment.this.confirmListener).show(SoloWifiFragment.this.getChildFragmentManager(), "Wifi settings update confirm dialog");
        }
      }
    });
  }

  private static abstract class TextValidator
    implements TextWatcher
  {
    private final TextView textView;

    public TextValidator(TextView paramTextView)
    {
      this.textView = paramTextView;
    }

    public final void afterTextChanged(Editable paramEditable)
    {
      String str = this.textView.getText().toString();
      validate(this.textView, str);
    }

    public final void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public final void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public abstract void validate(TextView paramTextView, String paramString);
  }

  static class UpdateWifiTask extends AsyncTask<Void, Void, Boolean>
  {
    private final WeakReference<SoloWifiFragment> wifiFragmentRef;

    UpdateWifiTask(SoloWifiFragment paramSoloWifiFragment)
    {
      this.wifiFragmentRef = new WeakReference(paramSoloWifiFragment);
    }

    public Boolean doInBackground(Void[] paramArrayOfVoid)
    {
      SoloWifiFragment localSoloWifiFragment = (SoloWifiFragment)this.wifiFragmentRef.get();
      if ((localSoloWifiFragment != null) && (localSoloWifiFragment.updateSololinkWifi()));
      for (boolean bool = true; ; bool = false)
        return Boolean.valueOf(bool);
    }

    protected void onCancelled()
    {
      SoloWifiFragment localSoloWifiFragment = (SoloWifiFragment)this.wifiFragmentRef.get();
      if (localSoloWifiFragment != null)
        localSoloWifiFragment.enableProgressView(false);
    }

    public void onPostExecute(Boolean paramBoolean)
    {
      SoloWifiFragment localSoloWifiFragment = (SoloWifiFragment)this.wifiFragmentRef.get();
      if (localSoloWifiFragment != null)
      {
        localSoloWifiFragment.enableProgressView(false);
        if (paramBoolean.booleanValue())
          Toast.makeText(localSoloWifiFragment.getContext(), "Wi-Fi settings update successful!", 0).show();
      }
      else
      {
        return;
      }
      Toast.makeText(localSoloWifiFragment.getContext(), "Wi-Fi settings update failed!", 0).show();
    }

    public void onPreExecute()
    {
      SoloWifiFragment localSoloWifiFragment = (SoloWifiFragment)this.wifiFragmentRef.get();
      if (localSoloWifiFragment != null)
        localSoloWifiFragment.enableProgressView(true);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.solo.SoloWifiFragment
 * JD-Core Version:    0.6.2
 */