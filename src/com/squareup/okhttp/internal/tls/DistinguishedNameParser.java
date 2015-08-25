package com.squareup.okhttp.internal.tls;

import javax.security.auth.x500.X500Principal;

final class DistinguishedNameParser
{
  private int beg;
  private char[] chars;
  private int cur;
  private final String dn;
  private int end;
  private final int length;
  private int pos;

  public DistinguishedNameParser(X500Principal paramX500Principal)
  {
    this.dn = paramX500Principal.getName("RFC2253");
    this.length = this.dn.length();
  }

  private String escapedAV()
  {
    this.beg = this.pos;
    this.end = this.pos;
    do
    {
      while (true)
      {
        if (this.pos >= this.length)
          return new String(this.chars, this.beg, this.end - this.beg);
        switch (this.chars[this.pos])
        {
        default:
          char[] arrayOfChar4 = this.chars;
          int m = this.end;
          this.end = (m + 1);
          arrayOfChar4[m] = this.chars[this.pos];
          this.pos = (1 + this.pos);
          break;
        case '+':
        case ',':
        case ';':
          return new String(this.chars, this.beg, this.end - this.beg);
        case '\\':
          char[] arrayOfChar3 = this.chars;
          int k = this.end;
          this.end = (k + 1);
          arrayOfChar3[k] = getEscaped();
          this.pos = (1 + this.pos);
        case ' ':
        }
      }
      this.cur = this.end;
      this.pos = (1 + this.pos);
      char[] arrayOfChar1 = this.chars;
      int i = this.end;
      this.end = (i + 1);
      arrayOfChar1[i] = ' ';
      while ((this.pos < this.length) && (this.chars[this.pos] == ' '))
      {
        char[] arrayOfChar2 = this.chars;
        int j = this.end;
        this.end = (j + 1);
        arrayOfChar2[j] = ' ';
        this.pos = (1 + this.pos);
      }
    }
    while ((this.pos != this.length) && (this.chars[this.pos] != ',') && (this.chars[this.pos] != '+') && (this.chars[this.pos] != ';'));
    return new String(this.chars, this.beg, this.cur - this.beg);
  }

  private int getByte(int paramInt)
  {
    if (paramInt + 1 >= this.length)
      throw new IllegalStateException("Malformed DN: " + this.dn);
    int i = this.chars[paramInt];
    int j;
    int k;
    int m;
    if ((i >= 48) && (i <= 57))
    {
      j = i - 48;
      k = this.chars[(paramInt + 1)];
      if ((k < 48) || (k > 57))
        break label172;
      m = k - 48;
    }
    while (true)
    {
      return m + (j << 4);
      if ((i >= 97) && (i <= 102))
      {
        j = i - 87;
        break;
      }
      if ((i >= 65) && (i <= 70))
      {
        j = i - 55;
        break;
      }
      throw new IllegalStateException("Malformed DN: " + this.dn);
      label172: if ((k >= 97) && (k <= 102))
      {
        m = k - 87;
      }
      else
      {
        if ((k < 65) || (k > 70))
          break label220;
        m = k - 55;
      }
    }
    label220: throw new IllegalStateException("Malformed DN: " + this.dn);
  }

  private char getEscaped()
  {
    this.pos = (1 + this.pos);
    if (this.pos == this.length)
      throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    switch (this.chars[this.pos])
    {
    default:
      return getUTF8();
    case ' ':
    case '"':
    case '#':
    case '%':
    case '*':
    case '+':
    case ',':
    case ';':
    case '<':
    case '=':
    case '>':
    case '\\':
    case '_':
    }
    return this.chars[this.pos];
  }

  private char getUTF8()
  {
    char c = '?';
    int i = getByte(this.pos);
    this.pos = (1 + this.pos);
    if (i < 128)
      c = (char)i;
    while ((i < 192) || (i > 247))
      return c;
    int j;
    int k;
    if (i <= 223)
    {
      j = 1;
      k = i & 0x1F;
    }
    while (true)
    {
      for (int m = 0; ; m++)
      {
        if (m >= j)
          break label197;
        this.pos = (1 + this.pos);
        if ((this.pos == this.length) || (this.chars[this.pos] != '\\'))
          break;
        this.pos = (1 + this.pos);
        int n = getByte(this.pos);
        this.pos = (1 + this.pos);
        if ((n & 0xC0) != 128)
          break;
        k = (k << 6) + (n & 0x3F);
      }
      if (i <= 239)
      {
        j = 2;
        k = i & 0xF;
      }
      else
      {
        j = 3;
        k = i & 0x7;
      }
    }
    label197: return (char)k;
  }

  private String hexAV()
  {
    if (4 + this.pos >= this.length)
      throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    this.beg = this.pos;
    int i;
    for (this.pos = (1 + this.pos); ; this.pos = (1 + this.pos))
    {
      if ((this.pos == this.length) || (this.chars[this.pos] == '+') || (this.chars[this.pos] == ',') || (this.chars[this.pos] == ';'))
        this.end = this.pos;
      while (true)
      {
        i = this.end - this.beg;
        if ((i >= 5) && ((i & 0x1) != 0))
          break label310;
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        if (this.chars[this.pos] != ' ')
          break;
        this.end = this.pos;
        for (this.pos = (1 + this.pos); (this.pos < this.length) && (this.chars[this.pos] == ' '); this.pos = (1 + this.pos));
      }
      if ((this.chars[this.pos] >= 'A') && (this.chars[this.pos] <= 'F'))
      {
        char[] arrayOfChar = this.chars;
        int m = this.pos;
        arrayOfChar[m] = ((char)(' ' + arrayOfChar[m]));
      }
    }
    label310: byte[] arrayOfByte = new byte[i / 2];
    int j = 0;
    int k = 1 + this.beg;
    while (j < arrayOfByte.length)
    {
      arrayOfByte[j] = ((byte)getByte(k));
      k += 2;
      j++;
    }
    return new String(this.chars, this.beg, i);
  }

  private String nextAT()
  {
    while ((this.pos < this.length) && (this.chars[this.pos] == ' '))
      this.pos = (1 + this.pos);
    if (this.pos == this.length)
      return null;
    this.beg = this.pos;
    for (this.pos = (1 + this.pos); (this.pos < this.length) && (this.chars[this.pos] != '=') && (this.chars[this.pos] != ' '); this.pos = (1 + this.pos));
    if (this.pos >= this.length)
      throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    this.end = this.pos;
    if (this.chars[this.pos] == ' ')
    {
      while ((this.pos < this.length) && (this.chars[this.pos] != '=') && (this.chars[this.pos] == ' '))
        this.pos = (1 + this.pos);
      if ((this.chars[this.pos] != '=') || (this.pos == this.length))
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }
    for (this.pos = (1 + this.pos); (this.pos < this.length) && (this.chars[this.pos] == ' '); this.pos = (1 + this.pos));
    if ((this.end - this.beg > 4) && (this.chars[(3 + this.beg)] == '.') && ((this.chars[this.beg] == 'O') || (this.chars[this.beg] == 'o')) && ((this.chars[(1 + this.beg)] == 'I') || (this.chars[(1 + this.beg)] == 'i')) && ((this.chars[(2 + this.beg)] == 'D') || (this.chars[(2 + this.beg)] == 'd')))
      this.beg = (4 + this.beg);
    return new String(this.chars, this.beg, this.end - this.beg);
  }

  private String quotedAV()
  {
    this.pos = (1 + this.pos);
    this.beg = this.pos;
    this.end = this.beg;
    if (this.pos == this.length)
      throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    if (this.chars[this.pos] == '"')
      for (this.pos = (1 + this.pos); (this.pos < this.length) && (this.chars[this.pos] == ' '); this.pos = (1 + this.pos));
    if (this.chars[this.pos] == '\\')
      this.chars[this.end] = getEscaped();
    while (true)
    {
      this.pos = (1 + this.pos);
      this.end = (1 + this.end);
      break;
      this.chars[this.end] = this.chars[this.pos];
    }
    return new String(this.chars, this.beg, this.end - this.beg);
  }

  public String findMostSpecific(String paramString)
  {
    this.pos = 0;
    this.beg = 0;
    this.end = 0;
    this.cur = 0;
    this.chars = this.dn.toCharArray();
    String str1 = nextAT();
    if (str1 == null)
    {
      str2 = null;
      return str2;
    }
    String str2 = "";
    if (this.pos == this.length)
      return null;
    switch (this.chars[this.pos])
    {
    default:
      str2 = escapedAV();
    case '+':
    case ',':
    case ';':
    case '"':
    case '#':
    }
    while (!paramString.equalsIgnoreCase(str1))
    {
      if (this.pos < this.length)
        break label162;
      return null;
      str2 = quotedAV();
      continue;
      str2 = hexAV();
    }
    label162: if ((this.chars[this.pos] == ',') || (this.chars[this.pos] == ';'));
    while (this.chars[this.pos] == '+')
    {
      this.pos = (1 + this.pos);
      str1 = nextAT();
      if (str1 != null)
        break;
      throw new IllegalStateException("Malformed DN: " + this.dn);
    }
    throw new IllegalStateException("Malformed DN: " + this.dn);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.tls.DistinguishedNameParser
 * JD-Core Version:    0.6.2
 */