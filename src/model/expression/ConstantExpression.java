package model.expression;

import Exceptions.CustomException;
import model.state.Heap;
import model.state.MyIDictionary;
import model.state.SymbolTable;
import model.type.IType;
import model.type.Type;
import model.value.Value;

import java.util.Map;

public record ConstantExpression(Value value) implements Expression {
  @Override
  public Value evaluate(SymbolTable symTable, Heap<Integer, Value> heap) {
    return value;
  }

  @Override
  public IType typecheck(MyIDictionary<String, IType> typeEnv) throws CustomException {
    return value.getType();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
