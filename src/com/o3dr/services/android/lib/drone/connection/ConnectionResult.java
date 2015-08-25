package com.o3dr.services.android.lib.drone.connection;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class ConnectionResult
  implements Parcelable
{
  public static final Parcelable.Creator<ConnectionResult> CREATOR = new Parcelable.Creator()
  {
    public ConnectionResult createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ConnectionResult(paramAnonymousParcel, null);
    }

    public ConnectionResult[] newArray(int paramAnonymousInt)
    {
      return new ConnectionResult[paramAnonymousInt];
    }
  };
  private final int mErrorCode;
  private final String mErrorMessage;

  public ConnectionResult(int paramInt, String paramString)
  {
    this.mErrorCode = paramInt;
    this.mErrorMessage = paramString;
  }

  private ConnectionResult(Parcel paramParcel)
  {
    this.mErrorCode = paramParcel.readInt();
    this.mErrorMessage = paramParcel.readString();
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    ConnectionResult localConnectionResult;
    do
    {
      return true;
      if (!(paramObject instanceof ConnectionResult))
        return false;
      localConnectionResult = (ConnectionResult)paramObject;
      if (this.mErrorCode != localConnectionResult.mErrorCode)
        return false;
      if (this.mErrorMessage == null)
        break;
    }
    while (this.mErrorMessage.equals(localConnectionResult.mErrorMessage));
    while (true)
    {
      return false;
      if (localConnectionResult.mErrorMessage == null)
        break;
    }
  }

  public int getErrorCode()
  {
    return this.mErrorCode;
  }

  public String getErrorMessage()
  {
    return this.mErrorMessage;
  }

  public int hashCode()
  {
    int i = 31 * this.mErrorCode;
    if (this.mErrorMessage != null);
    for (int j = this.mErrorMessage.hashCode(); ; j = 0)
      return i + j;
  }

  public String toString()
  {
    return "ConnectionResult{mErrorCode=" + this.mErrorCode + ", mErrorMessage='" + this.mErrorMessage + '\'' + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mErrorCode);
    paramParcel.writeString(this.mErrorMessage);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.connection.ConnectionResult
 * JD-Core Version:    0.6.2
 */