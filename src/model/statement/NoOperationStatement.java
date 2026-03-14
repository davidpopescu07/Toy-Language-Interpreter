package model.statement;

import Exceptions.CustomException;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.IType;

public class NoOperationStatement implements Statement {

  @Override
  public ProgramState execute(ProgramState state) {
    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    return typeEnv;
  }

  @Override
  public String toString() {
    return "nop()";
  }
}
