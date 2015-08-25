package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;

public final class Challenge
{
  private final String realm;
  private final String scheme;

  public Challenge(String paramString1, String paramString2)
  {
    this.scheme = paramString1;
    this.realm = paramString2;
  }

  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof Challenge)) && (Util.equal(this.scheme, ((Challenge)paramObject).scheme)) && (Util.equal(this.realm, ((Challenge)paramObject).realm));
  }

  public String getRealm()
  {
    return this.realm;
  }

  public String getScheme()
  {
    return this.scheme;
  }

  public int hashCode()
  {
    if (this.realm != null);
    for (int i = this.realm.hashCode(); ; i = 0)
    {
      int j = 31 * (i + 899);
      String str = this.scheme;
      int k = 0;
      if (str != null)
        k = this.scheme.hashCode();
      return j + k;
    }
  }

  public String toString()
  {
    return this.scheme + " realm=\"" + this.realm + "\"";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Challenge
 * JD-Core Version:    0.6.2
 */