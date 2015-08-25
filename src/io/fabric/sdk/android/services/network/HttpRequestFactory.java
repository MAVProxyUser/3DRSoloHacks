package io.fabric.sdk.android.services.network;

import java.util.Map;

public abstract interface HttpRequestFactory
{
  public abstract HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString);

  public abstract HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString, Map<String, String> paramMap);

  public abstract PinningInfoProvider getPinningInfoProvider();

  public abstract void setPinningInfoProvider(PinningInfoProvider paramPinningInfoProvider);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.network.HttpRequestFactory
 * JD-Core Version:    0.6.2
 */