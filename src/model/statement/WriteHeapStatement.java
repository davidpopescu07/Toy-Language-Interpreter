package model.statement;

import Exceptions.CustomException;
import Exceptions.InvalidAddressException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.state.Heap;
import model.type.IType;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;

public class WriteHeapStatement implements Statement {

  private final Expression refExpression;
  private final Expression valueExpression;

  public WriteHeapStatement(Expression refExpression, Expression valueExpression) {
    this.refExpression = refExpression;
    this.valueExpression = valueExpression;
  }

  @Override
  public ProgramState execute(ProgramState state) throws CustomException {
    SymbolTable sym = state.getSymTable();
    Heap<Integer, Value> heap = state.getHeap();

    Value val = refExpression.evaluate(sym, heap);
    if (!(val instanceof RefValue refVal)) {
      throw new CustomException("wH argument is not a RefValue");
    }

    int address = refVal.getAddress();

    try {
      heap.get(address);
    } catch (InvalidAddressException e) {
      throw new CustomException("Invalid heap address: " + address);
    }

    Value newVal = valueExpression.evaluate(sym, heap);

    if (!newVal.getType().equals(refVal.getLocationType())) {
      throw new CustomException(
          "Type mismatch in wH(): expected "
              + refVal.getLocationType()
              + ", got "
              + newVal.getType());
    }

    heap.put(address, newVal);

    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    IType typref = refExpression.typecheck(typeEnv);
    IType typval = valueExpression.typecheck(typeEnv);

    if (typref instanceof RefType reft) {
      if (reft.getInner().equals(typval)) {
        return typeEnv;
      } else {
        throw new CustomException(
            "WriteHeap: the expression type does not match the reference inner type");
      }
    } else {
      throw new CustomException("WriteHeap: first argument is not a Ref Type");
    }
  }

  @Override
  public String toString() {
    return "wH(" + refExpression + ", " + valueExpression + ")";
  }
}
