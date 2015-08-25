package android.support.v7.internal.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ActivityChooserModel extends DataSetObservable
{
  private static final String ATTRIBUTE_ACTIVITY = "activity";
  private static final String ATTRIBUTE_TIME = "time";
  private static final String ATTRIBUTE_WEIGHT = "weight";
  private static final boolean DEBUG = false;
  private static final int DEFAULT_ACTIVITY_INFLATION = 5;
  private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0F;
  public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
  public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
  private static final String HISTORY_FILE_EXTENSION = ".xml";
  private static final int INVALID_INDEX = -1;
  private static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
  private static final String TAG_HISTORICAL_RECORD = "historical-record";
  private static final String TAG_HISTORICAL_RECORDS = "historical-records";
  private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
  private static final Object sRegistryLock = new Object();
  private final List<ActivityResolveInfo> mActivities = new ArrayList();
  private OnChooseActivityListener mActivityChoserModelPolicy;
  private ActivitySorter mActivitySorter = new DefaultSorter(null);
  private boolean mCanReadHistoricalData = true;
  private final Context mContext;
  private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
  private boolean mHistoricalRecordsChanged = true;
  private final String mHistoryFileName;
  private int mHistoryMaxSize = 50;
  private final Object mInstanceLock = new Object();
  private Intent mIntent;
  private boolean mReadShareHistoryCalled = false;
  private boolean mReloadActivities = false;

  private ActivityChooserModel(Context paramContext, String paramString)
  {
    this.mContext = paramContext.getApplicationContext();
    if ((!TextUtils.isEmpty(paramString)) && (!paramString.endsWith(".xml")))
    {
      this.mHistoryFileName = (paramString + ".xml");
      return;
    }
    this.mHistoryFileName = paramString;
  }

  private boolean addHisoricalRecord(HistoricalRecord paramHistoricalRecord)
  {
    boolean bool = this.mHistoricalRecords.add(paramHistoricalRecord);
    if (bool)
    {
      this.mHistoricalRecordsChanged = true;
      pruneExcessiveHistoricalRecordsIfNeeded();
      persistHistoricalDataIfNeeded();
      sortActivitiesIfNeeded();
      notifyChanged();
    }
    return bool;
  }

  private void ensureConsistentState()
  {
    boolean bool = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
    pruneExcessiveHistoricalRecordsIfNeeded();
    if (bool)
    {
      sortActivitiesIfNeeded();
      notifyChanged();
    }
  }

  public static ActivityChooserModel get(Context paramContext, String paramString)
  {
    synchronized (sRegistryLock)
    {
      ActivityChooserModel localActivityChooserModel = (ActivityChooserModel)sDataModelRegistry.get(paramString);
      if (localActivityChooserModel == null)
      {
        localActivityChooserModel = new ActivityChooserModel(paramContext, paramString);
        sDataModelRegistry.put(paramString, localActivityChooserModel);
      }
      return localActivityChooserModel;
    }
  }

  private boolean loadActivitiesIfNeeded()
  {
    boolean bool1 = this.mReloadActivities;
    boolean bool2 = false;
    if (bool1)
    {
      Intent localIntent = this.mIntent;
      bool2 = false;
      if (localIntent != null)
      {
        this.mReloadActivities = false;
        this.mActivities.clear();
        List localList = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int i = localList.size();
        for (int j = 0; j < i; j++)
        {
          ResolveInfo localResolveInfo = (ResolveInfo)localList.get(j);
          this.mActivities.add(new ActivityResolveInfo(localResolveInfo));
        }
        bool2 = true;
      }
    }
    return bool2;
  }

  private void persistHistoricalDataIfNeeded()
  {
    if (!this.mReadShareHistoryCalled)
      throw new IllegalStateException("No preceding call to #readHistoricalData");
    if (!this.mHistoricalRecordsChanged);
    do
    {
      return;
      this.mHistoricalRecordsChanged = false;
    }
    while (TextUtils.isEmpty(this.mHistoryFileName));
    PersistHistoryAsyncTask localPersistHistoryAsyncTask = new PersistHistoryAsyncTask(null);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.mHistoricalRecords;
    arrayOfObject[1] = this.mHistoryFileName;
    AsyncTaskCompat.executeParallel(localPersistHistoryAsyncTask, arrayOfObject);
  }

  private void pruneExcessiveHistoricalRecordsIfNeeded()
  {
    int i = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
    if (i <= 0);
    while (true)
    {
      return;
      this.mHistoricalRecordsChanged = true;
      for (int j = 0; j < i; j++)
        ((HistoricalRecord)this.mHistoricalRecords.remove(0));
    }
  }

  private boolean readHistoricalDataIfNeeded()
  {
    if ((this.mCanReadHistoricalData) && (this.mHistoricalRecordsChanged) && (!TextUtils.isEmpty(this.mHistoryFileName)))
    {
      this.mCanReadHistoricalData = false;
      this.mReadShareHistoryCalled = true;
      readHistoricalDataImpl();
      return true;
    }
    return false;
  }

  private void readHistoricalDataImpl()
  {
    try
    {
      FileInputStream localFileInputStream = this.mContext.openFileInput(this.mHistoryFileName);
      try
      {
        localXmlPullParser = Xml.newPullParser();
        localXmlPullParser.setInput(localFileInputStream, null);
        for (int i = 0; (i != 1) && (i != 2); i = localXmlPullParser.next());
        if (!"historical-records".equals(localXmlPullParser.getName()))
          throw new XmlPullParserException("Share records file does not start with historical-records tag.");
      }
      catch (XmlPullParserException localXmlPullParserException)
      {
        Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, localXmlPullParserException);
        if (localFileInputStream != null)
        {
          try
          {
            localFileInputStream.close();
            return;
          }
          catch (IOException localIOException4)
          {
            return;
          }
          localList = this.mHistoricalRecords;
          localList.clear();
          int j;
          do
          {
            j = localXmlPullParser.next();
            if (j == 1)
            {
              if (localFileInputStream == null)
                break;
              try
              {
                localFileInputStream.close();
                return;
              }
              catch (IOException localIOException5)
              {
                return;
              }
            }
          }
          while ((j == 3) || (j == 4));
          if (!"historical-record".equals(localXmlPullParser.getName()))
            throw new XmlPullParserException("Share records file not well-formed.");
        }
      }
      catch (IOException localIOException2)
      {
        while (true)
        {
          XmlPullParser localXmlPullParser;
          List localList;
          Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, localIOException2);
          if (localFileInputStream == null)
            break;
          try
          {
            localFileInputStream.close();
            return;
          }
          catch (IOException localIOException3)
          {
            return;
          }
          localList.add(new HistoricalRecord(localXmlPullParser.getAttributeValue(null, "activity"), Long.parseLong(localXmlPullParser.getAttributeValue(null, "time")), Float.parseFloat(localXmlPullParser.getAttributeValue(null, "weight"))));
        }
      }
      finally
      {
        if (localFileInputStream != null);
        try
        {
          localFileInputStream.close();
          label312: throw localObject;
        }
        catch (IOException localIOException1)
        {
          break label312;
        }
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
    }
  }

  private boolean sortActivitiesIfNeeded()
  {
    if ((this.mActivitySorter != null) && (this.mIntent != null) && (!this.mActivities.isEmpty()) && (!this.mHistoricalRecords.isEmpty()))
    {
      this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
      return true;
    }
    return false;
  }

  public Intent chooseActivity(int paramInt)
  {
    synchronized (this.mInstanceLock)
    {
      if (this.mIntent == null)
        return null;
      ensureConsistentState();
      ActivityResolveInfo localActivityResolveInfo = (ActivityResolveInfo)this.mActivities.get(paramInt);
      ComponentName localComponentName = new ComponentName(localActivityResolveInfo.resolveInfo.activityInfo.packageName, localActivityResolveInfo.resolveInfo.activityInfo.name);
      Intent localIntent1 = new Intent(this.mIntent);
      localIntent1.setComponent(localComponentName);
      if (this.mActivityChoserModelPolicy != null)
      {
        Intent localIntent2 = new Intent(localIntent1);
        if (this.mActivityChoserModelPolicy.onChooseActivity(this, localIntent2))
          return null;
      }
      addHisoricalRecord(new HistoricalRecord(localComponentName, System.currentTimeMillis(), 1.0F));
      return localIntent1;
    }
  }

  public ResolveInfo getActivity(int paramInt)
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      ResolveInfo localResolveInfo = ((ActivityResolveInfo)this.mActivities.get(paramInt)).resolveInfo;
      return localResolveInfo;
    }
  }

  public int getActivityCount()
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      int i = this.mActivities.size();
      return i;
    }
  }

  public int getActivityIndex(ResolveInfo paramResolveInfo)
  {
    while (true)
    {
      int j;
      synchronized (this.mInstanceLock)
      {
        ensureConsistentState();
        List localList = this.mActivities;
        int i = localList.size();
        j = 0;
        if (j < i)
        {
          if (((ActivityResolveInfo)localList.get(j)).resolveInfo == paramResolveInfo)
            return j;
        }
        else
          return -1;
      }
      j++;
    }
  }

  public ResolveInfo getDefaultActivity()
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      if (!this.mActivities.isEmpty())
      {
        ResolveInfo localResolveInfo = ((ActivityResolveInfo)this.mActivities.get(0)).resolveInfo;
        return localResolveInfo;
      }
      return null;
    }
  }

  public int getHistoryMaxSize()
  {
    synchronized (this.mInstanceLock)
    {
      int i = this.mHistoryMaxSize;
      return i;
    }
  }

  public int getHistorySize()
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      int i = this.mHistoricalRecords.size();
      return i;
    }
  }

  public Intent getIntent()
  {
    synchronized (this.mInstanceLock)
    {
      Intent localIntent = this.mIntent;
      return localIntent;
    }
  }

  public void setActivitySorter(ActivitySorter paramActivitySorter)
  {
    synchronized (this.mInstanceLock)
    {
      if (this.mActivitySorter == paramActivitySorter)
        return;
      this.mActivitySorter = paramActivitySorter;
      if (sortActivitiesIfNeeded())
        notifyChanged();
      return;
    }
  }

  public void setDefaultActivity(int paramInt)
  {
    while (true)
    {
      synchronized (this.mInstanceLock)
      {
        ensureConsistentState();
        ActivityResolveInfo localActivityResolveInfo1 = (ActivityResolveInfo)this.mActivities.get(paramInt);
        ActivityResolveInfo localActivityResolveInfo2 = (ActivityResolveInfo)this.mActivities.get(0);
        if (localActivityResolveInfo2 != null)
        {
          f = 5.0F + (localActivityResolveInfo2.weight - localActivityResolveInfo1.weight);
          addHisoricalRecord(new HistoricalRecord(new ComponentName(localActivityResolveInfo1.resolveInfo.activityInfo.packageName, localActivityResolveInfo1.resolveInfo.activityInfo.name), System.currentTimeMillis(), f));
          return;
        }
      }
      float f = 1.0F;
    }
  }

  public void setHistoryMaxSize(int paramInt)
  {
    synchronized (this.mInstanceLock)
    {
      if (this.mHistoryMaxSize == paramInt)
        return;
      this.mHistoryMaxSize = paramInt;
      pruneExcessiveHistoricalRecordsIfNeeded();
      if (sortActivitiesIfNeeded())
        notifyChanged();
      return;
    }
  }

  public void setIntent(Intent paramIntent)
  {
    synchronized (this.mInstanceLock)
    {
      if (this.mIntent == paramIntent)
        return;
      this.mIntent = paramIntent;
      this.mReloadActivities = true;
      ensureConsistentState();
      return;
    }
  }

  public void setOnChooseActivityListener(OnChooseActivityListener paramOnChooseActivityListener)
  {
    synchronized (this.mInstanceLock)
    {
      this.mActivityChoserModelPolicy = paramOnChooseActivityListener;
      return;
    }
  }

  public static abstract interface ActivityChooserModelClient
  {
    public abstract void setActivityChooserModel(ActivityChooserModel paramActivityChooserModel);
  }

  public final class ActivityResolveInfo
    implements Comparable<ActivityResolveInfo>
  {
    public final ResolveInfo resolveInfo;
    public float weight;

    public ActivityResolveInfo(ResolveInfo arg2)
    {
      Object localObject;
      this.resolveInfo = localObject;
    }

    public int compareTo(ActivityResolveInfo paramActivityResolveInfo)
    {
      return Float.floatToIntBits(paramActivityResolveInfo.weight) - Float.floatToIntBits(this.weight);
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      ActivityResolveInfo localActivityResolveInfo;
      do
      {
        return true;
        if (paramObject == null)
          return false;
        if (getClass() != paramObject.getClass())
          return false;
        localActivityResolveInfo = (ActivityResolveInfo)paramObject;
      }
      while (Float.floatToIntBits(this.weight) == Float.floatToIntBits(localActivityResolveInfo.weight));
      return false;
    }

    public int hashCode()
    {
      return 31 + Float.floatToIntBits(this.weight);
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append("resolveInfo:").append(this.resolveInfo.toString());
      localStringBuilder.append("; weight:").append(new BigDecimal(this.weight));
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }

  public static abstract interface ActivitySorter
  {
    public abstract void sort(Intent paramIntent, List<ActivityChooserModel.ActivityResolveInfo> paramList, List<ActivityChooserModel.HistoricalRecord> paramList1);
  }

  private final class DefaultSorter
    implements ActivityChooserModel.ActivitySorter
  {
    private static final float WEIGHT_DECAY_COEFFICIENT = 0.95F;
    private final Map<String, ActivityChooserModel.ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();

    private DefaultSorter()
    {
    }

    public void sort(Intent paramIntent, List<ActivityChooserModel.ActivityResolveInfo> paramList, List<ActivityChooserModel.HistoricalRecord> paramList1)
    {
      Map localMap = this.mPackageNameToActivityMap;
      localMap.clear();
      int i = paramList.size();
      for (int j = 0; j < i; j++)
      {
        ActivityChooserModel.ActivityResolveInfo localActivityResolveInfo2 = (ActivityChooserModel.ActivityResolveInfo)paramList.get(j);
        localActivityResolveInfo2.weight = 0.0F;
        localMap.put(localActivityResolveInfo2.resolveInfo.activityInfo.packageName, localActivityResolveInfo2);
      }
      int k = -1 + paramList1.size();
      float f = 1.0F;
      for (int m = k; m >= 0; m--)
      {
        ActivityChooserModel.HistoricalRecord localHistoricalRecord = (ActivityChooserModel.HistoricalRecord)paramList1.get(m);
        ActivityChooserModel.ActivityResolveInfo localActivityResolveInfo1 = (ActivityChooserModel.ActivityResolveInfo)localMap.get(localHistoricalRecord.activity.getPackageName());
        if (localActivityResolveInfo1 != null)
        {
          localActivityResolveInfo1.weight += f * localHistoricalRecord.weight;
          f *= 0.95F;
        }
      }
      Collections.sort(paramList);
    }
  }

  public static final class HistoricalRecord
  {
    public final ComponentName activity;
    public final long time;
    public final float weight;

    public HistoricalRecord(ComponentName paramComponentName, long paramLong, float paramFloat)
    {
      this.activity = paramComponentName;
      this.time = paramLong;
      this.weight = paramFloat;
    }

    public HistoricalRecord(String paramString, long paramLong, float paramFloat)
    {
      this(ComponentName.unflattenFromString(paramString), paramLong, paramFloat);
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      HistoricalRecord localHistoricalRecord;
      do
      {
        return true;
        if (paramObject == null)
          return false;
        if (getClass() != paramObject.getClass())
          return false;
        localHistoricalRecord = (HistoricalRecord)paramObject;
        if (this.activity == null)
        {
          if (localHistoricalRecord.activity != null)
            return false;
        }
        else if (!this.activity.equals(localHistoricalRecord.activity))
          return false;
        if (this.time != localHistoricalRecord.time)
          return false;
      }
      while (Float.floatToIntBits(this.weight) == Float.floatToIntBits(localHistoricalRecord.weight));
      return false;
    }

    public int hashCode()
    {
      if (this.activity == null);
      for (int i = 0; ; i = this.activity.hashCode())
        return 31 * (31 * (i + 31) + (int)(this.time ^ this.time >>> 32)) + Float.floatToIntBits(this.weight);
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append("; activity:").append(this.activity);
      localStringBuilder.append("; time:").append(this.time);
      localStringBuilder.append("; weight:").append(new BigDecimal(this.weight));
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }

  public static abstract interface OnChooseActivityListener
  {
    public abstract boolean onChooseActivity(ActivityChooserModel paramActivityChooserModel, Intent paramIntent);
  }

  private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void>
  {
    private PersistHistoryAsyncTask()
    {
    }

    // ERROR //
    public Void doInBackground(Object[] paramArrayOfObject)
    {
      // Byte code:
      //   0: aload_1
      //   1: iconst_0
      //   2: aaload
      //   3: checkcast 32	java/util/List
      //   6: astore_2
      //   7: aload_1
      //   8: iconst_1
      //   9: aaload
      //   10: checkcast 34	java/lang/String
      //   13: astore_3
      //   14: aload_0
      //   15: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   18: invokestatic 40	android/support/v7/internal/widget/ActivityChooserModel:access$200	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Landroid/content/Context;
      //   21: aload_3
      //   22: iconst_0
      //   23: invokevirtual 46	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
      //   26: astore 6
      //   28: invokestatic 52	android/util/Xml:newSerializer	()Lorg/xmlpull/v1/XmlSerializer;
      //   31: astore 7
      //   33: aload 7
      //   35: aload 6
      //   37: aconst_null
      //   38: invokeinterface 58 3 0
      //   43: aload 7
      //   45: ldc 60
      //   47: iconst_1
      //   48: invokestatic 66	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   51: invokeinterface 70 3 0
      //   56: aload 7
      //   58: aconst_null
      //   59: ldc 72
      //   61: invokeinterface 76 3 0
      //   66: pop
      //   67: aload_2
      //   68: invokeinterface 80 1 0
      //   73: istore 24
      //   75: iconst_0
      //   76: istore 25
      //   78: iload 25
      //   80: iload 24
      //   82: if_icmpge +132 -> 214
      //   85: aload_2
      //   86: iconst_0
      //   87: invokeinterface 84 2 0
      //   92: checkcast 86	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord
      //   95: astore 26
      //   97: aload 7
      //   99: aconst_null
      //   100: ldc 88
      //   102: invokeinterface 76 3 0
      //   107: pop
      //   108: aload 7
      //   110: aconst_null
      //   111: ldc 90
      //   113: aload 26
      //   115: getfield 93	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord:activity	Landroid/content/ComponentName;
      //   118: invokevirtual 99	android/content/ComponentName:flattenToString	()Ljava/lang/String;
      //   121: invokeinterface 103 4 0
      //   126: pop
      //   127: aload 7
      //   129: aconst_null
      //   130: ldc 105
      //   132: aload 26
      //   134: getfield 108	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord:time	J
      //   137: invokestatic 111	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   140: invokeinterface 103 4 0
      //   145: pop
      //   146: aload 7
      //   148: aconst_null
      //   149: ldc 113
      //   151: aload 26
      //   153: getfield 116	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord:weight	F
      //   156: invokestatic 119	java/lang/String:valueOf	(F)Ljava/lang/String;
      //   159: invokeinterface 103 4 0
      //   164: pop
      //   165: aload 7
      //   167: aconst_null
      //   168: ldc 88
      //   170: invokeinterface 122 3 0
      //   175: pop
      //   176: iinc 25 1
      //   179: goto -101 -> 78
      //   182: astore 4
      //   184: invokestatic 125	android/support/v7/internal/widget/ActivityChooserModel:access$300	()Ljava/lang/String;
      //   187: new 127	java/lang/StringBuilder
      //   190: dup
      //   191: invokespecial 128	java/lang/StringBuilder:<init>	()V
      //   194: ldc 130
      //   196: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   199: aload_3
      //   200: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   203: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   206: aload 4
      //   208: invokestatic 143	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   211: pop
      //   212: aconst_null
      //   213: areturn
      //   214: aload 7
      //   216: aconst_null
      //   217: ldc 72
      //   219: invokeinterface 122 3 0
      //   224: pop
      //   225: aload 7
      //   227: invokeinterface 146 1 0
      //   232: aload_0
      //   233: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   236: iconst_1
      //   237: invokestatic 150	android/support/v7/internal/widget/ActivityChooserModel:access$502	(Landroid/support/v7/internal/widget/ActivityChooserModel;Z)Z
      //   240: pop
      //   241: aload 6
      //   243: ifnull +8 -> 251
      //   246: aload 6
      //   248: invokevirtual 155	java/io/FileOutputStream:close	()V
      //   251: aconst_null
      //   252: areturn
      //   253: astore 19
      //   255: invokestatic 125	android/support/v7/internal/widget/ActivityChooserModel:access$300	()Ljava/lang/String;
      //   258: new 127	java/lang/StringBuilder
      //   261: dup
      //   262: invokespecial 128	java/lang/StringBuilder:<init>	()V
      //   265: ldc 130
      //   267: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   270: aload_0
      //   271: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   274: invokestatic 159	android/support/v7/internal/widget/ActivityChooserModel:access$400	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Ljava/lang/String;
      //   277: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   280: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   283: aload 19
      //   285: invokestatic 143	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   288: pop
      //   289: aload_0
      //   290: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   293: iconst_1
      //   294: invokestatic 150	android/support/v7/internal/widget/ActivityChooserModel:access$502	(Landroid/support/v7/internal/widget/ActivityChooserModel;Z)Z
      //   297: pop
      //   298: aload 6
      //   300: ifnull -49 -> 251
      //   303: aload 6
      //   305: invokevirtual 155	java/io/FileOutputStream:close	()V
      //   308: goto -57 -> 251
      //   311: astore 22
      //   313: goto -62 -> 251
      //   316: astore 15
      //   318: invokestatic 125	android/support/v7/internal/widget/ActivityChooserModel:access$300	()Ljava/lang/String;
      //   321: new 127	java/lang/StringBuilder
      //   324: dup
      //   325: invokespecial 128	java/lang/StringBuilder:<init>	()V
      //   328: ldc 130
      //   330: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   333: aload_0
      //   334: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   337: invokestatic 159	android/support/v7/internal/widget/ActivityChooserModel:access$400	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Ljava/lang/String;
      //   340: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   343: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   346: aload 15
      //   348: invokestatic 143	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   351: pop
      //   352: aload_0
      //   353: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   356: iconst_1
      //   357: invokestatic 150	android/support/v7/internal/widget/ActivityChooserModel:access$502	(Landroid/support/v7/internal/widget/ActivityChooserModel;Z)Z
      //   360: pop
      //   361: aload 6
      //   363: ifnull -112 -> 251
      //   366: aload 6
      //   368: invokevirtual 155	java/io/FileOutputStream:close	()V
      //   371: goto -120 -> 251
      //   374: astore 18
      //   376: goto -125 -> 251
      //   379: astore 11
      //   381: invokestatic 125	android/support/v7/internal/widget/ActivityChooserModel:access$300	()Ljava/lang/String;
      //   384: new 127	java/lang/StringBuilder
      //   387: dup
      //   388: invokespecial 128	java/lang/StringBuilder:<init>	()V
      //   391: ldc 130
      //   393: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   396: aload_0
      //   397: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   400: invokestatic 159	android/support/v7/internal/widget/ActivityChooserModel:access$400	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Ljava/lang/String;
      //   403: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   406: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   409: aload 11
      //   411: invokestatic 143	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   414: pop
      //   415: aload_0
      //   416: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   419: iconst_1
      //   420: invokestatic 150	android/support/v7/internal/widget/ActivityChooserModel:access$502	(Landroid/support/v7/internal/widget/ActivityChooserModel;Z)Z
      //   423: pop
      //   424: aload 6
      //   426: ifnull -175 -> 251
      //   429: aload 6
      //   431: invokevirtual 155	java/io/FileOutputStream:close	()V
      //   434: goto -183 -> 251
      //   437: astore 14
      //   439: goto -188 -> 251
      //   442: astore 8
      //   444: aload_0
      //   445: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   448: iconst_1
      //   449: invokestatic 150	android/support/v7/internal/widget/ActivityChooserModel:access$502	(Landroid/support/v7/internal/widget/ActivityChooserModel;Z)Z
      //   452: pop
      //   453: aload 6
      //   455: ifnull +8 -> 463
      //   458: aload 6
      //   460: invokevirtual 155	java/io/FileOutputStream:close	()V
      //   463: aload 8
      //   465: athrow
      //   466: astore 34
      //   468: goto -217 -> 251
      //   471: astore 10
      //   473: goto -10 -> 463
      //
      // Exception table:
      //   from	to	target	type
      //   14	28	182	java/io/FileNotFoundException
      //   33	75	253	java/lang/IllegalArgumentException
      //   85	176	253	java/lang/IllegalArgumentException
      //   214	232	253	java/lang/IllegalArgumentException
      //   303	308	311	java/io/IOException
      //   33	75	316	java/lang/IllegalStateException
      //   85	176	316	java/lang/IllegalStateException
      //   214	232	316	java/lang/IllegalStateException
      //   366	371	374	java/io/IOException
      //   33	75	379	java/io/IOException
      //   85	176	379	java/io/IOException
      //   214	232	379	java/io/IOException
      //   429	434	437	java/io/IOException
      //   33	75	442	finally
      //   85	176	442	finally
      //   214	232	442	finally
      //   255	289	442	finally
      //   318	352	442	finally
      //   381	415	442	finally
      //   246	251	466	java/io/IOException
      //   458	463	471	java/io/IOException
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ActivityChooserModel
 * JD-Core Version:    0.6.2
 */