package model.value;

import model.type.IType;
import model.type.IntegerType;

public record IntegerValue(int value) implements Value {

  @Override
  public IType getType() {
    return IntegerType.getInstance();
  }

  public String toString() {
    return String.valueOf(value);
  }

  public int getValue() {
    return value;
  }
}
