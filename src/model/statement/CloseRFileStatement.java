package model.statement;

import Exceptions.CustomException;
import Exceptions.InvalidTypeException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.ProgramState;
import model.type.IType;
import model.type.StringType;
import model.value.StringValue;

import java.io.InvalidClassException;

public record CloseRFileStatement(Expression expression) implements Statement {

  @Override
  public ProgramState execute(ProgramState state) throws CustomException {
    var value = expression.evaluate(state.getSymTable(), state.getHeap());
    if (!(value instanceof StringValue(String fileName))) {
      throw new InvalidTypeException("Type must be String");
    }
    state.getFileTable().closeFile(fileName);
    return null;
  }

  @Override
  public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv)
      throws CustomException {
    IType typexp = expression.typecheck(typeEnv);
    if (typexp.equals(StringType.getInstance())) {
      return typeEnv;
    } else {
      throw new CustomException("CloseRFile: expression is not a string");
    }
  }
}
