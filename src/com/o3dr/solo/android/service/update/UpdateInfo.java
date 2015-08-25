package com.o3dr.solo.android.service.update;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.o3dr.solo.android.util.AppPreferences;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class UpdateInfo
{
  private static final long DEFAULT_REFRESH_PERIOD = 10000L;
  private final String componentName;
  private final AtomicLong downloadId = new AtomicLong(-1L);
  private final AtomicReference<File> downloadedFile = new AtomicReference();
  private final AtomicBoolean isUpdateRequired = new AtomicBoolean(false);
  private String prefDownloadIdKey;
  private String prefFilePathKey;
  private String prefMd5Key;
  private String prefReleaseNotesKey;
  private String prefTimestampKey;
  private String prefUpdateRequiredFlagKey;
  private String prefUrlKey;
  private String prefVersionKey;
  private AtomicReference<String> releaseNotes = new AtomicReference();
  private final AtomicReference<String> updateMd5 = new AtomicReference();
  private final AtomicLong updateTimestamp = new AtomicLong(-1L);
  private final AtomicReference<String> updateUrl = new AtomicReference();
  private final AtomicReference<String> updateVersion = new AtomicReference();

  public UpdateInfo(String paramString)
  {
    this.componentName = paramString;
    initializePrefKeys();
  }

  private String getPrefKey(String paramString)
  {
    return this.componentName + paramString;
  }

  private void initializePrefKeys()
  {
    this.prefVersionKey = getPrefKey("_update_version");
    this.prefUrlKey = getPrefKey("_update_url");
    this.prefFilePathKey = getPrefKey("_downloaded_filepath");
    this.prefMd5Key = getPrefKey("_update_md5");
    this.prefTimestampKey = getPrefKey("_update_timestamp");
    this.prefDownloadIdKey = getPrefKey("_download_id");
    this.prefReleaseNotesKey = getPrefKey("_release_notes");
    this.prefUpdateRequiredFlagKey = getPrefKey("_is_update_required");
  }

  public String getComponentName()
  {
    return this.componentName;
  }

  public long getDownloadId()
  {
    return this.downloadId.get();
  }

  public File getDownloadedFile()
  {
    return (File)this.downloadedFile.get();
  }

  public String getUpdateMd5()
  {
    return (String)this.updateMd5.get();
  }

  public String getUpdateReleaseNotes()
  {
    return (String)this.releaseNotes.get();
  }

  public long getUpdateTimestamp()
  {
    return this.updateTimestamp.get();
  }

  public String getUpdateUrl()
  {
    return (String)this.updateUrl.get();
  }

  public String getUpdateVersion()
  {
    return (String)this.updateVersion.get();
  }

  public boolean isComplete()
  {
    File localFile = (File)this.downloadedFile.get();
    return (localFile != null) && (localFile.isFile()) && (!TextUtils.isEmpty((CharSequence)this.updateVersion.get())) && (!TextUtils.isEmpty((CharSequence)this.updateUrl.get())) && (!TextUtils.isEmpty((CharSequence)this.updateMd5.get()));
  }

  public boolean isUpdateDownloading()
  {
    return getDownloadId() != -1L;
  }

  public boolean isUpdateRequired()
  {
    return this.isUpdateRequired.get();
  }

  public void reset()
  {
    this.downloadId.set(-1L);
    this.updateTimestamp.set(-1L);
    this.updateVersion.set(null);
    this.updateUrl.set(null);
    this.updateMd5.set(null);
    this.downloadedFile.set(null);
    this.releaseNotes.set(null);
  }

  public void restoreInstance(AppPreferences paramAppPreferences)
  {
    SharedPreferences localSharedPreferences = paramAppPreferences.getPrefs();
    String str1 = localSharedPreferences.getString(this.prefVersionKey, null);
    if (!TextUtils.isEmpty(str1))
      setUpdateVersion(str1);
    String str2 = localSharedPreferences.getString(this.prefReleaseNotesKey, null);
    if (!TextUtils.isEmpty(str2))
      setUpdateReleaseNotes(str2);
    String str3 = localSharedPreferences.getString(this.prefUrlKey, null);
    if (!TextUtils.isEmpty(str3))
      setUpdateUrl(str3);
    String str4 = localSharedPreferences.getString(this.prefMd5Key, null);
    if (!TextUtils.isEmpty(str4))
      setUpdateMd5(str4);
    String str5 = localSharedPreferences.getString(this.prefFilePathKey, null);
    if (!TextUtils.isEmpty(str5))
    {
      File localFile = new File(str5);
      if (localFile.isFile())
        setDownloadedFile(localFile);
    }
    setUpdateTimestamp(localSharedPreferences.getLong(this.prefTimestampKey, -1L));
    setDownloadId(localSharedPreferences.getLong(this.prefDownloadIdKey, -1L));
    setUpdateRequired(localSharedPreferences.getBoolean(this.prefUpdateRequiredFlagKey, false));
  }

  public void saveInstance(AppPreferences paramAppPreferences)
  {
    File localFile = getDownloadedFile();
    if ((localFile == null) || (!localFile.isFile()));
    for (String str = null; ; str = localFile.getAbsolutePath())
    {
      paramAppPreferences.getPrefs().edit().putString(this.prefVersionKey, getUpdateVersion()).putString(this.prefUrlKey, getUpdateUrl()).putString(this.prefMd5Key, getUpdateMd5()).putString(this.prefFilePathKey, str).putLong(this.prefTimestampKey, getUpdateTimestamp()).putLong(this.prefDownloadIdKey, getDownloadId()).putString(this.prefReleaseNotesKey, getUpdateReleaseNotes()).putBoolean(this.prefUpdateRequiredFlagKey, isUpdateRequired()).apply();
      return;
    }
  }

  public void setDownloadId(long paramLong)
  {
    this.downloadId.set(paramLong);
  }

  public void setDownloadedFile(File paramFile)
  {
    this.downloadedFile.set(paramFile);
  }

  public void setUpdateMd5(String paramString)
  {
    this.updateMd5.set(paramString);
  }

  public void setUpdateReleaseNotes(String paramString)
  {
    this.releaseNotes.set(paramString);
  }

  public void setUpdateRequired(boolean paramBoolean)
  {
    this.isUpdateRequired.set(paramBoolean);
  }

  public void setUpdateTimestamp(long paramLong)
  {
    this.updateTimestamp.set(paramLong);
  }

  public void setUpdateUrl(String paramString)
  {
    this.updateUrl.set(paramString);
  }

  public void setUpdateVersion(String paramString)
  {
    this.updateVersion.set(paramString);
  }

  public boolean shouldRefreshInfo()
  {
    long l = getUpdateTimestamp();
    if ((l == -1L) || (System.currentTimeMillis() - l >= 10000L));
    for (int i = 1; ; i = 0)
    {
      boolean bool1;
      if ((i == 0) && (!TextUtils.isEmpty(getUpdateMd5())) && (!TextUtils.isEmpty(getUpdateVersion())))
      {
        boolean bool2 = TextUtils.isEmpty(getUpdateUrl());
        bool1 = false;
        if (!bool2);
      }
      else
      {
        bool1 = true;
      }
      return bool1;
    }
  }

  public String toString()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this.componentName;
    arrayOfObject[1] = this.updateVersion.get();
    arrayOfObject[2] = this.updateUrl.get();
    arrayOfObject[3] = this.updateMd5.get();
    return String.format("%s version %s @ %s (%s)", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.update.UpdateInfo
 * JD-Core Version:    0.6.2
 */