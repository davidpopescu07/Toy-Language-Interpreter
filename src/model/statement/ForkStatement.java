package model.statement;

import Exceptions.CustomException;
import model.state.*;
import model.type.IType;

import java.io.InvalidClassException;

public record ForkStatement(Statement statement) implements Statement {
  @Override
  public ProgramState execute(ProgramState state) throws CustomException, InvalidClassException {
    StackExecutionStack executionStack = new StackExecutionStack();
    executionStack.push(statement);
    return new ProgramState(
        executionStack,
        (MapSymbolTable) state.getSymTable().deepCopy(),
        state.getOut(),
        state.getFileTable(),
        state.getHeap(),
        state.getSemaphoreTable());
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    statement.typecheck(typeEnv.deepCopy());
    return typeEnv;
  }

  @Override
  public String toString() {
    return "fork(" + statement.toString() + ")";
  }
}
