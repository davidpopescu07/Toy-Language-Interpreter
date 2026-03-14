package model.expression;

import Exceptions.CustomException;
import model.state.Heap;
import model.state.MyIDictionary;
import model.state.SymbolTable;
import model.type.IType;
import model.type.Type;
import model.value.Value;

import java.util.Map;

public record VariableExpression(String variableName) implements Expression {
  @Override
  public Value evaluate(SymbolTable symTable, Heap<Integer, Value> heap) throws CustomException {
    if (!symTable.isDefined(variableName)) throw new CustomException("Variable is not defined");
    return symTable.getValue(variableName);
  }

  @Override
  public IType typecheck(MyIDictionary<String, IType> typeEnv) throws CustomException {
    return typeEnv.lookup(variableName);
  }

  @Override
  public String toString() {
    return variableName;
  }
}
