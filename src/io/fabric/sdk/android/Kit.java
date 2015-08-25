package io.fabric.sdk.android;

import android.content.Context;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Task;
import java.io.File;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

public abstract class Kit<Result>
  implements Comparable<Kit>
{
  Context context;
  Fabric fabric;
  IdManager idManager;
  InitializationCallback<Result> initializationCallback;
  InitializationTask<Result> initializationTask = new InitializationTask(this);

  public int compareTo(Kit paramKit)
  {
    if (containsAnnotatedDependency(paramKit));
    do
    {
      return 1;
      if (paramKit.containsAnnotatedDependency(this))
        return -1;
    }
    while ((hasAnnotatedDependency()) && (!paramKit.hasAnnotatedDependency()));
    if ((!hasAnnotatedDependency()) && (paramKit.hasAnnotatedDependency()))
      return -1;
    return 0;
  }

  boolean containsAnnotatedDependency(Kit paramKit)
  {
    DependsOn localDependsOn = (DependsOn)getClass().getAnnotation(DependsOn.class);
    if (localDependsOn != null)
    {
      Class[] arrayOfClass = localDependsOn.value();
      int i = arrayOfClass.length;
      for (int j = 0; j < i; j++)
        if (arrayOfClass[j].equals(paramKit.getClass()))
          return true;
    }
    return false;
  }

  protected abstract Result doInBackground();

  public Context getContext()
  {
    return this.context;
  }

  protected Collection<Task> getDependencies()
  {
    return this.initializationTask.getDependencies();
  }

  public Fabric getFabric()
  {
    return this.fabric;
  }

  protected IdManager getIdManager()
  {
    return this.idManager;
  }

  public abstract String getIdentifier();

  public String getPath()
  {
    return ".Fabric" + File.separator + getIdentifier();
  }

  public abstract String getVersion();

  boolean hasAnnotatedDependency()
  {
    return (DependsOn)getClass().getAnnotation(DependsOn.class) != null;
  }

  final void initialize()
  {
    InitializationTask localInitializationTask = this.initializationTask;
    ExecutorService localExecutorService = this.fabric.getExecutorService();
    Void[] arrayOfVoid = new Void[1];
    arrayOfVoid[0] = ((Void)null);
    localInitializationTask.executeOnExecutor(localExecutorService, arrayOfVoid);
  }

  void injectParameters(Context paramContext, Fabric paramFabric, InitializationCallback<Result> paramInitializationCallback, IdManager paramIdManager)
  {
    this.fabric = paramFabric;
    this.context = new FabricContext(paramContext, getIdentifier(), getPath());
    this.initializationCallback = paramInitializationCallback;
    this.idManager = paramIdManager;
  }

  protected void onCancelled(Result paramResult)
  {
  }

  protected void onPostExecute(Result paramResult)
  {
  }

  protected boolean onPreExecute()
  {
    return true;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.Kit
 * JD-Core Version:    0.6.2
 */