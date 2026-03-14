package model.statement;

import Exceptions.CustomException;
import Exceptions.InvalidTypeException;
import Exceptions.SymbolNotFoundException;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.IType;

import java.io.InvalidClassException;

public interface Statement {
  ProgramState execute(ProgramState state) throws CustomException, InvalidClassException;

  default Statement copyStatement() {
    return this;
  }

  MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException;

  String toString();
}
