package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;

class TreeDocumentFile extends DocumentFile
{
  private Context mContext;
  private Uri mUri;

  TreeDocumentFile(DocumentFile paramDocumentFile, Context paramContext, Uri paramUri)
  {
    super(paramDocumentFile);
    this.mContext = paramContext;
    this.mUri = paramUri;
  }

  public boolean canRead()
  {
    return DocumentsContractApi19.canRead(this.mContext, this.mUri);
  }

  public boolean canWrite()
  {
    return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
  }

  public DocumentFile createDirectory(String paramString)
  {
    Uri localUri = DocumentsContractApi21.createDirectory(this.mContext, this.mUri, paramString);
    if (localUri != null)
      return new TreeDocumentFile(this, this.mContext, localUri);
    return null;
  }

  public DocumentFile createFile(String paramString1, String paramString2)
  {
    Uri localUri = DocumentsContractApi21.createFile(this.mContext, this.mUri, paramString1, paramString2);
    if (localUri != null)
      return new TreeDocumentFile(this, this.mContext, localUri);
    return null;
  }

  public boolean delete()
  {
    return DocumentsContractApi19.delete(this.mContext, this.mUri);
  }

  public boolean exists()
  {
    return DocumentsContractApi19.exists(this.mContext, this.mUri);
  }

  public String getName()
  {
    return DocumentsContractApi19.getName(this.mContext, this.mUri);
  }

  public String getType()
  {
    return DocumentsContractApi19.getType(this.mContext, this.mUri);
  }

  public Uri getUri()
  {
    return this.mUri;
  }

  public boolean isDirectory()
  {
    return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
  }

  public boolean isFile()
  {
    return DocumentsContractApi19.isFile(this.mContext, this.mUri);
  }

  public long lastModified()
  {
    return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
  }

  public long length()
  {
    return DocumentsContractApi19.length(this.mContext, this.mUri);
  }

  public DocumentFile[] listFiles()
  {
    Uri[] arrayOfUri = DocumentsContractApi21.listFiles(this.mContext, this.mUri);
    DocumentFile[] arrayOfDocumentFile = new DocumentFile[arrayOfUri.length];
    for (int i = 0; i < arrayOfUri.length; i++)
      arrayOfDocumentFile[i] = new TreeDocumentFile(this, this.mContext, arrayOfUri[i]);
    return arrayOfDocumentFile;
  }

  public boolean renameTo(String paramString)
  {
    Uri localUri = DocumentsContractApi21.renameTo(this.mContext, this.mUri, paramString);
    if (localUri != null)
    {
      this.mUri = localUri;
      return true;
    }
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.provider.TreeDocumentFile
 * JD-Core Version:    0.6.2
 */