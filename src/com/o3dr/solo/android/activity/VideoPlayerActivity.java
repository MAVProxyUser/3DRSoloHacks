package com.o3dr.solo.android.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import com.o3dr.solo.android.widget.NiceProgressView;

public class VideoPlayerActivity extends BaseActivity
  implements MediaPlayer.OnCompletionListener
{
  public static final String EXTRA_VIDEO_URL = "extra_video_url";

  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903080);
    final NiceProgressView localNiceProgressView = (NiceProgressView)findViewById(2131493062);
    localNiceProgressView.setVisibility(0);
    final VideoView localVideoView = (VideoView)findViewById(2131493061);
    localVideoView.setVideoURI(Uri.parse(getIntent().getExtras().getString("extra_video_url")));
    localVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
    {
      public void onPrepared(MediaPlayer paramAnonymousMediaPlayer)
      {
        paramAnonymousMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener()
        {
          public void onVideoSizeChanged(MediaPlayer paramAnonymous2MediaPlayer, int paramAnonymous2Int1, int paramAnonymous2Int2)
          {
            MediaController localMediaController = new MediaController(VideoPlayerActivity.this);
            VideoPlayerActivity.1.this.val$video.setMediaController(localMediaController);
            localMediaController.setAnchorView(VideoPlayerActivity.1.this.val$video);
          }
        });
        localNiceProgressView.setVisibility(8);
      }
    });
    localVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener()
    {
      public boolean onInfo(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        switch (paramAnonymousInt1)
        {
        default:
          return true;
        case 701:
          localNiceProgressView.setVisibility(0);
          return true;
        case 702:
        }
        localNiceProgressView.setVisibility(8);
        return true;
      }
    });
    localVideoView.setOnCompletionListener(this);
    localVideoView.requestFocus();
    localVideoView.start();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.VideoPlayerActivity
 * JD-Core Version:    0.6.2
 */