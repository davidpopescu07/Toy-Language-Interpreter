package model.value;

import model.type.IType;
import model.type.StringType;

public record StringValue(String value) implements Value {

  @Override
  public IType getType() {
    return StringType.getInstance();
  }

  public String getValue() {
    return value;
  }
}
