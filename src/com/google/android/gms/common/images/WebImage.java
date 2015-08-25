package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage
  implements SafeParcelable
{
  public static final Parcelable.Creator<WebImage> CREATOR = new zzb();
  private final Uri zzSr;
  private final int zzma;
  private final int zzmb;
  private final int zzzH;

  WebImage(int paramInt1, Uri paramUri, int paramInt2, int paramInt3)
  {
    this.zzzH = paramInt1;
    this.zzSr = paramUri;
    this.zzma = paramInt2;
    this.zzmb = paramInt3;
  }

  public WebImage(Uri paramUri)
    throws IllegalArgumentException
  {
    this(paramUri, 0, 0);
  }

  public WebImage(Uri paramUri, int paramInt1, int paramInt2)
    throws IllegalArgumentException
  {
    this(1, paramUri, paramInt1, paramInt2);
    if (paramUri == null)
      throw new IllegalArgumentException("url cannot be null");
    if ((paramInt1 < 0) || (paramInt2 < 0))
      throw new IllegalArgumentException("width and height must not be negative");
  }

  public WebImage(JSONObject paramJSONObject)
    throws IllegalArgumentException
  {
    this(zzg(paramJSONObject), paramJSONObject.optInt("width", 0), paramJSONObject.optInt("height", 0));
  }

  private static Uri zzg(JSONObject paramJSONObject)
  {
    boolean bool = paramJSONObject.has("url");
    Object localObject = null;
    if (bool);
    try
    {
      Uri localUri = Uri.parse(paramJSONObject.getString("url"));
      localObject = localUri;
      return localObject;
    }
    catch (JSONException localJSONException)
    {
    }
    return null;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    WebImage localWebImage;
    do
    {
      return true;
      if ((paramObject == null) || (!(paramObject instanceof WebImage)))
        return false;
      localWebImage = (WebImage)paramObject;
    }
    while ((zzu.equal(this.zzSr, localWebImage.zzSr)) && (this.zzma == localWebImage.zzma) && (this.zzmb == localWebImage.zzmb));
    return false;
  }

  public int getHeight()
  {
    return this.zzmb;
  }

  public Uri getUrl()
  {
    return this.zzSr;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public int getWidth()
  {
    return this.zzma;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.zzSr;
    arrayOfObject[1] = Integer.valueOf(this.zzma);
    arrayOfObject[2] = Integer.valueOf(this.zzmb);
    return zzu.hashCode(arrayOfObject);
  }

  public JSONObject toJson()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("url", this.zzSr.toString());
      localJSONObject.put("width", this.zzma);
      localJSONObject.put("height", this.zzmb);
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
    }
    return localJSONObject;
  }

  public String toString()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(this.zzma);
    arrayOfObject[1] = Integer.valueOf(this.zzmb);
    arrayOfObject[2] = this.zzSr.toString();
    return String.format("Image %dx%d %s", arrayOfObject);
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.images.WebImage
 * JD-Core Version:    0.6.2
 */