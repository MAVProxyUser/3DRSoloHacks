package com.squareup.okhttp.internal.spdy;

import java.util.Arrays;

public final class Settings
{
  static final int CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
  static final int COUNT = 10;
  static final int CURRENT_CWND = 5;
  static final int DEFAULT_INITIAL_WINDOW_SIZE = 65536;
  static final int DOWNLOAD_BANDWIDTH = 2;
  static final int DOWNLOAD_RETRANS_RATE = 6;
  static final int ENABLE_PUSH = 2;
  static final int FLAG_CLEAR_PREVIOUSLY_PERSISTED_SETTINGS = 1;
  static final int FLOW_CONTROL_OPTIONS = 10;
  static final int FLOW_CONTROL_OPTIONS_DISABLED = 1;
  static final int HEADER_TABLE_SIZE = 1;
  static final int INITIAL_WINDOW_SIZE = 7;
  static final int MAX_CONCURRENT_STREAMS = 4;
  static final int MAX_FRAME_SIZE = 5;
  static final int MAX_HEADER_LIST_SIZE = 6;
  static final int PERSISTED = 2;
  static final int PERSIST_VALUE = 1;
  static final int ROUND_TRIP_TIME = 3;
  static final int UPLOAD_BANDWIDTH = 1;
  private int persistValue;
  private int persisted;
  private int set;
  private final int[] values = new int[10];

  void clear()
  {
    this.persisted = 0;
    this.persistValue = 0;
    this.set = 0;
    Arrays.fill(this.values, 0);
  }

  int flags(int paramInt)
  {
    boolean bool = isPersisted(paramInt);
    int i = 0;
    if (bool)
      i = 0x0 | 0x2;
    if (persistValue(paramInt))
      i |= 1;
    return i;
  }

  int get(int paramInt)
  {
    return this.values[paramInt];
  }

  int getClientCertificateVectorSize(int paramInt)
  {
    if ((0x100 & this.set) != 0)
      paramInt = this.values[8];
    return paramInt;
  }

  int getCurrentCwnd(int paramInt)
  {
    if ((0x20 & this.set) != 0)
      paramInt = this.values[5];
    return paramInt;
  }

  int getDownloadBandwidth(int paramInt)
  {
    if ((0x4 & this.set) != 0)
      paramInt = this.values[2];
    return paramInt;
  }

  int getDownloadRetransRate(int paramInt)
  {
    if ((0x40 & this.set) != 0)
      paramInt = this.values[6];
    return paramInt;
  }

  boolean getEnablePush(boolean paramBoolean)
  {
    int i;
    if ((0x4 & this.set) != 0)
      i = this.values[2];
    while (i == 1)
    {
      return true;
      if (paramBoolean)
        i = 1;
      else
        i = 0;
    }
    return false;
  }

  int getHeaderTableSize()
  {
    if ((0x2 & this.set) != 0)
      return this.values[1];
    return -1;
  }

  int getInitialWindowSize(int paramInt)
  {
    if ((0x80 & this.set) != 0)
      paramInt = this.values[7];
    return paramInt;
  }

  int getMaxConcurrentStreams(int paramInt)
  {
    if ((0x10 & this.set) != 0)
      paramInt = this.values[4];
    return paramInt;
  }

  int getMaxFrameSize(int paramInt)
  {
    if ((0x20 & this.set) != 0)
      paramInt = this.values[5];
    return paramInt;
  }

  int getMaxHeaderListSize(int paramInt)
  {
    if ((0x40 & this.set) != 0)
      paramInt = this.values[6];
    return paramInt;
  }

  int getRoundTripTime(int paramInt)
  {
    if ((0x8 & this.set) != 0)
      paramInt = this.values[3];
    return paramInt;
  }

  int getUploadBandwidth(int paramInt)
  {
    if ((0x2 & this.set) != 0)
      paramInt = this.values[1];
    return paramInt;
  }

  boolean isFlowControlDisabled()
  {
    if ((0x400 & this.set) != 0);
    for (int i = this.values[10]; ; i = 0)
    {
      int j = i & 0x1;
      boolean bool = false;
      if (j != 0)
        bool = true;
      return bool;
    }
  }

  boolean isPersisted(int paramInt)
  {
    return (1 << paramInt & this.persisted) != 0;
  }

  boolean isSet(int paramInt)
  {
    return (1 << paramInt & this.set) != 0;
  }

  void merge(Settings paramSettings)
  {
    int i = 0;
    if (i < 10)
    {
      if (!paramSettings.isSet(i));
      while (true)
      {
        i++;
        break;
        set(i, paramSettings.flags(i), paramSettings.get(i));
      }
    }
  }

  boolean persistValue(int paramInt)
  {
    return (1 << paramInt & this.persistValue) != 0;
  }

  Settings set(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 >= this.values.length)
      return this;
    int i = 1 << paramInt1;
    this.set = (i | this.set);
    if ((paramInt2 & 0x1) != 0)
    {
      this.persistValue = (i | this.persistValue);
      if ((paramInt2 & 0x2) == 0)
        break label86;
    }
    label86: for (this.persisted = (i | this.persisted); ; this.persisted &= (i ^ 0xFFFFFFFF))
    {
      this.values[paramInt1] = paramInt3;
      return this;
      this.persistValue &= (i ^ 0xFFFFFFFF);
      break;
    }
  }

  int size()
  {
    return Integer.bitCount(this.set);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Settings
 * JD-Core Version:    0.6.2
 */