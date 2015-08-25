package com.segment.analytics;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class Cartographer
{
  static final Cartographer INSTANCE = new Cartographer();

  private void listToWriter(List<?> paramList, JsonWriter paramJsonWriter)
    throws IOException
  {
    paramJsonWriter.beginArray();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      writeValue(localIterator.next(), paramJsonWriter);
    paramJsonWriter.endArray();
  }

  private void mapToWriter(Map<?, ?> paramMap, JsonWriter paramJsonWriter)
    throws IOException
  {
    paramJsonWriter.beginObject();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      paramJsonWriter.name(String.valueOf(localEntry.getKey()));
      writeValue(localEntry.getValue(), paramJsonWriter);
    }
    paramJsonWriter.endObject();
  }

  private Object readValue(JsonReader paramJsonReader)
    throws IOException
  {
    JsonToken localJsonToken = paramJsonReader.peek();
    switch (1.$SwitchMap$android$util$JsonToken[localJsonToken.ordinal()])
    {
    default:
      throw new IllegalStateException("Invalid token " + localJsonToken);
    case 1:
      return readerToMap(paramJsonReader);
    case 2:
      return readerToList(paramJsonReader);
    case 3:
      return Boolean.valueOf(paramJsonReader.nextBoolean());
    case 4:
      paramJsonReader.nextNull();
      return null;
    case 5:
      return Double.valueOf(paramJsonReader.nextDouble());
    case 6:
    }
    return paramJsonReader.nextString();
  }

  private List<Object> readerToList(JsonReader paramJsonReader)
    throws IOException
  {
    ArrayList localArrayList = new ArrayList();
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext())
      localArrayList.add(readValue(paramJsonReader));
    paramJsonReader.endArray();
    return localArrayList;
  }

  private Map<String, Object> readerToMap(JsonReader paramJsonReader)
    throws IOException
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    paramJsonReader.beginObject();
    while (paramJsonReader.hasNext())
      localLinkedHashMap.put(paramJsonReader.nextName(), readValue(paramJsonReader));
    paramJsonReader.endObject();
    return localLinkedHashMap;
  }

  private void writeValue(Object paramObject, JsonWriter paramJsonWriter)
    throws IOException
  {
    if (paramObject == null)
    {
      paramJsonWriter.nullValue();
      return;
    }
    if ((paramObject instanceof Number))
    {
      paramJsonWriter.value((Number)paramObject);
      return;
    }
    if ((paramObject instanceof Boolean))
    {
      paramJsonWriter.value(((Boolean)paramObject).booleanValue());
      return;
    }
    if ((paramObject instanceof List))
    {
      listToWriter((List)paramObject, paramJsonWriter);
      return;
    }
    if ((paramObject instanceof Map))
    {
      mapToWriter((Map)paramObject, paramJsonWriter);
      return;
    }
    paramJsonWriter.value(String.valueOf(paramObject));
  }

  Map<String, Object> fromJson(Reader paramReader)
    throws IOException
  {
    JsonReader localJsonReader = new JsonReader(paramReader);
    try
    {
      Map localMap = readerToMap(localJsonReader);
      return localMap;
    }
    finally
    {
      paramReader.close();
    }
  }

  Map<String, Object> fromJson(String paramString)
    throws IOException
  {
    return fromJson(new StringReader(paramString));
  }

  String toJson(Map<?, ?> paramMap)
    throws IOException
  {
    StringWriter localStringWriter = new StringWriter();
    toJson(paramMap, localStringWriter);
    return localStringWriter.toString();
  }

  void toJson(Map<?, ?> paramMap, Writer paramWriter)
    throws IOException
  {
    if (paramMap == null)
      throw new IllegalArgumentException("map == null");
    JsonWriter localJsonWriter = new JsonWriter(paramWriter);
    try
    {
      mapToWriter(paramMap, localJsonWriter);
      return;
    }
    finally
    {
      localJsonWriter.close();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.Cartographer
 * JD-Core Version:    0.6.2
 */