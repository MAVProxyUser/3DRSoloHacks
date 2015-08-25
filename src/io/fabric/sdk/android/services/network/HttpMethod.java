package io.fabric.sdk.android.services.network;

public enum HttpMethod
{
  static
  {
    DELETE = new HttpMethod("DELETE", 3);
    HttpMethod[] arrayOfHttpMethod = new HttpMethod[4];
    arrayOfHttpMethod[0] = GET;
    arrayOfHttpMethod[1] = POST;
    arrayOfHttpMethod[2] = PUT;
    arrayOfHttpMethod[3] = DELETE;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.network.HttpMethod
 * JD-Core Version:    0.6.2
 */