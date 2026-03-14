package model.statement;

import Exceptions.CustomException;
import Exceptions.SymbolAlreadyExistsException;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.state.MapSymbolTable;
import model.type.IType;
import model.value.Value;

public record VariableDeclarationStatement(IType type, String variableName) implements Statement {

  @Override
  public ProgramState execute(ProgramState state) throws CustomException {
    MapSymbolTable symbolTable = state.getSymTable();
    if (symbolTable.isDefined(variableName)) {
      throw new SymbolAlreadyExistsException("Variable is already defined: " + variableName);
    }
    symbolTable.declareVariable(variableName, type);
    return state;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    typeEnv.add(variableName, type);
    return typeEnv;
  }

  @Override
  public String toString() {
    return type + " " + variableName;
  }
}
