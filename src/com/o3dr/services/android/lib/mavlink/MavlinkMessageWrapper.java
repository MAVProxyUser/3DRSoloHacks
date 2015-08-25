package com.o3dr.services.android.lib.mavlink;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.MAVLink.Messages.MAVLinkMessage;

public class MavlinkMessageWrapper
  implements Parcelable
{
  public static final Parcelable.Creator<MavlinkMessageWrapper> CREATOR = new Parcelable.Creator()
  {
    public MavlinkMessageWrapper createFromParcel(Parcel paramAnonymousParcel)
    {
      return new MavlinkMessageWrapper(paramAnonymousParcel, null);
    }

    public MavlinkMessageWrapper[] newArray(int paramAnonymousInt)
    {
      return new MavlinkMessageWrapper[paramAnonymousInt];
    }
  };
  private MAVLinkMessage mavLinkMessage;

  private MavlinkMessageWrapper(Parcel paramParcel)
  {
    this.mavLinkMessage = ((MAVLinkMessage)paramParcel.readSerializable());
  }

  public MavlinkMessageWrapper(MAVLinkMessage paramMAVLinkMessage)
  {
    this.mavLinkMessage = paramMAVLinkMessage;
  }

  public int describeContents()
  {
    return 0;
  }

  public MAVLinkMessage getMavLinkMessage()
  {
    return this.mavLinkMessage;
  }

  public void setMavLinkMessage(MAVLinkMessage paramMAVLinkMessage)
  {
    this.mavLinkMessage = paramMAVLinkMessage;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeSerializable(this.mavLinkMessage);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.mavlink.MavlinkMessageWrapper
 * JD-Core Version:    0.6.2
 */