package model.type;

import model.value.BooleanValue;
import model.value.Value;

public class BooleanType implements IType {
  private static BooleanType instance;

  private BooleanType() {}

  public static BooleanType getInstance() {
    if (instance == null) {
      instance = new BooleanType();
    }
    return instance;
  }

  @Override
  public Value getDefaultValue() {
    return new BooleanValue(false);
  }

  @Override
  public boolean equals(Object another) {
    return another instanceof BooleanType;
  }

  @Override
  public String toString() {
    return "boolean";
  }
}
