package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzhz
{
  private static final Pattern zzVD = Pattern.compile("\\\\.");
  private static final Pattern zzVE = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

  public static String zzbY(String paramString)
  {
    Matcher localMatcher;
    StringBuffer localStringBuffer;
    if (!TextUtils.isEmpty(paramString))
    {
      localMatcher = zzVE.matcher(paramString);
      localStringBuffer = null;
      while (localMatcher.find())
      {
        if (localStringBuffer == null)
          localStringBuffer = new StringBuffer();
        switch (localMatcher.group().charAt(0))
        {
        default:
          break;
        case '\b':
          localMatcher.appendReplacement(localStringBuffer, "\\\\b");
          break;
        case '"':
          localMatcher.appendReplacement(localStringBuffer, "\\\\\\\"");
          break;
        case '\\':
          localMatcher.appendReplacement(localStringBuffer, "\\\\\\\\");
          break;
        case '/':
          localMatcher.appendReplacement(localStringBuffer, "\\\\/");
          break;
        case '\f':
          localMatcher.appendReplacement(localStringBuffer, "\\\\f");
          break;
        case '\n':
          localMatcher.appendReplacement(localStringBuffer, "\\\\n");
          break;
        case '\r':
          localMatcher.appendReplacement(localStringBuffer, "\\\\r");
          break;
        case '\t':
          localMatcher.appendReplacement(localStringBuffer, "\\\\t");
        }
      }
      if (localStringBuffer != null);
    }
    else
    {
      return paramString;
    }
    localMatcher.appendTail(localStringBuffer);
    return localStringBuffer.toString();
  }

  public static boolean zzd(Object paramObject1, Object paramObject2)
  {
    boolean bool1;
    if ((paramObject1 == null) && (paramObject2 == null))
      bool1 = true;
    while (true)
    {
      return bool1;
      bool1 = false;
      if (paramObject1 != null)
      {
        bool1 = false;
        if (paramObject2 != null)
        {
          JSONObject localJSONObject1;
          JSONObject localJSONObject2;
          label80: String str;
          if (((paramObject1 instanceof JSONObject)) && ((paramObject2 instanceof JSONObject)))
          {
            localJSONObject1 = (JSONObject)paramObject1;
            localJSONObject2 = (JSONObject)paramObject2;
            int m = localJSONObject1.length();
            int n = localJSONObject2.length();
            bool1 = false;
            if (m == n)
            {
              Iterator localIterator = localJSONObject1.keys();
              if (localIterator.hasNext())
              {
                str = (String)localIterator.next();
                boolean bool3 = localJSONObject2.has(str);
                bool1 = false;
                if (!bool3)
                  continue;
              }
            }
          }
          else
          {
            try
            {
              boolean bool4 = zzd(localJSONObject1.get(str), localJSONObject2.get(str));
              if (bool4)
                break label80;
              return false;
              return true;
              JSONArray localJSONArray1;
              JSONArray localJSONArray2;
              int k;
              if (((paramObject1 instanceof JSONArray)) && ((paramObject2 instanceof JSONArray)))
              {
                localJSONArray1 = (JSONArray)paramObject1;
                localJSONArray2 = (JSONArray)paramObject2;
                int i = localJSONArray1.length();
                int j = localJSONArray2.length();
                bool1 = false;
                if (i == j)
                {
                  k = 0;
                  label196: if (k >= localJSONArray1.length());
                }
              }
              else
              {
                try
                {
                  boolean bool2 = zzd(localJSONArray1.get(k), localJSONArray2.get(k));
                  bool1 = false;
                  if (bool2)
                  {
                    k++;
                    break label196;
                    return true;
                    return paramObject1.equals(paramObject2);
                  }
                }
                catch (JSONException localJSONException1)
                {
                  return false;
                }
              }
            }
            catch (JSONException localJSONException2)
            {
            }
          }
        }
      }
    }
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhz
 * JD-Core Version:    0.6.2
 */