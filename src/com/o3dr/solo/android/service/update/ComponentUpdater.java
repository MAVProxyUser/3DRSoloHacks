package com.o3dr.solo.android.service.update;

import android.text.TextUtils;
import android.util.Log;
import com.o3dr.solo.android.util.connection.SshConnection;
import com.o3dr.solo.android.util.connection.SshConnection.UploadListener;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.regex.Pattern;

public class ComponentUpdater
{
  public static final String ARTOO_UPDATER = "artoo_updater";
  private static final boolean DEBUG_FLAG = false;
  public static final String PIXHAWK_UPDATER = "pixhawk_updater";
  public static final String SOLO_UPDATER = "solo_updater";
  public static final String STM32_UPDATER = "stm32_updater";
  private final SshConnection sshLink;
  private final String tag;
  private SshConnection.UploadListener updateListener;
  private final String versionFile;

  public ComponentUpdater(String paramString1, SshConnection paramSshConnection, String paramString2)
  {
    this.tag = paramString1;
    this.sshLink = paramSshConnection;
    this.versionFile = paramString2;
  }

  private static int compare(String paramString1, String paramString2)
  {
    return normalisedVersion(paramString1).compareTo(normalisedVersion(paramString2));
  }

  private int compareVersion(String paramString1, String paramString2)
  {
    return compare(paramString1, paramString2);
  }

  private boolean executeUpdateCommand(String paramString)
  {
    if (!shouldContinueUpdate())
      return false;
    try
    {
      this.sshLink.execute(paramString);
      return true;
    }
    catch (IOException localIOException)
    {
      Log.e(this.tag, "Unable to execute command: " + paramString, localIOException);
    }
    return false;
  }

  private boolean executeUpdateFileUpload(File paramFile, String paramString)
  {
    if (!shouldContinueUpdate())
      return false;
    try
    {
      if (!this.sshLink.uploadFile(paramFile, "/log/updates/" + paramString, this.updateListener))
      {
        Log.w(this.tag, "Unable to upload the update file to the vehicle.");
        return false;
      }
    }
    catch (IOException localIOException)
    {
      Log.e(this.tag, "Unable to upload the update file to the vehicle.", localIOException);
      return false;
    }
    return true;
  }

  public static String normalisedVersion(String paramString)
  {
    return normalisedVersion(paramString, ".", 4);
  }

  public static String normalisedVersion(String paramString1, String paramString2, int paramInt)
  {
    String[] arrayOfString = Pattern.compile(paramString2, 16).split(paramString1);
    StringBuilder localStringBuilder = new StringBuilder();
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = arrayOfString[j];
      localStringBuilder.append(String.format("%" + paramInt + 's', new Object[] { str }));
    }
    return localStringBuilder.toString();
  }

  private boolean shouldContinueUpdate()
  {
    return (this.updateListener == null) || (this.updateListener.shouldContinueUpload());
  }

  boolean applyUpdate()
  {
    Log.d(this.tag, "Applying update.");
    int i;
    try
    {
      if (!executeUpdateCommand("touch /log/updates/UPDATE"))
        return false;
      String str = this.tag;
      i = -1;
      switch (str.hashCode())
      {
      case -126038003:
        if (!str.equals("artoo_updater"))
          break;
        i = 0;
        break;
      case -617271959:
        if (!str.equals("solo_updater"))
          break;
        i = 1;
        break;
      case 1367910198:
        if (!str.equals("pixhawk_updater"))
          break;
        i = 2;
        break;
      case 626046485:
        if (!str.equals("stm32_updater"))
          break;
        i = 3;
        break;
        this.sshLink.execute("sololink_config --update-apply sololink");
      }
    }
    catch (IOException localIOException)
    {
      Log.e(this.tag, "Unable to apply update!", localIOException);
      return false;
    }
    this.sshLink.execute("sololink_config --update-apply pixhawk");
    break label224;
    this.sshLink.execute("sololink_config --update-apply artoo");
    break label224;
    switch (i)
    {
    case 0:
    case 1:
    case 2:
    case 3:
    }
    label224: return true;
  }

  boolean checkForUpdate(String paramString)
  {
    Log.d(this.tag, "Checking for update.");
    if (TextUtils.isEmpty(paramString))
    {
      Log.d(this.tag, "Invalid update version.");
      return false;
    }
    return checkForUpdate(paramString, retrieveCurrentVersion());
  }

  boolean checkForUpdate(String paramString1, String paramString2)
  {
    Log.d(this.tag, "Checking for update.");
    if (TextUtils.isEmpty(paramString1))
    {
      Log.d(this.tag, "Invalid update version.");
      return false;
    }
    if (TextUtils.isEmpty(paramString2))
    {
      Log.d(this.tag, "Invalid current version.");
      return false;
    }
    int i = compareVersion(paramString1, paramString2);
    if (i < 0)
    {
      Log.d(this.tag, String.format(Locale.US, "Update version is older than the current version: U %s, C %s", new Object[] { paramString1, paramString2 }));
      return false;
    }
    if (i == 0)
    {
      Log.d(this.tag, String.format(Locale.US, "Update version is the same as the current version: U %s, C %s", new Object[] { paramString1, paramString2 }));
      return false;
    }
    Log.d(this.tag, String.format(Locale.US, "Update version is greater than the current version: U %s, C %s", new Object[] { paramString1, paramString2 }));
    return true;
  }

  String getTag()
  {
    return this.tag;
  }

  String retrieveCurrentVersion()
  {
    try
    {
      String str1 = this.sshLink.execute("cat " + this.versionFile);
      if (TextUtils.isEmpty(str1))
      {
        Log.d(this.tag, "No version file was found");
        return "";
      }
      String str2 = str1.split("\n")[0];
      return str2;
    }
    catch (IOException localIOException)
    {
      Log.e(this.tag, "Unable to retrieve the current version.", localIOException);
    }
    return null;
  }

  void setUpdateListener(SshConnection.UploadListener paramUploadListener)
  {
    this.updateListener = paramUploadListener;
  }

  boolean transferUpdate(UpdateInfo paramUpdateInfo)
  {
    Log.d(this.tag, "Transferring update.");
    if ((paramUpdateInfo == null) || (!paramUpdateInfo.isComplete()))
    {
      Log.d(this.tag, "Missing update data.");
      return false;
    }
    String str1 = this.tag;
    int i = -1;
    switch (str1.hashCode())
    {
    default:
      label88: switch (i)
      {
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      }
      break;
    case -126038003:
    case -617271959:
    case 1367910198:
    case 626046485:
    }
    do
    {
      do
      {
        do
        {
          File localFile = paramUpdateInfo.getDownloadedFile();
          String str2 = localFile.getName();
          if ((!executeUpdateFileUpload(localFile, str2)) || (!executeUpdateCommand("echo \"" + paramUpdateInfo.getUpdateMd5() + "  " + str2 + "\" > /log/updates/" + str2 + ".md5")))
            break;
          return true;
          if (!str1.equals("artoo_updater"))
            break label88;
          i = 0;
          break label88;
          if (!str1.equals("solo_updater"))
            break label88;
          i = 1;
          break label88;
          if (!str1.equals("pixhawk_updater"))
            break label88;
          i = 2;
          break label88;
          if (!str1.equals("stm32_updater"))
            break label88;
          i = 3;
          break label88;
        }
        while (executeUpdateCommand("sololink_config --update-prepare sololink"));
        return false;
      }
      while (executeUpdateCommand("sololink_config --update-prepare pixhawk"));
      return false;
    }
    while (executeUpdateCommand("sololink_config --update-prepare artoo"));
    return false;
  }

  @Retention(RetentionPolicy.SOURCE)
  public static @interface ComponentToUpdate
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.update.ComponentUpdater
 * JD-Core Version:    0.6.2
 */