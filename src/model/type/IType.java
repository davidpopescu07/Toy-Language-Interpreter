package model.type;

import model.value.RefValue;
import model.value.Value;

public interface IType {
  Value getDefaultValue();

  boolean equals(Object another);

  String toString();
}
