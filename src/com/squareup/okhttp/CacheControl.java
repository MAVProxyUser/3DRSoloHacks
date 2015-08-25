package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HeaderParser;
import java.util.concurrent.TimeUnit;

public final class CacheControl
{
  public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(2147483647, TimeUnit.SECONDS).build();
  public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
  String headerValue;
  private final boolean isPrivate;
  private final boolean isPublic;
  private final int maxAgeSeconds;
  private final int maxStaleSeconds;
  private final int minFreshSeconds;
  private final boolean mustRevalidate;
  private final boolean noCache;
  private final boolean noStore;
  private final boolean noTransform;
  private final boolean onlyIfCached;
  private final int sMaxAgeSeconds;

  private CacheControl(Builder paramBuilder)
  {
    this.noCache = paramBuilder.noCache;
    this.noStore = paramBuilder.noStore;
    this.maxAgeSeconds = paramBuilder.maxAgeSeconds;
    this.sMaxAgeSeconds = -1;
    this.isPrivate = false;
    this.isPublic = false;
    this.mustRevalidate = false;
    this.maxStaleSeconds = paramBuilder.maxStaleSeconds;
    this.minFreshSeconds = paramBuilder.minFreshSeconds;
    this.onlyIfCached = paramBuilder.onlyIfCached;
    this.noTransform = paramBuilder.noTransform;
  }

  private CacheControl(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, int paramInt3, int paramInt4, boolean paramBoolean6, boolean paramBoolean7, String paramString)
  {
    this.noCache = paramBoolean1;
    this.noStore = paramBoolean2;
    this.maxAgeSeconds = paramInt1;
    this.sMaxAgeSeconds = paramInt2;
    this.isPrivate = paramBoolean3;
    this.isPublic = paramBoolean4;
    this.mustRevalidate = paramBoolean5;
    this.maxStaleSeconds = paramInt3;
    this.minFreshSeconds = paramInt4;
    this.onlyIfCached = paramBoolean6;
    this.noTransform = paramBoolean7;
    this.headerValue = paramString;
  }

  private String headerValue()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.noCache)
      localStringBuilder.append("no-cache, ");
    if (this.noStore)
      localStringBuilder.append("no-store, ");
    if (this.maxAgeSeconds != -1)
      localStringBuilder.append("max-age=").append(this.maxAgeSeconds).append(", ");
    if (this.sMaxAgeSeconds != -1)
      localStringBuilder.append("s-maxage=").append(this.sMaxAgeSeconds).append(", ");
    if (this.isPrivate)
      localStringBuilder.append("private, ");
    if (this.isPublic)
      localStringBuilder.append("public, ");
    if (this.mustRevalidate)
      localStringBuilder.append("must-revalidate, ");
    if (this.maxStaleSeconds != -1)
      localStringBuilder.append("max-stale=").append(this.maxStaleSeconds).append(", ");
    if (this.minFreshSeconds != -1)
      localStringBuilder.append("min-fresh=").append(this.minFreshSeconds).append(", ");
    if (this.onlyIfCached)
      localStringBuilder.append("only-if-cached, ");
    if (this.noTransform)
      localStringBuilder.append("no-transform, ");
    if (localStringBuilder.length() == 0)
      return "";
    localStringBuilder.delete(-2 + localStringBuilder.length(), localStringBuilder.length());
    return localStringBuilder.toString();
  }

  public static CacheControl parse(Headers paramHeaders)
  {
    boolean bool1 = false;
    boolean bool2 = false;
    int i = -1;
    int j = -1;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool5 = false;
    int k = -1;
    int m = -1;
    boolean bool6 = false;
    boolean bool7 = false;
    int n = 1;
    Object localObject = null;
    int i1 = 0;
    int i2 = paramHeaders.size();
    while (i1 < i2)
    {
      String str1 = paramHeaders.name(i1);
      String str2 = paramHeaders.value(i1);
      int i3;
      if (str1.equalsIgnoreCase("Cache-Control"))
        if (localObject != null)
        {
          n = 0;
          i3 = 0;
        }
      while (true)
      {
        label89: int i4 = str2.length();
        if (i3 >= i4)
          break label490;
        int i5 = i3;
        int i6 = HeaderParser.skipUntil(str2, i3, "=,;");
        String str3 = str2.substring(i5, i6).trim();
        String str4;
        if ((i6 == str2.length()) || (str2.charAt(i6) == ',') || (str2.charAt(i6) == ';'))
        {
          i3 = i6 + 1;
          str4 = null;
        }
        while (true)
        {
          if (!"no-cache".equalsIgnoreCase(str3))
            break label311;
          bool1 = true;
          break label89;
          localObject = str2;
          break;
          if (!str1.equalsIgnoreCase("Pragma"))
            break label490;
          n = 0;
          break;
          int i7 = HeaderParser.skipWhitespace(str2, i6 + 1);
          if ((i7 < str2.length()) && (str2.charAt(i7) == '"'))
          {
            int i8 = i7 + 1;
            int i9 = HeaderParser.skipUntil(str2, i8, "\"");
            str4 = str2.substring(i8, i9);
            i3 = i9 + 1;
          }
          else
          {
            i3 = HeaderParser.skipUntil(str2, i7, ",;");
            str4 = str2.substring(i7, i3).trim();
          }
        }
        label311: if ("no-store".equalsIgnoreCase(str3))
          bool2 = true;
        else if ("max-age".equalsIgnoreCase(str3))
          i = HeaderParser.parseSeconds(str4, -1);
        else if ("s-maxage".equalsIgnoreCase(str3))
          j = HeaderParser.parseSeconds(str4, -1);
        else if ("private".equalsIgnoreCase(str3))
          bool3 = true;
        else if ("public".equalsIgnoreCase(str3))
          bool4 = true;
        else if ("must-revalidate".equalsIgnoreCase(str3))
          bool5 = true;
        else if ("max-stale".equalsIgnoreCase(str3))
          k = HeaderParser.parseSeconds(str4, 2147483647);
        else if ("min-fresh".equalsIgnoreCase(str3))
          m = HeaderParser.parseSeconds(str4, -1);
        else if ("only-if-cached".equalsIgnoreCase(str3))
          bool6 = true;
        else if ("no-transform".equalsIgnoreCase(str3))
          bool7 = true;
      }
      label490: i1++;
    }
    if (n == 0)
      localObject = null;
    return new CacheControl(bool1, bool2, i, j, bool3, bool4, bool5, k, m, bool6, bool7, localObject);
  }

  public boolean isPrivate()
  {
    return this.isPrivate;
  }

  public boolean isPublic()
  {
    return this.isPublic;
  }

  public int maxAgeSeconds()
  {
    return this.maxAgeSeconds;
  }

  public int maxStaleSeconds()
  {
    return this.maxStaleSeconds;
  }

  public int minFreshSeconds()
  {
    return this.minFreshSeconds;
  }

  public boolean mustRevalidate()
  {
    return this.mustRevalidate;
  }

  public boolean noCache()
  {
    return this.noCache;
  }

  public boolean noStore()
  {
    return this.noStore;
  }

  public boolean noTransform()
  {
    return this.noTransform;
  }

  public boolean onlyIfCached()
  {
    return this.onlyIfCached;
  }

  public int sMaxAgeSeconds()
  {
    return this.sMaxAgeSeconds;
  }

  public String toString()
  {
    String str1 = this.headerValue;
    if (str1 != null)
      return str1;
    String str2 = headerValue();
    this.headerValue = str2;
    return str2;
  }

  public static final class Builder
  {
    int maxAgeSeconds = -1;
    int maxStaleSeconds = -1;
    int minFreshSeconds = -1;
    boolean noCache;
    boolean noStore;
    boolean noTransform;
    boolean onlyIfCached;

    public CacheControl build()
    {
      return new CacheControl(this, null);
    }

    public Builder maxAge(int paramInt, TimeUnit paramTimeUnit)
    {
      if (paramInt < 0)
        throw new IllegalArgumentException("maxAge < 0: " + paramInt);
      long l = paramTimeUnit.toSeconds(paramInt);
      if (l > 2147483647L);
      for (int i = 2147483647; ; i = (int)l)
      {
        this.maxAgeSeconds = i;
        return this;
      }
    }

    public Builder maxStale(int paramInt, TimeUnit paramTimeUnit)
    {
      if (paramInt < 0)
        throw new IllegalArgumentException("maxStale < 0: " + paramInt);
      long l = paramTimeUnit.toSeconds(paramInt);
      if (l > 2147483647L);
      for (int i = 2147483647; ; i = (int)l)
      {
        this.maxStaleSeconds = i;
        return this;
      }
    }

    public Builder minFresh(int paramInt, TimeUnit paramTimeUnit)
    {
      if (paramInt < 0)
        throw new IllegalArgumentException("minFresh < 0: " + paramInt);
      long l = paramTimeUnit.toSeconds(paramInt);
      if (l > 2147483647L);
      for (int i = 2147483647; ; i = (int)l)
      {
        this.minFreshSeconds = i;
        return this;
      }
    }

    public Builder noCache()
    {
      this.noCache = true;
      return this;
    }

    public Builder noStore()
    {
      this.noStore = true;
      return this;
    }

    public Builder noTransform()
    {
      this.noTransform = true;
      return this;
    }

    public Builder onlyIfCached()
    {
      this.onlyIfCached = true;
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.CacheControl
 * JD-Core Version:    0.6.2
 */