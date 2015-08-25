package com.o3dr.solo.android.util.video;

public abstract interface DecoderListener
{
  public abstract void onDecodingEnded();

  public abstract void onDecodingError();

  public abstract void onDecodingStarted();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.video.DecoderListener
 * JD-Core Version:    0.6.2
 */