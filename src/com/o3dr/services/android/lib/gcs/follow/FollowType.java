package com.o3dr.services.android.lib.gcs.follow;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public enum FollowType
  implements Parcelable
{
  public static final Parcelable.Creator<FollowType> CREATOR = new Parcelable.Creator()
  {
    public FollowType createFromParcel(Parcel paramAnonymousParcel)
    {
      return FollowType.valueOf(paramAnonymousParcel.readString());
    }

    public FollowType[] newArray(int paramAnonymousInt)
    {
      return new FollowType[paramAnonymousInt];
    }
  };
  public static final String EXTRA_FOLLOW_RADIUS = "extra_follow_radius";
  public static final String EXTRA_FOLLOW_ROI_TARGET = "extra_follow_roi_target";
  private final String typeLabel;

  static
  {
    // Byte code:
    //   0: new 2	com/o3dr/services/android/lib/gcs/follow/FollowType
    //   3: dup
    //   4: ldc 34
    //   6: iconst_0
    //   7: ldc 36
    //   9: invokespecial 40	com/o3dr/services/android/lib/gcs/follow/FollowType:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   12: putstatic 42	com/o3dr/services/android/lib/gcs/follow/FollowType:LEASH	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   15: new 2	com/o3dr/services/android/lib/gcs/follow/FollowType
    //   18: dup
    //   19: ldc 43
    //   21: iconst_1
    //   22: ldc 45
    //   24: invokespecial 40	com/o3dr/services/android/lib/gcs/follow/FollowType:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   27: putstatic 47	com/o3dr/services/android/lib/gcs/follow/FollowType:LEAD	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   30: new 2	com/o3dr/services/android/lib/gcs/follow/FollowType
    //   33: dup
    //   34: ldc 48
    //   36: iconst_2
    //   37: ldc 50
    //   39: invokespecial 40	com/o3dr/services/android/lib/gcs/follow/FollowType:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   42: putstatic 52	com/o3dr/services/android/lib/gcs/follow/FollowType:RIGHT	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   45: new 2	com/o3dr/services/android/lib/gcs/follow/FollowType
    //   48: dup
    //   49: ldc 53
    //   51: iconst_3
    //   52: ldc 55
    //   54: invokespecial 40	com/o3dr/services/android/lib/gcs/follow/FollowType:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   57: putstatic 57	com/o3dr/services/android/lib/gcs/follow/FollowType:LEFT	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   60: new 2	com/o3dr/services/android/lib/gcs/follow/FollowType
    //   63: dup
    //   64: ldc 58
    //   66: iconst_4
    //   67: ldc 60
    //   69: invokespecial 40	com/o3dr/services/android/lib/gcs/follow/FollowType:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   72: putstatic 62	com/o3dr/services/android/lib/gcs/follow/FollowType:CIRCLE	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   75: new 64	com/o3dr/services/android/lib/gcs/follow/FollowType$1
    //   78: dup
    //   79: ldc 65
    //   81: iconst_5
    //   82: ldc 67
    //   84: invokespecial 68	com/o3dr/services/android/lib/gcs/follow/FollowType$1:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   87: putstatic 70	com/o3dr/services/android/lib/gcs/follow/FollowType:ABOVE	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   90: new 2	com/o3dr/services/android/lib/gcs/follow/FollowType
    //   93: dup
    //   94: ldc 71
    //   96: bipush 6
    //   98: ldc 73
    //   100: invokespecial 40	com/o3dr/services/android/lib/gcs/follow/FollowType:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   103: putstatic 75	com/o3dr/services/android/lib/gcs/follow/FollowType:SPLINE_LEASH	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   106: new 77	com/o3dr/services/android/lib/gcs/follow/FollowType$2
    //   109: dup
    //   110: ldc 78
    //   112: bipush 7
    //   114: ldc 80
    //   116: invokespecial 81	com/o3dr/services/android/lib/gcs/follow/FollowType$2:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   119: putstatic 83	com/o3dr/services/android/lib/gcs/follow/FollowType:SPLINE_ABOVE	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   122: new 85	com/o3dr/services/android/lib/gcs/follow/FollowType$3
    //   125: dup
    //   126: ldc 86
    //   128: bipush 8
    //   130: ldc 88
    //   132: invokespecial 89	com/o3dr/services/android/lib/gcs/follow/FollowType$3:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   135: putstatic 91	com/o3dr/services/android/lib/gcs/follow/FollowType:GUIDED_SCAN	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   138: new 93	com/o3dr/services/android/lib/gcs/follow/FollowType$4
    //   141: dup
    //   142: ldc 94
    //   144: bipush 9
    //   146: ldc 96
    //   148: invokespecial 97	com/o3dr/services/android/lib/gcs/follow/FollowType$4:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   151: putstatic 99	com/o3dr/services/android/lib/gcs/follow/FollowType:LOOK_AT_ME	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   154: bipush 10
    //   156: anewarray 2	com/o3dr/services/android/lib/gcs/follow/FollowType
    //   159: astore_0
    //   160: aload_0
    //   161: iconst_0
    //   162: getstatic 42	com/o3dr/services/android/lib/gcs/follow/FollowType:LEASH	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   165: aastore
    //   166: aload_0
    //   167: iconst_1
    //   168: getstatic 47	com/o3dr/services/android/lib/gcs/follow/FollowType:LEAD	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   171: aastore
    //   172: aload_0
    //   173: iconst_2
    //   174: getstatic 52	com/o3dr/services/android/lib/gcs/follow/FollowType:RIGHT	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   177: aastore
    //   178: aload_0
    //   179: iconst_3
    //   180: getstatic 57	com/o3dr/services/android/lib/gcs/follow/FollowType:LEFT	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   183: aastore
    //   184: aload_0
    //   185: iconst_4
    //   186: getstatic 62	com/o3dr/services/android/lib/gcs/follow/FollowType:CIRCLE	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   189: aastore
    //   190: aload_0
    //   191: iconst_5
    //   192: getstatic 70	com/o3dr/services/android/lib/gcs/follow/FollowType:ABOVE	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   195: aastore
    //   196: aload_0
    //   197: bipush 6
    //   199: getstatic 75	com/o3dr/services/android/lib/gcs/follow/FollowType:SPLINE_LEASH	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   202: aastore
    //   203: aload_0
    //   204: bipush 7
    //   206: getstatic 83	com/o3dr/services/android/lib/gcs/follow/FollowType:SPLINE_ABOVE	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   209: aastore
    //   210: aload_0
    //   211: bipush 8
    //   213: getstatic 91	com/o3dr/services/android/lib/gcs/follow/FollowType:GUIDED_SCAN	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   216: aastore
    //   217: aload_0
    //   218: bipush 9
    //   220: getstatic 99	com/o3dr/services/android/lib/gcs/follow/FollowType:LOOK_AT_ME	Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   223: aastore
    //   224: aload_0
    //   225: putstatic 101	com/o3dr/services/android/lib/gcs/follow/FollowType:$VALUES	[Lcom/o3dr/services/android/lib/gcs/follow/FollowType;
    //   228: new 103	com/o3dr/services/android/lib/gcs/follow/FollowType$5
    //   231: dup
    //   232: invokespecial 105	com/o3dr/services/android/lib/gcs/follow/FollowType$5:<init>	()V
    //   235: putstatic 107	com/o3dr/services/android/lib/gcs/follow/FollowType:CREATOR	Landroid/os/Parcelable$Creator;
    //   238: return
  }

  private FollowType(String paramString)
  {
    this.typeLabel = paramString;
  }

  public static List<FollowType> getFollowTypes(boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(LEASH);
    localArrayList.add(LEAD);
    localArrayList.add(RIGHT);
    localArrayList.add(LEFT);
    localArrayList.add(CIRCLE);
    localArrayList.add(ABOVE);
    localArrayList.add(GUIDED_SCAN);
    localArrayList.add(LOOK_AT_ME);
    if (paramBoolean)
    {
      localArrayList.add(SPLINE_LEASH);
      localArrayList.add(SPLINE_ABOVE);
    }
    return localArrayList;
  }

  public int describeContents()
  {
    return 0;
  }

  public String getTypeLabel()
  {
    return this.typeLabel;
  }

  public boolean hasParam(String paramString)
  {
    int i = 1;
    int j = -1;
    switch (paramString.hashCode())
    {
    default:
    case 1619123953:
    case -1924169149:
    }
    while (true)
      switch (j)
      {
      default:
        i = 0;
      case 0:
        return i;
        if (paramString.equals("extra_follow_radius"))
        {
          j = 0;
          continue;
          if (paramString.equals("extra_follow_roi_target"))
            j = i;
        }
        break;
      case 1:
      }
    return false;
  }

  public String toString()
  {
    return getTypeLabel();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(name());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.gcs.follow.FollowType
 * JD-Core Version:    0.6.2
 */