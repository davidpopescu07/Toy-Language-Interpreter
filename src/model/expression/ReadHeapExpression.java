package model.expression;

import Exceptions.CustomException;
import Exceptions.InvalidAddressException;
import model.state.MyIDictionary;
import model.state.SymbolTable;
import model.state.Heap;
import model.type.IType;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;

public class ReadHeapExpression implements Expression {

  private final Expression expression;

  public ReadHeapExpression(Expression expression) {
    this.expression = expression;
  }

  @Override
  public Value evaluate(SymbolTable symTable, Heap<Integer, Value> heap) throws CustomException {
    Value val = expression.evaluate(symTable, heap);

    if (!(val instanceof RefValue refVal)) {
      throw new CustomException("rH argument is not a RefValue");
    }

    int address = refVal.getAddress();

    try {
      return heap.get(address);
    } catch (InvalidAddressException e) {
      throw new CustomException("Invalid heap address: " + address);
    }
  }

  @Override
  public IType typecheck(MyIDictionary<String, IType> typeEnv) throws CustomException {
    IType typ = expression.typecheck(typeEnv);
    if (typ instanceof RefType reft) {
      return reft.getInner();
    } else {
      throw new CustomException("The rH argument is not a Ref Type");
    }
  }

  @Override
  public String toString() {
    return "rH(" + expression.toString() + ")";
  }
}
