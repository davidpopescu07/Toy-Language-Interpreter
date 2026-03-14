package model.value;

import model.type.IType;
import model.type.Type;

public interface Value {
  IType getType();

  String toString();
}
