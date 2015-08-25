package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parameters
  implements Parcelable
{
  public static final Parcelable.Creator<Parameters> CREATOR = new Parcelable.Creator()
  {
    public Parameters createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Parameters(paramAnonymousParcel, null);
    }

    public Parameters[] newArray(int paramAnonymousInt)
    {
      return new Parameters[paramAnonymousInt];
    }
  };
  private List<Parameter> parametersList = new ArrayList();

  public Parameters()
  {
  }

  private Parameters(Parcel paramParcel)
  {
    paramParcel.readTypedList(this.parametersList, Parameter.CREATOR);
  }

  public Parameters(List<Parameter> paramList)
  {
    this.parametersList = paramList;
  }

  public int describeContents()
  {
    return 0;
  }

  public Parameter getParameter(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return null;
    Iterator localIterator = this.parametersList.iterator();
    while (localIterator.hasNext())
    {
      Parameter localParameter = (Parameter)localIterator.next();
      if (localParameter.getName().equalsIgnoreCase(paramString))
        return localParameter;
    }
    return null;
  }

  public List<Parameter> getParameters()
  {
    return this.parametersList;
  }

  public void setParametersList(List<Parameter> paramList)
  {
    this.parametersList = paramList;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeTypedList(this.parametersList);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Parameters
 * JD-Core Version:    0.6.2
 */