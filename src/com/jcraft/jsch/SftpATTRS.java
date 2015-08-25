package com.jcraft.jsch;

import java.util.Date;

public class SftpATTRS
{
  public static final int SSH_FILEXFER_ATTR_ACMODTIME = 8;
  public static final int SSH_FILEXFER_ATTR_EXTENDED = -2147483648;
  public static final int SSH_FILEXFER_ATTR_PERMISSIONS = 4;
  public static final int SSH_FILEXFER_ATTR_SIZE = 1;
  public static final int SSH_FILEXFER_ATTR_UIDGID = 2;
  static final int S_IEXEC = 64;
  static final int S_IFBLK = 24576;
  static final int S_IFCHR = 8192;
  static final int S_IFDIR = 16384;
  static final int S_IFIFO = 4096;
  static final int S_IFLNK = 40960;
  static final int S_IFMT = 61440;
  static final int S_IFREG = 32768;
  static final int S_IFSOCK = 49152;
  static final int S_IREAD = 256;
  static final int S_IRGRP = 32;
  static final int S_IROTH = 4;
  static final int S_IRUSR = 256;
  static final int S_ISGID = 1024;
  static final int S_ISUID = 2048;
  static final int S_ISVTX = 512;
  static final int S_IWGRP = 16;
  static final int S_IWOTH = 2;
  static final int S_IWRITE = 128;
  static final int S_IWUSR = 128;
  static final int S_IXGRP = 8;
  static final int S_IXOTH = 1;
  static final int S_IXUSR = 64;
  private static final int pmask = 4095;
  int atime;
  String[] extended = null;
  int flags = 0;
  int gid;
  int mtime;
  int permissions;
  long size;
  int uid;

  static SftpATTRS getATTR(Buffer paramBuffer)
  {
    SftpATTRS localSftpATTRS = new SftpATTRS();
    localSftpATTRS.flags = paramBuffer.getInt();
    if ((0x1 & localSftpATTRS.flags) != 0)
      localSftpATTRS.size = paramBuffer.getLong();
    if ((0x2 & localSftpATTRS.flags) != 0)
    {
      localSftpATTRS.uid = paramBuffer.getInt();
      localSftpATTRS.gid = paramBuffer.getInt();
    }
    if ((0x4 & localSftpATTRS.flags) != 0)
      localSftpATTRS.permissions = paramBuffer.getInt();
    if ((0x8 & localSftpATTRS.flags) != 0)
      localSftpATTRS.atime = paramBuffer.getInt();
    if ((0x8 & localSftpATTRS.flags) != 0)
      localSftpATTRS.mtime = paramBuffer.getInt();
    if ((0x80000000 & localSftpATTRS.flags) != 0)
    {
      int i = paramBuffer.getInt();
      if (i > 0)
      {
        localSftpATTRS.extended = new String[i * 2];
        for (int j = 0; j < i; j++)
        {
          localSftpATTRS.extended[(j * 2)] = Util.byte2str(paramBuffer.getString());
          localSftpATTRS.extended[(1 + j * 2)] = Util.byte2str(paramBuffer.getString());
        }
      }
    }
    return localSftpATTRS;
  }

  private boolean isType(int paramInt)
  {
    return ((0x4 & this.flags) != 0) && ((0xF000 & this.permissions) == paramInt);
  }

  void dump(Buffer paramBuffer)
  {
    paramBuffer.putInt(this.flags);
    if ((0x1 & this.flags) != 0)
      paramBuffer.putLong(this.size);
    if ((0x2 & this.flags) != 0)
    {
      paramBuffer.putInt(this.uid);
      paramBuffer.putInt(this.gid);
    }
    if ((0x4 & this.flags) != 0)
      paramBuffer.putInt(this.permissions);
    if ((0x8 & this.flags) != 0)
      paramBuffer.putInt(this.atime);
    if ((0x8 & this.flags) != 0)
      paramBuffer.putInt(this.mtime);
    if ((0x80000000 & this.flags) != 0)
    {
      int i = this.extended.length / 2;
      if (i > 0)
        for (int j = 0; j < i; j++)
        {
          paramBuffer.putString(Util.str2byte(this.extended[(j * 2)]));
          paramBuffer.putString(Util.str2byte(this.extended[(1 + j * 2)]));
        }
    }
  }

  public int getATime()
  {
    return this.atime;
  }

  public String getAtimeString()
  {
    return new Date(1000L * this.atime).toString();
  }

  public String[] getExtended()
  {
    return this.extended;
  }

  public int getFlags()
  {
    return this.flags;
  }

  public int getGId()
  {
    return this.gid;
  }

  public int getMTime()
  {
    return this.mtime;
  }

  public String getMtimeString()
  {
    return new Date(1000L * this.mtime).toString();
  }

  public int getPermissions()
  {
    return this.permissions;
  }

  public String getPermissionsString()
  {
    StringBuffer localStringBuffer = new StringBuffer(10);
    if (isDir())
    {
      localStringBuffer.append('d');
      if ((0x100 & this.permissions) == 0)
        break label210;
      localStringBuffer.append('r');
      label42: if ((0x80 & this.permissions) == 0)
        break label220;
      localStringBuffer.append('w');
      label60: if ((0x800 & this.permissions) == 0)
        break label230;
      localStringBuffer.append('s');
      label78: if ((0x20 & this.permissions) == 0)
        break label260;
      localStringBuffer.append('r');
      label95: if ((0x10 & this.permissions) == 0)
        break label270;
      localStringBuffer.append('w');
      label112: if ((0x400 & this.permissions) == 0)
        break label280;
      localStringBuffer.append('s');
      label130: if ((0x4 & this.permissions) == 0)
        break label310;
      localStringBuffer.append('r');
      label146: if ((0x2 & this.permissions) == 0)
        break label320;
      localStringBuffer.append('w');
      label162: if ((0x1 & this.permissions) == 0)
        break label330;
      localStringBuffer.append('x');
    }
    while (true)
    {
      return localStringBuffer.toString();
      if (isLink())
      {
        localStringBuffer.append('l');
        break;
      }
      localStringBuffer.append('-');
      break;
      label210: localStringBuffer.append('-');
      break label42;
      label220: localStringBuffer.append('-');
      break label60;
      label230: if ((0x40 & this.permissions) != 0)
      {
        localStringBuffer.append('x');
        break label78;
      }
      localStringBuffer.append('-');
      break label78;
      label260: localStringBuffer.append('-');
      break label95;
      label270: localStringBuffer.append('-');
      break label112;
      label280: if ((0x8 & this.permissions) != 0)
      {
        localStringBuffer.append('x');
        break label130;
      }
      localStringBuffer.append('-');
      break label130;
      label310: localStringBuffer.append('-');
      break label146;
      label320: localStringBuffer.append('-');
      break label162;
      label330: localStringBuffer.append('-');
    }
  }

  public long getSize()
  {
    return this.size;
  }

  public int getUId()
  {
    return this.uid;
  }

  public boolean isBlk()
  {
    return isType(24576);
  }

  public boolean isChr()
  {
    return isType(8192);
  }

  public boolean isDir()
  {
    return isType(16384);
  }

  public boolean isFifo()
  {
    return isType(4096);
  }

  public boolean isLink()
  {
    return isType(40960);
  }

  public boolean isReg()
  {
    return isType(32768);
  }

  public boolean isSock()
  {
    return isType(49152);
  }

  int length()
  {
    int i = 4;
    if ((0x1 & this.flags) != 0)
      i += 8;
    if ((0x2 & this.flags) != 0)
      i += 8;
    if ((0x4 & this.flags) != 0)
      i += 4;
    if ((0x8 & this.flags) != 0)
      i += 8;
    if ((0x80000000 & this.flags) != 0)
    {
      i += 4;
      int j = this.extended.length / 2;
      if (j > 0)
        for (int k = 0; k < j; k++)
          i = 4 + (i + 4 + this.extended[(k * 2)].length()) + this.extended[(1 + k * 2)].length();
    }
    return i;
  }

  public void setACMODTIME(int paramInt1, int paramInt2)
  {
    this.flags = (0x8 | this.flags);
    this.atime = paramInt1;
    this.mtime = paramInt2;
  }

  void setFLAGS(int paramInt)
  {
    this.flags = paramInt;
  }

  public void setPERMISSIONS(int paramInt)
  {
    this.flags = (0x4 | this.flags);
    this.permissions = (0xFFFFF000 & this.permissions | paramInt & 0xFFF);
  }

  public void setSIZE(long paramLong)
  {
    this.flags = (0x1 | this.flags);
    this.size = paramLong;
  }

  public void setUIDGID(int paramInt1, int paramInt2)
  {
    this.flags = (0x2 | this.flags);
    this.uid = paramInt1;
    this.gid = paramInt2;
  }

  public String toString()
  {
    return getPermissionsString() + " " + getUId() + " " + getGId() + " " + getSize() + " " + getMtimeString();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.SftpATTRS
 * JD-Core Version:    0.6.2
 */