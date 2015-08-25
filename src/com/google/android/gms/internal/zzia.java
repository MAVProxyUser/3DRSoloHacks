package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class zzia
{
  public static void zza(StringBuilder paramStringBuilder, HashMap<String, String> paramHashMap)
  {
    paramStringBuilder.append("{");
    Iterator localIterator = paramHashMap.keySet().iterator();
    int i = 1;
    if (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      int j;
      label56: String str2;
      if (i == 0)
      {
        paramStringBuilder.append(",");
        j = i;
        str2 = (String)paramHashMap.get(str1);
        paramStringBuilder.append("\"").append(str1).append("\":");
        if (str2 != null)
          break label109;
        paramStringBuilder.append("null");
      }
      while (true)
      {
        i = j;
        break;
        j = 0;
        break label56;
        label109: paramStringBuilder.append("\"").append(str2).append("\"");
      }
    }
    paramStringBuilder.append("}");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzia
 * JD-Core Version:    0.6.2
 */