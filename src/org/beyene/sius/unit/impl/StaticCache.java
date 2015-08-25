package org.beyene.sius.unit.impl;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;

public class StaticCache<D extends Dimension<D>, BASE extends Unit<D, BASE, BASE>, SELF extends Unit<D, BASE, SELF>>
{
  public final SELF[] cache;
  public final int high;
  public final int low;

  // ERROR //
  public StaticCache(int paramInt1, int paramInt2, java.lang.Class<? extends Unit<D, BASE, SELF>> paramClass)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 28	java/lang/Object:<init>	()V
    //   4: aload_0
    //   5: iload_1
    //   6: putfield 30	org/beyene/sius/unit/impl/StaticCache:low	I
    //   9: aload_0
    //   10: iload_2
    //   11: iconst_1
    //   12: isub
    //   13: invokestatic 36	java/lang/Math:abs	(I)I
    //   16: putfield 38	org/beyene/sius/unit/impl/StaticCache:high	I
    //   19: aload_0
    //   20: aload_3
    //   21: iconst_1
    //   22: aload_0
    //   23: getfield 38	org/beyene/sius/unit/impl/StaticCache:high	I
    //   26: iload_1
    //   27: isub
    //   28: iadd
    //   29: invokestatic 44	java/lang/reflect/Array:newInstance	(Ljava/lang/Class;I)Ljava/lang/Object;
    //   32: checkcast 45	[Lorg/beyene/sius/unit/Unit;
    //   35: checkcast 45	[Lorg/beyene/sius/unit/Unit;
    //   38: putfield 47	org/beyene/sius/unit/impl/StaticCache:cache	[Lorg/beyene/sius/unit/Unit;
    //   41: iload_1
    //   42: istore 4
    //   44: iconst_0
    //   45: istore 5
    //   47: iload 5
    //   49: aload_0
    //   50: getfield 47	org/beyene/sius/unit/impl/StaticCache:cache	[Lorg/beyene/sius/unit/Unit;
    //   53: arraylength
    //   54: if_icmpge +148 -> 202
    //   57: aload_0
    //   58: getfield 47	org/beyene/sius/unit/impl/StaticCache:cache	[Lorg/beyene/sius/unit/Unit;
    //   61: astore 12
    //   63: iconst_1
    //   64: anewarray 49	java/lang/Class
    //   67: astore 13
    //   69: aload 13
    //   71: iconst_0
    //   72: getstatic 55	java/lang/Double:TYPE	Ljava/lang/Class;
    //   75: aastore
    //   76: aload_3
    //   77: aload 13
    //   79: invokevirtual 59	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   82: astore 14
    //   84: iconst_1
    //   85: anewarray 5	java/lang/Object
    //   88: astore 15
    //   90: iload 4
    //   92: iconst_1
    //   93: iadd
    //   94: istore 16
    //   96: aload 15
    //   98: iconst_0
    //   99: iload 4
    //   101: invokestatic 65	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   104: aastore
    //   105: aload 12
    //   107: iload 5
    //   109: aload 14
    //   111: aload 15
    //   113: invokevirtual 70	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   116: checkcast 72	org/beyene/sius/unit/Unit
    //   119: aastore
    //   120: iinc 5 1
    //   123: iload 16
    //   125: istore 4
    //   127: goto -80 -> 47
    //   130: astore 11
    //   132: new 74	java/lang/IllegalStateException
    //   135: dup
    //   136: aload 11
    //   138: invokespecial 77	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
    //   141: athrow
    //   142: astore 10
    //   144: new 74	java/lang/IllegalStateException
    //   147: dup
    //   148: aload 10
    //   150: invokespecial 77	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
    //   153: athrow
    //   154: astore 9
    //   156: new 74	java/lang/IllegalStateException
    //   159: dup
    //   160: aload 9
    //   162: invokespecial 77	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
    //   165: athrow
    //   166: astore 8
    //   168: new 74	java/lang/IllegalStateException
    //   171: dup
    //   172: aload 8
    //   174: invokespecial 77	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
    //   177: athrow
    //   178: astore 7
    //   180: new 74	java/lang/IllegalStateException
    //   183: dup
    //   184: aload 7
    //   186: invokespecial 77	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
    //   189: athrow
    //   190: astore 6
    //   192: new 74	java/lang/IllegalStateException
    //   195: dup
    //   196: aload 6
    //   198: invokespecial 77	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
    //   201: athrow
    //   202: return
    //   203: astore 6
    //   205: goto -13 -> 192
    //   208: astore 7
    //   210: goto -30 -> 180
    //   213: astore 8
    //   215: goto -47 -> 168
    //   218: astore 9
    //   220: goto -64 -> 156
    //   223: astore 10
    //   225: goto -81 -> 144
    //   228: astore 11
    //   230: goto -98 -> 132
    //
    // Exception table:
    //   from	to	target	type
    //   57	90	130	java/lang/InstantiationException
    //   57	90	142	java/lang/IllegalAccessException
    //   57	90	154	java/lang/IllegalArgumentException
    //   57	90	166	java/lang/reflect/InvocationTargetException
    //   57	90	178	java/lang/NoSuchMethodException
    //   57	90	190	java/lang/SecurityException
    //   96	120	203	java/lang/SecurityException
    //   96	120	208	java/lang/NoSuchMethodException
    //   96	120	213	java/lang/reflect/InvocationTargetException
    //   96	120	218	java/lang/IllegalArgumentException
    //   96	120	223	java/lang/IllegalAccessException
    //   96	120	228	java/lang/InstantiationException
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.StaticCache
 * JD-Core Version:    0.6.2
 */