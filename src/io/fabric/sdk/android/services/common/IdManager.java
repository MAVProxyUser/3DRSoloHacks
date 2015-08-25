package io.fabric.sdk.android.services.common;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class IdManager
{
  public static final String APPLICATION_INSTALL_ID_FIELD = "APPLICATION_INSTALLATION_UUID";
  private static final String BAD_ANDROID_ID = "9774d56d682e549c";
  public static final String BETA_DEVICE_TOKEN_FIELD = "font_token";
  private static final String BLUETOOTH_ERROR_MESSAGE = "Utils#getBluetoothMacAddress failed, returning null. Requires prior call to BluetoothAdatpter.getDefaultAdapter() on thread that has called Looper.prepare()";
  public static final String COLLECT_DEVICE_IDENTIFIERS = "com.crashlytics.CollectDeviceIdentifiers";
  public static final String COLLECT_USER_IDENTIFIERS = "com.crashlytics.CollectUserIdentifiers";
  public static final String DEFAULT_VERSION_NAME = "0.0";
  private static final String FORWARD_SLASH_REGEX = Pattern.quote("/");
  private static final Pattern ID_PATTERN = Pattern.compile("[^\\p{Alnum}]");
  public static final String MODEL_FIELD = "model";
  public static final String OS_VERSION_FIELD = "os_version";
  private static final String PREFKEY_INSTALLATION_UUID = "crashlytics.installation.id";
  private static final String SDK_ASSETS_ROOT = ".TwitterSdk";
  private final Context appContext;
  private final String appIdentifier;
  private final String appInstallIdentifier;
  private final boolean collectHardwareIds;
  private final boolean collectUserIds;
  private final ReentrantLock installationIdLock = new ReentrantLock();
  private final InstallerPackageNameProvider installerPackageNameProvider;
  private final Collection<Kit> kits;

  public IdManager(Context paramContext, String paramString1, String paramString2, Collection<Kit> paramCollection)
  {
    if (paramContext == null)
      throw new IllegalArgumentException("appContext must not be null");
    if (paramString1 == null)
      throw new IllegalArgumentException("appIdentifier must not be null");
    if (paramCollection == null)
      throw new IllegalArgumentException("kits must not be null");
    this.appContext = paramContext;
    this.appIdentifier = paramString1;
    this.appInstallIdentifier = paramString2;
    this.kits = paramCollection;
    this.installerPackageNameProvider = new InstallerPackageNameProvider();
    this.collectHardwareIds = CommonUtils.getBooleanResourceValue(paramContext, "com.crashlytics.CollectDeviceIdentifiers", true);
    if (!this.collectHardwareIds)
      Fabric.getLogger().d("Fabric", "Device ID collection disabled for " + paramContext.getPackageName());
    this.collectUserIds = CommonUtils.getBooleanResourceValue(paramContext, "com.crashlytics.CollectUserIdentifiers", true);
    if (!this.collectUserIds)
      Fabric.getLogger().d("Fabric", "User information collection disabled for " + paramContext.getPackageName());
  }

  private void addAppInstallIdTo(JSONObject paramJSONObject)
  {
    try
    {
      paramJSONObject.put("APPLICATION_INSTALLATION_UUID".toLowerCase(Locale.US), getAppInstallIdentifier());
      return;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Could not write application id to JSON", localException);
    }
  }

  private void addDeviceIdentifiersTo(JSONObject paramJSONObject)
  {
    Iterator localIterator = getDeviceIdentifiers().entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      try
      {
        paramJSONObject.put(((DeviceIdentifierType)localEntry.getKey()).name().toLowerCase(Locale.US), localEntry.getValue());
      }
      catch (Exception localException)
      {
        Fabric.getLogger().e("Fabric", "Could not write value to JSON: " + ((DeviceIdentifierType)localEntry.getKey()).name(), localException);
      }
    }
  }

  private void addModelName(JSONObject paramJSONObject)
  {
    try
    {
      paramJSONObject.put("model", getModelName());
      return;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Could not write model to JSON", localException);
    }
  }

  private void addOsVersionTo(JSONObject paramJSONObject)
  {
    try
    {
      paramJSONObject.put("os_version", getOsVersionString());
      return;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Could not write OS version to JSON", localException);
    }
  }

  private String createInstallationUUID(SharedPreferences paramSharedPreferences)
  {
    this.installationIdLock.lock();
    try
    {
      String str = paramSharedPreferences.getString("crashlytics.installation.id", null);
      if (str == null)
      {
        str = formatId(UUID.randomUUID().toString());
        paramSharedPreferences.edit().putString("crashlytics.installation.id", str).commit();
      }
      return str;
    }
    finally
    {
      this.installationIdLock.unlock();
    }
  }

  private String formatId(String paramString)
  {
    if (paramString == null)
      return null;
    return ID_PATTERN.matcher(paramString).replaceAll("").toLowerCase(Locale.US);
  }

  private String[] getTwitterSdkAssetsList()
  {
    return new String[0];
  }

  private boolean hasPermission(String paramString)
  {
    return this.appContext.checkCallingPermission(paramString) == 0;
  }

  private void putNonNullIdInto(Map<DeviceIdentifierType, String> paramMap, DeviceIdentifierType paramDeviceIdentifierType, String paramString)
  {
    if (paramString != null)
      paramMap.put(paramDeviceIdentifierType, paramString);
  }

  private String removeForwardSlashesIn(String paramString)
  {
    return paramString.replaceAll(FORWARD_SLASH_REGEX, "");
  }

  public boolean canCollectUserIds()
  {
    return this.collectUserIds;
  }

  // ERROR //
  public String createIdHeaderValue(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_2
    //   1: ldc_w 307
    //   4: new 126	java/lang/StringBuilder
    //   7: dup
    //   8: new 164	java/lang/String
    //   11: dup
    //   12: iconst_3
    //   13: newarray char
    //   15: dup
    //   16: iconst_0
    //   17: ldc_w 308
    //   20: castore
    //   21: dup
    //   22: iconst_1
    //   23: ldc_w 309
    //   26: castore
    //   27: dup
    //   28: iconst_2
    //   29: ldc_w 310
    //   32: castore
    //   33: invokespecial 313	java/lang/String:<init>	([C)V
    //   36: invokespecial 314	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   39: invokevirtual 318	java/lang/StringBuilder:reverse	()Ljava/lang/StringBuilder;
    //   42: invokevirtual 142	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   45: invokevirtual 301	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   48: astore 4
    //   50: iconst_1
    //   51: new 126	java/lang/StringBuilder
    //   54: dup
    //   55: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   58: aload_1
    //   59: invokevirtual 133	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: aload 4
    //   64: invokevirtual 133	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: invokevirtual 142	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: invokestatic 321	io/fabric/sdk/android/services/common/CommonUtils:sha1	(Ljava/lang/String;)Ljava/lang/String;
    //   73: invokestatic 325	io/fabric/sdk/android/services/common/CommonUtils:createCipher	(ILjava/lang/String;)Ljavax/crypto/Cipher;
    //   76: astore 5
    //   78: new 173	org/json/JSONObject
    //   81: dup
    //   82: invokespecial 326	org/json/JSONObject:<init>	()V
    //   85: astore 6
    //   87: aload_0
    //   88: aload 6
    //   90: invokespecial 328	io/fabric/sdk/android/services/common/IdManager:addAppInstallIdTo	(Lorg/json/JSONObject;)V
    //   93: aload_0
    //   94: aload 6
    //   96: invokespecial 330	io/fabric/sdk/android/services/common/IdManager:addDeviceIdentifiersTo	(Lorg/json/JSONObject;)V
    //   99: aload_0
    //   100: aload 6
    //   102: invokespecial 332	io/fabric/sdk/android/services/common/IdManager:addOsVersionTo	(Lorg/json/JSONObject;)V
    //   105: aload_0
    //   106: aload 6
    //   108: invokespecial 334	io/fabric/sdk/android/services/common/IdManager:addModelName	(Lorg/json/JSONObject;)V
    //   111: ldc_w 280
    //   114: astore 7
    //   116: aload 6
    //   118: invokevirtual 338	org/json/JSONObject:length	()I
    //   121: ifle +25 -> 146
    //   124: aload 5
    //   126: aload 6
    //   128: invokevirtual 339	org/json/JSONObject:toString	()Ljava/lang/String;
    //   131: invokevirtual 343	java/lang/String:getBytes	()[B
    //   134: invokevirtual 349	javax/crypto/Cipher:doFinal	([B)[B
    //   137: invokestatic 353	io/fabric/sdk/android/services/common/CommonUtils:hexify	([B)Ljava/lang/String;
    //   140: astore 9
    //   142: aload 9
    //   144: astore 7
    //   146: aload 7
    //   148: areturn
    //   149: astore_3
    //   150: invokestatic 122	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   153: ldc 124
    //   155: ldc_w 355
    //   158: aload_3
    //   159: invokeinterface 183 4 0
    //   164: ldc_w 280
    //   167: areturn
    //   168: astore 8
    //   170: invokestatic 122	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   173: ldc 124
    //   175: ldc_w 357
    //   178: aload 8
    //   180: invokeinterface 183 4 0
    //   185: aload 7
    //   187: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	78	149	java/security/GeneralSecurityException
    //   124	142	168	java/security/GeneralSecurityException
  }

  public String getAdvertisingId()
  {
    boolean bool = this.collectHardwareIds;
    String str = null;
    if (bool)
    {
      AdvertisingInfo localAdvertisingInfo = new AdvertisingInfoProvider(this.appContext).getAdvertisingInfo();
      str = null;
      if (localAdvertisingInfo != null)
        str = localAdvertisingInfo.advertisingId;
    }
    return str;
  }

  public String getAndroidId()
  {
    boolean bool1 = this.collectHardwareIds;
    String str1 = null;
    if (bool1)
    {
      String str2 = Settings.Secure.getString(this.appContext.getContentResolver(), "android_id");
      boolean bool2 = "9774d56d682e549c".equals(str2);
      str1 = null;
      if (!bool2)
        str1 = formatId(str2);
    }
    return str1;
  }

  public String getAppIdentifier()
  {
    return this.appIdentifier;
  }

  public String getAppInstallIdentifier()
  {
    String str = this.appInstallIdentifier;
    if (str == null)
    {
      SharedPreferences localSharedPreferences = CommonUtils.getSharedPrefs(this.appContext);
      str = localSharedPreferences.getString("crashlytics.installation.id", null);
      if (str == null)
        str = createInstallationUUID(localSharedPreferences);
    }
    return str;
  }

  public String getBluetoothMacAddress()
  {
    if ((this.collectHardwareIds) && (hasPermission("android.permission.BLUETOOTH")));
    try
    {
      BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      if (localBluetoothAdapter != null)
        formatId(localBluetoothAdapter.getAddress());
      return null;
    }
    catch (Exception localException)
    {
      while (true)
        Fabric.getLogger().e("Fabric", "Utils#getBluetoothMacAddress failed, returning null. Requires prior call to BluetoothAdatpter.getDefaultAdapter() on thread that has called Looper.prepare()", localException);
    }
  }

  public Map<DeviceIdentifierType, String> getDeviceIdentifiers()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator1 = this.kits.iterator();
    while (localIterator1.hasNext())
    {
      Kit localKit = (Kit)localIterator1.next();
      if ((localKit instanceof DeviceIdentifierProvider))
      {
        Iterator localIterator2 = ((DeviceIdentifierProvider)localKit).getDeviceIdentifiers().entrySet().iterator();
        while (localIterator2.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator2.next();
          putNonNullIdInto(localHashMap, (DeviceIdentifierType)localEntry.getKey(), (String)localEntry.getValue());
        }
      }
    }
    putNonNullIdInto(localHashMap, DeviceIdentifierType.ANDROID_ID, getAndroidId());
    putNonNullIdInto(localHashMap, DeviceIdentifierType.ANDROID_DEVICE_ID, getTelephonyId());
    putNonNullIdInto(localHashMap, DeviceIdentifierType.ANDROID_SERIAL, getSerialNumber());
    putNonNullIdInto(localHashMap, DeviceIdentifierType.WIFI_MAC_ADDRESS, getWifiMacAddress());
    putNonNullIdInto(localHashMap, DeviceIdentifierType.BLUETOOTH_MAC_ADDRESS, getBluetoothMacAddress());
    putNonNullIdInto(localHashMap, DeviceIdentifierType.ANDROID_ADVERTISING_ID, getAdvertisingId());
    return Collections.unmodifiableMap(localHashMap);
  }

  public String getDeviceUUID()
  {
    String str = "";
    if (this.collectHardwareIds)
    {
      str = getAndroidId();
      if (str == null)
      {
        SharedPreferences localSharedPreferences = CommonUtils.getSharedPrefs(this.appContext);
        str = localSharedPreferences.getString("crashlytics.installation.id", null);
        if (str == null)
          str = createInstallationUUID(localSharedPreferences);
      }
    }
    return str;
  }

  public String getInstallerPackageName()
  {
    return this.installerPackageNameProvider.getInstallerPackageName(this.appContext);
  }

  public String getModelName()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = removeForwardSlashesIn(Build.MANUFACTURER);
    arrayOfObject[1] = removeForwardSlashesIn(Build.MODEL);
    return String.format(localLocale, "%s/%s", arrayOfObject);
  }

  public String getOsVersionString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = removeForwardSlashesIn(Build.VERSION.RELEASE);
    arrayOfObject[1] = removeForwardSlashesIn(Build.VERSION.INCREMENTAL);
    return String.format(localLocale, "%s/%s", arrayOfObject);
  }

  public String getSerialNumber()
  {
    if ((this.collectHardwareIds) && (Build.VERSION.SDK_INT >= 9))
      try
      {
        String str = formatId((String)Build.class.getField("SERIAL").get(null));
        return str;
      }
      catch (Exception localException)
      {
        Fabric.getLogger().e("Fabric", "Could not retrieve android.os.Build.SERIAL value", localException);
      }
    return null;
  }

  public String getTelephonyId()
  {
    if ((this.collectHardwareIds) && (hasPermission("android.permission.READ_PHONE_STATE")))
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)this.appContext.getSystemService("phone");
      if (localTelephonyManager != null)
        return formatId(localTelephonyManager.getDeviceId());
    }
    return null;
  }

  public String getWifiMacAddress()
  {
    if ((this.collectHardwareIds) && (hasPermission("android.permission.ACCESS_WIFI_STATE")))
    {
      WifiManager localWifiManager = (WifiManager)this.appContext.getSystemService("wifi");
      if (localWifiManager != null)
      {
        WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
        if (localWifiInfo != null)
          return formatId(localWifiInfo.getMacAddress());
      }
    }
    return null;
  }

  public static enum DeviceIdentifierType
  {
    public final int protobufIndex;

    static
    {
      BLUETOOTH_MAC_ADDRESS = new DeviceIdentifierType("BLUETOOTH_MAC_ADDRESS", 1, 2);
      FONT_TOKEN = new DeviceIdentifierType("FONT_TOKEN", 2, 53);
      ANDROID_ID = new DeviceIdentifierType("ANDROID_ID", 3, 100);
      ANDROID_DEVICE_ID = new DeviceIdentifierType("ANDROID_DEVICE_ID", 4, 101);
      ANDROID_SERIAL = new DeviceIdentifierType("ANDROID_SERIAL", 5, 102);
      ANDROID_ADVERTISING_ID = new DeviceIdentifierType("ANDROID_ADVERTISING_ID", 6, 103);
      DeviceIdentifierType[] arrayOfDeviceIdentifierType = new DeviceIdentifierType[7];
      arrayOfDeviceIdentifierType[0] = WIFI_MAC_ADDRESS;
      arrayOfDeviceIdentifierType[1] = BLUETOOTH_MAC_ADDRESS;
      arrayOfDeviceIdentifierType[2] = FONT_TOKEN;
      arrayOfDeviceIdentifierType[3] = ANDROID_ID;
      arrayOfDeviceIdentifierType[4] = ANDROID_DEVICE_ID;
      arrayOfDeviceIdentifierType[5] = ANDROID_SERIAL;
      arrayOfDeviceIdentifierType[6] = ANDROID_ADVERTISING_ID;
    }

    private DeviceIdentifierType(int paramInt)
    {
      this.protobufIndex = paramInt;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.IdManager
 * JD-Core Version:    0.6.2
 */