package com.segment.analytics;

import java.util.List;
import java.util.Map;

public class Properties extends ValueMap
{
  private static final String CATEGORY_KEY = "category";
  private static final String COUPON_KEY = "coupon";
  private static final String CURRENCY_KEY = "currency";
  private static final String DISCOUNT_KEY = "discount";
  private static final String ID_KEY = "id";
  private static final String NAME_KEY = "name";
  private static final String ORDER_ID_KEY = "orderId";
  private static final String PATH_KEY = "path";
  private static final String PRICE_KEY = "price";
  private static final String PRODUCTS_KEY = "products";
  private static final String REFERRER_KEY = "referrer";
  private static final String REPEAT_KEY = "repeat";
  private static final String REVENUE_KEY = "revenue";
  private static final String SHIPPING_KEY = "shipping";
  private static final String SKU_KEY = "sku";
  private static final String SUBTOTAL_KEY = "subtotal";
  private static final String TAX_KEY = "tax";
  private static final String TITLE_KEY = "title";
  private static final String TOTAL_KEY = "total";
  private static final String URL_KEY = "url";
  private static final String VALUE_KEY = "value";

  public Properties()
  {
  }

  Properties(Map<String, Object> paramMap)
  {
    super(paramMap);
  }

  public String category()
  {
    return getString("category");
  }

  public String currency()
  {
    return getString("currency");
  }

  public double discount()
  {
    return getDouble("discount", 0.0D);
  }

  public boolean isRepeatCustomer()
  {
    return getBoolean("repeat", false);
  }

  public String name()
  {
    return getString("name");
  }

  public String orderId()
  {
    return getString("orderId");
  }

  public String path()
  {
    return getString("path");
  }

  public double price()
  {
    return getDouble("price", 0.0D);
  }

  public String productId()
  {
    return getString("id");
  }

  public List<Product> products(Product[] paramArrayOfProduct)
  {
    return getList("products", Product.class);
  }

  public Properties putCategory(String paramString)
  {
    return putValue("category", paramString);
  }

  public Properties putCoupon(String paramString)
  {
    return putValue("coupon", paramString);
  }

  public Properties putCurrency(String paramString)
  {
    return putValue("currency", paramString);
  }

  public Properties putDiscount(double paramDouble)
  {
    return putValue("discount", Double.valueOf(paramDouble));
  }

  public Properties putName(String paramString)
  {
    return putValue("name", paramString);
  }

  public Properties putOrderId(String paramString)
  {
    return putValue("orderId", paramString);
  }

  public Properties putPath(String paramString)
  {
    return putValue("path", paramString);
  }

  public Properties putPrice(double paramDouble)
  {
    return putValue("price", Double.valueOf(paramDouble));
  }

  public Properties putProductId(String paramString)
  {
    return putValue("id", paramString);
  }

  public Properties putProducts(Product[] paramArrayOfProduct)
  {
    return putValue("products", paramArrayOfProduct);
  }

  public Properties putReferrer(String paramString)
  {
    return putValue("referrer", paramString);
  }

  public Properties putRepeatCustomer(boolean paramBoolean)
  {
    return putValue("repeat", Boolean.valueOf(paramBoolean));
  }

  public Properties putRevenue(double paramDouble)
  {
    return putValue("revenue", Double.valueOf(paramDouble));
  }

  public Properties putShipping(double paramDouble)
  {
    return putValue("shipping", Double.valueOf(paramDouble));
  }

  public Properties putSku(String paramString)
  {
    return putValue("sku", paramString);
  }

  public double putSubtotal()
  {
    return getDouble("subtotal", 0.0D);
  }

  public Properties putSubtotal(double paramDouble)
  {
    return putValue("subtotal", Double.valueOf(paramDouble));
  }

  public Properties putTax(double paramDouble)
  {
    return putValue("tax", Double.valueOf(paramDouble));
  }

  public Properties putTitle(String paramString)
  {
    return putValue("title", paramString);
  }

  public Properties putTotal(double paramDouble)
  {
    return putValue("total", Double.valueOf(paramDouble));
  }

  public Properties putUrl(String paramString)
  {
    return putValue("url", paramString);
  }

  public Properties putValue(double paramDouble)
  {
    return putValue("value", Double.valueOf(paramDouble));
  }

  public Properties putValue(String paramString, Object paramObject)
  {
    super.putValue(paramString, paramObject);
    return this;
  }

  public String referrer()
  {
    return getString("referrer");
  }

  public double revenue()
  {
    return getDouble("revenue", 0.0D);
  }

  double shipping()
  {
    return getDouble("shipping", 0.0D);
  }

  public String sku()
  {
    return getString("sku");
  }

  public double tax()
  {
    return getDouble("tax", 0.0D);
  }

  public String title()
  {
    return getString("title");
  }

  public double total()
  {
    return getDouble("total", 0.0D);
  }

  public String url()
  {
    return getString("url");
  }

  public double value()
  {
    return getDouble("value", 0.0D);
  }

  public static class Product extends ValueMap
  {
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String PRICE_KEY = "price";
    private static final String SKU_KEY = "sku";

    public Product(String paramString1, String paramString2, double paramDouble)
    {
      put("id", paramString1);
      put("sku", paramString2);
      put("price", Double.valueOf(paramDouble));
    }

    private Product(Map<String, Object> paramMap)
    {
      super();
    }

    public String id()
    {
      return getString("id");
    }

    public String name()
    {
      return getString("name");
    }

    public double price()
    {
      return getDouble("price", 0.0D);
    }

    public Product putName(String paramString)
    {
      return putValue("name", paramString);
    }

    public Product putValue(String paramString, Object paramObject)
    {
      super.putValue(paramString, paramObject);
      return this;
    }

    public String sku()
    {
      return getString("sku");
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.Properties
 * JD-Core Version:    0.6.2
 */