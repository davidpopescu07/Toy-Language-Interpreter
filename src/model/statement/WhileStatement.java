package model.statement;

import Exceptions.CustomException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.BooleanType;
import model.type.IType;
import model.value.BooleanValue;
import model.value.Value;

public record WhileStatement(Expression condition, Statement body) implements Statement {

  @Override
  public ProgramState execute(ProgramState state) throws CustomException {
    Value result = condition.evaluate(state.getSymTable(), state.getHeap());
    if (result instanceof BooleanValue booleanValue) {
      if (booleanValue.value()) {
        state.getExecutionStack().push(this);
        state.getExecutionStack().push(body);
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
      body.typecheck(typeEnv.deepCopy());
      return typeEnv;
    } else {
      throw new CustomException("The condition of WHILE has not the type bool");
    }
  }

  @Override
  public String toString() {
    return "while(" + condition.toString() + ") " + body.toString();
  }
}
