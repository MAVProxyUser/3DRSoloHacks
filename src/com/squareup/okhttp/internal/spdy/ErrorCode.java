package com.squareup.okhttp.internal.spdy;

public enum ErrorCode
{
  public final int httpCode;
  public final int spdyGoAwayCode;
  public final int spdyRstCode;

  static
  {
    INVALID_STREAM = new ErrorCode("INVALID_STREAM", 2, 1, 2, -1);
    UNSUPPORTED_VERSION = new ErrorCode("UNSUPPORTED_VERSION", 3, 1, 4, -1);
    STREAM_IN_USE = new ErrorCode("STREAM_IN_USE", 4, 1, 8, -1);
    STREAM_ALREADY_CLOSED = new ErrorCode("STREAM_ALREADY_CLOSED", 5, 1, 9, -1);
    INTERNAL_ERROR = new ErrorCode("INTERNAL_ERROR", 6, 2, 6, 2);
    FLOW_CONTROL_ERROR = new ErrorCode("FLOW_CONTROL_ERROR", 7, 3, 7, -1);
    STREAM_CLOSED = new ErrorCode("STREAM_CLOSED", 8, 5, -1, -1);
    FRAME_TOO_LARGE = new ErrorCode("FRAME_TOO_LARGE", 9, 6, 11, -1);
    REFUSED_STREAM = new ErrorCode("REFUSED_STREAM", 10, 7, 3, -1);
    CANCEL = new ErrorCode("CANCEL", 11, 8, 5, -1);
    COMPRESSION_ERROR = new ErrorCode("COMPRESSION_ERROR", 12, 9, -1, -1);
    CONNECT_ERROR = new ErrorCode("CONNECT_ERROR", 13, 10, -1, -1);
    ENHANCE_YOUR_CALM = new ErrorCode("ENHANCE_YOUR_CALM", 14, 11, -1, -1);
    INADEQUATE_SECURITY = new ErrorCode("INADEQUATE_SECURITY", 15, 12, -1, -1);
    HTTP_1_1_REQUIRED = new ErrorCode("HTTP_1_1_REQUIRED", 16, 13, -1, -1);
    INVALID_CREDENTIALS = new ErrorCode("INVALID_CREDENTIALS", 17, -1, 10, -1);
    ErrorCode[] arrayOfErrorCode = new ErrorCode[18];
    arrayOfErrorCode[0] = NO_ERROR;
    arrayOfErrorCode[1] = PROTOCOL_ERROR;
    arrayOfErrorCode[2] = INVALID_STREAM;
    arrayOfErrorCode[3] = UNSUPPORTED_VERSION;
    arrayOfErrorCode[4] = STREAM_IN_USE;
    arrayOfErrorCode[5] = STREAM_ALREADY_CLOSED;
    arrayOfErrorCode[6] = INTERNAL_ERROR;
    arrayOfErrorCode[7] = FLOW_CONTROL_ERROR;
    arrayOfErrorCode[8] = STREAM_CLOSED;
    arrayOfErrorCode[9] = FRAME_TOO_LARGE;
    arrayOfErrorCode[10] = REFUSED_STREAM;
    arrayOfErrorCode[11] = CANCEL;
    arrayOfErrorCode[12] = COMPRESSION_ERROR;
    arrayOfErrorCode[13] = CONNECT_ERROR;
    arrayOfErrorCode[14] = ENHANCE_YOUR_CALM;
    arrayOfErrorCode[15] = INADEQUATE_SECURITY;
    arrayOfErrorCode[16] = HTTP_1_1_REQUIRED;
    arrayOfErrorCode[17] = INVALID_CREDENTIALS;
  }

  private ErrorCode(int paramInt1, int paramInt2, int paramInt3)
  {
    this.httpCode = paramInt1;
    this.spdyRstCode = paramInt2;
    this.spdyGoAwayCode = paramInt3;
  }

  public static ErrorCode fromHttp2(int paramInt)
  {
    for (ErrorCode localErrorCode : values())
      if (localErrorCode.httpCode == paramInt)
        return localErrorCode;
    return null;
  }

  public static ErrorCode fromSpdy3Rst(int paramInt)
  {
    for (ErrorCode localErrorCode : values())
      if (localErrorCode.spdyRstCode == paramInt)
        return localErrorCode;
    return null;
  }

  public static ErrorCode fromSpdyGoAway(int paramInt)
  {
    for (ErrorCode localErrorCode : values())
      if (localErrorCode.spdyGoAwayCode == paramInt)
        return localErrorCode;
    return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.ErrorCode
 * JD-Core Version:    0.6.2
 */