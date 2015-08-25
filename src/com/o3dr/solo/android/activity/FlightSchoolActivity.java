package com.o3dr.solo.android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.o3dr.solo.android.adapter.RecyclerCardViewAdapter;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.util.NetworkUtils;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FlightSchoolActivity extends BaseActivity
{
  public static final String JSON_SCHOOL_ARRAY_TAG = "results";
  public static final String JSON_SCHOOL_THUMBNAIL_TAG = "thumbnail";
  public static final String JSON_SCHOOL_TITLE_TAG = "title";
  public static final String JSON_SCHOOL_VIDEO_TAG = "filename";
  private static final String SOLO_INFO_URL_PATH = "videos/";
  public static final String SOLO_SERVER_API_TOKEN = "431055c7bf461cf689453dfca6101d143914c494";
  private static final Header SOLO_SERVER_AUTHORIZATION_HEADER = new BasicHeader("Authorization", "Token 431055c7bf461cf689453dfca6101d143914c494");
  public static final String SOLO_SERVER_HOST = "http://takeoff.3dr.com/";
  private static final String TAG = FlightSchoolActivity.class.getSimpleName();
  private FetchVideos aSyncTask;
  private View requestFailed;
  private RecyclerView videoContainer;
  private final List<VideoThumbnail> videoThumbnailList = new ArrayList();
  private View waitForServerResponse;

  private boolean fetchFlightSchoolVideos()
  {
    if (!NetworkUtils.isNetworkAvailable(getApplicationContext()))
      return false;
    try
    {
      DefaultHttpClient localDefaultHttpClient = NetworkUtils.getHttpClient();
      String str1 = getVideoUrl();
      Log.d(TAG, "Checking for videoThumbnailList @ " + str1);
      HttpGet localHttpGet = new HttpGet(str1);
      localHttpGet.setHeader(SOLO_SERVER_AUTHORIZATION_HEADER);
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
      int i = localHttpResponse.getStatusLine().getStatusCode();
      if (i == 200)
      {
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity == null)
          break label299;
        String str2 = EntityUtils.toString(localHttpEntity, "UTF-8");
        Log.d(TAG, "Server responded with: " + str2);
        JSONArray localJSONArray = new JSONObject(str2).optJSONArray("results");
        if (localJSONArray != null)
        {
          int j = localJSONArray.length();
          k = 0;
          if (k >= j)
            break label299;
          JSONObject localJSONObject = localJSONArray.optJSONObject(k);
          if (localJSONObject != null)
            this.videoThumbnailList.add(new VideoThumbnail(localJSONObject));
        }
        else
        {
          Log.d(TAG, "No available videoThumbnailList to download.");
          break label299;
        }
      }
      else
      {
        Log.d(TAG, "No response was obtained from the server. Status Code: " + i);
        return false;
      }
    }
    catch (IOException localIOException)
    {
      while (true)
        Log.e(TAG, "Unable to access firmware server.", localIOException);
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        int k;
        Log.e(TAG, "Unable to parse the server response.", localJSONException);
        continue;
        k++;
      }
    }
    label299: return true;
  }

  private String getVideoUrl()
  {
    return "http://takeoff.3dr.com/videos/";
  }

  private void onVideosFailed()
  {
    Log.e(TAG, "We could not contact the server");
    resetSectionViews();
    if (this.requestFailed != null)
      this.requestFailed.setVisibility(0);
  }

  private void onVideosRetrieved()
  {
    Log.d(TAG, "Latest videoThumbnailList retrieved");
    resetSectionViews();
    if (this.videoContainer != null)
    {
      this.videoContainer.getAdapter().notifyDataSetChanged();
      this.videoContainer.setVisibility(0);
    }
  }

  private void requestConnectivityIfNeeded()
  {
    Context localContext = getApplicationContext();
    if (localContext == null);
    while (!isArtooLinkConnected())
      return;
    Log.d(TAG, "Requesting internet connection for update downloads.");
    startActivity(new Intent(localContext, WifiSettingsAccess.class).putExtra("extra_title", getString(2131100035)).putExtra("extra_message", getString(2131100039)).putExtra("extra_picture", true).putExtra("extra_button_text", getString(2131100041)));
  }

  private void resetSectionViews()
  {
    if (this.waitForServerResponse != null)
      this.waitForServerResponse.setVisibility(8);
    if (this.videoContainer != null)
      this.videoContainer.setVisibility(8);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903070);
    Context localContext = getApplicationContext();
    setSupportActionBar((Toolbar)findViewById(2131492978));
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
    {
      localActionBar.setDisplayShowTitleEnabled(true);
      localActionBar.setDisplayShowHomeEnabled(true);
      localActionBar.setDisplayHomeAsUpEnabled(true);
    }
    this.videoContainer = ((RecyclerView)findViewById(2131493014));
    this.videoContainer.setHasFixedSize(true);
    GridLayoutManager localGridLayoutManager = new GridLayoutManager(localContext, getResources().getInteger(2131361793));
    this.videoContainer.setLayoutManager(localGridLayoutManager);
    RecyclerCardViewAdapter localRecyclerCardViewAdapter = new RecyclerCardViewAdapter(this.videoThumbnailList, localContext, this.soloApp.getAppDB());
    this.videoContainer.setAdapter(localRecyclerCardViewAdapter);
    this.waitForServerResponse = findViewById(2131493012);
    this.waitForServerResponse.setVisibility(0);
    this.requestFailed = findViewById(2131493009);
    this.aSyncTask = new FetchVideos(this, null);
    this.aSyncTask.execute(new Void[0]);
  }

  public void onDestroy()
  {
    super.onDestroy();
    if (this.aSyncTask != null)
      this.aSyncTask.cancel(true);
  }

  public void onStop()
  {
    super.onStop();
    ImageLoader.getInstance().stop();
  }

  private static class FetchVideos extends AsyncTask<Void, Void, Boolean>
  {
    private WeakReference<FlightSchoolActivity> weakActivity;

    private FetchVideos(FlightSchoolActivity paramFlightSchoolActivity)
    {
      this.weakActivity = new WeakReference(paramFlightSchoolActivity);
    }

    protected Boolean doInBackground(Void[] paramArrayOfVoid)
    {
      FlightSchoolActivity localFlightSchoolActivity = (FlightSchoolActivity)this.weakActivity.get();
      if ((localFlightSchoolActivity != null) && (localFlightSchoolActivity.fetchFlightSchoolVideos()));
      for (boolean bool = true; ; bool = false)
        return Boolean.valueOf(bool);
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      FlightSchoolActivity localFlightSchoolActivity = (FlightSchoolActivity)this.weakActivity.get();
      if (localFlightSchoolActivity != null)
      {
        if (paramBoolean.booleanValue())
          localFlightSchoolActivity.onVideosRetrieved();
      }
      else
        return;
      localFlightSchoolActivity.onVideosFailed();
    }
  }

  public static class VideoThumbnail
  {
    private URL thumbnailUrl;
    private String videoTitle;
    private String videoURL;

    public VideoThumbnail(String paramString1, URL paramURL, String paramString2)
    {
      this.videoTitle = paramString1;
      this.thumbnailUrl = paramURL;
      this.videoURL = paramString2;
    }

    public VideoThumbnail(JSONObject paramJSONObject)
    {
      try
      {
        this.videoTitle = paramJSONObject.getString("title");
        this.thumbnailUrl = new URL(paramJSONObject.getString("thumbnail"));
        this.videoURL = paramJSONObject.getString("filename");
        return;
      }
      catch (JSONException localJSONException)
      {
        Log.e(FlightSchoolActivity.TAG, "Unable to load JSON object. Object likely to be malformed", localJSONException);
        return;
      }
      catch (MalformedURLException localMalformedURLException)
      {
        Log.e(FlightSchoolActivity.TAG, "Unable to create URL. Likely to be malformed", localMalformedURLException);
      }
    }

    public URL getThumbnailUrl()
    {
      return this.thumbnailUrl;
    }

    public String getVideoTitle()
    {
      return this.videoTitle;
    }

    public String getVideoURL()
    {
      return this.videoURL;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.FlightSchoolActivity
 * JD-Core Version:    0.6.2
 */