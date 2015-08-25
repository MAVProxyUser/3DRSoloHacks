package com.google.android.gms.location.places;

import android.net.Uri;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.List;
import java.util.Locale;

public abstract interface Place extends Freezable<Place>
{
  public static final int TYPE_ACCOUNTING = 1;
  public static final int TYPE_ADMINISTRATIVE_AREA_LEVEL_1 = 1001;
  public static final int TYPE_ADMINISTRATIVE_AREA_LEVEL_2 = 1002;
  public static final int TYPE_ADMINISTRATIVE_AREA_LEVEL_3 = 1003;
  public static final int TYPE_AIRPORT = 2;
  public static final int TYPE_AMUSEMENT_PARK = 3;
  public static final int TYPE_AQUARIUM = 4;
  public static final int TYPE_ART_GALLERY = 5;
  public static final int TYPE_ATM = 6;
  public static final int TYPE_BAKERY = 7;
  public static final int TYPE_BANK = 8;
  public static final int TYPE_BAR = 9;
  public static final int TYPE_BEAUTY_SALON = 10;
  public static final int TYPE_BICYCLE_STORE = 11;
  public static final int TYPE_BOOK_STORE = 12;
  public static final int TYPE_BOWLING_ALLEY = 13;
  public static final int TYPE_BUS_STATION = 14;
  public static final int TYPE_CAFE = 15;
  public static final int TYPE_CAMPGROUND = 16;
  public static final int TYPE_CAR_DEALER = 17;
  public static final int TYPE_CAR_RENTAL = 18;
  public static final int TYPE_CAR_REPAIR = 19;
  public static final int TYPE_CAR_WASH = 20;
  public static final int TYPE_CASINO = 21;
  public static final int TYPE_CEMETERY = 22;
  public static final int TYPE_CHURCH = 23;
  public static final int TYPE_CITY_HALL = 24;
  public static final int TYPE_CLOTHING_STORE = 25;
  public static final int TYPE_COLLOQUIAL_AREA = 1004;
  public static final int TYPE_CONVENIENCE_STORE = 26;
  public static final int TYPE_COUNTRY = 1005;
  public static final int TYPE_COURTHOUSE = 27;
  public static final int TYPE_DENTIST = 28;
  public static final int TYPE_DEPARTMENT_STORE = 29;
  public static final int TYPE_DOCTOR = 30;
  public static final int TYPE_ELECTRICIAN = 31;
  public static final int TYPE_ELECTRONICS_STORE = 32;
  public static final int TYPE_EMBASSY = 33;
  public static final int TYPE_ESTABLISHMENT = 34;
  public static final int TYPE_FINANCE = 35;
  public static final int TYPE_FIRE_STATION = 36;
  public static final int TYPE_FLOOR = 1006;
  public static final int TYPE_FLORIST = 37;
  public static final int TYPE_FOOD = 38;
  public static final int TYPE_FUNERAL_HOME = 39;
  public static final int TYPE_FURNITURE_STORE = 40;
  public static final int TYPE_GAS_STATION = 41;
  public static final int TYPE_GENERAL_CONTRACTOR = 42;
  public static final int TYPE_GEOCODE = 1007;
  public static final int TYPE_GROCERY_OR_SUPERMARKET = 43;
  public static final int TYPE_GYM = 44;
  public static final int TYPE_HAIR_CARE = 45;
  public static final int TYPE_HARDWARE_STORE = 46;
  public static final int TYPE_HEALTH = 47;
  public static final int TYPE_HINDU_TEMPLE = 48;
  public static final int TYPE_HOME_GOODS_STORE = 49;
  public static final int TYPE_HOSPITAL = 50;
  public static final int TYPE_INSURANCE_AGENCY = 51;
  public static final int TYPE_INTERSECTION = 1008;
  public static final int TYPE_JEWELRY_STORE = 52;
  public static final int TYPE_LAUNDRY = 53;
  public static final int TYPE_LAWYER = 54;
  public static final int TYPE_LIBRARY = 55;
  public static final int TYPE_LIQUOR_STORE = 56;
  public static final int TYPE_LOCALITY = 1009;
  public static final int TYPE_LOCAL_GOVERNMENT_OFFICE = 57;
  public static final int TYPE_LOCKSMITH = 58;
  public static final int TYPE_LODGING = 59;
  public static final int TYPE_MEAL_DELIVERY = 60;
  public static final int TYPE_MEAL_TAKEAWAY = 61;
  public static final int TYPE_MOSQUE = 62;
  public static final int TYPE_MOVIE_RENTAL = 63;
  public static final int TYPE_MOVIE_THEATER = 64;
  public static final int TYPE_MOVING_COMPANY = 65;
  public static final int TYPE_MUSEUM = 66;
  public static final int TYPE_NATURAL_FEATURE = 1010;
  public static final int TYPE_NEIGHBORHOOD = 1011;
  public static final int TYPE_NIGHT_CLUB = 67;
  public static final int TYPE_OTHER = 0;
  public static final int TYPE_PAINTER = 68;
  public static final int TYPE_PARK = 69;
  public static final int TYPE_PARKING = 70;
  public static final int TYPE_PET_STORE = 71;
  public static final int TYPE_PHARMACY = 72;
  public static final int TYPE_PHYSIOTHERAPIST = 73;
  public static final int TYPE_PLACE_OF_WORSHIP = 74;
  public static final int TYPE_PLUMBER = 75;
  public static final int TYPE_POINT_OF_INTEREST = 1013;
  public static final int TYPE_POLICE = 76;
  public static final int TYPE_POLITICAL = 1012;
  public static final int TYPE_POSTAL_CODE = 1015;
  public static final int TYPE_POSTAL_CODE_PREFIX = 1016;
  public static final int TYPE_POSTAL_TOWN = 1017;
  public static final int TYPE_POST_BOX = 1014;
  public static final int TYPE_POST_OFFICE = 77;
  public static final int TYPE_PREMISE = 1018;
  public static final int TYPE_REAL_ESTATE_AGENCY = 78;
  public static final int TYPE_RESTAURANT = 79;
  public static final int TYPE_ROOFING_CONTRACTOR = 80;
  public static final int TYPE_ROOM = 1019;
  public static final int TYPE_ROUTE = 1020;
  public static final int TYPE_RV_PARK = 81;
  public static final int TYPE_SCHOOL = 82;
  public static final int TYPE_SHOE_STORE = 83;
  public static final int TYPE_SHOPPING_MALL = 84;
  public static final int TYPE_SPA = 85;
  public static final int TYPE_STADIUM = 86;
  public static final int TYPE_STORAGE = 87;
  public static final int TYPE_STORE = 88;
  public static final int TYPE_STREET_ADDRESS = 1021;
  public static final int TYPE_SUBLOCALITY = 1022;
  public static final int TYPE_SUBLOCALITY_LEVEL_1 = 1023;
  public static final int TYPE_SUBLOCALITY_LEVEL_2 = 1024;
  public static final int TYPE_SUBLOCALITY_LEVEL_3 = 1025;
  public static final int TYPE_SUBLOCALITY_LEVEL_4 = 1026;
  public static final int TYPE_SUBLOCALITY_LEVEL_5 = 1027;
  public static final int TYPE_SUBPREMISE = 1028;
  public static final int TYPE_SUBWAY_STATION = 89;
  public static final int TYPE_SYNAGOGUE = 90;
  public static final int TYPE_SYNTHETIC_GEOCODE = 1029;
  public static final int TYPE_TAXI_STAND = 91;
  public static final int TYPE_TRAIN_STATION = 92;
  public static final int TYPE_TRANSIT_STATION = 1030;
  public static final int TYPE_TRAVEL_AGENCY = 93;
  public static final int TYPE_UNIVERSITY = 94;
  public static final int TYPE_VETERINARY_CARE = 95;
  public static final int TYPE_ZOO = 96;

  public abstract CharSequence getAddress();

  public abstract String getId();

  public abstract LatLng getLatLng();

  public abstract Locale getLocale();

  public abstract CharSequence getName();

  public abstract CharSequence getPhoneNumber();

  public abstract List<Integer> getPlaceTypes();

  public abstract int getPriceLevel();

  public abstract float getRating();

  public abstract LatLngBounds getViewport();

  public abstract Uri getWebsiteUri();

  public abstract boolean zzsT();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.Place
 * JD-Core Version:    0.6.2
 */