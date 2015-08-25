package com.google.android.gms.location.places.internal;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class zzo extends zzq
  implements Place
{
  private final String zzarP;
  private boolean zzasJ;
  private final zzn zzasM;

  public zzo(DataHolder paramDataHolder, int paramInt, Context paramContext)
  {
    super(paramDataHolder, paramInt);
    if (paramContext != null);
    for (zzn localzzn = zzn.zzag(paramContext); ; localzzn = null)
    {
      this.zzasM = localzzn;
      this.zzasJ = zzh("place_is_logging_enabled", false);
      this.zzarP = zzA("place_id", "");
      return;
    }
  }

  private void zzcJ(String paramString)
  {
    if ((this.zzasJ) && (this.zzasM != null))
      this.zzasM.zzz(this.zzarP, paramString);
  }

  public CharSequence getAddress()
  {
    zzcJ("getAddress");
    return zzA("place_address", "");
  }

  public String getId()
  {
    zzcJ("getId");
    return this.zzarP;
  }

  public LatLng getLatLng()
  {
    zzcJ("getLatLng");
    return (LatLng)zza("place_lat_lng", LatLng.CREATOR);
  }

  public Locale getLocale()
  {
    zzcJ("getLocale");
    String str = zzA("place_locale", "");
    if (!TextUtils.isEmpty(str))
      return new Locale(str);
    return Locale.getDefault();
  }

  public CharSequence getName()
  {
    zzcJ("getName");
    return zzA("place_name", "");
  }

  public CharSequence getPhoneNumber()
  {
    zzcJ("getPhoneNumber");
    return zzA("place_phone_number", "");
  }

  public List<Integer> getPlaceTypes()
  {
    zzcJ("getPlaceTypes");
    return zza("place_types", Collections.emptyList());
  }

  public int getPriceLevel()
  {
    zzcJ("getPriceLevel");
    return zzy("place_price_level", -1);
  }

  public float getRating()
  {
    zzcJ("getRating");
    return zzb("place_rating", -1.0F);
  }

  public LatLngBounds getViewport()
  {
    zzcJ("getViewport");
    return (LatLngBounds)zza("place_viewport", LatLngBounds.CREATOR);
  }

  public Uri getWebsiteUri()
  {
    zzcJ("getWebsiteUri");
    String str = zzA("place_website_uri", null);
    if (str == null)
      return null;
    return Uri.parse(str);
  }

  public boolean zzsT()
  {
    zzcJ("isPermanentlyClosed");
    return zzh("place_is_permanently_closed", false);
  }

  public float zzsZ()
  {
    zzcJ("getLevelNumber");
    return zzb("place_level_number", 0.0F);
  }

  public Place zztg()
  {
    PlaceImpl.zza localzza = new PlaceImpl.zza().zzZ(this.zzasJ);
    this.zzasJ = false;
    PlaceImpl localPlaceImpl = localzza.zzcM(getAddress().toString()).zzo(zzb("place_attributions", Collections.emptyList())).zzcK(getId()).zzY(zzsT()).zza(getLatLng()).zzf(zzsZ()).zzcL(getName().toString()).zzcN(getPhoneNumber().toString()).zzfV(getPriceLevel()).zzg(getRating()).zzn(getPlaceTypes()).zza(getViewport()).zzk(getWebsiteUri()).zzth();
    localPlaceImpl.setLocale(getLocale());
    localPlaceImpl.zza(this.zzasM);
    return localPlaceImpl;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzo
 * JD-Core Version:    0.6.2
 */