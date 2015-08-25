package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class FormEncodingBuilder
{
  private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
  private final StringBuilder content = new StringBuilder();

  public FormEncodingBuilder add(String paramString1, String paramString2)
  {
    if (this.content.length() > 0)
      this.content.append('&');
    try
    {
      this.content.append(URLEncoder.encode(paramString1, "UTF-8")).append('=').append(URLEncoder.encode(paramString2, "UTF-8"));
      return this;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new AssertionError(localUnsupportedEncodingException);
    }
  }

  public RequestBody build()
  {
    if (this.content.length() == 0)
      throw new IllegalStateException("Form encoded body must have at least one part.");
    byte[] arrayOfByte = this.content.toString().getBytes(Util.UTF_8);
    return RequestBody.create(CONTENT_TYPE, arrayOfByte);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.FormEncodingBuilder
 * JD-Core Version:    0.6.2
 */