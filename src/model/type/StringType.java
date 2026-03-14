package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements IType {
  private static StringType instance;

  private StringType() {}

  public static StringType getInstance() {
    if (instance == null) {
      instance = new StringType();
    }
    return instance;
  }

  @Override
  public Value getDefaultValue() {
    return new StringValue("");
  }

  @Override
  public boolean equals(Object another) {
    return another instanceof StringType;
  }

  @Override
  public String toString() {
    return "String";
  }
}
