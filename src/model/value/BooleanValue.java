package model.value;

import model.type.BooleanType;
import model.type.IType;

public record BooleanValue(boolean value) implements Value {

  @Override
  public IType getType() {
    return BooleanType.getInstance();
  }

  public String toString() {
    return String.valueOf(value);
  }

  public boolean getValue() {
    return value;
  }
}
