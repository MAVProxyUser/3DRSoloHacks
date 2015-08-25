package com.o3dr.solo.android.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class HomeActivity extends NavigationDrawerActivity
{
  private static final String TAG = HomeActivity.class.getSimpleName();
  private VideoView homeVideo;
  private View navAlert;

  protected void enableNavigationAlertIcon(boolean paramBoolean)
  {
    View localView;
    if (this.navAlert != null)
    {
      localView = this.navAlert;
      if (!paramBoolean)
        break label24;
    }
    label24: for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  protected int getNavigationDrawerEntryId()
  {
    return 2131493029;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903071);
    final Context localContext = getApplicationContext();
    ((ImageView)findViewById(2131493016)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        HomeActivity.this.openDrawer();
      }
    });
    this.navAlert = findViewById(2131493017);
    this.navAlert.setVisibility(8);
    ((TextView)findViewById(2131493020)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        HomeActivity.this.startActivity(new Intent(localContext, FlightActivity.class));
      }
    });
    ((TextView)findViewById(2131493021)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        HomeActivity.this.startActivity(new Intent(localContext, FlightSchoolActivity.class));
      }
    });
    this.homeVideo = ((VideoView)findViewById(2131493015));
    this.homeVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
    {
      public void onPrepared(MediaPlayer paramAnonymousMediaPlayer)
      {
        paramAnonymousMediaPlayer.setVolume(0.0F, 0.0F);
        paramAnonymousMediaPlayer.setLooping(true);
      }
    });
    this.homeVideo.setOnErrorListener(new MediaPlayer.OnErrorListener()
    {
      public boolean onError(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Log.w(HomeActivity.TAG, "Issue with playing home video. Attempting to fix.");
        switch (paramAnonymousInt1)
        {
        default:
          return false;
        case 1:
        }
        Log.d(HomeActivity.TAG, "Restarting home video.");
        HomeActivity.this.homeVideo.setVideoURI(Uri.parse("android.resource://" + HomeActivity.this.getPackageName() + "/" + 2131034123));
        HomeActivity.this.homeVideo.start();
        return true;
      }
    });
    this.homeVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + 2131034123));
  }

  public void onPause()
  {
    super.onPause();
    this.homeVideo.suspend();
  }

  public void onResume()
  {
    super.onResume();
    this.homeVideo.start();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.HomeActivity
 * JD-Core Version:    0.6.2
 */