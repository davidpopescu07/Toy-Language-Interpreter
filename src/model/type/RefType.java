package model.type;

import model.value.RefValue;
import model.value.Value;

public class RefType implements IType {
  private final IType inner;

  public RefType(IType inner) {
    this.inner = inner;
  }

  public IType getInner() {
    return inner;
  }

  @Override
  public boolean equals(Object another) {
    if (another instanceof RefType r) {
      return inner.equals(r.getInner());
    }
    return false;
  }

  @Override
  public Value getDefaultValue() {
    return new RefValue(0, inner);
  }

  @Override
  public String toString() {
    return "Ref(" + inner.toString() + ")";
  }
}
