package model.statement;

import Exceptions.CustomException;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.state.ExecutionStack;
import model.type.IType;

public record CompoundStatement(Statement first, Statement second) implements Statement {

  @Override
  public ProgramState execute(ProgramState state) {
    ExecutionStack stack = state.getExecutionStack();
    stack.push(second);
    stack.push(first);
    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    return second.typecheck(first.typecheck(typeEnv));
  }

  @Override
  public String toString() {
    return "(" + first.toString() + "; " + second.toString() + ")";
  }
}
