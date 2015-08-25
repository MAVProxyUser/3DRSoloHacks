package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public class InstallerPackageNameProvider
{
  private static final String NO_INSTALLER_PACKAGE_NAME = "";
  private final MemoryValueCache<String> installerPackageNameCache = new MemoryValueCache();
  private final ValueLoader<String> installerPackageNameLoader = new ValueLoader()
  {
    public String load(Context paramAnonymousContext)
      throws Exception
    {
      String str = paramAnonymousContext.getPackageManager().getInstallerPackageName(paramAnonymousContext.getPackageName());
      if (str == null)
        str = "";
      return str;
    }
  };

  public String getInstallerPackageName(Context paramContext)
  {
    try
    {
      String str = (String)this.installerPackageNameCache.get(paramContext, this.installerPackageNameLoader);
      boolean bool = "".equals(str);
      if (bool)
        str = null;
      return str;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Failed to determine installer package name", localException);
    }
    return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.InstallerPackageNameProvider
 * JD-Core Version:    0.6.2
 */