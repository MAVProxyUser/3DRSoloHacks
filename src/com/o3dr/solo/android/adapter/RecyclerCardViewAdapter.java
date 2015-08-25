package com.o3dr.solo.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.o3dr.solo.android.activity.FlightSchoolActivity.VideoThumbnail;
import com.o3dr.solo.android.activity.VideoPlayerActivity;
import com.o3dr.solo.android.util.database.SoloDb;
import java.net.URL;
import java.util.List;

public class RecyclerCardViewAdapter extends RecyclerView.Adapter<ThumbnailCardViewHolder>
{
  private static final String TAG = RecyclerCardViewAdapter.class.getSimpleName();
  private final SoloDb appDB;
  private final Context context;
  private final ImageLoader imageLoader = ImageLoader.getInstance();
  private final DisplayImageOptions options;
  private final List<FlightSchoolActivity.VideoThumbnail> videos;

  public RecyclerCardViewAdapter(List<FlightSchoolActivity.VideoThumbnail> paramList, Context paramContext, SoloDb paramSoloDb)
  {
    this.videos = paramList;
    this.context = paramContext;
    this.appDB = paramSoloDb;
    this.options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(false).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
  }

  public int getItemCount()
  {
    return this.videos.size();
  }

  public void onBindViewHolder(final ThumbnailCardViewHolder paramThumbnailCardViewHolder, int paramInt)
  {
    FlightSchoolActivity.VideoThumbnail localVideoThumbnail = (FlightSchoolActivity.VideoThumbnail)this.videos.get(paramInt);
    String str1 = localVideoThumbnail.getThumbnailUrl().toString();
    if ((paramThumbnailCardViewHolder.imageUri != null) && (!paramThumbnailCardViewHolder.imageUri.equals(str1)))
      this.imageLoader.cancelDisplayTask(paramThumbnailCardViewHolder.thumbnail);
    this.imageLoader.displayImage(str1, paramThumbnailCardViewHolder.thumbnail, this.options);
    ThumbnailCardViewHolder.access$002(paramThumbnailCardViewHolder, str1);
    final String str2 = localVideoThumbnail.getVideoURL();
    paramThumbnailCardViewHolder.videoTitle.setText(localVideoThumbnail.getVideoTitle());
    paramThumbnailCardViewHolder.videoTitle.setActivated(this.appDB.wasVideoSeen(str2));
    paramThumbnailCardViewHolder.cardView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(RecyclerCardViewAdapter.this.context, VideoPlayerActivity.class).addFlags(268435456);
        localIntent.putExtra("extra_video_url", str2);
        RecyclerCardViewAdapter.this.appDB.writeVideoInfo(System.currentTimeMillis(), str2);
        RecyclerCardViewAdapter.ThumbnailCardViewHolder.access$200(paramThumbnailCardViewHolder).setActivated(true);
        RecyclerCardViewAdapter.this.context.startActivity(localIntent);
      }
    });
  }

  public ThumbnailCardViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new ThumbnailCardViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(2130903083, paramViewGroup, false));
  }

  public static class ThumbnailCardViewHolder extends RecyclerView.ViewHolder
  {
    private final View cardView;
    private String imageUri;
    private final ImageView thumbnail;
    private final TextView videoTitle;

    ThumbnailCardViewHolder(View paramView)
    {
      super();
      this.videoTitle = ((TextView)paramView.findViewById(2131493067));
      this.thumbnail = ((ImageView)paramView.findViewById(2131493068));
      this.cardView = paramView.findViewById(2131493065);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.adapter.RecyclerCardViewAdapter
 * JD-Core Version:    0.6.2
 */