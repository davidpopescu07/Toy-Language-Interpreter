package model.expression;

import Exceptions.CustomException;
import model.state.Heap;
import model.state.MyIDictionary;
import model.state.SymbolTable;
import model.type.BooleanType;
import model.type.IType;
import model.value.BooleanValue;
import model.value.Value;

public record NotExpression(Expression exp) implements Expression {
  @Override
  public Value evaluate(SymbolTable symTable, Heap<Integer, Value> heap) throws CustomException {
    Value val = exp.evaluate(symTable, heap);
    if (!(val instanceof BooleanValue boolVal)) throw new CustomException("Not boolean value");
    return new BooleanValue(!boolVal.getValue());
  }

  @Override
  public IType typecheck(MyIDictionary<String, IType> typeEnv) throws CustomException {
    IType expType = exp.typecheck(typeEnv);
    if (!expType.equals(BooleanType.getInstance()))
      throw new CustomException("Negated expression is not boolean");
    return BooleanType.getInstance();
  }

  @Override
  public String toString() {
    return "!(" + exp.toString() + ")";
  }
}
