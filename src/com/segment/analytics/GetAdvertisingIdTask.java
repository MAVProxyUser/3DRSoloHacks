package com.segment.analytics;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import java.lang.reflect.Method;

class GetAdvertisingIdTask extends AsyncTask<Context, Void, Pair<String, Boolean>>
{
  final AnalyticsContext analyticsContext;

  GetAdvertisingIdTask(AnalyticsContext paramAnalyticsContext)
  {
    this.analyticsContext = paramAnalyticsContext;
  }

  protected Pair<String, Boolean> doInBackground(Context[] paramArrayOfContext)
  {
    Context localContext = paramArrayOfContext[0];
    try
    {
      Object localObject = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[] { Context.class }).invoke(null, new Object[] { localContext });
      Boolean localBoolean = (Boolean)localObject.getClass().getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(localObject, new Object[0]);
      Pair localPair = Pair.create((String)localObject.getClass().getMethod("getId", new Class[0]).invoke(localObject, new Object[0]), localBoolean);
      return localPair;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  protected void onPostExecute(Pair<String, Boolean> paramPair)
  {
    super.onPostExecute(paramPair);
    if (paramPair != null)
      this.analyticsContext.device().putAdvertisingInfo((String)paramPair.first, ((Boolean)paramPair.second).booleanValue());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.GetAdvertisingIdTask
 * JD-Core Version:    0.6.2
 */