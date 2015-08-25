package com.segment.analytics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.segment.analytics.internal.Utils;
import com.segment.analytics.internal.Utils.NullableConcurrentHashMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class AnalyticsContext extends ValueMap
{
  private static final String APP_BUILD_KEY = "build";
  private static final String APP_KEY = "app";
  private static final String APP_NAMESPACE_KEY = "namespace";
  private static final String APP_NAME_KEY = "name";
  private static final String APP_VERSION_KEY = "version";
  private static final String CAMPAIGN_KEY = "campaign";
  private static final String DEVICE_KEY = "device";
  private static final String LIBRARY_KEY = "library";
  private static final String LIBRARY_NAME_KEY = "name";
  private static final String LIBRARY_VERSION_KEY = "version";
  private static final String LOCALE_KEY = "locale";
  private static final String LOCATION_KEY = "location";
  private static final String NETWORK_BLUETOOTH_KEY = "bluetooth";
  private static final String NETWORK_CARRIER_KEY = "carrier";
  private static final String NETWORK_CELLULAR_KEY = "cellular";
  private static final String NETWORK_KEY = "network";
  private static final String NETWORK_WIFI_KEY = "wifi";
  private static final String OS_KEY = "os";
  private static final String OS_NAME_KEY = "name";
  private static final String OS_VERSION_KEY = "version";
  private static final String REFERRER_KEY = "referrer";
  private static final String SCREEN_DENSITY_KEY = "density";
  private static final String SCREEN_HEIGHT_KEY = "height";
  private static final String SCREEN_KEY = "screen";
  private static final String SCREEN_WIDTH_KEY = "width";
  private static final String TIMEZONE_KEY = "timezone";
  private static final String TRAITS_KEY = "traits";
  private static final String USER_AGENT_KEY = "userAgent";

  AnalyticsContext(Map<String, Object> paramMap)
  {
    super(paramMap);
  }

  static AnalyticsContext create(Context paramContext, Traits paramTraits)
  {
    try
    {
      AnalyticsContext localAnalyticsContext = new AnalyticsContext(new Utils.NullableConcurrentHashMap());
      localAnalyticsContext.putApp(paramContext);
      localAnalyticsContext.putDevice(paramContext);
      localAnalyticsContext.putLibrary();
      localAnalyticsContext.put("locale", Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry());
      localAnalyticsContext.putNetwork(paramContext);
      localAnalyticsContext.putOs();
      localAnalyticsContext.putScreen(paramContext);
      putUndefinedIfNull(localAnalyticsContext, "userAgent", System.getProperty("http.agent"));
      putUndefinedIfNull(localAnalyticsContext, "timezone", TimeZone.getDefault().getID());
      localAnalyticsContext.setTraits(paramTraits);
      return localAnalyticsContext;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  static void putUndefinedIfNull(Map<String, Object> paramMap, String paramString, CharSequence paramCharSequence)
  {
    if (Utils.isNullOrEmpty(paramCharSequence))
    {
      paramMap.put(paramString, "undefined");
      return;
    }
    paramMap.put(paramString, paramCharSequence);
  }

  void attachAdvertisingId(Context paramContext)
  {
    if (Utils.isOnClassPath("com.google.android.gms.analytics.GoogleAnalytics"))
      new GetAdvertisingIdTask(this).execute(new Context[] { paramContext });
  }

  public Campaign campaign()
  {
    return (Campaign)getValueMap("campaign", Campaign.class);
  }

  public Device device()
  {
    return (Device)getValueMap("device", Device.class);
  }

  public Location location()
  {
    return (Location)getValueMap("location", Location.class);
  }

  void putApp(Context paramContext)
  {
    try
    {
      PackageManager localPackageManager = paramContext.getPackageManager();
      PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0);
      Map localMap = Utils.createMap();
      putUndefinedIfNull(localMap, "name", localPackageInfo.applicationInfo.loadLabel(localPackageManager));
      putUndefinedIfNull(localMap, "version", localPackageInfo.versionName);
      putUndefinedIfNull(localMap, "namespace", localPackageInfo.packageName);
      localMap.put("build", Integer.valueOf(localPackageInfo.versionCode));
      put("app", localMap);
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
  }

  public AnalyticsContext putCampaign(Campaign paramCampaign)
  {
    return putValue("campaign", paramCampaign);
  }

  void putDevice(Context paramContext)
  {
    Device localDevice = new Device(null);
    localDevice.put("id", Utils.getDeviceId(paramContext));
    localDevice.put("manufacturer", Build.MANUFACTURER);
    localDevice.put("model", Build.MODEL);
    localDevice.put("name", Build.DEVICE);
    put("device", localDevice);
  }

  public AnalyticsContext putDeviceToken(String paramString)
  {
    device().putDeviceToken(paramString);
    return this;
  }

  void putLibrary()
  {
    Map localMap = Utils.createMap();
    localMap.put("name", "analytics-android");
    localMap.put("version", "3.1.2");
    put("library", localMap);
  }

  public AnalyticsContext putLocation(Location paramLocation)
  {
    return putValue("location", paramLocation);
  }

  void putNetwork(Context paramContext)
  {
    int i = 1;
    Map localMap = Utils.createMap();
    if (Utils.hasPermission(paramContext, "android.permission.ACCESS_NETWORK_STATE"))
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)Utils.getSystemService(paramContext, "connectivity");
      if (localConnectivityManager != null)
      {
        NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(i);
        if ((localNetworkInfo1 == null) || (!localNetworkInfo1.isConnected()))
          break label184;
        int k = i;
        localMap.put("wifi", Boolean.valueOf(k));
        NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(7);
        if ((localNetworkInfo2 == null) || (!localNetworkInfo2.isConnected()))
          break label190;
        int n = i;
        label96: localMap.put("bluetooth", Boolean.valueOf(n));
        NetworkInfo localNetworkInfo3 = localConnectivityManager.getNetworkInfo(0);
        if ((localNetworkInfo3 == null) || (!localNetworkInfo3.isConnected()))
          break label196;
        label131: localMap.put("cellular", Boolean.valueOf(i));
      }
    }
    TelephonyManager localTelephonyManager = (TelephonyManager)Utils.getSystemService(paramContext, "phone");
    if (localTelephonyManager != null)
      localMap.put("carrier", localTelephonyManager.getNetworkOperatorName());
    while (true)
    {
      put("network", localMap);
      return;
      label184: int m = 0;
      break;
      label190: int i1 = 0;
      break label96;
      label196: int j = 0;
      break label131;
      localMap.put("carrier", "unknown");
    }
  }

  void putOs()
  {
    Map localMap = Utils.createMap();
    localMap.put("name", "Android");
    localMap.put("version", Build.VERSION.RELEASE);
    put("os", localMap);
  }

  public AnalyticsContext putReferrer(Referrer paramReferrer)
  {
    return putValue("referrer", paramReferrer);
  }

  void putScreen(Context paramContext)
  {
    Map localMap = Utils.createMap();
    Display localDisplay = ((WindowManager)Utils.getSystemService(paramContext, "window")).getDefaultDisplay();
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localDisplay.getMetrics(localDisplayMetrics);
    localMap.put("density", Float.valueOf(localDisplayMetrics.density));
    localMap.put("height", Integer.valueOf(localDisplayMetrics.heightPixels));
    localMap.put("width", Integer.valueOf(localDisplayMetrics.widthPixels));
    put("screen", localMap);
  }

  public AnalyticsContext putValue(String paramString, Object paramObject)
  {
    super.putValue(paramString, paramObject);
    return this;
  }

  void setTraits(Traits paramTraits)
  {
    put("traits", paramTraits.unmodifiableCopy());
  }

  public Traits traits()
  {
    return (Traits)getValueMap("traits", Traits.class);
  }

  public AnalyticsContext unmodifiableCopy()
  {
    return new AnalyticsContext(Collections.unmodifiableMap(new LinkedHashMap(this)));
  }

  public static class Campaign extends ValueMap
  {
    private static final String CAMPAIGN_CONTENT_KEY = "content";
    private static final String CAMPAIGN_MEDIUM_KEY = "medium";
    private static final String CAMPAIGN_NAME_KEY = "name";
    private static final String CAMPAIGN_SOURCE_KEY = "source";
    private static final String CAMPAIGN_TERM_KEY = "term";

    public Campaign()
    {
    }

    private Campaign(Map<String, Object> paramMap)
    {
      super();
    }

    public String content()
    {
      return getString("content");
    }

    public String medium()
    {
      return getString("medium");
    }

    public String name()
    {
      return getString("name");
    }

    public Campaign putContent(String paramString)
    {
      return putValue("content", paramString);
    }

    public Campaign putMedium(String paramString)
    {
      return putValue("medium", paramString);
    }

    public Campaign putName(String paramString)
    {
      return putValue("name", paramString);
    }

    public Campaign putSource(String paramString)
    {
      return putValue("source", paramString);
    }

    public Campaign putTerm(String paramString)
    {
      return putValue("term", paramString);
    }

    public Campaign putValue(String paramString, Object paramObject)
    {
      super.putValue(paramString, paramObject);
      return this;
    }

    public String source()
    {
      return getString("source");
    }

    public String tern()
    {
      return getString("term");
    }
  }

  public static class Device extends ValueMap
  {
    private static final String DEVICE_ADVERTISING_ID_KEY = "advertisingId";
    private static final String DEVICE_AD_TRACKING_ENABLED_KEY = "adTrackingEnabled";
    private static final String DEVICE_ID_KEY = "id";
    private static final String DEVICE_MANUFACTURER_KEY = "manufacturer";
    private static final String DEVICE_MODEL_KEY = "model";
    private static final String DEVICE_NAME_KEY = "name";
    private static final String DEVICE_TOKEN_KEY = "token";

    private Device()
    {
    }

    private Device(Map<String, Object> paramMap)
    {
      super();
    }

    void putAdvertisingInfo(String paramString, boolean paramBoolean)
    {
      put("advertisingId", paramString);
      put("adTrackingEnabled", Boolean.valueOf(paramBoolean));
    }

    public Device putDeviceToken(String paramString)
    {
      return putValue("token", paramString);
    }

    public Device putValue(String paramString, Object paramObject)
    {
      super.putValue(paramString, paramObject);
      return this;
    }
  }

  public static class Location extends ValueMap
  {
    private static final String LOCATION_LATITUDE_KEY = "latitude";
    private static final String LOCATION_LONGITUDE_KEY = "longitude";
    private static final String LOCATION_SPEED_KEY = "speed";

    public Location()
    {
    }

    private Location(Map<String, Object> paramMap)
    {
      super();
    }

    public double latitude()
    {
      return getDouble("latitude", 0.0D);
    }

    public double longitude()
    {
      return getDouble("longitude", 0.0D);
    }

    public Location putLatitude(double paramDouble)
    {
      return putValue("latitude", Double.valueOf(paramDouble));
    }

    public Location putLongitude(double paramDouble)
    {
      return putValue("longitude", Double.valueOf(paramDouble));
    }

    public Location putSpeed(double paramDouble)
    {
      return putValue("speed", Double.valueOf(paramDouble));
    }

    public Location putValue(String paramString, Object paramObject)
    {
      super.putValue(paramString, paramObject);
      return this;
    }

    public double speed()
    {
      return getDouble("speed", 0.0D);
    }
  }

  public static class Referrer extends ValueMap
  {
    private static final String REFERRER_ID_KEY = "id";
    private static final String REFERRER_LINK_KEY = "link";
    private static final String REFERRER_NAME_KEY = "name";
    private static final String REFERRER_TYPE_KEY = "type";
    private static final String REFERRER_URL_KEY = "url";

    public Referrer()
    {
    }

    public Referrer(Map<String, Object> paramMap)
    {
      super();
    }

    public String id()
    {
      return getString("id");
    }

    public String link()
    {
      return getString("link");
    }

    public String name()
    {
      return getString("name");
    }

    public Referrer putId(String paramString)
    {
      return putValue("id", paramString);
    }

    public Referrer putLink(String paramString)
    {
      return putValue("link", paramString);
    }

    public Referrer putName(String paramString)
    {
      return putValue("name", paramString);
    }

    public Referrer putTerm(String paramString)
    {
      return putValue("url", paramString);
    }

    public Referrer putType(String paramString)
    {
      return putValue("type", paramString);
    }

    public Referrer putValue(String paramString, Object paramObject)
    {
      super.putValue(paramString, paramObject);
      return this;
    }

    public String type()
    {
      return getString("type");
    }

    public String url()
    {
      return getString("url");
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.AnalyticsContext
 * JD-Core Version:    0.6.2
 */