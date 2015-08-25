package com.google.android.gms.common.api;

public class CommonStatusCodes
{
  public static final int API_NOT_AVAILABLE = 17;
  public static final int CANCELED = 16;
  public static final int DEVELOPER_ERROR = 10;
  public static final int ERROR = 13;
  public static final int INTERNAL_ERROR = 8;
  public static final int INTERRUPTED = 14;
  public static final int INVALID_ACCOUNT = 5;
  public static final int LICENSE_CHECK_FAILED = 11;
  public static final int NETWORK_ERROR = 7;
  public static final int RESOLUTION_REQUIRED = 6;
  public static final int SERVICE_DISABLED = 3;
  public static final int SERVICE_INVALID = 9;
  public static final int SERVICE_MISSING = 1;
  public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
  public static final int SIGN_IN_REQUIRED = 4;
  public static final int SUCCESS = 0;
  public static final int SUCCESS_CACHE = -1;
  public static final int TIMEOUT = 15;

  public static String getStatusCodeString(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return "unknown status code: " + paramInt;
    case -1:
      return "SUCCESS_CACHE";
    case 0:
      return "SUCCESS";
    case 1:
      return "SERVICE_MISSING";
    case 2:
      return "SERVICE_VERSION_UPDATE_REQUIRED";
    case 3:
      return "SERVICE_DISABLED";
    case 4:
      return "SIGN_IN_REQUIRED";
    case 5:
      return "INVALID_ACCOUNT";
    case 6:
      return "RESOLUTION_REQUIRED";
    case 7:
      return "NETWORK_ERROR";
    case 8:
      return "INTERNAL_ERROR";
    case 9:
      return "SERVICE_INVALID";
    case 10:
      return "DEVELOPER_ERROR";
    case 11:
      return "LICENSE_CHECK_FAILED";
    case 13:
      return "ERROR_OPERATION_FAILED";
    case 14:
      return "INTERRUPTED";
    case 15:
      return "TIMEOUT";
    case 16:
      return "CANCELED";
    case 3000:
      return "AUTH_API_INVALID_CREDENTIALS";
    case 3001:
      return "AUTH_API_ACCESS_FORBIDDEN";
    case 3002:
      return "AUTH_API_CLIENT_ERROR";
    case 3003:
      return "AUTH_API_SERVER_ERROR";
    case 3004:
      return "AUTH_TOKEN_ERROR";
    case 3005:
    }
    return "AUTH_URL_RESOLUTION";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.CommonStatusCodes
 * JD-Core Version:    0.6.2
 */