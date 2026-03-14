package model.statement;

import Exceptions.CustomException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.IType;

public record PrintStatement(Expression expression) implements Statement {

  @Override
  public ProgramState execute(ProgramState state) throws CustomException {
    state.getOut().add(expression.evaluate(state.getSymTable(), state.getHeap()));
    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    expression.typecheck(typeEnv);
    return typeEnv;
  }

  @Override
  public String toString() {
    return "print(" + expression.toString() + ")";
  }
}
