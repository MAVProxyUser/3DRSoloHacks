package com.o3dr.services.android.lib.gcs.follow;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class FollowState
  implements Parcelable
{
  public static final Parcelable.Creator<FollowState> CREATOR = new Parcelable.Creator()
  {
    public FollowState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new FollowState(paramAnonymousParcel, null);
    }

    public FollowState[] newArray(int paramAnonymousInt)
    {
      return new FollowState[paramAnonymousInt];
    }
  };
  public static final int STATE_DRONE_DISCONNECTED = 2;
  public static final int STATE_DRONE_NOT_ARMED = 1;
  public static final int STATE_END = 5;
  public static final int STATE_INVALID = 0;
  public static final int STATE_RUNNING = 4;
  public static final int STATE_START = 3;
  private FollowType mode;
  private Bundle modeParams;
  private int state;

  public FollowState()
  {
  }

  public FollowState(int paramInt, FollowType paramFollowType, Bundle paramBundle)
  {
    this.state = paramInt;
    this.modeParams = paramBundle;
    this.mode = paramFollowType;
  }

  private FollowState(Parcel paramParcel)
  {
    this.state = paramParcel.readInt();
    this.modeParams = paramParcel.readBundle();
    int i = paramParcel.readInt();
    if (i == -1);
    for (FollowType localFollowType = null; ; localFollowType = FollowType.values()[i])
    {
      this.mode = localFollowType;
      return;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public FollowType getMode()
  {
    return this.mode;
  }

  public Bundle getParams()
  {
    return this.modeParams;
  }

  public int getState()
  {
    return this.state;
  }

  public boolean isEnabled()
  {
    return (this.state == 4) || (this.state == 3);
  }

  public void setMode(FollowType paramFollowType)
  {
    this.mode = paramFollowType;
  }

  public void setState(int paramInt)
  {
    this.state = paramInt;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.state);
    paramParcel.writeBundle(this.modeParams);
    if (this.mode == null);
    for (int i = -1; ; i = this.mode.ordinal())
    {
      paramParcel.writeInt(i);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.gcs.follow.FollowState
 * JD-Core Version:    0.6.2
 */