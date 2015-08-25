package com.o3dr.services.android.lib.util;

import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class MathUtils
{
  private static final double RADIUS_OF_EARTH = 6378137.0D;
  public static final int SIGNAL_MAX_FADE_MARGIN = 50;
  public static final int SIGNAL_MIN_FADE_MARGIN = 6;

  private static double Constrain(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return Math.min(Math.max(paramDouble1, paramDouble2), paramDouble3);
  }

  public static double Normalize(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return (Constrain(paramDouble1, paramDouble2, paramDouble3) - paramDouble2) / (paramDouble3 - paramDouble2);
  }

  static double getArcInRadians(LatLong paramLatLong1, LatLong paramLatLong2)
  {
    double d1 = Math.toRadians(paramLatLong1.getLatitude() - paramLatLong2.getLatitude());
    double d2 = Math.toRadians(paramLatLong1.getLongitude() - paramLatLong2.getLongitude());
    double d3 = Math.sin(0.5D * d1);
    double d4 = d3 * d3;
    double d5 = Math.sin(0.5D * d2);
    return Math.toDegrees(2.0D * Math.asin(Math.sqrt(d4 + d5 * d5 * (Math.cos(Math.toRadians(paramLatLong1.getLatitude())) * Math.cos(Math.toRadians(paramLatLong2.getLatitude()))))));
  }

  public static double getDistance2D(LatLong paramLatLong1, LatLong paramLatLong2)
  {
    if ((paramLatLong1 == null) || (paramLatLong2 == null))
      return -1.0D;
    return 6378137.0D * Math.toRadians(getArcInRadians(paramLatLong1, paramLatLong2));
  }

  public static double getDistance3D(LatLongAlt paramLatLongAlt1, LatLongAlt paramLatLongAlt2)
  {
    if ((paramLatLongAlt1 == null) || (paramLatLongAlt2 == null))
      return -1.0D;
    return Math.sqrt(Math.pow(getDistance2D(paramLatLongAlt1, paramLatLongAlt2), 2.0D) + Math.pow(paramLatLongAlt2.getAltitude() - paramLatLongAlt1.getAltitude(), 2.0D));
  }

  public static double getHeadingFromCoordinates(LatLong paramLatLong1, LatLong paramLatLong2)
  {
    double d1 = Math.toRadians(paramLatLong1.getLatitude());
    double d2 = Math.toRadians(paramLatLong1.getLongitude());
    double d3 = Math.toRadians(paramLatLong2.getLatitude());
    double d4 = Math.toRadians(paramLatLong2.getLongitude());
    double d5 = Math.toDegrees(Math.atan2(Math.sin(d4 - d2) * Math.cos(d3), Math.cos(d1) * Math.sin(d3) - Math.sin(d1) * Math.cos(d3) * Math.cos(d4 - d2)));
    if (d5 >= 0.0D)
      return d5;
    return d5 + 360.0D;
  }

  public static double getPolylineLength(List<LatLong> paramList)
  {
    double d = 0.0D;
    int i = 1;
    if (i < paramList.size())
    {
      LatLong localLatLong = (LatLong)paramList.get(i - 1);
      if (localLatLong == null);
      while (true)
      {
        i++;
        break;
        d += getDistance2D((LatLong)paramList.get(i), localLatLong);
      }
    }
    return d;
  }

  public static int getSignalStrength(double paramDouble1, double paramDouble2)
  {
    return (int)(100.0D * Normalize(Math.min(paramDouble1, paramDouble2), 6.0D, 50.0D));
  }

  public static LatLong newCoordFromBearingAndDistance(LatLong paramLatLong, double paramDouble1, double paramDouble2)
  {
    double d1 = paramLatLong.getLatitude();
    double d2 = paramLatLong.getLongitude();
    double d3 = Math.toRadians(d1);
    double d4 = Math.toRadians(d2);
    double d5 = Math.toRadians(paramDouble1);
    double d6 = paramDouble2 / 6378137.0D;
    double d7 = Math.asin(Math.sin(d3) * Math.cos(d6) + Math.cos(d3) * Math.sin(d6) * Math.cos(d5));
    double d8 = d4 + Math.atan2(Math.sin(d5) * Math.sin(d6) * Math.cos(d3), Math.cos(d6) - Math.sin(d3) * Math.sin(d7));
    LatLong localLatLong = new LatLong(Math.toDegrees(d7), Math.toDegrees(d8));
    return localLatLong;
  }

  public static double pointToLineDistance(LatLong paramLatLong1, LatLong paramLatLong2, LatLong paramLatLong3)
  {
    double d1 = paramLatLong3.getLatitude() - paramLatLong1.getLatitude();
    double d2 = paramLatLong3.getLongitude() - paramLatLong1.getLongitude();
    double d3 = paramLatLong2.getLatitude() - paramLatLong1.getLatitude();
    double d4 = paramLatLong2.getLongitude() - paramLatLong1.getLongitude();
    double d5 = (d1 * d3 + d2 * d4) / (d3 * d3 + d4 * d4);
    double d6;
    double d7;
    if (d5 < 0.0D)
    {
      d6 = paramLatLong1.getLatitude();
      d7 = paramLatLong1.getLongitude();
    }
    while (true)
    {
      return Math.hypot(d6 - paramLatLong3.getLatitude(), d7 - paramLatLong3.getLongitude());
      if (d5 > 1.0D)
      {
        d6 = paramLatLong2.getLatitude();
        d7 = paramLatLong2.getLongitude();
      }
      else
      {
        d6 = paramLatLong1.getLatitude() + d5 * d3;
        d7 = paramLatLong1.getLongitude() + d5 * d4;
      }
    }
  }

  public static List<LatLong> simplify(List<LatLong> paramList, double paramDouble)
  {
    int i = 0;
    double d1 = 0.0D;
    int j = -1 + paramList.size();
    for (int k = 1; k < j; k++)
    {
      double d2 = pointToLineDistance((LatLong)paramList.get(0), (LatLong)paramList.get(j), (LatLong)paramList.get(k));
      if (d2 > d1)
      {
        i = k;
        d1 = d2;
      }
    }
    ArrayList localArrayList = new ArrayList();
    if (d1 > paramDouble)
    {
      List localList1 = simplify(paramList.subList(0, i + 1), paramDouble);
      List localList2 = simplify(paramList.subList(i, j + 1), paramDouble);
      localList1.remove(-1 + localList1.size());
      localArrayList.addAll(localList1);
      localArrayList.addAll(localList2);
      return localArrayList;
    }
    localArrayList.add(paramList.get(0));
    localArrayList.add(paramList.get(j));
    return localArrayList;
  }

  public static class Spline
  {
    private static final float SPLINE_TENSION = 1.6F;
    private LatLong a;
    private LatLong b;
    private LatLong p0;
    private LatLong p0_prime;

    public Spline(LatLong paramLatLong1, LatLong paramLatLong2, LatLong paramLatLong3, LatLong paramLatLong4)
    {
      this.p0 = paramLatLong2;
      this.p0_prime = paramLatLong3.subtract(paramLatLong1).dot(0.625D);
      LatLong localLatLong = paramLatLong4.subtract(this.p0).dot(0.625D);
      LatLong[] arrayOfLatLong1 = new LatLong[4];
      arrayOfLatLong1[0] = this.p0.dot(2.0D);
      arrayOfLatLong1[1] = paramLatLong3.dot(-2.0D);
      arrayOfLatLong1[2] = this.p0_prime;
      arrayOfLatLong1[3] = localLatLong;
      this.a = LatLong.sum(arrayOfLatLong1);
      LatLong[] arrayOfLatLong2 = new LatLong[4];
      arrayOfLatLong2[0] = this.p0.dot(-3.0D);
      arrayOfLatLong2[1] = paramLatLong3.dot(3.0D);
      arrayOfLatLong2[2] = this.p0_prime.dot(-2.0D);
      arrayOfLatLong2[3] = localLatLong.negate();
      this.b = LatLong.sum(arrayOfLatLong2);
    }

    private LatLong evaluate(float paramFloat)
    {
      float f1 = paramFloat * paramFloat;
      float f2 = f1 * paramFloat;
      LatLong[] arrayOfLatLong = new LatLong[4];
      arrayOfLatLong[0] = this.a.dot(f2);
      arrayOfLatLong[1] = this.b.dot(f1);
      arrayOfLatLong[2] = this.p0_prime.dot(paramFloat);
      arrayOfLatLong[3] = this.p0;
      return LatLong.sum(arrayOfLatLong);
    }

    public List<LatLong> generateCoordinates(int paramInt)
    {
      ArrayList localArrayList = new ArrayList();
      float f1 = 1.0F / paramInt;
      for (float f2 = 0.0F; f2 < 1.0F; f2 += f1)
        localArrayList.add(evaluate(f2));
      return localArrayList;
    }
  }

  public static class SplinePath
  {
    private static final int SPLINE_DECIMATION = 20;
    private static final String TAG = SplinePath.class.getSimpleName();

    public static List<LatLong> process(List<LatLong> paramList)
    {
      int i = paramList.size();
      if (i < 4)
      {
        System.err.println("Not enough points!");
        return paramList;
      }
      List localList = processPath(paramList);
      localList.add(0, paramList.get(0));
      localList.add(paramList.get(i - 1));
      return localList;
    }

    private static List<LatLong> processPath(List<LatLong> paramList)
    {
      ArrayList localArrayList = new ArrayList();
      for (int i = 3; i < paramList.size(); i++)
        localArrayList.addAll(processPathSegment((LatLong)paramList.get(i - 3), (LatLong)paramList.get(i - 2), (LatLong)paramList.get(i - 1), (LatLong)paramList.get(i)));
      return localArrayList;
    }

    private static List<LatLong> processPathSegment(LatLong paramLatLong1, LatLong paramLatLong2, LatLong paramLatLong3, LatLong paramLatLong4)
    {
      return new MathUtils.Spline(paramLatLong1, paramLatLong2, paramLatLong3, paramLatLong4).generateCoordinates(20);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.util.MathUtils
 * JD-Core Version:    0.6.2
 */