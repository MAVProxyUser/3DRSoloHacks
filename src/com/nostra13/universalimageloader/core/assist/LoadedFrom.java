package com.nostra13.universalimageloader.core.assist;

public enum LoadedFrom
{
  static
  {
    DISC_CACHE = new LoadedFrom("DISC_CACHE", 1);
    MEMORY_CACHE = new LoadedFrom("MEMORY_CACHE", 2);
    LoadedFrom[] arrayOfLoadedFrom = new LoadedFrom[3];
    arrayOfLoadedFrom[0] = NETWORK;
    arrayOfLoadedFrom[1] = DISC_CACHE;
    arrayOfLoadedFrom[2] = MEMORY_CACHE;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.assist.LoadedFrom
 * JD-Core Version:    0.6.2
 */