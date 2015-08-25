package io.fabric.sdk.android.services.network;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.TreeMap;

public final class UrlUtils
{
  public static final String UTF8 = "UTF8";

  public static TreeMap<String, String> getQueryParams(String paramString, boolean paramBoolean)
  {
    TreeMap localTreeMap = new TreeMap();
    if (paramString == null)
      return localTreeMap;
    String[] arrayOfString1 = paramString.split("&");
    int i = arrayOfString1.length;
    int j = 0;
    label28: String[] arrayOfString2;
    if (j < i)
    {
      arrayOfString2 = arrayOfString1[j].split("=");
      if (arrayOfString2.length != 2)
        break label98;
      if (!paramBoolean)
        break label82;
      localTreeMap.put(urlDecode(arrayOfString2[0]), urlDecode(arrayOfString2[1]));
    }
    while (true)
    {
      j++;
      break label28;
      break;
      label82: localTreeMap.put(arrayOfString2[0], arrayOfString2[1]);
      continue;
      label98: if (!TextUtils.isEmpty(arrayOfString2[0]))
        if (paramBoolean)
          localTreeMap.put(urlDecode(arrayOfString2[0]), "");
        else
          localTreeMap.put(arrayOfString2[0], "");
    }
  }

  public static TreeMap<String, String> getQueryParams(URI paramURI, boolean paramBoolean)
  {
    return getQueryParams(paramURI.getRawQuery(), paramBoolean);
  }

  public static String percentEncode(String paramString)
  {
    if (paramString == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    String str = urlEncode(paramString);
    int i = str.length();
    int j = 0;
    if (j < i)
    {
      char c = str.charAt(j);
      if (c == '*')
        localStringBuilder.append("%2A");
      while (true)
      {
        j++;
        break;
        if (c == '+')
        {
          localStringBuilder.append("%20");
        }
        else if ((c == '%') && (j + 2 < i) && (str.charAt(j + 1) == '7') && (str.charAt(j + 2) == 'E'))
        {
          localStringBuilder.append('~');
          j += 2;
        }
        else
        {
          localStringBuilder.append(c);
        }
      }
    }
    return localStringBuilder.toString();
  }

  public static String urlDecode(String paramString)
  {
    if (paramString == null)
      return "";
    try
    {
      String str = URLDecoder.decode(paramString, "UTF8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
    }
  }

  public static String urlEncode(String paramString)
  {
    if (paramString == null)
      return "";
    try
    {
      String str = URLEncoder.encode(paramString, "UTF8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.network.UrlUtils
 * JD-Core Version:    0.6.2
 */