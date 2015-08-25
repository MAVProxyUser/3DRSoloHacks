package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Parameter
  implements Parcelable, Comparable<Parameter>
{
  public static final Parcelable.Creator<Parameter> CREATOR = new Parcelable.Creator()
  {
    public Parameter createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Parameter(paramAnonymousParcel, null);
    }

    public Parameter[] newArray(int paramAnonymousInt)
    {
      return new Parameter[paramAnonymousInt];
    }
  };
  public static final int RANGE_HIGH = 1;
  public static final int RANGE_LOW;
  private static final DecimalFormat formatter = (DecimalFormat)DecimalFormat.getInstance();
  private String description;
  private String displayName;
  private String name;
  private String range;
  private int type;
  private String units;
  private double value;
  private String values;

  static
  {
    formatter.applyPattern("0.###");
  }

  private Parameter(Parcel paramParcel)
  {
    this.name = paramParcel.readString();
    this.value = paramParcel.readDouble();
    this.type = paramParcel.readInt();
    this.displayName = paramParcel.readString();
    this.description = paramParcel.readString();
    this.units = paramParcel.readString();
    this.range = paramParcel.readString();
    this.values = paramParcel.readString();
  }

  public Parameter(String paramString, double paramDouble, int paramInt)
  {
    this.name = paramString;
    this.value = paramDouble;
    this.type = paramInt;
  }

  public int compareTo(Parameter paramParameter)
  {
    return this.name.compareTo(paramParameter.name);
  }

  public int describeContents()
  {
    return 0;
  }

  public String getDescription()
  {
    return this.description;
  }

  public String getDisplayName()
  {
    return this.displayName;
  }

  public String getDisplayValue()
  {
    return formatter.format(this.value);
  }

  public String getName()
  {
    return this.name;
  }

  public String getRange()
  {
    return this.range;
  }

  public int getType()
  {
    return this.type;
  }

  public String getUnits()
  {
    return this.units;
  }

  public double getValue()
  {
    return this.value;
  }

  public String getValues()
  {
    return this.values;
  }

  public boolean hasInfo()
  {
    return ((this.description != null) && (!this.description.isEmpty())) || ((this.values != null) && (!this.values.isEmpty()));
  }

  public double[] parseRange()
    throws ParseException
  {
    DecimalFormat localDecimalFormat = formatter;
    String[] arrayOfString = this.range.split(" ");
    if (arrayOfString.length != 2)
      throw new IllegalArgumentException();
    double[] arrayOfDouble = new double[2];
    arrayOfDouble[0] = localDecimalFormat.parse(arrayOfString[0]).doubleValue();
    arrayOfDouble[1] = localDecimalFormat.parse(arrayOfString[1]).doubleValue();
    return arrayOfDouble;
  }

  public Map<Double, String> parseValues()
    throws ParseException
  {
    DecimalFormat localDecimalFormat = formatter;
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    if (this.values != null)
    {
      String[] arrayOfString1 = this.values.split(",");
      int i = arrayOfString1.length;
      for (int j = 0; j < i; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split(":");
        if (arrayOfString2.length != 2)
          throw new IllegalArgumentException();
        localLinkedHashMap.put(Double.valueOf(localDecimalFormat.parse(arrayOfString2[0].trim()).doubleValue()), arrayOfString2[1].trim());
      }
    }
    return localLinkedHashMap;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }

  public void setDisplayName(String paramString)
  {
    this.displayName = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setRange(String paramString)
  {
    this.range = paramString;
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
  }

  public void setUnits(String paramString)
  {
    this.units = paramString;
  }

  public void setValue(double paramDouble)
  {
    this.value = paramDouble;
  }

  public void setValues(String paramString)
  {
    this.values = paramString;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.name);
    paramParcel.writeDouble(this.value);
    paramParcel.writeInt(this.type);
    paramParcel.writeString(this.displayName);
    paramParcel.writeString(this.description);
    paramParcel.writeString(this.units);
    paramParcel.writeString(this.range);
    paramParcel.writeString(this.values);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Parameter
 * JD-Core Version:    0.6.2
 */