package android.support.v4.provider;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class RawDocumentFile extends DocumentFile
{
  private File mFile;

  RawDocumentFile(DocumentFile paramDocumentFile, File paramFile)
  {
    super(paramDocumentFile);
    this.mFile = paramFile;
  }

  private static boolean deleteContents(File paramFile)
  {
    File[] arrayOfFile = paramFile.listFiles();
    boolean bool = true;
    if (arrayOfFile != null)
    {
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
      {
        File localFile = arrayOfFile[j];
        if (localFile.isDirectory())
          bool &= deleteContents(localFile);
        if (!localFile.delete())
        {
          Log.w("DocumentFile", "Failed to delete " + localFile);
          bool = false;
        }
      }
    }
    return bool;
  }

  private static String getTypeForName(String paramString)
  {
    int i = paramString.lastIndexOf('.');
    if (i >= 0)
    {
      String str1 = paramString.substring(i + 1).toLowerCase();
      String str2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str1);
      if (str2 != null)
        return str2;
    }
    return "application/octet-stream";
  }

  public boolean canRead()
  {
    return this.mFile.canRead();
  }

  public boolean canWrite()
  {
    return this.mFile.canWrite();
  }

  public DocumentFile createDirectory(String paramString)
  {
    File localFile = new File(this.mFile, paramString);
    if ((localFile.isDirectory()) || (localFile.mkdir()))
      return new RawDocumentFile(this, localFile);
    return null;
  }

  public DocumentFile createFile(String paramString1, String paramString2)
  {
    String str = MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString1);
    if (str != null)
      paramString2 = paramString2 + "." + str;
    File localFile = new File(this.mFile, paramString2);
    try
    {
      localFile.createNewFile();
      RawDocumentFile localRawDocumentFile = new RawDocumentFile(this, localFile);
      return localRawDocumentFile;
    }
    catch (IOException localIOException)
    {
      Log.w("DocumentFile", "Failed to createFile: " + localIOException);
    }
    return null;
  }

  public boolean delete()
  {
    deleteContents(this.mFile);
    return this.mFile.delete();
  }

  public boolean exists()
  {
    return this.mFile.exists();
  }

  public String getName()
  {
    return this.mFile.getName();
  }

  public String getType()
  {
    if (this.mFile.isDirectory())
      return null;
    return getTypeForName(this.mFile.getName());
  }

  public Uri getUri()
  {
    return Uri.fromFile(this.mFile);
  }

  public boolean isDirectory()
  {
    return this.mFile.isDirectory();
  }

  public boolean isFile()
  {
    return this.mFile.isFile();
  }

  public long lastModified()
  {
    return this.mFile.lastModified();
  }

  public long length()
  {
    return this.mFile.length();
  }

  public DocumentFile[] listFiles()
  {
    ArrayList localArrayList = new ArrayList();
    File[] arrayOfFile = this.mFile.listFiles();
    if (arrayOfFile != null)
    {
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
        localArrayList.add(new RawDocumentFile(this, arrayOfFile[j]));
    }
    return (DocumentFile[])localArrayList.toArray(new DocumentFile[localArrayList.size()]);
  }

  public boolean renameTo(String paramString)
  {
    File localFile = new File(this.mFile.getParentFile(), paramString);
    if (this.mFile.renameTo(localFile))
    {
      this.mFile = localFile;
      return true;
    }
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.provider.RawDocumentFile
 * JD-Core Version:    0.6.2
 */