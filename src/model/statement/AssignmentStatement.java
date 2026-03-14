package model.statement;

import Exceptions.CustomException;
import Exceptions.InvalidTypeException;
import Exceptions.KeyNotFoundException;
import Exceptions.SymbolNotFoundException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.state.MapSymbolTable;
import model.state.SymbolTable;
import model.type.IType;
import model.value.Value;

public record AssignmentStatement(Expression expression, String variableName) implements Statement {

  @Override
  public ProgramState execute(ProgramState state) throws CustomException {
    MapSymbolTable symbolTable = state.getSymTable();
    if (!symbolTable.isDefined(variableName)) {
      throw new SymbolNotFoundException("Variable not defined: " + variableName);
    }
    Value value = expression.evaluate(symbolTable, state.getHeap());
    if (!value.getType().equals(symbolTable.getType(variableName))) {
      throw new InvalidTypeException("Type mismatch for variable: " + variableName);
    }
    symbolTable.update(variableName, value);
    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    IType typevar = typeEnv.lookup(variableName);
    IType typeexp = expression.typecheck(typeEnv);
    if (typevar.equals(typeexp)) {
      return typeEnv;
    } else throw new CustomException("Right hand side and left hand side have different types");
  }

  @Override
  public String toString() {
    return variableName + " = " + expression.toString();
  }
}
