package org.beyene.sius.operation;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.dimension.composition.util.Fraction;
import org.beyene.sius.dimension.composition.util.Product;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitFactory;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.composition.CompositeUnitId;
import org.beyene.sius.unit.composition.FractionUnit;
import org.beyene.sius.unit.composition.ProductUnit;

public final class Operation
{
  public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP1 extends Unit<D, B, OP1>> OP1 add(Unit<D, B, OP1> paramUnit, Unit<D, B, ?> paramUnit1)
  {
    return paramUnit.valueOf(paramUnit.getValue() + Converter.convert(paramUnit1, paramUnit.getIdentifier()).getValue());
  }

  public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP1 extends Unit<D, B, OP1>> OP1 add(OP1 paramOP1, Unit<D, B, ?> paramUnit1, Unit<D, B, ?> paramUnit2)
  {
    return paramOP1.valueOf(paramOP1.getValue() + Converter.convert(paramUnit1, paramOP1.getIdentifier()).getValue() + Converter.convert(paramUnit1, paramOP1.getIdentifier()).getValue());
  }

  public static <D extends Dimension<D>, B extends Unit<D, B, B>, TARGET_UNIT extends Unit<D, B, TARGET_UNIT>> TARGET_UNIT convert(Unit<D, B, ?> paramUnit, UnitId<D, B, TARGET_UNIT> paramUnitId)
  {
    return Converter.convert(paramUnit, paramUnitId);
  }

  public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP extends Unit<D, B, OP>> OP div(OP paramOP, double paramDouble)
  {
    return paramOP.valueOf(paramOP.getValue() / paramDouble);
  }

  public static <DIM_DIVIDEND extends Dimension<DIM_DIVIDEND>, BASE_DIVIDEND extends Unit<DIM_DIVIDEND, BASE_DIVIDEND, BASE_DIVIDEND>, DIVIDEND extends Unit<DIM_DIVIDEND, BASE_DIVIDEND, DIVIDEND>, DIM_DIVISOR extends Dimension<DIM_DIVISOR>, BASE_DIVISOR extends Unit<DIM_DIVISOR, BASE_DIVISOR, BASE_DIVISOR>, DIVISOR extends Unit<DIM_DIVISOR, BASE_DIVISOR, DIVISOR>, DIM_FRACTION extends Fraction<DIM_DIVIDEND, DIM_DIVISOR, DIM_FRACTION>, BASE_FRACTION extends Unit<DIM_FRACTION, BASE_FRACTION, BASE_FRACTION>, UNIT_FRACTION extends FractionUnit<DIM_DIVIDEND, BASE_DIVIDEND, DIVIDEND, DIM_DIVISOR, BASE_DIVISOR, DIVISOR, DIM_FRACTION, BASE_FRACTION, UNIT_FRACTION>, TARGET_UNIT_ID extends CompositeUnitId<DIM_DIVIDEND, BASE_DIVIDEND, DIVIDEND, DIM_DIVISOR, BASE_DIVISOR, DIVISOR, DIM_FRACTION, BASE_FRACTION, UNIT_FRACTION>> UNIT_FRACTION div(Unit<DIM_DIVIDEND, BASE_DIVIDEND, ?> paramUnit, Unit<DIM_DIVISOR, BASE_DIVISOR, ?> paramUnit1, TARGET_UNIT_ID paramTARGET_UNIT_ID)
  {
    return (FractionUnit)UnitFactory.valueOf(Converter.convert(paramUnit, paramTARGET_UNIT_ID.getComponentUnit1Id()).getValue() / Converter.convert(paramUnit1, paramTARGET_UNIT_ID.getComponentUnit2Id()).getValue(), paramTARGET_UNIT_ID);
  }

  public static <DIM_FACTOR1 extends Dimension<DIM_FACTOR1>, BASE_FACTOR1 extends Unit<DIM_FACTOR1, BASE_FACTOR1, BASE_FACTOR1>, UNIT_FACTOR1 extends Unit<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1>, DIM_FACTOR2 extends Dimension<DIM_FACTOR2>, BASE_FACTOR2 extends Unit<DIM_FACTOR2, BASE_FACTOR2, BASE_FACTOR2>, UNIT_FACTOR2 extends Unit<DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2>, DIM_PRODUCT extends Product<DIM_FACTOR1, DIM_FACTOR2, DIM_PRODUCT>, BASE_PRODUCT extends Unit<DIM_PRODUCT, BASE_PRODUCT, BASE_PRODUCT>, UNIT_PRODUCT extends ProductUnit<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1, DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2, DIM_PRODUCT, BASE_PRODUCT, UNIT_PRODUCT>> UNIT_FACTOR2 div1(UNIT_PRODUCT paramUNIT_PRODUCT, Unit<DIM_FACTOR1, BASE_FACTOR1, ?> paramUnit)
  {
    double d = Converter.convert(paramUnit, paramUNIT_PRODUCT.getComponentUnit1Id()).getValue();
    return UnitFactory.valueOf(paramUNIT_PRODUCT.getValue() / d, paramUNIT_PRODUCT.getComponentUnit2Id());
  }

  public static <DIM_FACTOR1 extends Dimension<DIM_FACTOR1>, BASE_FACTOR1 extends Unit<DIM_FACTOR1, BASE_FACTOR1, BASE_FACTOR1>, UNIT_FACTOR1 extends Unit<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1>, DIM_FACTOR2 extends Dimension<DIM_FACTOR2>, BASE_FACTOR2 extends Unit<DIM_FACTOR2, BASE_FACTOR2, BASE_FACTOR2>, UNIT_FACTOR2 extends Unit<DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2>, DIM_PRODUCT extends Product<DIM_FACTOR1, DIM_FACTOR2, DIM_PRODUCT>, BASE_PRODUCT extends Unit<DIM_PRODUCT, BASE_PRODUCT, BASE_PRODUCT>, UNIT_PRODUCT extends ProductUnit<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1, DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2, DIM_PRODUCT, BASE_PRODUCT, UNIT_PRODUCT>> UNIT_FACTOR1 div2(UNIT_PRODUCT paramUNIT_PRODUCT, Unit<DIM_FACTOR2, BASE_FACTOR2, ?> paramUnit)
  {
    double d = Converter.convert(paramUnit, paramUNIT_PRODUCT.getComponentUnit2Id()).getValue();
    return UnitFactory.valueOf(paramUNIT_PRODUCT.getValue() / d, paramUNIT_PRODUCT.getComponentUnit1Id());
  }

  public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP extends Unit<D, B, OP>> OP mul(OP paramOP, double paramDouble)
  {
    return paramOP.valueOf(paramDouble * paramOP.getValue());
  }

  public static <DIM_NUMERATOR extends Dimension<DIM_NUMERATOR>, BASE_NUMERATOR extends Unit<DIM_NUMERATOR, BASE_NUMERATOR, BASE_NUMERATOR>, UNIT_NUMERATOR extends Unit<DIM_NUMERATOR, BASE_NUMERATOR, UNIT_NUMERATOR>, DIM_DENOMINATOR extends Dimension<DIM_DENOMINATOR>, BASE_DENOMINATOR extends Unit<DIM_DENOMINATOR, BASE_DENOMINATOR, BASE_DENOMINATOR>, UNIT_DENOMINATOR extends Unit<DIM_DENOMINATOR, BASE_DENOMINATOR, UNIT_DENOMINATOR>, DIM_FRACTION extends Fraction<DIM_NUMERATOR, DIM_DENOMINATOR, DIM_FRACTION>, BASE_FRACTION extends Unit<DIM_FRACTION, BASE_FRACTION, BASE_FRACTION>, UNIT_FRACTION extends FractionUnit<DIM_NUMERATOR, BASE_NUMERATOR, UNIT_NUMERATOR, DIM_DENOMINATOR, BASE_DENOMINATOR, UNIT_DENOMINATOR, DIM_FRACTION, BASE_FRACTION, UNIT_FRACTION>> UNIT_NUMERATOR mul(UNIT_FRACTION paramUNIT_FRACTION, Unit<DIM_DENOMINATOR, BASE_DENOMINATOR, ?> paramUnit)
  {
    return UnitFactory.valueOf(Converter.convert(paramUnit, paramUNIT_FRACTION.getComponentUnit2Id()).getValue() * paramUNIT_FRACTION.getValue(), paramUNIT_FRACTION.getComponentUnit1Id());
  }

  public static <DIM_FACTOR1 extends Dimension<DIM_FACTOR1>, BASE_FACTOR1 extends Unit<DIM_FACTOR1, BASE_FACTOR1, BASE_FACTOR1>, UNIT_FACTOR1 extends Unit<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1>, DIM_FACTOR2 extends Dimension<DIM_FACTOR2>, BASE_FACTOR2 extends Unit<DIM_FACTOR2, BASE_FACTOR2, BASE_FACTOR2>, UNIT_FACTOR2 extends Unit<DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2>, DIM_PRODUCT extends Product<DIM_FACTOR1, DIM_FACTOR2, DIM_PRODUCT>, BASE_PRODUCT extends Unit<DIM_PRODUCT, BASE_PRODUCT, BASE_PRODUCT>, UNIT_PRODUCT extends ProductUnit<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1, DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2, DIM_PRODUCT, BASE_PRODUCT, UNIT_PRODUCT>, TARGET_UNIT_ID extends CompositeUnitId<DIM_FACTOR1, BASE_FACTOR1, UNIT_FACTOR1, DIM_FACTOR2, BASE_FACTOR2, UNIT_FACTOR2, DIM_PRODUCT, BASE_PRODUCT, UNIT_PRODUCT>> UNIT_PRODUCT mul(Unit<DIM_FACTOR1, BASE_FACTOR1, ?> paramUnit, Unit<DIM_FACTOR2, BASE_FACTOR2, ?> paramUnit1, TARGET_UNIT_ID paramTARGET_UNIT_ID)
  {
    return (ProductUnit)UnitFactory.valueOf(Converter.convert(paramUnit, paramTARGET_UNIT_ID.getComponentUnit1Id()).getValue() * Converter.convert(paramUnit1, paramTARGET_UNIT_ID.getComponentUnit2Id()).getValue(), paramTARGET_UNIT_ID);
  }

  public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP extends Unit<D, B, OP>> OP sub(OP paramOP, Unit<D, B, ?> paramUnit)
  {
    return paramOP.valueOf(paramOP.getValue() - Converter.convert(paramUnit, paramOP.getIdentifier()).getValue());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.operation.Operation
 * JD-Core Version:    0.6.2
 */