package com.o3dr.services.android.lib.model.action;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Action
  implements Parcelable
{
  public static final Parcelable.Creator<Action> CREATOR = new Parcelable.Creator()
  {
    public Action createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Action(paramAnonymousParcel, null);
    }

    public Action[] newArray(int paramAnonymousInt)
    {
      return new Action[paramAnonymousInt];
    }
  };
  private Bundle data;
  private String type;

  private Action(Parcel paramParcel)
  {
    readFromParcel(paramParcel);
  }

  public Action(String paramString)
  {
    this.type = paramString;
    this.data = null;
  }

  public Action(String paramString, Bundle paramBundle)
  {
    this.type = paramString;
    this.data = paramBundle;
  }

  public int describeContents()
  {
    return 0;
  }

  public Bundle getData()
  {
    return this.data;
  }

  public String getType()
  {
    return this.type;
  }

  public void readFromParcel(Parcel paramParcel)
  {
    this.type = paramParcel.readString();
    this.data = paramParcel.readBundle();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.type);
    paramParcel.writeBundle(this.data);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.model.action.Action
 * JD-Core Version:    0.6.2
 */