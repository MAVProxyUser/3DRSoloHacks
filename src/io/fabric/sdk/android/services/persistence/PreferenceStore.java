package io.fabric.sdk.android.services.persistence;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public abstract interface PreferenceStore
{
  public abstract SharedPreferences.Editor edit();

  public abstract SharedPreferences get();

  public abstract boolean save(SharedPreferences.Editor paramEditor);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.persistence.PreferenceStore
 * JD-Core Version:    0.6.2
 */