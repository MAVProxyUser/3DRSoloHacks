package org.beyene.sius.unit.impl;

import org.beyene.sius.cache.Cache;
import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;

public abstract class AbstractUnit<D extends Dimension<D>, BASE extends Unit<D, BASE, BASE>, SELF extends Unit<D, BASE, SELF>>
  implements Unit<D, BASE, SELF>
{
  private final D dimension;
  private final transient Cache<D, BASE, SELF> dynamicCache;
  private final Class<? extends SELF> interfaceClass;
  private final transient StaticCache<D, BASE, SELF> staticCache;
  private final UnitId<D, BASE, SELF> unitId;
  protected final double value;

  public AbstractUnit(double paramDouble, D paramD, UnitId<D, BASE, SELF> paramUnitId, Class<? extends SELF> paramClass, Cache<D, BASE, SELF> paramCache, StaticCache<D, BASE, SELF> paramStaticCache)
  {
    this.value = paramDouble;
    this.dimension = paramD;
    this.unitId = paramUnitId;
    this.interfaceClass = paramClass;
    this.dynamicCache = paramCache;
    this.staticCache = paramStaticCache;
  }

  private boolean _has_dynamic_cache()
  {
    return this.dynamicCache != null;
  }

  private boolean _has_static_cache()
  {
    return this.staticCache != null;
  }

  protected abstract SELF _new_instance(double paramDouble);

  protected abstract SELF _this();

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Unit localUnit;
    do
    {
      return true;
      if (paramObject == null)
        return false;
      if (!this.interfaceClass.isAssignableFrom(paramObject.getClass()))
        return false;
      localUnit = (Unit)this.interfaceClass.cast(paramObject);
    }
    while (Double.doubleToLongBits(this.value) == Double.doubleToLongBits(localUnit.getValue()));
    return false;
  }

  public abstract SELF fromBase(BASE paramBASE);

  public D getDimension()
  {
    return this.dimension;
  }

  public UnitId<D, BASE, SELF> getIdentifier()
  {
    return this.unitId;
  }

  public double getValue()
  {
    return this.value;
  }

  public int hashCode()
  {
    if (this.interfaceClass == null);
    for (int i = 0; ; i = this.interfaceClass.getName().hashCode())
    {
      int j = i + 31;
      long l = Double.doubleToLongBits(this.value);
      return j * 31 + (int)(l ^ l >>> 32);
    }
  }

  public abstract BASE toBase();

  public String toString()
  {
    return this.interfaceClass.getSimpleName() + " [value=" + this.value + "]";
  }

  public SELF valueOf(double paramDouble)
  {
    Unit localUnit1;
    if (this.value == paramDouble)
      localUnit1 = _this();
    do
    {
      return localUnit1;
      if ((_has_static_cache()) && (paramDouble == Math.floor(paramDouble)) && (!Double.isInfinite(paramDouble)))
      {
        int i = (int)paramDouble;
        if ((i >= this.staticCache.low) && (i <= this.staticCache.high))
          return this.staticCache.cache[(i + -this.staticCache.low)];
      }
      if (!_has_dynamic_cache())
        break;
      localUnit1 = this.dynamicCache.lookUp(paramDouble);
    }
    while (localUnit1 != null);
    Unit localUnit2 = _new_instance(paramDouble);
    this.dynamicCache.put(localUnit2);
    return localUnit2;
    return _new_instance(paramDouble);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.unit.impl.AbstractUnit
 * JD-Core Version:    0.6.2
 */