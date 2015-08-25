package com.segment.analytics;

import android.content.Context;
import com.segment.analytics.internal.Utils;
import com.segment.analytics.internal.Utils.NullableConcurrentHashMap;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Traits extends ValueMap
{
  private static final String ADDRESS_KEY = "address";
  private static final String AGE_KEY = "age";
  private static final String ANONYMOUS_ID_KEY = "anonymousId";
  private static final String AVATAR_KEY = "avatar";
  private static final String BIRTHDAY_KEY = "birthday";
  private static final String CREATED_AT_KEY = "createdAt";
  private static final String DESCRIPTION_KEY = "description";
  private static final String EMAIL_KEY = "email";
  private static final String EMPLOYEES_KEY = "employees";
  private static final String FAX_KEY = "fax";
  private static final String FIRST_NAME_KEY = "firstName";
  private static final String GENDER_KEY = "gender";
  private static final String INDUSTRY_KEY = "industry";
  private static final String LAST_NAME_KEY = "lastName";
  private static final String NAME_KEY = "name";
  private static final String PHONE_KEY = "phone";
  private static final String TITLE_KEY = "title";
  private static final String USERNAME_KEY = "username";
  private static final String USER_ID_KEY = "userId";
  private static final String WEBSITE_KEY = "website";

  public Traits()
  {
  }

  private Traits(Map<String, Object> paramMap)
  {
    super(paramMap);
  }

  static Traits create()
  {
    Traits localTraits = new Traits(new Utils.NullableConcurrentHashMap());
    localTraits.putAnonymousId(UUID.randomUUID().toString());
    return localTraits;
  }

  public Address address()
  {
    return (Address)getValueMap("address", Address.class);
  }

  public int age()
  {
    return getInt("age", 0);
  }

  public String anonymousId()
  {
    return getString("anonymousId");
  }

  public String avatar()
  {
    return getString("avatar");
  }

  public Date birthday()
  {
    try
    {
      Date localDate = Utils.toISO8601Date(getString("birthday"));
      return localDate;
    }
    catch (ParseException localParseException)
    {
    }
    return null;
  }

  public String createdAt()
  {
    return getString("createdAt");
  }

  public String currentId()
  {
    String str = userId();
    if (Utils.isNullOrEmpty(str))
      str = anonymousId();
    return str;
  }

  public String description()
  {
    return getString("description");
  }

  public String email()
  {
    return getString("email");
  }

  public long employees()
  {
    return getLong("employees", 0L);
  }

  public String fax()
  {
    return getString("fax");
  }

  public String firstName()
  {
    return getString("firstName");
  }

  public String gender()
  {
    return getString("gender");
  }

  public String industry()
  {
    return getString("industry");
  }

  public String lastName()
  {
    return getString("lastName");
  }

  public String name()
  {
    String str1 = getString("name");
    if ((Utils.isNullOrEmpty(str1)) && (Utils.isNullOrEmpty(firstName())) && (Utils.isNullOrEmpty(lastName())))
      str1 = null;
    while (!Utils.isNullOrEmpty(str1))
      return str1;
    StringBuilder localStringBuilder = new StringBuilder();
    String str2 = firstName();
    boolean bool = Utils.isNullOrEmpty(str2);
    int i = 0;
    if (!bool)
    {
      i = 1;
      localStringBuilder.append(str2);
    }
    String str3 = lastName();
    if (!Utils.isNullOrEmpty(str3))
    {
      if (i != 0)
        localStringBuilder.append(' ');
      localStringBuilder.append(str3);
    }
    return localStringBuilder.toString();
  }

  public String phone()
  {
    return getString("phone");
  }

  public Traits putAddress(Address paramAddress)
  {
    return putValue("address", paramAddress);
  }

  public Traits putAge(int paramInt)
  {
    return putValue("age", Integer.valueOf(paramInt));
  }

  Traits putAnonymousId(String paramString)
  {
    return putValue("anonymousId", paramString);
  }

  public Traits putAvatar(String paramString)
  {
    return putValue("avatar", paramString);
  }

  public Traits putBirthday(Date paramDate)
  {
    return putValue("birthday", Utils.toISO8601Date(paramDate));
  }

  public Traits putCreatedAt(String paramString)
  {
    return putValue("createdAt", paramString);
  }

  public Traits putDescription(String paramString)
  {
    return putValue("description", paramString);
  }

  public Traits putEmail(String paramString)
  {
    return putValue("email", paramString);
  }

  public Traits putEmployees(long paramLong)
  {
    return putValue("employees", Long.valueOf(paramLong));
  }

  public Traits putFax(String paramString)
  {
    return putValue("fax", paramString);
  }

  public Traits putFirstName(String paramString)
  {
    return putValue("firstName", paramString);
  }

  public Traits putGender(String paramString)
  {
    return putValue("gender", paramString);
  }

  public Traits putIndustry(String paramString)
  {
    return putValue("industry", paramString);
  }

  public Traits putLastName(String paramString)
  {
    return putValue("lastName", paramString);
  }

  public Traits putName(String paramString)
  {
    return putValue("name", paramString);
  }

  public Traits putPhone(String paramString)
  {
    return putValue("phone", paramString);
  }

  public Traits putTitle(String paramString)
  {
    return putValue("title", paramString);
  }

  Traits putUserId(String paramString)
  {
    return putValue("userId", paramString);
  }

  public Traits putUsername(String paramString)
  {
    return putValue("username", paramString);
  }

  public Traits putValue(String paramString, Object paramObject)
  {
    super.putValue(paramString, paramObject);
    return this;
  }

  public Traits putWebsite(String paramString)
  {
    return putValue("website", paramString);
  }

  public String title()
  {
    return getString("title");
  }

  public Traits unmodifiableCopy()
  {
    return new Traits(Collections.unmodifiableMap(new LinkedHashMap(this)));
  }

  public String userId()
  {
    return getString("userId");
  }

  public String username()
  {
    return getString("username");
  }

  public String website()
  {
    return getString("website");
  }

  public static class Address extends ValueMap
  {
    private static final String ADDRESS_CITY_KEY = "city";
    private static final String ADDRESS_COUNTRY_KEY = "country";
    private static final String ADDRESS_POSTAL_CODE_KEY = "postalCode";
    private static final String ADDRESS_STATE_KEY = "state";
    private static final String ADDRESS_STREET_KEY = "street";

    public Address()
    {
    }

    public Address(Map<String, Object> paramMap)
    {
      super();
    }

    public String city()
    {
      return getString("city");
    }

    public String country()
    {
      return getString("country");
    }

    public String postalCode()
    {
      return getString("postalCode");
    }

    public Address putCity(String paramString)
    {
      return putValue("city", paramString);
    }

    public Address putCountry(String paramString)
    {
      return putValue("country", paramString);
    }

    public Address putPostalCode(String paramString)
    {
      return putValue("postalCode", paramString);
    }

    public Address putState(String paramString)
    {
      return putValue("state", paramString);
    }

    public Address putStreet(String paramString)
    {
      return putValue("street", paramString);
    }

    public Address putValue(String paramString, Object paramObject)
    {
      super.putValue(paramString, paramObject);
      return this;
    }

    public String state()
    {
      return getString("state");
    }

    public String street()
    {
      return getString("street");
    }
  }

  static class Cache extends ValueMap.Cache<Traits>
  {
    private static final String TRAITS_CACHE_PREFIX = "traits-";

    Cache(Context paramContext, Cartographer paramCartographer, String paramString)
    {
      super(paramCartographer, "traits-" + paramString, Traits.class);
    }

    public Traits create(Map<String, Object> paramMap)
    {
      return new Traits(new Utils.NullableConcurrentHashMap(paramMap), null);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.Traits
 * JD-Core Version:    0.6.2
 */