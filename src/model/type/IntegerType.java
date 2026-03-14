package model.type;

import model.value.IntegerValue;
import model.value.Value;

public class IntegerType implements IType {
  private static IntegerType instance;

  private IntegerType() {}

  public static IntegerType getInstance() {
    if (instance == null) {
      instance = new IntegerType();
    }
    return instance;
  }

  @Override
  public Value getDefaultValue() {
    return new IntegerValue(0);
  }

  @Override
  public boolean equals(Object another) {
    return another instanceof IntegerType;
  }

  @Override
  public String toString() {
    return "int";
  }
}
