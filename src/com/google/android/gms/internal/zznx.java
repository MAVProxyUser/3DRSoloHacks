package com.google.android.gms.internal;

import java.io.IOException;

public class zznx extends IOException
{
  public zznx(String paramString)
  {
    super(paramString);
  }

  static zznx zzAa()
  {
    return new zznx("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
  }

  static zznx zzzU()
  {
    return new zznx("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
  }

  static zznx zzzV()
  {
    return new zznx("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
  }

  static zznx zzzW()
  {
    return new zznx("CodedInputStream encountered a malformed varint.");
  }

  static zznx zzzX()
  {
    return new zznx("Protocol message contained an invalid tag (zero).");
  }

  static zznx zzzY()
  {
    return new zznx("Protocol message end-group tag did not match expected tag.");
  }

  static zznx zzzZ()
  {
    return new zznx("Protocol message tag had invalid wire type.");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznx
 * JD-Core Version:    0.6.2
 */