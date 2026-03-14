package model.statement;

import Exceptions.CustomException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.BooleanType;
import model.type.IType;
import model.value.BooleanValue;
import model.value.Value;

public record IfStatement(Expression condition, Statement thenBranch, Statement elseBranch)
    implements Statement {

  @Override
  public ProgramState execute(ProgramState state) throws CustomException {
    Value result = condition.evaluate(state.getSymTable(), state.getHeap());
    if (result instanceof BooleanValue booleanValue) {
      if (booleanValue.value()) {
        state.getExecutionStack().push(thenBranch);
      } else {
        state.getExecutionStack().push(elseBranch);
      }
    } else {
      throw new CustomException("Condition expression does not evaluate to a boolean.");
    }
    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    IType typexp = condition.typecheck(typeEnv);
    if (typexp.equals(BooleanType.getInstance())) {
      thenBranch.typecheck(typeEnv.deepCopy());
      elseBranch.typecheck(typeEnv.deepCopy());
      return typeEnv;
    } else {
      throw new CustomException("The condition of IF has not the type bool");
    }
  }

  @Override
  public String toString() {
    return "if("
        + condition.toString()
        + ") then("
        + thenBranch.toString()
        + ") else("
        + elseBranch.toString()
        + ")";
  }
}
