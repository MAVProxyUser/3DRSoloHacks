package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public abstract interface zzoc
{
  public static final class zza extends zzns<zza>
  {
    public String[] zzaOd;
    public String[] zzaOe;
    public int[] zzaOf;

    public zza()
    {
      zzAd();
    }

    public boolean equals(Object paramObject)
    {
      boolean bool2;
      if (paramObject == this)
        bool2 = true;
      zza localzza;
      boolean bool5;
      do
      {
        boolean bool4;
        do
        {
          boolean bool3;
          do
          {
            boolean bool1;
            do
            {
              return bool2;
              bool1 = paramObject instanceof zza;
              bool2 = false;
            }
            while (!bool1);
            localzza = (zza)paramObject;
            bool3 = zznw.equals(this.zzaOd, localzza.zzaOd);
            bool2 = false;
          }
          while (!bool3);
          bool4 = zznw.equals(this.zzaOe, localzza.zzaOe);
          bool2 = false;
        }
        while (!bool4);
        bool5 = zznw.equals(this.zzaOf, localzza.zzaOf);
        bool2 = false;
      }
      while (!bool5);
      return zza(localzza);
    }

    public int hashCode()
    {
      return 31 * (31 * (31 * (527 + zznw.hashCode(this.zzaOd)) + zznw.hashCode(this.zzaOe)) + zznw.hashCode(this.zzaOf)) + zzzP();
    }

    public zza zzAd()
    {
      this.zzaOd = zzob.zzaOa;
      this.zzaOe = zzob.zzaOa;
      this.zzaOf = zzob.zzaNV;
      this.zzaNI = null;
      this.zzaNT = -1;
      return this;
    }

    public zza zzB(zznq paramzznq)
      throws IOException
    {
      while (true)
      {
        int i = paramzznq.zzzy();
        switch (i)
        {
        default:
          if (zza(paramzznq, i))
            continue;
        case 0:
          return this;
        case 10:
          int i5 = zzob.zzb(paramzznq, 10);
          if (this.zzaOd == null);
          String[] arrayOfString2;
          for (int i6 = 0; ; i6 = this.zzaOd.length)
          {
            arrayOfString2 = new String[i5 + i6];
            if (i6 != 0)
              System.arraycopy(this.zzaOd, 0, arrayOfString2, 0, i6);
            while (i6 < -1 + arrayOfString2.length)
            {
              arrayOfString2[i6] = paramzznq.readString();
              paramzznq.zzzy();
              i6++;
            }
          }
          arrayOfString2[i6] = paramzznq.readString();
          this.zzaOd = arrayOfString2;
          break;
        case 18:
          int i3 = zzob.zzb(paramzznq, 18);
          if (this.zzaOe == null);
          String[] arrayOfString1;
          for (int i4 = 0; ; i4 = this.zzaOe.length)
          {
            arrayOfString1 = new String[i3 + i4];
            if (i4 != 0)
              System.arraycopy(this.zzaOe, 0, arrayOfString1, 0, i4);
            while (i4 < -1 + arrayOfString1.length)
            {
              arrayOfString1[i4] = paramzznq.readString();
              paramzznq.zzzy();
              i4++;
            }
          }
          arrayOfString1[i4] = paramzznq.readString();
          this.zzaOe = arrayOfString1;
          break;
        case 24:
          int i1 = zzob.zzb(paramzznq, 24);
          if (this.zzaOf == null);
          int[] arrayOfInt2;
          for (int i2 = 0; ; i2 = this.zzaOf.length)
          {
            arrayOfInt2 = new int[i1 + i2];
            if (i2 != 0)
              System.arraycopy(this.zzaOf, 0, arrayOfInt2, 0, i2);
            while (i2 < -1 + arrayOfInt2.length)
            {
              arrayOfInt2[i2] = paramzznq.zzzB();
              paramzznq.zzzy();
              i2++;
            }
          }
          arrayOfInt2[i2] = paramzznq.zzzB();
          this.zzaOf = arrayOfInt2;
          break;
        case 26:
        }
        int j = paramzznq.zzjn(paramzznq.zzzF());
        int k = paramzznq.getPosition();
        for (int m = 0; paramzznq.zzzK() > 0; m++)
          paramzznq.zzzB();
        paramzznq.zzjp(k);
        if (this.zzaOf == null);
        int[] arrayOfInt1;
        for (int n = 0; ; n = this.zzaOf.length)
        {
          arrayOfInt1 = new int[m + n];
          if (n != 0)
            System.arraycopy(this.zzaOf, 0, arrayOfInt1, 0, n);
          while (n < arrayOfInt1.length)
          {
            arrayOfInt1[n] = paramzznq.zzzB();
            n++;
          }
        }
        this.zzaOf = arrayOfInt1;
        paramzznq.zzjo(j);
      }
    }

    public void zza(zznr paramzznr)
      throws IOException
    {
      if ((this.zzaOd != null) && (this.zzaOd.length > 0))
        for (int m = 0; m < this.zzaOd.length; m++)
        {
          String str2 = this.zzaOd[m];
          if (str2 != null)
            paramzznr.zzb(1, str2);
        }
      if ((this.zzaOe != null) && (this.zzaOe.length > 0))
        for (int k = 0; k < this.zzaOe.length; k++)
        {
          String str1 = this.zzaOe[k];
          if (str1 != null)
            paramzznr.zzb(2, str1);
        }
      if (this.zzaOf != null)
      {
        int i = this.zzaOf.length;
        int j = 0;
        if (i > 0)
          while (j < this.zzaOf.length)
          {
            paramzznr.zzx(3, this.zzaOf[j]);
            j++;
          }
      }
      super.zza(paramzznr);
    }

    protected int zzc()
    {
      int i = 0;
      int j = super.zzc();
      int i4;
      int i5;
      if ((this.zzaOd != null) && (this.zzaOd.length > 0))
      {
        int i3 = 0;
        i4 = 0;
        i5 = 0;
        while (i3 < this.zzaOd.length)
        {
          String str2 = this.zzaOd[i3];
          if (str2 != null)
          {
            i5++;
            i4 += zznr.zzeB(str2);
          }
          i3++;
        }
      }
      for (int k = j + i4 + i5 * 1; ; k = j)
      {
        if ((this.zzaOe != null) && (this.zzaOe.length > 0))
        {
          int n = 0;
          int i1 = 0;
          int i2 = 0;
          while (n < this.zzaOe.length)
          {
            String str1 = this.zzaOe[n];
            if (str1 != null)
            {
              i2++;
              i1 += zznr.zzeB(str1);
            }
            n++;
          }
          k = k + i1 + i2 * 1;
        }
        if ((this.zzaOf != null) && (this.zzaOf.length > 0))
        {
          int m = 0;
          while (i < this.zzaOf.length)
          {
            m += zznr.zzju(this.zzaOf[i]);
            i++;
          }
          k = k + m + 1 * this.zzaOf.length;
        }
        return k;
      }
    }
  }

  public static final class zzb extends zzns<zzb>
  {
    public String version;
    public int zzaOg;
    public String zzaOh;

    public zzb()
    {
      zzAe();
    }

    public boolean equals(Object paramObject)
    {
      boolean bool2;
      if (paramObject == this)
        bool2 = true;
      zzb localzzb;
      do
      {
        String str2;
        do
        {
          int i;
          int j;
          do
          {
            boolean bool1;
            do
            {
              return bool2;
              bool1 = paramObject instanceof zzb;
              bool2 = false;
            }
            while (!bool1);
            localzzb = (zzb)paramObject;
            i = this.zzaOg;
            j = localzzb.zzaOg;
            bool2 = false;
          }
          while (i != j);
          if (this.zzaOh != null)
            break;
          str2 = localzzb.zzaOh;
          bool2 = false;
        }
        while (str2 != null);
        if (this.version != null)
          break label114;
        str1 = localzzb.version;
        bool2 = false;
      }
      while (str1 != null);
      label114: 
      while (this.version.equals(localzzb.version))
      {
        String str1;
        return zza(localzzb);
        if (this.zzaOh.equals(localzzb.zzaOh))
          break;
        return false;
      }
      return false;
    }

    public int hashCode()
    {
      int i = 31 * (527 + this.zzaOg);
      int j;
      int k;
      int m;
      if (this.zzaOh == null)
      {
        j = 0;
        k = 31 * (j + i);
        String str = this.version;
        m = 0;
        if (str != null)
          break label66;
      }
      while (true)
      {
        return 31 * (k + m) + zzzP();
        j = this.zzaOh.hashCode();
        break;
        label66: m = this.version.hashCode();
      }
    }

    public zzb zzAe()
    {
      this.zzaOg = 0;
      this.zzaOh = "";
      this.version = "";
      this.zzaNI = null;
      this.zzaNT = -1;
      return this;
    }

    public zzb zzC(zznq paramzznq)
      throws IOException
    {
      while (true)
      {
        int i = paramzznq.zzzy();
        switch (i)
        {
        default:
          if (zza(paramzznq, i))
            continue;
        case 0:
          return this;
        case 8:
          int j = paramzznq.zzzB();
          switch (j)
          {
          default:
            break;
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
          case 11:
          case 12:
          case 13:
          case 14:
          case 15:
          case 16:
          case 17:
          case 18:
          case 19:
          case 20:
          case 21:
          case 22:
          case 23:
          case 24:
          case 25:
          }
          this.zzaOg = j;
          break;
        case 18:
          this.zzaOh = paramzznq.readString();
          break;
        case 26:
        }
        this.version = paramzznq.readString();
      }
    }

    public void zza(zznr paramzznr)
      throws IOException
    {
      if (this.zzaOg != 0)
        paramzznr.zzx(1, this.zzaOg);
      if (!this.zzaOh.equals(""))
        paramzznr.zzb(2, this.zzaOh);
      if (!this.version.equals(""))
        paramzznr.zzb(3, this.version);
      super.zza(paramzznr);
    }

    protected int zzc()
    {
      int i = super.zzc();
      if (this.zzaOg != 0)
        i += zznr.zzz(1, this.zzaOg);
      if (!this.zzaOh.equals(""))
        i += zznr.zzj(2, this.zzaOh);
      if (!this.version.equals(""))
        i += zznr.zzj(3, this.version);
      return i;
    }
  }

  public static final class zzc extends zzns<zzc>
  {
    public byte[] zzaOi;
    public byte[][] zzaOj;
    public boolean zzaOk;

    public zzc()
    {
      zzAf();
    }

    public boolean equals(Object paramObject)
    {
      boolean bool2;
      if (paramObject == this)
        bool2 = true;
      zzc localzzc;
      boolean bool5;
      boolean bool6;
      do
      {
        boolean bool4;
        do
        {
          boolean bool3;
          do
          {
            boolean bool1;
            do
            {
              return bool2;
              bool1 = paramObject instanceof zzc;
              bool2 = false;
            }
            while (!bool1);
            localzzc = (zzc)paramObject;
            bool3 = Arrays.equals(this.zzaOi, localzzc.zzaOi);
            bool2 = false;
          }
          while (!bool3);
          bool4 = zznw.zza(this.zzaOj, localzzc.zzaOj);
          bool2 = false;
        }
        while (!bool4);
        bool5 = this.zzaOk;
        bool6 = localzzc.zzaOk;
        bool2 = false;
      }
      while (bool5 != bool6);
      return zza(localzzc);
    }

    public int hashCode()
    {
      int i = 31 * (31 * (527 + Arrays.hashCode(this.zzaOi)) + zznw.zza(this.zzaOj));
      if (this.zzaOk);
      for (int j = 1231; ; j = 1237)
        return 31 * (j + i) + zzzP();
    }

    public zzc zzAf()
    {
      this.zzaOi = zzob.zzaOc;
      this.zzaOj = zzob.zzaOb;
      this.zzaOk = false;
      this.zzaNI = null;
      this.zzaNT = -1;
      return this;
    }

    public zzc zzD(zznq paramzznq)
      throws IOException
    {
      while (true)
      {
        int i = paramzznq.zzzy();
        switch (i)
        {
        default:
          if (zza(paramzznq, i))
            continue;
        case 0:
          return this;
        case 10:
          this.zzaOi = paramzznq.readBytes();
          break;
        case 18:
          int j = zzob.zzb(paramzznq, 18);
          if (this.zzaOj == null);
          byte[][] arrayOfByte;
          for (int k = 0; ; k = this.zzaOj.length)
          {
            arrayOfByte = new byte[j + k][];
            if (k != 0)
              System.arraycopy(this.zzaOj, 0, arrayOfByte, 0, k);
            while (k < -1 + arrayOfByte.length)
            {
              arrayOfByte[k] = paramzznq.readBytes();
              paramzznq.zzzy();
              k++;
            }
          }
          arrayOfByte[k] = paramzznq.readBytes();
          this.zzaOj = arrayOfByte;
          break;
        case 24:
        }
        this.zzaOk = paramzznq.zzzC();
      }
    }

    public void zza(zznr paramzznr)
      throws IOException
    {
      if (!Arrays.equals(this.zzaOi, zzob.zzaOc))
        paramzznr.zza(1, this.zzaOi);
      if ((this.zzaOj != null) && (this.zzaOj.length > 0))
        for (int i = 0; i < this.zzaOj.length; i++)
        {
          byte[] arrayOfByte = this.zzaOj[i];
          if (arrayOfByte != null)
            paramzznr.zza(2, arrayOfByte);
        }
      if (this.zzaOk)
        paramzznr.zzb(3, this.zzaOk);
      super.zza(paramzznr);
    }

    protected int zzc()
    {
      int i = 0;
      int j = super.zzc();
      if (!Arrays.equals(this.zzaOi, zzob.zzaOc))
        j += zznr.zzb(1, this.zzaOi);
      if ((this.zzaOj != null) && (this.zzaOj.length > 0))
      {
        int k = 0;
        int m = 0;
        while (i < this.zzaOj.length)
        {
          byte[] arrayOfByte = this.zzaOj[i];
          if (arrayOfByte != null)
          {
            m++;
            k += zznr.zzy(arrayOfByte);
          }
          i++;
        }
        j = j + k + m * 1;
      }
      if (this.zzaOk)
        j += zznr.zzc(3, this.zzaOk);
      return j;
    }
  }

  public static final class zzd extends zzns<zzd>
  {
    public String tag;
    public long zzaOl;
    public long zzaOm;
    public int zzaOn;
    public int zzaOo;
    public boolean zzaOp;
    public zzoc.zze[] zzaOq;
    public zzoc.zzb zzaOr;
    public byte[] zzaOs;
    public byte[] zzaOt;
    public byte[] zzaOu;
    public zzoc.zza zzaOv;
    public String zzaOw;
    public long zzaOx;
    public zzoc.zzc zzaOy;

    public zzd()
    {
      zzAg();
    }

    public boolean equals(Object paramObject)
    {
      boolean bool2;
      if (paramObject == this)
        bool2 = true;
      zzd localzzd;
      label193: 
      do
      {
        boolean bool11;
        do
        {
          String str1;
          do
          {
            zzoc.zza localzza;
            do
            {
              boolean bool10;
              do
              {
                boolean bool9;
                do
                {
                  boolean bool8;
                  do
                  {
                    zzoc.zzb localzzb;
                    do
                    {
                      boolean bool7;
                      do
                      {
                        boolean bool5;
                        boolean bool6;
                        do
                        {
                          int k;
                          int m;
                          do
                          {
                            int i;
                            int j;
                            do
                            {
                              String str2;
                              do
                              {
                                boolean bool4;
                                do
                                {
                                  boolean bool3;
                                  do
                                  {
                                    boolean bool1;
                                    do
                                    {
                                      return bool2;
                                      bool1 = paramObject instanceof zzd;
                                      bool2 = false;
                                    }
                                    while (!bool1);
                                    localzzd = (zzd)paramObject;
                                    bool3 = this.zzaOl < localzzd.zzaOl;
                                    bool2 = false;
                                  }
                                  while (bool3);
                                  bool4 = this.zzaOm < localzzd.zzaOm;
                                  bool2 = false;
                                }
                                while (bool4);
                                if (this.tag != null)
                                  break;
                                str2 = localzzd.tag;
                                bool2 = false;
                              }
                              while (str2 != null);
                              i = this.zzaOn;
                              j = localzzd.zzaOn;
                              bool2 = false;
                            }
                            while (i != j);
                            k = this.zzaOo;
                            m = localzzd.zzaOo;
                            bool2 = false;
                          }
                          while (k != m);
                          bool5 = this.zzaOp;
                          bool6 = localzzd.zzaOp;
                          bool2 = false;
                        }
                        while (bool5 != bool6);
                        bool7 = zznw.equals(this.zzaOq, localzzd.zzaOq);
                        bool2 = false;
                      }
                      while (!bool7);
                      if (this.zzaOr != null)
                        break label362;
                      localzzb = localzzd.zzaOr;
                      bool2 = false;
                    }
                    while (localzzb != null);
                    bool8 = Arrays.equals(this.zzaOs, localzzd.zzaOs);
                    bool2 = false;
                  }
                  while (!bool8);
                  bool9 = Arrays.equals(this.zzaOt, localzzd.zzaOt);
                  bool2 = false;
                }
                while (!bool9);
                bool10 = Arrays.equals(this.zzaOu, localzzd.zzaOu);
                bool2 = false;
              }
              while (!bool10);
              if (this.zzaOv != null)
                break label379;
              localzza = localzzd.zzaOv;
              bool2 = false;
            }
            while (localzza != null);
            if (this.zzaOw != null)
              break label396;
            str1 = localzzd.zzaOw;
            bool2 = false;
          }
          while (str1 != null);
          bool11 = this.zzaOx < localzzd.zzaOx;
          bool2 = false;
        }
        while (bool11);
        if (this.zzaOy != null)
          break label413;
        localzzc = localzzd.zzaOy;
        bool2 = false;
      }
      while (localzzc != null);
      label277: label298: label362: label379: 
      while (this.zzaOy.equals(localzzd.zzaOy))
      {
        zzoc.zzc localzzc;
        return zza(localzzd);
        if (this.tag.equals(localzzd.tag))
          break;
        return false;
        if (this.zzaOr.equals(localzzd.zzaOr))
          break label193;
        return false;
        if (this.zzaOv.equals(localzzd.zzaOv))
          break label277;
        return false;
        if (this.zzaOw.equals(localzzd.zzaOw))
          break label298;
        return false;
      }
      label396: label413: return false;
    }

    public int hashCode()
    {
      int i = 31 * (31 * (527 + (int)(this.zzaOl ^ this.zzaOl >>> 32)) + (int)(this.zzaOm ^ this.zzaOm >>> 32));
      int j;
      int m;
      label82: int i1;
      label112: int i3;
      label165: int i5;
      label185: int i6;
      int i7;
      if (this.tag == null)
      {
        j = 0;
        int k = 31 * (31 * (31 * (j + i) + this.zzaOn) + this.zzaOo);
        if (!this.zzaOp)
          break label251;
        m = 1231;
        int n = 31 * (31 * (m + k) + zznw.hashCode(this.zzaOq));
        if (this.zzaOr != null)
          break label259;
        i1 = 0;
        int i2 = 31 * (31 * (31 * (31 * (i1 + n) + Arrays.hashCode(this.zzaOs)) + Arrays.hashCode(this.zzaOt)) + Arrays.hashCode(this.zzaOu));
        if (this.zzaOv != null)
          break label271;
        i3 = 0;
        int i4 = 31 * (i3 + i2);
        if (this.zzaOw != null)
          break label283;
        i5 = 0;
        i6 = 31 * (31 * (i5 + i4) + (int)(this.zzaOx ^ this.zzaOx >>> 32));
        zzoc.zzc localzzc = this.zzaOy;
        i7 = 0;
        if (localzzc != null)
          break label295;
      }
      while (true)
      {
        return 31 * (i6 + i7) + zzzP();
        j = this.tag.hashCode();
        break;
        label251: m = 1237;
        break label82;
        label259: i1 = this.zzaOr.hashCode();
        break label112;
        label271: i3 = this.zzaOv.hashCode();
        break label165;
        label283: i5 = this.zzaOw.hashCode();
        break label185;
        label295: i7 = this.zzaOy.hashCode();
      }
    }

    public zzd zzAg()
    {
      this.zzaOl = 0L;
      this.zzaOm = 0L;
      this.tag = "";
      this.zzaOn = 0;
      this.zzaOo = 0;
      this.zzaOp = false;
      this.zzaOq = zzoc.zze.zzAh();
      this.zzaOr = null;
      this.zzaOs = zzob.zzaOc;
      this.zzaOt = zzob.zzaOc;
      this.zzaOu = zzob.zzaOc;
      this.zzaOv = null;
      this.zzaOw = "";
      this.zzaOx = 180000L;
      this.zzaOy = null;
      this.zzaNI = null;
      this.zzaNT = -1;
      return this;
    }

    public zzd zzE(zznq paramzznq)
      throws IOException
    {
      while (true)
      {
        int i = paramzznq.zzzy();
        switch (i)
        {
        default:
          if (zza(paramzznq, i))
            continue;
        case 0:
          return this;
        case 8:
          this.zzaOl = paramzznq.zzzA();
          break;
        case 18:
          this.tag = paramzznq.readString();
          break;
        case 26:
          int j = zzob.zzb(paramzznq, 26);
          if (this.zzaOq == null);
          zzoc.zze[] arrayOfzze;
          for (int k = 0; ; k = this.zzaOq.length)
          {
            arrayOfzze = new zzoc.zze[j + k];
            if (k != 0)
              System.arraycopy(this.zzaOq, 0, arrayOfzze, 0, k);
            while (k < -1 + arrayOfzze.length)
            {
              arrayOfzze[k] = new zzoc.zze();
              paramzznq.zza(arrayOfzze[k]);
              paramzznq.zzzy();
              k++;
            }
          }
          arrayOfzze[k] = new zzoc.zze();
          paramzznq.zza(arrayOfzze[k]);
          this.zzaOq = arrayOfzze;
          break;
        case 50:
          this.zzaOs = paramzznq.readBytes();
          break;
        case 58:
          if (this.zzaOv == null)
            this.zzaOv = new zzoc.zza();
          paramzznq.zza(this.zzaOv);
          break;
        case 66:
          this.zzaOt = paramzznq.readBytes();
          break;
        case 74:
          if (this.zzaOr == null)
            this.zzaOr = new zzoc.zzb();
          paramzznq.zza(this.zzaOr);
          break;
        case 80:
          this.zzaOp = paramzznq.zzzC();
          break;
        case 88:
          this.zzaOn = paramzznq.zzzB();
          break;
        case 96:
          this.zzaOo = paramzznq.zzzB();
          break;
        case 106:
          this.zzaOu = paramzznq.readBytes();
          break;
        case 114:
          this.zzaOw = paramzznq.readString();
          break;
        case 120:
          this.zzaOx = paramzznq.zzzE();
          break;
        case 130:
          if (this.zzaOy == null)
            this.zzaOy = new zzoc.zzc();
          paramzznq.zza(this.zzaOy);
          break;
        case 136:
        }
        this.zzaOm = paramzznq.zzzA();
      }
    }

    public void zza(zznr paramzznr)
      throws IOException
    {
      if (this.zzaOl != 0L)
        paramzznr.zzb(1, this.zzaOl);
      if (!this.tag.equals(""))
        paramzznr.zzb(2, this.tag);
      if ((this.zzaOq != null) && (this.zzaOq.length > 0))
        for (int i = 0; i < this.zzaOq.length; i++)
        {
          zzoc.zze localzze = this.zzaOq[i];
          if (localzze != null)
            paramzznr.zza(3, localzze);
        }
      if (!Arrays.equals(this.zzaOs, zzob.zzaOc))
        paramzznr.zza(6, this.zzaOs);
      if (this.zzaOv != null)
        paramzznr.zza(7, this.zzaOv);
      if (!Arrays.equals(this.zzaOt, zzob.zzaOc))
        paramzznr.zza(8, this.zzaOt);
      if (this.zzaOr != null)
        paramzznr.zza(9, this.zzaOr);
      if (this.zzaOp)
        paramzznr.zzb(10, this.zzaOp);
      if (this.zzaOn != 0)
        paramzznr.zzx(11, this.zzaOn);
      if (this.zzaOo != 0)
        paramzznr.zzx(12, this.zzaOo);
      if (!Arrays.equals(this.zzaOu, zzob.zzaOc))
        paramzznr.zza(13, this.zzaOu);
      if (!this.zzaOw.equals(""))
        paramzznr.zzb(14, this.zzaOw);
      if (this.zzaOx != 180000L)
        paramzznr.zzc(15, this.zzaOx);
      if (this.zzaOy != null)
        paramzznr.zza(16, this.zzaOy);
      if (this.zzaOm != 0L)
        paramzznr.zzb(17, this.zzaOm);
      super.zza(paramzznr);
    }

    protected int zzc()
    {
      int i = super.zzc();
      if (this.zzaOl != 0L)
        i += zznr.zzd(1, this.zzaOl);
      if (!this.tag.equals(""))
        i += zznr.zzj(2, this.tag);
      if ((this.zzaOq != null) && (this.zzaOq.length > 0))
      {
        int j = i;
        for (int k = 0; k < this.zzaOq.length; k++)
        {
          zzoc.zze localzze = this.zzaOq[k];
          if (localzze != null)
            j += zznr.zzc(3, localzze);
        }
        i = j;
      }
      if (!Arrays.equals(this.zzaOs, zzob.zzaOc))
        i += zznr.zzb(6, this.zzaOs);
      if (this.zzaOv != null)
        i += zznr.zzc(7, this.zzaOv);
      if (!Arrays.equals(this.zzaOt, zzob.zzaOc))
        i += zznr.zzb(8, this.zzaOt);
      if (this.zzaOr != null)
        i += zznr.zzc(9, this.zzaOr);
      if (this.zzaOp)
        i += zznr.zzc(10, this.zzaOp);
      if (this.zzaOn != 0)
        i += zznr.zzz(11, this.zzaOn);
      if (this.zzaOo != 0)
        i += zznr.zzz(12, this.zzaOo);
      if (!Arrays.equals(this.zzaOu, zzob.zzaOc))
        i += zznr.zzb(13, this.zzaOu);
      if (!this.zzaOw.equals(""))
        i += zznr.zzj(14, this.zzaOw);
      if (this.zzaOx != 180000L)
        i += zznr.zze(15, this.zzaOx);
      if (this.zzaOy != null)
        i += zznr.zzc(16, this.zzaOy);
      if (this.zzaOm != 0L)
        i += zznr.zzd(17, this.zzaOm);
      return i;
    }
  }

  public static final class zze extends zzns<zze>
  {
    private static volatile zze[] zzaOz;
    public String value;
    public String zzgk;

    public zze()
    {
      zzAi();
    }

    public static zze[] zzAh()
    {
      if (zzaOz == null);
      synchronized (zznw.zzaNS)
      {
        if (zzaOz == null)
          zzaOz = new zze[0];
        return zzaOz;
      }
    }

    public boolean equals(Object paramObject)
    {
      boolean bool2;
      if (paramObject == this)
        bool2 = true;
      zze localzze;
      do
      {
        String str2;
        do
        {
          boolean bool1;
          do
          {
            return bool2;
            bool1 = paramObject instanceof zze;
            bool2 = false;
          }
          while (!bool1);
          localzze = (zze)paramObject;
          if (this.zzgk != null)
            break;
          str2 = localzze.zzgk;
          bool2 = false;
        }
        while (str2 != null);
        if (this.value != null)
          break label92;
        str1 = localzze.value;
        bool2 = false;
      }
      while (str1 != null);
      label92: 
      while (this.value.equals(localzze.value))
      {
        String str1;
        return zza(localzze);
        if (this.zzgk.equals(localzze.zzgk))
          break;
        return false;
      }
      return false;
    }

    public int hashCode()
    {
      int i;
      int j;
      int k;
      if (this.zzgk == null)
      {
        i = 0;
        j = 31 * (i + 527);
        String str = this.value;
        k = 0;
        if (str != null)
          break label54;
      }
      while (true)
      {
        return 31 * (j + k) + zzzP();
        i = this.zzgk.hashCode();
        break;
        label54: k = this.value.hashCode();
      }
    }

    public zze zzAi()
    {
      this.zzgk = "";
      this.value = "";
      this.zzaNI = null;
      this.zzaNT = -1;
      return this;
    }

    public zze zzF(zznq paramzznq)
      throws IOException
    {
      while (true)
      {
        int i = paramzznq.zzzy();
        switch (i)
        {
        default:
          if (zza(paramzznq, i))
            continue;
        case 0:
          return this;
        case 10:
          this.zzgk = paramzznq.readString();
          break;
        case 18:
        }
        this.value = paramzznq.readString();
      }
    }

    public void zza(zznr paramzznr)
      throws IOException
    {
      if (!this.zzgk.equals(""))
        paramzznr.zzb(1, this.zzgk);
      if (!this.value.equals(""))
        paramzznr.zzb(2, this.value);
      super.zza(paramzznr);
    }

    protected int zzc()
    {
      int i = super.zzc();
      if (!this.zzgk.equals(""))
        i += zznr.zzj(1, this.zzgk);
      if (!this.value.equals(""))
        i += zznr.zzj(2, this.value);
      return i;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzoc
 * JD-Core Version:    0.6.2
 */