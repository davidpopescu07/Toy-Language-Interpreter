package model.type;

import model.value.*;

public enum Type {
  INTEGER,
  BOOLEAN,
  STRING;

  public Value getDefaultValue() {
    switch (this) {
      case INTEGER:
        return new IntegerValue(0);
      case BOOLEAN:
        return new BooleanValue(false);
      case STRING:
        return new StringValue("");
    }
    return null;
  }
}
