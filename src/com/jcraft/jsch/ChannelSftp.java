package com.jcraft.jsch;

import [B;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Hashtable;
import java.util.Vector;

public class ChannelSftp extends ChannelSession
{
  public static final int APPEND = 2;
  private static final int LOCAL_MAXIMUM_PACKET_SIZE = 32768;
  private static final int LOCAL_WINDOW_SIZE_MAX = 2097152;
  private static final int MAX_MSG_LENGTH = 262144;
  public static final int OVERWRITE = 0;
  public static final int RESUME = 1;
  private static final int SSH_FILEXFER_ATTR_ACMODTIME = 8;
  private static final int SSH_FILEXFER_ATTR_EXTENDED = -2147483648;
  private static final int SSH_FILEXFER_ATTR_PERMISSIONS = 4;
  private static final int SSH_FILEXFER_ATTR_SIZE = 1;
  private static final int SSH_FILEXFER_ATTR_UIDGID = 2;
  private static final int SSH_FXF_APPEND = 4;
  private static final int SSH_FXF_CREAT = 8;
  private static final int SSH_FXF_EXCL = 32;
  private static final int SSH_FXF_READ = 1;
  private static final int SSH_FXF_TRUNC = 16;
  private static final int SSH_FXF_WRITE = 2;
  private static final byte SSH_FXP_ATTRS = 105;
  private static final byte SSH_FXP_CLOSE = 4;
  private static final byte SSH_FXP_DATA = 103;
  private static final byte SSH_FXP_EXTENDED = -56;
  private static final byte SSH_FXP_EXTENDED_REPLY = -55;
  private static final byte SSH_FXP_FSETSTAT = 10;
  private static final byte SSH_FXP_FSTAT = 8;
  private static final byte SSH_FXP_HANDLE = 102;
  private static final byte SSH_FXP_INIT = 1;
  private static final byte SSH_FXP_LSTAT = 7;
  private static final byte SSH_FXP_MKDIR = 14;
  private static final byte SSH_FXP_NAME = 104;
  private static final byte SSH_FXP_OPEN = 3;
  private static final byte SSH_FXP_OPENDIR = 11;
  private static final byte SSH_FXP_READ = 5;
  private static final byte SSH_FXP_READDIR = 12;
  private static final byte SSH_FXP_READLINK = 19;
  private static final byte SSH_FXP_REALPATH = 16;
  private static final byte SSH_FXP_REMOVE = 13;
  private static final byte SSH_FXP_RENAME = 18;
  private static final byte SSH_FXP_RMDIR = 15;
  private static final byte SSH_FXP_SETSTAT = 9;
  private static final byte SSH_FXP_STAT = 17;
  private static final byte SSH_FXP_STATUS = 101;
  private static final byte SSH_FXP_SYMLINK = 20;
  private static final byte SSH_FXP_VERSION = 2;
  private static final byte SSH_FXP_WRITE = 6;
  public static final int SSH_FX_BAD_MESSAGE = 5;
  public static final int SSH_FX_CONNECTION_LOST = 7;
  public static final int SSH_FX_EOF = 1;
  public static final int SSH_FX_FAILURE = 4;
  public static final int SSH_FX_NO_CONNECTION = 6;
  public static final int SSH_FX_NO_SUCH_FILE = 2;
  public static final int SSH_FX_OK = 0;
  public static final int SSH_FX_OP_UNSUPPORTED = 8;
  public static final int SSH_FX_PERMISSION_DENIED = 3;
  private static final String UTF8 = "UTF-8";
  private static final String file_separator = File.separator;
  private static final char file_separatorc = File.separatorChar;
  private static boolean fs_is_bs;
  private int[] ackid = new int[1];
  private Buffer buf;
  private int client_version = 3;
  private String cwd;
  private boolean extension_hardlink = false;
  private boolean extension_posix_rename = false;
  private boolean extension_statvfs = false;
  private Hashtable extensions = null;
  private String fEncoding = "UTF-8";
  private boolean fEncoding_is_utf8 = true;
  private String home;
  private boolean interactive = false;
  private InputStream io_in = null;
  private String lcwd;
  private Buffer obuf;
  private Packet opacket;
  private Packet packet;
  private RequestQueue rq = new RequestQueue(16);
  private int seq = 1;
  private int server_version = 3;
  private String version = String.valueOf(this.client_version);

  static
  {
    if ((byte)File.separatorChar == 92);
    for (boolean bool = true; ; bool = false)
    {
      fs_is_bs = bool;
      return;
    }
  }

  public ChannelSftp()
  {
    setLocalWindowSizeMax(2097152);
    setLocalWindowSize(2097152);
    setLocalPacketSize(32768);
  }

  private void _get(String paramString, OutputStream paramOutputStream, SftpProgressMonitor paramSftpProgressMonitor, int paramInt, long paramLong)
    throws SftpException
  {
    byte[] arrayOfByte1 = Util.str2byte(paramString, this.fEncoding);
    try
    {
      sendOPENR(arrayOfByte1);
      Header localHeader1 = new Header();
      localHeader2 = header(this.buf, localHeader1);
      int i = localHeader2.length;
      j = localHeader2.type;
      fill(this.buf, i);
      if ((j != 101) && (j != 102))
        throw new SftpException(4, "");
    }
    catch (Exception localException)
    {
      Header localHeader2;
      int j;
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        if (j == 101)
        {
          int i10 = this.buf.getInt();
          throwStatusError(this.buf, i10);
        }
        byte[] arrayOfByte2 = this.buf.getString();
        long l1 = 0L;
        if (paramInt == 1)
          l1 += paramLong;
        int k = 1;
        this.rq.init();
        long l2 = l1;
        int m = -13 + this.buf.buffer.length;
        if (this.server_version == 0)
          m = 1024;
        while (true)
          if (this.rq.count() < k)
          {
            sendREAD(arrayOfByte2, l2, m, this.rq);
            l2 += m;
          }
          else
          {
            localHeader2 = header(this.buf, localHeader2);
            int n = localHeader2.length;
            int i1 = localHeader2.type;
            ChannelSftp.RequestQueue.Request localRequest;
            label390: label400: int i3;
            int i4;
            while (true)
            {
              int i9;
              try
              {
                localRequest = this.rq.get(localHeader2.rid);
                if (i1 != 101)
                  break label400;
                fill(this.buf, n);
                i9 = this.buf.getInt();
                if (i9 != 1)
                  break label390;
                paramOutputStream.flush();
                if (paramSftpProgressMonitor != null)
                  paramSftpProgressMonitor.end();
                RequestQueue localRequestQueue3 = this.rq;
                Buffer localBuffer3 = this.buf;
                localRequestQueue3.cancel(localHeader2, localBuffer3);
                _sendCLOSE(arrayOfByte2, localHeader2);
                return;
              }
              catch (ChannelSftp.RequestQueue.OutOfOrderException localOutOfOrderException)
              {
                l2 = localOutOfOrderException.offset;
                skip(localHeader2.length);
                RequestQueue localRequestQueue1 = this.rq;
                Buffer localBuffer1 = this.buf;
                localRequestQueue1.cancel(localHeader2, localBuffer1);
              }
              break;
              throwStatusError(this.buf, i9);
              if (i1 == 103)
              {
                this.buf.rewind();
                fill(this.buf.buffer, 0, 4);
                int i2 = n - 4;
                i3 = this.buf.getInt();
                i4 = i2 - i3;
                int i5 = i3;
                int i8;
                do
                {
                  if (i5 <= 0)
                    break label577;
                  int i7 = i5;
                  if (i7 > this.buf.buffer.length)
                    i7 = this.buf.buffer.length;
                  i8 = this.io_in.read(this.buf.buffer, 0, i7);
                  if (i8 < 0)
                    break;
                  paramOutputStream.write(this.buf.buffer, 0, i8);
                  l1 += i8;
                  i5 -= i8;
                }
                while ((paramSftpProgressMonitor == null) || (paramSftpProgressMonitor.count(i8)));
                skip(i5);
                if (i4 > 0)
                  skip(i4);
              }
            }
            label577: if (i4 > 0)
              skip(i4);
            if (i3 < localRequest.length)
            {
              RequestQueue localRequestQueue2 = this.rq;
              Buffer localBuffer2 = this.buf;
              localRequestQueue2.cancel(localHeader2, localBuffer2);
              sendREAD(arrayOfByte2, localRequest.offset + i3, (int)(localRequest.length - i3), this.rq);
              l2 = localRequest.offset + localRequest.length;
            }
            int i6 = this.rq.size();
            if (k < i6)
              k++;
          }
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  private SftpATTRS _lstat(String paramString)
    throws SftpException
  {
    try
    {
      sendLSTAT(Util.str2byte(paramString, this.fEncoding));
      Header localHeader1 = new Header();
      Header localHeader2 = header(this.buf, localHeader1);
      int i = localHeader2.length;
      int j = localHeader2.type;
      fill(this.buf, i);
      if (j != 105)
      {
        if (j == 101)
        {
          int k = this.buf.getInt();
          throwStatusError(this.buf, k);
        }
        throw new SftpException(4, "");
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        SftpATTRS localSftpATTRS = SftpATTRS.getATTR(this.buf);
        return localSftpATTRS;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  private byte[] _realpath(String paramString)
    throws SftpException, IOException, Exception
  {
    sendREALPATH(Util.str2byte(paramString, this.fEncoding));
    Header localHeader1 = new Header();
    Header localHeader2 = header(this.buf, localHeader1);
    int i = localHeader2.length;
    int j = localHeader2.type;
    fill(this.buf, i);
    if ((j != 101) && (j != 104))
      throw new SftpException(4, "");
    if (j == 101)
    {
      int i1 = this.buf.getInt();
      throwStatusError(this.buf, i1);
    }
    int k = this.buf.getInt();
    byte[] arrayOfByte = null;
    int n;
    for (int m = k; ; m = n)
    {
      n = m - 1;
      if (m <= 0)
        break;
      arrayOfByte = this.buf.getString();
      if (this.server_version <= 3)
        this.buf.getString();
      SftpATTRS.getATTR(this.buf);
    }
    return arrayOfByte;
  }

  private boolean _sendCLOSE(byte[] paramArrayOfByte, Header paramHeader)
    throws Exception
  {
    sendCLOSE(paramArrayOfByte);
    return checkStatus(null, paramHeader);
  }

  private void _setStat(String paramString, SftpATTRS paramSftpATTRS)
    throws SftpException
  {
    try
    {
      sendSETSTAT(Util.str2byte(paramString, this.fEncoding), paramSftpATTRS);
      Header localHeader1 = new Header();
      Header localHeader2 = header(this.buf, localHeader1);
      int i = localHeader2.length;
      int j = localHeader2.type;
      fill(this.buf, i);
      if (j != 101)
        throw new SftpException(4, "");
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        int k = this.buf.getInt();
        if (k != 0)
          throwStatusError(this.buf, k);
        return;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  private SftpATTRS _stat(String paramString)
    throws SftpException
  {
    return _stat(Util.str2byte(paramString, this.fEncoding));
  }

  private SftpATTRS _stat(byte[] paramArrayOfByte)
    throws SftpException
  {
    try
    {
      sendSTAT(paramArrayOfByte);
      Header localHeader1 = new Header();
      Header localHeader2 = header(this.buf, localHeader1);
      int i = localHeader2.length;
      int j = localHeader2.type;
      fill(this.buf, i);
      if (j != 105)
      {
        if (j == 101)
        {
          int k = this.buf.getInt();
          throwStatusError(this.buf, k);
        }
        throw new SftpException(4, "");
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        SftpATTRS localSftpATTRS = SftpATTRS.getATTR(this.buf);
        return localSftpATTRS;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  private SftpStatVFS _statVFS(String paramString)
    throws SftpException
  {
    return _statVFS(Util.str2byte(paramString, this.fEncoding));
  }

  private SftpStatVFS _statVFS(byte[] paramArrayOfByte)
    throws SftpException
  {
    if (!this.extension_statvfs)
      throw new SftpException(8, "statvfs@openssh.com is not supported");
    try
    {
      sendSTATVFS(paramArrayOfByte);
      Header localHeader1 = new Header();
      Header localHeader2 = header(this.buf, localHeader1);
      int i = localHeader2.length;
      int j = localHeader2.type;
      fill(this.buf, i);
      if (j != 201)
      {
        if (j == 101)
        {
          int k = this.buf.getInt();
          throwStatusError(this.buf, k);
        }
        throw new SftpException(4, "");
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        SftpStatVFS localSftpStatVFS = SftpStatVFS.getStatVFS(this.buf);
        return localSftpStatVFS;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  private boolean checkStatus(int[] paramArrayOfInt, Header paramHeader)
    throws IOException, SftpException
  {
    Header localHeader = header(this.buf, paramHeader);
    int i = localHeader.length;
    int j = localHeader.type;
    if (paramArrayOfInt != null)
      paramArrayOfInt[0] = localHeader.rid;
    fill(this.buf, i);
    if (j != 101)
      throw new SftpException(4, "");
    int k = this.buf.getInt();
    if (k != 0)
      throwStatusError(this.buf, k);
    return true;
  }

  private int fill(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = paramInt1;
    while (paramInt2 > 0)
    {
      int j = this.io_in.read(paramArrayOfByte, paramInt1, paramInt2);
      if (j <= 0)
        throw new IOException("inputstream is closed");
      paramInt1 += j;
      paramInt2 -= j;
    }
    return paramInt1 - i;
  }

  private void fill(Buffer paramBuffer, int paramInt)
    throws IOException
  {
    paramBuffer.reset();
    fill(paramBuffer.buffer, 0, paramInt);
    paramBuffer.skip(paramInt);
  }

  private String getCwd()
    throws SftpException
  {
    if (this.cwd == null)
      this.cwd = getHome();
    return this.cwd;
  }

  private Vector glob_local(String paramString)
    throws Exception
  {
    Vector localVector = new Vector();
    byte[] arrayOfByte1 = Util.str2byte(paramString, "UTF-8");
    int i = -1 + arrayOfByte1.length;
    while (i >= 0)
      if ((arrayOfByte1[i] != 42) && (arrayOfByte1[i] != 63))
      {
        i--;
      }
      else
      {
        if ((fs_is_bs) || (i <= 0) || (arrayOfByte1[(i - 1)] != 92))
          break;
        i--;
        if ((i <= 0) || (arrayOfByte1[(i - 1)] != 92))
          break;
        i = -1 + (i - 1);
      }
    if (i < 0)
      if (fs_is_bs)
        localVector.addElement(paramString);
    while (true)
    {
      return localVector;
      paramString = Util.unquote(paramString);
      break;
      do
        i--;
      while ((i >= 0) && (arrayOfByte1[i] != file_separatorc) && ((!fs_is_bs) || (arrayOfByte1[i] != 47)));
      if (i < 0)
      {
        if (fs_is_bs);
        while (true)
        {
          localVector.addElement(paramString);
          return localVector;
          paramString = Util.unquote(paramString);
        }
      }
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      if (i == 0)
      {
        arrayOfByte2 = new byte[1];
        arrayOfByte2[0] = ((byte)file_separatorc);
        arrayOfByte3 = new byte[-1 + (arrayOfByte1.length - i)];
        System.arraycopy(arrayOfByte1, i + 1, arrayOfByte3, 0, arrayOfByte3.length);
      }
      try
      {
        String[] arrayOfString = new File(Util.byte2str(arrayOfByte2, "UTF-8")).list();
        String str = Util.byte2str(arrayOfByte2) + file_separator;
        for (int j = 0; j < arrayOfString.length; j++)
          if (Util.glob(arrayOfByte3, Util.str2byte(arrayOfString[j], "UTF-8")))
            localVector.addElement(str + arrayOfString[j]);
        arrayOfByte2 = new byte[i];
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
      }
      catch (Exception localException)
      {
      }
    }
    return localVector;
  }

  private Vector glob_remote(String paramString)
    throws Exception
  {
    Vector localVector = new Vector();
    int i = paramString.lastIndexOf('/');
    if (i < 0)
      localVector.addElement(Util.unquote(paramString));
    String str3;
    [B local[B;
    Header localHeader2;
    byte[] arrayOfByte1;
    String str4;
    int n;
    do
    {
      return localVector;
      if (i == 0);
      byte[][] arrayOfByte;
      for (int j = 1; ; j = i)
      {
        String str1 = paramString.substring(0, j);
        String str2 = paramString.substring(i + 1);
        str3 = Util.unquote(str1);
        arrayOfByte = new byte[1][];
        if (isPattern(str2, arrayOfByte))
          break;
        if (!str3.equals("/"))
          str3 = str3 + "/";
        localVector.addElement(str3 + Util.unquote(str2));
        return localVector;
      }
      local[B = arrayOfByte[0];
      sendOPENDIR(Util.str2byte(str3, this.fEncoding));
      Header localHeader1 = new Header();
      localHeader2 = header(this.buf, localHeader1);
      int k = localHeader2.length;
      int m = localHeader2.type;
      fill(this.buf, k);
      if ((m != 101) && (m != 102))
        throw new SftpException(4, "");
      if (m == 101)
      {
        int i6 = this.buf.getInt();
        throwStatusError(this.buf, i6);
      }
      arrayOfByte1 = this.buf.getString();
      str4 = null;
      sendREADDIR(arrayOfByte1);
      localHeader2 = header(this.buf, localHeader2);
      n = localHeader2.length;
      int i1 = localHeader2.type;
      if ((i1 != 101) && (i1 != 104))
        throw new SftpException(4, "");
      if (i1 != 101)
        break;
      fill(this.buf, n);
    }
    while (_sendCLOSE(arrayOfByte1, localHeader2));
    return null;
    this.buf.rewind();
    fill(this.buf.buffer, 0, 4);
    int i2 = n - 4;
    int i3 = this.buf.getInt();
    this.buf.reset();
    label405: if (i3 > 0)
      if (i2 > 0)
      {
        this.buf.shift();
        if (this.buf.buffer.length <= i2 + this.buf.index)
          break label673;
      }
    label673: for (int i4 = i2; ; i4 = this.buf.buffer.length - this.buf.index)
    {
      int i5 = this.io_in.read(this.buf.buffer, this.buf.index, i4);
      if (i5 <= 0)
        break;
      Buffer localBuffer = this.buf;
      localBuffer.index = (i5 + localBuffer.index);
      i2 -= i5;
      byte[] arrayOfByte2 = this.buf.getString();
      if (this.server_version <= 3)
        this.buf.getString();
      SftpATTRS.getATTR(this.buf);
      byte[] arrayOfByte3 = arrayOfByte2;
      boolean bool = this.fEncoding_is_utf8;
      String str5 = null;
      if (!bool)
      {
        str5 = Util.byte2str(arrayOfByte2, this.fEncoding);
        arrayOfByte3 = Util.str2byte(str5, "UTF-8");
      }
      if (Util.glob(local[B, arrayOfByte3))
      {
        if (str5 == null)
          str5 = Util.byte2str(arrayOfByte2, this.fEncoding);
        if (str4 == null)
        {
          str4 = str3;
          if (!str4.endsWith("/"))
            str4 = str4 + "/";
        }
        localVector.addElement(str4 + str5);
      }
      i3--;
      break label405;
      break;
    }
  }

  private Header header(Buffer paramBuffer, Header paramHeader)
    throws IOException
  {
    paramBuffer.rewind();
    fill(paramBuffer.buffer, 0, 9);
    paramHeader.length = (-5 + paramBuffer.getInt());
    paramHeader.type = (0xFF & paramBuffer.getByte());
    paramHeader.rid = paramBuffer.getInt();
    return paramHeader;
  }

  private static boolean isLocalAbsolutePath(String paramString)
  {
    return new File(paramString).isAbsolute();
  }

  private boolean isPattern(String paramString)
  {
    return isPattern(paramString, (byte[][])null);
  }

  private boolean isPattern(String paramString, byte[][] paramArrayOfByte)
  {
    byte[] arrayOfByte = Util.str2byte(paramString, "UTF-8");
    if (paramArrayOfByte != null)
      paramArrayOfByte[0] = arrayOfByte;
    return isPattern(arrayOfByte);
  }

  private boolean isPattern(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++)
    {
      if ((paramArrayOfByte[j] == 42) || (paramArrayOfByte[j] == 63))
        return true;
      if ((paramArrayOfByte[j] == 92) && (j + 1 < i))
        j++;
    }
    return false;
  }

  private boolean isRemoteDir(String paramString)
  {
    try
    {
      sendSTAT(Util.str2byte(paramString, this.fEncoding));
      Header localHeader1 = new Header();
      Header localHeader2 = header(this.buf, localHeader1);
      int i = localHeader2.length;
      int j = localHeader2.type;
      fill(this.buf, i);
      if (j != 105)
        return false;
      boolean bool = SftpATTRS.getATTR(this.buf).isDir();
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private String isUnique(String paramString)
    throws SftpException, Exception
  {
    Vector localVector = glob_remote(paramString);
    if (localVector.size() != 1)
      throw new SftpException(4, paramString + " is not unique: " + localVector.toString());
    return (String)localVector.elementAt(0);
  }

  private String localAbsolutePath(String paramString)
  {
    if (isLocalAbsolutePath(paramString))
      return paramString;
    if (this.lcwd.endsWith(file_separator))
      return this.lcwd + paramString;
    return this.lcwd + file_separator + paramString;
  }

  private void putHEAD(byte paramByte, int paramInt)
    throws Exception
  {
    putHEAD(this.buf, paramByte, paramInt);
  }

  private void putHEAD(Buffer paramBuffer, byte paramByte, int paramInt)
    throws Exception
  {
    paramBuffer.putByte((byte)94);
    paramBuffer.putInt(this.recipient);
    paramBuffer.putInt(paramInt + 4);
    paramBuffer.putInt(paramInt);
    paramBuffer.putByte(paramByte);
  }

  private void read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException, SftpException
  {
    while (paramInt2 > 0)
    {
      int i = this.io_in.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i <= 0)
        throw new SftpException(4, "");
      paramInt1 += i;
      paramInt2 -= i;
    }
  }

  private String remoteAbsolutePath(String paramString)
    throws SftpException
  {
    if (paramString.charAt(0) == '/')
      return paramString;
    String str = getCwd();
    if (str.endsWith("/"))
      return str + paramString;
    return str + "/" + paramString;
  }

  private void sendCLOSE(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)4, paramArrayOfByte);
  }

  private void sendFSTAT(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)8, paramArrayOfByte);
  }

  private void sendHARDLINK(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    sendPacketPath((byte)0, paramArrayOfByte1, paramArrayOfByte2, "hardlink@openssh.com");
  }

  private void sendINIT()
    throws Exception
  {
    this.packet.reset();
    putHEAD((byte)1, 5);
    this.buf.putInt(3);
    getSession().write(this.packet, this, 9);
  }

  private void sendLSTAT(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)7, paramArrayOfByte);
  }

  private void sendMKDIR(byte[] paramArrayOfByte, SftpATTRS paramSftpATTRS)
    throws Exception
  {
    int i = 4;
    this.packet.reset();
    int j = 9 + paramArrayOfByte.length;
    int k;
    if (paramSftpATTRS != null)
    {
      k = paramSftpATTRS.length();
      putHEAD((byte)14, k + j);
      Buffer localBuffer = this.buf;
      int m = this.seq;
      this.seq = (m + 1);
      localBuffer.putInt(m);
      this.buf.putString(paramArrayOfByte);
      if (paramSftpATTRS == null)
        break label133;
      paramSftpATTRS.dump(this.buf);
    }
    while (true)
    {
      Session localSession = getSession();
      Packet localPacket = this.packet;
      int n = 9 + paramArrayOfByte.length;
      if (paramSftpATTRS != null)
        i = paramSftpATTRS.length();
      localSession.write(localPacket, this, 4 + (i + n));
      return;
      k = i;
      break;
      label133: this.buf.putInt(0);
    }
  }

  private void sendOPEN(byte[] paramArrayOfByte, int paramInt)
    throws Exception
  {
    this.packet.reset();
    putHEAD((byte)3, 17 + paramArrayOfByte.length);
    Buffer localBuffer = this.buf;
    int i = this.seq;
    this.seq = (i + 1);
    localBuffer.putInt(i);
    this.buf.putString(paramArrayOfByte);
    this.buf.putInt(paramInt);
    this.buf.putInt(0);
    getSession().write(this.packet, this, 4 + (17 + paramArrayOfByte.length));
  }

  private void sendOPENA(byte[] paramArrayOfByte)
    throws Exception
  {
    sendOPEN(paramArrayOfByte, 10);
  }

  private void sendOPENDIR(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)11, paramArrayOfByte);
  }

  private void sendOPENR(byte[] paramArrayOfByte)
    throws Exception
  {
    sendOPEN(paramArrayOfByte, 1);
  }

  private void sendOPENW(byte[] paramArrayOfByte)
    throws Exception
  {
    sendOPEN(paramArrayOfByte, 26);
  }

  private void sendPacketPath(byte paramByte, byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath(paramByte, paramArrayOfByte, (String)null);
  }

  private void sendPacketPath(byte paramByte, byte[] paramArrayOfByte, String paramString)
    throws Exception
  {
    this.packet.reset();
    int i = 9 + paramArrayOfByte.length;
    if (paramString == null)
    {
      putHEAD(paramByte, i);
      Buffer localBuffer2 = this.buf;
      int k = this.seq;
      this.seq = (k + 1);
      localBuffer2.putInt(k);
    }
    while (true)
    {
      this.buf.putString(paramArrayOfByte);
      getSession().write(this.packet, this, i + 4);
      return;
      i += 4 + paramString.length();
      putHEAD((byte)-56, i);
      Buffer localBuffer1 = this.buf;
      int j = this.seq;
      this.seq = (j + 1);
      localBuffer1.putInt(j);
      this.buf.putString(Util.str2byte(paramString));
    }
  }

  private void sendPacketPath(byte paramByte, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    sendPacketPath(paramByte, paramArrayOfByte1, paramArrayOfByte2, null);
  }

  private void sendPacketPath(byte paramByte, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString)
    throws Exception
  {
    this.packet.reset();
    int i = 13 + paramArrayOfByte1.length + paramArrayOfByte2.length;
    if (paramString == null)
    {
      putHEAD(paramByte, i);
      Buffer localBuffer2 = this.buf;
      int k = this.seq;
      this.seq = (k + 1);
      localBuffer2.putInt(k);
    }
    while (true)
    {
      this.buf.putString(paramArrayOfByte1);
      this.buf.putString(paramArrayOfByte2);
      getSession().write(this.packet, this, i + 4);
      return;
      i += 4 + paramString.length();
      putHEAD((byte)-56, i);
      Buffer localBuffer1 = this.buf;
      int j = this.seq;
      this.seq = (j + 1);
      localBuffer1.putInt(j);
      this.buf.putString(Util.str2byte(paramString));
    }
  }

  private void sendREAD(byte[] paramArrayOfByte, long paramLong, int paramInt)
    throws Exception
  {
    sendREAD(paramArrayOfByte, paramLong, paramInt, null);
  }

  private void sendREAD(byte[] paramArrayOfByte, long paramLong, int paramInt, RequestQueue paramRequestQueue)
    throws Exception
  {
    this.packet.reset();
    putHEAD((byte)5, 21 + paramArrayOfByte.length);
    Buffer localBuffer = this.buf;
    int i = this.seq;
    this.seq = (i + 1);
    localBuffer.putInt(i);
    this.buf.putString(paramArrayOfByte);
    this.buf.putLong(paramLong);
    this.buf.putInt(paramInt);
    getSession().write(this.packet, this, 4 + (21 + paramArrayOfByte.length));
    if (paramRequestQueue != null)
      paramRequestQueue.add(-1 + this.seq, paramLong, paramInt);
  }

  private void sendREADDIR(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)12, paramArrayOfByte);
  }

  private void sendREADLINK(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)19, paramArrayOfByte);
  }

  private void sendREALPATH(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)16, paramArrayOfByte);
  }

  private void sendREMOVE(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)13, paramArrayOfByte);
  }

  private void sendRENAME(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    if (this.extension_posix_rename);
    for (String str = "posix-rename@openssh.com"; ; str = null)
    {
      sendPacketPath((byte)18, paramArrayOfByte1, paramArrayOfByte2, str);
      return;
    }
  }

  private void sendRMDIR(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)15, paramArrayOfByte);
  }

  private void sendSETSTAT(byte[] paramArrayOfByte, SftpATTRS paramSftpATTRS)
    throws Exception
  {
    this.packet.reset();
    putHEAD((byte)9, 9 + paramArrayOfByte.length + paramSftpATTRS.length());
    Buffer localBuffer = this.buf;
    int i = this.seq;
    this.seq = (i + 1);
    localBuffer.putInt(i);
    this.buf.putString(paramArrayOfByte);
    paramSftpATTRS.dump(this.buf);
    getSession().write(this.packet, this, 4 + (9 + paramArrayOfByte.length + paramSftpATTRS.length()));
  }

  private void sendSTAT(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)17, paramArrayOfByte);
  }

  private void sendSTATVFS(byte[] paramArrayOfByte)
    throws Exception
  {
    sendPacketPath((byte)0, paramArrayOfByte, "statvfs@openssh.com");
  }

  private void sendSYMLINK(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    sendPacketPath((byte)20, paramArrayOfByte1, paramArrayOfByte2);
  }

  private int sendWRITE(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
    throws Exception
  {
    int i = paramInt2;
    this.opacket.reset();
    if (this.obuf.buffer.length < 84 + (paramInt2 + (21 + (13 + this.obuf.index) + paramArrayOfByte1.length)))
      i = this.obuf.buffer.length - (84 + (21 + (13 + this.obuf.index) + paramArrayOfByte1.length));
    putHEAD(this.obuf, (byte)6, i + (21 + paramArrayOfByte1.length));
    Buffer localBuffer = this.obuf;
    int j = this.seq;
    this.seq = (j + 1);
    localBuffer.putInt(j);
    this.obuf.putString(paramArrayOfByte1);
    this.obuf.putLong(paramLong);
    if (this.obuf.buffer != paramArrayOfByte2)
      this.obuf.putString(paramArrayOfByte2, paramInt1, i);
    while (true)
    {
      getSession().write(this.opacket, this, 4 + (i + (21 + paramArrayOfByte1.length)));
      return i;
      this.obuf.putInt(i);
      this.obuf.skip(i);
    }
  }

  private void setCwd(String paramString)
  {
    this.cwd = paramString;
  }

  private void skip(long paramLong)
    throws IOException
  {
    while (true)
    {
      long l;
      if (paramLong > 0L)
      {
        l = this.io_in.skip(paramLong);
        if (l > 0L);
      }
      else
      {
        return;
      }
      paramLong -= l;
    }
  }

  private void throwStatusError(Buffer paramBuffer, int paramInt)
    throws SftpException
  {
    if ((this.server_version >= 3) && (paramBuffer.getLength() >= 4))
      throw new SftpException(paramInt, Util.byte2str(paramBuffer.getString(), "UTF-8"));
    throw new SftpException(paramInt, "Failure");
  }

  // ERROR //
  public void _put(InputStream paramInputStream, String paramString, SftpProgressMonitor paramSftpProgressMonitor, int paramInt)
    throws SftpException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 169	com/jcraft/jsch/ChannelSftp:io_in	Ljava/io/InputStream;
    //   4: checkcast 659	com/jcraft/jsch/Channel$MyPipedInputStream
    //   7: invokevirtual 662	com/jcraft/jsch/Channel$MyPipedInputStream:updateReadSide	()V
    //   10: aload_2
    //   11: aload_0
    //   12: getfield 177	com/jcraft/jsch/ChannelSftp:fEncoding	Ljava/lang/String;
    //   15: invokestatic 210	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;Ljava/lang/String;)[B
    //   18: astore 6
    //   20: lconst_0
    //   21: lstore 7
    //   23: iload 4
    //   25: iconst_1
    //   26: if_icmpeq +9 -> 35
    //   29: iload 4
    //   31: iconst_2
    //   32: if_icmpne +18 -> 50
    //   35: aload_0
    //   36: aload 6
    //   38: invokespecial 366	com/jcraft/jsch/ChannelSftp:_stat	([B)Lcom/jcraft/jsch/SftpATTRS;
    //   41: invokevirtual 666	com/jcraft/jsch/SftpATTRS:getSize	()J
    //   44: lstore 33
    //   46: lload 33
    //   48: lstore 7
    //   50: iload 4
    //   52: iconst_1
    //   53: if_icmpne +67 -> 120
    //   56: lload 7
    //   58: lconst_0
    //   59: lcmp
    //   60: ifle +60 -> 120
    //   63: aload_1
    //   64: lload 7
    //   66: invokevirtual 650	java/io/InputStream:skip	(J)J
    //   69: lload 7
    //   71: lcmp
    //   72: ifge +48 -> 120
    //   75: new 200	com/jcraft/jsch/SftpException
    //   78: dup
    //   79: iconst_4
    //   80: new 461	java/lang/StringBuilder
    //   83: dup
    //   84: invokespecial 462	java/lang/StringBuilder:<init>	()V
    //   87: ldc_w 668
    //   90: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: aload_2
    //   94: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: invokevirtual 472	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   100: invokespecial 240	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;)V
    //   103: athrow
    //   104: astore 5
    //   106: aload 5
    //   108: instanceof 200
    //   111: ifeq +602 -> 713
    //   114: aload 5
    //   116: checkcast 200	com/jcraft/jsch/SftpException
    //   119: athrow
    //   120: iload 4
    //   122: ifne +99 -> 221
    //   125: aload_0
    //   126: aload 6
    //   128: invokespecial 670	com/jcraft/jsch/ChannelSftp:sendOPENW	([B)V
    //   131: new 216	com/jcraft/jsch/ChannelSftp$Header
    //   134: dup
    //   135: aload_0
    //   136: invokespecial 219	com/jcraft/jsch/ChannelSftp$Header:<init>	(Lcom/jcraft/jsch/ChannelSftp;)V
    //   139: astore 10
    //   141: aload_0
    //   142: aload_0
    //   143: getfield 221	com/jcraft/jsch/ChannelSftp:buf	Lcom/jcraft/jsch/Buffer;
    //   146: aload 10
    //   148: invokespecial 225	com/jcraft/jsch/ChannelSftp:header	(Lcom/jcraft/jsch/Buffer;Lcom/jcraft/jsch/ChannelSftp$Header;)Lcom/jcraft/jsch/ChannelSftp$Header;
    //   151: astore 11
    //   153: aload 11
    //   155: getfield 228	com/jcraft/jsch/ChannelSftp$Header:length	I
    //   158: istore 12
    //   160: aload 11
    //   162: getfield 231	com/jcraft/jsch/ChannelSftp$Header:type	I
    //   165: istore 13
    //   167: aload_0
    //   168: aload_0
    //   169: getfield 221	com/jcraft/jsch/ChannelSftp:buf	Lcom/jcraft/jsch/Buffer;
    //   172: iload 12
    //   174: invokespecial 235	com/jcraft/jsch/ChannelSftp:fill	(Lcom/jcraft/jsch/Buffer;I)V
    //   177: iload 13
    //   179: bipush 101
    //   181: if_icmpeq +49 -> 230
    //   184: iload 13
    //   186: bipush 102
    //   188: if_icmpeq +42 -> 230
    //   191: new 200	com/jcraft/jsch/SftpException
    //   194: dup
    //   195: iconst_4
    //   196: new 461	java/lang/StringBuilder
    //   199: dup
    //   200: invokespecial 462	java/lang/StringBuilder:<init>	()V
    //   203: ldc_w 672
    //   206: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   209: iload 13
    //   211: invokevirtual 675	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   214: invokevirtual 472	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   217: invokespecial 240	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;)V
    //   220: athrow
    //   221: aload_0
    //   222: aload 6
    //   224: invokespecial 677	com/jcraft/jsch/ChannelSftp:sendOPENA	([B)V
    //   227: goto -96 -> 131
    //   230: iload 13
    //   232: bipush 101
    //   234: if_icmpne +22 -> 256
    //   237: aload_0
    //   238: getfield 221	com/jcraft/jsch/ChannelSftp:buf	Lcom/jcraft/jsch/Buffer;
    //   241: invokevirtual 246	com/jcraft/jsch/Buffer:getInt	()I
    //   244: istore 14
    //   246: aload_0
    //   247: aload_0
    //   248: getfield 221	com/jcraft/jsch/ChannelSftp:buf	Lcom/jcraft/jsch/Buffer;
    //   251: iload 14
    //   253: invokespecial 249	com/jcraft/jsch/ChannelSftp:throwStatusError	(Lcom/jcraft/jsch/Buffer;I)V
    //   256: aload_0
    //   257: getfield 221	com/jcraft/jsch/ChannelSftp:buf	Lcom/jcraft/jsch/Buffer;
    //   260: invokevirtual 253	com/jcraft/jsch/Buffer:getString	()[B
    //   263: astore 15
    //   265: aconst_null
    //   266: astore 16
    //   268: iconst_1
    //   269: ifne +487 -> 756
    //   272: aload_0
    //   273: getfield 644	com/jcraft/jsch/ChannelSftp:obuf	Lcom/jcraft/jsch/Buffer;
    //   276: getfield 260	com/jcraft/jsch/Buffer:buffer	[B
    //   279: arraylength
    //   280: bipush 84
    //   282: bipush 39
    //   284: aload 15
    //   286: arraylength
    //   287: iadd
    //   288: iadd
    //   289: isub
    //   290: newarray byte
    //   292: astore 16
    //   294: goto +462 -> 756
    //   297: aload_0
    //   298: getfield 151	com/jcraft/jsch/ChannelSftp:seq	I
    //   301: istore 19
    //   303: iconst_0
    //   304: istore 20
    //   306: iconst_1
    //   307: ifne +110 -> 417
    //   310: aload 16
    //   312: arraylength
    //   313: istore 21
    //   315: aload_0
    //   316: getfield 186	com/jcraft/jsch/ChannelSftp:rq	Lcom/jcraft/jsch/ChannelSftp$RequestQueue;
    //   319: invokevirtual 326	com/jcraft/jsch/ChannelSftp$RequestQueue:size	()I
    //   322: istore 22
    //   324: iconst_0
    //   325: istore 23
    //   327: goto +454 -> 781
    //   330: aload_1
    //   331: aload 16
    //   333: iload 24
    //   335: iload 25
    //   337: invokevirtual 311	java/io/InputStream:read	([BII)I
    //   340: istore 27
    //   342: iload 27
    //   344: ifle +451 -> 795
    //   347: iload 24
    //   349: iload 27
    //   351: iadd
    //   352: istore 24
    //   354: iload 25
    //   356: iload 27
    //   358: isub
    //   359: istore 25
    //   361: iload 26
    //   363: iload 27
    //   365: iadd
    //   366: istore 26
    //   368: goto +427 -> 795
    //   371: aload_0
    //   372: getfield 151	com/jcraft/jsch/ChannelSftp:seq	I
    //   375: iload 19
    //   377: isub
    //   378: istore 28
    //   380: iload 28
    //   382: iload 23
    //   384: if_icmple +13 -> 397
    //   387: aload_0
    //   388: aconst_null
    //   389: aload 11
    //   391: invokespecial 356	com/jcraft/jsch/ChannelSftp:checkStatus	([ILcom/jcraft/jsch/ChannelSftp$Header;)Z
    //   394: ifne +313 -> 707
    //   397: aload_3
    //   398: ifnull +9 -> 407
    //   401: aload_3
    //   402: invokeinterface 284 1 0
    //   407: aload_0
    //   408: aload 15
    //   410: aload 11
    //   412: invokespecial 292	com/jcraft/jsch/ChannelSftp:_sendCLOSE	([BLcom/jcraft/jsch/ChannelSftp$Header;)Z
    //   415: pop
    //   416: return
    //   417: aload_0
    //   418: getfield 644	com/jcraft/jsch/ChannelSftp:obuf	Lcom/jcraft/jsch/Buffer;
    //   421: getfield 260	com/jcraft/jsch/Buffer:buffer	[B
    //   424: astore 16
    //   426: bipush 39
    //   428: aload 15
    //   430: arraylength
    //   431: iadd
    //   432: istore 20
    //   434: bipush 172
    //   436: aload_0
    //   437: getfield 644	com/jcraft/jsch/ChannelSftp:obuf	Lcom/jcraft/jsch/Buffer;
    //   440: getfield 260	com/jcraft/jsch/Buffer:buffer	[B
    //   443: arraylength
    //   444: iload 20
    //   446: isub
    //   447: iadd
    //   448: istore 21
    //   450: goto -135 -> 315
    //   453: iload 30
    //   455: ifle +221 -> 676
    //   458: iconst_m1
    //   459: aload_0
    //   460: getfield 151	com/jcraft/jsch/ChannelSftp:seq	I
    //   463: iadd
    //   464: iload 19
    //   466: if_icmpeq +18 -> 484
    //   469: aload_0
    //   470: getfield 151	com/jcraft/jsch/ChannelSftp:seq	I
    //   473: iload 19
    //   475: isub
    //   476: iload 23
    //   478: isub
    //   479: iload 22
    //   481: if_icmplt +174 -> 655
    //   484: aload_0
    //   485: getfield 151	com/jcraft/jsch/ChannelSftp:seq	I
    //   488: iload 19
    //   490: isub
    //   491: iload 23
    //   493: isub
    //   494: iload 22
    //   496: if_icmplt +159 -> 655
    //   499: aload_0
    //   500: aload_0
    //   501: getfield 153	com/jcraft/jsch/ChannelSftp:ackid	[I
    //   504: aload 11
    //   506: invokespecial 356	com/jcraft/jsch/ChannelSftp:checkStatus	([ILcom/jcraft/jsch/ChannelSftp$Header;)Z
    //   509: ifeq +146 -> 655
    //   512: aload_0
    //   513: getfield 153	com/jcraft/jsch/ChannelSftp:ackid	[I
    //   516: iconst_0
    //   517: iaload
    //   518: istore 31
    //   520: iload 19
    //   522: iload 31
    //   524: if_icmpgt +14 -> 538
    //   527: iload 31
    //   529: iconst_m1
    //   530: aload_0
    //   531: getfield 151	com/jcraft/jsch/ChannelSftp:seq	I
    //   534: iadd
    //   535: if_icmple +285 -> 820
    //   538: iload 31
    //   540: aload_0
    //   541: getfield 151	com/jcraft/jsch/ChannelSftp:seq	I
    //   544: if_icmpne +57 -> 601
    //   547: getstatic 681	java/lang/System:err	Ljava/io/PrintStream;
    //   550: new 461	java/lang/StringBuilder
    //   553: dup
    //   554: invokespecial 462	java/lang/StringBuilder:<init>	()V
    //   557: ldc_w 683
    //   560: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   563: iload 19
    //   565: invokevirtual 675	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   568: ldc_w 685
    //   571: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   574: aload_0
    //   575: getfield 151	com/jcraft/jsch/ChannelSftp:seq	I
    //   578: invokevirtual 675	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   581: ldc_w 687
    //   584: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   587: iload 31
    //   589: invokevirtual 675	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   592: invokevirtual 472	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   595: invokevirtual 692	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   598: goto +222 -> 820
    //   601: new 200	com/jcraft/jsch/SftpException
    //   604: dup
    //   605: iconst_4
    //   606: new 461	java/lang/StringBuilder
    //   609: dup
    //   610: invokespecial 462	java/lang/StringBuilder:<init>	()V
    //   613: ldc_w 683
    //   616: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   619: iload 19
    //   621: invokevirtual 675	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   624: ldc_w 685
    //   627: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   630: aload_0
    //   631: getfield 151	com/jcraft/jsch/ChannelSftp:seq	I
    //   634: invokevirtual 675	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   637: ldc_w 687
    //   640: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   643: iload 31
    //   645: invokevirtual 675	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   648: invokevirtual 472	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   651: invokespecial 240	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;)V
    //   654: athrow
    //   655: iload 30
    //   657: aload_0
    //   658: aload 15
    //   660: lload 17
    //   662: aload 16
    //   664: iconst_0
    //   665: iload 30
    //   667: invokespecial 393	com/jcraft/jsch/ChannelSftp:sendWRITE	([BJ[BII)I
    //   670: isub
    //   671: istore 30
    //   673: goto -220 -> 453
    //   676: lload 17
    //   678: iload 26
    //   680: i2l
    //   681: ladd
    //   682: lstore 17
    //   684: aload_3
    //   685: ifnull +96 -> 781
    //   688: aload_3
    //   689: iload 26
    //   691: i2l
    //   692: invokeinterface 318 3 0
    //   697: istore 32
    //   699: iload 32
    //   701: ifne +80 -> 781
    //   704: goto -333 -> 371
    //   707: iinc 23 1
    //   710: goto -330 -> 380
    //   713: aload 5
    //   715: instanceof 328
    //   718: ifeq +19 -> 737
    //   721: new 200	com/jcraft/jsch/SftpException
    //   724: dup
    //   725: iconst_4
    //   726: aload 5
    //   728: invokevirtual 693	java/lang/Exception:toString	()Ljava/lang/String;
    //   731: aload 5
    //   733: invokespecial 331	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;Ljava/lang/Throwable;)V
    //   736: athrow
    //   737: new 200	com/jcraft/jsch/SftpException
    //   740: dup
    //   741: iconst_4
    //   742: aload 5
    //   744: invokevirtual 693	java/lang/Exception:toString	()Ljava/lang/String;
    //   747: invokespecial 240	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;)V
    //   750: athrow
    //   751: astore 9
    //   753: goto -703 -> 50
    //   756: lconst_0
    //   757: lstore 17
    //   759: iload 4
    //   761: iconst_1
    //   762: if_icmpeq +9 -> 771
    //   765: iload 4
    //   767: iconst_2
    //   768: if_icmpne -471 -> 297
    //   771: lload 17
    //   773: lload 7
    //   775: ladd
    //   776: lstore 17
    //   778: goto -481 -> 297
    //   781: iload 20
    //   783: istore 24
    //   785: iload 21
    //   787: istore 25
    //   789: iconst_0
    //   790: istore 26
    //   792: goto -462 -> 330
    //   795: iload 25
    //   797: ifle +8 -> 805
    //   800: iload 27
    //   802: ifgt -472 -> 330
    //   805: iload 26
    //   807: ifgt +6 -> 813
    //   810: goto -439 -> 371
    //   813: iload 26
    //   815: istore 30
    //   817: goto -364 -> 453
    //   820: iinc 23 1
    //   823: goto -339 -> 484
    //
    // Exception table:
    //   from	to	target	type
    //   0	20	104	java/lang/Exception
    //   63	104	104	java/lang/Exception
    //   125	131	104	java/lang/Exception
    //   131	177	104	java/lang/Exception
    //   191	221	104	java/lang/Exception
    //   221	227	104	java/lang/Exception
    //   237	256	104	java/lang/Exception
    //   256	265	104	java/lang/Exception
    //   272	294	104	java/lang/Exception
    //   297	303	104	java/lang/Exception
    //   310	315	104	java/lang/Exception
    //   315	324	104	java/lang/Exception
    //   330	342	104	java/lang/Exception
    //   371	380	104	java/lang/Exception
    //   387	397	104	java/lang/Exception
    //   401	407	104	java/lang/Exception
    //   407	416	104	java/lang/Exception
    //   417	450	104	java/lang/Exception
    //   458	484	104	java/lang/Exception
    //   484	520	104	java/lang/Exception
    //   527	538	104	java/lang/Exception
    //   538	598	104	java/lang/Exception
    //   601	655	104	java/lang/Exception
    //   655	673	104	java/lang/Exception
    //   688	699	104	java/lang/Exception
    //   35	46	751	java/lang/Exception
  }

  public void cd(String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      str = isUnique(remoteAbsolutePath(paramString));
      arrayOfByte = _realpath(str);
      localSftpATTRS = _stat(arrayOfByte);
      if ((0x4 & localSftpATTRS.getFlags()) == 0)
        throw new SftpException(4, "Can't change directory: " + str);
    }
    catch (Exception localException)
    {
      String str;
      byte[] arrayOfByte;
      SftpATTRS localSftpATTRS;
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        if (!localSftpATTRS.isDir())
          throw new SftpException(4, "Can't change directory: " + str);
        setCwd(Util.byte2str(arrayOfByte, this.fEncoding));
        return;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public void chgrp(int paramInt, String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      Vector localVector = glob_remote(remoteAbsolutePath(paramString));
      int i = localVector.size();
      for (int j = 0; j < i; j++)
      {
        String str = (String)localVector.elementAt(j);
        SftpATTRS localSftpATTRS = _stat(str);
        localSftpATTRS.setFLAGS(0);
        localSftpATTRS.setUIDGID(localSftpATTRS.uid, paramInt);
        _setStat(str, localSftpATTRS);
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
      throw new SftpException(4, "");
    }
  }

  public void chmod(int paramInt, String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      Vector localVector = glob_remote(remoteAbsolutePath(paramString));
      int i = localVector.size();
      for (int j = 0; j < i; j++)
      {
        String str = (String)localVector.elementAt(j);
        SftpATTRS localSftpATTRS = _stat(str);
        localSftpATTRS.setFLAGS(0);
        localSftpATTRS.setPERMISSIONS(paramInt);
        _setStat(str, localSftpATTRS);
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
      throw new SftpException(4, "");
    }
  }

  public void chown(int paramInt, String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      Vector localVector = glob_remote(remoteAbsolutePath(paramString));
      int i = localVector.size();
      for (int j = 0; j < i; j++)
      {
        String str = (String)localVector.elementAt(j);
        SftpATTRS localSftpATTRS = _stat(str);
        localSftpATTRS.setFLAGS(0);
        localSftpATTRS.setUIDGID(paramInt, localSftpATTRS.gid);
        _setStat(str, localSftpATTRS);
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
      throw new SftpException(4, "");
    }
  }

  public void disconnect()
  {
    super.disconnect();
  }

  public void exit()
  {
    disconnect();
  }

  public InputStream get(String paramString)
    throws SftpException
  {
    return get(paramString, null, 0L);
  }

  public InputStream get(String paramString, int paramInt)
    throws SftpException
  {
    return get(paramString, null, 0L);
  }

  public InputStream get(String paramString, SftpProgressMonitor paramSftpProgressMonitor)
    throws SftpException
  {
    return get(paramString, paramSftpProgressMonitor, 0L);
  }

  public InputStream get(String paramString, SftpProgressMonitor paramSftpProgressMonitor, int paramInt)
    throws SftpException
  {
    return get(paramString, paramSftpProgressMonitor, 0L);
  }

  public InputStream get(String paramString, SftpProgressMonitor paramSftpProgressMonitor, final long paramLong)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      String str = isUnique(remoteAbsolutePath(paramString));
      byte[] arrayOfByte1 = Util.str2byte(str, this.fEncoding);
      SftpATTRS localSftpATTRS = _stat(arrayOfByte1);
      if (paramSftpProgressMonitor != null)
        paramSftpProgressMonitor.init(1, str, "??", localSftpATTRS.getSize());
      sendOPENR(arrayOfByte1);
      Header localHeader1 = new Header();
      Header localHeader2 = header(this.buf, localHeader1);
      int i = localHeader2.length;
      j = localHeader2.type;
      fill(this.buf, i);
      if ((j != 101) && (j != 102))
        throw new SftpException(4, "");
    }
    catch (Exception localException)
    {
      int j;
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        if (j == 101)
        {
          int k = this.buf.getInt();
          throwStatusError(this.buf, k);
        }
        final byte[] arrayOfByte2 = this.buf.getString();
        this.rq.init();
        InputStream local2 = new InputStream()
        {
          byte[] _data = new byte[1];
          boolean closed = false;
          ChannelSftp.Header header = new ChannelSftp.Header(ChannelSftp.this);
          long offset = paramLong;
          int request_max = 1;
          long request_offset = this.offset;
          byte[] rest_byte = new byte[1024];
          int rest_length = 0;

          public void close()
            throws IOException
          {
            if (this.closed)
              return;
            this.closed = true;
            if (arrayOfByte2 != null)
              arrayOfByte2.end();
            ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
            try
            {
              ChannelSftp.this._sendCLOSE(this.val$handle, this.header);
              return;
            }
            catch (Exception localException)
            {
            }
            throw new IOException("error");
          }

          public int read()
            throws IOException
          {
            if (this.closed);
            while (read(this._data, 0, 1) == -1)
              return -1;
            return 0xFF & this._data[0];
          }

          public int read(byte[] paramAnonymousArrayOfByte)
            throws IOException
          {
            if (this.closed)
              return -1;
            return read(paramAnonymousArrayOfByte, 0, paramAnonymousArrayOfByte.length);
          }

          public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
            throws IOException
          {
            if (this.closed)
              return -1;
            if (paramAnonymousArrayOfByte == null)
              throw new NullPointerException();
            if ((paramAnonymousInt1 < 0) || (paramAnonymousInt2 < 0) || (paramAnonymousInt1 + paramAnonymousInt2 > paramAnonymousArrayOfByte.length))
              throw new IndexOutOfBoundsException();
            if (paramAnonymousInt2 == 0)
              return 0;
            if (this.rest_length > 0)
            {
              int i7 = this.rest_length;
              if (i7 > paramAnonymousInt2)
                i7 = paramAnonymousInt2;
              System.arraycopy(this.rest_byte, 0, paramAnonymousArrayOfByte, paramAnonymousInt1, i7);
              int i8 = this.rest_length;
              if (i7 != i8)
              {
                byte[] arrayOfByte1 = this.rest_byte;
                byte[] arrayOfByte2 = this.rest_byte;
                int i9 = this.rest_length - i7;
                System.arraycopy(arrayOfByte1, i7, arrayOfByte2, 0, i9);
              }
              if ((arrayOfByte2 != null) && (!arrayOfByte2.count(i7)))
              {
                close();
                return -1;
              }
              this.rest_length -= i7;
              return i7;
            }
            if (-13 + ChannelSftp.this.buf.buffer.length < paramAnonymousInt2)
              paramAnonymousInt2 = -13 + ChannelSftp.this.buf.buffer.length;
            if ((ChannelSftp.this.server_version == 0) && (paramAnonymousInt2 > 1024))
              paramAnonymousInt2 = 1024;
            if (ChannelSftp.this.rq.count() == 0)
            {
              int i6 = -13 + ChannelSftp.this.buf.buffer.length;
              if (ChannelSftp.this.server_version == 0)
                i6 = 1024;
              while (ChannelSftp.this.rq.count() < this.request_max)
                try
                {
                  ChannelSftp.this.sendREAD(this.val$handle, this.request_offset, i6, ChannelSftp.this.rq);
                  this.request_offset += i6;
                }
                catch (Exception localException2)
                {
                  throw new IOException("error");
                }
            }
            this.header = ChannelSftp.this.header(ChannelSftp.this.buf, this.header);
            this.rest_length = this.header.length;
            int i = this.header.type;
            ChannelSftp.RequestQueue.Request localRequest;
            try
            {
              localRequest = ChannelSftp.this.rq.get(this.header.rid);
              if ((i != 101) && (i != 103))
                throw new IOException("error");
            }
            catch (ChannelSftp.RequestQueue.OutOfOrderException localOutOfOrderException)
            {
              this.request_offset = localOutOfOrderException.offset;
              skip(this.header.length);
              ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
              return 0;
            }
            catch (SftpException localSftpException)
            {
              throw new IOException("error: " + localSftpException.toString());
            }
            if (i == 101)
            {
              ChannelSftp.this.fill(ChannelSftp.this.buf, this.rest_length);
              int i5 = ChannelSftp.this.buf.getInt();
              this.rest_length = 0;
              if (i5 == 1)
              {
                close();
                return -1;
              }
              throw new IOException("error");
            }
            ChannelSftp.this.buf.rewind();
            ChannelSftp.this.fill(ChannelSftp.this.buf.buffer, 0, 4);
            int j = ChannelSftp.this.buf.getInt();
            this.rest_length = (-4 + this.rest_length);
            int k = this.rest_length - j;
            this.offset += j;
            if (j > 0)
            {
              int m = j;
              if (m > paramAnonymousInt2)
                m = paramAnonymousInt2;
              int n = ChannelSftp.this.io_in.read(paramAnonymousArrayOfByte, paramAnonymousInt1, m);
              if (n < 0)
                return -1;
              int i1 = j - n;
              this.rest_length = i1;
              int i2;
              int i3;
              if (i1 > 0)
              {
                if (this.rest_byte.length < i1)
                  this.rest_byte = new byte[i1];
                i2 = 0;
                i3 = i1;
              }
              while (true)
              {
                int i4;
                if (i3 > 0)
                {
                  i4 = ChannelSftp.this.io_in.read(this.rest_byte, i2, i3);
                  if (i4 > 0);
                }
                else
                {
                  if (k > 0)
                    ChannelSftp.this.io_in.skip(k);
                  if (j < localRequest.length)
                    ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                }
                try
                {
                  ChannelSftp.this.sendREAD(this.val$handle, localRequest.offset + j, (int)(localRequest.length - j), ChannelSftp.this.rq);
                  this.request_offset = (localRequest.offset + localRequest.length);
                  if (this.request_max < ChannelSftp.this.rq.size())
                    this.request_max = (1 + this.request_max);
                  if ((arrayOfByte2 != null) && (!arrayOfByte2.count(n)))
                  {
                    close();
                    return -1;
                    i2 += i4;
                    i3 -= i4;
                  }
                }
                catch (Exception localException1)
                {
                  throw new IOException("error");
                }
              }
              return n;
            }
            return 0;
          }
        };
        return local2;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public void get(String paramString, OutputStream paramOutputStream)
    throws SftpException
  {
    get(paramString, paramOutputStream, null, 0, 0L);
  }

  public void get(String paramString, OutputStream paramOutputStream, SftpProgressMonitor paramSftpProgressMonitor)
    throws SftpException
  {
    get(paramString, paramOutputStream, paramSftpProgressMonitor, 0, 0L);
  }

  public void get(String paramString, OutputStream paramOutputStream, SftpProgressMonitor paramSftpProgressMonitor, int paramInt, long paramLong)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      String str = isUnique(remoteAbsolutePath(paramString));
      if (paramSftpProgressMonitor != null)
      {
        paramSftpProgressMonitor.init(1, str, "??", _stat(str).getSize());
        if (paramInt == 1)
          paramSftpProgressMonitor.count(paramLong);
      }
      _get(str, paramOutputStream, paramSftpProgressMonitor, paramInt, paramLong);
      return;
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public void get(String paramString1, String paramString2)
    throws SftpException
  {
    get(paramString1, paramString2, null, 0);
  }

  public void get(String paramString1, String paramString2, SftpProgressMonitor paramSftpProgressMonitor)
    throws SftpException
  {
    get(paramString1, paramString2, paramSftpProgressMonitor, 0);
  }

  public void get(String paramString1, String paramString2, SftpProgressMonitor paramSftpProgressMonitor, int paramInt)
    throws SftpException
  {
    int i = 0;
    Object localObject1 = null;
    long l1;
    long l2;
    label645: label657: 
    do
      while (true)
      {
        String str2;
        try
        {
          ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
          String str1 = remoteAbsolutePath(paramString1);
          str2 = localAbsolutePath(paramString2);
          localVector = glob_remote(str1);
          j = localVector.size();
          localObject1 = null;
          i = 0;
          if (j == 0)
            throw new SftpException(2, "No such file");
        }
        catch (Exception localException)
        {
          Vector localVector;
          int j;
          if ((i == 0) && (localObject1 != null))
          {
            File localFile1 = new File((String)localObject1);
            if ((localFile1.exists()) && (localFile1.length() == 0L))
              localFile1.delete();
          }
          if ((localException instanceof SftpException))
          {
            throw ((SftpException)localException);
            File localFile2 = new File(str2);
            boolean bool1 = localFile2.isDirectory();
            localObject1 = null;
            i = 0;
            StringBuffer localStringBuffer;
            String str4;
            SftpATTRS localSftpATTRS;
            if (bool1)
            {
              String str3 = file_separator;
              boolean bool2 = str2.endsWith(str3);
              localObject1 = null;
              i = 0;
              if (!bool2)
                str2 = str2 + file_separator;
              localStringBuffer = new StringBuffer(str2);
              break label645;
              if (k >= j)
                return;
              str4 = (String)localVector.elementAt(k);
              localSftpATTRS = _stat(str4);
              if (localSftpATTRS.isDir())
                throw new SftpException(4, "not supported to get directory " + str4);
            }
            else
            {
              localObject1 = null;
              i = 0;
              localStringBuffer = null;
              if (j <= 1)
                break label645;
              throw new SftpException(4, "Copying multiple files, but destination is missing or a file.");
            }
            localObject1 = null;
            if (!bool1)
              break label657;
            int m = str4.lastIndexOf('/');
            File localFile3;
            if (m == -1)
            {
              localStringBuffer.append(str4);
              localObject1 = localStringBuffer.toString();
              int n = str2.length();
              int i1 = ((String)localObject1).length();
              localStringBuffer.delete(n, i1);
              localFile3 = new File((String)localObject1);
              if (paramInt == 1)
              {
                l1 = localSftpATTRS.getSize();
                l2 = localFile3.length();
                if (l2 <= l1)
                  break;
                throw new SftpException(4, "failed to resume for " + (String)localObject1);
              }
            }
            else
            {
              String str5 = str4.substring(m + 1);
              localStringBuffer.append(str5);
              continue;
            }
            if (paramSftpProgressMonitor != null)
            {
              paramSftpProgressMonitor.init(1, str4, (String)localObject1, localSftpATTRS.getSize());
              if (paramInt == 1)
                paramSftpProgressMonitor.count(localFile3.length());
            }
            Object localObject2 = null;
            boolean bool3 = localFile3.exists();
            i = bool3;
            if (paramInt == 0);
            try
            {
              FileOutputStream localFileOutputStream2 = new FileOutputStream((String)localObject1);
              localObject2 = localFileOutputStream2;
              _get(str4, localObject2, paramSftpProgressMonitor, paramInt, new File((String)localObject1).length());
              if (localObject2 != null)
                localObject2.close();
              k++;
              continue;
              FileOutputStream localFileOutputStream1 = new FileOutputStream((String)localObject1, true);
              localObject2 = localFileOutputStream1;
              continue;
            }
            finally
            {
              if (localObject2 != null)
                localObject2.close();
            }
          }
          else
          {
            if ((localException instanceof Throwable))
              throw new SftpException(4, "", localException);
            throw new SftpException(4, "");
          }
        }
        localObject1 = null;
        i = 0;
        int k = 0;
        continue;
        localObject1 = str2;
      }
    while (l2 != l1);
  }

  public int getBulkRequests()
  {
    return this.rq.size();
  }

  public String getExtension(String paramString)
  {
    if (this.extensions == null)
      return null;
    return (String)this.extensions.get(paramString);
  }

  public String getHome()
    throws SftpException
  {
    if (this.home == null);
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      this.home = Util.byte2str(_realpath(""), this.fEncoding);
      return this.home;
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public int getServerVersion()
    throws SftpException
  {
    if (!isConnected())
      throw new SftpException(4, "The channel is not connected.");
    return this.server_version;
  }

  public void hardlink(String paramString1, String paramString2)
    throws SftpException
  {
    if (!this.extension_hardlink)
      throw new SftpException(8, "hardlink@openssh.com is not supported");
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      String str1 = remoteAbsolutePath(paramString1);
      str2 = remoteAbsolutePath(paramString2);
      str3 = isUnique(str1);
      if (paramString1.charAt(0) != '/')
      {
        String str6 = getCwd();
        int m = str6.length();
        if (str6.endsWith("/"))
        {
          n = 0;
          str4 = str3.substring(n + m);
          if (!isPattern(str2))
            break label147;
          throw new SftpException(4, str2);
        }
      }
    }
    catch (Exception localException)
    {
      String str2;
      String str3;
      int n;
      String str4;
      while ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        n = 1;
        continue;
        str4 = str3;
        continue;
        label147: String str5 = Util.unquote(str2);
        sendHARDLINK(Util.str2byte(str4, this.fEncoding), Util.str2byte(str5, this.fEncoding));
        Header localHeader1 = new Header();
        Header localHeader2 = header(this.buf, localHeader1);
        int i = localHeader2.length;
        int j = localHeader2.type;
        fill(this.buf, i);
        if (j != 101)
          throw new SftpException(4, "");
        int k = this.buf.getInt();
        if (k == 0)
          return;
        throwStatusError(this.buf, k);
        return;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  void init()
  {
  }

  public void lcd(String paramString)
    throws SftpException
  {
    Object localObject = localAbsolutePath(paramString);
    if (new File((String)localObject).isDirectory());
    try
    {
      String str = new File((String)localObject).getCanonicalPath();
      localObject = str;
      label36: this.lcwd = ((String)localObject);
      return;
      throw new SftpException(2, "No such directory");
    }
    catch (Exception localException)
    {
      break label36;
    }
  }

  public String lpwd()
  {
    return this.lcwd;
  }

  public Vector ls(String paramString)
    throws SftpException
  {
    final Vector localVector = new Vector();
    ls(paramString, new LsEntrySelector()
    {
      public int select(ChannelSftp.LsEntry paramAnonymousLsEntry)
      {
        localVector.addElement(paramAnonymousLsEntry);
        return 0;
      }
    });
    return localVector;
  }

  public void ls(String paramString, LsEntrySelector paramLsEntrySelector)
    throws SftpException
  {
    boolean bool1;
    Object localObject2;
    Header localHeader2;
    int m;
    while (true)
    {
      String str1;
      int i;
      String str3;
      Object localObject1;
      byte[][] arrayOfByte;
      try
      {
        ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
        str1 = remoteAbsolutePath(paramString);
        new Vector();
        i = str1.lastIndexOf('/');
        if (i != 0)
          break label194;
        j = 1;
        String str2 = str1.substring(0, j);
        str3 = str1.substring(i + 1);
        localObject1 = Util.unquote(str2);
        arrayOfByte = new byte[1][];
        bool1 = isPattern(str3, arrayOfByte);
        if (!bool1)
          break label201;
        localObject2 = arrayOfByte[0];
        sendOPENDIR(Util.str2byte((String)localObject1, this.fEncoding));
        Header localHeader1 = new Header();
        localHeader2 = header(this.buf, localHeader1);
        int k = localHeader2.length;
        m = localHeader2.type;
        fill(this.buf, k);
        if ((m == 101) || (m == 102))
          break;
        throw new SftpException(4, "");
      }
      catch (Exception localException)
      {
        if (!(localException instanceof SftpException))
          break label804;
      }
      throw ((SftpException)localException);
      label194: int j = i;
      continue;
      label201: String str7 = Util.unquote(str1);
      if (_stat(str7).isDir())
      {
        localObject1 = str7;
        localObject2 = null;
      }
      else if (this.fEncoding_is_utf8)
      {
        localObject2 = Util.unquote(arrayOfByte[0]);
      }
      else
      {
        localObject2 = Util.str2byte(Util.unquote(str3), this.fEncoding);
      }
    }
    if (m == 101)
    {
      int n = this.buf.getInt();
      throwStatusError(this.buf, n);
    }
    int i1 = 0;
    byte[] arrayOfByte1 = this.buf.getString();
    while (true)
    {
      int i2;
      int i4;
      if (i1 == 0)
      {
        sendREADDIR(arrayOfByte1);
        localHeader2 = header(this.buf, localHeader2);
        i2 = localHeader2.length;
        int i3 = localHeader2.type;
        if ((i3 != 101) && (i3 != 104))
          throw new SftpException(4, "");
        if (i3 != 101)
          break label418;
        fill(this.buf, i2);
        i4 = this.buf.getInt();
        if (i4 != 1);
      }
      else
      {
        _sendCLOSE(arrayOfByte1, localHeader2);
        return;
      }
      throwStatusError(this.buf, i4);
      label418: this.buf.rewind();
      fill(this.buf.buffer, 0, 4);
      int i5 = i2 - 4;
      int i6 = this.buf.getInt();
      this.buf.reset();
      while (i6 > 0)
      {
        if (i5 > 0)
        {
          this.buf.shift();
          if (this.buf.buffer.length <= i5 + this.buf.index)
            break label605;
        }
        byte[] arrayOfByte2;
        byte[] arrayOfByte3;
        SftpATTRS localSftpATTRS;
        label605: for (int i7 = i5; ; i7 = this.buf.buffer.length - this.buf.index)
        {
          int i8 = fill(this.buf.buffer, this.buf.index, i7);
          Buffer localBuffer = this.buf;
          localBuffer.index = (i8 + localBuffer.index);
          i5 -= i8;
          arrayOfByte2 = this.buf.getString();
          int i9 = this.server_version;
          arrayOfByte3 = null;
          if (i9 <= 3)
            arrayOfByte3 = this.buf.getString();
          localSftpATTRS = SftpATTRS.getATTR(this.buf);
          if (i1 != 1)
            break label838;
          i6--;
          break;
        }
        boolean bool2;
        while (bool2)
        {
          if (str4 == null)
            str4 = Util.byte2str(arrayOfByte2, this.fEncoding);
          Object localObject3;
          if (arrayOfByte3 == null)
          {
            localObject3 = localSftpATTRS.toString() + " " + str4;
            label683: LsEntry localLsEntry = new LsEntry(str4, (String)localObject3, localSftpATTRS);
            i1 = paramLsEntrySelector.select(localLsEntry);
            break;
          }
          label804: label838: 
          do
          {
            if (!bool1)
            {
              bool2 = Util.array_equals((byte[])localObject2, arrayOfByte2);
              str4 = null;
              break;
            }
            byte[] arrayOfByte4 = arrayOfByte2;
            boolean bool3 = this.fEncoding_is_utf8;
            str4 = null;
            if (!bool3)
            {
              str4 = Util.byte2str(arrayOfByte4, this.fEncoding);
              arrayOfByte4 = Util.str2byte(str4, "UTF-8");
            }
            bool2 = Util.glob((byte[])localObject2, arrayOfByte4);
            break;
            String str5 = this.fEncoding;
            String str6 = Util.byte2str(arrayOfByte3, str5);
            localObject3 = str6;
            break label683;
            if ((localException instanceof Throwable))
            {
              SftpException localSftpException = new SftpException(4, "", localException);
              throw localSftpException;
            }
            throw new SftpException(4, "");
          }
          while (localObject2 != null);
          bool2 = true;
          String str4 = null;
        }
        i6--;
      }
    }
  }

  public SftpATTRS lstat(String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      SftpATTRS localSftpATTRS = _lstat(isUnique(remoteAbsolutePath(paramString)));
      return localSftpATTRS;
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public void mkdir(String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      sendMKDIR(Util.str2byte(remoteAbsolutePath(paramString), this.fEncoding), null);
      Header localHeader1 = new Header();
      Header localHeader2 = header(this.buf, localHeader1);
      int i = localHeader2.length;
      int j = localHeader2.type;
      fill(this.buf, i);
      if (j != 101)
        throw new SftpException(4, "");
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        int k = this.buf.getInt();
        if (k == 0)
          return;
        throwStatusError(this.buf, k);
        return;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public OutputStream put(String paramString)
    throws SftpException
  {
    return put(paramString, (SftpProgressMonitor)null, 0);
  }

  public OutputStream put(String paramString, int paramInt)
    throws SftpException
  {
    return put(paramString, (SftpProgressMonitor)null, paramInt);
  }

  public OutputStream put(String paramString, SftpProgressMonitor paramSftpProgressMonitor, int paramInt)
    throws SftpException
  {
    return put(paramString, paramSftpProgressMonitor, paramInt, 0L);
  }

  public OutputStream put(String paramString, final SftpProgressMonitor paramSftpProgressMonitor, int paramInt, long paramLong)
    throws SftpException
  {
    String str;
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      str = isUnique(remoteAbsolutePath(paramString));
      if (!isRemoteDir(str))
        break label76;
      throw new SftpException(4, str + " is a directory");
    }
    catch (Exception localException1)
    {
      if (!(localException1 instanceof SftpException))
        break label277;
    }
    throw ((SftpException)localException1);
    label76: byte[] arrayOfByte1 = Util.str2byte(str, this.fEncoding);
    long l1 = 0L;
    if ((paramInt == 1) || (paramInt == 2));
    try
    {
      long l2 = _stat(arrayOfByte1).getSize();
      l1 = l2;
      if (paramInt == 0)
        sendOPENW(arrayOfByte1);
      int j;
      while (true)
      {
        Header localHeader1 = new Header();
        Header localHeader2 = header(this.buf, localHeader1);
        int i = localHeader2.length;
        j = localHeader2.type;
        fill(this.buf, i);
        if ((j == 101) || (j == 102))
          break;
        throw new SftpException(4, "");
        sendOPENA(arrayOfByte1);
      }
      if (j == 101)
      {
        int k = this.buf.getInt();
        throwStatusError(this.buf, k);
      }
      final byte[] arrayOfByte2 = this.buf.getString();
      if ((paramInt != 1) && (paramInt != 2))
      {
        OutputStream local1 = new OutputStream()
        {
          private int _ackid = 0;
          byte[] _data = new byte[1];
          private int ackcount = 0;
          private int[] ackid = new int[1];
          private ChannelSftp.Header header = new ChannelSftp.Header(ChannelSftp.this);
          private boolean init = true;
          private boolean isClosed = false;
          private int startid = 0;
          private int writecount = 0;

          public void close()
            throws IOException
          {
            if (this.isClosed)
              return;
            flush();
            if (paramSftpProgressMonitor != null)
              paramSftpProgressMonitor.end();
            try
            {
              ChannelSftp.this._sendCLOSE(arrayOfByte2, this.header);
              this.isClosed = true;
              return;
            }
            catch (IOException localIOException)
            {
              throw localIOException;
            }
            catch (Exception localException)
            {
              throw new IOException(localException.toString());
            }
          }

          public void flush()
            throws IOException
          {
            if (this.isClosed)
              throw new IOException("stream already closed");
            if (!this.init)
              try
              {
                while (this.writecount > this.ackcount)
                {
                  if (!ChannelSftp.this.checkStatus(null, this.header))
                    return;
                  this.ackcount = (1 + this.ackcount);
                }
              }
              catch (SftpException localSftpException)
              {
                throw new IOException(localSftpException.toString());
              }
          }

          public void write(int paramAnonymousInt)
            throws IOException
          {
            this._data[0] = ((byte)paramAnonymousInt);
            write(this._data, 0, 1);
          }

          public void write(byte[] paramAnonymousArrayOfByte)
            throws IOException
          {
            write(paramAnonymousArrayOfByte, 0, paramAnonymousArrayOfByte.length);
          }

          public void write(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
            throws IOException
          {
            if (this.init)
            {
              this.startid = ChannelSftp.this.seq;
              this._ackid = ChannelSftp.this.seq;
              this.init = false;
            }
            if (this.isClosed)
              throw new IOException("stream already closed");
            int i = paramAnonymousInt2;
            while (i > 0)
              try
              {
                int j = ChannelSftp.this.sendWRITE(arrayOfByte2, this.val$_offset[0], paramAnonymousArrayOfByte, paramAnonymousInt1, i);
                this.writecount = (1 + this.writecount);
                long[] arrayOfLong = this.val$_offset;
                arrayOfLong[0] += j;
                paramAnonymousInt1 += j;
                i -= j;
                if (((-1 + ChannelSftp.this.seq == this.startid) || (ChannelSftp.this.io_in.available() >= 1024)) && (ChannelSftp.this.io_in.available() > 0) && (ChannelSftp.this.checkStatus(this.ackid, this.header)))
                {
                  this._ackid = this.ackid[0];
                  if ((this.startid > this._ackid) || (this._ackid > -1 + ChannelSftp.this.seq))
                    throw new SftpException(4, "");
                }
              }
              catch (IOException localIOException)
              {
                while (true)
                {
                  throw localIOException;
                  this.ackcount = (1 + this.ackcount);
                }
              }
              catch (Exception localException)
              {
                throw new IOException(localException.toString());
              }
            if ((paramSftpProgressMonitor != null) && (!paramSftpProgressMonitor.count(paramAnonymousInt2)))
            {
              close();
              throw new IOException("canceled");
            }
          }
        };
        return local1;
        label277: if ((localException1 instanceof Throwable))
          throw new SftpException(4, "", localException1);
        throw new SftpException(4, "");
      }
    }
    catch (Exception localException2)
    {
      while (true)
      {
        continue;
        paramLong += l1;
      }
    }
  }

  public void put(InputStream paramInputStream, String paramString)
    throws SftpException
  {
    put(paramInputStream, paramString, null, 0);
  }

  public void put(InputStream paramInputStream, String paramString, int paramInt)
    throws SftpException
  {
    put(paramInputStream, paramString, null, paramInt);
  }

  public void put(InputStream paramInputStream, String paramString, SftpProgressMonitor paramSftpProgressMonitor)
    throws SftpException
  {
    put(paramInputStream, paramString, paramSftpProgressMonitor, 0);
  }

  public void put(InputStream paramInputStream, String paramString, SftpProgressMonitor paramSftpProgressMonitor, int paramInt)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      paramString = remoteAbsolutePath(paramString);
      localVector = glob_remote(paramString);
      int i = localVector.size();
      if (i != 1)
        if (i == 0)
          if (isPattern(paramString))
            throw new SftpException(4, paramString);
    }
    catch (Exception localException)
    {
      Vector localVector;
      if ((localException instanceof SftpException))
      {
        if ((((SftpException)localException).id == 4) && (isRemoteDir(paramString)))
        {
          throw new SftpException(4, paramString + " is a directory");
          paramString = Util.unquote(paramString);
          throw new SftpException(4, localVector.toString());
          paramString = (String)localVector.elementAt(0);
          if (paramSftpProgressMonitor != null)
            paramSftpProgressMonitor.init(0, "-", paramString, -1L);
          _put(paramInputStream, paramString, paramSftpProgressMonitor, paramInt);
          return;
        }
        throw ((SftpException)localException);
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, localException.toString(), localException);
      throw new SftpException(4, localException.toString());
    }
  }

  public void put(String paramString1, String paramString2)
    throws SftpException
  {
    put(paramString1, paramString2, null, 0);
  }

  public void put(String paramString1, String paramString2, int paramInt)
    throws SftpException
  {
    put(paramString1, paramString2, null, paramInt);
  }

  public void put(String paramString1, String paramString2, SftpProgressMonitor paramSftpProgressMonitor)
    throws SftpException
  {
    put(paramString1, paramString2, paramSftpProgressMonitor, 0);
  }

  // ERROR //
  public void put(String paramString1, String paramString2, SftpProgressMonitor paramSftpProgressMonitor, int paramInt)
    throws SftpException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 169	com/jcraft/jsch/ChannelSftp:io_in	Ljava/io/InputStream;
    //   4: checkcast 659	com/jcraft/jsch/Channel$MyPipedInputStream
    //   7: invokevirtual 662	com/jcraft/jsch/Channel$MyPipedInputStream:updateReadSide	()V
    //   10: aload_0
    //   11: aload_1
    //   12: invokespecial 765	com/jcraft/jsch/ChannelSftp:localAbsolutePath	(Ljava/lang/String;)Ljava/lang/String;
    //   15: astore 6
    //   17: aload_0
    //   18: aload_2
    //   19: invokespecial 696	com/jcraft/jsch/ChannelSftp:remoteAbsolutePath	(Ljava/lang/String;)Ljava/lang/String;
    //   22: astore 7
    //   24: aload_0
    //   25: aload 7
    //   27: invokespecial 534	com/jcraft/jsch/ChannelSftp:glob_remote	(Ljava/lang/String;)Ljava/util/Vector;
    //   30: astore 8
    //   32: aload 8
    //   34: invokevirtual 535	java/util/Vector:size	()I
    //   37: istore 9
    //   39: iload 9
    //   41: iconst_1
    //   42: if_icmpeq +64 -> 106
    //   45: iload 9
    //   47: ifne +45 -> 92
    //   50: aload_0
    //   51: aload 7
    //   53: invokespecial 821	com/jcraft/jsch/ChannelSftp:isPattern	(Ljava/lang/String;)Z
    //   56: ifeq +30 -> 86
    //   59: new 200	com/jcraft/jsch/SftpException
    //   62: dup
    //   63: iconst_4
    //   64: aload 7
    //   66: invokespecial 240	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;)V
    //   69: athrow
    //   70: astore 5
    //   72: aload 5
    //   74: instanceof 200
    //   77: ifeq +430 -> 507
    //   80: aload 5
    //   82: checkcast 200	com/jcraft/jsch/SftpException
    //   85: athrow
    //   86: aload 7
    //   88: invokestatic 444	com/jcraft/jsch/Util:unquote	(Ljava/lang/String;)Ljava/lang/String;
    //   91: pop
    //   92: new 200	com/jcraft/jsch/SftpException
    //   95: dup
    //   96: iconst_4
    //   97: aload 8
    //   99: invokevirtual 538	java/util/Vector:toString	()Ljava/lang/String;
    //   102: invokespecial 240	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;)V
    //   105: athrow
    //   106: aload 8
    //   108: iconst_0
    //   109: invokevirtual 542	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   112: checkcast 159	java/lang/String
    //   115: checkcast 159	java/lang/String
    //   118: astore 10
    //   120: aload_0
    //   121: aload 10
    //   123: invokespecial 876	com/jcraft/jsch/ChannelSftp:isRemoteDir	(Ljava/lang/String;)Z
    //   126: istore 11
    //   128: aload_0
    //   129: aload 6
    //   131: invokespecial 902	com/jcraft/jsch/ChannelSftp:glob_local	(Ljava/lang/String;)Ljava/util/Vector;
    //   134: astore 12
    //   136: aload 12
    //   138: invokevirtual 535	java/util/Vector:size	()I
    //   141: istore 13
    //   143: iload 11
    //   145: ifeq +233 -> 378
    //   148: aload 10
    //   150: ldc_w 494
    //   153: invokevirtual 514	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   156: ifne +26 -> 182
    //   159: new 461	java/lang/StringBuilder
    //   162: dup
    //   163: invokespecial 462	java/lang/StringBuilder:<init>	()V
    //   166: aload 10
    //   168: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   171: ldc_w 494
    //   174: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: invokevirtual 472	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   180: astore 10
    //   182: new 780	java/lang/StringBuffer
    //   185: dup
    //   186: aload 10
    //   188: invokespecial 781	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   191: astore 14
    //   193: goto +366 -> 559
    //   196: iload 15
    //   198: iload 13
    //   200: if_icmpge +380 -> 580
    //   203: aload 12
    //   205: iload 15
    //   207: invokevirtual 542	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   210: checkcast 159	java/lang/String
    //   213: checkcast 159	java/lang/String
    //   216: astore 16
    //   218: iload 11
    //   220: ifeq +345 -> 565
    //   223: aload 16
    //   225: getstatic 142	com/jcraft/jsch/ChannelSftp:file_separatorc	C
    //   228: invokevirtual 481	java/lang/String:lastIndexOf	(I)I
    //   231: istore 17
    //   233: getstatic 144	com/jcraft/jsch/ChannelSftp:fs_is_bs	Z
    //   236: ifeq +29 -> 265
    //   239: aload 16
    //   241: bipush 47
    //   243: invokevirtual 481	java/lang/String:lastIndexOf	(I)I
    //   246: istore 33
    //   248: iload 33
    //   250: iconst_m1
    //   251: if_icmpeq +14 -> 265
    //   254: iload 33
    //   256: iload 17
    //   258: if_icmple +7 -> 265
    //   261: iload 33
    //   263: istore 17
    //   265: iload 17
    //   267: iconst_m1
    //   268: if_icmpne +131 -> 399
    //   271: aload 14
    //   273: aload 16
    //   275: invokevirtual 788	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   278: pop
    //   279: aload 14
    //   281: invokevirtual 789	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   284: astore 19
    //   286: aload 14
    //   288: aload 10
    //   290: invokevirtual 618	java/lang/String:length	()I
    //   293: aload 19
    //   295: invokevirtual 618	java/lang/String:length	()I
    //   298: invokevirtual 792	java/lang/StringBuffer:delete	(II)Ljava/lang/StringBuffer;
    //   301: pop
    //   302: lconst_0
    //   303: lstore 21
    //   305: iload 4
    //   307: iconst_1
    //   308: if_icmpne +109 -> 417
    //   311: aload_0
    //   312: aload 19
    //   314: invokespecial 710	com/jcraft/jsch/ChannelSftp:_stat	(Ljava/lang/String;)Lcom/jcraft/jsch/SftpATTRS;
    //   317: invokevirtual 666	com/jcraft/jsch/SftpATTRS:getSize	()J
    //   320: lstore 30
    //   322: lload 30
    //   324: lstore 21
    //   326: new 132	java/io/File
    //   329: dup
    //   330: aload 16
    //   332: invokespecial 455	java/io/File:<init>	(Ljava/lang/String;)V
    //   335: invokevirtual 772	java/io/File:length	()J
    //   338: lstore 28
    //   340: lload 28
    //   342: lload 21
    //   344: lcmp
    //   345: ifge +227 -> 572
    //   348: new 200	com/jcraft/jsch/SftpException
    //   351: dup
    //   352: iconst_4
    //   353: new 461	java/lang/StringBuilder
    //   356: dup
    //   357: invokespecial 462	java/lang/StringBuilder:<init>	()V
    //   360: ldc_w 668
    //   363: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   366: aload 19
    //   368: invokevirtual 469	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: invokevirtual 472	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   374: invokespecial 240	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;)V
    //   377: athrow
    //   378: aconst_null
    //   379: astore 14
    //   381: iload 13
    //   383: iconst_1
    //   384: if_icmple +175 -> 559
    //   387: new 200	com/jcraft/jsch/SftpException
    //   390: dup
    //   391: iconst_4
    //   392: ldc_w 904
    //   395: invokespecial 240	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;)V
    //   398: athrow
    //   399: aload 14
    //   401: aload 16
    //   403: iload 17
    //   405: iconst_1
    //   406: iadd
    //   407: invokevirtual 487	java/lang/String:substring	(I)Ljava/lang/String;
    //   410: invokevirtual 788	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   413: pop
    //   414: goto -135 -> 279
    //   417: aload_3
    //   418: ifnull +41 -> 459
    //   421: aload_3
    //   422: iconst_0
    //   423: aload 16
    //   425: aload 19
    //   427: new 132	java/io/File
    //   430: dup
    //   431: aload 16
    //   433: invokespecial 455	java/io/File:<init>	(Ljava/lang/String;)V
    //   436: invokevirtual 772	java/io/File:length	()J
    //   439: invokeinterface 747 6 0
    //   444: iload 4
    //   446: iconst_1
    //   447: if_icmpne +12 -> 459
    //   450: aload_3
    //   451: lload 21
    //   453: invokeinterface 318 3 0
    //   458: pop
    //   459: new 906	java/io/FileInputStream
    //   462: dup
    //   463: aload 16
    //   465: invokespecial 907	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   468: astore 24
    //   470: aload_0
    //   471: aload 24
    //   473: aload 19
    //   475: aload_3
    //   476: iload 4
    //   478: invokevirtual 897	com/jcraft/jsch/ChannelSftp:_put	(Ljava/io/InputStream;Ljava/lang/String;Lcom/jcraft/jsch/SftpProgressMonitor;I)V
    //   481: aload 24
    //   483: ifnull +98 -> 581
    //   486: aload 24
    //   488: invokevirtual 908	java/io/FileInputStream:close	()V
    //   491: goto +90 -> 581
    //   494: aload 26
    //   496: ifnull +8 -> 504
    //   499: aload 26
    //   501: invokevirtual 908	java/io/FileInputStream:close	()V
    //   504: aload 25
    //   506: athrow
    //   507: aload 5
    //   509: instanceof 328
    //   512: ifeq +19 -> 531
    //   515: new 200	com/jcraft/jsch/SftpException
    //   518: dup
    //   519: iconst_4
    //   520: aload 5
    //   522: invokevirtual 693	java/lang/Exception:toString	()Ljava/lang/String;
    //   525: aload 5
    //   527: invokespecial 331	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;Ljava/lang/Throwable;)V
    //   530: athrow
    //   531: new 200	com/jcraft/jsch/SftpException
    //   534: dup
    //   535: iconst_4
    //   536: aload 5
    //   538: invokevirtual 693	java/lang/Exception:toString	()Ljava/lang/String;
    //   541: invokespecial 240	com/jcraft/jsch/SftpException:<init>	(ILjava/lang/String;)V
    //   544: athrow
    //   545: astore 25
    //   547: aload 24
    //   549: astore 26
    //   551: goto -57 -> 494
    //   554: astore 27
    //   556: goto -230 -> 326
    //   559: iconst_0
    //   560: istore 15
    //   562: goto -366 -> 196
    //   565: aload 10
    //   567: astore 19
    //   569: goto -267 -> 302
    //   572: lload 28
    //   574: lload 21
    //   576: lcmp
    //   577: ifne -160 -> 417
    //   580: return
    //   581: iinc 15 1
    //   584: goto -388 -> 196
    //   587: astore 25
    //   589: aconst_null
    //   590: astore 26
    //   592: goto -98 -> 494
    //
    // Exception table:
    //   from	to	target	type
    //   0	39	70	java/lang/Exception
    //   50	70	70	java/lang/Exception
    //   86	92	70	java/lang/Exception
    //   92	106	70	java/lang/Exception
    //   106	143	70	java/lang/Exception
    //   148	182	70	java/lang/Exception
    //   182	193	70	java/lang/Exception
    //   203	218	70	java/lang/Exception
    //   223	248	70	java/lang/Exception
    //   271	279	70	java/lang/Exception
    //   279	302	70	java/lang/Exception
    //   326	340	70	java/lang/Exception
    //   348	378	70	java/lang/Exception
    //   387	399	70	java/lang/Exception
    //   399	414	70	java/lang/Exception
    //   421	444	70	java/lang/Exception
    //   450	459	70	java/lang/Exception
    //   486	491	70	java/lang/Exception
    //   499	504	70	java/lang/Exception
    //   504	507	70	java/lang/Exception
    //   470	481	545	finally
    //   311	322	554	java/lang/Exception
    //   459	470	587	finally
  }

  public String pwd()
    throws SftpException
  {
    return getCwd();
  }

  public void quit()
  {
    disconnect();
  }

  public String readlink(String paramString)
    throws SftpException
  {
    try
    {
      if (this.server_version < 3)
        throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
        sendREADLINK(Util.str2byte(isUnique(remoteAbsolutePath(paramString)), this.fEncoding));
        Header localHeader1 = new Header();
        Header localHeader2 = header(this.buf, localHeader1);
        int i = localHeader2.length;
        int j = localHeader2.type;
        fill(this.buf, i);
        if ((j != 101) && (j != 104))
          throw new SftpException(4, "");
        if (j == 104)
        {
          int k = this.buf.getInt();
          byte[] arrayOfByte = null;
          for (int m = 0; m < k; m++)
          {
            arrayOfByte = this.buf.getString();
            if (this.server_version <= 3)
              this.buf.getString();
            SftpATTRS.getATTR(this.buf);
          }
          return Util.byte2str(arrayOfByte, this.fEncoding);
        }
        int n = this.buf.getInt();
        throwStatusError(this.buf, n);
        return null;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public String realpath(String paramString)
    throws SftpException
  {
    try
    {
      String str = Util.byte2str(_realpath(remoteAbsolutePath(paramString)), this.fEncoding);
      return str;
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public void rename(String paramString1, String paramString2)
    throws SftpException
  {
    if (this.server_version < 2)
      throw new SftpException(8, "The remote sshd is too old to support rename operation.");
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      String str1 = remoteAbsolutePath(paramString1);
      str2 = remoteAbsolutePath(paramString2);
      str3 = isUnique(str1);
      localVector = glob_remote(str2);
      i = localVector.size();
      if (i >= 2)
        throw new SftpException(4, localVector.toString());
    }
    catch (Exception localException)
    {
      String str2;
      String str3;
      Vector localVector;
      int i;
      if ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        if (i == 1);
        for (String str4 = (String)localVector.elementAt(0); ; str4 = Util.unquote(str2))
        {
          sendRENAME(Util.str2byte(str3, this.fEncoding), Util.str2byte(str4, this.fEncoding));
          Header localHeader1 = new Header();
          Header localHeader2 = header(this.buf, localHeader1);
          int j = localHeader2.length;
          int k = localHeader2.type;
          fill(this.buf, j);
          if (k == 101)
            break;
          throw new SftpException(4, "");
          if (isPattern(str2))
            throw new SftpException(4, str2);
        }
        int m = this.buf.getInt();
        if (m == 0)
          return;
        throwStatusError(this.buf, m);
        return;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public void rm(String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      Vector localVector = glob_remote(remoteAbsolutePath(paramString));
      int i = localVector.size();
      Header localHeader = new Header();
      j = 0;
      if (j < i)
      {
        sendREMOVE(Util.str2byte((String)localVector.elementAt(j), this.fEncoding));
        localHeader = header(this.buf, localHeader);
        int k = localHeader.length;
        int m = localHeader.type;
        fill(this.buf, k);
        if (m != 101)
          throw new SftpException(4, "");
      }
    }
    catch (Exception localException)
    {
      int j;
      while ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        int n = this.buf.getInt();
        if (n != 0)
          throwStatusError(this.buf, n);
        j++;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
      throw new SftpException(4, "");
    }
  }

  public void rmdir(String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      Vector localVector = glob_remote(remoteAbsolutePath(paramString));
      int i = localVector.size();
      Header localHeader = new Header();
      j = 0;
      if (j < i)
      {
        sendRMDIR(Util.str2byte((String)localVector.elementAt(j), this.fEncoding));
        localHeader = header(this.buf, localHeader);
        int k = localHeader.length;
        int m = localHeader.type;
        fill(this.buf, k);
        if (m != 101)
          throw new SftpException(4, "");
      }
    }
    catch (Exception localException)
    {
      int j;
      while ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        int n = this.buf.getInt();
        if (n != 0)
          throwStatusError(this.buf, n);
        j++;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
      throw new SftpException(4, "");
    }
  }

  public void setBulkRequests(int paramInt)
    throws JSchException
  {
    if (paramInt > 0)
    {
      this.rq = new RequestQueue(paramInt);
      return;
    }
    throw new JSchException("setBulkRequests: " + paramInt + " must be greater than 0.");
  }

  public void setFilenameEncoding(String paramString)
    throws SftpException
  {
    int i = getServerVersion();
    if ((3 <= i) && (i <= 5) && (!paramString.equals("UTF-8")))
      throw new SftpException(4, "The encoding can not be changed for this sftp server.");
    if (paramString.equals("UTF-8"))
      paramString = "UTF-8";
    this.fEncoding = paramString;
    this.fEncoding_is_utf8 = this.fEncoding.equals("UTF-8");
  }

  public void setMtime(String paramString, int paramInt)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      Vector localVector = glob_remote(remoteAbsolutePath(paramString));
      int i = localVector.size();
      for (int j = 0; j < i; j++)
      {
        String str = (String)localVector.elementAt(j);
        SftpATTRS localSftpATTRS = _stat(str);
        localSftpATTRS.setFLAGS(0);
        localSftpATTRS.setACMODTIME(localSftpATTRS.getATime(), paramInt);
        _setStat(str, localSftpATTRS);
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
      throw new SftpException(4, "");
    }
  }

  public void setStat(String paramString, SftpATTRS paramSftpATTRS)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      Vector localVector = glob_remote(remoteAbsolutePath(paramString));
      int i = localVector.size();
      for (int j = 0; j < i; j++)
        _setStat((String)localVector.elementAt(j), paramSftpATTRS);
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
      throw new SftpException(4, "");
    }
  }

  public void start()
    throws JSchException
  {
    try
    {
      PipedOutputStream localPipedOutputStream = new PipedOutputStream();
      this.io.setOutputStream(localPipedOutputStream);
      Channel.MyPipedInputStream localMyPipedInputStream = new Channel.MyPipedInputStream(this, localPipedOutputStream, this.rmpsize);
      this.io.setInputStream(localMyPipedInputStream);
      this.io_in = this.io.in;
      if (this.io_in == null)
        throw new JSchException("channel is down");
    }
    catch (Exception localException)
    {
      if ((localException instanceof JSchException))
      {
        throw ((JSchException)localException);
        new RequestSftp().request(getSession(), this);
        this.buf = new Buffer(this.lmpsize);
        this.packet = new Packet(this.buf);
        this.obuf = new Buffer(this.rmpsize);
        this.opacket = new Packet(this.obuf);
        sendINIT();
        Header localHeader1 = new Header();
        Header localHeader2 = header(this.buf, localHeader1);
        int i = localHeader2.length;
        if (i > 262144)
          throw new SftpException(4, "Received message is too long: " + i);
        this.server_version = localHeader2.rid;
        this.extensions = new Hashtable();
        if (i > 0)
        {
          fill(this.buf, i);
          while (i > 0)
          {
            byte[] arrayOfByte1 = this.buf.getString();
            int j = i - (4 + arrayOfByte1.length);
            byte[] arrayOfByte2 = this.buf.getString();
            i = j - (4 + arrayOfByte2.length);
            this.extensions.put(Util.byte2str(arrayOfByte1), Util.byte2str(arrayOfByte2));
          }
        }
        if ((this.extensions.get("posix-rename@openssh.com") != null) && (this.extensions.get("posix-rename@openssh.com").equals("1")))
          this.extension_posix_rename = true;
        if ((this.extensions.get("statvfs@openssh.com") != null) && (this.extensions.get("statvfs@openssh.com").equals("2")))
          this.extension_statvfs = true;
        if ((this.extensions.get("hardlink@openssh.com") != null) && (this.extensions.get("hardlink@openssh.com").equals("1")))
          this.extension_hardlink = true;
        this.lcwd = new File(".").getCanonicalPath();
        return;
      }
      if ((localException instanceof Throwable))
        throw new JSchException(localException.toString(), localException);
      throw new JSchException(localException.toString());
    }
  }

  public SftpATTRS stat(String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      SftpATTRS localSftpATTRS = _stat(isUnique(remoteAbsolutePath(paramString)));
      return localSftpATTRS;
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public SftpStatVFS statVFS(String paramString)
    throws SftpException
  {
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      SftpStatVFS localSftpStatVFS = _statVFS(isUnique(remoteAbsolutePath(paramString)));
      return localSftpStatVFS;
    }
    catch (Exception localException)
    {
      if ((localException instanceof SftpException))
        throw ((SftpException)localException);
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public void symlink(String paramString1, String paramString2)
    throws SftpException
  {
    if (this.server_version < 3)
      throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
    try
    {
      ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
      String str1 = remoteAbsolutePath(paramString1);
      str2 = remoteAbsolutePath(paramString2);
      str3 = isUnique(str1);
      if (paramString1.charAt(0) != '/')
      {
        String str6 = getCwd();
        int m = str6.length();
        if (str6.endsWith("/"))
        {
          n = 0;
          str4 = str3.substring(n + m);
          if (!isPattern(str2))
            break label148;
          throw new SftpException(4, str2);
        }
      }
    }
    catch (Exception localException)
    {
      String str2;
      String str3;
      int n;
      String str4;
      while ((localException instanceof SftpException))
      {
        throw ((SftpException)localException);
        n = 1;
        continue;
        str4 = str3;
        continue;
        label148: String str5 = Util.unquote(str2);
        sendSYMLINK(Util.str2byte(str4, this.fEncoding), Util.str2byte(str5, this.fEncoding));
        Header localHeader1 = new Header();
        Header localHeader2 = header(this.buf, localHeader1);
        int i = localHeader2.length;
        int j = localHeader2.type;
        fill(this.buf, i);
        if (j != 101)
          throw new SftpException(4, "");
        int k = this.buf.getInt();
        if (k == 0)
          return;
        throwStatusError(this.buf, k);
        return;
      }
      if ((localException instanceof Throwable))
        throw new SftpException(4, "", localException);
    }
    throw new SftpException(4, "");
  }

  public String version()
  {
    return this.version;
  }

  class Header
  {
    int length;
    int rid;
    int type;

    Header()
    {
    }
  }

  public class LsEntry
    implements Comparable
  {
    private SftpATTRS attrs;
    private String filename;
    private String longname;

    LsEntry(String paramString1, String paramSftpATTRS, SftpATTRS arg4)
    {
      setFilename(paramString1);
      setLongname(paramSftpATTRS);
      SftpATTRS localSftpATTRS;
      setAttrs(localSftpATTRS);
    }

    public int compareTo(Object paramObject)
      throws ClassCastException
    {
      if ((paramObject instanceof LsEntry))
        return this.filename.compareTo(((LsEntry)paramObject).getFilename());
      throw new ClassCastException("a decendent of LsEntry must be given.");
    }

    public SftpATTRS getAttrs()
    {
      return this.attrs;
    }

    public String getFilename()
    {
      return this.filename;
    }

    public String getLongname()
    {
      return this.longname;
    }

    void setAttrs(SftpATTRS paramSftpATTRS)
    {
      this.attrs = paramSftpATTRS;
    }

    void setFilename(String paramString)
    {
      this.filename = paramString;
    }

    void setLongname(String paramString)
    {
      this.longname = paramString;
    }

    public String toString()
    {
      return this.longname;
    }
  }

  public static abstract interface LsEntrySelector
  {
    public static final int BREAK = 1;
    public static final int CONTINUE;

    public abstract int select(ChannelSftp.LsEntry paramLsEntry);
  }

  private class RequestQueue
  {
    int count;
    int head;
    Request[] rrq = null;

    RequestQueue(int arg2)
    {
      int i;
      this.rrq = new Request[i];
      for (int j = 0; j < this.rrq.length; j++)
        this.rrq[j] = new Request();
      init();
    }

    void add(int paramInt1, long paramLong, int paramInt2)
    {
      if (this.count == 0)
        this.head = 0;
      int i = this.head + this.count;
      if (i >= this.rrq.length)
        i -= this.rrq.length;
      this.rrq[i].id = paramInt1;
      this.rrq[i].offset = paramLong;
      this.rrq[i].length = paramInt2;
      this.count = (1 + this.count);
    }

    void cancel(ChannelSftp.Header paramHeader, Buffer paramBuffer)
      throws IOException
    {
      int i = this.count;
      int j = 0;
      if (j < i)
      {
        paramHeader = ChannelSftp.this.header(paramBuffer, paramHeader);
        int k = paramHeader.length;
        for (int m = 0; ; m++)
          if (m < this.rrq.length)
          {
            if (this.rrq[m].id == paramHeader.rid)
              this.rrq[m].id = 0;
          }
          else
          {
            ChannelSftp.this.skip(k);
            j++;
            break;
          }
      }
      init();
    }

    int count()
    {
      return this.count;
    }

    Request get(int paramInt)
      throws ChannelSftp.RequestQueue.OutOfOrderException, SftpException
    {
      this.count = (-1 + this.count);
      int i = this.head;
      this.head = (1 + this.head);
      if (this.head == this.rrq.length)
        this.head = 0;
      if (this.rrq[i].id != paramInt)
      {
        long l = getOffset();
        for (int j = 0; ; j++)
        {
          int k = this.rrq.length;
          int m = 0;
          if (j < k)
          {
            if (this.rrq[j].id == paramInt)
            {
              m = 1;
              this.rrq[j].id = 0;
            }
          }
          else
          {
            if (m == 0)
              break;
            throw new OutOfOrderException(l);
          }
        }
        throw new SftpException(4, "RequestQueue: unknown request id " + paramInt);
      }
      this.rrq[i].id = 0;
      return this.rrq[i];
    }

    long getOffset()
    {
      long l = 9223372036854775807L;
      int i = 0;
      if (i < this.rrq.length)
      {
        if (this.rrq[i].id == 0);
        while (true)
        {
          i++;
          break;
          if (l > this.rrq[i].offset)
            l = this.rrq[i].offset;
        }
      }
      return l;
    }

    void init()
    {
      this.count = 0;
      this.head = 0;
    }

    int size()
    {
      return this.rrq.length;
    }

    class OutOfOrderException extends Exception
    {
      long offset;

      OutOfOrderException(long arg2)
      {
        Object localObject;
        this.offset = localObject;
      }
    }

    class Request
    {
      int id;
      long length;
      long offset;

      Request()
      {
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ChannelSftp
 * JD-Core Version:    0.6.2
 */