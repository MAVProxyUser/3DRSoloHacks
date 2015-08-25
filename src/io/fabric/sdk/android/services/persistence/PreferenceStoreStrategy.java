package io.fabric.sdk.android.services.persistence;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceStoreStrategy<T>
  implements PersistenceStrategy<T>
{
  private final String key;
  private final SerializationStrategy<T> serializer;
  private final PreferenceStore store;

  public PreferenceStoreStrategy(PreferenceStore paramPreferenceStore, SerializationStrategy<T> paramSerializationStrategy, String paramString)
  {
    this.store = paramPreferenceStore;
    this.serializer = paramSerializationStrategy;
    this.key = paramString;
  }

  public void clear()
  {
    this.store.edit().remove(this.key).commit();
  }

  public T restore()
  {
    SharedPreferences localSharedPreferences = this.store.get();
    return this.serializer.deserialize(localSharedPreferences.getString(this.key, null));
  }

  @SuppressLint({"CommitPrefEdits"})
  public void save(T paramT)
  {
    this.store.save(this.store.edit().putString(this.key, this.serializer.serialize(paramT)));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.persistence.PreferenceStoreStrategy
 * JD-Core Version:    0.6.2
 */