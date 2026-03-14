package model.value;

import model.type.IType;
import model.type.RefType;
import model.type.Type;

public record RefValue(int address, IType locationType) implements Value {

  @Override
  public IType getType() {
    return new RefType(locationType);
  }

  public int getAddress() {
    return address;
  }

  public IType getLocationType() {
    return locationType;
  }

  @Override
  public String toString() {
    return "Ref(" + address + ", " + locationType + ")";
  }
}
